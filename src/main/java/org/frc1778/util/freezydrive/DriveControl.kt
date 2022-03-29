package org.frc1778.util

import org.frc1778.robot.subsystems.drive.Drive
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sin

object DriveControl {
    private var oldWheel = 0.0
    private var quickStopAccumulator = 0.0

    // "Deadband" is the dead zone of the joysticks, for throttle and steering
    private const val throttleDeadband = 0.04
    private const val wheelDeadband = 0.02
    fun calculateDrive(throttle: Double, wheel: Double, isQuickTurn: Boolean) {
        var throttle = throttle
        var wheel = wheel
        wheel = handleDeadband(wheel, wheelDeadband)
        throttle = handleDeadband(throttle, throttleDeadband)

        // throttle = throttle / 0.6;
        if (throttle < 0) wheel = -wheel
        val negInertia = wheel - oldWheel
        oldWheel = wheel
        val wheelNonLinearity: Double = 0.5
        // Apply a sin function that's scaled to make it feel better.
        wheel = (sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / sin(Math.PI / 2.0 * wheelNonLinearity))
        wheel = (sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / sin(Math.PI / 2.0 * wheelNonLinearity))
        wheel = (sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / sin(Math.PI / 2.0 * wheelNonLinearity))
        var sensitivity: Double
        val angularPower: Double
        var negInertiaAccumulator = 0.0
        val negInertiaScalar: Double = if (wheel * negInertia > 0) {
            2.5
        } else {
            if (abs(wheel) > 0.65) {
                5.0
            } else {
                3.0
            }
        }
        val negInertiaPower = negInertia * negInertiaScalar
        negInertiaAccumulator += negInertiaPower
        wheel += negInertiaAccumulator
        if (negInertiaAccumulator > 1) {
            negInertiaAccumulator -= 1.0
        } else if (negInertiaAccumulator < -1) {
            negInertiaAccumulator += 1.0
        } else {
            negInertiaAccumulator = 0.0
        }
        val linearPower: Double = throttle

        // linearPower += .01;
        var rightPower: Double
        var leftPower: Double
        val overPower: Double
        sensitivity = .85

        // Calculates quick turn (top right switch)
        if (isQuickTurn) {
            if (abs(linearPower) < 0.2) {
                val alpha = 0.1
                quickStopAccumulator =
                    (1 - alpha) * quickStopAccumulator + alpha * 5 * -1 * min(wheel, 1.0)
            }
            overPower = 1.0
            sensitivity = 1.0
            angularPower = -wheel // for old controller
        } else {
            overPower = 0.0
            angularPower = -1 * (Math.abs(throttle) * wheel * sensitivity - quickStopAccumulator)
            if (quickStopAccumulator > 1) {
                quickStopAccumulator -= 1.0
            } else if (quickStopAccumulator < -1) {
                quickStopAccumulator += 1.0
            } else {
                quickStopAccumulator = 0.0
            }
        }
        leftPower = linearPower
        rightPower = leftPower
        leftPower += angularPower
        rightPower -= angularPower
        if (leftPower > 1.0) {
            rightPower -= overPower * (leftPower - 1.0)
            leftPower = 1.0
        } else if (rightPower > 1.0) {
            leftPower -= overPower * (rightPower - 1.0)
            rightPower = 1.0
        } else if (leftPower < -1.0) {
            rightPower += overPower * (-1.0 - leftPower)
            leftPower = -1.0
        } else if (rightPower < -1.0) {
            leftPower += overPower * (-1.0 - rightPower)
            rightPower = -1.0
        }

        // sends final values to drive train
        Drive.drive(Pair(leftPower, rightPower))
    }

    // calculates the deadband of the value
    private fun handleDeadband(`val`: Double, deadband: Double): Double {
        return if (abs(`val`) > abs(deadband)) `val` else 0.0
    }
}