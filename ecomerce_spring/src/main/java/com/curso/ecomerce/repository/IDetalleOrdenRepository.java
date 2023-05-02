package com.curso.ecomerce.repository;

import com.curso.ecomerce.entity.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {
}
