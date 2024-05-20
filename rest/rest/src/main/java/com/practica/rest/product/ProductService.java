package com.practica.rest.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
       return this.productRepository.findAll();
    }


    public ResponseEntity<Object> nuevoProducto(Product product) {
        Optional<Product> res = productRepository.findProductByNombre(product.getNombre());
        HashMap<String, Object> datos = new HashMap<>();
        if (res.isPresent() && product.getId()==null) {
            datos.put("data", true);
            datos.put("message", "Ya existe un producto con ese nombre");
           return new ResponseEntity<>(datos,HttpStatus.CONFLICT);
        }

        datos.put("message", "Producto guardado con éxito");
        if (product.getId()!=null) {
            datos.put("message", "Producto se actualizó con éxito");
        }
        productRepository.save(product);
        datos.put("data", product);

        return new ResponseEntity<>(datos,HttpStatus.CREATED);
    }

    public ResponseEntity<Object> borrarProducto(Long productId) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existe = this.productRepository.existsById(productId);
        if (!existe) {
            datos.put("data", true);
            datos.put("message", "No existe el producto con ese Id");
            return new ResponseEntity<>(datos,HttpStatus.CONFLICT);
        }
        productRepository.deleteById(productId);
        datos.put("message", "Producto eliminado con éxito");
        return new ResponseEntity<>(datos,HttpStatus.ACCEPTED);
    }
}

