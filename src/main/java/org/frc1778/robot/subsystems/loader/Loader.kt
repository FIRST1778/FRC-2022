package org.frc1778.robot.subsystems.loader

import com.revrobotics.CANSparkMaxLowLevel
import org.frc1778.robot.Constants
import org.frc1778.robot.main
import org.frc1778.robot.subsystems.loader.commands.LoaderCommands
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.mathematics.units.nativeunit.DefaultNativeUnitModel
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.motors.rev.falconMAX

object Loader : FalconSubsystem() {
    private val mainMotor = falconFX(Constants.Loader.MAIN_WHEEL, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    private val loaderMotor = falconMAX(Constants.Loader.LOADER_WHEEL, CANSparkMaxLowLevel.MotorType.kBrushless, DefaultNativeUnitModel) {
        brakeMode = true
        outputInverted = false
    }

    fun runMain(percent: Double) {
        mainMotor.setDutyCycle(percent)
    }
    fun runLoader(percent: Double) = loaderMotor.setDutyCycle(percent)

    init {
        defaultCommand = LoaderCommands()
    }

}