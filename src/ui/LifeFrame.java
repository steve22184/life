package ui;

import jet.Function0;
import jet.Tuple0;
import life.core.Board;
import life.core.Liveness;

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
        JFrame life = new JFrame("Life");
        life.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final Board board = new Board(25, 24);
        final LifeView lifeView = new LifeView(board);
        life.getContentPane().add(lifeView, BorderLayout.CENTER);

        life.setSize(500, 500);

        life.addKeyListener(
                new KeyAdapter() {
                    boolean go = false;

                    {
                        java.util.Timer timer = new java.util.Timer("Animate");
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (go) {
                                            board.step();
                                        }
                                    }
                                },
                                500, 300
                        );
                    }

                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyChar()) {
                            case ' ':
                                go = !go;
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



        life.setVisible(true);
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
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics;
            for (int y = 0; y < board.getHeight(); y++) {
                for (int x = 0; x < board.getWidth(); x++) {
                    if (board.get(x, y) == Liveness.LIVE) {
                        g.setColor(Color.RED);
                    }
                    else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}
