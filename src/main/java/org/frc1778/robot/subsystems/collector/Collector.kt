package org.frc1778.robot.subsystems.collector

import com.revrobotics.CANSparkMaxLowLevel
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.collector.commands.CollectorCommands
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.motors.rev.falconMAX


object Collector : FalconSubsystem() {
    private val miniLeftMaster = falconMAX(Constants.Collector.LEFT_MINI_MASTER, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    val miniRightSlave = falconMAX(Constants.Collector.RIGHT_MINI_SLAVE, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }


    val deployMotor = falconFX(Constants.Collector.DEPLOY_MOTOR, Constants.Collector.NATIVE_ROTATION_MODEL) {
        brakeMode = true
        outputInverted = false
        useMotionProfileForPosition = true
        motionProfileCruiseVelocity = SIUnit(14.5)
        motionProfileAcceleration = SIUnit(100.0)
    }


    var collectorDown = false

    fun runCollector(percent: Double) {
        if(collectorDown) miniLeftMaster.setDutyCycle(percent)
    }

    fun toggleCollector() {
        collectorDown = if(!collectorDown) {
            deployMotor.setPosition(SIUnit(9.5))
            true
        } else {
            deployMotor.setPosition(SIUnit(0.0))
            false
        }
    }

    fun lift() {
        if(collectorDown) {
            deployMotor.setPosition(SIUnit(8.75))
        }
    }

    fun lower() {
        if(collectorDown) {
            deployMotor.setPosition(SIUnit(9.5))
        }
    }

    init {
        deployMotor.encoder.resetPosition(SIUnit(0.0))
        deployMotor.motorController.configAllowableClosedloopError(0, 100.0, 30)
        deployMotor.motorController.config_kF(0, 0.075, 30)
        deployMotor.motorController.config_kP(0, 1.6, 30)
        deployMotor.motorController.config_kI(0, 0.0, 30)
        deployMotor.motorController.config_kD(0, 16.0, 30)


        defaultCommand = CollectorCommands()
    }


}