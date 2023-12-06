import {Component, OnDestroy, OnInit} from '@angular/core';
import {PushService} from "./service/push-service.service";
import {SwPush} from "@angular/service-worker";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy{
  title = 'angular-ui';

  constructor(private pushService: PushService, private swPush: SwPush) {

  }

  ngOnInit() {
    this.swPush.notificationClicks.subscribe(notification => {
      console.log(notification);

      window.location.href = notification.notification.data.url;
    });
    this.pushService.subscribeToNotifications()
    this.pushService.subscribeMessage();

  }

  ngOnDestroy() {
    this.pushService.unsubscribeFromNotifications();
  }
}
