package ui;

import jet.Function0;
import jet.Tuple0;
import life.core.Board;
import life.core.Liveness;
import life.core.ToroidalBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private static class LifeView extends JComponent {
        private final int cellSize = 20;
        private final Board board;

        public LifeView(Board _board) {
            this.board = _board;
            board.addListener(this, new Function0<Tuple0>() {
                @Override
                public Tuple0 invoke() {
                    repaint();
                    return Tuple0.INSTANCE;
                }
            });

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int x = e.getX() / cellSize;
                    int y = e.getY() / cellSize;
                    board.set(x, y, board.get(x, y).invert());
                }
            });


        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int y = 0; y < board.getHeight(); y++) {
                for (int x = 0; x < board.getWidth(); x++) {
                    g.setColor(board.get(x, y) == Liveness.LIVE ? Color.RED : Color.WHITE);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}
