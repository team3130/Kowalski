package frc.team3130.robot.subsystems.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.RobotMap;

public class ArmClimber implements Subsystem {

    private static Solenoid m_Leia;
    private static Solenoid m_Luke;

    //Create and define all standard data types needed

    /**
     * The Singleton instance of this Climber. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static ArmClimber INSTANCE = new ArmClimber();

    /**
     * Returns the Singleton instance of this Climber. This static method
     * should be used -- {@code Climber.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static ArmClimber getInstance() {
        return INSTANCE;
    }

    private ArmClimber() {
        m_Leia = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_LEIA);
        m_Luke = new Solenoid(RobotMap.CAN_PNMMODULE, RobotMap.PNM_LUKE);

    }

    /*
    public static void motorSpin(double spin) {
        m_skyWalker.set(spin);
    }
    */

    //method for deploying wheel to be called in a command
    public static void deployLeia() {
        m_Leia.set(true);
    }

    //method for deploying wheel to be called in a command
    public static void deployLuke() {
        m_Luke.set(true);
    }

    //method for retracting climberLeia to be called in a command
    public static void RetractLeia() {
        m_Leia.set(false);
    }

    //method for retracting climberLuke to be called in a command
    public static void retractLuke() {
        m_Leia.set(false);
    }
}

