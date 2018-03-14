package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeTilt extends Subsystem {

	

	
	TalonSRX tilt;

	
	DigitalInput tiltLimit;
	
	RobotMap hardware;
	
	public static IntakeTilt instance;

    public static IntakeTilt getInstance() {
    	
	   if (instance == null) {
		   instance = new IntakeTilt();
	   }
	
	   return instance;
    }
	
	private IntakeTilt() {
		hardware = new RobotMap();
		
		tiltLimit = new DigitalInput(hardware.INTAKE_TILT_LIMIT);
		
		try {
			tilt = new TalonSRX(hardware.TEST_TALON);
		}
		
		catch (Exception e) {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
//	public void leftArmIn() {
//		leftPiston.set(DoubleSolenoid.Value.kForward);
//		}
//	public void leftArmOut() {
//		leftPiston.set(DoubleSolenoid.Value.kReverse);
//	}
	
	
	public void setTilt(double speed) {
		tilt.set(ControlMode.PercentOutput, speed);
	}

	
//	public void rightArmIn() {
//		rightPiston.set(DoubleSolenoid.Value.kForward);
//	}
//	public void rightArmOut() {
//		rightPiston.set(DoubleSolenoid.Value.kReverse);
//	}
	
	public boolean getTiltLimit() {
		return !tiltLimit.get();
	}
//
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
