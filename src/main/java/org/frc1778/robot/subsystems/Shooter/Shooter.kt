package org.frc1778.robot.subsystems.Shooter

import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.Shooter.commands.ShootCommand
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnit
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitRotationModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits
import org.ghrobotics.lib.motors.ctre.falconFX

object Shooter : FalconSubsystem() {
    private val rotationModel = NativeUnitRotationModel(2048.nativeUnits)

    private val flywheelMotor = falconFX(Constants.Shooter.SHOOTER_FLYWHEEL, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }
    private val angleAdjuster = falconFX(Constants.Shooter.ANGLE_ADJUSTMENT, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    val angleEncoder = angleAdjuster.encoder

    fun runShooter(percent: Double) {
        flywheelMotor.setDutyCycle(percent)
    }

    fun setAngle(angle: SIUnit<Radian>) {
        angleAdjuster.setPosition(rotationModel.toNativeUnitPosition(angle))
    }

    init {
//        encoder.resetPosition(rotationModel.toNativeUnitPosition(SIUnit<Radian>(0.0)))
        defaultCommand = ShootCommand()
    }
}
