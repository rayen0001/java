package Store.Lumia.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Magasin")
public class Magasin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String designation;

    private String location;

	private String inventory;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "magasin")
    private List<User> users = new ArrayList<>();
}
