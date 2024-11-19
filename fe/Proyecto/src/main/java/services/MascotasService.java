package services;

import entities.Habilidad;
import entities.Mascota;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MascotasService {

    //Carga de mascotas desde el CSV
    public static List<Mascota> cargaDesdeCSV(String rutaArchivo) {
        List<Mascota> mascotas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            br.readLine(); //Omitir la cabecera
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                //Extraer y asignar valores
                String nombre = datos[0];
                int nivelEnergia = Integer.parseInt(datos[1]);

                //Crear la lista de habilidades para la mascota
                Set<Habilidad> habilidades = new HashSet<>();
                String[] habilidadesSet = datos[3].split(";");
                for (String nombreHabilidad : habilidadesSet) {
                    habilidades.add(new Habilidad(nombreHabilidad.trim()));
                }
                //Crear y agregar la mascota
                mascotas.add(new Mascota(nombre, nivelEnergia, habilidades));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mascotas;
    }
}
