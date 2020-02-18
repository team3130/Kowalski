package frc.team3130.robot.commands.Auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.commands.Turret.ToggleTurretAim;
import frc.team3130.robot.subsystems.Chassis;
import frc.team3130.robot.subsystems.Turret;

import java.util.Set;

import static frc.team3130.robot.subsystems.Turret.isTurretAiming;
import static frc.team3130.robot.subsystems.Turret.setAimState;

public class TurnAndShoot implements Command {
    private final Set<Subsystem> subsystems;


    public TurnAndShoot() {
        this.subsystems = Set.of(Turret.getInstance());

    }
    public void initialize(){
        Turret.toggleAimState();

    }

    public void execute(){
        
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
