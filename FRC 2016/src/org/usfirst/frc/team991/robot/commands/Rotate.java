package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Rotate extends Command {

	boolean active;

	public Rotate() {
		requires(Robot.turret);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.turret.getVisionRotatePower() != 0){
			Robot.turret.setSpin(Robot.turret.getVisionRotatePower());
		} else {
			if (Math.abs(Robot.oi.getSecondaryJoystick().getTwist()) > 0.1) {
				Robot.turret.setSpin(Robot.oi.getSecondaryJoystick().getTwist());
			} else {
				Robot.turret.setSpin(0);
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.turret.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
