package frc.team3130.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team3130.robot.commands.Hopper.HopperIn;
import frc.team3130.robot.commands.Hopper.HopperOut;
import frc.team3130.robot.commands.Intake.IntakeIn;
import frc.team3130.robot.commands.Intake.IntakeOut;
import frc.team3130.robot.commands.WheelOfFortune.ColorAlignment;
import frc.team3130.robot.commands.WheelOfFortune.TestHSB;
import frc.team3130.robot.commands.WheelOfFortune.TripleSpinFinish;
import frc.team3130.robot.controls.JoystickTrigger;

public class OI {


    //Instance Handling
    private static OI m_pInstance;

    public static OI GetInstance() {
        if (m_pInstance == null) m_pInstance = new OI();
        return m_pInstance;
    }


    public static double getSkywalker() {
        double spin = 0;
        spin += driverGamepad.getRawAxis(RobotMap.LST_AXS_RTRIGGER);
        spin -= driverGamepad.getRawAxis(RobotMap.LST_AXS_LTRIGGER);
        return spin;
    }

    //Joysticks
    public static Joystick driverGamepad = new Joystick(0);
    public static Joystick weaponsGamepad = new Joystick(1);

    /**
     * Definitions for joystick buttons start
     */
    private static JoystickButton spinShooter = new JoystickButton(driverGamepad, RobotMap.LST_BTN_X);
    private static JoystickButton testColorAlignment = new JoystickButton(driverGamepad, RobotMap.LST_BTN_Y);
    private static JoystickButton testTripleSpinFinish = new JoystickButton(driverGamepad, RobotMap.LST_BTN_B);
    private static JoystickTrigger intakeIn = new JoystickTrigger(driverGamepad, RobotMap.LST_BTN_RBUMPER);
    private static JoystickButton intakeOut = new JoystickButton(driverGamepad, RobotMap.LST_BTN_LBUMPER);
    private static JoystickTrigger hopperIn = new JoystickTrigger(driverGamepad, RobotMap.LST_AXS_RTRIGGER);
    private static JoystickButton hopperOut = new JoystickButton(driverGamepad, RobotMap.LST_BTN_RBUMPER);
    private static JoystickButton testTestHSB = new JoystickButton(driverGamepad, RobotMap.LST_BTN_A);

    public void checkTriggers() {
        //Driver
        if (Math.abs(OI.driverGamepad.getRawAxis(RobotMap.LST_AXS_LTRIGGER)) >= RobotMap.kIntakeTriggerDeadband) {

        } else {

        }
        if (Math.abs(OI.driverGamepad.getRawAxis(RobotMap.LST_AXS_RTRIGGER)) >= RobotMap.kIntakeTriggerDeadband) {

        } else {

        }
    }

    //Settings for gamepad
    private OI() {
        intakeIn.whenActive(new IntakeIn());
        intakeOut.whileHeld(new IntakeOut());
        hopperIn.whenActive(new HopperIn());
        hopperOut.whileHeld(new HopperOut());
        testTripleSpinFinish.whenPressed(new TripleSpinFinish());
        testColorAlignment.whenPressed(new ColorAlignment());
        testTestHSB.whenActive(new TestHSB());
    }
}

