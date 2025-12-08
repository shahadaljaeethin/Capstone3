package com.example.Capstone3.Service;

import com.example.Capstone3.Api.ApiException;
import com.example.Capstone3.Model.Category;
import com.example.Capstone3.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void updateCategory(Integer id, Category category){
        Category oldCategory = categoryRepository.findCategoryById(id);

        if(oldCategory == null){
            throw new ApiException("Category not found");
        }

        oldCategory.setName(category.getName());
        oldCategory.setRangeKm(category.getRangeKm());
        oldCategory.setDescription(category.getDescription());
        oldCategory.setSize(category.getSize());

        categoryRepository.save(oldCategory);
    }

    public void deleteCategory(Integer id){
        Category category = categoryRepository.findCategoryById(id);

        if(category == null){
            throw new ApiException("Category not found");
        }

        categoryRepository.delete(category);
    }
}
