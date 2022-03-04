package org.frc1778.robot.subsystems.shooter

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.Constants.Shooter.NATIVE_ROTATION_MODEL
import org.frc1778.robot.subsystems.shooter.commands.ShootCommand
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.ghrobotics.lib.mathematics.units.derived.Velocity
import org.ghrobotics.lib.motors.ctre.falconFX

object Shooter : FalconSubsystem() {

    private val angleValue = Constants.debugTab2
        .add("Angle",0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1, 0)
        .withSize(1,1)
        .entry


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

    fun runShooter(velocity: SIUnit<Velocity<Radian>>) {
        flywheelMotor.setVelocity(velocity)
    }

    fun setAngle(angle: SIUnit<Radian>) {
        angleValue.setDouble(angle.value)
        angleAdjuster.setPosition(angle)
    }

    //TODO: !!! GET VALUES FOR SHOOTER !!!
    fun getSetPositions(distance: Double): Pair<Double, SIUnit<Radian>> {
        return Pair(0.0, SIUnit(0.0))
    }



    init {
        defaultCommand = ShootCommand()

//        angleEncoder.resetPosition(SIUnit(0.0))

        angleAdjuster.motorController.config_kF(0, 0.075, 30)
        angleAdjuster.motorController.config_kP(0, .85, 30)
        angleAdjuster.motorController.config_kI(0,0.0,30)
        angleAdjuster.motorController.config_kD(0, 10.0, 30)
    }
}
