package org.frc1778.util.pathing.events

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.util.pathing.Event
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.tan
import kotlin.properties.Delegates

/**
 * Event that aims at target using limelight.
 */

object Aim : Event() {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val tx = limeTable["tx"]
    private val ty = limeTable["ty"]
    private var endTime by Delegates.notNull<Double>()

    override fun execute(timer: Timer): Boolean {
        val distance = ((104.0 - 23.5) / (tan((33.322 + ty.getDouble(0.0)) / 57.296)))
        return if(timer.get() < endTime) {
                    if(tx.getDouble(0.0) > if(distance > 135) 2.15 else 2.7) {
                        Drive.curvatureDrive(0.0, if(tx.getDouble(0.0) > if(distance > 135) 3.75 else 4.5) 0.1 else .03, true)
                        false
                    } else if(tx.getDouble(0.0) < if(distance > 135) 1.35 else 2.3) {
                        Drive.curvatureDrive(0.0, if(tx.getDouble(0.0) < if(distance > 135) 1.55 else -.5) -0.1 else -.03, true)
                        false
                    } else {
                        Drive.stop()
                        true
                    }
            } else {
                Drive.stop()
                true
        }
    }

    override fun initialize(cumulativeTime: Double) {
        endTime = cumulativeTime + 1.15
    }
}