/**
 * @author abreslav
 */
package life.core

import util.*
import life.core.Liveness.*
import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.IllegalFormatWidthException

open class Board(width: Int, height: Int) {
    private val matrix: MutableMatrix<Liveness> = MutableMatrixImpl(width, height) {x, y -> DEAD}
    private val onChange: MutableMap<Any, () -> Unit> = LinkedHashMap()

    val width: Int
        get() = matrix.width

    val height: Int
        get() = matrix.height

    fun get(x: Int, y: Int): Liveness = matrix[x, y]

    fun set(x: Int, y: Int, value: Liveness) {
        matrix[x, y] = value
        notifyListeners()
    }

    fun addListener(key: Any, callback: () -> Unit) {
        onChange[key] = callback
    }

    fun removeListener(key: Any) {
        onChange.remove(key)
    }

    fun step() {
        val tmp = matrix.toMutableMatrix()
        tmp.fill { x, y, v -> nextState(x, y)}
        matrix.copyFrom(tmp)
        notifyListeners()
    }

    private fun nextState(x: Int, y: Int): Liveness {
        return when(liveNeighbors(x, y)) {
            2 -> get(x, y)
            3 -> LIVE
            else -> DEAD
        }
    }

    private fun liveNeighbors(x: Int, y: Int): Int = neighbors(x, y).filter { p -> get(p.x, p.y) == LIVE}.size()
    // count()

    private fun neighbors(x: Int, y: Int): Collection<Point> {
        val result = ArrayList<Point>()
        for (dx in -1..1) {
            for (dy in -1..1) {
                if (dx != 0 || dy != 0) {
                    val n = cell(x + dx, y + dy)
                    if (n != null) {
                        result.add(n)
                    }
                }
            }
        }
        return result
    }

    protected open fun cell(x: Int, y: Int): Point? {
        if (x !in 0..width - 1 || y !in 0..height - 1) return null
        return Point(x, y)
    }

    private fun notifyListeners() {
        for (l in onChange.values()) {
            l()
        }
    }

}

class ToroidalBoard(width: Int, height: Int) : Board(width, height) {

    protected override fun cell(x: Int, y: Int): Point? {
        return Point(wrap(x, width), wrap(y, height))
    }

    private fun wrap(n: Int, size: Int): Int {
      return if (n < 0)
                size + (n % size)
             else
                n % size
    }
}

public fun Board.iterator(): Iterator<Point> = object : Iterator<Point> {
    var i = 0

    public override fun next(): Point {
        val result = Point(i % width, i / width)
        i++
        return result
    }

    public override fun hasNext(): Boolean {
        return i < width * height
    }
}

enum class Liveness {
    LIVE,
    DEAD;

    fun invert(): Liveness = when (this) {
        DEAD -> LIVE
        LIVE -> DEAD
    }
}

data class Point(val x: Int, val y: Int)