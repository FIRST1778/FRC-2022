package org.frc1778.robot.subsystems.climber.commands

import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.climber.Climber
import org.ghrobotics.lib.commands.FalconCommand

open class ClimberCommands : FalconCommand(Climber) {




    companion object {
        var climberUpSource = Controls.operatorController.getRawButton(0)
        var climberDownSource = Controls.operatorController.getRawButton(0)
    }
}