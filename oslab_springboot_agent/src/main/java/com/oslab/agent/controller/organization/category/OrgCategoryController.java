package com.oslab.agent.controller.organization.category;

import com.oslab.agent.model.transfer.categoryDto.CategoryDto;
import com.oslab.agent.service.organization.category.OrgCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrgCategoryController {
    private final OrgCategoryService orgCategoryService;

    @PostMapping("api/category/add")
    public ResponseEntity<?> addNewCategory(@RequestBody CategoryDto categoryDto) throws SQLException {
        boolean success = orgCategoryService.addNewCategory(categoryDto);
        return success ? ResponseEntity.ok().body("Success") : ResponseEntity.internalServerError().body("Fail");
    }

    @PutMapping("api/category/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto) throws SQLException {
        boolean success = orgCategoryService.updateCategory(categoryDto);
        return success ? ResponseEntity.ok().body("Success") : ResponseEntity.internalServerError().body("Fail");
    }

    @DeleteMapping("api/category/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody CategoryDto categoryDto) throws SQLException {
        boolean success = orgCategoryService.deleteCategory(categoryDto);
        return success ? ResponseEntity.ok().body("Success") : ResponseEntity.internalServerError().body("Fail");
    }
}
