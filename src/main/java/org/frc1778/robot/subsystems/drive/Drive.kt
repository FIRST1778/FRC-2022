package org.frc1778.robot.subsystems.drive

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.math.controller.RamseteController
import edu.wpi.first.math.controller.SimpleMotorFeedforward
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.interfaces.Gyro
import org.frc1778.robot.Constants
import org.frc1778.robot.subsystems.drive.commands.TeleopDriveCommand
import org.ghrobotics.lib.localization.TimePoseInterpolatableBuffer
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.motors.ctre.falconFX
import org.ghrobotics.lib.subsystems.drive.FalconWestCoastDrivetrain
import org.ghrobotics.lib.utils.Source

object Drive : FalconWestCoastDrivetrain() {

    private val navx: AHRS = AHRS()

    override val controller: RamseteController
        get() = RamseteController(2.0, 0.7)


    override val gyro: Source<Rotation2d> = { navx.rotation2d }

    override val kinematics: DifferentialDriveKinematics = DifferentialDriveKinematics(Constants.Drive.TRACK_WIDTH.value)

    override var odometry: DifferentialDriveOdometry = DifferentialDriveOdometry(gyro())

    override val leftCharacterization: SimpleMotorFeedforward
        get() = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    override val rightCharacterization: SimpleMotorFeedforward
        get() = SimpleMotorFeedforward(0.0, 0.0, 0.0)

    public override val leftMotor = falconFX(Constants.Drive.LEFT_MASTER_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
        outputInverted = false
        brakeMode = true
        useMotionProfileForPosition = true
        motionProfileAcceleration = SIUnit(4.0)
        motionProfileCruiseVelocity = SIUnit(4.5)
    }

    public override val rightMotor = falconFX(Constants.Drive.RIGHT_MASTER_ID, Constants.Drive.NATIVE_UNIT_MODEL) {
        outputInverted = true
        brakeMode = true
        useMotionProfileForPosition = true
        motionProfileAcceleration = SIUnit(.5)
        motionProfileCruiseVelocity = SIUnit(1.0)
    }


    fun resetEncoders() {
        rightMotor.encoder.resetPosition(SIUnit(0.0))
        leftMotor.encoder.resetPosition(SIUnit(0.0))
    }

    fun resetYaw() {
        navx.zeroYaw()
    }

    fun reset() {
        odometry = DifferentialDriveOdometry(gyro())
        Drive.autoReset()
        rightMotor.encoder.resetPosition(SIUnit(0.0))
        leftMotor.encoder.resetPosition(SIUnit(0.0))
    }



    object Autonomous {
        var auto = true
        fun driveForward(){
            curvatureDrive(.25,0.0,false)
        }

        fun driveBackwards() {
            curvatureDrive(-.25, 0.0, false)
        }


        fun rotateLeft() {
            curvatureDrive(0.0, -.25, true)
        }

        fun rotateRight() {
            curvatureDrive(0.0, .25, true)
        }

        fun driveForwardsFast() {
            curvatureDrive(.42, 0.0, false)
        }

        fun driveBackwardsFast() {
            curvatureDrive(-.42, 0.0, false)
        }
    }


    fun stop() {
        curvatureDrive(0.0, 0.0, false)
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

//        rightMotor.encoder.encoderPhase = false
//        rightMotor.encoder.encoderPhase = false

        defaultCommand = TeleopDriveCommand()

    }
}
