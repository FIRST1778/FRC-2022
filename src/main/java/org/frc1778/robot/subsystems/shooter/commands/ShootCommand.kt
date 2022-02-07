package org.frc1778.robot.subsystems.shooter.commands

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants.debugTab2
import org.frc1778.robot.subsystems.shooter.Shooter
import org.ghrobotics.lib.commands.FalconCommand
import org.frc1778.robot.Controls.operatorController
import org.ghrobotics.lib.mathematics.units.derived.radians



open class ShootCommand : FalconCommand(Shooter) {
    private val encoderVal = debugTab2
        .add("Encoder Value", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(0, 0)
        .withSize(1, 1)
        .entry

    private var setAngle = 0

    private var buttonLast = false

    override fun execute() {
        encoderVal.setDouble(Shooter.angleEncoder.position.value)
        Shooter.runShooter(if(getRunShooter()) 0.5 else 0.0)
        Shooter.setAngle((9.0 * angle()).radians)
        if(!buttonLast) {
            setAngle += if(angleUp() && setAngle < 9) 1 else if(angleDown() && setAngle > 0) -1 else 0
            Shooter.setAngle(setAngle.radians)
            buttonLast = true
        }
        buttonLast = angleUp() || angleDown()
    }

    companion object {
        var angle = operatorController.getRawAxis(3)
        var angleUp = operatorController.getRawButton(4)
        var angleDown = operatorController.getRawButton(3)
        var getRunShooter = operatorController.getRawButton(1)
    }
}
