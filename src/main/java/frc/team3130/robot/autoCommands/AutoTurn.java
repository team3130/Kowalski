/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3130.robot.autoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team3130.robot.subsystems.Chassis;

public class AutoTurn extends CommandBase {

	private double angle;
	private double thresh;

	/**
	 * Creates a new AutoTurn.
	 */
	public AutoTurn() {
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(Chassis.getInstance());
	}

    /**
     * Sets the turn parameters
     * @param angle in degrees
     * @param threshold in degrees
     */
    public void setParam(double angle, double threshold){
    	this.angle=angle;
    	thresh=threshold;
    }

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		Chassis.ReleaseAngle();
		Chassis.holdAngle(angle);
		Chassis.getInstance().setAbsoluteTolerance(thresh);
		Chassis.driveStraight(0);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		Chassis.ReleaseAngle();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return Chassis.getInstance().onTarget();
	}
}
