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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import listeners.KeyListener;

/**

 @author rich
 */
public class Game extends AnimationTimer {

    private double changeX;
    private double changeY;
    private final double ballSpeed = 10;
    private int ballX;
    private int ballY;
    private int leftY;
    private int rightY;
    private boolean started = false;
    private boolean firstMenu = true;
    private boolean firstWin = true;
    private long winTime;
    private final double winTimeout = 1500000000.0;
    private int menuSelection = 1;

    private final int paddleHeight = 70;
    private final int paddleWidth = 10;

    private final int ballHeight = 10;
    private final int ballWidth = 10;

    private final double paddleSpeed = 8;

    public GameState state = GameState.MENU;

    int players = 1;

    KeyListener keyboard = new KeyListener();
    Rectangle leftPaddle = new Rectangle(paddleWidth, paddleHeight, Color.WHITE);
    Rectangle rightPaddle = new Rectangle(paddleWidth, paddleHeight, Color.WHITE);
    Rectangle ball = new Rectangle(ballWidth, ballHeight, Color.WHITE);

    Text title = new Text();
    Text playerl = new Text();
    Text playerr = new Text();
    Rectangle selector = new Rectangle(10, 10, Color.WHITE);

    Text win = new Text();

    List<Node> menuNodes = new ArrayList<>();
    List<Node> runningNodes = new ArrayList<>();
    List<Node> winNodes = new ArrayList<>();
    
    SoundPlayer soundplayer = new SoundPlayer();
    
    public void init() {
        menuNodes.add(playerl);
        menuNodes.add(playerr);
        menuNodes.add(selector);
        menuNodes.add(title);
        runningNodes.add(leftPaddle);
        runningNodes.add(rightPaddle);
        runningNodes.add(ball);
        winNodes.add(win);

        Pong.root.getChildren().addAll(menuNodes);
        Pong.root.getChildren().addAll(runningNodes);
        Pong.root.getChildren().addAll(winNodes);
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
                    System.out.println("Menu Activated");

                    title = formatText(0, -50, 100, "Pong", title);
                    playerl = formatText(0, 50, 20, "1 Player", playerl);
                    playerr = formatText(0, 70, 20, "2 Player", playerr);

                    setVisibility(runningNodes, false);
                    setVisibility(winNodes, false);
                    setVisibility(menuNodes, true);

                    firstMenu = false;
                }

                if (keyboard.isDown("Up")) {
                    menuSelection = 1;
                    System.out.println("Setting Players to " + menuSelection);

                }

                if (keyboard.isDown("Down")) {
                    menuSelection = 2;
                    System.out.println("Setting Players to " + menuSelection);
                }

