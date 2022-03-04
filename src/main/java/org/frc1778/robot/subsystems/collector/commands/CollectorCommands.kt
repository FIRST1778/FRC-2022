package org.frc1778.robot.subsystems.collector.commands

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants.debugTab2
import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.collector.Collector
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.SIUnit

private val collectorEncoder = debugTab2
    .add("Collector Encoder", 0.0)
    .withWidget(BuiltInWidgets.kTextView)
    .withPosition(0,3)
    .withSize(1,1)
    .entry
var deployLast = true
var firstExecute = true

open class CollectorCommands : FalconCommand(Collector) {

    override fun execute() {
        collectorEncoder.setDouble(Collector.rotationModel.fromNativeUnitPosition(Collector.deployMotor.encoder.position).value)

        if(!deployLast) {
            if(deploySource()) Collector.toggleCollector()
            deployLast = true
        }
        deployLast = deploySource()
        if(firstExecute) {
            Collector.deployMotor.setPosition(SIUnit(-9.5))
            firstExecute = false
        }
        Collector.runCollector(if(collectSource()) 0.25 else if(reverse()) -.15 else 0.0)
    }

    companion object {
        var collectSource = Controls.operatorController.getRawButton(1)
        var reverse = Controls.operatorController.getRawButton(4)
        var deploySource = Controls.operatorController.getRawButton(9)
    }
}