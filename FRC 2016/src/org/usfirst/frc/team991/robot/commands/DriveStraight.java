package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {

	double Kp = .09;
	double speed, distance = -1;

	public DriveStraight(double speed, double timeout) {
		requires(Robot.drivetrain);
		setTimeout(timeout);
		this.speed = speed;
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.resetGryo();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double angle = Robot.drivetrain.getGyroAngle();
		Robot.drivetrain.arcadeDrive(-speed, -angle*Kp);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
