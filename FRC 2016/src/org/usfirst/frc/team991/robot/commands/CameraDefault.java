package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraDefault extends Command {

	public CameraDefault() {
		requires(Robot.camera);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.camera.isCameraPluggedIn()){
			Robot.camera.camera.getImage(Robot.camera.frame); //Gets frame from camera
			Robot.camera.server.setImage(Robot.camera.frame); //Pushes frame to driver station
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.camera.isCameraPluggedIn();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
