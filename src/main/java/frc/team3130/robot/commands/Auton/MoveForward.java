package frc.team3130.robot.commands.Auton;

//Put Imports Here

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.subsystems.Chassis;
import frc.team3130.robot.subsystems.WheelOfFortune;

import java.util.Set;


public class MoveForward implements Command {
    private final Set<Subsystem> subsystems;

    public MoveForward() {
        this.subsystems = Set.of(Chassis.getInstance());


    }

    double time;

    @Override
    public void initialize() {

         time = Timer.getFPGATimestamp();

         /*  This is what Tank Drive does
            LeftMotor1.set(0.6);
            LeftMotor2.set(0.6);
            RightMotor1.set(-0.6);
            RightMotor2.set(-0.6);
         */

        Chassis.driveTank(.6, .6, false);
        }

    @Override
    public void end(boolean interrupted) {

        /*
        LeftMotor1.set(0);
        LeftMotor2.set(0);
        RightMotor1.set(0);
        RightMotor2.set(0);
        */

        Chassis.driveTank(0, 0, false);
    }

    @Override
    public boolean isFinished() {
        double currentTime = Timer.getFPGATimestamp();
        return (currentTime - time > 0.5);
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return null;
    }
}
