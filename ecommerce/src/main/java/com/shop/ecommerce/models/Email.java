package com.shop.ecommerce.models;

import com.shop.ecommerce.enums.StatusEmail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "emails")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "email_from")
    private String emailFrom;
    @Column(name = "email_to")
    private String emailTo;
    @Column(name = "subject")
    private String subject;
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;
    @Column(name = "send_date")
    private LocalDateTime emailSendDate;
    @Column(name = "email_status")
    private StatusEmail statusEmail;


}
