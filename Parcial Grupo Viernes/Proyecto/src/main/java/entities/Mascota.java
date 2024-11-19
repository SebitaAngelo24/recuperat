package entities;

import exceptions.MascotaMuertaException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity @Table(name = "Mascotas")
public class Mascota {

    // Attributes

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nivel_energia")
    private int nivel_energia;

    @Column(name = "nivel_humor")
    private int nivel_humor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Habilidades_x_Mascota",
            joinColumns = @JoinColumn(name = "id_mascota"),
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private List<Habilidad> habilidades;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
    private List<HistoriaMascota> historicoAcciones;

    private int contadorIngestas;
    private int contadorIngestasConsecutivas;
    private int contadorEjercitaciones;
    private int contadorEjercitacionesConsecutivas;
    private int maxRachaFitness;

    // Constructors

    public Mascota() {
    }

    public Mascota(String nombre,
                   int nivel_energia,
                   int nivel_humor,
                   List<Habilidad> habilidades)
    {
        this.nombre = nombre;
        this.nivel_energia = nivel_energia;
        this.nivel_humor = nivel_humor;
        this.habilidades = habilidades;
        this.historicoAcciones = new ArrayList<>();
    }

    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel_energia() {
        return nivel_energia;
    }

    public void setNivel_energia(int nivel_energia) {
        this.nivel_energia = nivel_energia;
    }

    public int getNivel_humor() {
        return nivel_humor;
    }

    public void setNivel_humor(int nivel_humor) {
        this.nivel_humor = nivel_humor;
    }

    // Methods (Acciones)

    public boolean estaViva() {
        return nivel_energia > 0;
    }

    public void ingerirAlimentos() {
        if (!estaViva()) {
            throw new MascotaMuertaException("La mascota está muerta, no puede realizar acciones.");
        }

        int energiaInicio = this.nivel_energia;
        int humorInicio = this.nivel_humor;

        contadorEjercitacionesConsecutivas = 0;

        contadorIngestas++;
        contadorIngestasConsecutivas++;

        if (contadorIngestasConsecutivas <= 3) {
            this.nivel_energia += 10;
            this.nivel_humor++;
        } else if (contadorIngestasConsecutivas <= 5) {
            this.nivel_energia += 5;
        } else {
            this.nivel_energia = 0;
            System.out.println("La mascota a muerto por comer en exceso.");
        }
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this, "Alimentar", energiaInicio, humorInicio, this.nivel_energia, this.nivel_humor, fechaHora.format(formato));
        this.historicoAcciones.add(historia);
    }

    public void ejercitar() {
        if (!estaViva()) {
            throw new MascotaMuertaException("La mascota está muerta, no puede realizar acciones.");
        }

        int energiaInicio = this.nivel_energia;
        int humorInicio = this.nivel_humor;

        contadorIngestasConsecutivas = 0;

        contadorEjercitaciones++;
        contadorEjercitacionesConsecutivas++;
        this.nivel_energia -= 10;

        if (contadorEjercitacionesConsecutivas > maxRachaFitness) {
            maxRachaFitness = contadorEjercitacionesConsecutivas;
        }
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this, "Ejercitar", energiaInicio, humorInicio, this.nivel_energia, this.nivel_humor, fechaHora.format(formato));
        this.historicoAcciones.add(historia);
    }

    public void demostrarHabilidad(String habilidadName) {
        if (!estaViva()) {
            throw new MascotaMuertaException("La mascota está muerta, no puede realizar acciones.");
        }

        int energiaInicio = this.nivel_energia;
        int humorInicio = this.nivel_humor;

        contadorIngestasConsecutivas = 0;
        contadorEjercitacionesConsecutivas = 0;

        for (Habilidad habilidad : this.habilidades) {
            if (Objects.equals(habilidad.getNombre(), habilidadName)) {
                System.out.println("Mirá como puedo " + habilidadName + "!");
                this.nivel_humor++;
            } else {
                throw new IllegalArgumentException("La habilidad '" + habilidadName + "' no existe.");
            }
        }
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this, "DemostrarHabilidad", energiaInicio, humorInicio, this.nivel_energia, this.nivel_humor, fechaHora.format(formato));
        this.historicoAcciones.add(historia);
    }

    public float getEstadoFisico() {
        return (float) contadorEjercitaciones / contadorIngestas;
    }

    public int getRachaFitness() {
        return maxRachaFitness;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", estado fisico='" + getEstadoFisico() + '\'' +
                ", racha fitness='" + getRachaFitness() + '\'' +
                '}';
    }
}
