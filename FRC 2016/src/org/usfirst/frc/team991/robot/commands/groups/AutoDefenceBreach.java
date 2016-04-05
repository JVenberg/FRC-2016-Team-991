package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.VisionProcessingMode;
import org.usfirst.frc.team991.robot.commands.SwapCamera.Cam;
import org.usfirst.frc.team991.robot.commands.DriveStraight;
import org.usfirst.frc.team991.robot.commands.SwapCamera;
import org.usfirst.frc.team991.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoDefenceBreach extends CommandGroup {
	
	
	public enum AutoDefenceLocation {
		LOCATION_1, LOCATION_2, LOCATION_3, LOCATION_4, NULL;
	}
	
	public enum AutoDefenceType {
		ROUGH_TERRAIN, MOAT, RAMPARTS;
	}
	
	double breachSpeed, breachTime;
	double turnAngle, turnTime;
	double alignSpeed, alignTimeout, alignDistance;
	
	public  AutoDefenceBreach() {
		AutoDefenceLocation defenceLocation = (AutoDefenceLocation) Robot.locationChooser.getSelected();
		AutoDefenceType defenceType = (AutoDefenceType) Robot.typeChooser.getSelected();
		if (defenceLocation != AutoDefenceLocation.NULL){
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
			alignTimeout = 5;
			alignDistance = 8.5;
			
			if (defenceType == AutoDefenceType.MOAT) {
				breachSpeed = -0.95;
				turnAngle += 195;
				breachTime = 3.8;
			} else if (defenceType == AutoDefenceType.ROUGH_TERRAIN) {
				breachSpeed = 0.6;
				breachTime = 3.4;
			} else if (defenceType == AutoDefenceType.RAMPARTS) {
				breachSpeed = -0.75;
				turnAngle += 195;
				breachTime = 4.1;
			} else {
				breachSpeed = 0;
				breachTime = 0;
				turnAngle = 0;
			}
			
//			addSequential(new SwapCamera(Cam.SHOOTER));
			addSequential(new DriveStraight(breachSpeed, breachTime));
			addSequential(new Turn(turnAngle, turnTime));
			addParallel(new CameraVisionProcessing(VisionProcessingMode.ACTIVE));
			addSequential(new DriveStraight(alignSpeed, alignTimeout, alignDistance));
			addSequential(new WaitCommand(2));
			addSequential(new ShootGroup());
		}
	}
	

}
