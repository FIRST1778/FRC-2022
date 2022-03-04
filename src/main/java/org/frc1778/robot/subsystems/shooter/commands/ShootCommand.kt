package org.frc1778.robot.subsystems.shooter.commands

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants.debugTab2
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.frc1778.robot.Controls.operatorController
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.radians



open class ShootCommand : FalconCommand(Shooter) {
    private val encoderVal = debugTab2
        .add("Angle Encoder Value", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(0, 0)
        .withSize(1, 1)
        .entry

    private val angleEncoderReset = debugTab2
        .add("Shooter Angle Reset", false)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .withPosition(0, 3)
        .withSize(1,1)
        .entry

    private val velocity = debugTab2
        .add("Velocity", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1,2)
        .withSize(1,1)
        .entry

    private var setAngle = 0.0

    private var buttonLast = false

    override fun execute() {
        encoderVal.setDouble(Shooter.angleEncoder.position.value)
        if(angleEncoderReset.getBoolean(false)) Shooter.angleEncoder.resetPosition(SIUnit(0.0))
//        Shooter.runShooter(if(getRunShooter()) 0.5 else 0.0)
        if(getRunShooter()) Shooter.runShooter(SIUnit(velocity.getDouble(0.0))) else Shooter.runShooter(0.0)

        if(!buttonLast) {
            setAngle += if(angleUp() && setAngle < 9) 0.5 else if(angleDown() && setAngle > 0) -0.5 else 0.0
            Shooter.setAngle(setAngle.radians)
            buttonLast = true
        }
        buttonLast = angleUp() || angleDown()
    }

    companion object {
//        var angle = operatorController.getRawAxis(3)
        var angleUp = operatorController.getRawButton(6)
        var angleDown = operatorController.getRawButton(5)
        var getRunShooter = operatorController.getRawButton(3)
    }
}
