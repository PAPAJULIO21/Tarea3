package com.example.AccessoADatos.clases;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "Carnet")
public class Carnet {
    @Id
    private Long idEntrenador;

    @Temporal(TemporalType.DATE)
    private LocalDate fechaExpedicion;

    private int puntos;
    private int numVictorias;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idEntrenador")
    private Entrenador entrenador;

    public Carnet() {}

    public Carnet(Long idEntrenador, LocalDate fechaExpedicion, int puntos, int numVictorias, Entrenador entrenador) {
        this.idEntrenador = idEntrenador;
        this.fechaExpedicion = fechaExpedicion;
        this.puntos = puntos;
        this.numVictorias = numVictorias;
        this.entrenador = entrenador;
    }

	public Long getIdEntrenador() {
		return idEntrenador;
	}

	public void setIdEntrenador(Long idEntrenador) {
		this.idEntrenador = idEntrenador;
	}

	public LocalDate getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(LocalDate fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getNumVictorias() {
		return numVictorias;
	}

	public void setNumVictorias(int numVictorias) {
		this.numVictorias = numVictorias;
	}

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}
    
    
}