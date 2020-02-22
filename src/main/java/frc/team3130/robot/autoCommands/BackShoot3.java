/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3130.robot.autoCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team3130.robot.commands.Flywheel.SpinFlywheel;
import frc.team3130.robot.commands.Hopper.HopperIn;
import frc.team3130.robot.commands.Turret.ToggleTurretAim;


public class BackShoot3 extends SequentialCommandGroup {
	private AutoDriveStraightToPoint driveBack;

	/**
	 * Creates a new BackShoot3.
	 */
	public BackShoot3() {
		driveBack = new AutoDriveStraightToPoint();

		// Add your commands in the super() call, e.g.
		// super(new FooCommand(), new BarCommand());
		addCommands(
			new ParallelRaceGroup(driveBack = new AutoDriveStraightToPoint(), new AutoDelay(1.5)),
			new ToggleTurretAim(), //Assume aim is off prior to
			new ParallelCommandGroup(new SpinFlywheel(), new SequentialCommandGroup(//Spin flywheel through next part
				new HopperIn(true)
			))
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

		super.initialize();		
	}
}
