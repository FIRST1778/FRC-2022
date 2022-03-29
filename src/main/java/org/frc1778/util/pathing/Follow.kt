package org.frc1778.util.pathing

import edu.wpi.first.math.trajectory.Trajectory
import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.subsystems.drive.Drive
import kotlin.properties.Delegates

class Follow(private val trajectory: Trajectory): PathSegment() {
    override var timeToComplete by Delegates.notNull<Double>()
    override fun execute(timer: Timer): Boolean {
        return if(timer.get() < timeToComplete) {
            Drive.followTrajectory(trajectory)
            false
        } else {
            Drive.stop()
            true
        }
    }

    override fun initialize(cumulativeTime: Double) {
        timeToComplete = trajectory.totalTimeSeconds
    }


}