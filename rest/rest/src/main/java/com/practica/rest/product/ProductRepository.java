package com.practica.rest.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //@Query("SELECT + FROM Product p WHERE p.nombre=**")
    Optional<Product> findProductByNombre(String name);
}
