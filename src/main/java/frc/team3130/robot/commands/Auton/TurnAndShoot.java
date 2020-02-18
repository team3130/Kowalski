package frc.team3130.robot.commands.Auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.commands.Turret.ToggleTurretAim;
import frc.team3130.robot.subsystems.Chassis;
import frc.team3130.robot.subsystems.Turret;
import frc.team3130.robot.subsystems.Flywheel;

import java.util.Set;

import static frc.team3130.robot.subsystems.Turret.*;

public class TurnAndShoot implements Command {
    private final Set<Subsystem> subsystems;


    public TurnAndShoot() {
        this.subsystems = Set.of(Turret.getInstance());


    }

    double time;

    public void initialize(){
        time = Timer.getFPGATimestamp();

        Turret.toggleAimState();

    }

    public void execute(){
        double currentTime;
        currentTime = Timer.getFPGATimestamp();
        double differenceInTime = currentTime - time;

        if(Turret.isOnTarget() == true && differenceInTime >= 0.3 && differenceInTime <= 3.0) {
            Flywheel.setOpenLoop(0.7);
        }

    }



    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return null;
    }

}
