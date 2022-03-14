package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.util.pathing.Event

class LoaderOff: Event() {
    override fun execute(timer: Timer): Boolean {
        Loader.runLoader(0.0)
        return true
    }
}