package com.curso.ecomerce.controller;

import com.curso.ecomerce.entity.Producto;
import com.curso.ecomerce.entity.Usuario;
import com.curso.ecomerce.service.ProductoService;

import java.io.IOException;
import java.util.Optional;

import com.curso.ecomerce.service.UploadFileService;
import com.curso.ecomerce.service.UsuarioService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    @Autowired
    private ProductoService productoServ;
    @Autowired
    private UsuarioService userServ;
    @Autowired
    private UploadFileService upload;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", productoServ.findAll());
        return "productos/show.html";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create.html";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        logger.info("Este es el objeto de la vista {}", producto);
        Usuario us = userServ.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(us);
        if (producto.getId() == null) {
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        } else {

        }
        productoServ.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoServ.get(id);
        producto = optionalProducto.get();

        logger.info("Producto buscado {}", producto);
        model.addAttribute("producto", producto);

        return "productos/edit.html";
    }

    @PostMapping("update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Producto p = new Producto();
        p = productoServ.get(producto.getId()).get();
        if (file.isEmpty()) {
            producto.setImagen(p.getImagen());
        } else {
            if (!p.getImagen().equals("default.jpg")) {
                upload.deleteImage(p.getImagen());
            }
            String nombreImg = upload.saveImage(file);
            producto.setImagen(nombreImg);
        }
        producto.setUsuario(p.getUsuario());
        productoServ.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Producto p = new Producto();
        p = productoServ.get(id).get();
        if (!p.getImagen().equals("default.jpg")) {
            upload.deleteImage(p.getImagen());
        }
        productoServ.delete(id);
        return "redirect:/productos";
    }


}
