package com.wildproyect.literalura.Repository;

import com.wildproyect.literalura.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitulo(String nombre);

    List<Book> findBookByLanguageContaining(String language);
}
