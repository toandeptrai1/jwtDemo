package com.example.demoJwt.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    @RolesAllowed({"ROLE_EDITOR","ROLE_CUSTOMER"})
    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    @PostMapping
    @RolesAllowed("ROLE_EDITOR")
    public ResponseEntity<Product> create(@RequestBody @Valid Product product){
        Product savedProduct=productRepo.save(product);
        URI productUri=URI.create("/products/"+savedProduct.getId());
        return ResponseEntity.created(productUri).body(savedProduct);
    }


}
