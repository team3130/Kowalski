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


public class AutoShootAll extends ParallelCommandGroup {
	/**
	 * Creates a new AutoShootAll.
	 */
	public AutoShootAll() {
		// Add your commands in the super() call, e.g.
		// super(new FooCommand(), new BarCommand());super();
		super(
			new ParallelRaceGroup(new SpinFlywheel(), new AutoDelay(3)), new SequentialCommandGroup(//Spin flywheel through next part
				new ParallelRaceGroup(new HopperIn(true), new AutoDelay(3)))
		);
	}
}
