package Store.Lumia.entity;

import lombok.*;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refP;

    private String designationP;

    private BigDecimal prixHt;

    private BigDecimal tva;

    private BigDecimal prixTtc;

    private BigDecimal remise;

    private int qtStock;

    // Add setter methods for each field
    public void setId(Long id) {
        this.id = id;
    }

    public void setRefP(String refP) {
        this.refP = refP;
    }

    public void setDesignationP(String designationP) {
        this.designationP = designationP;
    }

    public void setPrixHt(BigDecimal prixHt) {
        this.prixHt = prixHt;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public void setPrixTtc(BigDecimal prixTtc) {
        this.prixTtc = prixTtc;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }

    public void setQtStock(int qtStock) {
        this.qtStock = qtStock;
    }
}
