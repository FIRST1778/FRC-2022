package org.frc1778.util.pathing

import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import kotlin.math.pow

class PathSegment(private var x: Double, private var y: Double, private var angle: SIUnit<Radian>) {
    private var points: ArrayList<Pair<Double, Double>> = ArrayList()
    private var lines: ArrayList<Line> = ArrayList()

    init {
        for (t in 0 until 1) {
            val tempX  = (3.0 * (1-t).toDouble().pow(2) * t.toDouble() * x) + (3.0 * (1-t).toDouble() * t.toDouble().pow(2) * x) + (t.toDouble().pow(3) * x)
            val tempY  = (3.0 * (1-t).toDouble().pow(2) * t.toDouble() * y) + (3.0 * (1-t).toDouble() * t.toDouble().pow(2) * y) + (t.toDouble().pow(3) * y)
            points.add(Pair(tempX, tempY))
        }

        for(i in 0 until points.size) {
           val (x,y) = points[i]
        }
    }
}