package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.commands.WindowMotorUp;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class WindowMotor extends Subsystem {
//window motor refers to tilt control on intake
	Victor windowMotor;
	DigitalInput windowLimit;
	
	Potentiometer pot;
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
			pot = new AnalogPotentiometer(hardware.WINDOW_POT_ANALOG, 72, 0);
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
		return !windowLimit.get();
	}
	
	public void setDegree(int degree) {
		if(!windowLimit.get()) {
			if(pot.get() < (degree - 5)) {
				windowMotor.set(1);
			}
			else if(pot.get() > (degree + 5)) {
				windowMotor.set(-1);
			}
			else {
				windowMotor.set(0);
			}
		}
		else {
			windowMotor.set(0);
		}
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new WindowMotorUp());
	}

}
