package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import kotlin.math.abs
import kotlin.properties.Delegates


/**
 * Line movement of length in meters at angle direction in radians
 *
 * @property length
 * @property angle
 * @constructor Create a line of length meters at angle direction in radians
 */
class Line(private var length: Double, private var angle: SIUnit<Radian>) : PathSegment(){

    private lateinit var turn: Turn
    private var turnComplete = false

    override var timeToComplete by Delegates.notNull<Double>()

    override fun execute(timer: Timer): Boolean {
        return if(!turnComplete) {
            if(turn.execute(timer)) turnComplete = true
            false
        } else if(timer.get() <= timeToComplete) {
            if(length > 0) {
                Drive.Autonomous.driveForward()
            } else if(length < 0) {
                Drive.Autonomous.driveBackwards()
            }
            false
        } else {
            Drive.stop()
            true
        }






    }

    override fun initialize(cumulativeTime: Double) {
        turn = Turn(angle)
        turn.initialize(cumulativeTime)
        turnComplete = false
        timeToComplete = turn.timeToComplete + (abs(length) / Constants.Drive.speed.value)
    }



}