package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDriveJoystick extends Command {

	public ArcadeDriveJoystick() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {}

	protected void execute() {
		Robot.drivetrain.arcadeDrive(Robot.oi.getPrimaryJoystick().getY(), Robot.oi.getPrimaryJoystick().getTwist());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drivetrain.stop();
	}

	protected void interrupted() {
		end();
	}
}
