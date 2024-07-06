package com.wildproyect.literalura.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author(){

    }

    public Author(DataAuthor dataAuthor) {
        this.nombre = dataAuthor.nombreAuthor();
        this.fechaNacimiento = dataAuthor.fechaNacimiento();
        this.fechaFallecimiento = dataAuthor.fechaFallecimiento();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        for (Book book : books) {
            book.setAuthor(this);
        }
        this.books = books;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    @Override
    public String toString() {
        return """
                /////////////////DATOS DEL AUTOR//////////////////
                Autor: %s
                Fecha de nacimiento: %s
                Fecha de fallecimiento: %s
                Libros: %s
                ///////////////////////////////////////////////////
                """.formatted(nombre, fechaNacimiento, fechaFallecimiento, books.stream().map(d -> "Titulo: " + d.getTitulo()).toList());
    }
}
