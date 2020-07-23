package com.account.controller;

import com.account.mail.EmailService;
import com.account.mail.Mail;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Adedayo
 */
@Controller
public class MailController {
    
    @Autowired
    EmailService mailService;
    
    @RequestMapping(value = "/send-mail", method = RequestMethod.POST)
    public void sendMail()throws MessagingException, IOException, TemplateException{
        
            Mail mail = new Mail();
            mail.setTo("matthewadedayo@yahoo.com");
            mail.setFrom("dayoanifannu@gmail.com");
            mail.setSubject("User Account Registration");
            Map<String, Object> model = new HashMap<String, Object>();
                     {
	            model.put("salutation", "Dear " );
			}
            mail.setModel(model);
            mail.setTemplate("email_template.ftl");
            
            mailService.sendSimpleMessage(mail);
    }
}
