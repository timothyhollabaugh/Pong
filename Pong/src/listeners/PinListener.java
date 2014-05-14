/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listeners;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import pong.Printer;

/**

 @author rich
 */
public class PinListener implements GpioPinListenerDigital {
    
    public static Map<Pin, Boolean> keys = new HashMap<>();
    
    static GpioPinDigitalInput leftUp;
    static GpioPinDigitalInput leftDown;
    static GpioPinDigitalInput leftRight;
    static GpioPinDigitalInput leftLeft;
    static GpioPinDigitalInput leftButton;
    static GpioPinDigitalInput rightUp;
    static GpioPinDigitalInput rightDown;
    static GpioPinDigitalInput rightRight;
    static GpioPinDigitalInput rightLeft;
    static GpioPinDigitalInput rightButton;
    static GpioPinDigitalInput exit;
    
    public static void init(){
        final GpioController gpio = GpioFactory.getInstance();
        Printer.println("Setting Variables");
        leftUp = gpio.provisionDigitalInputPin(Input.L_UP, PinPullResistance.PULL_UP);
        leftDown = gpio.provisionDigitalInputPin(Input.L_DOWN, PinPullResistance.PULL_UP);
        leftLeft = gpio.provisionDigitalInputPin(Input.L_LEFT, PinPullResistance.PULL_UP);
        leftRight = gpio.provisionDigitalInputPin(Input.L_RIGHT, PinPullResistance.PULL_UP);
        leftButton = gpio.provisionDigitalInputPin(Input.L_BUTTON, PinPullResistance.PULL_UP);
        rightUp = gpio.provisionDigitalInputPin(Input.R_UP, PinPullResistance.PULL_UP);
        rightDown = gpio.provisionDigitalInputPin(Input.R_DOWN, PinPullResistance.PULL_UP);
        rightLeft = gpio.provisionDigitalInputPin(Input.R_LEFT, PinPullResistance.PULL_UP);
        rightRight = gpio.provisionDigitalInputPin(Input.R_RIGHT, PinPullResistance.PULL_UP);
        rightButton = gpio.provisionDigitalInputPin(Input.R_BUTTON, PinPullResistance.PULL_UP);
        exit = gpio.provisionDigitalInputPin(Input.EXIT, PinPullResistance.PULL_UP);
        
        Printer.println("Adding Listeners");
        leftUp.addListener(new PinListener());
        leftDown.addListener(new PinListener());
        leftLeft.addListener(new PinListener());
        leftRight.addListener(new PinListener());
        leftButton.addListener(new PinListener());
        rightUp.addListener(new PinListener());
        rightDown.addListener(new PinListener());
        rightLeft.addListener(new PinListener());
        rightRight.addListener(new PinListener());
        rightButton.addListener(new PinListener());
        exit.addListener(new PinListener());
        
        Printer.println("Putting Keys");
        keys.put(Input.EXIT, false);
        keys.put(Input.L_UP, false);
        keys.put(Input.L_DOWN, false);
        keys.put(Input.L_LEFT, false);
        keys.put(Input.L_RIGHT, false);
        keys.put(Input.L_BUTTON, false);
        keys.put(Input.R_UP, false);
        keys.put(Input.R_DOWN, false);
        keys.put(Input.R_LEFT, false);
        keys.put(Input.R_LEFT, false);
        keys.put(Input.R_BUTTON, false);
    }
    
    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        //System.out.println(event.getEventType() + " " + event.getCode().getName());
        GpioPin eventPin = event.getPin();
        PinState eventState = event.getState();
        if (eventState == PinState.LOW) {
            keys.replace(eventPin.getPin(), true);
            if(eventPin.getPin() == Input.EXIT){
                try {
                    Runtime runtime = Runtime.getRuntime();
                    Process proc = runtime.exec("init 0");
                    System.exit(0);
                } catch (IOException ex) {
                    
                }
            }
        }
        
        if (eventState == PinState.HIGH) {
            keys.replace(eventPin.getPin(), false);
        }
        
    }
    
    public boolean isDown(Pin pin){
        return keys.get(pin);
    }
    
}
