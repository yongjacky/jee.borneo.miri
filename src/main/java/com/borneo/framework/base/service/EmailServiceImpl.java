package com.borneo.framework.base.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl extends JavaMailSenderImpl implements EmailService {

    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public static final String BEAN_NAME = "emailService";

    private Properties javaMailProperties = new Properties();

    private Executor executor = Executors.newFixedThreadPool(2);

    public EmailServiceImpl() {
    }

    /**
     * filter email by using EmailValidator.getInstance().isValid().
     * @param emailAddresses
     * @return
     */
    /**
     * Send email.<br>
     * Thread will be use.<br>
     * Custom message will be use.<br>
     * @param msg
     * @param emailAddress
     */

    @PostConstruct
    public void init() throws Exception {
        updateMailSender();
    }

    public void updateMailSender() {

        //setDefaultEncoding("UTF-8"); setUsername("polygonsmtp@borneo.com"); setPassword("PS1288#a"); setHost("mail.borneo.com"); javaMailProperties.setProperty("mail.smtp.auth", "true"); javaMailProperties.setProperty("mail.smtp.starttls.enable", "true"); setJavaMailProperties(javaMailProperties);
        setDefaultEncoding("UTF-8");
        //        setPort(Integer.parseInt(SpringUtil.getMessage("email.port")));
        //        setUsername(SpringUtil.getMessage("email.username"));
        //        setPassword(SpringUtil.getMessage("email.password"));
        //        setHost(SpringUtil.getMessage("email.host"));

        setPort(25);
        setUsername("do-not-reply.vcircle@appxtream.com");
        setPassword("vcircle2014");
        setHost("smtp.gmail.com");

        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");

        setJavaMailProperties(javaMailProperties);

    }

    @Override
    public void sendTextMail(String[] receviers, String replyTo, String subject, String content) {
        if ((receviers == null) || (receviers.length < 1)) {
            return;
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("do-not-reply.vcircle@appxtream.com");
        msg.setTo(receviers);
        msg.setSubject(subject);
        msg.setText(content);
        if (StringUtils.isNotBlank(replyTo)) {
            msg.setReplyTo(replyTo);
        }
        //executor.execute(new MailTask(this, msg));
        this.send(msg);
    }

    @Override
    public void sendMimeMail(String[] receviers, String replyTo, String subject, String content, File[] attachments) {
        if ((receviers == null) || (receviers.length < 1)) {
            return;
        }
        MimeMessage msg = this.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            helper.setTo(receviers);
            //helper.setFrom(Constant.SYSTEMCONFIG.getAdminEmail(),Constant.SYSTEMCONFIG.getStoreName());
            helper.setFrom("do-not-reply.vcircle@appxtream.com", "Vcircle");
            helper.setSubject(subject);
            helper.setText(content, true);
            if (StringUtils.isNotBlank(replyTo)) {
                helper.setReplyTo(replyTo);
            }
            if ((attachments != null) && (attachments.length > 0)) {
                for (File file : attachments) {
                    helper.addAttachment(file.getName(), file);
                }
            }
        } catch (MessagingException e) {
            //	            logger.error(e.getMessage());
            e.printStackTrace();
            return;

        } catch (UnsupportedEncodingException e) {
            //	            logger.error(e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            MailTask mailTask = new MailTask(this, msg);
            new Thread(mailTask).start();
        } catch (Exception e) {
            //	            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private static class MailTask implements Runnable {

        private JavaMailSender mailSender;

        private MimeMessage msg;

        public MailTask(JavaMailSender mailSender, MimeMessage msg) {
            this.mailSender = mailSender;
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                synchronized (mailSender) {
                    mailSender.send(msg);
                }
            } catch (MailException e) {
                //logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
