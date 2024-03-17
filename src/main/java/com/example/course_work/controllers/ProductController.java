package com.example.course_work.controllers;

import com.example.course_work.entity.Category;
import com.example.course_work.entity.Product;
import com.example.course_work.entity.Role;
import com.example.course_work.entity.User;
import com.example.course_work.exceptions.ProductNotFoundException;
import com.example.course_work.exceptions.UserNotFoundException;
import com.example.course_work.export.FileUploadUtil;
import com.example.course_work.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping( "/products/save")
    public String saveProduct(Product product, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ProductNotFoundException {
        if(!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            product.setMainImage(fileName);

            Product savedProduct = productService.save(product);
            String uploadDir = "product-images/" + savedProduct.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }else {
            productService.save(product);
        }
        redirectAttributes.addFlashAttribute("massage","Продукт сохранен успешно");
        return "redirect:/products";
    }
    @GetMapping("/products")
    public String listFirstPage(Model model){
        return listByPage(1,model,"name","asc",null);
    }
    @GetMapping("/products/page/{pageNum}")
    public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword){
        Product product=new Product();
        product.setEnabled(true);
        product.setInStock(true);
        Page<Product> page =productService.listByPage(pageNum,sortField,sortDir,keyword);
        List<Product> listProducts=page.getContent();
        Integer currentPage=pageNum;
        Long startCount= Long.valueOf((pageNum-1)* productService.PRODUCTS_PER_PAGE+1);
        Long endCount=startCount+productService.PRODUCTS_PER_PAGE-1;
        if(endCount>page.getTotalElements()){
            endCount=page.getTotalElements();
        }

        String reverseSortDir= sortDir.equals("asc")?"desc":"asc";

        model.addAttribute("product",product);
        model.addAttribute("categories", Category.values());
        model.addAttribute("keyword",keyword);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        return "admin_pages/products";
    }

    @GetMapping("/products/change_status/{id}")
    public String change_status(@PathVariable(name="id") Long id,RedirectAttributes redirectAttributes){
        try{
            productService.changeStatus(id);
            redirectAttributes.addFlashAttribute("massage","Статус изменён успешно");

        }catch(ProductNotFoundException ex){
            redirectAttributes.addFlashAttribute("massage",ex.getMessage());
        }

        return "redirect:/products";
    }
    @GetMapping("/products/change_stock/{id}")
    public String change_stock(@PathVariable(name="id") Long id,RedirectAttributes redirectAttributes){
        try{
            productService.changeStock(id);
            redirectAttributes.addFlashAttribute("massage","Наличие изменёно успешно");

        }catch(ProductNotFoundException ex){
            redirectAttributes.addFlashAttribute("massage",ex.getMessage());
        }

        return "redirect:/products";
    }
    @GetMapping("/products/edit/{id}")
    public String editUser(@PathVariable(name="id") Long id,RedirectAttributes redirectAttributes,Model model){
        try{
            Product product=productService.get(id);
            model.addAttribute("categories", Category.values());
            model.addAttribute("product",product);
            return "admin_pages/edit_product";
        }catch(ProductNotFoundException ex){
            redirectAttributes.addFlashAttribute("massage",ex.getMessage());
        }

        return "redirect:/products";
    }
    @GetMapping("/products/delete/{id}")
    public String deleteUser(@PathVariable(name="id") Long id, RedirectAttributes redirectAttributes){
        try{
            productService.delete(id);
            redirectAttributes.addFlashAttribute("massage","Продукт был успешно удалён");

        }catch(ProductNotFoundException ex){
            redirectAttributes.addFlashAttribute("massage",ex.getMessage());
        }

        return "redirect:/products";
    }
}
