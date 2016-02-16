package org.usfirst.frc.team991.robot.commands.groups;

import org.usfirst.frc.team991.robot.commands.DriveStraight;
import org.usfirst.frc.team991.robot.commands.Turn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveAndTurn extends CommandGroup {
    
    public  AutoDriveAndTurn() {
        addSequential(new DriveStraight(.4, 2));
        addSequential(new Turn(90, 2));
    }
}
