package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.commands.LeftWheelJoystickControl;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LeftIntakeWheel extends Subsystem {

	Victor leftIntake30A;
	
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
			
			
			leftIntake30A = new Victor(hardware.LEFT_INTAKE_30);
	
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void spin (double value) {
		leftIntake30A.set(value);
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new LeftWheelJoystickControl());
	}

}
