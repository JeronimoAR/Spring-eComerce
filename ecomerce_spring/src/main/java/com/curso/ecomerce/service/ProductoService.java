package com.curso.ecomerce.service;

import com.curso.ecomerce.entity.Producto;
import com.curso.ecomerce.repository.IProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoCRUD {

    @Autowired
    private IProductoRepository productoRep;
    
    @Override
    public Producto save(Producto producto) {
        return productoRep.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return productoRep.findById(id);
    }

    @Override
    public void update(Producto producto) {
        productoRep.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productoRep.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productoRep.findAll();
    }
    
}
