package com.example.AccessoADatos.repository;

import com.example.AccessoADatos.clases.Combate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CombateRepository extends JpaRepository<Combate, Long> {



}
