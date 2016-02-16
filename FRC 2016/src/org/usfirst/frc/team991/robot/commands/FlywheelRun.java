package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlywheelRun extends Command {

    public FlywheelRun() {
		requires(Robot.flywheels);
    }

    // Called just before this Command runs the first time
    Preferences prefs;
    Double frontWheel, backWheel;
    
    protected void initialize() {
    	prefs = Preferences.getInstance();
		frontWheel = prefs.getDouble("Front Wheel", 0.0);
		backWheel = prefs.getDouble("Back Wheel", 0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.flywheels.setSpeed(frontWheel, backWheel);
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
    	end();
    }
}
