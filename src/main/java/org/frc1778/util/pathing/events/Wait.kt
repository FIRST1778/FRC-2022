package org.frc1778.util.pathing.events

import edu.wpi.first.wpilibj.Timer
import org.frc1778.util.pathing.Event
import kotlin.properties.Delegates

/**
 * Wait
 *
 * @property time
 * @constructor Create a wait event for time amount of seconds
 */
class Wait(private var time: Double) : Event() {
    override var timeToComplete by Delegates.notNull<Double>()

    override fun execute(timer: Timer): Boolean {
        return timer.get() > timeToComplete
    }

    override fun initialize(cumulativeTime: Double) {
        timeToComplete = cumulativeTime + time
    }
}