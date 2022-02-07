package org.frc1778.robot.subsystems.drive

import edu.wpi.first.math.controller.RamseteController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.commands.TeleopDriveCommand
import org.ghrobotics.lib.commands.FalconSubsystem
import org.ghrobotics.lib.localization.TimePoseInterpolatableBuffer
import org.ghrobotics.lib.mathematics.units.Ampere
import org.ghrobotics.lib.mathematics.units.Meter
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.amps
import org.ghrobotics.lib.mathematics.units.derived.LinearVelocity
import org.ghrobotics.lib.mathematics.units.derived.Volt
import org.ghrobotics.lib.mathematics.units.derived.volts
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.mathematics.units.operations.div
import org.ghrobotics.lib.mathematics.units.seconds
import org.ghrobotics.lib.motors.ctre.falconSRX
import org.ghrobotics.lib.subsystems.drive.FalconDriveHelper
import org.ghrobotics.lib.utils.Source

object Drive : FalconSubsystem() {
    val periodicIO = PeriodicIO()

    val driveHelper = FalconDriveHelper()

    val controller: RamseteController
        get() = RamseteController(2.0, 0.7)

    val gyro: Source<Rotation2d>
        get() = { Rotation2d() }

    val kinematics: DifferentialDriveKinematics
        get() = DifferentialDriveKinematics(Constants.Drive.TRACK_WIDTH.value)

    val leftCharacterization: SimpleMotorFeedforward
        get() = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    val leftMotor = falconSRX(Constants.Drive.LEFT_MASTER_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
        outputInverted = false
        brakeMode = true
    }
    val odometry: DifferentialDriveOdometry
        get() = DifferentialDriveOdometry(gyro())

    val rightCharacterization: SimpleMotorFeedforward
        get() = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    val rightMotor = falconSRX(Constants.Drive.RIGHT_MASTER_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
        outputInverted = true
        brakeMode = true
    }

    val leftPercent = Constants.debugTab2
        .add("Left Percent", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(0, 1)
        .withSize(1, 1)
        .entry

    val rightPercent = Constants.debugTab2
        .add("Right Percent", 0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1, 1)
        .withSize(1, 1)
        .entry

    val poseBuffer = TimePoseInterpolatableBuffer()

    override fun periodic() {
//        periodicIO.leftVoltage = leftMotor.voltageOutput
//        periodicIO.rightVoltage = rightMotor.voltageOutput

//        periodicIO.leftCurrent = leftMotor.drawnCurrent
//        periodicIO.rightCurrent = rightMotor.drawnCurrent

        when (val desiredOutput = periodicIO.desiredOutput) {
            is Output.Nothing -> {
                leftMotor.setNeutral()
                rightMotor.setNeutral()
            }
            is Output.Percent -> {
                leftMotor.setDutyCycle(desiredOutput.left)
                rightMotor.setDutyCycle(desiredOutput.right)
                leftPercent.setDouble(desiredOutput.left)
                rightPercent.setDouble(desiredOutput.right)
            }
            is Output.Velocity -> {
                leftMotor.setVelocity(desiredOutput.left)
                rightMotor.setVelocity(desiredOutput.right)
            }
        }
    }

    fun disableClosedLoopControl() {}

    fun enableClosedLoopControl() {}

    fun curvatureDrive(
        linearPercent: Double,
        curvaturePercent: Double,
        isQuickTurn: Boolean
    ) {
        val (l, r) = driveHelper.curvatureDrive(linearPercent, curvaturePercent, isQuickTurn)
        setPercent(l, r)
    }

    fun setPercent(left: Double, right: Double) {
        periodicIO.desiredOutput = Output.Percent(left, right)
        periodicIO.leftFeedforward = 0.volts
        periodicIO.rightFeedforward = 0.volts
    }

    class PeriodicIO {
        var leftVoltage: SIUnit<Volt> = 0.volts
        var rightVoltage: SIUnit<Volt> = 0.volts

        var leftCurrent: SIUnit<Ampere> = 0.amps
        var rightCurrent: SIUnit<Ampere> = 0.amps

        var leftPosition: SIUnit<Meter> = 0.meters
        var rightPosition: SIUnit<Meter> = 0.meters

        var leftVelocity: SIUnit<LinearVelocity> = 0.meters / 1.seconds
        var rightVelocity: SIUnit<LinearVelocity> = 0.meters / 1.seconds

        var gyro: Rotation2d = Rotation2d()

        var desiredOutput: Output = Output.Nothing
        var leftFeedforward: SIUnit<Volt> = 0.volts
        var rightFeedforward: SIUnit<Volt> = 0.volts
    }

    sealed class Output {
        // No outputs
        object Nothing : Output()

        // Percent Output
        class Percent(val left: Double, val right: Double) : Output()

        // Velocity Output
        class Velocity(
            val left: SIUnit<LinearVelocity>,
            val right: SIUnit<LinearVelocity>
        ) : Output()
    }
    init {
        val leftSlave = falconSRX(Constants.Drive.LEFT_SLAVE_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
            outputInverted = false
            brakeMode = true
            follow(leftMotor)
        }
        val rightSlave = falconSRX(Constants.Drive.RIGHT_SLAVE_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
            outputInverted = true
            brakeMode = true
            follow(rightMotor)
        }

        defaultCommand = TeleopDriveCommand()
    }
}
