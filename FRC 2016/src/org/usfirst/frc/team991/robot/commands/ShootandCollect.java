package org.usfirst.frc.team991.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ShootandCollect extends CommandGroup {
    
    public  ShootandCollect() {
        addSequential(new FlywheelRun(), 2);
        addSequential(new StartCollector(), 4);
        addSequential(new FlywheelStop(), 2);
    }
}
