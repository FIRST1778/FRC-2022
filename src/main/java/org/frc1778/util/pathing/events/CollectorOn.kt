package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.util.pathing.Event

/**
 * Turns the collector on
 */

object CollectorOn: Event() {
    override fun execute(timer: Timer): Boolean {
        Collector.runCollector(.30)
        Loader.runMain(.20)
        return true
    }
}