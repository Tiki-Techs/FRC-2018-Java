package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeWheels extends Subsystem {

	Victor leftIntake30A;
	Victor rightIntake30A;
	
	RobotMap hardware;
	
	public static IntakeWheels instance;

    public static IntakeWheels getInstance() {
    	
	   if (instance == null) {
		   instance = new IntakeWheels();
	   }
	
	   return instance;
    }
	
	private IntakeWheels() {
		
		hardware = new RobotMap(); 
		
		try {
			
			leftIntake30A = new Victor(hardware.LEFT_INTAKE_30);
			rightIntake30A = new Victor(hardware.RIGHT_INTAKE_30);
	
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void spin (double value) {
		leftIntake30A.set(value);
		rightIntake30A.set(value);
	}
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
