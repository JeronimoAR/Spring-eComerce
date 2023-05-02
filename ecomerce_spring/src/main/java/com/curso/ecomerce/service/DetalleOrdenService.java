package com.curso.ecomerce.service;

import com.curso.ecomerce.entity.DetalleOrden;
import com.curso.ecomerce.repository.IDetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenService implements IDetalleOrdenCRUD{
    @Autowired
    private IDetalleOrdenRepository detalleRep;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleRep.save(detalleOrden);
    }
}
