package org.frc1778.robot

import com.pathplanner.lib.PathPlanner
import edu.wpi.first.math.trajectory.Trajectory
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.frc1778.robot.subsystems.climber.Climber
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.robot.subsystems.shooter.Shooter
import org.frc1778.util.pathing.FastLine
import org.frc1778.util.pathing.Line
import org.frc1778.util.pathing.Path
import org.frc1778.util.pathing.Turn
import org.frc1778.util.pathing.events.*
import org.ghrobotics.lib.commands.FalconCommand
import org.ghrobotics.lib.mathematics.units.SIUnit
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.mathematics.units.inches
import org.ghrobotics.lib.wrappers.FalconTimedRobot
import kotlin.contracts.contract

/**
 * The VM is configured to automatically run this object (which basically functions as a singleton class),
 * and to call the functions corresponding to each mode, as described in the TimedRobot documentation.
 * This is written as an object rather than a class since there should only ever be a single instance, and
 * it cannot take any constructor arguments. This makes it a natural fit to be an object in Kotlin.
 *
 * If you change the name of this object or its package after creating this project, you must also update
 * the `Main.kt` file in the project. (If you use the IDE's Rename or Move refactorings when renaming the
 * object or package, it will get changed everywhere.)
 */
object Robot : FalconTimedRobot()
{
    private var matchTimer = Timer()
    private val autoPath1 = Path()
    private val autoPath2 = Path()
    private val autoPath4 = Path()
    private val autoPath3 = Path()
    private val autoPath5 = Path()
    private val autoPath6 = Path()
    private val autoPath7 = Path()
    private val testAutoPath = Path()
    private lateinit var auto: Path
    private lateinit var trajectoryTest: Trajectory
    private lateinit var trajectoryCommand: FalconCommand
    private var trajectory = false

    private val testPos = Constants.debugTab2
        .add("Curr Angle", "")
        .entry

    private val currPos = Constants.debugTab2
        .add("Curr Pos", "")
        .entry



    init {
        +Shooter
        +Drive
        +Climber
        +Collector
        +Loader


    }

    private var selectedAutoMode = AutoMode.default
    private val autoModeChooser = SendableChooser<AutoMode>().also { chooser ->
        AutoMode.values().forEach { chooser.addOption(it.optionName, it) }
        chooser.setDefaultOption(AutoMode.default.optionName, AutoMode.default)
    }

    /**
     * A enumeration of the available autonomous modes.
     *
     * @param optionName The name for the [autoModeChooser] option.
     * @param periodicFunction The function that is called in the [autonomousPeriodic] function each time it is called.
     * @param autoInitFunction An optional function that is called in the [autonomousInit] function.
     */
    private enum class AutoMode(val optionName: String,
                                val autoInitFunction: () -> Unit = {        },
                                val periodicFunction: () -> Unit = { /* No op by default */ } )
    {
        CUSTOM_AUTO_1("4 Ball? Red", ::autoMode1),
        CUSTOM_AUTO_8("4 Ball? Blue", ::autoMode7),
        CUSTOM_AUTO_6("3 Ball Red", ::autoMode5),
        CUSTOM_AUTO_7("3 Ball Blue", ::autoMode6),
        CUSTOM_AUTO_2("Standard 2 Ball", ::autoMode2),
        CUSTOM_AUTO_5("Short 2 Ball", ::autoMode4),
        CUSTOM_AUTO_3("1 Ball", ::autoMode3),
        CUSTOM_AUTO_4("Test Auto", ::testMode),
        CUSTOM_AUTO_9("Trajectory Test", ::autoMode8)
        ;
        companion object
        {
            /** The default auto mode. */
            val default = CUSTOM_AUTO_1
        }
    }

    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    override fun robotInit()
    {
        SmartDashboard.putData("Auto choices", autoModeChooser)

        autoPath1.run {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(43.inches.value, 0.degrees))
            add(Turn((-218).degrees))
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)

            add(FastLine(105.inches.value, (-93).degrees))
            add(Stop)

            add(FastLine(142.inches.value, (-49).degrees))
            add(Stop)

            add(FastLine(120.inches.value, 170.degrees))
            add(Stop)

            add(Wait(.15))
            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)



        }

        autoPath2.run {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(60.inches.value, 0.degrees))
            add(Turn(190.degrees))
            add(Stop)
            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)
            add(CollectorOff)

        }

        autoPath4.run {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(43.inches.value, 0.degrees))
            add(Turn((-210).degrees))
            add(Stop)
            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)
            add(CollectorOff)
            add(Line(-5.inches.value, 0.degrees))
            add(Stop)
        }

        autoPath3.run {
            add(Line(-50.inches.value, 0.degrees))
            add(Stop)
            add(Wait(.25))

            add(Aim)
            add(Stop)

            add(Wait(.35))
            add(ShooterOn)
            add(Wait(1.25))
            add(LoaderOn)
            add(Wait(.45))
            add(LoaderOff)
            add(ShooterOff)
            add(Stop)
        }

        autoPath5.run {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(43.inches.value, 0.degrees))
            add(Turn((-218).degrees))
            add(Stop)
