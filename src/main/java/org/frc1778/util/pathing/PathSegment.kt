package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import kotlin.properties.Delegates


abstract class PathSegment() {
    abstract var timeToComplete: Double
    abstract fun execute(timer: Timer): Boolean
}