package org.frc1778.robot

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.feet
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitRotationModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits

object Constants {
    val debugTab2: ShuffleboardTab = Shuffleboard.getTab("TeleOp Info")

    object Drive {
        const val LEFT_MASTER_ID = 2
        const val LEFT_SLAVE_ID = 4
        const val RIGHT_MASTER_ID = 1
        const val RIGHT_SLAVE_ID = 3

        private val WHEEL_RADIUS = 3.inches
        val TRACK_WIDTH = 23.inches

        val speed = 4.feet //Speed in feet/sec
        val fastSpeed = 80.inches // Faster Speed in inches/sec
        val rotSpeed = 185.degrees // Rotation speed in degrees/sec

        val NATIVE_UNIT_MODEL = NativeUnitLengthModel(17000.nativeUnits, WHEEL_RADIUS)
    }

    object Shooter {
        const val SHOOTER_FLYWHEEL = 19
        const val ANGLE_ADJUSTMENT = 21
        val NATIVE_ROTATION_MODEL = NativeUnitRotationModel(2048.nativeUnits)
    }

    object Loader {
        const val MAIN_WHEEL = 16
        const val LOADER_WHEEL = 40
        const val INTERMEDIATE_WHEEL = 30
    }

    object Collector {
        const val LEFT_MINI_MASTER = 22
        const val RIGHT_MINI_SLAVE = 33
        const val DEPLOY_MOTOR = 11

        val NATIVE_ROTATION_MODEL = NativeUnitRotationModel(2048.nativeUnits)
    }

    object Climber {
        const val CLIMBER_MOTOR_RIGHT = 42
        const val CLIMBER_MOTOR_LEFT = 31

        val NATIVE_ROTATION_MODEL = NativeUnitRotationModel(42.nativeUnits)

    }
}

