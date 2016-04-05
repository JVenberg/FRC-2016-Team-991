package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionMoveToDistance extends Command {

	double distance;
	double Kp = .03;
	double max_difference = 0.5;
	double MAX_SPEED = 0.5;

	public VisionMoveToDistance(double distance, double timeout) {
		setTimeout(timeout);
		requires(Robot.drivetrain);
		this.distance = distance;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.resetGryo();

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double angle = Robot.drivetrain.getGyroAngle();
		double calculated_speed = (Robot.drivetrain.getDistanceFromTarget() - distance)/distance * 2;
		Robot.drivetrain.arcadeDrive(-Math.max(-MAX_SPEED, Math.min(calculated_speed, MAX_SPEED)), -angle*Kp);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return ((Robot.drivetrain.getDistanceFromTarget() - distance) <= max_difference) || isTimedOut();
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
