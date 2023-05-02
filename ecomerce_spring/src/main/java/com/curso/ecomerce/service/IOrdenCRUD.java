package com.curso.ecomerce.service;

import com.curso.ecomerce.entity.Orden;
import com.curso.ecomerce.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenCRUD {
    public Orden save(Orden orden);
    public Optional<Orden> findById(Integer id);
    public List<Orden> findAll();
    public String generarNumeroOrden();
    public List<Orden> findByUsuario(Usuario usuario);
}
