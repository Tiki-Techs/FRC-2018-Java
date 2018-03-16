package org.usfirst.frc.team3880.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
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
	
//	public void set(double forward, double turn) {
//		backLeftDrive.set(ControlMode.PercentOutput, - (forward - turn));
//		frontLeftDrive.set(ControlMode.PercentOutput, -(forward - turn));
//		backRightDrive.set(ControlMode.PercentOutput, forward + turn);
//		frontRightDrive.set(ControlMode.PercentOutput, forward + turn);
//    }
	
	public void set(double left, double right) {
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

	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveStandard());
	}

}
