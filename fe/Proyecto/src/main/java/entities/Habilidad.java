package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Habilidades")
public class Habilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre_habilidad")
    private String nombreHabilidad;

    @ManyToMany(mappedBy = "habilidades")
    private Set<Mascota> mascotas = new HashSet<>();

    public Habilidad() {
    }

    public Habilidad(String nombreHabilidad) {
        this.nombreHabilidad = nombreHabilidad;
    }

    //Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreHabilidad() {
        return nombreHabilidad;
    }

    public void setNombreHabilidad(String nombreHabilidad) {
        this.nombreHabilidad = nombreHabilidad;
    }

    public Set<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(Set<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    @Override
    public String toString() {
        return "Habilidad{" +
                "id=" + id +
                ", nombreHabilidad='" + nombreHabilidad + '\'' +
                '}';
    }
}
