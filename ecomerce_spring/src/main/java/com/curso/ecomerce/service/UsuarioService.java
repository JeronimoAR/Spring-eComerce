package com.curso.ecomerce.service;

import com.curso.ecomerce.entity.Usuario;
import com.curso.ecomerce.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioCRUD {

    @Autowired
    private IUsuarioRepository userRep;

    @Override
    public Usuario save(Usuario usuario) {
        return userRep.save(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return userRep.findAll();
    }
    @Override
    public Optional<Usuario> findById(Integer id){
        return userRep.findById(id);
    }

    @Override
    public  Optional<Usuario> findByEmail(String email){
        return userRep.findByEmail(email);
    }
}
