package Store.Lumia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import Store.Lumia.entity.Inventory;
import Store.Lumia.mapper.InventoryRowMapper;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addInventoryItem(String inventoryName, Inventory request) {
        String sql = "INSERT INTO " + inventoryName + " (ref_p, designation_p, prix_ht, tva, prix_ttc, remise, qt_stock) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                request.getRefP(),
                request.getDesignationP(),
                request.getPrixHt(),
                request.getTva(),
                request.getPrixTtc(),
                request.getRemise(),
                request.getQtStock());
    }

    @Override
    public Inventory getInventoryItem(String inventoryName, Long inventoryItemId) {
        String sql = "SELECT * FROM " + inventoryName + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{inventoryItemId}, new InventoryRowMapper());
    }

    @Override
    public void updateInventoryItem(String inventoryName, Long inventoryItemId, Inventory request) {
        String sql = "UPDATE " + inventoryName + " SET ref_p = ?, designation_p = ?, prix_ht = ?, tva = ?, prix_ttc = ?, remise = ?, qt_stock = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql,
                request.getRefP(),
                request.getDesignationP(),
                request.getPrixHt(),
                request.getTva(),
                request.getPrixTtc(),
                request.getRemise(),
                request.getQtStock(),
                inventoryItemId);
    }

    @Override
    public void deleteInventoryItem(String inventoryName, Long inventoryItemId) {
        String sql = "DELETE FROM " + inventoryName + " WHERE id = ?";
        jdbcTemplate.update(sql, inventoryItemId);
    }
    @Override
    public List<Inventory> getInventoryItems(String inventoryName) {
        String sql = "SELECT * FROM " + inventoryName;
        return jdbcTemplate.query(sql, new InventoryRowMapper());
    }

}
