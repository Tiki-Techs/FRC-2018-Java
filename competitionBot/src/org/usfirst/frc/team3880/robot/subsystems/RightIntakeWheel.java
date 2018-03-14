package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.commands.RightWheelJoystickControl;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RightIntakeWheel extends Subsystem {

	Victor rightIntake30A;
	
	RobotMap hardware;
	
	public static RightIntakeWheel instance;

    public static RightIntakeWheel getInstance() {
    	
	   if (instance == null) {
		   instance = new RightIntakeWheel();
	   }
	
	   return instance;
    }
	
	private RightIntakeWheel() {
		
		hardware = new RobotMap(); 
		
		try {
			
			
			rightIntake30A = new Victor(hardware.RIGHT_INTAKE_30);
	
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void spin (double value) {
		rightIntake30A.set(value);
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new RightWheelJoystickControl());
	}

}
