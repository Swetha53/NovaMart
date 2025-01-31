package com.novamart.inventory_service.repository;

import com.novamart.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
    @Query("SELECT i FROM Inventory i WHERE i.productId = ?1")
    Inventory findByProductId(String productId);
}
