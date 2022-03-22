package org.frc1778.robot.subsystems.shooter.commands

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.Constants.debugTab2
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.frc1778.robot.Controls.operatorController
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.radians
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.wrappers.networktables.get
import kotlin.math.tan


open class ShootCommand : FalconCommand(Shooter) {



    private var setAngle = 0.0

    private var buttonLast = false



    private var shooterCurrVelocity = Constants.debugTab2
        .add("Current Velocity", 0.0)
        .withWidget(BuiltInWidgets.kGraph)
        .entry




    override fun execute() {
        if(!Drive.auto) {
            shooterCurrVelocity.setDouble(Shooter.flywheelMotor.encoder.velocity.value)


            if (getRunShooter()) {
                Shooter.shoot()
//                Shooter.setAngle(SIUnit(.0))
            } else {
                Shooter.runShooter(0.0)
            }

//            if(!buttonLast) {
//                setAngle += if(setAngleBack() > .35 && setAngle > 0.0) -.25 else if(setAngleForward() > .35 && setAngle < 9.0) .25 else 0.0
//                shooterAngle.setDouble(setAngle)
//                Shooter.setAngle(setAngle)
//            }
//            buttonLast = setAngleBack() >.35 || setAngleForward() > .35
        }
    }

    companion object {
        var getRunShooter = operatorController.getRawButton(3)
        var setAngleBack = operatorController.getRawAxis(2)
        var setAngleForward = operatorController.getRawAxis(3)
    }
}
