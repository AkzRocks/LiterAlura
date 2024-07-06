package com.wildproyect.literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DataAuthor> dataAuthors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Integer cantidadDescargas
) {
}