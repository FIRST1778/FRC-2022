package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import org.frc1778.robot.Robot
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.loader.Loader


/**
 * Path
 *
 * @constructor Create empty Path
 */
class Path {
    private var path: ArrayList<PathSegment> = ArrayList()
    var currSegment = 0


    /**
     * Called periodically during autonomous
     *
     * @param timer
     */
    fun runPath(timer: Timer) {
        if(!Loader.loaderLineBreakSensor.get() && !loaderOn) Loader.runMain(0.0)
        if(currSegment < path.size) {
            if(currSegment == 0) path[currSegment].initialize(0.0)
//            Robot.currPos.setString(path[currSegment].timeToComplete.toString())
            if (path[currSegment].execute(timer)) {
                currSegment++
                if(currSegment < path.size) path[currSegment].initialize(timer.get())
            }
        } else {
            Drive.stop()
        }
    }

    /**
     * Add a PathSegment or Event to the path
     *
     * @param segment
     */
    fun add(segment: PathSegment) {
        path.add(segment)
    }

    companion object {
        var loaderOn = false
    }

}