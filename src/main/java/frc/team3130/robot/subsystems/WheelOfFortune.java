package frc.team3130.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.RobotMap;

import java.util.HashMap;
import java.util.Map;


public class WheelOfFortune implements Subsystem {

    //Create necessary objects
    private static ColorSensorV3 m_colorSensor;
    private static WPI_VictorSPX m_spinWheel;
    private static Solenoid m_wheelArm;

    //Create and define all standard data types needed
    private SuppliedValueWidget colorWidget =
            Shuffleboard.getTab("Kyber").addBoolean("Wheel Color", () -> true);

    private Map<String, String> fieldToTargetColorMap = new HashMap<String, String>();

    private double lastTimestamp;
    private boolean isChanged;
    private boolean isCounted;

    private String actualColor;

    private float deg = 0;
    private float sat = 0;
    private float brightness = 0;


    /**
     * The Singleton instance of this WheelOfFortune. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static WheelOfFortune INSTANCE = new WheelOfFortune();

    /**
     * Returns the Singleton instance of this WheelOfFortune. This static method
     * should be used -- {@code WheelOfFortune.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static WheelOfFortune getInstance() {
        return INSTANCE;
    }

    private WheelOfFortune() {
        m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

        m_spinWheel = new WPI_VictorSPX(RobotMap.CAN_WHEELOFFORTUNE);
        m_spinWheel.configFactoryDefault();

        isChanged = false;

        m_wheelArm = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_WHEELARM);

        m_wheelArm.set(false);

        //This is the Map for converting the fieldColor into targetColor, which can be used to clear a lot of confusion while making the algorithm
        fieldToTargetColorMap.put("Blue", "Red");
        fieldToTargetColorMap.put("Green", "Yellow");
        fieldToTargetColorMap.put("Red", "Blue");
        fieldToTargetColorMap.put("Yellow", "Green");

        actualColor = "Black"; // Initialize color tracker to black
    }

    public static String getTargetColor(String sourceColor) {
        return getInstance().fieldToTargetColorMap.get(sourceColor);
    }

    public String determineColor() { //TODO: check with motor

        String possibleColor = getInstance().detectHSB();

        if (!possibleColor.equals(actualColor)) {
            if (!isChanged) {
                lastTimestamp = Timer.getFPGATimestamp();
                isChanged = true;
                isCounted = false;
            } else {
                if (Timer.getFPGATimestamp() - lastTimestamp > .2 && !isCounted) {
                    isCounted = true;
                    isChanged = false;
                    actualColor = possibleColor;
                }
            }
        } else {
            isChanged = false;
        }
        return actualColor;
    }

    /**
     * Run the color match algorithm on our detected color
     *
     * @return String name of the most likely color
     */
    public String detectHSB() {
        int r = m_colorSensor.getRed();
        int g = m_colorSensor.getGreen();
        int b = m_colorSensor.getBlue();

        float[] hsb = java.awt.Color.RGBtoHSB(r, g, b, null);
        deg = hsb[0] * 360;
        sat = hsb[1];
        brightness = hsb[2];
        //Potential algorithm for rgb to hsb
        if (sat < 0.3 && brightness > 0.9) {
            return "White";
        } else if (brightness < 0.02) {
            return "Black";
        } else {
            if (deg < 60 || deg > 310) {
                return "Red";
            } else if (deg < 100) {
                return "Yellow";
            } else if (deg < 130) {
                return "Green";
            } else if (deg < 250) {
                return "Blue";
            } else {
                //System.out.println("bruh what color is this");
                return "Bruh";
            }
        }
    }


    /**
     * Method for toggling wheel of fortune manipulator
     */
    public static void toggleWheel() {
        System.out.println("Wheel has toggled");
        m_wheelArm.set(!m_wheelArm.get());
    }

    /**
     * method for retracting wheel to be called in a command
     */
    public static void retractWheel() {
        System.out.println("Wheel has retracted");
        m_wheelArm.set(false);
    }

    public static void motorSpin(double spin) {
        m_spinWheel.set(spin);
    }

    public static void outputToShuffleboard() {
        SmartDashboard.putString("HSB Detected color", getInstance().detectHSB());
        SmartDashboard.putNumber("Hue Degree", getInstance().deg); //TODO: remove these
        SmartDashboard.putNumber("Saturation", getInstance().sat);
        SmartDashboard.putNumber("Brightness", getInstance().brightness);
    }

    @Override
    public void periodic() {
        colorWidget.withProperties(Map.of("colorWhenTrue", getInstance().determineColor()));
    }

}
