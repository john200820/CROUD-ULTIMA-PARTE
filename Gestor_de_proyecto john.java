package com.mycompany.gestor_de_proyecto;

import java.util.Scanner;

public class Gestor_de_proyecto {
    static String[] nombres = new String[5];
    static double[] precios = new double[5];
    static int contador = 0;
    static boolean esAdministrador = false; 

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ CRUD PRODUCTOS ---");
            if (esAdministrador) {
                System.out.println("Perfil actual: Administrador");
                System.out.println("1. Agregar producto (Create)");
                System.out.println("2. Listar productos (Read)");
                System.out.println("3. Buscar producto (Read)");
                System.out.println("4. Actualizar precio (Update)");
                System.out.println("5. Eliminar producto (Delete)");
                System.out.println("6. Cerrar sesión de Administrador");
            } else {
                System.out.println("Perfil actual: Usuario (Limitado)");
                System.out.println("1. Listar productos (Read)");
                System.out.println("2. Buscar producto (Read)");
                System.out.println("3. Iniciar sesión como Administrador");
            }
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    if (esAdministrador) {
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Precio: ");
                        double precio = sc.nextDouble();
                        agregar(nombre, precio);
                    } else {
                        System.out.println("Acceso denegado. Se requieren privilegios de administrador.");
                    }
                }
                case 2 -> listar();
                case 3 -> {
                    System.out.print("Nombre a buscar: ");
                    String nombre = sc.nextLine();
                    int idx = buscar(nombre);
                    System.out.println(idx == -1 ? "No encontrado." : "Encontrado en posición " + idx);
                }
                case 4 -> {
                    if (esAdministrador) {
                        System.out.print("Nombre a actualizar: ");
                        String nombre = sc.nextLine();
                        System.out.print("Nuevo precio: ");
                        double nuevo = sc.nextDouble();
                        actualizarPrecio(nombre, nuevo);
                    } else {
                        System.out.println("Acceso denegado. Se requieren privilegios de administrador.");
                    }
                }
                case 5 -> {
                    if (esAdministrador) {
                        System.out.print("Nombre a eliminar: ");
                        String nombre = sc.nextLine();
                        eliminar(nombre);
                    } else {
                        System.out.println("Acceso denegado. Se requieren privilegios de administrador.");
                    }
                }
                case 6 -> {
                    if (!esAdministrador) {
                        System.out.print("Usuario: ");
                        String user = sc.nextLine();
                        System.out.print("Contraseña: ");
                        String pass = sc.nextLine();

                        if (user.equals("admin") && pass.equals("1234")) {
                            esAdministrador = true;
                            System.out.println("¡Sesión de administrador iniciada con éxito!");
                        } else {
                            System.out.println("Credenciales incorrectas.");
                        }
                    } else {
                        esAdministrador = false;
                        System.out.println("Se ha cerrado la sesión de administrador.");
                    }
                }
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }

    static boolean agregar(String nombre, double precio) {
        if (contador == nombres.length) {
            System.out.println("No hay espacio disponible.");
            return false;
        }
        nombres[contador] = nombre;
        precios[contador] = precio;
        contador++;
        System.out.println("Producto agregado con éxito.");
        return true;
    }

    static void listar() {
        if (contador == 0) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (int i = 0; i < contador; i++) {
            System.out.println(i + ". " + nombres[i] + " - $" + precios[i]);
        }
    }

    static int buscar(String nombre) {
        for (int i = 0; i < contador; i++) {
            if (nombres[i].equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1; 
    }

    static boolean actualizarPrecio(String nombre, double nuevoPrecio) {
        int indice = buscar(nombre);
        if (indice == -1) {
            System.out.println("Producto no encontrado.");
            return false;
        }
        
        precios[indice] = nuevoPrecio;
        System.out.println("Precio actualizado con éxito.");
        return true;
    }

    static boolean eliminar(String nombre) {
        int indice = buscar(nombre);
        if (indice == -1) {
            System.out.println("Producto no encontrado.");
            return false;
        }
        for (int i = indice; i < contador - 1; i++) {
            nombres[i] = nombres[i + 1];
            precios[i] = precios[i + 1];
        }
        nombres[contador - 1] = null;
        precios[contador - 1] = 0.0;
        
        contador--; 
        System.out.println("Producto eliminado con éxito.");
        return true;
    }
}