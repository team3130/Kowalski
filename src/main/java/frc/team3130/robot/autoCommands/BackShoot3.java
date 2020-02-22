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
import frc.team3130.robot.commands.Turret.ToggleTurretAim;


public class BackShoot3 extends SequentialCommandGroup {
	private AutoDriveStraightToPoint driveBack;
	private AutoTurretAngle adjustAim;

	/**
	 * Creates a new BackShoot3.
	 */
	public BackShoot3() {
		driveBack = new AutoDriveStraightToPoint();
		adjustAim = new AutoTurretAngle();

		// Add your commands in the super() call, e.g.
		// super(new FooCommand(), new BarCommand());
		addCommands(
			new ParallelRaceGroup(new ParallelCommandGroup(driveBack, adjustAim), new AutoDelay(1.5)),//need both driveBack and adjustAim done, or AutoDelay times out
			new ToggleTurretAim(), //Assume aim is off prior to
			new ParallelRaceGroup(new AutoShootAll(), new AutoDelay(3)) //Shoot All Balls
		);
	}

	@Override
	public void initialize(){
		driveBack.SetParam(
			-24,
			4,
			0.7,
			false
		);

		adjustAim.setParam(
			15
		);

		super.initialize();		
	}
}
