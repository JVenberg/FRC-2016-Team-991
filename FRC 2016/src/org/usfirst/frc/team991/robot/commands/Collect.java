package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Collect extends Command {

	
    public Collect() {
        requires(Robot.sucker);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.sucker.setSucker(1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putBoolean("SWITCH!", Robot.sucker.getSwitch());
    	return (Robot.sucker.getSwitch());
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.sucker.setSucker(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
