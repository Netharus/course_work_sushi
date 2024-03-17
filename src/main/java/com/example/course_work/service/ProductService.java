package com.example.course_work.service;

import com.example.course_work.entity.Category;
import com.example.course_work.entity.Product;
import com.example.course_work.exceptions.ProductNotFoundException;
import com.example.course_work.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public static final int PRODUCTS_PER_PAGE =10;

    public List<Product> findByCategory(Category category){
        return productRepository.findByCategory(category);
    }

    public boolean isProductUnique(Long id, String name) {
        Product product=productRepository.findByName(name);
        if(product==null){
            return true;
        }
        boolean isCreatingNew=(id==null);
        if(isCreatingNew){
            if(product!=null) return false;
        }else {
            if(product.getId()!=id){
                return false;
            }
        }
        return true;
    }

    public Product save(Product product){
           return productRepository.save(product);
    }

    public Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword){
        Sort sort= Sort.by(sortField);
        sort=sortDir.equals("asc")? sort.ascending():sort.descending();

        Pageable pageable= PageRequest.of(pageNum-1, PRODUCTS_PER_PAGE,sort);

        if(keyword !=null){
            return productRepository.findAll(keyword,pageable);
        }
        return productRepository.findAll(pageable);
    }
    public void changeStatus(Long id) throws ProductNotFoundException {
        isExist(id);
        Product existingProduct=productRepository.findById(id).get();
        if (existingProduct.isEnabled()) {
            existingProduct.setEnabled(false);
        } else {
            existingProduct.setEnabled(true);
        }
        productRepository.save(existingProduct);
    }

    public void changeStock(Long id) throws ProductNotFoundException {
        isExist(id);
        Product existingProduct=productRepository.findById(id).get();
        if (existingProduct.isInStock()) {
            existingProduct.setInStock(false);
        } else {
            existingProduct.setInStock(true);
        }
        productRepository.save(existingProduct);
    }
    private void isExist(Long id) throws ProductNotFoundException {
        if(productRepository.findById(id).get()==null){
            throw new ProductNotFoundException("Не может найти продукт с таким ID" + id);
        }
    }
    public Product get(Long id) throws ProductNotFoundException {
        try{
            return productRepository.findById(id).get();
        }catch(NoSuchElementException ex){
            throw new ProductNotFoundException("Не может найти пользователя с таким ID" + id);
        }
    }

    public void delete(Long id) throws ProductNotFoundException {
        Long countById= productRepository.countById(id);
        if(countById==null||countById==0){
            throw new ProductNotFoundException("Не может найти пользователя с таким ID" + id);
        }
        productRepository.deleteById(id);
    }


    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }
}
