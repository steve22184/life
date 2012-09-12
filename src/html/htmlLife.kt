package html

import life.core.Board
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JLabel
import life.core.Liveness
import life.core.ToroidalBoard

fun render(board: Board) : String {
    return html {

        table {
            tr {
                td { bold("Title") }
                td {
                    text("Life in HTML")
                }
            }
            tr {
                td { bold("Author") }
                td {
                    text("Andrey")
                }
            }
            tr {
                td { bold("Description") }
                td {
                    text("Display steps of Life evolution in HTML")
                }
            }
        }


//        for (i in 1..10) {
//
//            table {
//
//                for (y in 0..board.height - 1) {
//                    tr {
//                        for (x in 0..board.width - 1) {
//                            td(bgcolor = getColor(board[x, y])) {
//                                text("*")
//                            }
//                        }
//                    }
//                }
//
//            }
//
//            board.step()
//        }


    }.toString()
}

fun getColor(l: Liveness) = when (l) {
    Liveness.LIVE -> "red"
    Liveness.DEAD -> "white"
}

fun main(args : Array<String>) {

    val board = ToroidalBoard(11, 11)
    board[5, 4] = Liveness.LIVE
    board[5, 5] = Liveness.LIVE
    board[5, 6] = Liveness.LIVE
    board[4, 5] = Liveness.LIVE
    board[6, 5] = Liveness.LIVE

    val frame = JFrame("Life")
    frame.setSize(800, 600)
    val scrollPane = JScrollPane(JLabel(
            render(board)
    ))
    frame.add(scrollPane)
    frame.show()
}
