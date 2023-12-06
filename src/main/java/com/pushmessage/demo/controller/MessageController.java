package com.pushmessage.demo.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pushmessage.demo.dto.NotificationSubscription;
import com.pushmessage.demo.service.MessageService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MessageController {

    private MessageService messageService;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/publicKey")
    public String getPublicKey() {
        return messageService.getPublicKey();
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Map<String, Object> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Subscription subscription = objectMapper.convertValue(map.get("pushSubscription"), Subscription.class);
        System.out.println(subscription);
        NotificationSubscription notificationSubscription = new NotificationSubscription();
        notificationSubscription.setSubscriptionId((String) map.get("subscriptionId"));
        notificationSubscription.setSubscription(subscription);
        System.out.println(notificationSubscription);
        messageService.subscribe(notificationSubscription);
    }

    @DeleteMapping("/unsubscribe")
    public void unSubscribe(@RequestParam String endpoint) {
        messageService.unSubscribe(endpoint);
    }
}
