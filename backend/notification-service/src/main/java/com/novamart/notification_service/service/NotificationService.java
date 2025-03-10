package com.novamart.notification_service.service;

import com.novamart.notification_service.dto.EmailInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final JavaMailSender mailSender;
    private final Map<String, EmailInfo> emailInfo = Map.of(
            "orderPlaced",
            new EmailInfo(
                    "Order Placed Successfully",
                    "Thank you for shopping with us! Your order has been placed successfully. We will let you know when there is any update in the order."
            ),
            "orderDelivered",
            new EmailInfo(
                    "Order Delivered Successfully",
                    "Thank you for shopping with us! Your order has been delivered successfully. Hope you have a nice day!"
            ),
            "orderCancelled",
            new EmailInfo(
                    "Order Cancelled Successfully",
                    "Your order has been cancelled successfully. Please let us know if you had any problems with us or our products so that we can improve your experience. Thank you for giving us a chance!"
            ),
            "paymentCompleted",
            new EmailInfo(
                    "Payment Completed Successfully",
                    "Thank you for shopping with us! Your payment has been completed successfully. We will let you know when there is any update in the order"
            ),
            "refundInitiated",
            new EmailInfo(
                    "Refund Initiated Successfully",
                    "Your refund has been initiated successfully. We will let you know when there is any update in the refund"
            ),
            "refundCompleted",
            new EmailInfo(
                    "Refund Completed Successfully",
                    "Your refund has been completed successfully. Please let us know if you had any problems with us or our products so that we can improve your experience. Thank you for giving us a chance!"
            ),
            "profileCompleted",
            new EmailInfo(
                    "Welcome to NovaMart!",
                    "Welcome to NovaMart! Hope you find the furniture of your dreams here."
            ),
            "profileUpdated",
            new EmailInfo(
                    "Your Profile has been updated",
                    "Your profile is successfully updated if not done by you please contact customer service."
            ),
            "profileDeleted",
            new EmailInfo(
                    "Your profile has been deleted",
                    "We are really sorry to see you go!! Hope to see you again sometime in the future."
            ),
            "reviewAdded",
            new EmailInfo(
                    "Review added to Product",
                    "Thank you for taking off your precious time to write the review."
            )
    );

    @KafkaListener(topics = "order-placed", groupId = "order")
    public void sendOrderPlacedEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "orderPlaced"));
    }

    @KafkaListener(topics = "order-delivered", groupId = "order")
    public void sendOrderDeliveredEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "orderDelivered"));
    }

    @KafkaListener(topics = "order-cancelled", groupId = "order")
    public void sendOrderCancelledEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "orderCancelled"));
    }

    @KafkaListener(topics = "payment-completed", groupId = "payment")
    public void sendPaymentCompletedEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "paymentCompleted"));
    }

    @KafkaListener(topics = "refund-initiated", groupId = "payment")
    public void sendRefundInitiatedEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "refundInitiated"));
    }

    @KafkaListener(topics = "refund-completed", groupId = "payment")
    public void sendRefundCompleteddEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "refundCompleted"));
    }

    @KafkaListener(topics = "profile-completed", groupId = "user")
    public void sendProfileCompletedEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "profileCompleted"));
    }

    @KafkaListener(topics = "profile-updated", groupId = "user")
    public void sendProfileUpdatedEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "profileUpdated"));
    }

    @KafkaListener(topics = "profile-deleted", groupId = "user")
    public void sendProfileDeletedEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "profileDeleted"));
    }

    @KafkaListener(topics = "review-added", groupId = "review")
    public void sendReviewAddedEmail(String userEmail) {
        mailSender.send(setEmailMessage(userEmail, "reviewAdded"));
    }

    public SimpleMailMessage setEmailMessage(String userEmail, String emailType) {
        EmailInfo emailDetails = emailInfo.get(emailType);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("swethanarayan25@gmail.com");
        message.setTo(userEmail);
        message.setSubject(emailDetails.subject());
        message.setText(emailDetails.body());
        return message;
    }
}
