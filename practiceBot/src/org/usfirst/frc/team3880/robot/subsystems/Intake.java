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

public class Intake extends Subsystem {

	
	VictorSP intakeRightWheel;
	VictorSP intakeLeftWheel;
	
	
//	DoubleSolenoid rightPiston;
//	DoubleSolenoid leftPiston;
	
	
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
		
//		rightPiston = new DoubleSolenoid(hardware.RIGHTARM_PISTON_MODULE_NUMBER,
//										hardware.RIGHTARM_PISTON_FORWARD,
//										hardware.RIGHTARM_PISTON_BACKWARD);
//		
//		leftPiston = new DoubleSolenoid(hardware.LEFTARM_PISTON_MODULE_NUMBER,
//										hardware.LEFTARM_PISTON_FORWARD,
//										hardware.LEFTARM_PISTON_BACKWARD);
		
		try {
			intakeRightWheel = new VictorSP(hardware.INTAKE_RIGHT_TALON);
			intakeLeftWheel = new VictorSP(hardware.INTAKE_LEFT_TALON);
		}
		
		catch (Exception e) {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void setWheelRight(double speed) {
		intakeRightWheel.set(speed);
	}
	public void setWheelLeft(double speed) {
		intakeLeftWheel.set(speed);
	}
	
//
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
