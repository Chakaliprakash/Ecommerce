package com.prakash.Ecomerce.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prakash.Ecomerce.Model.ProductModel;
import com.prakash.Ecomerce.Service.ProductService;

// import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @RequestMapping("/Login")
    public String hello() {
        return "Hello, World!";
    }

    @RequestMapping("/products")
    public List<ProductModel> getAll() {
        return service.getall();
    }
    @RequestMapping(value="/hello", method=RequestMethod.GET)
    public String requestMethodName(@RequestParam String param) {
        return new String("Hello"+param);
    }
    @RequestMapping("/product/{id}")
    public ResponseEntity<ProductModel> getByid(@PathVariable int id) {
        ProductModel product=service.getByid(id);
        
        if(product==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart ProductModel product, @RequestPart MultipartFile imageFile) {
        try {
            ProductModel product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id) {
        ProductModel product = service.getByid(id);
        if (product == null || product.getImageData() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(product.getImageType()))
                .body(product.getImageData());
    } 
    @PutMapping("/product/{id}") 
    public ResponseEntity<?> updateProduct(@PathVariable int id,@RequestPart MultipartFile imageFile,@RequestPart ProductModel product) throws IOException {
        ProductModel product1=service.updateProduct(id, imageFile, product);
        if (product1 == null || product1.getImageData() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(product1.getImageType()))
                    .body(product.getImageData());
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        ProductModel existing = service.getByid(id);
        if (existing == null) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        service.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductModel>> searchByKeyword (@RequestParam String keyword) {
        System.out.println("Searched with "+keyword);
        List<ProductModel> list=service.searchByKeyword(keyword);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    
}
