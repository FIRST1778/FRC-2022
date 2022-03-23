package org.frc1778.util.pathing

/**
 * Events for operating subsystems during autonomous
 */
abstract class Event: PathSegment() {
    override var timeToComplete: Double = 0.0
    override fun initialize(cumulativeTime: Double) {}
}