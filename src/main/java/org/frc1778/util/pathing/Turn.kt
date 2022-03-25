package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import kotlin.math.abs
import kotlin.properties.Delegates

/**
 * Turn in place a certain number of degrees. Negative turns left, positive turns right
 *
 * @property angle
 * @constructor Create turn for angle degrees
 */
class Turn(private val angle: SIUnit<Radian>) : PathSegment() {


    override var timeToComplete by Delegates.notNull<Double>()

    override fun execute(timer: Timer): Boolean {
        return if(timer.get() < timeToComplete) {
            if(angle.value > 0) {
                Drive.Autonomous.rotateRight()
            } else if(angle.value < 0) {
                Drive.Autonomous.rotateLeft()
            }
            false
        } else {
            Drive.stop()
            true
        }
    }

    override fun initialize(cumulativeTime: Double) {
        timeToComplete = cumulativeTime + (abs(angle.value) / Constants.Drive.rotSpeed.value)
    }
}