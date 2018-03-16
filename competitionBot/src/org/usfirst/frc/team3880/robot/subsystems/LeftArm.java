package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.commands.LeftArmJoystickControl;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LeftArm extends Subsystem {

	DoubleSolenoid leftArm;
	
	RobotMap hardware;
	
	public static LeftArm instance;

    public static LeftArm getInstance() {
    	
	   if (instance == null) {
		   instance = new LeftArm();
	   }
	
	   return instance;
    }
	
	private LeftArm() {
		
		hardware = new RobotMap(); 
		
		try {
			
			leftArm = new DoubleSolenoid(hardware.LEFT_ARM_MODULE_NUMBER, 
										 hardware.LEFT_ARM_FORWARD, 
										 hardware.LEFT_ARM_BACKWARD);
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void set (DoubleSolenoid.Value value) {
		leftArm.set(value);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
