package vn.btl.hdvcoreservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.btl.hdvcoreservice.repository.entity.Category;

import java.util.List;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {

    List<Category> findByNameContainsIgnoreCase(String keyword);

    Category findOneByCategoryId(Integer catId);
}
