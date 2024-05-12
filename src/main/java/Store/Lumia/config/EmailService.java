package Store.Lumia.config;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import Store.Lumia.entity.Mail;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendSimpleEmail(final Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rayenaouechria@gmail.com");
        message.setTo(mail.getTo());
        message.setSubject("Réinitialiser le mot de passe");
        message.setText(mail.getContent());
        mailSender.send(message);
    }
}
