/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import listeners.Input;
import listeners.PinListener;

/**

 @author rich
 */
public class Game extends AnimationTimer {

    private final static double TOP_OFFSET = 40;
    private final static double BOTTOM_OFFSET = 40;
    private final static double RIGHT_OFFSET = 40;
    private final static double LEFT_OFFSET = 40;

    private static double TOP;
    private static double BOTTOM;
    private static double RIGHT;
    private static double LEFT;

    private double changeX;
    private double changeY;
    private final double ballSpeed = 8;
    private int ballX;
    private int ballY;
    private int leftY;
    private int rightY;
    private int leftX;
    private int rightX;
    private boolean started = false;
    private boolean firstMenu = true;
    private boolean firstWin = true;
    private boolean firstStart = true;
    private boolean firstOne = true;
    private boolean firstTwo = true;
    private boolean firstThree = true;

    private long winTime;
    private long startTime;
    private final double winTimeout = 1500000000.0;
    private int menuSelection = 1;

    private final int paddleHeight = 70;
    private final int paddleWidth = 10;

    private final int ballHeight = 10;
    private final int ballWidth = 10;

    private final double paddleSpeed = 8;

    public GameState state = GameState.MENU;

    int players = 1;

    PinListener keyboard = new PinListener();
    Rectangle leftPaddle = new Rectangle(paddleWidth, paddleHeight, Color.WHITE);
    Rectangle rightPaddle = new Rectangle(paddleWidth, paddleHeight, Color.WHITE);
    Rectangle ball = new Rectangle(ballWidth, ballHeight, Color.WHITE);

    Text title = new Text();
    Text name = new Text();
    Text playerl = new Text();
    Text playerr = new Text();
    Rectangle selector = new Rectangle(10, 10, Color.WHITE);

    Text win = new Text();

    List<Node> menuNodes = new ArrayList<>();
    List<Node> startingNodes = new ArrayList<>();
    List<Node> runningNodes = new ArrayList<>();
    List<Node> winNodes = new ArrayList<>();

    SoundPlayer soundplayer = new SoundPlayer();
    

