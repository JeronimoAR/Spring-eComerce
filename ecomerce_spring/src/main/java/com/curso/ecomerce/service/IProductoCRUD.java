package com.curso.ecomerce.service;

import com.curso.ecomerce.entity.Producto;
import java.util.List;
import java.util.Optional;


public interface IProductoCRUD {
    public Producto save(Producto producto);
    public Optional<Producto>get(Integer id);
    public void update(Producto producto);
    public void delete(Integer id);
    public List<Producto> findAll();
}
