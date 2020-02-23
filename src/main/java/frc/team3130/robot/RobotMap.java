package frc.team3130.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    /**
     * Constants
     */
    //Which Robot
    public static boolean kUseCompbot = true;

    //Chassis
    public static double kChassisMaxVoltage = 12.0;

    public static double kChassisWidth = 23.0; //FIXME
    public static double kChassisLengthBumpers = 39.0; //FIXME
    public static double kLWheelDiameter = 6.0; // Center wheel
    public static double kRWheelDiameter = 6.0; // Center wheel

    public static double kMaxHighGearDriveSpeed = 0.8;
    public static double kMaxTurnThrottle = 0.7; // Applied on top of max drive speed

    public static double kChassisCodesPerRev = 2048;
    public static double kLChassisTicksPerInch = kChassisCodesPerRev / (Math.PI * kLWheelDiameter);
    public static double kRChassisTicksPerInch = kChassisCodesPerRev / (Math.PI * kRWheelDiameter);

    public static double kDriveDeadband = 0.02;
    public static double kDriveMaxRampRate = 0.7; // Minimum seconds from 0 to 100

    //Motion Profiling
    public static double kChassisMinPointsInBuffer = 5;
    public static double kChassisMPOutputDeadband = 0.01;
    public static int kChassisMPDefaultFireRate = 20;

    public static double kMPChassisP = 5.47;
    public static double kMPChassisI = 0.0;
    public static double kMPChassisD = 0.0;
    public static double kMPChassisF = 1023.0 / (92.0 * (kLChassisTicksPerInch + kRChassisTicksPerInch) / 2.0); //Checked 3/23
    public static double kChassisShiftWait = 0.07;

    public static double kMPMaxVel = 115.0; //maximum achievable velocity of the drivetrain in in/s NOTE: the actual motion profile should be generated at 80% of this
    public static double kMPMaxAcc = 60.0; ///maximum achievable acceleration of the drivetrain in in/s^2 NOTE: the actual motion profile should be generated at 80% of this


    public static double kDistanceToEncoder = kChassisCodesPerRev / (Math.PI * 0.5 * (kLWheelDiameter + kRWheelDiameter));
    public static double kVelocityToEncoder = kDistanceToEncoder / 10.0;        // Per 100ms
    public static double kAccelerationToEncoder = kVelocityToEncoder / 10.0;    // Per 100ms


    //Turret

    // Turret pitch and roll is how much the plane of the turret's rotation isn't level
    public static final double kTurretPitch = (kUseCompbot ? -0.31 : -0.875); // Drop forward in degrees
    public static final double kTurretRoll = 0; // Roll to the right in degrees

    public static double kTurretManualDeadband = 0.09;
    public static double kTurretManualMultipler = 0.15;

    public static double kTurretStartupAngle = -90.0;
    public static double kTurretFwdLimit = 190.0; // Angle in degrees
    public static double kTurretRevLimit = 190.0; // Angle in degrees

    public static double kTurretP = 1.4; // PID checked 2/6
    public static double kTurretI = 0;
    public static double kTurretD = 210.0;
    public static double kTurretF = 0;

    public static double kTurretTicksPerDegree = (kUseCompbot ? (1.0 / 360.0) * 4096.0 * (204.0 / 32.0) : (1.0 / 360.0) * 4096.0 * (204.0 / 30.0)); // Checked 1/31
    public static double kTurretOnTargetTolerance = 0.5;

    //Limelight

    public static int kLimelightFilterBufferSize = 5; // Number of samples in input filtering window
    public static double kLimelightLatencyMs = 11.0; // Image capture latency

    public static double kLimelightPitch = (kUseCompbot ? -27.26 : -31.625);   // Facing up is negative Checked: 2/21
    public static double kLimelightYaw = (kUseCompbot ? 0: 3.1);        // Aiming bias, facing left is positive FIXME: calibrate
    public static double kLimelightRoll = 0;       // If any, drooping to right is positive
    public static double kLimelightHeight = 22.5;     // Height of camera aperture from the ground
    public static double kLimelightLength = 9.5;    // Distance to the turret's rotation axis
    public static double kLimelightOffset = 0;      // Side offset from the turret's plane of symmetry (left+)
    public static double kLimelightCalibrationDist = 120.0; // Exact horizontal distance between target and lens


    //Flywheel
    public static double kFlywheelMaxVoltage = 12.0;
    public static double kFlywheelOpenRampRate = 1.0; // Minimum amount to time in seconds for Open Loop control output to ramp up

    public static double kFlywheelP = 0.22; //Checked 2/14
    public static double kFlywheelI = 0.0;
    public static double kFlywheelD = 12.0;
    public static double kFlywheelF = (0.51 * 1023.0) / 10650.0; // Checked 2/11, Optimal speed at 51% power

    public static double kFlywheelTicksPerRevolution = 2048.0 * (24.0 / 60.0); // Checked 2/11
    public static double kFlywheelRPMtoNativeUnitsScalar = RobotMap.kFlywheelTicksPerRevolution / (10.0 * 60.0);
    public static double kFlywheelReadyTolerance = 60.0; // In RPM FIXME: might be why we have variation while shooting

    //Hopper
    public static double kHopperMaxVoltage = 12.0;
    public static double kHopperChamberPause = 0.3;

    //Intake
    public static double kIntakeTriggerDeadband = 0.4;

    /**
     * Field parameters
     */
    public static final double VISIONTARGETHEIGHT = 98.25; // Height of vision target center

    /**
     * Digital I/O ports
     */
    public static final int DIO_FEEDERBEAM = 0;

    /**
     * CAN IDs
     */
    public static final int CAN_PNMMODULE = 1;

    public static final int CAN_RIGHTMOTORFRONT = 2;
    public static final int CAN_RIGHTMOTORREAR = 3;
    public static final int CAN_LEFTMOTORFRONT = 4;
    public static final int CAN_LEFTMOTORREAR = 5;

    public static final int CAN_WHEELOFFORTUNE = 15;

    // public static final int CAN_SKYWALKER = 106;
    public static final int CAN_CLIMBER1 = 7;
    public static final int CAN_CLIMBER2 = 11;

    public static final int CAN_TURRETANGLE = 6;
    public static final int CAN_FLYWHEEL1 = 14;
    public static final int CAN_FLYWHEEL2 = 13;

    public static final int CAN_INTAKE = 10;

    public static final int CAN_HOPPERL = 8;
    public static final int CAN_HOPPERR = 9;
    public static final int CAN_HOPPERTOP = 12;
    /**
     * Pneumatics ports
     */
    public static final int PNM_SHIFT = 0;
    public static final int PNM_INTAKE = 1;
    public static final int PNM_CLIMBERARM2 = 2;
    public static final int PNM_WHEELARM = 3;
    public static final int PNM_HOODPISTONS = 4;
    public static final int PNM_CLIMBERARM1 = 5;


    /**
     * Gamepad Button List
     */
    public static final int LST_BTN_A = 1;
    public static final int LST_BTN_B = 2;
    public static final int LST_BTN_X = 3;
    public static final int LST_BTN_Y = 4;
    public static final int LST_BTN_LBUMPER = 5;
    public static final int LST_BTN_RBUMPER = 6;
    public static final int LST_BTN_WINDOW = 7;
    public static final int LST_BTN_MENU = 8;
    public static final int LST_BTN_LJOYSTICKPRESS = 9;
    public static final int LST_BTN_RJOYSTICKPRESS = 10;

    /**
     * Gamepad POV List
     */
    public static final int LST_POV_UNPRESSED = -1;
    public static final int LST_POV_N = 0;
    public static final int LST_POV_NE = 45;
    public static final int LST_POV_E = 90;
    public static final int LST_POV_SE = 135;
    public static final int LST_POV_S = 180;
    public static final int LST_POV_SW = 225;
    public static final int LST_POV_W = 270;
    public static final int LST_POV_NW = 315;


    /**
     * Gamepad Axis List
     */
    public static final int LST_AXS_LJOYSTICKX = 0;
    public static final int LST_AXS_LJOYSTICKY = 1;
    public static final int LST_AXS_LTRIGGER = 2;
    public static final int LST_AXS_RTRIGGER = 3;
    public static final int LST_AXS_RJOYSTICKX = 4;
    public static final int LST_AXS_RJOYSTICKY = 5;
}


