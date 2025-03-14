package com.example.AccessoADatos.gestionUsuarios;

import java.util.Scanner;

public class Menu {

    public  void menuGestion(){
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("Escogue una opcion pon un numero");
            System.out.println("--------------------------------");
            System.out.println("1- Listar Entrenadores y Administradores\n" +
                    "2- Eliminar credenciales de entrenadores y administradores\n" +
                    "3- Modificar contraseña de entrenadores y administradores");
            System.out.println("--------------------------------");
            System.out.println("Dime un número");
            System.out.println("--------------------------------");
            int num = sc.nextInt();
            System.out.println("--------------------------------");
            Acciones acciones = new Acciones();
            switch (num){
                case 1:
                    acciones.listarAdminEntre();
                    break;
                case 2:
                    acciones.eliminarUsu();
                    break;
                case 3:
                    acciones.modificarContra();
                    break;
                default:
                    System.out.println("Numero no valido");
                    break;
            }
            break;
        }
    }
}
