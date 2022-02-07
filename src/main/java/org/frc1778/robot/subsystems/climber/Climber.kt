package org.frc1778.robot.subsystems.climber

import com.revrobotics.CANSparkMaxLowLevel
import org.frc1778.robot.Constants
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.rev.falconMAX

object Climber : FalconSubsystem() {
    private val whinchMotor = falconMAX(Constants.Climber.CLIMBER_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    //TODO: Figure out how this is gonna work
    fun deployHook() {

    }

    //TODO: Figure out how this is gonna work
    fun climb() {

    }


}