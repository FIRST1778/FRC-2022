package org.frc1778.robot

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4069.keigen.mat
import org.frc1778.robot.subsystems.climber.Climber
import org.frc1778.robot.subsystems.collector.Collector
import org.frc1778.robot.subsystems.collector.commands.deployLast
import org.frc1778.robot.subsystems.drive.Drive
import org.frc1778.robot.subsystems.loader.Loader
import org.frc1778.robot.subsystems.shooter.Shooter
import org.frc1778.util.pathing.Line
import org.frc1778.util.pathing.Path
import org.frc1778.util.pathing.Turn
import org.frc1778.util.pathing.events.*
import org.ghrobotics.lib.mathematics.units.*
import org.ghrobotics.lib.mathematics.units.derived.degrees
import org.ghrobotics.lib.wrappers.FalconTimedRobot

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
    private val autoPath3 = Path()
    private lateinit var auto: Path


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
                                val periodicFunction: () -> Unit = {        },
                                val autoInitFunction: () -> Unit = { /* No op by default */ } )
    {
        CUSTOM_AUTO_1("4 Ball?", ::autoMode1, ::autoMode1),
        CUSTOM_AUTO_2("2 Ball", ::autoMode2, ::autoMode2),
        CUSTOM_AUTO_3("1 Ball", ::autoMode3, ::autoMode3)
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
        Drive.auto = true
        Drive.resetEncoders()
        matchTimer.start()
        autoPath1.currSegment = 0
        Climber.winchMotorRight.encoder.resetPosition(SIUnit(0.0))
        Shooter.angleEncoder.resetPosition(SIUnit(0.0))


        autoPath1.run {
            add(CollectorDown)
            add(Wait(.35))
            add(CollectorOn)
            add(Line(41.inches.value, 0.degrees))
//            add(Line((-12).inches.value, 0.degrees, autoPath.path[3].timeToComplete))
            add(Turn((-220.5).degrees))
            add(Stop) //6
//
////Shoot
            add(CollectorOff)
            add(Wait(.45))
            add(ShooterOn)
            add(Wait(.95))
            add(LoaderOn) //10
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
            add(CollectorOn)
//
//            add(Line(87.5.inches.value, (-75.7).degrees, autoPath1.getLastTime()))
////
//            add(Line(156.7.inches.value, (-54).degrees, autoPath1.getLastTime()))
//            add(Line(120.inches.value, 165.degrees, autoPath1.getLastTime()))
////
//////Shoot
//            add(CollectorOff())
//            add(ShooterOn())
//            add(Wait(.95, autoPath1.getLastTime()))
//            add(LoaderOn()) //10
//            add(Wait(.1, autoPath1.getLastTime()))
//            add(LoaderOff())
//            add(Wait(.1, autoPath1.getLastTime()))
//            add(CollectorOn())
//            add(Wait(.15, autoPath1.getLastTime()))
//            add(LoaderOn())
//            add(Wait(.2, autoPath1.getLastTime()))
//            add(LoaderOff())
//            add(ShooterOff())
//            add(CollectorOff())
//            add(CollectorOn())

        }

        autoPath2.run {
            add(CollectorDown)
            add(Wait(.3))
            add(CollectorOn)
            add(Line(50.inches.value, 0.degrees))
            add(Turn(180.degrees))
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
            add(CollectorOn)

        }

        autoPath3.run {
            add(Line(-50.inches.value, 0.degrees))
            add(Stop)
            add(Aim)
            add(Wait(.35))
            add(ShooterOn)
            add(Wait(1.25))
            add(LoaderOn)
            add(Wait(.45))
            add(LoaderOff)
            add(ShooterOff)
        }


//        Shooter.setAngle()
        /* This autonomousInit function (along with the initAutoChooser function) shows how to select
        between different autonomous modes using the dashboard. The sendable chooser code works with the
        SmartDashboard. You can add additional auto modes by adding additional options to the AutoMode enum
        and then adding them to the `when` statement in the [autonomousPeriodic] function.

        If you prefer the LabVIEW Dashboard, remove all the chooser code and uncomment the following line: */
        //selectedAutoMode = AutoMode.valueOf(SmartDashboard.getString("Auto Selector", AutoMode.default.name))
        selectedAutoMode = autoModeChooser.selected ?: AutoMode.default
        println("Selected auto mode: ${selectedAutoMode.optionName}")
        selectedAutoMode.autoInitFunction.invoke()
    }

    /** This method is called periodically during autonomous.  */
    override fun autonomousPeriodic() {

        auto.runPath(matchTimer)



//        val dist = 2.feet
//        val timeToComplete = (dist / Constants.Drive.speed.value).value
//        if(matchTimer.get() < timeToComplete) {
//            Drive.drive()
//        } else {
//            Drive.curvatureDrive(0.0, 0.0, false)
//        }

//        if(matchTimer.get() < 1.0) {
//            Drive.curvatureDrive(-.12, 0.0, false)
////            Shooter.runShooter()
//        } else if(matchTimer.get() > 2.5 && matchTimer.get() < 4.0) {
//            Drive.curvatureDrive(0.0,0.0,false)
//            Loader.runLoader(.15)
//        } else if(matchTimer.get() > 4.0 && matchTimer.get() < 5.0 ){
//            Loader.runLoader(0.0)
//            Shooter.runShooter(0.0)
//        } else if(matchTimer.get() > 5.0 && matchTimer.get() < 5.3) {
//            Drive.curvatureDrive(-.15, 0.0, false)
//        } else if(matchTimer.get() > 5.5){
//            Drive.curvatureDrive(0.0, 0.0, false)
//        }

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

    /** This method is called once when teleop is enabled.  */
    override fun teleopInit() {
        Drive.auto = false
        Shooter.setAngle(SIUnit(.0))
//        Climber.winchMotorRight.encoder.resetPosition(SIUnit(0.0))
        Collector.deployMotor.setPosition(SIUnit(0.0))

    }

    /** This method is called periodically during operator control.  */
    override fun teleopPeriodic() {}

    /** This method is called once when the robot is disabled.  */
    override fun disabledInit() {}

    /** This method is called periodically when disabled.  */
    override fun disabledPeriodic() {}

    /** This method is called once when test mode is enabled.  */
    fun testInit() {}

    /** This method is called periodically during test mode.  */
    fun testPeriodic() {}
}