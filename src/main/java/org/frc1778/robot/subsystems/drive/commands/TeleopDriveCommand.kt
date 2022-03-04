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
import org.ghrobotics.lib.wrappers.networktables.get

open class TeleopDriveCommand : FalconCommand(Drive) {

    private val limeTable: NetworkTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val tx = limeTable["tx"]
    private val tv = limeTable["tv"]
    private val ty = limeTable["ty"]
    private val ta = limeTable["ta"]
    private val ledMode = limeTable["ledMode"]
    private val targetDistance = 3

    val linearValue = Constants.debugTab2
        .add("Linear Value", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(0, 0)
        .withSize(1, 1)
        .entry

    val turnValue = Constants.debugTab2
        .add("Turn Value", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(2, 1)
        .withSize(1, 1)
        .entry

//    val quickTurn = Constants.debugTab2
//        .add("Quick Turn", false)
//        .withWidget(BuiltInWidgets.kBooleanBox)
//        .withPosition(0, 2)
//        .withSize(1, 1)
//        .entry

    val Tx = Constants.debugTab2
        .add("Tx", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1, 0)
        .withSize(1, 1)
        .entry

    val Ty = Constants.debugTab2
        .add("Ty", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(5, 0)
        .withSize(1, 1)
        .entry

    val Tv = Constants.debugTab2
        .add("Target?", false)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(2, 0)
        .withSize(1, 1)
        .entry

    val Dist = Constants.debugTab2
        .add("Distance", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(3, 0)
        .withSize(1, 1)
        .entry

    val DeltaD = Constants.debugTab2
        .add("Delta Distance", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(4, 0)
        .withSize(1, 1)
        .entry

    override fun execute() {
        linearValue.setDouble(linearSource())
        Tx.setDouble(tx.getDouble(0.0))
        Ty.setDouble(ty.getDouble(0.0))
        Tv.setBoolean(tv.getBoolean(false))
        //distance in feet
        val distance = ((104 - 22.75) / tan(25 + ty.getDouble(0.0) * (PI / 180))) / 12
        Dist.setDouble(distance)
        DeltaD.setDouble(targetDistance - distance)

        ledMode.setDouble(3.0)

        if (!limeSource() || ta.getDouble(0.0) < 0.0) {
            Drive.curvatureDrive(linearSource(), turnSource(), quickTurnSource())
            turnValue.setDouble(turnSource())
        } else {
            ledMode.setDouble(3.0)
            Drive.curvatureDrive(0.0, tx.getDouble(0.0) / 75, true)
            turnValue.setDouble(tx.getDouble(0.0) / 85)
            //(distance - targetDistance) / 4
//            linearValue.setDouble((distance - targetDistance) / 4)
//            Tv.setBoolean(true)
        }
    }

    companion object {
        val linearSource = Controls.driverController.getRawAxis(1)
        val turnSource = Controls.driverController.getRawAxis(2)
        val quickTurnSource = Controls.driverController.getRawButton(2)
        val limeSource = Controls.driverController.getRawButton(1)
    }
}
