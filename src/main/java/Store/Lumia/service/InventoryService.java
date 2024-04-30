package Store.Lumia.service;

import java.util.List;

import Store.Lumia.entity.Inventory;

public interface InventoryService {
    void addInventoryItem(String inventoryName, Inventory request);
    Inventory getInventoryItem(String inventoryName, Long inventoryItemId);
    void updateInventoryItem(String inventoryName, Long inventoryItemId, Inventory request);
    void deleteInventoryItem(String inventoryName, Long inventoryItemId);
	List<Inventory> getInventoryItems(String inventoryName);
}

