package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.CamMode;
import org.usfirst.frc.team991.robot.commands.DriveStraight;
import org.usfirst.frc.team991.robot.commands.Rotate;
import org.usfirst.frc.team991.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveAndTurn extends CommandGroup {

	public  AutoDriveAndTurn() {
		addSequential(new DriveStraight(0.75, 2.5));
		addSequential(new Turn(-25, 3));
		addParallel(new CameraVisionProcessing(CamMode.ACTIVE));
		addSequential(new DriveStraight(0.35, 3, 9));
		addParallel(new Rotate(), 3);
		addSequential(new ShootGroup());
	}
}
