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
    private val ty = limeTable["ty"]
    private val ta = limeTable["ta"]


    private val Tx = Constants.debugTab2
        .add("Tx", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    private val limeDistance = Constants.debugTab2
        .add("Distance", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .entry

    private val Ty = Constants.debugTab2
        .add("Ty", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .entry




    var doOnce = true
    override fun execute() {
        Tx.setDouble(tx.getDouble(0.0))
        Ty.setDouble(ty.getDouble(0.0))
        //distance in feet
        val distance = ((104.0 - 23.5) / (tan((33.322 + ty.getDouble(0.0)) / 57.296)))
        limeDistance.setDouble(distance)

        if(!Drive.auto) {
            if ((!limeSource() || ta.getDouble(0.0) < 0.0)) {
                Drive.curvatureDrive(linearSource(), turnSource(), quickTurnSource())
            } else {
                if(tx.getDouble(0.0) > if(distance > 135) 1.95 else 2.7) {
                    Drive.curvatureDrive(0.0, if(tx.getDouble(0.0) > if(distance > 135) 3.75 else 4.5) 0.085 else .02, true)
                } else if(tx.getDouble(0.0) < if(distance > 135) 1.55 else 2.3) {
                    Drive.curvatureDrive(0.0, if(tx.getDouble(0.0) < if(distance > 135) 1.55 else -.5) -0.085 else -.02, true)
                } else  {
                    Drive.stop()
                }
            }

        }
    }
    companion object {
        val linearSource = Controls.driverController.getRawAxis(1)
        val turnSource = Controls.driverController.getRawAxis(2)
        val quickTurnSource = Controls.driverController.getRawButton(2)
        val limeSource = Controls.driverController.getRawButton(1)
    }
}
