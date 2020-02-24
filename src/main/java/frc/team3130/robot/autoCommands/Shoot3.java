/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3130.robot.autoCommands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class Shoot3 extends SequentialCommandGroup {
	private AutoDriveStraightToPoint driveBack;
	private AutoDelay wait;
	private AutoDelay shoot1Delay;

	/**
	 * Creates a new Shoot3.
	 */
	public Shoot3() {
		driveBack = new AutoDriveStraightToPoint();
		wait = new AutoDelay(4);

		// Add your commands in the super() call, e.g.
		// super(new FooCommand(), new BarCommand());
		addCommands(
			new ParallelRaceGroup(driveBack,new AutoDelay(2)),
			new ParallelRaceGroup(new AutoTurnTurret(), new AutoDelay(2)),
			wait,
			new ParallelRaceGroup(new AutoShootAll(), new AutoDelay(10))
		);
	}

	@Override
	public void initialize(){

		driveBack.SetParam(
			24,
			2,
			0.5,
			false
		);

		super.initialize();		
	}
}
