package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootandCollect extends CommandGroup {

	public  ShootandCollect() {
		addSequential(new FlywheelRun(), 2);
		addSequential(new StartCollector(), 6);
	}

	protected void end() {
		Robot.flywheels.stop();
	}
}
