package Store.Lumia.mapper;
import org.springframework.jdbc.core.RowMapper;

import Store.Lumia.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryRowMapper implements RowMapper<Inventory> {
    @Override
    public Inventory mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setId(rs.getLong("id"));
        inventory.setRefP(rs.getString("ref_p"));
        inventory.setDesignationP(rs.getString("designation_p"));
        inventory.setPrixHt(rs.getBigDecimal("prix_ht"));
        inventory.setTva(rs.getBigDecimal("tva"));
        inventory.setPrixTtc(rs.getBigDecimal("prix_ttc"));
        inventory.setRemise(rs.getBigDecimal("remise"));
        inventory.setQtStock(rs.getInt("qt_stock"));
        return inventory;
    }
}
