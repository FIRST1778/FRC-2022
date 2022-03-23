package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer


/**
 * Abstract class Path segment for use in autonomous
 *
 */
abstract class PathSegment() {
    abstract var timeToComplete: Double

    /**
     * Abstract function that runs each event or movement
     *
     * @param timer
     * @return event completion as boolean
     */
    abstract fun execute(timer: Timer): Boolean

    /**
     * Abstract function that initializes each Movement or Event
     *
     * @param cumulativeTime
     */
    abstract fun initialize(cumulativeTime: Double)
}