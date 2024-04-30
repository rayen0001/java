package Store.Lumia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Store.Lumia.entity.Magasin;
import Store.Lumia.repository.MagasinRepository;

@Transactional
@Service
public class MagasinServiceImpl implements MagasinService {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Inject JdbcTemplate

    @Autowired
    private MagasinRepository magasinRepository;

    @Override
    public List<Magasin> getAllMagasins() {
        return magasinRepository.findAll();
    }

    @Override
    public Optional<Magasin> getMagasinById(Long id) {
        return magasinRepository.findById(id);
    }

    @Override
    public Magasin createMagasin(Magasin magasin) {
        String designation = magasin.getDesignation();
        magasin.setInventory("inventory_" + designation);

        // Save the magasin entity
        Magasin savedMagasin = magasinRepository.save(magasin);

        // Create the inventory table
        createInventoryTable(designation);

        return savedMagasin;
    }

    public void createInventoryTable(String designation) {
        String tableName = "inventory_" + designation;
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ( " +
                "id SERIAL PRIMARY KEY, " +
                "ref_p TEXT, " +
                "designation_p TEXT, " +
                "prix_ht DECIMAL(10, 2), " +
                "tva DECIMAL(5, 2), " +
                "prix_ttc DECIMAL(10, 2), " +
                "remise DECIMAL(5, 2), " +
                "qt_stock INT " +  // Removed the extra comma here
                ")";

        jdbcTemplate.execute(sql);
    }

    @Override
    public Optional<Magasin> updateMagasin(Long id, Magasin magasin) {
        Optional<Magasin> existingMagasin = magasinRepository.findById(id);
        if (existingMagasin.isPresent()) {
            magasin.setId(id);
            return Optional.of(magasinRepository.save(magasin));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteMagasin(Long id) {
        magasinRepository.deleteById(id);
    }
}
