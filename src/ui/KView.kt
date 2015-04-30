package life.ui

import life.core.*
import javax.swing.JComponent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.Graphics
import life.core.Liveness
import java.awt.Color

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
        super<JComponent>.paintComponent(g)
        for ((x, y) in board) {
            g.setColor(if (board[x, y] == Liveness.LIVE) Color.RED else Color.WHITE)
            g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize)
            g.setColor(Color.BLACK)
            g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize)
        }
    }
}
