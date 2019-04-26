package com.glic.adminserver.mails;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.glic.adminserver.model.AppUser;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class EmailService {

   private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

   @Autowired
   private JavaMailSender emailSender;

   @Value("${spring.application.activeEmail}")
   private boolean emailActive;

   @Value("${spring.application.fromAddress}")
   private String fromAddress;

   @Value("${spring.application.emailBaseLink}")
   private String emailBaseLink;

   @Autowired
   private Configuration freemarkerConfig;

   @Async
   public void sendUserEmail(AppUser user, EmailTypes type) throws IOException, TemplateException, MessagingException {
      if (emailActive) {
         Template t = freemarkerConfig.getTemplate(type.getTemplateFileName());

         //Generate variables that may be used on the template
         Map<String, String> variables = new HashMap<>();
         if (type == EmailTypes.ACTIVATION) {
            variables.put("activationLink", EmailTypes.ACTIVATION.getUrl(emailBaseLink, user.getEmail(), user.getActivatioToken()));
            variables.put("activationExpDate", user.getActivationTokenValidity().format(DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm")));
         } else {
            variables.put("recoveryLink", EmailTypes.RECOVERY.getUrl(emailBaseLink, user.getEmail(), user.getRecoveryToken()));
            variables.put("recoveryExpDate", user.getRecoveryTokenValidity().format(DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm")));
         }

         variables.put("name", user.getNameToShow());
         String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(t, variables);
         final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
         final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
         message.setTo(user.getEmail());
         message.setSubject(type.getEmailSubject());
         message.setText(htmlContent, true);
         message.setFrom(fromAddress);
         LOG.info("Email sent to:{}", user.getEmail());
         emailSender.send(mimeMessage);
      }
   }

   public enum EmailTypes {
      ACTIVATION("Activa tu cuenta", "{0}/#/activation?userId={1}&token={2}"),
      RECOVERY("Recupera tu password", "{0}/#/recovery?userId={1}&token={2}");

      private final String subject;

      private final String url;

      EmailTypes(String subject, String url) {
         this.subject = subject;
         this.url = url;

      }

      public String getTemplateFileName() {
         return StringUtils.lowerCase(this.name()) + ".html";
      }

      public String getEmailSubject() {
         return subject;
      }

      public String getUrl(String baseUrl, String email, String token) {
         return MessageFormat.format(this.url, baseUrl, email, token);
      }

   }

}