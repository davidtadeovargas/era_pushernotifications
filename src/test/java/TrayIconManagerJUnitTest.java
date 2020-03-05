import com.era.logger.LoggerUtility;
import com.era.pushernotifications.TrayIconManager;
import com.era.pushernotifications.data.PushDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author PC
 */
@TestMethodOrder(OrderAnnotation.class)
public class TrayIconManagerJUnitTest  {
    
    @Test
    @Order(1)
    public void updateCompanyHttpClientTest() {
        
        try{
            
            final PushDataModel PushDataModel = new PushDataModel();
            PushDataModel.setUrlBanner("http://localhost/banners/146815e268ff0cb8188.10859614.png");
            PushDataModel.setBannerAction("http://localhost/banners/146815e268ff0cb8188.10859614.png");
            TrayIconManager.getSingletlon().showPushNotification(PushDataModel);
            
            //Never ends this program
            Thread.currentThread().join();        
            
        }catch(Exception e){
            LoggerUtility.getSingleton().logError(PusherPushNotificationsManagerJUnitTest.class, e);
            
            Assertions.fail();
        }
    }
}
