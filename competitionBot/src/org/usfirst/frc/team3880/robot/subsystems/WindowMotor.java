package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WindowMotor extends Subsystem {
//window motor refers to tilt control on intake
	Victor windowMotor;
	DigitalInput windowLimit;
	
	RobotMap hardware;
	
	public static WindowMotor instance;

    public static WindowMotor getInstance() {
    	
	   if (instance == null) {
		   instance = new WindowMotor();
	   }
	
	   return instance;
    }
	
	private WindowMotor() {
		
		hardware = new RobotMap(); 
		
		try {
			windowLimit = new DigitalInput(hardware.WINDOW_UPPER_LIMIT);
			windowMotor = new Victor(hardware.WINDOW_MOTOR_VICTOR);
			
		}
		
		catch (Exception e) 
        {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void set(double value) {
		windowMotor.set(value);
	}
	public boolean getLimit() {
		return windowLimit.get();
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
