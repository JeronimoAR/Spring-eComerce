package com.curso.ecomerce.controller;

import com.curso.ecomerce.entity.DetalleOrden;
import com.curso.ecomerce.entity.Orden;
import com.curso.ecomerce.entity.Producto;
import com.curso.ecomerce.entity.Usuario;
import com.curso.ecomerce.service.DetalleOrdenService;
import com.curso.ecomerce.service.OrdenService;
import com.curso.ecomerce.service.ProductoService;
import com.curso.ecomerce.service.UsuarioService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductoService prodServ;
    @Autowired
    private UsuarioService userServ;
    @Autowired
    private OrdenService ordenServ;
    @Autowired
    private DetalleOrdenService detalleServ;
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session){
        log.info("Sesion: {}", session.getAttribute("idusuario"));
        model.addAttribute("productos", prodServ.findAll());
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        return "/user/home.html";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model, HttpSession session){
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        Producto producto = new Producto();
        Optional<Producto> productoOptional = prodServ.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto",producto);

        log.info("id enviado como parametro: {}",id);
        return "/user/productohome.html";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model, HttpSession session){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTot = 0;

        Optional<Producto> optionalprod = prodServ.get(id);
        producto = optionalprod.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        Integer idProd = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProd);

        if(!ingresado){
            detalles.add(detalleOrden);
        }
        sumaTot = detalles.stream().mapToDouble(dt ->dt.getTotal()).sum();

        orden.setTotal(sumaTot);

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "/user/carrito.html";
    }

    @GetMapping("/delete/cart/{id}")
    public String deleteProdCart(@PathVariable Integer id, Model model, HttpSession session){
        List<DetalleOrden> ordenesNew = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden: detalles){
            if(detalleOrden.getProducto().getId() != id){
                ordenesNew.add(detalleOrden);
            }
        }

        detalles = ordenesNew;
        double sumaTot = 0;
        sumaTot = detalles.stream().mapToDouble(dt ->dt.getTotal()).sum();

        orden.setTotal(sumaTot);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "user/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "user/carrito";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session){
        Usuario user = userServ.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("user", user);
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        return "user/resumenorden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenServ.generarNumeroOrden());

        Usuario user = userServ.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        orden.setUsuario(user);
        ordenServ.save(orden);

        for (DetalleOrden dt: detalles){
            dt.setOrden(orden);
            detalleServ.save(dt);
        }
        orden.setDetalle(detalles);
        ordenServ.save(orden);
        orden = new Orden();
        detalles.clear();
        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(@RequestParam String nombre, Model model, HttpSession session){
        String car = String.valueOf(nombre.charAt(0));
        car = car.toUpperCase();
        nombre = nombre.replace(String.valueOf(nombre.charAt(0)),car);
        log.info("nombre: {}", nombre);
        String name = nombre;
        List<Producto> productos = prodServ.findAll().stream().filter(p -> p.getNombre().contains(name)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "user/home";
    }
}
