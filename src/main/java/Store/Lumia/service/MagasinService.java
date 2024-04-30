package Store.Lumia.service;

import java.util.List;
import java.util.Optional;

import Store.Lumia.entity.Magasin;

public interface MagasinService {
    List<Magasin> getAllMagasins();
    Optional<Magasin> getMagasinById(Long id);
    Magasin createMagasin(Magasin magasin);
    Optional<Magasin> updateMagasin(Long id, Magasin magasin);
    void deleteMagasin(Long id);
    void createInventoryTable(String designation);
}