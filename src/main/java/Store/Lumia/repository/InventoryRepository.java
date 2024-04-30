package Store.Lumia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Store.Lumia.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // You can add custom query methods here if needed
}