                if (keyboard.isDown("Enter")) {
                    players = menuSelection;
                    state = GameState.RUNNING;
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

            case RUNNING:

                if (!started) {

                    System.out.println("Game Started With " + players + " Players");

                    setVisibility(runningNodes, true);
                    setVisibility(winNodes, false);
                    setVisibility(menuNodes, false);

                    rightY = (int) (Pong.scene.getHeight() / 2);
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

                    changeY = (Math.random() * .24 - 0.12);
                    ballX = (int) (Pong.scene.getWidth() / 2);
                    ballY = rightY;
                    leftY = rightY;

                    started = true;
                }

                // Set posistion of paddles
                // 1 Player Input
                if (players == 1) {
                    if (keyboard.isDown("Up")) {
                        leftY -= paddleSpeed;
                    }

                    if (keyboard.isDown("Down")) {
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
                    if (keyboard.isDown("Up")) {
                        leftY -= paddleSpeed;
                    }

                    if (keyboard.isDown("Down")) {
                        leftY += paddleSpeed;
                    }

                    if (keyboard.isDown("W")) {
                        rightY -= paddleSpeed;
                    }

                    if (keyboard.isDown("S")) {
                        rightY += paddleSpeed;
                    }
                }

                //Set ball position
                ballX += (changeX * ballSpeed);
                ballY += (changeY * ballSpeed);

                // Check if ball is at left paddle, bounce if within paddle, end game if not
                if (ballX <= 40) {
                    if (ballY >= leftY - (paddleHeight / 2 + ballHeight / 2) && ballY <= leftY + (paddleHeight / 2 + ballHeight / 2)) {
                        playClip();
                        changeX = (Math.abs(changeX));
                        changeY = -((double) leftY - (double) ballY) / ((double) paddleHeight / 2);
                    } else {
                        firstWin = true;
                        state = GameState.WINRIGHT;
                    }

                } else // Check if ball is at right paddle, bounce if within paddle, end game if not
                if (ballX >= Pong.scene.getWidth() - 30) {
                    if (ballY >= rightY - (paddleHeight / 2 + ballHeight / 2) && ballY <= rightY + (paddleHeight / 2 + ballHeight / 2)) {
                        playClip();
                        changeX = -(Math.abs(changeX));
                        changeY = -((double) rightY - (double) ballY) / ((double) paddleHeight / 2);
                    } else {
                        firstWin = true;
                        System.out.println("Left Wins");
                        state = GameState.WINLEFT;
                    }
                } else // Make ball bounce from top
                if (ballY <= ballHeight / 2) {
                    playClip();
                    changeY = (Math.abs(changeY));
                }

                // Make ball bounce from bottom
                if (ballY >= Pong.scene.getHeight() - ballHeight / 2) {
                    playClip();
                    changeY = -(Math.abs(changeY));
                }

                //Check of right paddle is too low
                if (rightY < paddleHeight / 2) {
                    rightY = paddleHeight / 2;
                }

                //Check of right paddle is too high
                if (rightY > Pong.scene.getHeight() - paddleHeight / 2) {
                    rightY = (int) (Pong.scene.getHeight() - paddleHeight / 2);
                }

                //Check of left paddle is too low
                if (leftY < paddleHeight / 2) {
                    leftY = paddleHeight / 2;
                }

                //Check of left paddle is too high
                if (leftY > Pong.scene.getHeight() - paddleHeight / 2) {
                    leftY = (int) (Pong.scene.getHeight() - paddleHeight / 2);
                }

                //Set position of the ball onscreen
                ball.setX(ballX - 5);
                ball.setY(ballY - 5);

                //Set left paddle position onscreen
                leftPaddle.setX(25);
                leftPaddle.setY(leftY - paddleHeight / 2);

                //Set right paddle position onscreen
                rightPaddle.setX(Pong.scene.getWidth() - 25);
                rightPaddle.setY(rightY - paddleHeight / 2);

                System.out.println(changeX + ", " + changeY);

                return;

            case WINLEFT:
                started = false;
                if (firstWin) {
                    System.out.println("Win Screen Activated, Left Won");

                    if (players == 1) {
                        formatText(0, 0, 100, "You Win!", win);
                    }

                    if (players == 2) {
                        formatText(0, 0, 75, "Player 1 Wins!", win);
                    }

                    setVisibility(runningNodes, false);
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
                    System.out.println("Win Screen Activated, Right Won");

                    if (players == 1) {
                        formatText(0, 0, 100, "You Lose", win);
                    }

                    if (players == 2) {
                        formatText(0, 0, 75, "Player 2 Wins!", win);
                    }

                    setVisibility(runningNodes, false);
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
        double tx = (Pong.scene.getWidth() / 2 + x) - (s.length() / 2) * ((charHeight / 3) * 2);
        double ty = (Pong.scene.getHeight() / 2 + y) + (charHeight / 2);
        text.setText(s);
        text.setFill(Color.WHITE);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Courier New", FontWeight.THIN, charHeight));
        text.setX(tx);
        text.setY(ty);
        return text;
    }

    private void playClip() {
        System.out.println("Beeping");
        (new Thread(new SoundPlayer())).start();
    }

}
