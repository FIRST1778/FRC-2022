package org.frc1778.robot.subsystems.climber

import com.revrobotics.CANSparkMaxLowLevel
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.climber.commands.ClimberCommands
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.ghrobotics.lib.motors.rev.falconMAX

object Climber : FalconSubsystem() {
    private val winchMotor = falconMAX(Constants.Climber.CLIMBER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless, Constants.Climber.NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = false
    }

    //TODO: Determine open loop control or close loop to two set positions
    fun deployHook() {

    }

    //TODO: Find correct position for maintained climb
    fun climb() {
        winchMotor.setPosition(SIUnit<Radian>(0.0))
    }

    init {
        defaultCommand = ClimberCommands()
    }


}