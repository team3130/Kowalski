package frc.team3130.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.RobotMap;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import frc.team3130.robot.sensors.Navx;

import static frc.team3130.robot.RobotMap.kChassisCodesPerRev;
import static frc.team3130.robot.RobotMap.kLWheelDiameter;


public class Chassis implements Subsystem {

    //Create necessary objects
    private static WPI_TalonFX m_leftMotorFront;
    private static WPI_TalonFX m_leftMotorRear;
    private static WPI_TalonFX m_rightMotorFront;
    private static WPI_TalonFX m_rightMotorRear;
    

    private static DifferentialDrive m_drive;

    private static Solenoid m_shifter;



    //DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(RobotMap.kChassisWidth));
    public static DifferentialDriveOdometry m_odometry;
    //Create and define all standard data types needed

    /**
     * The Singleton instance of this Chassis. External classes should use the
     * {@link #getInstance()} method to get the instance.
     */
    private final static Chassis INSTANCE = new Chassis();

    /**
     * Returns the Singleton instance of this Chassis. This static method should be
     * used -- {@code Chassis.getInstance();} -- by external classes, rather than
     * the constructor to get the instance of this class.
     */
    public static Chassis getInstance() {
        return INSTANCE;
    }

    private Chassis() {
        m_leftMotorFront = new WPI_TalonFX(RobotMap.CAN_LEFTMOTORFRONT);
        m_leftMotorRear = new WPI_TalonFX(RobotMap.CAN_LEFTMOTORREAR);
        m_rightMotorFront = new WPI_TalonFX(RobotMap.CAN_RIGHTMOTORFRONT);
        m_rightMotorRear = new WPI_TalonFX(RobotMap.CAN_RIGHTMOTORREAR);

        m_leftMotorFront.configFactoryDefault();
        m_leftMotorRear.configFactoryDefault();
        m_rightMotorFront.configFactoryDefault();
        m_rightMotorRear.configFactoryDefault();

        configBrakeMode(false); // Set to coast on cstr

        m_leftMotorRear.set(ControlMode.Follower, RobotMap.CAN_LEFTMOTORFRONT);
        m_rightMotorRear.set(ControlMode.Follower, RobotMap.CAN_RIGHTMOTORFRONT);

        /**
         * For all motors, forward is the positive direction
         *
         * Shift false is low gear
         */

        m_shifter = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_SHIFT);

        m_shifter.set(false);

        m_rightMotorFront.configVoltageCompSaturation(RobotMap.kChassisMaxVoltage);
        m_leftMotorFront.configVoltageCompSaturation(RobotMap.kChassisMaxVoltage);
        m_rightMotorFront.enableVoltageCompensation(true);
        m_leftMotorFront.enableVoltageCompensation(true);


        m_rightMotorFront.setInverted(true);
        m_leftMotorFront.setInverted(false);
        m_rightMotorRear.setInverted(true);
        m_leftMotorRear.setInverted(false);

        m_rightMotorFront.setSensorPhase(true);
        m_leftMotorFront.setSensorPhase(true);

        m_leftMotorFront.overrideLimitSwitchesEnable(false);
        m_rightMotorFront.overrideLimitSwitchesEnable(false);

        SpeedControllerGroup m_left = new SpeedControllerGroup(m_leftMotorFront, m_leftMotorRear);
        SpeedControllerGroup m_right = new SpeedControllerGroup(m_rightMotorFront, m_rightMotorRear);

