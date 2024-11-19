package repositories;

import entities.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class MascotaRepository {

    // Attributes

    private EntityManager em;

    // Constructors

    public MascotaRepository(EntityManager em) {
        this.em = em;
    }

    // Methods

    public void saveAll(List<Mascota> mascotasList) {
        mascotasList.forEach(this::save);
    }

    public void save(Mascota mascota) {
        begin();
        em.persist(mascota);
        commit();
    }

    private void begin() {
        em.getTransaction().begin();
    }

    private void commit() {
        em.getTransaction().commit();
    }

    public List<Mascota> getFelices() {
        String consulta = "SELECT m FROM Mascota m WHERE m.nivel_humor > :nivelHumor";
        return em.createQuery(consulta, Mascota.class).setParameter("nivelHumor", 3).getResultList();
    }
}
