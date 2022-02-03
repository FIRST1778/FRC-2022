package org.frc1778.robot.subsystems.Shooter.commands

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants.debugTab2
import org.frc1778.robot.subsystems.Shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand

val encoderVal = debugTab2
    .add("Encoder Value", 0)
    .withWidget(BuiltInWidgets.kTextView)
    .withPosition(0, 0)
    .withSize(1, 1)
    .entry

val setAngle = 0

open class ShootCommand : FalconCommand(Shooter) {
    override fun execute() {
        encoderVal.setDouble(Shooter.angleEncoder.position.value)
//        Shooter.setAngle(SIUnit(1.0))
    }

    companion object {
    }
}
