package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LedControl extends Command {

	int color;
	
    public LedControl(int color) {
		this.color = color;
       requires(Robot.leds);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    			
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(color){
			case(0):
		    	Robot.leds.setRed();
				break;
			case(1):
				Robot.leds.setGreen();
				break;
			case(2):
				Robot.leds.setBlue();
				break;
			default:
				Robot.leds.turnOff();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
