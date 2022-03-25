package org.frc1778.robot.subsystems.climber

import com.revrobotics.CANSparkMaxLowLevel
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.climber.commands.ClimberCommands
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.motors.rev.falconMAX

/**
 * Climber subsystem
 */
object Climber : FalconSubsystem() {

    val winchMotorRight = falconMAX(Constants.Climber.CLIMBER_MOTOR_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.Climber.NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = false
        useMotionProfileForPosition = true
        motionProfileCruiseVelocity = SIUnit(20.0)
        motionProfileAcceleration = SIUnit(250.0)
    }



    private const val deployedClimberEncoderValue = 7.5
    fun deployHook1() {
        winchMotorRight.setPosition(SIUnit(deployedClimberEncoderValue-2.2))
    }


    fun deployHook2() {
        winchMotorRight.setPosition(SIUnit(deployedClimberEncoderValue))
    }

    fun climbDown() {
        winchMotorRight.setPosition(SIUnit(0.0))
    }

    fun climb() {
        winchMotorRight.setPosition(SIUnit(-1.0))
    }

    fun manualClimbUp() {
        winchMotorRight.setPosition(SIUnit(winchMotorRight.encoder.position.value - .25))
    }

    fun manualClimbDown() {
        winchMotorRight.setPosition(SIUnit(winchMotorRight.encoder.position.value + .25))
    }



    init {
        winchMotorRight.encoder.resetPosition(SIUnit(0.0))
        winchMotorRight.setDutyCycle(0.0)

        winchMotorRight.controller.ff = 0.000065
        winchMotorRight.controller.p = 0.000004815
        winchMotorRight.controller.i = 0.00000
        winchMotorRight.controller.d = 0.0

        val winchMotorLeft = falconMAX(Constants.Climber.CLIMBER_MOTOR_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.Climber.NATIVE_ROTATION_MODEL) {
            brakeMode = true
            outputInverted = false
            follow(winchMotorRight)
        }

        defaultCommand = ClimberCommands()
    }


}