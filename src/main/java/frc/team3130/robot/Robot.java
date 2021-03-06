package frc.team3130.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.team3130.robot.Auton.RendezvousShoot5;
import frc.team3130.robot.Auton.Shoot3;
import frc.team3130.robot.Auton.Shoot5;
import frc.team3130.robot.Auton.Shoot6;
import frc.team3130.robot.commands.Chassis.DefaultDrive;
import frc.team3130.robot.commands.Climber.SpinWinches;
import frc.team3130.robot.commands.Turret.ManualTurretAim;
import frc.team3130.robot.subsystems.*;
import frc.team3130.robot.vision.Limelight;
import frc.team3130.robot.vision.WheelSpeedCalculations;

import static frc.team3130.robot.OI.driverGamepad;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    CommandScheduler scheduler = CommandScheduler.getInstance();
    CommandBase autonomousCommand = null;
    private SendableChooser<String> chooser = new SendableChooser<String>();
    private static Timer timer;
    private static double lastTimestamp;

    boolean gettime = true;
    boolean checkif = true;


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        timer = new Timer();
        timer.reset();
        timer.start();

        //Instantiate operator interface
        OI.GetInstance();

        //Instantiate Limelight interface
        Limelight.GetInstance();

        //Instantiate Navx
        Navx.GetInstance();

        //Instantiate Wheel Speed interpolator
        WheelSpeedCalculations.GetInstance();

        //Register and instantiate subsystems (optionally with default commands)
        //Note: registerSubsystem is NOT needed if setDefaultCommand is used
        scheduler.setDefaultCommand(Chassis.getInstance(), new DefaultDrive());
        scheduler.setDefaultCommand(Climber.getInstance(), new SpinWinches());
        scheduler.setDefaultCommand(Turret.getInstance(), new ManualTurretAim());
        scheduler.registerSubsystem(Intake.getInstance());
        scheduler.registerSubsystem(Hopper.getInstance());
        scheduler.registerSubsystem(Flywheel.getInstance());
        scheduler.registerSubsystem(WheelOfFortune.getInstance());

        Limelight.GetInstance().setLedState(false); //Turn vision tracking off when robot boots up


        /*
         chooser = new SendableChooser<>();
         chooser.setDefaultOption("No Auton", "None");
         chooser.addOption("3Ball", "3Ball");
         chooser.addOption("6Ball", "6Ball");
         SmartDashboard.putData("Auto mode", chooser);
         */
    }

    @Override
    public void disabledInit() {
        Chassis.configBrakeMode(false);
        Intake.retractIntake();
        //Hood.setPistons(false);
        WheelOfFortune.retractWheel();
        Climber.retractClimb();
        Limelight.GetInstance().setLedState(false); //Turn vision tracking off when robot disables
    }

    @Override
    public void disabledPeriodic() {

    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        outputToShuffleboard();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        determineAuto();

        // Schedule autonomous command if it exists
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        Limelight.GetInstance().updateData();
        scheduler.run();
        writePeriodicOutputs();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }

        Chassis.configBrakeMode(true);
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Limelight.GetInstance().updateData();
        scheduler.run();
        writePeriodicOutputs();
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    public void outputToShuffleboard() {
//        Navx.GetInstance().outputToShuffleboard();
//        WheelOfFortune.outputToShuffleboard();
//        Chassis.outputToShuffleboard();
        Turret.outputToShuffleboard();
//        Hopper.outputToShuffleboard();
        Limelight.GetInstance().outputToShuffleboard();
        Flywheel.outputToShuffleboard();
        WheelSpeedCalculations.GetInstance().outputToShuffleboard();

        //TODO: move this somewhere logical
        if (RobotState.isEnabled() && Turret.isOnTarget() && checkif) {
            if (gettime == true) {
                lastTimestamp = Timer.getFPGATimestamp();
                gettime = false;
            }
            driverGamepad.setRumble(GenericHID.RumbleType.kRightRumble, 1);
            driverGamepad.setRumble(GenericHID.RumbleType.kLeftRumble, 1);
            if (Timer.getFPGATimestamp() - lastTimestamp > .3) {
                checkif = false;
            }
        } else {
            driverGamepad.setRumble(GenericHID.RumbleType.kRightRumble, 0);
            driverGamepad.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
            gettime = true;
            if (Turret.isOnTarget() == false) {
                checkif = true;
            }
        }


    }

    private void determineAuto() {
//          autonomousCommand = new RendezvousShoot5();
//        autonomousCommand = new Shoot3();
//        autonomousCommand = new Shoot5();
        autonomousCommand = new Shoot6();
    }

    public void writePeriodicOutputs() {
        Turret.getInstance().writePeriodicOutputs();
    }

    public void resetSubsystems() {

    }
}
