package com.curso.ecomerce.controller;

import com.curso.ecomerce.entity.Orden;
import com.curso.ecomerce.entity.Usuario;
import com.curso.ecomerce.service.OrdenService;
import com.curso.ecomerce.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UsuarioController {

    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private OrdenService ordenServ;
    @Autowired
    private UsuarioService userServ;

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();


    @GetMapping("/register")
    public String register() {
        return "user/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario) {
        log.info("Usuario registro: {}", usuario);
        usuario.setTipo("USER");
        usuario.setPassword(passEncode.encode(usuario.getPassword()));
        userServ.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "user/login";
    }

    @GetMapping("/acces")
    public String acces(HttpSession session) {
        Integer id = (Integer) session.getAttribute("idusuario");
        log.info("Acces: {}", id);

        Optional<Usuario> users = userServ.findById(id);
        log.info("User: {}", users);

        if (users.isPresent()) {
            log.info("pasa el if");
            //session.setAttribute("idusuario", users.get().getId());
            if (users.get().getTipo().equals("ADMIN")) {
                log.info("Detecta admin");
                return "redirect:/admin";
            } else {
                return "redirect:/";
            }
        } else {
            log.info("Usuario no encotrado");
        }
        return "redirect:/";
    }

    @GetMapping("/compras")
    public String compras(Model model, HttpSession session) {
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        Usuario user = userServ.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        List<Orden> ordenes = ordenServ.findByUsuario(user);
        model.addAttribute("ordenes", ordenes);
        return "user/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model, HttpSession session) {
        log.info("id: {}", id);
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        Optional<Orden> orden = ordenServ.findById(id);
        log.info("orden: {}", orden);
        model.addAttribute("detalles", orden.get().getDetalle());
        return "user/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrar(HttpSession session) {
        session.removeAttribute("idusuario");
        return "redirect:/";
    }
}
