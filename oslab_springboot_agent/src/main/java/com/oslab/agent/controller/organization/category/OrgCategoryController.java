package com.oslab.agent.controller.organization.category;

import com.oslab.agent.model.transfer.categoryDto.*;
import com.oslab.agent.service.organization.category.OrgCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class OrgCategoryController {
    private final OrgCategoryService orgCategoryService;

    @PostMapping("api/category/addL1")
    public ResponseEntity<Long> addNewCategoryL1(@RequestBody CategoryDto categoryDto) throws SQLException {
        Long success = orgCategoryService.addNewCategoryL1(categoryDto);
        return ResponseEntity.ok().body(success);
    }

    @PostMapping("api/category/addL2")
    public ResponseEntity<Long> addNewCategoryL2(@RequestBody CategoryDtoL2 categoryDtoL2) throws SQLException {
        Long success = orgCategoryService.addNewCategoryL2(categoryDtoL2);
        return ResponseEntity.ok().body(success);
    }

    @PostMapping("api/category/delCategory")
    public ResponseEntity<Long> delCategory(@RequestBody DelCategoryDto delCategoryDto) throws SQLException {
        System.out.println("Controller/Delete Type: " + delCategoryDto.getType());
        Long success = orgCategoryService.delCategory(delCategoryDto);
        return ResponseEntity.ok().body(success);
    }

    @PostMapping("api/category/edit")
    public ResponseEntity<Long> editCategory(@RequestBody EditCategoryDto editCategoryDto) throws SQLException {
        Long success = orgCategoryService.editCategory(editCategoryDto);
        return ResponseEntity.ok().body(success);
    }

    @GetMapping("api/category/reFetchCategory")
    public ResponseEntity<ReFetchCategoryDto> reFetchCategory(@RequestParam("org_id") Integer org_id, @RequestParam("project_id") Integer project_id) throws SQLException {
        ReFetchCategoryDto dto = orgCategoryService.reFetchCategory(org_id, project_id);
        return ResponseEntity.ok().body(dto);
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
