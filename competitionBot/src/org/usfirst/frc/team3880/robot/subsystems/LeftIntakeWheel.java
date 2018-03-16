package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LeftIntakeWheel extends Subsystem {

	Victor leftIntakeWheel;
	
	RobotMap hardware;
	
	public static LeftIntakeWheel instance;

    public static LeftIntakeWheel getInstance() {
    	
	   if (instance == null) {
		   instance = new LeftIntakeWheel();
	   }
	
	   return instance;
    }
	
	private LeftIntakeWheel() {
		
		hardware = new RobotMap(); 
		
		try {
			
			
			leftIntakeWheel = new Victor(hardware.LEFT_INTAKE_VICTOR);
	
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void spin (double value) {
		leftIntakeWheel.set(value);
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
