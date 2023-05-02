package com.curso.ecomerce.repository;

import com.curso.ecomerce.entity.Orden;
import com.curso.ecomerce.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {
    public List<Orden> findByUsuario (Usuario usuario);
}
