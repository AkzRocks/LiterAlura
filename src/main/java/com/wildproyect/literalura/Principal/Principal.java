package com.wildproyect.literalura.Principal;

import com.wildproyect.literalura.Model.*;
import com.wildproyect.literalura.Services.ConvierteDatos;
import com.wildproyect.literalura.Repository.AuthorRepository;
import com.wildproyect.literalura.Repository.BookRepository;
import com.wildproyect.literalura.Services.ConsumoApi;

import java.util.DoubleSummaryStatistics;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.*;

public class Principal {
    private Integer opcion = -1;
    private Boolean iniciar = true;
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final Scanner teclado = new Scanner(System.in);
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final String URL_ROOT = "https://gutendex.com/books/?search=";

    public Principal(AuthorRepository autorRepository, BookRepository bookRepository){
        this.authorRepository = autorRepository;
        this.bookRepository = bookRepository;
    }

    public void muestraMenu(){
        String menu = """
                --------------------------------------------
                Elija la opción a través de un número:
                1 - Búsqueda de libro por titulo.
                2 - Listar todos los libros registrados.
                3 - Listar todos los autores registrados.
                4 - Listar autores vivos en un determinado año.
                5 - Listar libros por idioma.
                6 - Obtener estadísticas.
                0 - Salir.
                --------------------------------------------
                """;

        while (iniciar){
            try{
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion){
                    case 1:
                        agregarLibro();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 6:
                        obtenerEstadisticas();
                        break;
                    case 0:
                        iniciar = false;
                        System.out.println("Saliendo de la aplicación...");
                        break;
                    default:
                        System.out.println("Opcion inválida."); 
                }
            }catch (InputMismatchException e){
                teclado.nextLine();
                System.out.println("Ingrese una opcion de numero valida "+ e.getMessage());
            }
        }
    }
    private DataResult busquedaLibro(){
        System.out.println("Ingresa el libro de que deseas buscar");
        String nombreLibro = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL_ROOT + nombreLibro.replace(" ", "+"));
        var resultado = conversor.obtenerDatos(json, DataResult.class);
        return resultado;
    }

    private void agregarLibro() {
        DataResult nuevabusqueda = busquedaLibro();

        if (nuevabusqueda.dataBooks().size() > 0) {
            DataBook dataBook = nuevabusqueda.dataBooks().get(0);
            DataAuthor datosAuthor = dataBook.dataAuthors().get(0);
            var tituloDeLibro = bookRepository.findBookByTitulo(dataBook.titulo());
            if (tituloDeLibro != null) {
                System.out.println("No se puede registrar el mismo libro más de una vez.");
            } else {
                var authorBook = authorRepository.findAuthorByNombreIgnoreCase(datosAuthor.nombreAuthor());
                Book book;
                if (authorBook != null) {
                    book = new Book(dataBook, authorBook);
                } else {
                    Author autor = new Author(datosAuthor);
                    authorRepository.save(autor);
                    book = new Book(dataBook, autor);
                }
                bookRepository.save(book);
                System.out.println("--------- SAVED ---------");
                System.out.println(book);
            }
        } else {
            System.out.println("El libro no existe, intentelo de nuevo.");
        }
    }

    private void listarLibrosRegistrados() {
        List<Book> libros = bookRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            authors.forEach(System.out::println);
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar.");
        var fechaAuthor = teclado.nextInt();
        teclado.nextLine();
        if (fechaAuthor < 0) {
            System.out.println("Has ingresado un año negativo, intenta de nuevo.");
        } else {
            List<Author> fechaAuthors = authorRepository
                    .findAuthorByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(fechaAuthor,
                            fechaAuthor);
            if (fechaAuthors.isEmpty()) {
                System.out.println("No hay autores registrados en ese año.");
            } else {
                fechaAuthors.forEach(System.out::println);
            }
        }
    }

    private void listarLibrosPorIdioma() {
        String menu = """
                Ingrese el idioma para buscar los libros:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """;
        System.out.println(menu);
        String language = teclado.nextLine();
        if (!language.equals("es") && !language.equals("en") && !language.equals("fr") && !language.equals("pt")) {
            System.out.println("Has ingresado un idioma incorrecto, intentalo de nuevo.");
        } else {
            List<Book> booksXLanguage = bookRepository.findBookByLanguageContaining(language);
            if (booksXLanguage.isEmpty()) {
                System.out.println("No hay libros registrados en ese idioma.");
            } else {
                int cantidadLibros = booksXLanguage.size();
                System.out.println(
                        "Total libros registrados en %s: ".formatted(Language.fromString(language)) + cantidadLibros);
                booksXLanguage.forEach(System.out::println);
            }
        }
    }

    private void obtenerEstadisticas() {
        System.out.println("¿De donde quiere obtener las estadísticas?");
        String menu = """
                1 - Gutendex
                2 - Base de datos
                """;
        System.out.println(menu);
        var option = teclado.nextInt();
        teclado.nextLine();
        if (option == 1) {
            System.out.println("----- ESTADÍSTICAS DE DESCARGAS EN GUTENDEX -----");
            String json = consumoApi.obtenerDatos(URL_ROOT);
            DataResult dataResults = conversor.obtenerDatos(json, DataResult.class);
            DoubleSummaryStatistics statistics = dataResults.dataBooks()
                    .stream()
                    .collect(summarizingDouble(DataBook::cantidadDescargas));
            System.out.println("Libro con más descargas: " + statistics.getMax());
            System.out.println("Libro con menos descargas: " + statistics.getMin());
            System.out.println("Promedio de descargas: " + statistics.getAverage());
        } else if (option == 2) {
            System.out.println("----- ESTADÍSTICAS DE DESCARGAS EN BASE DE DATOS -----");
            List<Book> libros = bookRepository.findAll();
            if (libros.isEmpty()) {
                System.out.println("No hay libros registrados en la base de datos.");
            } else {
                DoubleSummaryStatistics estadisticas = libros
                        .stream()
                        .collect(summarizingDouble(Book::getCantidadDescargas));
                System.out.println("Libro con más descargas: " + estadisticas.getMax());
                System.out.println("Libro con menos descargas: " + estadisticas.getMin());
                System.out.println("Promedio de descargas: " + estadisticas.getAverage());
            }
        } else {
            System.out.println("Opción no válida, intentelo de nuevo.");
        }
    }
}