        m_drive = new DifferentialDrive(m_left, m_right);
        m_drive.setRightSideInverted(false); //Motor inversion is already handled by talon configs
        m_drive.setDeadband(RobotMap.kDriveDeadband);
        m_drive.setSafetyEnabled(true); //feed() must be called to prevent motor disable
    }

    /**
     * Drive the robot using tank mode
     *
     * @param moveL         Left input throttle
     * @param moveR         Right input throttle
     * @param squaredInputs Whether or not to use squared inputs
     */
    public static void driveTank(double moveL, double moveR, boolean squaredInputs) {
        //NOTE: DifferentialDrive uses set(), which sets a speed in PercentOutput mode for Talons/Victors
        m_drive.tankDrive(moveL, moveR, squaredInputs);
    }

    /**
     * Drive the robot using arcade mode
     *
     * @param moveThrottle  Base forward and backward speed to move at. Positive is
     *                      forward
     * @param turnThrottle  Turning velocity
     * @param squaredInputs Whether or not to use squared inputs
     */
    public static void driveArcade(double moveThrottle, double turnThrottle, boolean squaredInputs) {
        //NOTE: DifferentialDrive uses set(), which sets a speed in PercentOutput mode for Talons/Victors
        m_drive.arcadeDrive(moveThrottle, turnThrottle, squaredInputs);
    }

    /**
     * Shifts the drivetrain gear box into an absolute gear
     *
     * @param shiftVal true is high gear, false is low gear
     */
    public static void shift(boolean shiftVal) {
        m_shifter.set(shiftVal);
    }

    /**
     * Tell the Chassis to hold a relative angle
     *
     * @param angle angle to hold in degrees
     */
    public static void holdAngle(double angle) {
        // TODO: Rework
    }

    /**
     * Reset the drivetrain encoder positions to 0
     */
    public static void reset() {
        m_leftMotorFront.setSelectedSensorPosition(0);
        m_rightMotorFront.setSelectedSensorPosition(0);
    }

    /**
     * Returns if robot is in low gear
     *
     * @return true means the robot is in low gear, false if it's in high gear
     */

    public static boolean getShift() {
        return m_shifter.get();
    }

    public static boolean isLowGear() {
        return !m_shifter.get();
    }

    public Pose2d getPose(){
        return m_odometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose){
        reset();
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(Navx.getHeading()));
    }
    /**
     * Gets absolute distance traveled by the left side of the robot
     *
     * @return The absolute distance of the left side in inches
     */
    public static double getDistanceL() {
        return m_leftMotorFront.getSelectedSensorPosition(0) / RobotMap.kLChassisTicksPerInch;
    }

    /**
     * Gets absolute distance traveled by the right side of the robot
     *
     * @return The absolute distance of the right side in inches
     */
    public static double getDistanceR() {
        return m_rightMotorFront.getSelectedSensorPosition(0) / RobotMap.kRChassisTicksPerInch;
    }

    /**
     * Gets the absolute distance traveled by the robot
     *
     * @return The absolute distance traveled of robot in inches
     */
    public static double getDistance() {
        return (getDistanceL() + getDistanceR()) / 2.0; //the average of the left and right distances
    }

    /**
     * Returns the current speed of the front left motor in native units
     *
     * @return Current speed of the front left motor (ticks per 0.1 seconds)
     */
    public static double getRawSpeedL() {
        return m_leftMotorFront.getSelectedSensorVelocity(0);
    }

    /**
     * Returns the current speed of the front left motor in native units
     *
     * @return Current speed of the front left motor (ticks per 0.1 seconds)
     */
    public static double getRawSpeedR() {
        return m_rightMotorFront.getSelectedSensorVelocity(0);
    }

    /**
     * Returns the current speed of the front left motor
     *
     * @return Current speed of the front left motor (inches per second)
     */
    public static double getSpeedL() {
        // The raw speed units will be in the sensor's native ticks per 100ms.
        return 10.0 * getRawSpeedL() / RobotMap.kLChassisTicksPerInch;
    }

    /**
     * Returns the current speed of the front right motor
     *
     * @return Current speed of the front right motor (inches per second)
     */
    public static double getSpeedR() {
        // The raw speed units will be in the sensor's native ticks per 100ms.
        return 10.0 * getRawSpeedR() / RobotMap.kRChassisTicksPerInch;
    }

    /**
     * Returns the current speed of the robot by averaging the front left and right motors
     *
     * @return Current speed of the robot
     */
    public static double getSpeed() {
        return 0.5 * (getSpeedL() + getSpeedR());
    }

    /**
     * @return Raw absolute encoder ticks of the left side of the robot
     */
    public static double getRawL() {
        return m_leftMotorFront.getSelectedSensorPosition(0);
    }

    /**
     * @return Raw absolute encoder ticks of the right side of the robot
     */
    public static double getRawR() {
        return m_rightMotorFront.getSelectedSensorPosition(0);
    }

    /**
     * @return Returns the left master drive Talon
     */
    public static WPI_TalonFX getFrontL() {
        return m_leftMotorFront;
    }

    /**
     * @return Returns the right master drive Talon
     */
    public static WPI_TalonFX getFrontR() {
        return m_rightMotorFront;
    }

    //Configs
    
    /**
     * Set the drive talons to either Brake or Coast mode
     *
     * @param brake true for Brake mode, false for Coast mode
     */
    public static void configBrakeMode(boolean brake) {
        if (brake) {
            m_leftMotorFront.setNeutralMode(NeutralMode.Brake);
            m_leftMotorRear.setNeutralMode(NeutralMode.Brake);
            m_rightMotorFront.setNeutralMode(NeutralMode.Brake);
            m_rightMotorRear.setNeutralMode(NeutralMode.Brake);
        } else {
            m_leftMotorFront.setNeutralMode(NeutralMode.Coast);
            m_leftMotorRear.setNeutralMode(NeutralMode.Coast);
            m_rightMotorFront.setNeutralMode(NeutralMode.Coast);
            m_rightMotorRear.setNeutralMode(NeutralMode.Coast);
        }
    }

    /**
     * Configure the maximum ramping rate of the drivetrain while in Open Loop control mode
     * <p>
     * Value of 0 disables ramping
     *
     * @param maxRampRateSeconds Minimum desired time to go from neutral to full throttle
     */
    public static void configRampRate(double maxRampRateSeconds) {
        m_rightMotorFront.configOpenloopRamp(maxRampRateSeconds);
        m_leftMotorFront.configOpenloopRamp(maxRampRateSeconds);
    }

    /**
     * Configure the drivetrain for motion profiling
     *
     * @param duration fire rate of the motion profile in ms
     */
    public static void configMP(int duration) {

        //left
        m_leftMotorFront.config_kP(0, RobotMap.kMPChassisP, 0);
        m_leftMotorFront.config_kI(0, RobotMap.kMPChassisI, 0);
        m_leftMotorFront.config_kD(0, RobotMap.kMPChassisD, 0);
        m_leftMotorFront.config_kF(0, RobotMap.kMPChassisF, 0);
        m_leftMotorFront.configNeutralDeadband(RobotMap.kChassisMPOutputDeadband, 0);
        // Status 10 provides the trajectory target for motion profile AND motion magic
        m_leftMotorFront.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, duration, 0);
        //Profile already assumes base time is 0
        m_leftMotorFront.configMotionProfileTrajectoryPeriod(0, 0);

        //right
        m_rightMotorFront.config_kP(0, RobotMap.kMPChassisP, 0);
        m_rightMotorFront.config_kI(0, RobotMap.kMPChassisI, 0);
        m_rightMotorFront.config_kD(0, RobotMap.kMPChassisD, 0);
        m_rightMotorFront.config_kF(0, RobotMap.kMPChassisF, 0);
        m_rightMotorFront.configNeutralDeadband(RobotMap.kChassisMPOutputDeadband, 0);
        // Status 10 provides the trajectory target for motion profile AND motion magic
        m_rightMotorFront.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, duration, 0);
        //Profile already assumes base time is 0
        m_rightMotorFront.configMotionProfileTrajectoryPeriod(0, 0);
    }


    public static void outputToShuffleboard() {
        SmartDashboard.putNumber("Chassis Right Velocity", getRawSpeedR());
        SmartDashboard.putNumber("Chassis Left Velocity", getRawSpeedL());

        //SmartDashboard.putNumber("Chassis Right Vel Traj", m_rightMotorFront.getActiveTrajectoryVelocity(0));
        //SmartDashboard.putNumber("Chassis Left Vel Traj", m_leftMotorFront.getActiveTrajectoryVelocity(0));
        SmartDashboard.putNumber("Chassis Right Speed", getSpeedR());
        SmartDashboard.putNumber("Chassis Left Speed", getSpeedL());

        SmartDashboard.putNumber("Chassis Distance R", getDistanceR());
        SmartDashboard.putNumber("Chassis Distance L", getDistanceL());

        SmartDashboard.putNumber("Chassis Right Sensor Value", getRawR());
        SmartDashboard.putNumber("Chassis Left Sensor Value", getRawL());

        SmartDashboard.putNumber("Chassis Right Output %", m_rightMotorFront.getMotorOutputPercent());
        SmartDashboard.putNumber("Chassis Left Output %", m_leftMotorFront.getMotorOutputPercent());

    }

}
