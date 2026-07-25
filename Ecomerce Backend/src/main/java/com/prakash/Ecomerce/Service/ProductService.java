package com.prakash.Ecomerce.Service;

import java.io.IOException;
import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prakash.Ecomerce.Model.ProductModel;
import com.prakash.Ecomerce.Repository.ProductRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo repo;

    public List<ProductModel> getall () {
        return repo.findAll();
    }

    public ProductModel getByid(int id) {
        return repo.findById(id).orElse(null);
    }

    public ProductModel addProduct(ProductModel product,MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public ProductModel updateProduct(int id,MultipartFile imageFile,ProductModel product) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<ProductModel> searchByKeyword(String keyword) {
    return repo.searchByKeyword(keyword);
}
}
