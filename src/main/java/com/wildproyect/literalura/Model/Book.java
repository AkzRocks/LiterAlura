package com.wildproyect.literalura.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String titulo;
    private String nombreAuthor;
    private String language;
    private Integer cantidadDescargas;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Book() {

    }

    public Book(DataBook dataBook, Author author) {
        this.author = author;
        this.cantidadDescargas = dataBook.cantidadDescargas();
        this.titulo = dataBook.titulo();
        this.language = dataBook.languages().get(0);
        this.nombreAuthor = author.getNombre();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreAuthor() {
        return nombreAuthor;
    }

    public void setNombreAuthor(String nombreAuthor) {
        this.nombreAuthor = nombreAuthor;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(int cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String toString() {
        return """
                //////////////////////////////////////////////
                Titulo: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d
                //////////////////////////////////////////////
                """.formatted(titulo, nombreAuthor, language, cantidadDescargas);
    }
}
