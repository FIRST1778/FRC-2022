package org.frc1778.robot.subsystems.shooter.commands

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.Controls.operatorController
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand


open class ShootCommand : FalconCommand(Shooter) {

    private var shooterCurrVelocity = Constants.debugTab2
        .add("Current Velocity", 0.0)
        .withWidget(BuiltInWidgets.kGraph)
        .entry


    override fun execute() {
        if(!Drive.Autonomous.auto) {
            shooterCurrVelocity.setDouble(Shooter.flywheelMotor.encoder.velocity.value)


            if (getRunShooter()) {
                Shooter.shoot()
            } else {
                Shooter.runShooter(0.0)
            }
        }
    }

    companion object {
        var getRunShooter = operatorController.getRawButton(3)
    }
}
