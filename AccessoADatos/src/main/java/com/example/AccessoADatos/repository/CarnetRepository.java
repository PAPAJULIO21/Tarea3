package com.example.AccessoADatos.repository;

import com.example.AccessoADatos.clases.Carnet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {
}