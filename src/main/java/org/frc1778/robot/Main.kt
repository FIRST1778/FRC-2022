@file:JvmName("Main")
// set the compiled Java class name to "Main" rather than "MainKt"
package org.frc1778.robot

import edu.wpi.first.wpilibj.RobotBase
import org.frc1778.util.pathing.Line
import org.frc1778.util.pathing.Path
import org.frc1778.util.pathing.Turn
import org.frc1778.util.pathing.events.*
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.inches

/**
 * Main initialization function. Do not perform any initialization here
 * other than calling `RobotBase.startRobot`. Do not modify this file
 * except to change the object passed to the `startRobot` call.
 *
 * If you change the package of this file, you must also update the
 * `ROBOT_MAIN_CLASS` variable in the gradle build file. Note that
 * this file has a `@file:JvmName` annotation so that its compiled
 * Java class name is "Main" rather than "MainKt". This is to prevent
 * any issues/confusion if this file is ever replaced with a Java class.
 *
 * If you change your main Robot object (name), change the parameter of the
 * `RobotBase.startRobot` call to the new name. (If you use the IDE's Rename
 * Refactoring when renaming the object, it will get changed everywhere
 * including here.)
 */
fun main() = Robot.start()

//fun main() {
//    val autoPath = Path()
//
//    autoPath.run {
//        add(CollectorDown())
//        add(Wait(.35, 0.0))
//        add(CollectorOn())
//        add(Line(36.inches.value, 0.degrees, autoPath.getLastTime()))
////        add(Line((-12).inches.value, 0.degrees, autoPath.path[3].timeToComplete))
//        add(Turn((-184).degrees, autoPath.getLastTime()))
//        add(Stop()) //6
//
//        //Shoot
//        add(CollectorOff())
//        add(ShooterOn())
//        add(Wait(.5, autoPath.getLastTime()))
//        add(LoaderOn()) //10
//        add(Wait(1.0, autoPath.getLastTime()))
//        add(LoaderOff())
//        add(CollectorOn())
//        add(Wait(1.0, autoPath.getLastTime()))
//        add(LoaderOn())
//        add(Wait(.2, autoPath.getLastTime()))
//        add(LoaderOff())
//        add(ShooterOff())
//    }
//
//    for(segment in autoPath.path) {
//        println(segment.timeToComplete)
//    }
//}
