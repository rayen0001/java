package Store.Lumia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Store.Lumia.entity.Fournisseur;
import Store.Lumia.service.FournisseurService;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @Autowired
    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @PostMapping
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        Fournisseur createdFournisseur = fournisseurService.createFournisseur(fournisseur);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFournisseur);
    }

    @PutMapping("/{fournisseurId}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long fournisseurId, @RequestBody Fournisseur fournisseur) {
        Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(fournisseurId, fournisseur);
        return ResponseEntity.ok(updatedFournisseur);
    }

    @DeleteMapping("/{fournisseurId}")
    public ResponseEntity<String> deleteFournisseur(@PathVariable Long fournisseurId) {
        fournisseurService.deleteFournisseur(fournisseurId);
        return ResponseEntity.ok("Fournisseur deleted successfully.");
    }

    @GetMapping("/{fournisseurId}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Long fournisseurId) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId);
        if (fournisseur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(fournisseur);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        return ResponseEntity.ok(fournisseurs);
    }

    @PostMapping("/{fournisseurId}/commander")
    public ResponseEntity<String> commanderProduit(@PathVariable Long fournisseurId, @RequestParam String email) {
        Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId);
        if (fournisseur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fournisseur not found");
        }
        fournisseurService.commander(email);
        return ResponseEntity.ok("Commande envoyée au fournisseur avec succès.");
    }

}
