package biblioteca.simple.app;
import biblioteca.simple.contratos.Prestable;
import biblioteca.simple.modelo.*;
import biblioteca.simple.servicios.Catalogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
//crear objeto catalogo
// lista de usuarios

//carga de 4 libros + 4 peliculas
//carga de 4 usuarios

//Menu(1.listar, 2. buscar por titulo, 3.buscar por año.
// 4. prestar ¿que porducto?que esten libres, ¿que usuario?,
// 5. devolver lista de cosas prestadas o que te de error si ya esta prestado, 6.salir
public class Main {

    private static final Catalogo catalogo = new Catalogo();

    private static final List<Usuario> usuarios = new ArrayList<>();

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        cargarDatos();
        menu();
    }

    private static void cargarDatos(){
        catalogo.alta(new Libro("Ozzy Osbourne: El Príncipe de las tinieblas", "2024",Formato.FISICO, "979-8303868120", "John Walsh"  ));
        catalogo.alta(new Libro("Vida", "2010",Formato.FISICO, "9788448024437", "Keith Richards" ));
        catalogo.alta(new Libro("DESAYUNO CON JOHN LENNON: Y OTRAS CRONICAS PARA LA HISTORIA DEL ROCK", "2010",Formato.DIGITAL, "9788475069357", "Robert Hilburn"));
        catalogo.alta(new Libro("FILOSOFIA DE LA CANCION", "2004",Formato.DIGITAL, " 9788433910196", "Bod Dylan"));

        catalogo.alta(new Pelicula("Airbag", "1997", Formato.FISICO, "Juanma Bajo Ulloa", 125));
        catalogo.alta(new Pelicula("Snatch cerdos y diamantes", "2000", Formato.DIGITAL, "Guy Ritchie", 104));
        catalogo.alta(new Pelicula("La vida de Brian", "1979", Formato.FISICO, "Terry Jones", 93));
        catalogo.alta(new Pelicula("Eduardo manos tijeras", "1990", Formato.DIGITAL, "Tim Burton", 98));


        usuarios.add(new Usuario(1,"Pako"));
        usuarios.add(new Usuario(2,"kike"));
        usuarios.add(new Usuario(3,"Gema"));
        usuarios.add(new Usuario(4,"Lidia"));
    }

    private static void menu(){

        int op;


        do{

            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Lista");
            System.out.println("2. Buscar por título");
            System.out.println("3. Buscar por año");
            System.out.println("4. Alquilar");
            System.out.println("5. Devoluciones");
            System.out.println("0. Salir");
            System.out.println("Elige una opción: ");
            while(!sc.hasNextInt()) sc.next();
            op = sc.nextInt();

            sc.nextLine();

            switch (op){
                case 1 ->listar();
                case 2 -> buscarPorTitulo();
                case 3 -> buscarPorAnio();
                case 4 -> prestar();
                case 5 -> devolver();
                case 0 ->System.out.println("Sayonara!");
                default -> System.out.println("Opción no válida");
            }

        } while (op != 0);
    }

    private static void listar(){
        List<Producto> lista = catalogo.listar();

        if (lista.isEmpty()){
            System.out.println("Catalogo vacío");
            return;
        }
        System.out.println("\n--- LISTADO DE PRODUCTOS ---");
        for (Producto p : lista) System.out.println("- " + p);
    }

    private static void buscarPorTitulo(){
        System.out.println("Título(escribe parte del título): ");
        String t = sc.nextLine();
        catalogo.buscar(t).forEach(p  -> System.out.println("- " + p));
    }

    private static void buscarPorAnio(){
        System.out.println("Año: ");
        int a = sc.nextInt();
        sc.nextLine();
        catalogo.buscar(a).forEach(p  -> System.out.println("- " + p));
    }


    private static void listarUsuarios(){
        if(usuarios.isEmpty()){
            System.out.println("No hay usuarios registrados");
            return;
        }
        System.out.println("Lista usuarios");
        usuarios.forEach( u ->
                System.out.println("- Código : " + u.getId() + "| Nombre: " + u.getNombre() )
        );
    }
    // buscar al usuario por id
    private static Usuario getUsuarioPorCodigo(int id){
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
    private static void prestar(){

        // 1) mostrar productos disponibles

        List<Producto> disponibles = catalogo.listar().stream()
                .filter(p -> p instanceof Prestable pN && !pN.estaPrestado())
                .collect(Collectors.toList());

        if (disponibles.isEmpty()){
            System.out.println("No hay productos para prestar");
            return;
        }

        System.out.println("-- PRODUCTOS DISPONIBLES --");
        disponibles.forEach(p -> System.out.println("- ID: " + p.getId() + " | " + p));

        System.out.println("Escribe el id del producto: ");
        int id = sc.nextInt();
        sc.nextLine();

        Producto pEncontrado = disponibles.stream()
                .filter(p ->{
                        try{
                            return p.getId() == id;
                         }catch (NumberFormatException e){
                            return false;
                        }
                })
                .findFirst()
                .orElse(null);

                if (pEncontrado == null){
                    System.out.println("El id no existe");
                    return;
                }

                listarUsuarios();

        System.out.println("Ingresa código de usuario");

        int cUsuario = sc.nextInt();
        sc.nextLine();
        Usuario u1 = getUsuarioPorCodigo(cUsuario);

        if (u1 == null){
            System.out.println("Usuario no encontrado");
        }

        Prestable pPrestable = (Prestable) pEncontrado;
        pPrestable.prestar(u1);

    }

    public static void devolver(){

        List<Producto> pPrestados = catalogo.listar().stream()
                .filter(p -> p instanceof Prestable pN && pN.estaPrestado())
                .collect(Collectors.toList());

        if (pPrestados .isEmpty()){
            System.out.println("No hay productos para prestar");
            return;
        }

        System.out.println("-- PRODUCTOS DISPONIBLES --");
        pPrestados .forEach(p -> System.out.println("- ID: " + p.getId() + " | " + p));


        System.out.println("Escribe el id del producto: ");
        int id = sc.nextInt();
        sc.nextLine();

        Producto pEncontrado = pPrestados.stream()
                .filter(p ->{
                    try{
                        return p.getId() == id;
                    }catch (NumberFormatException e){
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);

        if (pEncontrado == null){
            System.out.println("El id no existe");
            return;
        }

        Prestable pE = (Prestable) pEncontrado;
        pE.devolver();
        System.out.println("Devuleto correctamente");

    }


}