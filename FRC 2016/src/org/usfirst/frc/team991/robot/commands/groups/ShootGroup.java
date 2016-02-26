package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.Shoot;
import org.usfirst.frc.team991.robot.commands.SpinUpShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootGroup extends CommandGroup {

	public  ShootGroup() {
		// addSequential(new CameraVisionProcessing(), 1);
		addSequential(new SpinUpShooter(), 2);
		addSequential(new Shoot(), 5);
	}

	@Override
	public void end() {
		Robot.sucker.stop();
		Robot.flywheels.stop();
	}

	@Override
	public void interrupted() {
		end();
	}
}
