package org.frc1778.util.pathing

abstract class Event: PathSegment() {
    override var timeToComplete: Double = 0.0
    override var endTime: Double = 0.0
}