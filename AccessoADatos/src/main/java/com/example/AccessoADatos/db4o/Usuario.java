package com.example.AccessoADatos.db4o;

public class Usuario {

    private String nombre;
    private String contra;
    private String perfil;
    private  Long id;


    public Usuario(String nombre,String contra,String perfil,Long id){
        this.nombre = nombre;
        this.contra = contra;
        this.perfil = perfil;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
