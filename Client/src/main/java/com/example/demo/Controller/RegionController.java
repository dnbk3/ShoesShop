package com.example.demo.Controller;

import com.example.demo.Model.Region;
import com.example.demo.Service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/region/{id}")
    public List<Region> getDistricts(@PathVariable("id") int id) {
        return regionService.GetAllByParentId(id);
    }
}