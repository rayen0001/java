package Store.Lumia.service;

import Store.Lumia.config.EmailService;
import Store.Lumia.entity.Fournisseur;
import Store.Lumia.entity.Mail;
import Store.Lumia.repository.FournisseurRepository;
import Store.Lumia.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournisseurServiceImpl implements FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;



    @Override
    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Fournisseur updateFournisseur(Long id, Fournisseur fournisseur) {
        Fournisseur existingFournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));
        existingFournisseur.setDesignation(fournisseur.getDesignation());
        existingFournisseur.setEmail(fournisseur.getEmail());
        existingFournisseur.setTel(fournisseur.getTel());
        existingFournisseur.setFax(fournisseur.getFax());
        existingFournisseur.setAdresse(fournisseur.getAdresse());
        return fournisseurRepository.save(existingFournisseur);
    }

    @Override
    public void deleteFournisseur(Long id) {
        fournisseurRepository.deleteById(id);
    }

    @Override
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    @Override
    public Fournisseur getFournisseurById(Long id) {
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));
    }

    @Override
    public Fournisseur getFournisseurByDesignation(String designation) {
        return fournisseurRepository.findByDesignation(designation);
    }

    @Override
    public Fournisseur getFournisseurByEmail(String email) {
        return fournisseurRepository.findByEmail(email);
    }

    @Autowired
    private EmailService emailService;

    @Override
    public void commander(String email) {
        Fournisseur fournisseur = fournisseurRepository.findByEmail(email);
        if (fournisseur == null) {
            throw new RuntimeException("Fournisseur not found");
        }

        Mail mail = new Mail();
        mail.setTo(email);
        mail.setSubject("Commande de Produits chez le Fournisseur");
        mail.setContent("Bonjour,"+fournisseur.getDesignation() +"\n\n nous souhaitons passer commande. Voici les détails de la commande:\n\n" +
                "Fournisseur: " + "\n" +
                "Cordialement,\nEauipe Lumia");

        emailService.sendSimpleEmail(mail);
    }
}