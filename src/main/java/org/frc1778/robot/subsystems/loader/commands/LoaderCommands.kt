package org.frc1778.robot.subsystems.loader.commands

import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.loader.Loader
import org.ghrobotics.lib.commands.FalconCommand

open class LoaderCommands : FalconCommand(Loader){

    override fun execute() {
        Loader.runMain(if(mainSource()) .20 else if(reverse()) -.15 else 0.0)
        Loader.runLoader(if(loaderSource()) 0.15 else 0.0)
    }
    companion object {
        var mainSource = Controls.operatorController.getRawButton(1)
        var shooterSource = Controls.operatorController.getRawButton(3)
        var reverse = Controls.operatorController.getRawButton(4)
        var loaderSource = Controls.operatorController.getRawButton(2)
    }
}