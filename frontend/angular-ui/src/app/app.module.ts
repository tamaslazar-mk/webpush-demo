import {isDevMode, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PushNotificationComponent } from './components/push-notification/push-notification.component';
import {ServiceWorkerModule} from "@angular/service-worker";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    PushNotificationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: isDevMode(),
      // Remove the following line to enable the service worker for development
      // enabled: environment.production,
      registrationStrategy: 'registerImmediately'
}),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
