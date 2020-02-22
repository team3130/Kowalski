package frc.team3130.robot.commands.Hopper;

import java.util.Set;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team3130.robot.RobotMap;
import frc.team3130.robot.subsystems.Flywheel;
import frc.team3130.robot.subsystems.Hopper;
import frc.team3130.robot.subsystems.Turret;

public class HopperIn implements Command {
    private final Set<Subsystem> subsystems;

    private boolean justShot;
    private boolean changedState;
    private boolean hasIndexed;
    private boolean isShooting;
    private double lastIndexTime;

    private boolean isAuton;

    //auton mode
    public HopperIn(boolean inAuton){
        this();
        isAuton=inAuton;
    }

    public HopperIn() {
        this.subsystems = Set.of(Hopper.getInstance());
        justShot = true;
        hasIndexed = false;
        isShooting = false;
        changedState = true;
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        justShot = true;
        changedState = true;
        hasIndexed = false;
        isShooting = false;
        lastIndexTime = Timer.getFPGATimestamp();
    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {
        if (justShot) {
            if (changedState) {
                lastIndexTime = Timer.getFPGATimestamp();
                changedState = false;
                hasIndexed = true;
            }
            if (Hopper.isEmpty()) {
                lastIndexTime = Timer.getFPGATimestamp();
                Hopper.runHopperTop(0.25);
                Hopper.runHopperLeft(0.33);
                Hopper.runHopperRight(-0.33);
                hasIndexed = false;
            } else {
                Hopper.runHopperTop(0.0);
                Hopper.runHopperLeft(0.0);
                Hopper.runHopperRight(0.0);
                if (Timer.getFPGATimestamp() - lastIndexTime > RobotMap.kHopperChamberPause) {
                    justShot = false;
                    changedState = true;
                }

            }
        } else {
            if (changedState && Flywheel.canShoot()) {
                Hopper.runHopperTop(0.6);
                isShooting = true;
                changedState = false;
            } else {
                if (isShooting) {
                    if (!Flywheel.canShoot()) {
                        isShooting = false;
                    }
                } else {
                    Hopper.runHopperTop(0.0);
                    justShot = true;
                    changedState = true;
                }
            }
        }
    }

    /**
     * <p>
     * Returns whether this command has finished. Once a command finishes -- indicated by
     * this method returning true -- the scheduler will call its {@link #end(boolean)} method.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Hard coding this command to always
     * return true will result in the command executing once and finishing immediately. It is
     * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand}
     * for such an operation.
     * </p>
     *
     * @return whether this command has finished.
     */
    @Override
    public boolean isFinished() {
        if(isAuton){//shoot until empty in Auton
            return !Hopper.isEmpty();
        }else{
            return false;
        }
    }

    /**
     * The action to take when the command ends. Called when either the command
     * finishes normally -- that is it is called when {@link #isFinished()} returns
     * true -- or when  it is interrupted/canceled. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the command.
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    @Override
    public void end(boolean interrupted) {
        justShot = true;
        changedState = true;
        Hopper.runHopperLeft(0.0);
        Hopper.runHopperRight(0.0);
        Hopper.runHopperTop(0.0);
    }

    /**
     * <p>
     * Specifies the set of subsystems used by this command.  Two commands cannot use the same
     * subsystem at the same time.  If the command is scheduled as interruptible and another
     * command is scheduled that shares a requirement, the command will be interrupted.  Else,
     * the command will not be scheduled. If no subsystems are required, return an empty set.
     * </p><p>
     * Note: it is recommended that user implementations contain the requirements as a field,
     * and return that field here, rather than allocating a new set every time this is called.
     * </p>
     *
     * @return the set of subsystems that are required
     */
    @Override
    public Set<Subsystem> getRequirements() {
        return this.subsystems;
    }
}
