package org.frc1778.util.pathing

import edu.wpi.first.math.trajectory.Trajectory
import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.Robot
import org.frc1778.robot.subsystems.drive.Drive
import kotlin.properties.Delegates

class Follow(private val trajectory: Trajectory): PathSegment() {
    override var timeToComplete by Delegates.notNull<Double>()
    private val trajectoryCommand = Drive.followTrajectory(trajectory)
    private var startTime by Delegates.notNull<Double>()
    override fun execute(timer: Timer): Boolean {
        return if(timer.get() <= timeToComplete && !trajectoryCommand.isFinished) {
            trajectoryCommand.schedule()
            false
        } else {
            trajectoryCommand.end(false)
            Drive.stop()
            true
        }
    }

    override fun initialize(cumulativeTime: Double) {
        trajectoryCommand.initialize()
        Drive.resetYaw()
        Drive.reset(trajectory.initialPose)
        startTime = cumulativeTime
        timeToComplete = cumulativeTime + trajectory.totalTimeSeconds
    }


}