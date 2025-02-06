package com.example.AccessoADatos.service;

import com.example.AccessoADatos.clases.Entrenador;
import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.repository.EntrenadorRepository;
import com.example.AccessoADatos.repository.TorneoRepository;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;
    
    @Autowired
    private TorneoRepository torneoRepository;

    @Transactional
    public Entrenador guardarEntrenador(Entrenador entrenador) {
        // Sincroniza las relaciones bidireccionales
        if (entrenador.getTorneos() != null) {
            for (Torneo torneo : entrenador.getTorneos()) {
                torneo.agregarEntrenador(entrenador);
            }
        }

        // Guarda el entrenador
        return entrenadorRepository.save(entrenador);
    }
    
    
    public Entrenador buscarEntrenadorPorId(Long id) {
        return entrenadorRepository.findById(id).orElse(null);
    }
}
