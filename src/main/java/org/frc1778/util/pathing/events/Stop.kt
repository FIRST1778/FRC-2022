package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.util.pathing.Event

class Stop: Event() {
    override fun execute(timer: Timer): Boolean {
        Drive.stop()
        return true
    }
}