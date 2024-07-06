package com.wildproyect.literalura.Services;

public interface IConveritDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
