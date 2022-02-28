package org.frc1778.robot.subsystems.collector

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.collector.commands.CollectorCommands
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.motors.rev.falconMAX

object Collector : FalconSubsystem() {
    private val miniLeftMaster = falconMAX(Constants.Collector.LEFT_MINI_MASTER, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    private val deployMotor = falconFX(Constants.Collector.DEPLOY_MOTOR, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    fun runCollector(percent: Double) = miniLeftMaster.setDutyCycle(percent)


    fun deployCollector() {

    }

    init {
        val minRightSlave = falconMAX(Constants.Collector.RIGHT_MINI_SLAVE, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
            brakeMode = true
            outputInverted = false
            follow(miniLeftMaster)
        }

        deployMotor.motorController.config_kF(0, 0.0, 30)
        deployMotor.motorController.config_kP(0, 0.0, 30)
        deployMotor.motorController.config_kI(0, 0.0, 30)
        deployMotor.motorController.config_kD(0, 0.0, 30)


        defaultCommand = CollectorCommands()
    }


}