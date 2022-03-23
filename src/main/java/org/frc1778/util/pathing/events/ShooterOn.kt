package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.shooter.Shooter
import org.frc1778.util.pathing.Event

/**
 * Turns the shooter on
 */

object ShooterOn: Event() {
    override fun execute(timer: Timer): Boolean {
        Shooter.shoot()
        return true
    }
}