package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import kotlin.math.abs

class Turn(private val angle: SIUnit<Radian>, cumulativeTime: Double) : PathSegment() {


    override var timeToComplete = cumulativeTime + (abs(angle.value) / Constants.Drive.rotSpeed.value)

    override fun execute(timer: Timer): Boolean {
        return if(timer.get() < timeToComplete) {
            if(angle.value > 0) {
                Drive.rotateRight()
            } else if(angle.value < 0) {
                Drive.rotateLeft()
            }
            false
        } else {
            Drive.stop()
            true
        }
    }
}