package org.frc1778.robot.subsystems.shooter

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import org.frc1778.robot.Constants
import org.frc1778.robot.Constants.Shooter.NATIVE_ROTATION_MODEL
import org.frc1778.robot.subsystems.shooter.commands.ShootCommand
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.Radian
import org.ghrobotics.lib.mathematics.units.derived.Velocity
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.pow
import kotlin.math.tan

object Shooter : FalconSubsystem() {
    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val ty = limeTable["ty"]

    private var shooterAngle = Constants.debugTab2
        .add("Angle", 0.0)
        .entry


    private var shooterVelocity = Constants.debugTab2
        .add("Velocity", 0.0)
        .entry



    val flywheelMotor = falconFX(Constants.Shooter.SHOOTER_FLYWHEEL, NATIVE_ROTATION_MODEL) {
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

    private fun runShooter(velocity: SIUnit<Velocity<Radian>>) {
        flywheelMotor.setVelocity(velocity)
    }

    fun setAngle(angle: SIUnit<Radian>) {
        angleAdjuster.setPosition(angle)
    }

    fun shoot() {
        val distance = ((104.0 - 23.5) / (tan((33.322 + ty.getDouble(0.0)) / 57.296)))
        val (v, a) = Shooter.getSetPositions(distance)
        shooterAngle.setDouble(a.value)
        shooterVelocity.setDouble(v.value)
        Shooter.runShooter(v)
        Shooter.setAngle(a)
    }

    private fun getSetPositions(distance: Double): Pair<SIUnit<Velocity<Radian>>, SIUnit<Radian>> {

        val v: SIUnit<Velocity<Radian>> = if(distance > 75)  {
            SIUnit(((((0.0004 * distance.pow(3)) - (0.109 * distance.pow(2)) + (11.759 * distance) + 39.691))* (.86*((distance-150)/750))) + 700 - if(distance < 150) 20.5 else 15.0)
        } else {
            SIUnit(((0.0004 * distance.pow(3)) - (0.117 * distance.pow(2)) + (11.759 * distance) + 39.691) + if(distance > 75) 25.0 else 10.25)
        }

        val a: SIUnit<Radian> = SIUnit((-(7.324605E-9 * distance.pow(4)) + (4.4445282E-6 * distance.pow(3)) - (9.211335E-4 * distance.pow(2)) + (.1009318946 * distance) - .078396) + if(distance > 150) .75 else if(distance > 120) .825 else if(distance < 90) .205 else 0.225)
        return Pair(v, a)

    }



    init {
        defaultCommand = ShootCommand()

        angleEncoder.resetPosition(SIUnit(0.0))

        angleAdjuster.motorController.config_kF(0, 0.075, 30)
        angleAdjuster.motorController.config_kP(0, .85, 30)
        angleAdjuster.motorController.config_kI(0,0.0,30)
        angleAdjuster.motorController.config_kD(0, 10.0, 30)

        flywheelMotor.motorController.config_kD(0, 0.065, 30)
        flywheelMotor.motorController.config_kF(0, 0.045, 30)
        flywheelMotor.motorController.config_kI(0, 0.000, 30)
        flywheelMotor.motorController.config_kP(0, 0.17, 30)
    }
}
