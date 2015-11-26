package life.ui

import life.core.Liveness
import life.core.ToroidalBoard
import life.core.iterator
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.JFrame

fun main(args: Array<String>) {
    val frame = JFrame()
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

    val board = ToroidalBoard(25, 24)
    val view = KLifeView(board)

    frame.add(view)
    frame.setSize(500, 500)

    var go = false
    frame.addKeyListener(
            object : KeyAdapter() {
                public override fun keyPressed(e: KeyEvent) {
                    when (e.getKeyChar()) {
                        ' ' -> go = !go
                        'c' -> {
                            for ((x, y) in board) {
                                board[x, y] = Liveness.DEAD
                            }
                        }
                        else -> {}
                    }
                }
            }
    )

    val timer = Timer()
    timer.schedule(
            object : TimerTask() {
                public override fun run() {
                    if (go) {
                        board.step()
                    }
                }
            },
            500,
            300
    )

    frame.setVisible(true)
}

