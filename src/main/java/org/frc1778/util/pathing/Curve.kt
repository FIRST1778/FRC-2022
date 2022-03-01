package org.frc1778.util.pathing

import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.derived.Radian
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Curve(private val x: Double, private val y: Double, endAngle: SIUnit<Radian>) {
    private var points: ArrayList<Pair<Double, Double>> = ArrayList()
    private var lines: ArrayList<Line> = ArrayList()

    init {
        var t = 0.0
        while(t <= 1.0) {
            //                                                             P3                   P4
            val tempX: Double = (3 * (1-t) * t.pow(2) * (x * sin(endAngle.value))) + (t.pow(3) * x)
            //                          P2                                  P3                          P4
            val tempY: Double = (3 * (1-t).pow(2) * t * 1) + (3 * (1-t) * t.pow(2) * y * cos(endAngle.value)) + (t.pow(3) * y)

            points.add(Pair(tempX, tempY))

            t += 0.05
        }

        for(i in 0 until points.size-1) {
            val (x1, y1) = points[i]
            val (x2, y2) = points[i + 1]
            lines.add(Line(x1, y1, x2, y2))
        }
    }

}