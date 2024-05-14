package Store.Lumia.service;

import Store.Lumia.entity.Fournisseur;

import java.util.List;

public interface FournisseurService {
    Fournisseur createFournisseur(Fournisseur fournisseur);
    Fournisseur updateFournisseur(Long id, Fournisseur fournisseur);
    void deleteFournisseur(Long id);
    List<Fournisseur> getAllFournisseurs();
    Fournisseur getFournisseurById(Long id);
    Fournisseur getFournisseurByDesignation(String designation);
    Fournisseur getFournisseurByEmail(String email);
    void commander(String email);
}
