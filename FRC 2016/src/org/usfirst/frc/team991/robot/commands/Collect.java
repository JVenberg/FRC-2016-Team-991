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
		requires(Robot.collector);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (mode != MotorMode.SHOOT) {
			Robot.collector.armDown();
			if (mode == MotorMode.FORWARD) {
				Robot.collector.setSucker(1.0);
			} else {
				Robot.collector.setSucker(-1.0);
			}
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (mode == MotorMode.BACKWARD) {
			Robot.collector.setSucker(-1.0);
		} else {
			Robot.collector.setSucker(1.0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (mode == MotorMode.SHOOT) {
			return false;
		} else {
			return Robot.collector.isPressed();
		}
		
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.collector.armUp();
		Robot.collector.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
