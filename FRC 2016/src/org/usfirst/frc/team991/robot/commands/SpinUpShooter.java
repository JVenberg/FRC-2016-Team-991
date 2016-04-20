package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpinUpShooter extends Command {

	double speed;
	
	
	public SpinUpShooter() {
		this.speed = .55;
		requires(Robot.flywheels);
	}

	double frontWheel, backWheel;

	protected void initialize() {


	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		Robot.flywheels.setSpeed(speed, -speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
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
