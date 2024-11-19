package entities;

import jakarta.persistence.*;

@Entity @Table(name = "Habilidades")
public class Habilidad {

    // Attributes

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre_habilidad")
    private String nombre;

    // Constructors

    public Habilidad() {
    }

    public Habilidad(String nombre) {
        this.nombre = nombre;
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


}
