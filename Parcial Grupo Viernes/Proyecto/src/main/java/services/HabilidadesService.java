package services;

import entities.Habilidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabilidadesService {
    // Attributes

        Map<String, Habilidad> habilidades;

    // Constructors

    public HabilidadesService() {
        habilidades = new HashMap<>();
    }

    // Methods

    public List<Habilidad> getOrCreateHabilidades(String names) {

        List<Habilidad> habilidadesToReturn = new ArrayList<>();
        String[] habilidadesNames = names.split(";");

        for (String habilidadName : habilidadesNames) {
            if (habilidades.containsKey(habilidadName)) {
                habilidadesToReturn.add(habilidades.get(habilidadName));
            } else {
                Habilidad habilidad = new Habilidad(habilidadName);
                habilidades.put(habilidadName, habilidad);
                habilidadesToReturn.add(habilidad);
            }
        }

        return habilidadesToReturn;
    }
}
