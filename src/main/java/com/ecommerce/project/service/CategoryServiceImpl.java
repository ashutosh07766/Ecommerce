package com.ecommerce.project.service;

import com.ecommerce.project.exception.APIException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ecommerce.project.payload.CategoryDTO;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories(Integer PageNumber,Integer PageSize,String sortBy,String sortOrder) {

        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(PageNumber,PageSize,sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category>categories=categoryPage.getContent();
        if(categories.isEmpty()){
            throw new APIException("Category is empty");
        }
       List<CategoryDTO> categoryDTOS=categories.stream()
               .map(category -> modelMapper.map(category, CategoryDTO.class))
               .toList();
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getNumberOfElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO, Category.class);
         Category savedCategoryFromdb = categoryRepository.findByCategoryName(category.getCategoryName());
         if(savedCategoryFromdb != null) {
             throw new APIException("Category with the name "+ category.getCategoryName()+" already exists");
         }

         Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);

    }

    @Override
    public CategoryDTO deleteCategory(long categoryId) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category",categoryId,"categoryId"));

            categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);


    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, long categoryId) {

        Category savedCategory=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category",categoryId,"categoryId"));

        Category category=modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(categoryId);

        savedCategory=categoryRepository.save(category);
        return modelMapper.map(category,CategoryDTO.class);

    }
}
