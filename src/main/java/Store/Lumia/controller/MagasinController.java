package Store.Lumia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Store.Lumia.entity.Magasin;
import Store.Lumia.service.MagasinService;

@RestController
@RequestMapping("/magasin")
public class MagasinController {

    @Autowired
    private MagasinService magasinService;

    @GetMapping
    public List<Magasin> getAllMagasins() {
        return magasinService.getAllMagasins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Magasin> getMagasinById(@PathVariable Long id) {
        Optional<Magasin> magasin = magasinService.getMagasinById(id);
        if (magasin.isPresent()) {
            return ResponseEntity.ok(magasin.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public Magasin createMagasin(@RequestBody Magasin magasin) {
        return magasinService.createMagasin(magasin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Magasin> updateMagasin(@PathVariable Long id, @RequestBody Magasin magasin) {
        Optional<Magasin> updatedMagasin = magasinService.updateMagasin(id, magasin);
        if (updatedMagasin.isPresent()) {
            return ResponseEntity.ok(updatedMagasin.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMagasin(@PathVariable Long id) {
        magasinService.deleteMagasin(id);
        return ResponseEntity.noContent().build();
    }
}