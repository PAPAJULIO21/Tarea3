package com.example.AccessoADatos.service;

import com.example.AccessoADatos.clases.Torneo;
import com.example.AccessoADatos.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TorneoService {

    @Autowired
    private final TorneoRepository torneoRepository;

    public TorneoService(TorneoRepository torneoRepository) {
		super();
		this.torneoRepository = torneoRepository;
	}

	public Torneo guardarTorneo(Torneo torneo) {
        return torneoRepository.save(torneo);
    }

    public Torneo buscarTorneoPorId(Long id) {
        return torneoRepository.findById(id).orElse(null);
    }


}