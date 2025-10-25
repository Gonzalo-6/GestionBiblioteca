package biblioteca.simple.app;
import biblioteca.simple.contratos.Prestable;
import biblioteca.simple.modelo.*;
import biblioteca.simple.servicios.Catalogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//crear objeto catalogo
// lista de usuarios

//carga de 4 libros + 4 peliculas
//carga de 4 usuarios

//Menu(1.listar, 2. buscar por titulo, 3.buscar por año.
// 4. prestar ¿que porducto?que esten libres, ¿que usuario?,
// 5. devolver lista de cosas prestadas o que te de error si ya esta prestado, 6.salir
public class Main {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        Catalogo catalogo = new Catalogo();
        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(new Usuario("Paco"));
        usuarios.add(new Usuario("Kike"));
        usuarios.add(new Usuario("Lidia"));
        usuarios.add(new Usuario("Gema"));

        catalogo.alta(new Libro("Ozzy Osbourne: El Príncipe de las tinieblas", "2024",Formato.FISICO, "979-8303868120", "John Walsh" ));
        catalogo.alta(new Libro("Vida", "2010",Formato.FISICO, "9788448024437", "Keith Richards" ));
        catalogo.alta(new Libro("DESAYUNO CON JOHN LENNON: Y OTRAS CRONICAS PARA LA HISTORIA DEL ROCK", "2010",Formato.DIGITAL, "9788475069357", "Robert Hilburn" ));
        catalogo.alta(new Libro("FILOSOFIA DE LA CANCION", "2004",Formato.DIGITAL, " 9788433910196", "Bod Dylan" ));

        catalogo.alta(new Pelicula("Airbag", "1997", Formato.FISICO, "Juanma Bajo Ulloa", 125));
        catalogo.alta(new Pelicula("Snatch cerdos y diamantes", "2000", Formato.DIGITAL, "Guy Ritchie", 104));
        catalogo.alta(new Pelicula("La vida de Brian", "1979", Formato.FISICO, "Terry Jones", 93));
        catalogo.alta(new Pelicula("Eduardo manos tijeras", "1990", Formato.DIGITAL, "Tim Burton", 98));

        int opcion;
        do{
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Lista");
            System.out.println("2. Buscar por título");
            System.out.println("3. Buscar por año");
            System.out.println("4. Alquilar");
            System.out.println("5. Devoluciones");
            System.out.println("6. Salir");
            System.out.println("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpia el salto de línea pendiente

            switch (opcion){
                case 1 -> {
                    System.out.println("\n--- LISTADO DE PRODUCTOS ---");
                    for (Producto p : catalogo.listar()){
                        System.out.println(p);
                    }
                }
                case 2 -> {
                    System.out.print("Introduce el título a buscar: ");
                    String titulo = sc.nextLine();

                    System.out.print("¿Qué tipo de producto quieres buscar? (LIBRO o PELICULA): ");
                    String tipo = sc.nextLine().toUpperCase();

                    System.out.print("Introduce el formato (FISICO o DIGITAL): ");
                    String formatoStr = sc.nextLine().toUpperCase();

                    try {
                        Formato formato = Formato.valueOf(formatoStr);
                        List<Producto> resultados = catalogo.buscar(titulo);
                        boolean encontrado = false;

                        for (Producto p : resultados) {
                            if (p.getClass().getSimpleName().equalsIgnoreCase(tipo)
                                    && p.getFormato() == formato) {
                                System.out.println(p);
                                encontrado = true;
                            }
                        }

                        if (!encontrado)
                            System.out.println("No se encontraron productos con esos criterios.");

                    } catch (IllegalArgumentException e) {
                        System.out.println("Formato no válido. Usa FISICO o DIGITAL.");
                    }
                }

                case 3 -> {
                    System.out.print("Introduce el año: ");
                    int anho = sc.nextInt();
                    sc.nextLine();

                    System.out.print("¿Qué tipo de producto quieres buscar? (LIBRO o PELICULA): ");
                    String tipo = sc.nextLine().toUpperCase();

                    System.out.print("Introduce el formato (FISICO o DIGITAL): ");
                    String formatoStr = sc.nextLine().toUpperCase();

                    try {
                        Formato formato = Formato.valueOf(formatoStr);
                        List<Producto> resultados = catalogo.buscar(anho);
                        boolean encontrado = false;

                        for (Producto p : resultados) {
                            if (p.getClass().getSimpleName().equalsIgnoreCase(tipo)
                                    && p.getFormato() == formato) {
                                System.out.println(p);
                                encontrado = true;
                            }
                        }

                        if (!encontrado)
                            System.out.println("No se encontraron productos con esos criterios.");

                    } catch (IllegalArgumentException e) {
                        System.out.println("Formato no válido. Usa FISICO o DIGITAL.");
                    }
                }

                case 4 -> {
                    System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
                    List<Producto> disponibles = new ArrayList<>();
                    for (Producto p : catalogo.listar()) {
                        if (p instanceof Prestable prestable && !prestable.estaPrestado()) {
                            disponibles.add(p);
                            System.out.println(p.getTitulo());
                        }
                    }

                    if (disponibles.isEmpty()) {
                        System.out.println("No hay productos disponibles para prestar.");
                        break;
                    }

                    System.out.print("Introduce el título del producto a prestar: ");
                    String titulo = sc.nextLine();

                    Producto producto = null;
                    for (Producto p : disponibles) {
                        if (p.getTitulo().equalsIgnoreCase(titulo)) {
                            producto = p;
                            break;
                        }
                    }

                    if (producto == null) {
                        System.out.println("Producto no encontrado o ya prestado.");
                        break;
                    }

                    System.out.println("Usuarios:");
                    for (int i = 0; i < usuarios.size(); i++) {
                        System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
                    }
                    System.out.print("Selecciona número de usuario: ");
                    int numUsuario = sc.nextInt();
                    sc.nextLine();

                    if (numUsuario < 1 || numUsuario > usuarios.size()) {
                        System.out.println("Usuario no válido.");
                    } else {
                        Usuario u = usuarios.get(numUsuario - 1);
                        try {
                            ((Prestable) producto).prestar(u);
                            System.out.println("Producto prestado a " + u.getNombre());
                        } catch (IllegalStateException e) {
                            System.out.println("ERROR: " + e.getMessage());
                        }
                    }
                }
                case 5 -> {
                    System.out.println("\n--- PRODUCTOS PRESTADOS ---");
                    List<Producto> prestados = new ArrayList<>();
                    for (Producto p : catalogo.listar()) {
                        if (p instanceof Prestable prestable && prestable.estaPrestado()) {
                            prestados.add(p);
                            System.out.println(p.getTitulo());
                        }
                    }

                    if (prestados.isEmpty()) {
                        System.out.println("No hay productos prestados.");
                        break;
                    }

                    System.out.print("Introduce el título del producto a devolver: ");
                    String titulo = sc.nextLine();

                    boolean devuelto = false;
                    for (Producto p : prestados) {
                        if (p.getTitulo().equalsIgnoreCase(titulo)) {
                            ((Prestable) p).devolver();
                            System.out.println("Producto devuelto correctamente.");
                            devuelto = true;
                            break;
                        }
                    }

                    if (!devuelto)
                        System.out.println("ERROR: producto no encontrado o ya devuelto.");
                }
                case 6 -> System.out.println("Gracias por su visita");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 6);

        sc.close();
    }

}
