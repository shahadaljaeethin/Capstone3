package com.example.Capstone3.Repository;

import com.example.Capstone3.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryById(Integer id);


}
