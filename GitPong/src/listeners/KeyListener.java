/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyEvent;

/**

 @author rich
 */
public class KeyListener implements EventHandler<KeyEvent> {

    public static Map<String, Boolean> keys = new HashMap<>();
    public static Map<String, Boolean> oldKeys = new HashMap<>();

    @Override
    public void handle(KeyEvent event) {
        //System.out.println(event.getEventType() + " " + event.getCode().getName());
        EventType<KeyEvent> eventType = event.getEventType();
        String eventName = event.getCode().getName();
        
        if (eventType == KeyEvent.KEY_PRESSED) {
            if (keys.containsKey(eventName)) {
                keys.replace(eventName, true);
            } else {
                keys.put(eventName, true);
            }
        }
        
        if (eventType == KeyEvent.KEY_RELEASED) {
            if (keys.containsKey(eventName)) {
                keys.replace(eventName, false);
            } else {
                keys.put(eventName, false);
            }
        }

        if (KeyListener.keys.containsKey("Esc")) {
            if (KeyListener.keys.get("Esc")) {
                System.exit(0);
            }
        }

    }

    public boolean isDown(String key) {
        if (KeyListener.keys.containsKey(key)) {
            if (KeyListener.keys.get(key)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
