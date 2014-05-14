/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listeners;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**

 @author tim
 */
public class Input {
    
    public static final Pin L_UP = RaspiPin.GPIO_12;
    public static final Pin L_DOWN = RaspiPin.GPIO_13;
    public static final Pin L_LEFT = RaspiPin.GPIO_14;
    public static final Pin L_RIGHT = RaspiPin.GPIO_06;
    public static final Pin L_BUTTON = RaspiPin.GPIO_05;
    public static final Pin R_UP = RaspiPin.GPIO_07;
    public static final Pin R_DOWN = RaspiPin.GPIO_00;
    public static final Pin R_LEFT = RaspiPin.GPIO_02;
    public static final Pin R_RIGHT = RaspiPin.GPIO_03;
    public static final Pin R_BUTTON = RaspiPin.GPIO_01;
    public static final Pin EXIT = RaspiPin.GPIO_04;
    
}
