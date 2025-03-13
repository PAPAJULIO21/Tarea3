package com.example.AccessoADatos.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.example.AccessoADatos.clases.Torneo;

import java.util.List;

public class UsuariosDb4o {
    private static final String DB_FILE = "src/main/java/com/example/AccessoADatos/ficheros/usuarios.db4o";

    public void agregarUsuario(Usuario usuario) {
        ObjectContainer db = Db4oEmbedded.openFile(DB_FILE);
        try {
            db.store(usuario);
            System.out.println("Se ha guardado usuario");
        } finally {
            db.close();
        }
    }

    public  List<Usuario> obtenerTodosLosUsuarios() {
        ObjectContainer db = Db4oEmbedded.openFile(DB_FILE);
        try {
            ObjectSet<Usuario> usuarios = db.query(Usuario.class);
            return usuarios;
        } finally {
            db.close();
        }
    }

    public  Usuario buscarUsuariosPorNombre(String nombre) {
        ObjectContainer db = Db4oEmbedded.openFile(DB_FILE);
        try {
            Query query = db.query();
            query.constrain(Torneo.class);
            query.descend("nombre").constrain(nombre);
            ObjectSet<Usuario> resultado = query.execute();
            return resultado.isEmpty() ? null : resultado.next();
        } finally {
            db.close();
        }
    }
    public Usuario buscarUsuarioPorNombreYContraseña(String nombre, String contraseña) {
        ObjectContainer db = Db4oEmbedded.openFile(DB_FILE);
        try {
            Query query = db.query();
            query.constrain(Usuario.class);
            query.descend("nombre").constrain(nombre);
            query.descend("contra").constrain(contraseña);
            ObjectSet<Usuario> resultado = query.execute();
            return resultado.isEmpty() ? null : resultado.next();
        } finally {
            db.close();
        }
    }

    public  void actualizarUsuario(String nombre, Usuario usuarioActualizado) {
        ObjectContainer db = Db4oEmbedded.openFile(DB_FILE);
        try {
            Usuario torneo = buscarUsuariosPorNombre(nombre);
            if (torneo != null) {
                db.delete(torneo);
                db.store(usuarioActualizado);
                System.out.println("Usuario actualizado: " + usuarioActualizado);
            } else {
                System.out.println("Usuario no encontrado");
            }
        } finally {
            db.close();
        }
    }
}
