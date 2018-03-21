package org.usfirst.frc.team3880.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;

import org.usfirst.frc.team3880.robot.subsystems.Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Drive extends Subsystem {

	public TalonSRX frontRightDrive;
	public TalonSRX backRightDrive;
	public TalonSRX frontLeftDrive;
	public TalonSRX backLeftDrive;
	
	double joystickSensitivity = 0.85;
	
	Encoder driveEncoderLeft;
	Encoder driveEncoderRight;
	
	RobotMap hardware;
	
	public static Drive instance;

    public static Drive getInstance() {
    	
	   if (instance == null) {
		   instance = new Drive();
	   }
	
	   return instance;
    }

	
	private Drive() {
		super("DriveWheels");
		
        hardware = new RobotMap();
        
        try {
        	// TODO: add encoders to robotmap
        	driveEncoderLeft = new Encoder(hardware.DRIVE_ENCODER_LEFT_ONE, 
        								   hardware.DRIVE_ENCODER_LEFT_TWO, 
        								   false, Encoder.EncodingType.k4X);
        	driveEncoderRight = new Encoder(hardware.DRIVE_ENCODER_RIGHT_ONE, 
        									hardware.DRIVE_ENCODER_RIGHT_TWO, 
        									false, Encoder.EncodingType.k4X);

    		driveEncoderLeft.setDistancePerPulse(0.0092);
    		driveEncoderRight.setDistancePerPulse(0.0092);
    		
			frontRightDrive = new TalonSRX(hardware.FRONT_RIGHT_DRIVE_TALON);
			backRightDrive = new TalonSRX(hardware.BACK_RIGHT_DRIVE_TALON);
			frontLeftDrive = new TalonSRX(hardware.FRONT_LEFT_DRIVE_TALON);
			backLeftDrive = new TalonSRX(hardware.BACK_LEFT_DRIVE_TALON);
			
        }
        
        catch (Exception e) {
            System.out.println(e);
            System.out.println("Drive failed");
        }
		
	}

	public void drive(double left, double right) {
		
		left = (joystickSensitivity * Math.pow(left, 3)) + ((1 - joystickSensitivity) * left);
		right = (joystickSensitivity * Math.pow(right, 3)) + ((1 - joystickSensitivity) * right);

		if(CommandBase.OI_MODE == 2) {
			backLeftDrive.set(ControlMode.PercentOutput, -left);
			frontLeftDrive.set(ControlMode.PercentOutput, -left);
			backRightDrive.set(ControlMode.PercentOutput, right);
			frontRightDrive.set(ControlMode.PercentOutput, right);
		}
		else if(CommandBase.OI_MODE == 1 || CommandBase.OI_MODE == 3) {
			backLeftDrive.set(ControlMode.PercentOutput, -(left - right));
			frontLeftDrive.set(ControlMode.PercentOutput, -(left - right));
			backRightDrive.set(ControlMode.PercentOutput, left + right);
			frontRightDrive.set(ControlMode.PercentOutput, left + right);
		}
    }
	
	public void set(double left, double right) {
		
			backLeftDrive.set(ControlMode.PercentOutput, -left);
			frontLeftDrive.set(ControlMode.PercentOutput, -left);
			backRightDrive.set(ControlMode.PercentOutput, right);
			frontRightDrive.set(ControlMode.PercentOutput, right);

    }
	
	public double getEncoderLeftRate() {
		return driveEncoderLeft.getRate();
	}
	
	public double getEncoderLeftDist() {
		return driveEncoderLeft.getDistance();
	}
	
	public double getEncoderRightRate() {
		return driveEncoderRight.getRate();
	}
	
	public double getEncoderRightDist() {
		return driveEncoderRight.getDistance();
	}

	public void setHeading(double percent) {
		double angle = CommandBase.gyro.gyro.getAngleZ();
		double left;
		double right;
		System.out.println("angle " + angle);
		
		while(angle>= 360) {
			angle -= 360;
			//keeps angle from getting too big if it turns more than 360 degrees in one direction
		}
		
		if (angle < 10 || angle > 350) {
			left = 1;
			right = 1;
		}
		else if (angle >= 180) {
			left = 1;
			right = 1+((angle-360)/180);
		}
		else if (angle < 180) {
			right = 1;
			left = 1-(angle/180);
		}
		else {
			left = 0;
			right = 0;
		}
		
		if (percent == 0) {
			percent = 0.5;
		}
		
		double finalLeft = left*percent;
		double finalRight = right*percent;
		
		System.out.println(finalLeft);
		System.out.println(finalRight);
		
		set(finalLeft, finalRight);
		
	}
	
	public void resetEncoders() {
		driveEncoderLeft.reset();
		driveEncoderRight.reset();
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveStandard());
	}

}
