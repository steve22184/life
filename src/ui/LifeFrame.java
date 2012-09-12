package ui;

import life.core.Board;
import life.core.Liveness;
import life.core.ToroidalBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

/**
 * @author abreslav
 */
public class LifeFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Life");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final Board board = new ToroidalBoard(25, 24);
        final LifeView lifeView = new LifeView(board);
        frame.add(lifeView, BorderLayout.CENTER);

        frame.setSize(500, 500);

        final boolean[] go = {false};
        frame.addKeyListener(
                new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyChar()) {
                            case ' ':
                                go[0] = !go[0];
                                break;
                            case 'c':
                                for (int y = 0; y < board.getHeight(); y++) {
                                    for (int x = 0; x < board.getWidth(); x++) {
                                        board.set(x, y, Liveness.DEAD);
                                    }
                                }
                        }
                    }
                }
        );

        java.util.Timer timer = new java.util.Timer("Animate");
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (go[0]) {
                            board.step();
                        }
                    }
                },
                500, 300
        );

        frame.setVisible(true);
    }

}
