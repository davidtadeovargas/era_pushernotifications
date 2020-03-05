/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.pushernotifications;

import com.era.logger.LoggerUtility;
import com.era.pushernotifications.data.PushDataModel;
import com.era.pushernotifications.data.PushMessageDataModel;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

/**
 *
 * @author PC
 */
public class PusherPushNotificationsManager extends PushNotificationsManager {
    
    final private PusherOptions options = new PusherOptions().setCluster("us2");
    final private Pusher pusher;
    
    public PusherPushNotificationsManager(){
        this.apiKey = "61e2e7c9033d9ac84aef";//
        this.pusherChannel = "era";
        this.event = "main-event";
        
        pusher = new Pusher(this.apiKey, options);
    }

    @Override
    public void connect() {
        
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                
                if(change.getCurrentState()==ConnectionState.CONNECTED){
                    if(IPushNotificationConnectionSubscriber!=null){
                        IPushNotificationConnectionSubscriber.OnConnect(-1);
                    }
                }
                else if(change.getCurrentState()==ConnectionState.DISCONNECTED){
                    if(IPushNotificationConnectionSubscriber!=null){
                        IPushNotificationConnectionSubscriber.OnDisconnect();
                    }
                }
                
                LoggerUtility.getSingleton().logInfo(PusherPushNotificationsManager.class, "Push Notifications: State changed to " + change.getCurrentState() + " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                
                if(IPushNotificationConnectionErrorSubscriber!=null){
                    IPushNotificationConnectionErrorSubscriber.OnError(e);
                }
                
                LoggerUtility.getSingleton().logInfo(PusherPushNotificationsManager.class, "Push Notifications: There was a problem connecting! code: " + code + ", exception: " + e.getMessage());
            }
        }, ConnectionState.ALL);
        
        // Subscribe to a channel
        Channel channel = pusher.subscribe(this.pusherChannel);
        
        // Bind to listen for events called "my-event" sent to "my-channel"
        channel.bind(this.event, (PusherEvent event1) -> {
            
            LoggerUtility.getSingleton().logInfo(PusherPushNotificationsManager.class, "Push Notifications: Received event with data: " + event1.toString());
                        
            //Get the message
            final Gson g = new Gson();
            final PushMessageDataModel PushMessageDataModel = g.fromJson(event1.getData(), PushMessageDataModel.class);
            //Get the json response
            final PushDataModel PushDataModel = g.fromJson(PushMessageDataModel.getMessage(), PushDataModel.class);
            //Send to the user
            if(IPushNotificationMessageSubscriber!=null){
                IPushNotificationMessageSubscriber.OnMessage(PushDataModel);
            }
        });
    }

    @Override
    public void disconnect() {
        
        LoggerUtility.getSingleton().logInfo(PusherPushNotificationsManager.class, "Push Notifications: Disconnect command sent");
        
        // Disconnect from the service
        pusher.disconnect();
    }
}