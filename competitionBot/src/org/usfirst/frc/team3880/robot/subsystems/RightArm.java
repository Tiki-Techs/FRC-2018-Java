package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.commands.LeftArmJoystickControl;
import org.usfirst.frc.team3880.robot.commands.RightArmJoystickControl;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RightArm extends Subsystem {

	DoubleSolenoid rightArm;
	
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
			
			rightArm = new DoubleSolenoid(hardware.RIGHT_ARM_MODULE_NUMBER, 
										  hardware.RIGHT_ARM_FORWARD, 
										  hardware.RIGHT_ARM_BACKWARD);
			
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void set(DoubleSolenoid.Value value) {
		rightArm.set(value);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
