package org.example.restaurant.api;

import org.example.restaurant.dto.*;
import org.springframework.web.bind.annotation.*;
import org.example.restaurant.service.MenuItemService;

@RestController
@RequestMapping("/api/menuitems")
public class MenuItemApi {
    private final MenuItemService menuItemService;

    public MenuItemApi(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/{restauantId}")
    public SimpleResponse addMenuItem(@RequestBody MenuItemRequest menuItemRequest, @PathVariable Long restauantId) {
       return menuItemService.addMenuItem(menuItemRequest,restauantId);
    }

    @GetMapping("/{id}")
    public MenuItemResponceCategory getMenuItem(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateMenuItem(@RequestBody MenuItemRequest menuItemRequest,@PathVariable Long id) {
        return menuItemService.updateMenuItem(menuItemRequest,id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteMenuItem(@PathVariable Long id) {
        return menuItemService.deleteMenuItem(id);
    }

    @GetMapping("/type-first-character/{firstCharacter}")
    public MenuItemSpecailResponse getMenuItemWithFirstCharacter(@PathVariable String firstCharacter) {
        return menuItemService.getMenuItemWithFirstCharacter(firstCharacter);
    }

    @GetMapping("/sort-as-you-want/{sortType}")
    public MenuItemSpecailResponse getSorttedMenuItems(@PathVariable String sortType) {
        return menuItemService.getSorttedMenuItems(sortType);
    }

    @GetMapping("/vegan-nonvegan")
    public MenuItemVegeterian getSortByVegeterian() {
        return menuItemService.getSortByVegeterian();
    }

}

