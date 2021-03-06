package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.Collect;
import org.usfirst.frc.team991.robot.commands.Collect.MotorMode;
import org.usfirst.frc.team991.robot.commands.LedControl;
import org.usfirst.frc.team991.robot.commands.LedShoot;
import org.usfirst.frc.team991.robot.commands.SpinUpShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShootGroup extends CommandGroup {

	public  ShootGroup() {
		addSequential(new SpinUpShooter()); //Spins up shooter while shooter is rotating
		addSequential(new WaitCommand(1));
		addSequential(new Collect(MotorMode.SHOOT), 4); //Starts collector to feed into shooter
	}

	//Stops all subsystems
	@Override
	public void end() {
		Robot.collector.stop();
		Robot.flywheels.stop();
		Robot.turret.stop();
		new LedControl().start();
	}

	//Ends when interrupted
	@Override
	public void interrupted() {
		end();
	}
}
