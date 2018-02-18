package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	Victor leftIntake30A;
	Victor leftIntake20A;
	Victor rightIntake30A;
	Victor rightIntake20A;
	
	DigitalInput leftLimit;
	DigitalInput rightLimit;
	
	RobotMap hardware;
	
	public static Intake instance;

    public static Intake getInstance() {
    	
	   if (instance == null) {
		   instance = new Intake();
	   }
	
	   return instance;
    }
	
	private Intake() {
		
		hardware = new RobotMap(); 
		
		try {
			
			leftIntake30A = new Victor(hardware.LEFT_INTAKE_30);
			leftIntake20A = new Victor(hardware.LEFT_INTAKE_20);
			rightIntake30A = new Victor(hardware.RIGHT_INTAKE_30);
			rightIntake20A = new Victor(hardware.RIGHT_INTAKE_20);
		
			leftLimit = new DigitalInput(hardware.LEFT_INTAKE_LIMIT);
			rightLimit = new DigitalInput(hardware.RIGHT_INTAKE_LIMIT);
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
	
	public void leftArm (double value) {
		leftIntake20A.set(value);
	}
	
	public void rightArm (double value) {
		rightIntake20A.set(value);
	}
	
	public boolean getLeftLimit() {
		return leftLimit.get();
	}
	
	public boolean getRightLimit() {
		return rightLimit.get();
	}
		
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