//            add(Wait(.15))
//            add(Aim)
//            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)

            add(Line(105.inches.value, (-93).degrees))
            add(Turn((115).degrees))
            add(Stop)

            add(Aim)
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.15))
            add(ShooterOff)
        }

        autoPath6.run {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(43.inches.value, 0.degrees))
            add(Turn((-196).degrees))
            add(Stop)
//            add(Wait(.15))
//            add(Aim)
//            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)
//
            add(Line(105.inches.value, (-82).degrees))
            add(Turn((115).degrees))
            add(Stop)

            add(Aim)
            add(Stop)
//
//            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.15))
            add(ShooterOff)
        }

        autoPath7.run {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(43.inches.value, 0.degrees))
            add(Turn((-187).degrees))
            add(Stop)

            //Shoot
            add(CollectorOff)
            add(ShooterOn)
            add(Wait(.7))
            add(LoaderOn)
            add(Wait(.1))
            add(LoaderOff)
            add(Wait(.1))
            add(CollectorOn)
            add(Wait(.15))
            add(LoaderOn)
            add(Wait(.2))
            add(LoaderOff)
            add(ShooterOff)


            add(FastLine(95.inches.value, (-73).degrees))
            add(Stop)
            add(Wait(.275))

            add(FastLine(132.inches.value, (-35).degrees))
            add(Stop)
            add(Wait(.275))

//            add(FastLine(120.inches.value, 170.degrees))
//            add(Stop)
//
//            add(Wait(.15))
//            add(Aim)
//            add(Stop)
//
//            //Shoot
//            add(CollectorOff)
//            add(ShooterOn)
//            add(Wait(.95))
//            add(LoaderOn)
//            add(Wait(.1))
//            add(LoaderOff)
//            add(Wait(.1))
//            add(CollectorOn)
//            add(Wait(.15))
//            add(LoaderOn)
//            add(Wait(.2))
//            add(LoaderOff)
//            add(ShooterOff)
        }

        testAutoPath.run {
            add(Turn(90.degrees))
        }

        trajectoryTest = PathPlanner.loadPath("Test Path", 4.0, 2.5)


    }

    /**
     * This method is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    override fun robotPeriodic() {}

    override fun autonomousInit() {
        matchTimer = Timer()
        Drive.Autonomous.auto = true
        Drive.resetEncoders()
        matchTimer.start()
        autoPath1.currSegment = 0
        Climber.winchMotorRight.encoder.resetPosition(SIUnit(0.0))
        Shooter.angleEncoder.resetPosition(SIUnit(0.0))
        trajectory = false
        Drive.resetYaw()
        Drive.reset()


        /* This autonomousInit function (along with the initAutoChooser function) shows how to select
        between different autonomous modes using the dashboard. The sendable chooser code works with the
        SmartDashboard. You can add additional auto modes by adding additional options to the AutoMode enum
        and then adding them to the `when` statement in the [autonomousPeriodic] function.

        If you prefer the LabVIEW Dashboard, remove all the chooser code and uncomment the following line: */
        //selectedAutoMode = AutoMode.valueOf(SmartDashboard.getString("Auto Selector", AutoMode.default.name))
        selectedAutoMode = autoModeChooser.selected ?: AutoMode.default
        println("Selected auto mode: ${selectedAutoMode.optionName}")
        selectedAutoMode.autoInitFunction.invoke()

        if(!trajectory) {
            auto.currSegment = 0
        } else {
            trajectoryCommand = Drive.followTrajectory(PathPlanner.loadPath("Test Path", 8.0, 5.0))
//            trajectoryCommand.initialize()
        }

    }

    /** This method is called periodically during autonomous.  */
    override fun autonomousPeriodic() {
        if(!trajectory) {
            auto.runPath(matchTimer)
        } else {
            trajectoryCommand.schedule()
            testPos.setString(trajectoryTest.sample(matchTimer.get()).toString())
        }
    }

    private fun autoMode1()
    {
        auto = autoPath1
    }

    private fun autoMode2()
    {
        auto = autoPath2
    }

    private fun autoMode3() {
        auto = autoPath3
    }

    private fun testMode() {
        auto = testAutoPath
    }

    private fun autoMode4() {
        auto = autoPath4
    }

    private fun autoMode5() {
        auto = autoPath5
    }

    private fun autoMode6() {
        auto = autoPath6
    }

    private fun autoMode7() {
        auto = autoPath7
    }

    private fun autoMode8() {
        trajectory = true
    }


    /** This method is called once when teleop is enabled.  */
    override fun teleopInit() {
        Drive.Autonomous.auto = false
        Shooter.setAngle(SIUnit(.0))
        Drive.resetYaw()
        Drive.reset()
//        Climber.winchMotorRight.encoder.resetPosition(SIUnit(0.0))
        Collector.deployMotor.setPosition(SIUnit(0.0))

    }

    /** This method is called periodically during operator control.  */
    override fun teleopPeriodic() {
        currPos.setString(Drive.robotPosition.toString())
//        testPos.setString(Drive.gyro().toString())
        testPos.setDouble(Drive.gyro().degrees)

    }

    /** This method is called once when the robot is disabled.  */
    override fun disabledInit() {}

    /** This method is called periodically when disabled.  */
    override fun disabledPeriodic() {}

    /** This method is called once when test mode is enabled.  */
    fun testInit() {}

    /** This method is called periodically during test mode.  */
    fun testPeriodic() {}
}