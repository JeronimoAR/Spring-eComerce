package com.curso.ecomerce.service;

import com.curso.ecomerce.entity.Orden;
import com.curso.ecomerce.entity.Usuario;
import com.curso.ecomerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenService implements IOrdenCRUD{

    @Autowired
    private IOrdenRepository ordenRep;

    @Override
    public Orden save(Orden orden) {
        return ordenRep.save(orden);
    }

    @Override
    public Optional<Orden> findById(Integer id) {
        return ordenRep.findById(id);
    }

    @Override
    public List<Orden> findAll() {
        return ordenRep.findAll();
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario){
        return ordenRep.findByUsuario(usuario);
    }
    public String generarNumeroOrden(){
        int numero = 0;
        String numeroConcatenado="";

        List<Orden> ordenes = findAll();
        List<Integer> numeros = new ArrayList<Integer>();

        ordenes.stream().forEach(
                o -> numeros.add(Integer.parseInt(o.getNumero()))
        );

        if(ordenes.isEmpty()){
            numero = 1;
        }else {
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if(numero < 10){
            numeroConcatenado = "0000000000"+String.valueOf(numero);
        }else if(numero < 100){
            numeroConcatenado = "000000000"+String.valueOf(numero);
        }else if(numero < 1000){
            numeroConcatenado = "00000000"+String.valueOf(numero);
        }else if(numero < 10000){
            numeroConcatenado = "0000000"+String.valueOf(numero);
        }else if(numero < 100000){
            numeroConcatenado = "000000"+String.valueOf(numero);
        }else if(numero < 1000000){
            numeroConcatenado = "00000"+String.valueOf(numero);
        }else if(numero < 10000000){
            numeroConcatenado = "0000"+String.valueOf(numero);
        }
        return numeroConcatenado;
    }
}
