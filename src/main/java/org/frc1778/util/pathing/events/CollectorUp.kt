package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.util.pathing.Event
import org.ghrobotics.lib.mathematics.units.SIUnit

/**
 * Puts the collector up
 */
object CollectorUp: Event() {
    override fun execute(timer: Timer): Boolean {
        Collector.deployMotor.setPosition(SIUnit(0.0))
        Collector.collectorDown = false
        return true
    }
}