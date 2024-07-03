package com.finalExam.cashier.service;

import com.finalExam.cashier.model.Menu;
import com.finalExam.cashier.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Long id, Menu updatedmenu) {
        Optional<Menu> menuOptional = menuRepository.findById(id);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            menu.setName(updatedmenu.getName());
            menu.setDescription(updatedmenu.getDescription());
            menu.setPrice(updatedmenu.getPrice());
            menu.setCategory(updatedmenu.getCategory());
            menu.setStock(updatedmenu.getStock());
            return menuRepository.save(menu);
        } else {
            throw new RuntimeException("Menu not found");
        }
    }

    public void deleteMenu(Long id) {
       menuRepository.deleteById(id);
    }
}
