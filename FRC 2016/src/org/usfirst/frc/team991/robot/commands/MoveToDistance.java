package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveToDistance extends Command {

	double distance;
	double Kp = .03;
	double max_difference = 0.5;
	
    public MoveToDistance(double distance, double timeout) {
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
    	Robot.drivetrain.arcadeDrive(-(Robot.camera.distance - distance)/distance * 2, -angle*Kp);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.camera.distance - distance) <= max_difference;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
