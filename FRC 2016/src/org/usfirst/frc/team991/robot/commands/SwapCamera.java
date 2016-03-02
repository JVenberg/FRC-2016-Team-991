package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.CamMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwapCamera extends Command {

	public enum Cam {
		SUCKER, SHOOTER;
	}
	
	Cam cam;
	
	public SwapCamera() {
		requires(Robot.camera);
	}
	
	public SwapCamera(Cam cam) {
		this.cam = cam;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (cam == null) {
			Robot.camera.cycleCamera();
		} else if (cam == Cam.SHOOTER) {
			Robot.camera.changeCamera(0);
		} else {
			Robot.camera.changeCamera(1);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		if (Robot.camera.getCamIndex() == 0) {
			new CameraVisionProcessing(CamMode.PASSIVE).start();
		} else {
			new CameraDefault().start();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
