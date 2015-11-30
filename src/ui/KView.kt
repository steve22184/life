package life.ui

import life.core.Board
import life.core.Liveness
import life.core.iterator
import java.awt.Color
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent

/**
 * @author abreslav 
 */

class KLifeView(val board: Board): JComponent() {
    private val cellSize = 20;

    init {
        board.addListener(this) { repaint() }

        addMouseListener(object : MouseAdapter() {
            public override fun mousePressed(e: MouseEvent) {
                val x = e.getX() / cellSize
                val y = e.getY() / cellSize
                board[x, y] = board[x, y].invert()
            }
        })
//        addMousePressed { }
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        for ((x, y) in board) {
            g.color = if (board[x, y] == Liveness.LIVE) Color.RED else Color.WHITE
            g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize)
            g.color = Color.BLACK
            g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize)
        }
    }
}
