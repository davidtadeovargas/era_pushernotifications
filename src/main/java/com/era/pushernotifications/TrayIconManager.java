/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era.pushernotifications;

import com.era.logger.LoggerUtility;
import com.era.pushernotifications.data.PushDataModel;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import com.era.views.PushNotificationJFrame;
import com.era.views.ViewsFactory;

/**
 *
 * @author PC
 */
public class TrayIconManager {
 
    //Singleton
    private static TrayIconManager TrayIconManager;
    
    //Obtain only one instance of the SystemTray object
    private SystemTray tray;
    
    
    private TrayIconManager(){
    }
    
    public static TrayIconManager getSingletlon(){
        if(TrayIconManager==null){
            TrayIconManager = new TrayIconManager();
        }
        return TrayIconManager;
    }
    
    final public boolean isTraySupported(){
        return SystemTray.isSupported();
    }
    
    final public void showPushTray(final PushDataModel PushDataModel) throws Exception{
        
        LoggerUtility.getSingleton().logInfo(PusherPushNotificationsManager.class, "Push Notifications: Showing try icon");
        
        tray = SystemTray.getSystemTray();
        
        final String imageURL = PushDataModel.getUrlBanner();
        
        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "ERA Notificación");
        
        trayIcon.setImage(image);
        
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("Notificación ERA");
        
        tray.add(trayIcon);

        trayIcon.displayMessage("Notificacioens ERA", "Nueva notificación", TrayIcon.MessageType.INFO);
        
        LoggerUtility.getSingleton().logInfo(PusherPushNotificationsManager.class, "Push Notifications: Try icon showed");
    }
    
    
    final public void showPushNotification(final PushDataModel PushDataModel) throws Exception{
        
        final PushNotificationJFrame PushNotificationJFrame = ViewsFactory.getSingleton().getPushNotificationJFrame(PushDataModel.getUrlBanner(),PushDataModel.getBannerAction());        
        PushNotificationJFrame.setSetVisibleWithEfect(true);
        PushNotificationJFrame.setVisible();
    }
}
