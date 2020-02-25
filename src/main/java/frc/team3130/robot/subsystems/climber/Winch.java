package frc.team3130.robot.subsystems.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.RobotMap;

public class Winch implements Subsystem {

    //Create necessary objects
    private static WPI_TalonSRX m_skyWalker;
    private static WPI_TalonSRX m_climberWinchMaster;
    private static WPI_VictorSPX m_climberWinchSlave;


    //Create and define all standard data types needed

    /**
     * The Singleton instance of this Climber. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static Winch INSTANCE = new Winch();

    /**
     * Returns the Singleton instance of this Climber. This static method
     * should be used -- {@code Climber.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static Winch getInstance() {
        return INSTANCE;
    }

    private Winch() {
        // m_skyWalker = new WPI_TalonSRX(RobotMap.CAN_SKYWALKER);
        m_climberWinchMaster = new WPI_TalonSRX(RobotMap.CAN_CLIMBER1);
        m_climberWinchSlave = new WPI_VictorSPX(RobotMap.CAN_CLIMBER2);



    }

    /*
    public static void motorSpin(double spin) {
        m_skyWalker.set(spin);
    }
    */

    public static void leftFlier(double spin) {
        m_climberWinchMaster.set(spin);
    }

    public static void rightFlier(double spin) {
        m_climberWinchSlave.set(ControlMode.PercentOutput, spin);
    }

    public static void setBreakWinchMaster(){m_climberWinchMaster.setNeutralMode(NeutralMode.Brake);}
    public static void setBreakWinchSlave(){m_climberWinchMaster.setNeutralMode(NeutralMode.Brake);}

    public static void setCoastWinchMaster(){m_climberWinchMaster.setNeutralMode(NeutralMode.Coast);}
    public static void setCoastWinchSlave(){m_climberWinchMaster.setNeutralMode(NeutralMode.Coast);}


    //method for deploying wheel to be called in a command


    //method for deploying wheel to be called in a command


    //method for retracting climberLeia to be called in a command


    //method for retracting climberLuke to be called in a command

}

