package Store.Lumia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Store.Lumia.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByMatricule(String matricule);

}
