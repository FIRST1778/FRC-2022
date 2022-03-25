package org.frc1778.robot.subsystems.collector.commands

import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.commands.FalconCommand


var deployLast = true


open class CollectorCommands : FalconCommand(Collector) {

    override fun execute() {
        if(!Drive.Autonomous.auto) {

            if (!deployLast) {
                if (deploySource()) Collector.toggleCollector()
                deployLast = true
            }
//            if(Collector.collectorDown) {
//                if(liftSource() < -0.3) {
//                    Collector.lift()
//                } else {
//                    Collector.lower()
//                }
//            }
            deployLast = deploySource()
            Collector.runCollector(if (collectSource()) 0.35 else if (reverse()) -.15 else 0.0)
        }
    }

    companion object {
        var collectSource = Controls.operatorController.getRawButton(1)
        var liftSource = Controls.operatorController.getRawAxis(1)
        var loaderSource = Controls.operatorController.getRawButton(6)
        var reverse = Controls.operatorController.getRawButton(4)
        var deploySource = Controls.operatorController.getRawButton(9)
    }
}