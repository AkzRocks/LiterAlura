package com.wildproyect.literalura.Repository;

import com.wildproyect.literalura.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByNombreIgnoreCase(String nombre);
    List<Author> findAuthorByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(int fechaNacimiento, int fechaFallecimiento);
}