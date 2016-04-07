package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionMoveToDistance extends Command {

	double distance;
	double Kp = .03;
	double maxDistanceError = 0.5;
	double maxVelocityError = .25;
	double MAX_SPEED = 0.5;
	
	long endTime;
	long startTime;
	double startDistance;
	double endDistance;
	double velocity;

	public VisionMoveToDistance(double distance, double timeout) {
		setTimeout(timeout);
		requires(Robot.drivetrain);
		this.distance = distance;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.resetGryo();
		startTime = System.nanoTime();
		startDistance = Robot.drivetrain.getDistanceFromTarget();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double angle = Robot.drivetrain.getGyroAngle();
		
		endTime = System.nanoTime();
		endDistance = Robot.drivetrain.getDistanceFromTarget();
		
		double calculated_speed = (endDistance - distance)/distance * 2;

		long deltaTime = (endTime - startTime);
		double deltaDistance = (endDistance - startDistance);
		
		startDistance = Robot.drivetrain.getDistanceFromTarget();
		startTime = System.nanoTime();
		
		velocity = deltaDistance/((double)deltaTime/1000000000.0);
		SmartDashboard.putNumber("Vision Velocity", velocity);
		
		
		Robot.drivetrain.arcadeDrive(-Math.max(-MAX_SPEED, Math.min(calculated_speed, MAX_SPEED)), -angle*Kp);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return ((endDistance - distance <= maxDistanceError) && (Math.abs(velocity) <= maxVelocityError)) || (isTimedOut());
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
