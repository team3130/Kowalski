/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3130.robot.autoCommands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team3130.robot.commands.Intake.IntakeIn;

public class Shoot6 extends SequentialCommandGroup {
	AutoDriveStraightToPoint driveBack20;
	AutoDelay shoot1Delay;
	AutoTurn intakeTurn;
	IntakeIn intake;
	AutoDriveStraightToPoint driveBackIntake;
	AutoDriveStraightToPoint driveUp;
	AutoDelay shoot2Delay;

	/**
	 * Creates a new Shoot6.
	 */
	public Shoot6() {
		driveBack20 = new AutoDriveStraightToPoint();
		shoot1Delay = new AutoDelay(3);
		intakeTurn = new AutoTurn();
		intake = new IntakeIn();
		driveBackIntake = new AutoDriveStraightToPoint();
		driveUp = new AutoDriveStraightToPoint();
		shoot2Delay = new AutoDelay(3);

		// Add your commands in the super() call, e.g.
		// super(new FooCommand(), new BarCommand());
		addCommands(
			new ParallelRaceGroup(driveBack20,new AutoDelay(2)),
			shoot1Delay,
			new ParallelRaceGroup(intakeTurn, new AutoDelay(2)),
			new ParallelDeadlineGroup(
				new ParallelRaceGroup(driveBackIntake, new AutoDelay(4)),
				intake
			),
			new ParallelRaceGroup(driveUp, new AutoDelay(4)),
			shoot2Delay
		);
	}

	@Override
	public void initialize(){
		driveBack20.SetParam(
			20, 
			2, 
			0.5, 
			true
		);

		intakeTurn.setParam(
			-10, 
			2
		);

		driveBackIntake.SetParam(
			12*12,
			2,
			0.8,
			true
		);

		driveUp.SetParam(
			-12*12,
			2,
			0.8,
			true
		);

		super.initialize();		
	}
}
