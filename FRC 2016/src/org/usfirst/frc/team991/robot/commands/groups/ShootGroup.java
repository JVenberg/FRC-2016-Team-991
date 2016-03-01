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
		addSequential(new SwapCamera(Cam.SHOOTER));
		addParallel(new CameraVisionProcessing(CamMode.ACTIVE), 3);
		addParallel(new Rotate(), 3);
		addSequential(new SpinUpShooter(), 2);
		addSequential(new Collect(MotorMode.SHOOT), 5);
	}

	@Override
	public void end() {
		Robot.sucker.stop();
		Robot.flywheels.stop();
		Robot.rotator.stop();
	}

	@Override
	public void interrupted() {
		end();
	}
}
