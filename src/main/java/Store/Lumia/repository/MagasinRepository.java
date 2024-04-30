package Store.Lumia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Store.Lumia.entity.Magasin;

@Repository
public interface MagasinRepository extends JpaRepository<Magasin, Long> {
}