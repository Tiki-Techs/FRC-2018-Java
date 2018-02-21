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
	
	Encoder driveEncoderOne;
	Encoder driveEncoderTwo;
	
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
        	driveEncoderOne = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
        	driveEncoderTwo = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

    		driveEncoderOne.setDistancePerPulse(0.0092);
    		driveEncoderTwo.setDistancePerPulse(0.0092);
    		
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
	
	public void set(double forward, double turn) {
		backLeftDrive.set(ControlMode.PercentOutput, - (forward - turn));
		frontLeftDrive.set(ControlMode.PercentOutput, -(forward - turn));
		backRightDrive.set(ControlMode.PercentOutput, forward + turn);
		frontRightDrive.set(ControlMode.PercentOutput, forward + turn);
    }
	
	public double getEncoderOne() {
		return driveEncoderOne.getDistance();
	}
	
	public double getEncoderTwo() {
		return driveEncoderTwo.getDistance();
	}

	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveStandard());
	}

}
