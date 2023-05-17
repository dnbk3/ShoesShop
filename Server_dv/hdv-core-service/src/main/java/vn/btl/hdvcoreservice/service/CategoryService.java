package vn.btl.hdvcoreservice.service;

import org.springframework.http.ResponseEntity;
import vn.btl.hdvcoreservice.request.CategoryRequest;

public interface CategoryService {

    ResponseEntity getAllCategory(String keyword);

    ResponseEntity addCategory(CategoryRequest request);

    ResponseEntity updateCategory(CategoryRequest request, Integer catId);

    ResponseEntity getOneCategory(Integer catId);

    ResponseEntity deleteCategory(Integer catId);
}
