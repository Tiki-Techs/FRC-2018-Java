package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.commands.LeftArmJoystickControl;
import org.usfirst.frc.team3880.robot.commands.RightArmJoystickControl;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RightArm extends Subsystem {

	Victor intake20A;
	
	DigitalInput innerLimit;
	DigitalInput outerLimit;
	
	RobotMap hardware;
	
	public static RightArm instance;

    public static RightArm getInstance() {
    	
	   if (instance == null) {
		   instance = new RightArm();
	   }
	
	   return instance;
    }
	
	private RightArm() {
		
		hardware = new RobotMap(); 
		
		try {
			
			intake20A = new Victor(hardware.RIGHT_INTAKE_20);
		
			innerLimit = new DigitalInput(hardware.RIGHT_INTAKE_INNER_LIMIT);
			outerLimit = new DigitalInput(hardware.RIGHT_INTAKE_OUTER_LIMIT);
			
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void set (double value) {
		intake20A.set(value);
	}
	
	public boolean getInnerLimit() {
		return !innerLimit.get();
	}
	
	public boolean getOuterLimit() {
		return !outerLimit.get();
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new RightArmJoystickControl());

	}

}
