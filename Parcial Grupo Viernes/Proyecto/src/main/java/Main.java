import entities.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import services.MascotasService;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mascotas-pu");
        EntityManager em = emf.createEntityManager();

        MascotasService mascotasService = new MascotasService(em);

        // Cargar datos del archivo csv
        mascotasService.loadMascotasFromFile();
        System.out.println("cantidad de mascotas cargadas: " + mascotasService.getCantMascotas());

        // Mostrar mascotas vivas
        mascotasService.mostrarMascotasVivas();

        // Probando Acciones de las Mascotas
        Mascota mascota1 = mascotasService.getMascotas().get(1);
        System.out.println(mascota1.getNombre());
        mascota1.ejercitar();
        mascota1.ingerirAlimentos();
        mascota1.demostrarHabilidad("saltar");

        // Guardar mascotas vivas en la base de datos
        mascotasService.guardarVivas();

        // Listado de Masctoas con humor mayor a 3
        List<Mascota> mascotasFelices = mascotasService.getFelices();
        System.out.println(mascotasFelices);
    }
}
