package entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Historia_Mascota")
public class HistoriaMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    @Column(name = "tipo_historico", nullable = false)
    private String tipoHistorico;

    @Column(name = "energia_inicio")
    private int energiaInicio;

    @Column(name = "energia_fin")
    private int energiaFin;

    @Column(name = "fecha_hora", nullable = false)
    private String fechaHora;

    public HistoriaMascota() {
    }

    public HistoriaMascota(Mascota mascota, String tipoHistorico, int energiaInicio, int energiaFin, String fechaHora) {
        this.mascota = mascota;
        this.tipoHistorico = tipoHistorico;
        this.energiaInicio = energiaInicio;
        this.energiaFin = energiaFin;
        this.fechaHora = fechaHora;
    }

    //getters y setters

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getTipoHistorico() {
        return tipoHistorico;
    }

    public void setTipoHistorico(String tipoHistorico) {
        this.tipoHistorico = tipoHistorico;
    }

    public int getEnergiaInicio() {
        return energiaInicio;
    }

    public void setEnergiaInicio(int energiaInicio) {
        this.energiaInicio = energiaInicio;
    }

    public int getEnergiaFin() {
        return energiaFin;
    }

    public void setEnergiaFin(int energiaFin) {
        this.energiaFin = energiaFin;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HistoriaMascota{" +
                "id=" + id +
                ", mascota=" + mascota +
                ", tipoHistorico='" + tipoHistorico + '\'' +
                ", energiaInicio=" + energiaInicio +
                ", energiaFin=" + energiaFin +
                ", fechaHora='" + fechaHora + '\'' +
                '}';
    }
}
