package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.CamMode;
import org.usfirst.frc.team991.robot.commands.SwapCamera.Cam;
import org.usfirst.frc.team991.robot.commands.DriveStraight;
import org.usfirst.frc.team991.robot.commands.SwapCamera;
import org.usfirst.frc.team991.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDefenceBreach extends CommandGroup {
	
	
	public enum AutoDefenceLocation {
		LOCATION_1, LOCATION_2, LOCATION_3, LOCATION_4, NULL;
	}
	
	double breachSpeed, breachTime;
	double turnAngle, turnTime;
	double alignSpeed, alignTimeout, alignDistance;
	
	public  AutoDefenceBreach() {
		AutoDefenceLocation defenceLocation = (AutoDefenceLocation) Robot.locationChooser.getSelected();

		if (defenceLocation == AutoDefenceLocation.LOCATION_1) {
			turnAngle = 35;
		} else if (defenceLocation == AutoDefenceLocation.LOCATION_2) {
			turnAngle = 25;
		} else if (defenceLocation == AutoDefenceLocation.LOCATION_3) {
			turnAngle = 0;
		} else {
			turnAngle = -35;
		}
		
		turnTime = 3;
		alignSpeed = 0.35;
		alignTimeout = 3;
		alignDistance = 9;
		breachSpeed = 0.4;
		breachTime = 1;
		
		addSequential(new SwapCamera(Cam.SHOOTER));
		addSequential(new DriveStraight(breachSpeed, breachTime));
		addSequential(new Turn(turnAngle, turnTime));
		addParallel(new CameraVisionProcessing(CamMode.ACTIVE));
		addSequential(new DriveStraight(alignSpeed, alignTimeout, alignDistance));
		addSequential(new ShootGroup());
	}
	

}
