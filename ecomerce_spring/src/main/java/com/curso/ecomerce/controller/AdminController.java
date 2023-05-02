package com.curso.ecomerce.controller;

import com.curso.ecomerce.entity.Producto;
import com.curso.ecomerce.service.OrdenService;
import com.curso.ecomerce.service.ProductoService;
import com.curso.ecomerce.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UsuarioService userServ;
    @Autowired
    private ProductoService prodServ;
    @Autowired
    private OrdenService ordenServ;

    @GetMapping("")
    public String home(Model model, HttpSession session) {
        log.info("Session: {}", session.getAttribute("idusuario"));
        List<Producto> productos = prodServ.findAll();
        model.addAttribute("productos",productos);
        return "admin/home";
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("usuarios", userServ.findAll());
        return "/admin/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model){
        model.addAttribute("ordenes", ordenServ.findAll());
        return "/admin/ordenes";
    }
}
