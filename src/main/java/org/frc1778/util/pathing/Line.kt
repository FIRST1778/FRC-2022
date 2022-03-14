package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.Constants
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import kotlin.math.pow
import kotlin.math.sqrt
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.mathematics.units.inFeet
import kotlin.math.atan
import kotlin.math.cos
import kotlin.properties.Delegates


var turnComplete = false
lateinit var turn: Turn

class Line(private var length: Double, private var angle: SIUnit<Radian>, cumulativeTime: Double) : PathSegment(){


    init {

        turn = Turn(angle, cumulativeTime)

    }

    override var timeToComplete: Double = cumulativeTime + turn.timeToComplete + (length / Constants.Drive.speed.value)
    override var endTime = cumulativeTime + timeToComplete
//    constructor(x: Double, y: Double) : this(sqrt((x - 0.0).pow(2) + (y - 0.0).pow(2)), SIUnit<Radian>(atan(x/y)))



    override fun execute(timer: Timer): Boolean {
        return if(!turnComplete) {
            if(turn.execute(timer)) turnComplete = true
            false
        } else if(timer.get() < timeToComplete) {
            if(length > 0) {
                Drive.driveForward()
            } else if(length < 0) {
                Drive.driveBackwards()
            }
            false
        } else {
            Drive.stop()
            true
        }






    }


}