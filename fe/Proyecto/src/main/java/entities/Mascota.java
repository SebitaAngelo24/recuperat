package entities;

import exceptions.MascotaMuertaException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "Mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nivel_energia", nullable = false)
    private int nivelEnergia;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Habilidades_x_Mascota", joinColumns = @JoinColumn(name = "id_mascota"),
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private Set<Habilidad> habilidades = new HashSet<>();

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoriaMascota> historiaAcciones;

    public Mascota() {
    }

    public Mascota(String nombre, int nivelEnergia, Set<Habilidad> habilidades) {
        this.nombre = nombre;
        this.nivelEnergia = nivelEnergia;
        this.habilidades = habilidades;
        this.historiaAcciones = new ArrayList<>();
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(int nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Habilidad> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(Set<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    public List<HistoriaMascota> getHistoriaAcciones() {
        return historiaAcciones;
    }

    //Metodos

    //Metodo esta viva
    public boolean estaViva() {
        return nivelEnergia > 0;
    }

    //Metodo para ingerir alimentos
    public void ingerirAlimentos() {
        if (!estaViva()) {
            throw new MascotaMuertaException("La mascota esta muerta, no puede realizar acciones");
        }
        int contadorIngestasConsecutivas = 0;
        int energiaInicio = this.nivelEnergia;


        int contadorEjercicitacionesConsecutivas = 0;
        int contadorIngestas = 0;

        contadorIngestas++;
        contadorEjercicitacionesConsecutivas++;

        if(contadorIngestasConsecutivas <= 3) {
            this.nivelEnergia += 10;
        } else if(contadorIngestasConsecutivas <= 5) {
            this.nivelEnergia += 5;
        } else {
            this.nivelEnergia = 0;
            System.out.println("La mascota a muerto por exceso de comida.");
        }
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this, "Alimentar", energiaInicio, this.nivelEnergia, fechaHora.format(formato));
        this.historiaAcciones.add(historia);
    }

    //Metodo ejercitar
    public void ejercitar() {
        if(!estaViva()) {
            throw new MascotaMuertaException("La mascota esta muerta, no puede realizar ninguna accion.");
        }

        int energiaInicio = this.nivelEnergia;

        this.nivelEnergia -= 10;

        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this, "Ejercitar", energiaInicio, this.nivelEnergia, fechaHora.format(formato));
        this.historiaAcciones.add(historia);
    }

    //Demostrar habilidad
    public void demostrarHabilidad(String habilidad) {
        if(!estaViva()) {
            throw new MascotaMuertaException("La mascota esta muerta, no puede realizar acciones");
        }

        int energiaInicio = this.nivelEnergia;


        if(habilidades.stream().anyMatch(h -> h.getNombreHabilidad().equalsIgnoreCase(habilidad))) {
            System.out.println("¡Mira como puedo " + habilidad + "!");
        } else {
            throw new IllegalArgumentException("La habilidad " + habilidad + " no existe.");
        }
        LocalDateTime fechaHora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        HistoriaMascota historia = new HistoriaMascota(this,"Demostrar habilidad", energiaInicio, this.nivelEnergia, fechaHora.format(formato));
        this.historiaAcciones.add(historia);
    }

    //Metodo obtener racha fitnes
    public int getRachaFitness() {
        int rachaActual = 0;
        int rachaMaxima = 0;

        for (HistoriaMascota accion : historiaAcciones) {
            if (accion.getTipoHistorico().equals("Ejercitar")) {
                rachaActual++;
                rachaMaxima = Math.max(rachaMaxima, rachaActual);
            } else {
                rachaActual = 0;
            }
        }
        return rachaMaxima;
    }

    //Metodo obtener estado fisico
    public float getEstadoFisico() {
        long cantidadIngestas = historiaAcciones.stream()
                .filter(a -> a.getTipoHistorico().equalsIgnoreCase("Alimentar"))
                .count();
        long cantidadEjercitaciones = historiaAcciones.stream()
                .filter(a -> a.getTipoHistorico().equalsIgnoreCase("Ejercitar"))
                .count();

        if(cantidadIngestas > 0) {
            return (float) cantidadEjercitaciones / cantidadIngestas;
        } else {
            return 0;
        }
    }

    //Forma mas simple
    /*
    * public float getEstadoFisico() {
    * return (float) contadorEjercitaciones / contadorIngestas;
    * }*/

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nivelEnergia=" + nivelEnergia +
                ", habilidades=" + habilidades +
                ", historiaAcciones=" + historiaAcciones +
                '}';
    }
}
