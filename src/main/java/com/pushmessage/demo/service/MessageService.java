package com.pushmessage.demo.service;

import com.pushmessage.demo.dto.NotificationSubscription;
import jakarta.annotation.PostConstruct;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class MessageService {

    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    private PushService pushService;
    private Map<String, Subscription> subscriptions = new HashMap<>();

    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void subscribe(NotificationSubscription notificationSubscription) {
        subscriptions.put(notificationSubscription.getSubscriptionId(), notificationSubscription.getSubscription());
        System.out.println(subscriptions.size());
        System.out.println("Subscribed to " + notificationSubscription.getSubscription().endpoint);
    }

    public void unSubscribe(String endpoint) {
        subscriptions.forEach((id, subscription) -> {
            if (subscription.endpoint.equals(endpoint)) {
                subscriptions.remove(id);
            }
        });
        System.out.println("Unsubscribed from " + endpoint);
    }

    public void SendNotification(Subscription subscription, String messageJson) {
        try {
            pushService.send(new Notification(subscription, messageJson));
        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 10000)
    private void sendNotifications() {

        String json = """
                {
                    "notification": {
                    
                        "title": "Hello from server",
                        "body": "It is now: %s",
                        "data": {
                        "url": "http://localhost:4200"
                    }
                    }
                    
                }
                """;

        subscriptions.forEach((id, subscription) -> {
            SendNotification(subscription, String.format(json, LocalTime.now()));
        });
    }


}
