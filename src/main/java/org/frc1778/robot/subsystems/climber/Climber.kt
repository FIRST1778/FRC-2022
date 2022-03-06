package org.frc1778.robot.subsystems.climber

import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.AnalogEncoder
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.Constants.debugTab2
import org.frc1778.robot.subsystems.climber.commands.ClimberCommands
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.rev.falconMAX

object Climber : FalconSubsystem() {
    private val climberSetPosition = debugTab2
        .add("Climber Set Position", 0.0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(0, 3)
        .withSize(1,1)
        .entry

    val winchMotorRight = falconMAX(Constants.Climber.CLIMBER_MOTOR_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.Climber.NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = false
        useMotionProfileForPosition = true
        motionProfileCruiseVelocity = SIUnit(10.0)
        motionProfileAcceleration = SIUnit(100.0)
    }

    val winchMotorLeft = falconMAX(Constants.Climber.CLIMBER_MOTOR_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.Climber.NATIVE_ROTATION_MODEL) {
        brakeMode = true
        //TODO: Get Motor Direction
        outputInverted = false
        follow(winchMotorRight)
    }


    private const val deployedClimberEncoderValue = 7.5
    fun deployHook1() {
        winchMotorRight.setPosition(SIUnit(deployedClimberEncoderValue-2.2))
    }


    //TODO: Determine Deployed Hook Encoder Val
    fun deployHook2() {
        winchMotorRight.setPosition(SIUnit(deployedClimberEncoderValue))
    }

    //TODO: Find correct position for maintained climb
    fun climb() {
        winchMotorRight.setPosition(SIUnit(0.0))
    }

    init {
       winchMotorRight.encoder.resetPosition(SIUnit(0.0))
        winchMotorRight.setDutyCycle(0.0)

        winchMotorRight.controller.ff = 0.000065
        winchMotorRight.controller.p = 0.000004815
        winchMotorRight.controller.i = 0.00000
        winchMotorRight.controller.d = 0.0



        defaultCommand = ClimberCommands()
    }


}