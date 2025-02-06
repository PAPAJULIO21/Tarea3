package com.example.AccessoADatos.service;

import com.example.AccessoADatos.clases.Carnet;
import com.example.AccessoADatos.repository.CarnetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarnetService {

    @Autowired
    private CarnetRepository carnetRepository;

    public Carnet guardarCarnet(Carnet carnet) {
        return carnetRepository.save(carnet);
    }

    public Carnet buscarCarnetPorId(Long id) {
        return carnetRepository.findById(id).orElse(null);
    }
}
