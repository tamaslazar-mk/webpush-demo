package com.pushmessage.demo.dto;


import nl.martijndwars.webpush.Subscription;

public class NotificationSubscription {

    private String subscriptionId;
    private Subscription pushSubscription;

    public NotificationSubscription() {
    }

    public NotificationSubscription(Subscription pushSubscription, String subscriptionId) {
        this.pushSubscription = pushSubscription;
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Subscription getSubscription() {
        return pushSubscription;
    }

    public void setSubscription(Subscription pushSubscription) {
        this.pushSubscription = pushSubscription;
    }

    @Override
    public String toString() {
        return "NotificationSubscription{" +
                "subscriptionId='" + subscriptionId + '\'' +
                ", pushSubscription=" + pushSubscription +
                '}';
    }
}
