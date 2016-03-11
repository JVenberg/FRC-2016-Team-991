package org.usfirst.frc.team991.robot.commands.groups;

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
	
	public enum AutoDefenceType {
		MOAT, RAMPARTS, ROCK_WALL, ROUGH_TERRAIN;
	}
	
	public enum AutoDefenceLocation {
		LOCATION_1, LOCATION_2, LOCATION_3, LOCATION_4;
	}
	
	double breachSpeed, breachTime;
	double turnAngle, turnTime;
	double alignSpeed, alignTimeout, alignDistance;
	
	public  AutoDefenceBreach(AutoDefenceType defenceType, AutoDefenceLocation defenceLocation) {
//		switch(defenceLocation) {
//		case LOCATION_1:
//			turnAngle = 35;
//			turnTime = 3;
//			alignSpeed = 0.35;
//			alignTimeout = 3;
//			alignDistance = 9;
//			break;
//		case LOCATION_2:
			turnAngle = 25;
			turnTime = 3;
			alignSpeed = 0.35;
			alignTimeout = 3;
			alignDistance = 9;
//			break;
//		case LOCATION_3:
//			turnAngle = 0;
//			turnTime = 0;
//			alignSpeed = 0.35;
//			alignTimeout = 3;
//			alignDistance = 9;
//			break;
//		case LOCATION_4:
//			turnAngle = 35;
//			turnTime = 3;
//			alignSpeed = 0.35;
//			alignTimeout = 3;
//			alignDistance = 9;
//			break;
//		}
//		
//		switch(defenceType) {
//		case MOAT:
//			breachSpeed = 0.75;
//			breachTime = 2.5;
//			break;
//		case RAMPARTS:
//			breachSpeed = 0.75;
//			breachTime = 2.5;
//			break;
//		case ROCK_WALL:
//			breachSpeed = 0.75;
//			breachTime = 2.5;
//			break;
//		case ROUGH_TERRAIN:
			breachSpeed = 0.4;
			breachTime = 1;
//			break;
//		}
		
//		addSequential(new SwapCamera(Cam.SHOOTER));
		addSequential(new DriveStraight(breachSpeed, breachTime));
		addSequential(new Turn(turnAngle, turnTime));
		addParallel(new CameraVisionProcessing(CamMode.ACTIVE));
		addSequential(new DriveStraight(alignSpeed, alignTimeout, alignDistance));
		addSequential(new ShootGroup());
	}
}
