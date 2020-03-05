/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.pushernotifications;

import com.era.pushernotifications.data.PushDataModel;


/**
 *
 * @author PC
 */
public abstract class PushNotificationsManager {
    
    protected IPushNotificationConnectionSubscriber IPushNotificationConnectionSubscriber;
    protected IPushNotificationMessageSubscriber IPushNotificationMessageSubscriber;
    protected IPushNotificationConnectionErrorSubscriber IPushNotificationConnectionErrorSubscriber;
    protected String apiKey;
    protected String event;
    protected String pusherChannel;
    
    
    public void setIPushNotificationConnectionSubscriber(IPushNotificationConnectionSubscriber IPushNotificationConnectionSubscriber) {
        this.IPushNotificationConnectionSubscriber = IPushNotificationConnectionSubscriber;
    }

    public void setIPushNotificationConnectionErrorSubscriber(IPushNotificationConnectionErrorSubscriber IPushNotificationConnectionErrorSubscriber) {
        this.IPushNotificationConnectionErrorSubscriber = IPushNotificationConnectionErrorSubscriber;
    }
    
    public void setIPushNotificationMessageSubscriber(IPushNotificationMessageSubscriber IPushNotificationMessageSubscriber) {
        this.IPushNotificationMessageSubscriber = IPushNotificationMessageSubscriber;
    }
        
    public abstract void connect();
    public abstract void disconnect();
        
    public interface IPushNotificationConnectionSubscriber{
        public void OnConnect(int socketID);
        public void OnDisconnect();
    }
    public interface IPushNotificationConnectionErrorSubscriber{
        public void OnError(Exception Exception);
    }    
    public interface IPushNotificationMessageSubscriber{
        public void OnMessage(PushDataModel PushDataModel);
    }

    public void setPusherChannel(String pusherChannel) {
        this.pusherChannel = pusherChannel;
    }        
}
