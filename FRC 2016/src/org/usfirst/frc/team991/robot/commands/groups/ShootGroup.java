package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.Collect;
import org.usfirst.frc.team991.robot.commands.Collect.MotorMode;
import org.usfirst.frc.team991.robot.commands.SpinUpShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootGroup extends CommandGroup {

	public  ShootGroup() {
		addSequential(new SpinUpShooter()); //Spins up shooter while shooter is rotating
		addSequential(new Collect(MotorMode.SHOOT), 4); //Starts collector to feed into shooter
	}

	//Stops all subsystems
	@Override
	public void end() {
		Robot.sucker.stop();
		Robot.flywheels.stop();
		Robot.turret.stop();
	}

	//Ends when interrupted
	@Override
	public void interrupted() {
		end();
	}
}
