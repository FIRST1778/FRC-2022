package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.util.pathing.Event

class LoaderOn: Event() {
    override fun execute(timer: Timer): Boolean {
        Loader.runLoader(.15)
        return true
    }
}