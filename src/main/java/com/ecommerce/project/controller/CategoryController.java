package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController

public class CategoryController {
    @Autowired
       private CategoryService categoryService;



    @GetMapping("api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber ,
                                                             @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                             @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_CATEGORIES_BY)String sortBy,
                                                             @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_DIR)String sortOrder){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);

    }


    @PostMapping("api/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
            CategoryDTO categoryDTO1=categoryService.createCategory(categoryDTO);

            return new ResponseEntity<>(categoryDTO1,HttpStatus.CREATED);

    }


  @DeleteMapping("api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable long categoryId){

            CategoryDTO status= categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status,HttpStatus.OK);


  }
    @PutMapping("api/public/categories/{categoryId}")
  public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO
    ,@PathVariable long categoryId){

           CategoryDTO savedCategoryDTO= categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>( savedCategoryDTO,HttpStatus.OK);

  }

}
