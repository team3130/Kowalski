/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3130.robot.autoCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team3130.robot.commands.Flywheel.SpinFlywheel;
import frc.team3130.robot.commands.Hopper.HopperIn;
import frc.team3130.robot.commands.Intake.IntakeIn;
import frc.team3130.robot.commands.Turret.ToggleTurretAim;

public class Shoot8 extends SequentialCommandGroup {

	AutoDriveStraightToPoint driveBackIntake;
	AutoDriveStraightToPoint driveUp;
	
	/**
	 * Creates a new Shoot8.
	 */
	public Shoot8() {
		driveBackIntake = new AutoDriveStraightToPoint();
		driveUp = new AutoDriveStraightToPoint();

		// Add your commands in the super() call, e.g.
		// super(new FooCommand(), new BarCommand());
		addCommands(
			new ParallelRaceGroup(new BackShoot3(), new AutoDelay(5)), //Start with drive back and shoot 3
			new ToggleTurretAim(), //Disable aim
			new ParallelRaceGroup(new ParallelDeadlineGroup(driveBackIntake, new IntakeIn()), new AutoDelay(2)),
			new ParallelRaceGroup(new ParallelDeadlineGroup(driveUp, new ToggleTurretAim()), new AutoDelay(1.5)),
			new ParallelRaceGroup(new AutoShootAll(), new AutoDelay(3)) //Shoot All Balls
		);
	}

	@Override
	public void initialize(){
		driveBackIntake.SetParam(
			-36, 
			-4, 
			0.5, 
			false
		);

		driveUp.SetParam(
			36, 
			4, 
			0.7, 
			false
		);

		super.initialize();		
	}
}
