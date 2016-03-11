package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.Collect;
import org.usfirst.frc.team991.robot.commands.Rotate;
import org.usfirst.frc.team991.robot.commands.Collect.MotorMode;
import org.usfirst.frc.team991.robot.commands.SpinUpShooter;
import org.usfirst.frc.team991.robot.commands.SwapCamera;
import org.usfirst.frc.team991.robot.commands.SwapCamera.Cam;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.CamMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootGroup extends CommandGroup {

	public  ShootGroup() {
		addSequential(new SwapCamera(Cam.SHOOTER)); //Swaps camera to shooter vision processing
		addParallel(new CameraVisionProcessing(CamMode.ACTIVE), 1); //Starts active vision processing in parallel with next
		addParallel(new Rotate(), 1); //Starts rotation command in parallel with vision processing to allow it to rotate
		addSequential(new SpinUpShooter(), 1); //Spins up shooter while shooter is rotating
		addSequential(new Collect(MotorMode.SHOOT), 4); //Starts collector to feed into shooter
	}

	//Stops all subsystems
	@Override
	public void end() {
		Robot.sucker.stop();
		Robot.flywheels.stop();
		Robot.rotator.stop();
	}

	//Ends when interrupted
	@Override
	public void interrupted() {
		end();
	}
}
