package Store.Lumia.repository;

import Store.Lumia.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    Fournisseur findByDesignation(String designation);
    Fournisseur findByEmail(String email);
}
