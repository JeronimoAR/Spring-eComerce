package com.curso.ecomerce.service;

import com.curso.ecomerce.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioCRUD {
     public Usuario save(Usuario producto);
     public List<Usuario> findAll();
     public Optional<Usuario> findById(Integer id);
     public Optional<Usuario> findByEmail(String email);
}
