package org.frc1778.robot.subsystems.climber.commands

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.climber.Climber
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.SIUnit

open class ClimberCommands : FalconCommand(Climber) {

    override fun execute() {
        if(climberUpSource2()) Climber.deployHook2()
        if(climberUpSource1()) Climber.deployHook1()
        if(climberDownSource()) Climber.climbDown()
        if(climbSource()) Climber.climb()
        if(restEncoder()) Climber.winchMotorRight.encoder.resetPosition(SIUnit(0.0))
    }


    companion object {
        var climbAdjust = Controls.driverController.getRawAxis(3)
        var climberDownSource = Controls.operatorController.getRawButton(7)
        var climberUpSource1 = Controls.operatorController.getRawButton(10)
        var climberUpSource2 = Controls.operatorController.getRawButton(8)
        var climbSource = Controls.operatorController.getRawButton(5)
        var restEncoder = Controls.operatorController.getRawButton(2)
    }
}