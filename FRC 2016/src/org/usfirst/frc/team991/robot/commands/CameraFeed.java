package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraFeed extends Command {

    public CameraFeed() {
        requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.camera.isCameraPluggedIn()){
	    	NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
			Robot.camera.camera.getImage(Robot.camera.frame);
			NIVision.imaqDrawShapeOnImage(Robot.camera.frame, Robot.camera.frame, rect,
	                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
			Robot.camera.server.setImage(Robot.camera.frame);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
