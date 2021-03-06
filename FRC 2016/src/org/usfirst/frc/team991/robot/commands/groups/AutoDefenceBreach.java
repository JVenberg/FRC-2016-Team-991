package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing;
import org.usfirst.frc.team991.robot.commands.CameraVisionProcessing.VisionProcessingMode;
import org.usfirst.frc.team991.robot.commands.DriveStraight;
import org.usfirst.frc.team991.robot.commands.Turn;
import org.usfirst.frc.team991.robot.commands.VisionMoveToDistance;

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
	double turnAngle, turnTime, turnForward;
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
			turnForward = 0;
			alignTimeout = 4;
			alignDistance = 8.5;
			
			if (defenceType == AutoDefenceType.MOAT) {
				breachSpeed = -0.95;
				turnAngle += 185;
				turnForward = .5;
				breachTime = 2.9;
			} else if (defenceType == AutoDefenceType.ROUGH_TERRAIN) {
				breachSpeed = 0.99;
				breachTime = 2.0;
			} else if (defenceType == AutoDefenceType.RAMPARTS) {
				breachSpeed = -0.99;
				turnAngle += 185;
				turnForward = .5;
				breachTime = 3.0;
			} else {
				breachSpeed = 0;
				breachTime = 0;
				turnAngle = 0;
			}
			
			addSequential(new DriveStraight(breachSpeed, breachTime));
			addSequential(new Turn(turnAngle, turnTime, turnForward));
			addParallel(new CameraVisionProcessing(VisionProcessingMode.ACTIVE));
			addSequential(new VisionMoveToDistance(alignDistance, alignTimeout));
			addSequential(new WaitCommand(1));
			addSequential(new ShootGroup());
		}
	}
	

}
