/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3130.robot.autoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team3130.robot.subsystems.Turret;

public class AutoTurretAngle extends CommandBase {
	private double angle;

	/**
	 * Creates a new AutoTurretAngle.
	 */
	public AutoTurretAngle() {
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(Turret.getInstance());
	}

	public void setParam(double angle){
		this.angle=angle;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		Turret.setAngle(angle);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return Turret.isOnTarget();
	}
}