    public void init() {
        menuNodes.add(playerl);
        menuNodes.add(playerr);
        menuNodes.add(selector);
        menuNodes.add(title);
        menuNodes.add(name);

        runningNodes.add(leftPaddle);
        runningNodes.add(rightPaddle);
        runningNodes.add(ball);
        winNodes.add(win);

        Pong.root.getChildren().addAll(menuNodes);
        Pong.root.getChildren().addAll(runningNodes);
        Pong.root.getChildren().addAll(winNodes);
        
        System.out.println("Initing PinLister");
        PinListener.init();
        
        
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void handle(long now) {

        switch (state) {

            case MENU:
                if (firstMenu) {
                    Printer.println("Menu Activated");

                    playClip();

                    title = formatText(0, -100, 100, "Pong", title);
                    name = formatText(0, -20, 30, "Timothy Hollabaugh", name);
                    playerl = formatText(0, 50, 20, "1 Player", playerl);
                    playerr = formatText(0, 70, 20, "2 Player", playerr);

                    setVisibility(runningNodes, false);
                    setVisibility(startingNodes, false);
                    setVisibility(winNodes, false);
                    setVisibility(menuNodes, true);

                    firstMenu = false;
                }

                if (keyboard.isDown(Input.L_UP)) {
                    menuSelection = 1;
                    System.out.println("Setting Players to " + menuSelection);

                }

                if (keyboard.isDown(Input.L_DOWN)) {
                    menuSelection = 2;
                    System.out.println("Setting Players to " + menuSelection);
                }

                if (keyboard.isDown(Input.L_BUTTON)) {
                    players = menuSelection;
                    playClip();
                    state = GameState.STARTING;
                    firstMenu = true;
                }

                switch (menuSelection) {
                    case 1:
                        selector.setX((Pong.scene.getWidth() / 2) - (playerl.getText().length() / 2) * ((playerl.getFont().getSize() / 3) * 2) - 20);
                        selector.setY(playerl.getY() - (playerl.getFont().getSize() / 2));
                        return;

                    case 2:
                        selector.setX((Pong.scene.getWidth() / 2) - (playerr.getText().length() / 2) * ((playerr.getFont().getSize() / 3) * 2) - 20);
                        selector.setY(playerr.getY() - (playerr.getFont().getSize() / 2));
                        return;
                }

                return;

            case STARTING:
                if (firstStart) {

                    TOP = TOP_OFFSET;
                    BOTTOM = Pong.scene.getHeight() - BOTTOM_OFFSET;
                    RIGHT = Pong.scene.getWidth() - RIGHT_OFFSET;
                    LEFT = LEFT_OFFSET;

                    System.out.println(Pong.scene.getWidth());
                    System.out.println(Pong.scene.getHeight());

                    System.out.println("Virtual TOP: " + TOP);
                    System.out.println("Virtual BOTTOM: " + BOTTOM);
                    System.out.println("Virtual RIGHT: " + RIGHT);
                    System.out.println("Virtual LEFT: " + LEFT);

                    System.out.println("Game Starting With " + players + " Players");

                    rightY = (int) (Pong.scene.getHeight() / 2);
                    leftY = rightY;
                    rightX = (int) (RIGHT - 25);
                    leftX = (int) (LEFT + 25);
                    ballX = (int) (Pong.scene.getWidth() / 2);
                    ballY = rightY;

                    if (players == 2) {
                        double rand = Math.random();
                        if (rand < .5) {
                            changeX = -1;
                        }

                        if (rand >= .5) {
                            changeX = 1;
                        }
                    } else {
                        changeX = 1;
                    }

                    double rand = Math.random();
                    if (rand < .5) {
                        changeY = -0.15;
                    }

                    if (rand >= .5) {
                        changeY = 0.15;
                    }

                    setVisibility(runningNodes, true);
                    setVisibility(startingNodes, true);
                    setVisibility(winNodes, false);
                    setVisibility(menuNodes, false);

                    startTime = now;
                    firstStart = false;

                }

                if (now - startTime <= 1000000000.0) {
                    if (firstOne) {
                        playClip();
                        Printer.println("Game starting in: 3");
                        firstOne = false;
                    }
                } else if (now - startTime <= 2000000000.0) {
                    if (firstTwo) {
                        playClip();
                        Printer.println("Game starting in: 2");
                        firstTwo = false;
                    }
                } else if (now - startTime <= 3000000000.0) {
                    if (firstThree) {
                        playClip();
                        Printer.println("Game starting in: 1");
                        firstThree = false;
                    }
                } else {
                    playClip();
                    state = GameState.RUNNING;
                    firstStart = true;
                    firstOne = true;
                    firstTwo = true;
                    firstThree = true;
                }

                //Set position of the ball onscreen
                ball.setX(ballX - 5);
                ball.setY(ballY - 5);

                //Set left paddle position onscreen
                leftPaddle.setX(leftX);
                leftPaddle.setY(leftY - paddleHeight / 2);

                //Set right paddle position onscreen
                rightPaddle.setX(rightX);
                rightPaddle.setY(rightY - paddleHeight / 2);

                return;

            case RUNNING:

                if (!started) {

                    setVisibility(startingNodes, false);
                    started = true;
                }

                // Set posistion of paddles
                // 1 Player Input
                if (players == 1) {
                    if (keyboard.isDown(Input.L_UP)) {
                        leftY -= paddleSpeed;
                    }

                    if (keyboard.isDown(Input.L_DOWN)) {
                        leftY += paddleSpeed;
                    }

                    if (changeX > 0) {
                        if (ballX > Pong.scene.getWidth() / 2) {
                            if (ballY > rightY + (paddleHeight / 2 - 10)) {
                                rightY += paddleSpeed;
                            }

                            if (ballY < rightY - (paddleHeight / 2 - 10)) {
                                rightY -= paddleSpeed;
                            }
                        } else {
                            if (ballY > rightY + (paddleHeight / 2 - 10)) {
                                rightY += paddleSpeed * .75;
                            }

                            if (ballY < rightY - (paddleHeight / 2 - 10)) {
                                rightY -= paddleSpeed * .75;
                            }
                        }
                    }
                }

                // 2 Player Input
                if (players == 2) {
                    if (keyboard.isDown(Input.L_UP)) {
                        leftY -= paddleSpeed;
                    }

                    if (keyboard.isDown(Input.L_DOWN)) {
                        leftY += paddleSpeed;
                    }

                    if (keyboard.isDown(Input.R_UP)) {
                        rightY -= paddleSpeed;
                    }

                    if (keyboard.isDown(Input.R_DOWN)) {
                        rightY += paddleSpeed;
                    }
                }

                //Set ball position
                ballX += (changeX * ballSpeed);
                ballY += (changeY * ballSpeed);

                // Check if ball is at left paddle, bounce if within paddle, end game if not
                if (ballX <= LEFT + 40) {
                    if (ballY >= leftY - (paddleHeight / 2 + ballHeight / 2) && ballY <= leftY + (paddleHeight / 2 + ballHeight / 2)) {
                        playClip();
                        changeX = (Math.abs(changeX));
                        changeY = -((double) leftY - (double) ballY) / ((double) paddleHeight / 2);
                    } else {
                        firstWin = true;
                        state = GameState.WINRIGHT;
                    }

                } else // Check if ball is at right paddle, bounce if within paddle, end game if not
                if (ballX >= RIGHT - 30) {
                    if (ballY >= rightY - (paddleHeight / 2 + ballHeight / 2) && ballY <= rightY + (paddleHeight / 2 + ballHeight / 2)) {
                        playClip();
                        changeX = -(Math.abs(changeX));
                        changeY = -((double) rightY - (double) ballY) / ((double) paddleHeight / 2);
                    } else {
                        firstWin = true;
                        System.out.println("Left Wins");
                        state = GameState.WINLEFT;
                    }
                }

                // Make ball bounce from top
                if (ballY <= TOP - (ballHeight / 2)) {
                    playClip();
                    changeY = (Math.abs(changeY));
                }

                // Make ball bounce from bottom
                if (ballY >= BOTTOM - (ballHeight / 2)) {
                    playClip();
                    changeY = -(Math.abs(changeY));
                }

                //Check of right paddle is too low
                if (rightY < TOP + (paddleHeight / 2)) {
                    rightY = (int) (TOP + (paddleHeight / 2));
                }

                //Check of right paddle is too high
                if (rightY > BOTTOM - (paddleHeight / 2)) {
                    rightY = (int) (BOTTOM - paddleHeight / 2);
                }

                //Check of left paddle is too low
                if (leftY < TOP + (paddleHeight / 2)) {
                    leftY = (int) (TOP + (paddleHeight / 2));
                }

                //Check of left paddle is too high
                if (leftY > BOTTOM - (paddleHeight / 2)) {
                    leftY = (int) (BOTTOM - (paddleHeight / 2));
                }

                //Set position of the ball onscreen
                ball.setX(ballX - 5);
                ball.setY(ballY - 5);

                //Set left paddle position onscreen
                leftPaddle.setX(leftX);
                leftPaddle.setY(leftY - paddleHeight / 2);

                //Set right paddle position onscreen
                rightPaddle.setX(rightX);
                rightPaddle.setY(rightY - paddleHeight / 2);

                //System.out.println("Setting ball to: " + ballX + ", " + ballY);
                //System.out.println("Setting left to: " + leftX + ", " + leftY);
                //System.out.println("Setting right to: " + rightX + ", " + rightY);
                //System.out.println(changeX + ", " + changeY);
                return;

            case WINLEFT:
                started = false;
                if (firstWin) {
                    Printer.println("Win Screen Activated, Left Won");
                    //playClip();

                    if (players == 1) {
                        formatText(0, 0, 100, "You Win!", win);
                    }

                    if (players == 2) {
                        formatText(0, 0, 75, "Player 1 Wins!", win);
                    }

                    setVisibility(runningNodes, false);
                    setVisibility(startingNodes, false);
                    setVisibility(winNodes, true);
                    setVisibility(menuNodes, false);

                    winTime = now;

                    firstWin = false;
                }

                if (now - winTime > winTimeout) {
                    state = GameState.MENU;
                    firstMenu = true;
                }

                return;

            case WINRIGHT:
                started = false;
                if (firstWin) {
                    Printer.println("Win Screen Activated, Right Won");
                    //playClip();

                    if (players == 1) {
                        formatText(0, 0, 100, "You Lose", win);
                    }

                    if (players == 2) {
                        formatText(0, 0, 75, "Player 2 Wins!", win);
                    }

                    setVisibility(runningNodes, false);
                    setVisibility(startingNodes, false);
                    setVisibility(winNodes, true);
                    setVisibility(menuNodes, false);

                    winTime = now;

                    firstWin = false;
                }

                if (now - winTime > winTimeout) {
                    state = GameState.MENU;
                    firstMenu = true;
                }

        }

    }

    void setVisibility(Collection c, boolean visibility) {
        Iterator<Node> it = c.iterator();
        for (int i = 0; i < c.size(); i++) {
            Node n = it.next();
            n.setVisible(visibility);
        }
    }

    Text formatText(int x, int y, int charHeight, String s, Text text) {
        double tx = (Pong.scene.getWidth() / 2 + x) - (s.length() / 2) * ((charHeight / 2));
        double ty = (Pong.scene.getHeight() / 2 + y) + (charHeight / 2);
        text.setText(s);
        text.setFill(Color.WHITE);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.loadFont("file:pong/UbuntuMono-R.ttf", charHeight));
        text.setX(tx);
        text.setY(ty);
        return text;
    }

    private void playClip() {
        Printer.println("Beeping");
        (new Thread(new SoundPlayer())).start();
    }

}
