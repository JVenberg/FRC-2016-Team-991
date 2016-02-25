package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpinUpShooter extends Command {

	public SpinUpShooter() {
		requires(Robot.flywheels);
	}

	// Called just before this Command runs the first time
	Preferences prefs;
	double frontWheel, backWheel, speed;

	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		speed = .55;
		frontWheel = speed;
		backWheel = -speed;
		Robot.flywheels.setSpeed(frontWheel, backWheel);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
