package com.example.AccessoADatos.service;

import com.example.AccessoADatos.clases.Combate;
import com.example.AccessoADatos.repository.CombateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CombateService {

    @Autowired
    private CombateRepository combateRepository;

    public Combate guardarCombate(Combate combate) {
        return combateRepository.save(combate);
    }

    public Combate buscarCombatePorId(Long id) {
        return combateRepository.findById(id).orElse(null);
    }


}
