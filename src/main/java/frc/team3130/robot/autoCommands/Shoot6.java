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
		shoot1Delay = new AutoDelay(2);
		intakeTurn = new AutoTurn();
		intake = new IntakeIn();
		driveBackIntake = new AutoDriveStraightToPoint();
		driveUp = new AutoDriveStraightToPoint();
		shoot2Delay = new AutoDelay(3);

		// Add your commands in the super() call, e.g.
		// super(new FooCommand(), new BarCommand());
		addCommands(
			new ParallelRaceGroup(driveBack20,new AutoDelay(2)),
			shoot1Delay,//Replace with Shoot command
			new ParallelRaceGroup(intakeTurn, new AutoDelay(2)),
			new ParallelDeadlineGroup(
				new ParallelRaceGroup(driveBackIntake, new AutoDelay(4)),
				intake
			),
			new AutoDelay(0.75),
			new ParallelRaceGroup(driveUp, new AutoDelay(8)),
			shoot2Delay//Replace with shoot command
		);
	}

	@Override
	public void initialize(){
		driveBack20.SetParam(
			20, //Drive Distance (inches)
			2,  //Tolerance
			0.5,//PVbus speed
			true//Nothing
		);

		intakeTurn.setParam(
			-3, //turn angle (degrees)
			0.5,//tolerance (degrees)
			true//small angle
		);

		driveBackIntake.SetParam(
			12*13,
			6,
			0.5,
			true
		);

		driveUp.SetParam(
			-12*13,
			6,
			0.8,
			true
		);

		super.initialize();		
	}
}
