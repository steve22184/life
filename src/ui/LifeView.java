package ui;

import kotlin.Function0;
import kotlin.Unit;
import life.core.Board;
import life.core.Liveness;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* @author abreslav
*/
class LifeView extends JComponent {
    private final int cellSize = 20;
    private final Board board;

    public LifeView(Board _board) {
        this.board = _board;
        board.addListener(this, new Function0<Unit>() {
            @Override
            public Unit invoke() {
                repaint();
                return Unit.INSTANCE$;
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
