package Store.Lumia.controller;

import Store.Lumia.entity.Inventory;
import Store.Lumia.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{inventoryName}/items")
    public ResponseEntity<List<Inventory>> getInventoryItems(@PathVariable String inventoryName) {
        List<Inventory> inventoryItems = inventoryService.getInventoryItems(inventoryName);
        return ResponseEntity.ok(inventoryItems);
    }

    @PostMapping("/{inventoryName}/items")
    public ResponseEntity<Void> addInventoryItem(@PathVariable String inventoryName, @RequestBody Inventory request) {
        inventoryService.addInventoryItem(inventoryName, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{inventoryName}/items/{inventoryItemId}")
    public ResponseEntity<Inventory> getInventoryItem(@PathVariable String inventoryName, @PathVariable Long inventoryItemId) {
        Inventory inventoryItem = inventoryService.getInventoryItem(inventoryName, inventoryItemId);
        if (inventoryItem != null) {
            return ResponseEntity.ok(inventoryItem);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{inventoryName}/items/{inventoryItemId}")
    public ResponseEntity<Void> updateInventoryItem(@PathVariable String inventoryName, @PathVariable Long inventoryItemId, @RequestBody Inventory request) {
        inventoryService.updateInventoryItem(inventoryName, inventoryItemId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{inventoryName}/items/{inventoryItemId}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable String inventoryName, @PathVariable Long inventoryItemId) {
        inventoryService.deleteInventoryItem(inventoryName, inventoryItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

