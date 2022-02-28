package org.frc1778.util.pathing

import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import kotlin.math.*


class Line(private var length: Double, private var angle: SIUnit<Radian>) {
    constructor(x: Double, y: Double) : this(sqrt((x - 0.0).pow(2) + (y - 0.0).pow(2)), SIUnit<Radian>(atan(x/y)))
    constructor(x1: Double, y1: Double, x2: Double, y2: Double): this(sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2)), SIUnit<Radian>(atan((x2-x1)/(y2-y1))))

}