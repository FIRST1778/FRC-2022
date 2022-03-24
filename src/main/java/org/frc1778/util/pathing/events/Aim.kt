package org.frc1778.util.pathing.events

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.util.pathing.Event
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.tan

/**
 * Event that aims at target using limelight.
 */

object Aim : Event() {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val tx = limeTable["tx"]
    private val ty = limeTable["ty"]

    override fun execute(timer: Timer): Boolean {
        val distance = ((104.0 - 23.5) / (tan((33.322 + ty.getDouble(0.0)) / 57.296)))
        return if(tx.getDouble(0.0) > if(distance > 135) 1.95 else 2.7) {
            Drive.curvatureDrive(0.0, if(tx.getDouble(0.0) > if(distance > 135) 3.75 else 4.5) 0.085 else .02, true)
            false
        } else if(tx.getDouble(0.0) < if(distance > 135) 1.55 else 2.3) {
            Drive.curvatureDrive(0.0, if(tx.getDouble(0.0) < if(distance > 135) 1.55 else -.5) -0.085 else -.02, true)
            false
        } else  {
            Drive.stop()
            true
        }
    }
}