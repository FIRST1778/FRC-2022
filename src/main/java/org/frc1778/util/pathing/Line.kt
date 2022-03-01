package org.frc1778.util.pathing

import org.frc1778.robot.Constants
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import kotlin.math.pow
import kotlin.math.sqrt
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.mathematics.units.inFeet
import kotlin.math.atan
import kotlin.math.cos


class Line(private var length: Double, private var angle: SIUnit<Radian>) : PathSegment(){
//    constructor(x: Double, y: Double) : this(sqrt((x - 0.0).pow(2) + (y - 0.0).pow(2)), SIUnit<Radian>(atan(x/y)))
    constructor(x1: Double, y1: Double, x2: Double, y2: Double): this(sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2)), SIUnit<Radian>(atan((x2-x1)/(y2-y1))))

    override fun execute() {
        val leftDistance = length.pow(2) + (Constants.Drive.TRACK_WIDTH.inFeet()/2).pow(2) - (2 * (sqrt(length.pow(2) + (Constants.Drive.TRACK_WIDTH.inFeet()/2).pow(2))) * (Constants.Drive.TRACK_WIDTH.inFeet()/2) * cos(90 + atan((Constants.Drive.TRACK_WIDTH.inFeet()/2)/length)))
        val rightDistance = length.pow(2) + (Constants.Drive.TRACK_WIDTH.inFeet()/2).pow(2) - (2 * (sqrt(length.pow(2) + (Constants.Drive.TRACK_WIDTH.inFeet()/2).pow(2))) * (Constants.Drive.TRACK_WIDTH.inFeet()/2) * cos(90 - atan((Constants.Drive.TRACK_WIDTH.inFeet()/2)/length)))




    }


}