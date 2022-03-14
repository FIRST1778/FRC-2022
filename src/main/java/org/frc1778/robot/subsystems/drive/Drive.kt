package org.frc1778.robot.subsystems.drive

import edu.wpi.first.math.controller.RamseteController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.commands.TeleopDriveCommand
import org.ghrobotics.lib.localization.TimePoseInterpolatableBuffer
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.inMeters
import org.ghrobotics.lib.mathematics.units.meters
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnit
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits
import org.ghrobotics.lib.mathematics.units.nativeunit.toNativeUnitPosition
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.subsystems.drive.FalconWestCoastDrivetrain
import org.ghrobotics.lib.utils.Source
import java.lang.annotation.Native
import kotlin.math.PI

object Drive : FalconWestCoastDrivetrain() {


    val driveSetDistance = Constants.debugTab2
        .add("Set Drive Distance", 0.0)
        .withWidget(BuiltInWidgets.kTextView)
        .withPosition(1, 3)
        .withSize(1,1)
        .entry

    override val controller: RamseteController
        get() = RamseteController(2.0, 0.7)

    private val nativeUnitModel = Constants.Drive.NATIVE_UNIT_MODEL

    override val gyro: Source<Rotation2d>
        get() = { Rotation2d() }

    override val kinematics: DifferentialDriveKinematics
        get() = DifferentialDriveKinematics(Constants.Drive.TRACK_WIDTH.value)

    override val leftCharacterization: SimpleMotorFeedforward
        get() = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    override val odometry: DifferentialDriveOdometry
        get() = DifferentialDriveOdometry(gyro())

    override val rightCharacterization: SimpleMotorFeedforward
        get() = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    override val leftMotor = falconFX(Constants.Drive.LEFT_MASTER_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
        outputInverted = false
        brakeMode = true
        useMotionProfileForPosition = true
        motionProfileAcceleration = SIUnit(4.0)
        motionProfileCruiseVelocity = SIUnit(4.5)
    }

    override val rightMotor = falconFX(Constants.Drive.RIGHT_MASTER_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
        outputInverted = true
        brakeMode = true
        useMotionProfileForPosition = true
        motionProfileAcceleration = SIUnit(.5)
        motionProfileCruiseVelocity = SIUnit(1.0)
    }

    val encoder = rightMotor.encoder

    private val arcTab = Constants.debugTab2
        .add("Arc", 0.0)
        .withWidget(BuiltInWidgets.kTextView)
        .withSize(1,1)
        .withPosition(0, 3)
        .entry

        fun resetEncoders() {
        rightMotor.encoder.resetPosition(SIUnit(0.0))
        leftMotor.encoder.resetPosition(SIUnit(0.0))

    }


    var auto = true
    fun driveForward(){
        curvatureDrive(.25,0.0,false)

    }

    fun driveBackwards() {
        curvatureDrive(-.25, 0.0, false)
    }

    fun rotateInPlace(angle: Double) {
        if(!auto) resetEncoders()
        val arc = (2 * PI * (Constants.Drive.TRACK_WIDTH.inMeters() / 2) * (angle/360)).meters
        leftMotor.setPosition(arc)
        rightMotor.setPosition(-arc)
    }

    fun rotateLeft() {
        curvatureDrive(0.0, -.25, true)
    }

    fun rotateRight() {
        curvatureDrive(0.0, .25, true)
    }

    fun stop() {
        curvatureDrive(0.0, 0.0, false)
    }

    fun fullRotation() {
//        resetEncoders()
        rightMotor.setPosition(nativeUnitModel.fromNativeUnitPosition(17000.nativeUnits))
    }

    override val poseBuffer = TimePoseInterpolatableBuffer()



   override fun disableClosedLoopControl() {}

    override fun enableClosedLoopControl() {}




    init {
        val leftSlave = falconFX(Constants.Drive.LEFT_SLAVE_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
            outputInverted = false
            brakeMode = true
            useMotionProfileForPosition = true
            motionProfileAcceleration = SIUnit(.5)
            motionProfileCruiseVelocity = SIUnit(1.0)
            follow(leftMotor)
        }
        val rightSlave = falconFX(Constants.Drive.RIGHT_SLAVE_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
            outputInverted = true
            brakeMode = true
            useMotionProfileForPosition = true
            motionProfileAcceleration = SIUnit(.5)
            motionProfileCruiseVelocity = SIUnit(1.0)
            follow(rightMotor)
        }

        leftMotor.motorController.config_kF(0, 0.15, 30)
        leftMotor.motorController.config_kP(0, .65, 30)
        leftMotor.motorController.config_kI(0,0.0,30)
        leftMotor.motorController.config_kD(0, 4.0, 30)

        rightMotor.motorController.config_kF(0, 0.15, 30)
        rightMotor.motorController.config_kP(0, .65, 30)
        rightMotor.motorController.config_kI(0,0.0,30)
        rightMotor.motorController.config_kD(0, 4.0, 30)

        defaultCommand = TeleopDriveCommand()
    }
}
