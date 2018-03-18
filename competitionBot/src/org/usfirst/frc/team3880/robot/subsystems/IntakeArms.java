package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeArms extends Subsystem {

	DoubleSolenoid armSolenoid;
	
	RobotMap hardware;
	
	public static IntakeArms instance;

    public static IntakeArms getInstance() {
    	
	   if (instance == null) {
		   instance = new IntakeArms();
	   }
	
	   return instance;
    }
	
	private IntakeArms() {
		
		hardware = new RobotMap(); 
		
		try {
			
			armSolenoid = new DoubleSolenoid(hardware.ARM_MODULE_NUMBER, 
										 hardware.ARM_FORWARD, 
										 hardware.ARM_BACKWARD);
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void set (DoubleSolenoid.Value value) {
		armSolenoid.set(value);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
