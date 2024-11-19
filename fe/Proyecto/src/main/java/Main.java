import entities.Mascota;
import repository.MascotaDAO;
import services.MascotasService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String rutaArchivo = "C:\\Mis Archivos\\Ingenieria en sistemas\\Backend\\Parcial\\parcial\\3K7_PreEnunciado_Recuperatorio\\Proyecto\\src\\main\\resources/mascotas.csv";

        try {
            //Cargar mascotas desde el archivo CSV
            List<Mascota> mascotas = MascotasService.cargaDesdeCSV(rutaArchivo);

            //Guardar las mascotas en la base de datos
            MascotaDAO mascotaDAO = new MascotaDAO();
            mascotaDAO.guardarMascotas(mascotas);
            System.out.println("Todas las mascotas se guardaron con exito.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ocurrio un error al guardarlas.");
        }
    }
}
