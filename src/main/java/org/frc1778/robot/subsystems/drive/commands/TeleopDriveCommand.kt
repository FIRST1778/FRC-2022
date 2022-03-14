package org.frc1778.robot.subsystems.drive.commands

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import kotlin.math.PI
import kotlin.math.tan
import org.frc1778.robot.Constants
import org.frc1778.robot.Controls
import org.frc1778.robot.subsystems.drive.Drive
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.inMeters
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.wrappers.networktables.get

open class TeleopDriveCommand : FalconCommand(Drive) {

    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val tx = limeTable["tx"]
    private val tv = limeTable["tv"]
    private val ty = limeTable["ty"]
    private val ta = limeTable["ta"]
    private val ledMode = limeTable["ledMode"]
    private val targetDistance = 3


    private val Tx = Constants.debugTab2
        .add("Tx", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1, 0)
        .withSize(1, 1)
        .entry

    private val limeDistance = Constants.debugTab2
        .add("Distance", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1, 0)
        .withSize(1, 1)
        .entry

    private val Ty = Constants.debugTab2
        .add("Ty", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(5, 0)
        .withSize(1, 1)
        .entry

    private val Tv = Constants.debugTab2
        .add("Target?", false)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(2, 0)
        .withSize(1, 1)
        .entry

    private val DriveEncoder = Constants.debugTab2
        .add("Drive Encoder", 0.0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1, 3)
        .withSize(1,1)
        .entry



    var doOnce = true
    override fun execute() {
        Tx.setDouble(tx.getDouble(0.0))
        Ty.setDouble(ty.getDouble(0.0))
        Tv.setBoolean(tv.getBoolean(false))
        DriveEncoder.setDouble(Drive.encoder.rawPosition.value)
        //distance in feet
        val distance = ((104.0 - 23.5) / (tan((33.322 + ty.getDouble(0.0)) / 57.296))).inches
        limeDistance.setDouble(distance.value * 39.37)

        ledMode.setDouble(3.0)
        if(!Drive.auto) {
            if ((!limeSource() || ta.getDouble(0.0) < 0.0)) {
                Drive.curvatureDrive(linearSource(), turnSource(), quickTurnSource())
            } else {
                ledMode.setDouble(3.0)
                Drive.rotateInPlace(tx.getDouble(0.0)+1.875)
            }

        } else {
//            Drive.drive(1.0)
//            Drive.rotateInPlace(90.0)
        }
    }
    companion object {
        val linearSource = Controls.driverController.getRawAxis(1)
        val turnSource = Controls.driverController.getRawAxis(2)
        val quickTurnSource = Controls.driverController.getRawButton(2)
        val limeSource = Controls.driverController.getRawButton(1)
        val wheelFullRotationSource = Controls.driverController.getRawButton(2)
    }
}
