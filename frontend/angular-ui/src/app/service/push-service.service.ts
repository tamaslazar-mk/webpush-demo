import {Injectable} from '@angular/core';
import {SwPush} from "@angular/service-worker";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {PushNotificationModel} from "../models/push-notification-model";
import {v4 as uuid} from 'uuid';


@Injectable({
  providedIn: 'root'
})
export class PushService {

  constructor(private swPush: SwPush, private http: HttpClient) {
  }

  subscribeToNotifications() {

    console.log(this.swPush.isEnabled)


    this.swPush.requestSubscription({
      serverPublicKey: environment.PUBLIC_KEY
    })
      .then(subscription => {
        const sub: PushNotificationModel = {
          subscriptionId: localStorage.getItem('subscriptionId') === null ? uuid() : localStorage.getItem('subscriptionId'),
          pushSubscription: subscription,

        }
        console.log(sub)
          this.http.post('http://localhost:8080/api/subscribe', sub).subscribe({
            complete: () => {
              if (sub.subscriptionId !== null) {
                localStorage.setItem('subscriptionId', sub.subscriptionId);
                console.log('subscriptionId saved')
              }
            }
          });
      })
      .catch(console.error);


  }

  unsubscribeFromNotifications() {
    this.swPush.unsubscribe();
  }

  subscribeMessage() {
    this.swPush.messages.subscribe((message) => {
      console.log(message);
    });
  }
}
