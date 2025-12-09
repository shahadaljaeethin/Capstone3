package com.example.Capstone3.Service;



import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class sendMail {

    @Autowired
    private JavaMailSender javaEmailSender;

    public void sendMessage(){

        String body = "any body";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo("shahadkn67@gmail.com"); //change later, I want to test it
        simpleMailMessage.setFrom("rewaya.website26@gmail.com");
        simpleMailMessage.setSubject("any Title");
        simpleMailMessage.setText(body);
        javaEmailSender.send(simpleMailMessage);


    }




}
