package org.frc1778.robot.subsystems.shooter.commands

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants.debugTab2
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.frc1778.robot.Controls.operatorController
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.radians



open class ShootCommand : FalconCommand(Shooter) {



    private var setAngle = 0.0

    private var buttonLast = false

    override fun execute() {
        if(!Drive.auto) {
            if (getRunShooter()) Shooter.runShooter() else Shooter.runShooter(0.0)
            Shooter.setAngle()
        }
    }

    companion object {
        var getRunShooter = operatorController.getRawButton(3)
    }
}
