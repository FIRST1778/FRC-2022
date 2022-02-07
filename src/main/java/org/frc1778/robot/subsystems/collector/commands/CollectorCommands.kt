package org.frc1778.robot.subsystems.collector.commands

import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.collector.Collector
import org.ghrobotics.lib.commands.FalconCommand

open class CollectorCommands : FalconCommand(Collector) {

    override fun execute() {
        Collector.runCollector(if(collectSource()) 0.25 else 0.0)
        if(deploySource()) Collector.deployCollector()
    }

    companion object {
        var collectSource = Controls.operatorController.getRawButton(0)
        var deploySource = Controls.operatorController.getRawButton(0)
    }
}