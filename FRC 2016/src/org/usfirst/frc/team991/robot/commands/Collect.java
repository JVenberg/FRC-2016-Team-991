package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Collect extends Command {

	MotorMode mode;
	
	public enum MotorMode {
		FORWARD, BACKWARD, SHOOT;
	}

	public Collect(MotorMode mode) {
		this.mode = mode;
		requires(Robot.sucker);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.sucker.setSucker(1.0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (mode == MotorMode.SHOOT) {
			return false;
		} else {
			return Robot.sucker.isPressed();
		}
		
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.sucker.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
