package com.curso.ecomerce.repository;

import com.curso.ecomerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer>{
    
}
