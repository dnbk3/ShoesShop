package vn.btl.hdvcoreservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.btl.hdvcoreservice.exception.CoreErrorCode;
import vn.btl.hdvcoreservice.exception.CoreException;
import vn.btl.hdvcoreservice.repository.CategoryDao;
import vn.btl.hdvcoreservice.repository.entity.Category;
import vn.btl.hdvcoreservice.request.CategoryRequest;
import vn.btl.hdvcoreservice.service.CategoryService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public ResponseEntity getAllCategory(String keyword){
        List<Category> suppliers = categoryDao.findByNameContainsIgnoreCase(keyword);
        return ResponseFactory.success(suppliers, List.class);
    }

    @Override
    public ResponseEntity addCategory(CategoryRequest request){
        Category category = new Category();
        AppUtils.copyPropertiesIgnoreNull(request, category);
        return ResponseFactory.success(categoryDao.save(category), Category.class);
    }

    @Override
    public ResponseEntity updateCategory(CategoryRequest request, Integer catId){
        Category category = categoryDao.findOneByCategoryId(catId);
        if(category == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AppUtils.copyPropertiesIgnoreNull(request, category);
        return ResponseFactory.success(categoryDao.save(category), Category.class);
    }

    @Override
    public ResponseEntity getOneCategory(Integer catId){
        Category category = categoryDao.findOneByCategoryId(catId);
        if(category == null)
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
        return ResponseFactory.success(category, Category.class);
    }

    @Override
    public ResponseEntity deleteCategory(Integer catId){
        Category category = categoryDao.findOneByCategoryId(catId);
        if(category == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        categoryDao.delete(category);
        return ResponseFactory.success();
    }
}
