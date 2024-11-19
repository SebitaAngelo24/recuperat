package services;

import entities.Habilidad;
import entities.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repositories.MascotaRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MascotasService {

    // Attributes

    private static final String MASCOTAS_FILE_PATH = "src/main/resources/mascotas.csv";

    private HabilidadesService habilidadesService;

    private MascotaRepository mascotaRepository;

    private List<Mascota> mascotas;

    // Constructors

    public MascotasService(EntityManager em) {
        this.mascotas = new ArrayList<>();
        this.habilidadesService = new HabilidadesService();
        this.mascotaRepository = new MascotaRepository(em);
    }

    // Getters and Setters

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }


    // Methods

    public void loadMascotasFromFile() {
        File file = new File(MASCOTAS_FILE_PATH);

        try {
            Scanner sc = new Scanner(file);

            sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Mascota mascota = lineToMascota(line);
                mascotas.add(mascota);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Mascota lineToMascota(String line) {
        String delim = ",";
        String[] tokens = line.split(delim);

        //nombre,nivel_energia,nivel_humor,habilidades
        String nombre = tokens[0];
        int nivel_energia = Integer.parseInt(tokens[1]);
        int nivel_humor = Integer.parseInt(tokens[2]);

        String habilidadesNames = tokens[3];
        List<Habilidad> habilidades = habilidadesService.getOrCreateHabilidades(habilidadesNames);

        return new Mascota(nombre, nivel_energia, nivel_humor, habilidades);
    }

    public int getCantMascotas() {
        return mascotas.size();
    }

    public void mostrarMascotasVivas() {
        for (Mascota mascota : this.mascotas) {
            if (mascota.estaViva()) {
                System.out.println(mascota);
            }
        }
    }

    public void guardarVivas() throws IOException {

        List<Mascota> mascotasToSave = new ArrayList<>();

        for (Mascota mascota : mascotas) {
            if (mascota.estaViva()) {
                mascotasToSave.add(mascota);
            }
        }

        mascotaRepository.saveAll(mascotasToSave);
        System.out.println("Se insertaron las entidades en la base de datos correctamente.");
    }

    public List<Mascota> getFelices() {
        return mascotaRepository.getFelices();
    }
}
