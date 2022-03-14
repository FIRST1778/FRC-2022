package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.util.pathing.Event

class Wait(time: Double, currTime: Double): Event() {
    override var timeToComplete = currTime + time
    override fun execute(timer: Timer): Boolean {
        return timer.get() >= timeToComplete
    }
}