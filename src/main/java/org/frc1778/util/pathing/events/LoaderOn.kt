package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.util.pathing.Event
import org.frc1778.util.pathing.Path

/**
 * Turns the loader on
 */

object LoaderOn: Event() {
    override fun execute(timer: Timer): Boolean {
        Loader.runLoader(.15)
        Path.loaderOn = true
        return true
    }
}