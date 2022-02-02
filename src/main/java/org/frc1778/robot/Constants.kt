package org.frc1778.robot

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.mathematics.units.nativeunit.NativeUnitLengthModel
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
    }
}
