package com.ecommerce.fullcart.service.product;

import com.ecommerce.fullcart.entity.product.Inventory;
import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.repository.product.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory findProductInventory(int productId) {
        return inventoryRepository.findInventoryByRefProduct_Id(productId);
    }

    public Inventory create(Product product, int quantity) {
        Inventory inventory = new Inventory();
        inventory.setSoldQuantity(0);
        inventory.setAvaQuantity(quantity);
        inventory.setRefProduct(product);
        return inventoryRepository.save(inventory);
    }

    public Inventory save(int productId, int quantity) {
        Inventory inventory = inventoryRepository.findInventoryByRefProduct_Id(productId);
        inventory.setAvaQuantity(quantity);
        return inventoryRepository.save(inventory);

    }
}
