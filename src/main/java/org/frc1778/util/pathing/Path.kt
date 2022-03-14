package org.frc1778.util.pathing

import edu.wpi.first.wpilibj.Timer


class Path {
    var path: ArrayList<PathSegment> = ArrayList()
    var currSegment = 0


    fun runPath(timer: Timer) {
        if(currSegment < path.size) {
            if (path[currSegment].execute(timer)) {
                currSegment++
            }
        }
    }

    fun add(segment: PathSegment) {
        path.add(segment)
    }

}