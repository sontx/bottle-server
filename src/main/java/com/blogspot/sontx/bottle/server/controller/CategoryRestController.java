package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.Category;
import com.blogspot.sontx.bottle.server.model.bean.Room;
import com.blogspot.sontx.bottle.server.model.bean.RoomList;
import com.blogspot.sontx.bottle.server.model.service.category.CategoryService;
import com.blogspot.sontx.bottle.server.model.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
    private final CategoryService categoryService;
    private final RoomService roomService;

    @Autowired
    public CategoryRestController(CategoryService categoryService, RoomService roomService) {
        this.categoryService = categoryService;
        this.roomService = roomService;
    }

    @GetMapping
    ResponseEntity getCategories(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<Category> categories = categoryService.getCategories(page, pageSize);
        return categories != null ? ResponseEntity.ok(categories) : ResponseEntity.status(400).build();
    }

    @GetMapping("{categoryId}")
    ResponseEntity getCategory(@PathVariable int categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.status(400).build();
    }

    @GetMapping("{categoryId}/rooms")
    ResponseEntity getRooms(@PathVariable int categoryId, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<Room> rooms = roomService.getRooms(categoryId, page, pageSize);
        Category category = categoryService.getCategory(categoryId);
        if (rooms != null && category != null) {
            RoomList roomList = new RoomList();
            roomList.setCategoryId(category.getId());
            roomList.setCategoryName(category.getName());
            roomList.setRooms(rooms);
            return ResponseEntity.ok(roomList);
        }
        return ResponseEntity.status(400).build();
    }
}
