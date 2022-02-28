package org.frc1778.robot.subsystems.shooter

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.Constants.Shooter.NATIVE_ROTATION_MODEL
import org.frc1778.robot.subsystems.shooter.commands.ShootCommand
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitRotationModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits
import org.ghrobotics.lib.motors.ctre.falconFX

object Shooter : FalconSubsystem() {

    val angleValue= Constants.debugTab2
        .add("Angle",0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1, 0)
        .withSize(1,1)
        .entry

    val nativeUnitsVal = Constants.debugTab2
        .add("Native Units", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(0, 2)
        .withSize(1,1)
        .entry

    private val rotationModel = NativeUnitRotationModel(2048.nativeUnits)

    private val flywheelMotor = falconFX(Constants.Shooter.SHOOTER_FLYWHEEL, NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = false

    }
private val angleAdjuster = falconFX(Constants.Shooter.ANGLE_ADJUSTMENT, NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = true
        motionProfileCruiseVelocity = SIUnit(360.0)
        motionProfileAcceleration = SIUnit(520.0)
        useMotionProfileForPosition = true

    }

    val angleEncoder = angleAdjuster.encoder

    fun runShooter(percent: Double) {
        flywheelMotor.setDutyCycle(percent)
    }

    fun setAngle(angle: SIUnit<Radian>) {
        nativeUnitsVal.setDouble(NATIVE_ROTATION_MODEL.toNativeUnitPosition(angle).value)
        angleValue.setDouble(angle.value)
        angleAdjuster.setPosition(angle)
    }

    init {
//        angleEncoder.resetPosition(0.radians)
        defaultCommand = ShootCommand()

        angleAdjuster.motorController.config_kF(0, 0.075, 30)
        angleAdjuster.motorController.config_kP(0, .85, 30)
        angleAdjuster.motorController.config_kI(0,0.0,30)
        angleAdjuster.motorController.config_kD(0, 10.0, 30)
    }
}
