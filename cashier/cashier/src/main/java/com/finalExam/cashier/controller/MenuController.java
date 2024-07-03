package com.finalExam.cashier.controller;


import com.finalExam.cashier.model.Menu;
import com.finalExam.cashier.service.CartService;
import com.finalExam.cashier.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    private CartService cartService;

    @GetMapping
    public List<Menu> getMenu() {
        return menuService.getAllMenu();
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @PostMapping
    public Menu addMenu(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        return menuService.updateMenu(id, menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }

    @PostMapping("/addToCart")
    public void addToCart(@RequestParam Long menuId) {
        cartService.addToCart(menuId);
    }

    @GetMapping("/cart")
    public List<Menu> getCart() {
        return cartService.getCart();
    }
}
