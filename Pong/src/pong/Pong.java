/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pong;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import listeners.KeyListener;

/**
 *
 * @author rich
 */
public class Pong extends Application {
    
    Game game = new Game();
    KeyListener keyboard = new KeyListener();
    
    public static Font font = Font.getDefault();
    
    static Group root = new Group();
    static Scene scene = new Scene(root);
    
    
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        
        Printer.println("==========Pong Starting==========");
	//Set up root pane
	root.setCursor(Cursor.NONE);
        game.init();
        scene.setFill(Color.BLACK);
        scene.setOnKeyPressed(new KeyListener());
        scene.setOnKeyReleased(new KeyListener());
	scene.setCursor(Cursor.NONE);
        primaryStage.setScene(scene); 
	primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        game.start();
        primaryStage.show();
        Printer.println("==========Pong  Started==========");
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
