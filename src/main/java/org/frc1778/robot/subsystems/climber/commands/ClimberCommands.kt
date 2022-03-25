package org.frc1778.robot.subsystems.climber.commands

import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.climber.Climber
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.commands.FalconCommand

open class ClimberCommands : FalconCommand(Climber) {

    override fun execute() {
        if(!Drive.Autonomous.auto) {
            if (climberUpSource2()) Climber.deployHook2()
            if (climberUpSource1()) Climber.deployHook1()
            if (climberDownSource()) Climber.climbDown()
            if (climbSource()) Climber.climb()
            if(manualClimbControl() > .45) Climber.manualClimbUp() else if(manualClimbControl() < -.45) Climber.manualClimbDown()
        }
    }


    companion object {
        var climberDownSource = Controls.operatorController.getRawButton(7)
        var climberUpSource1 = Controls.operatorController.getRawButton(10)
        var climberUpSource2 = Controls.operatorController.getRawButton(8)
        var climbSource = Controls.operatorController.getRawButton(5)
        var manualClimbControl = Controls.operatorController.getRawAxis(5)
    }
}