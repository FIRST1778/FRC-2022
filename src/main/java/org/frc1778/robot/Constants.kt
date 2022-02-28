package org.frc1778.robot

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitRotationModel
import org.ghrobotics.lib.mathematics.units.nativeunit.nativeUnits

object Constants {
    val debugTab2: ShuffleboardTab = Shuffleboard.getTab("Debug2")

    object Drive {
        const val LEFT_MASTER_ID = 2
        const val LEFT_SLAVE_ID = 1
        const val RIGHT_MASTER_ID = 4
        const val RIGHT_SLAVE_ID = 3

        val WHEEL_RADIUS = 3.inches
        val TRACK_WIDTH = 23.inches
        val NATIVE_UNIT_MODEL = NativeUnitLengthModel(2048.nativeUnits, WHEEL_RADIUS)
    }

    object Shooter {
        const val SHOOTER_FLYWHEEL = 0
        const val ANGLE_ADJUSTMENT = 21
        val NATIVE_ROTATION_MODEL = NativeUnitRotationModel(2048.nativeUnits)
    }

    object Loader {
        const val MAIN_WHEEL = 0
        const val LOADER_WHEEL = 0
        const val INTERMEDIATE_WHEEL = 0
    }

    object Collector {
        const val LEFT_MINI_MASTER = 0
        const val RIGHT_MINI_SLAVE = 0
        const val DEPLOY_MOTOR = 0
    }

    object Climber {
        const val CLIMBER_MOTOR = 0

        //TODO: Find native units per rotation for absolute encoder
        val NATIVE_ROTATION_MODEL = NativeUnitRotationModel(0.nativeUnits)
    }
}
