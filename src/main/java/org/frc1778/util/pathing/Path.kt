package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.loader.Loader



class Path {
    private var path: ArrayList<PathSegment> = ArrayList()
    var currSegment = 0

    fun runPath(timer: Timer) {
        if(!Loader.loaderLineBreakSensor.get() && !loaderOn) Loader.runMain(0.0)
        if(currSegment < path.size) {
            if (path[currSegment].execute(timer)) {
                currSegment++
            }
        } else {
            Drive.stop()
        }
    }

    fun add(segment: PathSegment) {
        path.add(segment)
    }

    fun getLastTime(): Double {
        for(i in path.size-1  downTo 0) {
            if(path[i].timeToComplete != 0.0) return path[i].timeToComplete
        }
        return -1.0

    }

    companion object {
        var loaderOn = false
    }

}