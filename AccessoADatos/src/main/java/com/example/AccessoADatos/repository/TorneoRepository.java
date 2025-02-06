package com.example.AccessoADatos.repository;

import com.example.AccessoADatos.clases.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, Long> {

}