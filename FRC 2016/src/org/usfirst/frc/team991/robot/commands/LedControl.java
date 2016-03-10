package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.subsystems.LEDs.LedColor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LedControl extends Command {

	LedColor color;

	public LedControl() {
		requires(Robot.leds);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		setRunWhenDisabled(true);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		color = (LedColor) Robot.leds.getLedChooser().getSelected();
		switch(color){
		case RED:
			Robot.leds.setRed();
		break;
		case GREEN:
			Robot.leds.setGreen();
		break;
		case BLUE:
			Robot.leds.setBlue();
		break;
		default:
			Robot.leds.setRed();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.leds.turnOff();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
