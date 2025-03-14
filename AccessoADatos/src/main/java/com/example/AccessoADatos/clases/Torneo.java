package com.example.AccessoADatos.clases;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Torneo")
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private char codRegion;
    private int puntosVictoria;

    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Combate> combates;

    @ManyToMany(mappedBy = "torneos", fetch = FetchType.EAGER)
    private Set<Entrenador> entrenadores = new HashSet<>();
    
    private Long ganadorTorneo;


    public Torneo() {}

    public Torneo(String nombre, char codRegion, int puntosVictoria, Set<Combate> combates, Set<Entrenador> entrenadores,Long idENtrenadorGanador) {
        this.nombre = nombre;
        this.codRegion = codRegion;
        this.puntosVictoria = puntosVictoria;
        this.combates = combates;
        this.entrenadores = entrenadores;
        this.ganadorTorneo = idENtrenadorGanador;

    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getCodRegion() {
        return codRegion;
    }

    public void setCodRegion(char codRegion) {
        this.codRegion = codRegion;
    }

    public int getPuntosVictoria() {
        return puntosVictoria;
    }

    public void setPuntosVictoria(int puntosVictoria) {
        this.puntosVictoria = puntosVictoria;
    }

    public Set<Combate> getCombates() {
        return combates;
    }

    public void setCombates(Set<Combate> combates) {
        this.combates = combates;
    }

    public Set<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(Set<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }

    public void agregarEntrenador(Entrenador entrenador) {
        this.entrenadores.add(entrenador);
        entrenador.getTorneos().add(this); // Establece la relación bidireccional
    }

    


    public Long getGanadorTorneo() {
		return ganadorTorneo;
	}

	public void setGanadorTorneo(Long ganadorTorneo) {
		this.ganadorTorneo = ganadorTorneo;
	}

	@Override
    public String toString() {
        return "Torneo{" +

                "nombre='" + nombre + '\'' +
                ", codRegion=" + codRegion +
                ", puntosVictoria=" + puntosVictoria +", gandor="+ganadorTorneo+
                '}';
    }
}