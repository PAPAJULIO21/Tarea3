package com.example.AccessoADatos.gestionUsuarios;

import com.example.AccessoADatos.db4o.Usuario;
import com.example.AccessoADatos.db4o.UsuariosDb4o;

import java.util.List;
import java.util.Scanner;

public class Acciones {

    public  void listarAdminEntre(){
        UsuariosDb4o usuariosDb4o = new UsuariosDb4o();
        List<Usuario> usuariosAdmin = usuariosDb4o.buscarUsuariosPorPerfil("AT");
        List<Usuario> usuariosEntre = usuariosDb4o.buscarUsuariosPorPerfil("ET");

        System.out.println("Estos son los Administradores de Torneos");
        System.out.println("--------------------------------");
        for (int i = 0;i<usuariosAdmin.size();i++){
            System.out.println("Nombre: "+usuariosAdmin.get(i).getNombre()+" - Contra: "+usuariosAdmin.get(i).getContra());
        }
        System.out.println("--------------------------------");
        System.out.println("Estos son los Entrenadores que hay");
        System.out.println("--------------------------------");
        for (int i = 0;i<usuariosEntre.size();i++){
            System.out.println("Nombre: "+usuariosEntre.get(i).getNombre()+" - Contra: "+usuariosEntre.get(i).getContra());
        }
        System.out.println("--------------------------------");
    }

    public void eliminarUsu(){
        Scanner sc = new Scanner(System.in);
        UsuariosDb4o usuariosDb4o = new UsuariosDb4o();
        System.out.println("Dime el nombre del usuario que quieres borrar");
        System.out.println("--------------------------------");
        String nombreUsu = sc.nextLine();
        System.out.println("--------------------------------");
        System.out.println("Dime la contra del usuario que quieres borrar");
        System.out.println("--------------------------------");
        String contraUsu = sc.nextLine();
        System.out.println("--------------------------------");
        System.out.println("Dime el perfil del usuario que quieres borrar");
        System.out.println("--------------------------------");
        String perfil = sc.nextLine();
        System.out.println("--------------------------------");

        if (perfil.equalsIgnoreCase("AT") || perfil.equalsIgnoreCase("AdminTorneo")){
            perfil = "AT";
            usuariosDb4o.eliminarUsuario(nombreUsu,contraUsu,perfil);
        } else if (perfil.equalsIgnoreCase("ET") || perfil.equalsIgnoreCase("Entrenador")) {
            perfil = "ET";
            usuariosDb4o.eliminarUsuario(nombreUsu,contraUsu,perfil);
        }else {
            System.out.println("Ese perfil no existe");
        }

    }

    public  void modificarContra(){
        Scanner sc = new Scanner(System.in);
        UsuariosDb4o usuariosDb4o = new UsuariosDb4o();
        System.out.println("Dime el nombre del usuario que quieres modificar");
        System.out.println("--------------------------------");
        String nombreUsu = sc.nextLine();
        System.out.println("--------------------------------");
        System.out.println("Dime la contra del usuario que quieres modificar");
        System.out.println("--------------------------------");
        String contraUsu = sc.nextLine();
        System.out.println("--------------------------------");



        Usuario usuario = usuariosDb4o.buscarUsuarioPorNombreYContraseña(nombreUsu,contraUsu);
        if (usuario != null){
            System.out.println("Que contraseña quires nueva para "+usuario.getNombre());
            System.out.println("--------------------------------");
            String contraNueva = sc.nextLine();
            System.out.println("--------------------------------");

            Usuario usuarioNuevo = new Usuario(usuario.getNombre(),contraNueva,usuario.getPerfil(), usuario.getId());
            usuariosDb4o.actualizarUsuario(usuario,usuarioNuevo);

        }else{
            System.out.println("No existe ese Usuario");
        }


    }
}
