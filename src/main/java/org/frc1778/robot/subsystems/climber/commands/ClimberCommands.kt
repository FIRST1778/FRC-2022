package org.frc1778.robot.subsystems.climber.commands

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.climber.Climber
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.SIUnit

open class ClimberCommands : FalconCommand(Climber) {
    private val climberEncoderReset = Constants.debugTab2
        .add("Climber Encoder Reset", 0)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .withPosition(0,0)
        .withSize(1,1)
        .entry

    override fun execute() {
        if(climberEncoderReset.getBoolean(false)) Climber.climberEncoder.resetPosition(SIUnit(0.0))
//        Climber.moveClimber(climbAdjust())
        if(climberUpSource()) Climber.deployHook()
        if(climberDownSource()) Climber.climb()
    }


    companion object {
        var climbAdjust = Controls.driverController.getRawAxis(3)
        var climberDownSource = Controls.operatorController.getRawButton(8)
        var climberUpSource = Controls.operatorController.getRawButton(7)
    }
}