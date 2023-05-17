package vn.btl.hdvcoreservice.controller;



import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.btl.hdvcoreservice.interceptor.Payload;
import vn.btl.hdvcoreservice.request.CategoryRequest;
import vn.btl.hdvcoreservice.service.CategoryService;
import vn.btl.hdvcoreservice.service.UserRoleService;
import vn.btl.hdvcoreservice.utils.Operator;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import javax.xml.ws.Response;


@RestController
@RequestMapping("/category")
@Log4j2
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRoleService userRoleService;


    @GetMapping
    public ResponseEntity getCategorys(
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        log.info("get categorys with keyword {}", keyword);
        return categoryService.getAllCategory(keyword);
    }

    @GetMapping("/{categoryId:\\d+}")
    public ResponseEntity getOneCategory(@RequestAttribute Payload payload, @PathVariable Integer categoryId){
        log.info("get category with id {}", categoryId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.VIEW_CATEGORY))
            return ResponseFactory.permissionDenied();
        return categoryService.getOneCategory(categoryId);
    }

    @PostMapping()
    public ResponseEntity addCategory(@RequestAttribute Payload payload, @RequestBody CategoryRequest request){
        log.info("add new category");
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.CREATE_CATEGORY))
            return ResponseFactory.permissionDenied();
        return categoryService.addCategory(request);
    }

    @PutMapping("/{categoryId:\\d+}")
    public ResponseEntity updateCategory(@RequestAttribute Payload payload, @RequestBody CategoryRequest request
            , @PathVariable Integer categoryId){
        log.info("update category with id {}", categoryId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.UPDATE_CATEGORY))
            return ResponseFactory.permissionDenied();
        return categoryService.updateCategory(request,categoryId);
    }

    @DeleteMapping("/{categoryId:\\d+}")
    public ResponseEntity deleteCategory(
            @RequestAttribute Payload payload, @PathVariable Integer categoryId){
        log.info("delete category with id {}", categoryId);
        if(!userRoleService.checkRole(payload.getCustomerId(), Operator.ACTION.DELETE_CATEGORY))
            return ResponseFactory.permissionDenied();
        return categoryService.deleteCategory(categoryId);
    }

}
