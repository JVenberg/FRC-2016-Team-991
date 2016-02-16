package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.Rotate;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Rotator extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	SpeedController spinner;
	public Rotator() {
		spinner = new CANTalon(RobotMap.spinner);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new Rotate());
    }

    public void setSpin(double spin) {
    	spinner.set(spin);
    }
    
    public void stop() {
    	spinner.set(0);
    }
}

