package com.klav.service.ext.impl;

import com.klav.config.Constants;
import com.klav.service.MailService;
import com.klav.service.ext.NotificationContext;
import com.klav.service.ext.NotificationService;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Locale;

/**
 * Concrete implementation using Email as notifier.
 * We use the @Async annotation to send emails asynchronously.
 */
@Service
public class MailNotificationService implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(MailNotificationService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final MailService mailService;

    private final JHipsterProperties jHipsterProperties;

    private final SpringTemplateEngine templateEngine;

    private final MessageSource messageSource;

    public MailNotificationService(MailService mailService, JHipsterProperties jHipsterProperties,
                                   SpringTemplateEngine templateEngine, MessageSource messageSource) {
        this.mailService = mailService;
        this.jHipsterProperties = jHipsterProperties;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
    }

    @Async
    @Override
    public void pushAccountCreation(NotificationContext notificationContext) {
        log.debug("Sending creation email to '{}'", notificationContext.getTo());
        sendEmailFromTemplate(notificationContext, "mail/accountCreationEmail", "email.creation.text1");
    }

    private void sendEmailFromTemplate(NotificationContext notificationContext, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(Constants.DEFAULT_LANGUAGE);
        Context context = new Context(locale);
        context.setVariable(USER, notificationContext.getBoundedContext());
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);

        mailService.sendEmail(notificationContext.getTo(), subject, content, false, true);
    }
}
