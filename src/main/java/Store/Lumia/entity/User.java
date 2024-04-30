package Store.Lumia.entity;

import lombok.Data;
import lombok.*;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "\"user\"")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    
    private String prenom;

    @Column(unique = true)
    private String matricule;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String mot2passe;
    
    private String email;
    
    private String typeCompte;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_magasin",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "magasin_id"))
    private List<Magasin> magasin = new ArrayList<>();
    
}
