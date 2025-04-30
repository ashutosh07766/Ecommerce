package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public  interface CategoryService {
      CategoryResponse getAllCategories(Integer PageNumber,Integer PageSize,String sortBy,String sortOrder);
      CategoryDTO createCategory(CategoryDTO categoryDTO);
      CategoryDTO deleteCategory(long categoryId);
      CategoryDTO updateCategory(CategoryDTO categoryDTO,long categoryId);
}
