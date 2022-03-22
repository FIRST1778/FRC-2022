package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.mathematics.units.inFeet
import kotlin.math.*
import kotlin.properties.Delegates






class Line(private var length: Double, private var angle: SIUnit<Radian>, cumulativeTime: Double) : PathSegment(){

    private var turn: Turn
    var turnComplete = false

    init {
        turn = Turn(angle, cumulativeTime)


    }

    override var timeToComplete: Double = turn.timeToComplete + (abs(length) / Constants.Drive.speed.value)
//    constructor(x: Double, y: Double) : this(sqrt((x - 0.0).pow(2) + (y - 0.0).pow(2)), SIUnit<Radian>(atan(x/y)))



    override fun execute(timer: Timer): Boolean {
        return if(!turnComplete) {
            if(turn.execute(timer)) turnComplete = true
            false
        } else if(timer.get() <= timeToComplete) {
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