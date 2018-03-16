package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Autonomous_DriveStraight extends CommandBase {
	Timer timer = new Timer();
	
	double DRIVE_VALUE = 0.1;
	public Autonomous_DriveStraight() {
		requires(drive);
		requires(lift);
		requires(gyro);
		requires(leftIntakeWheel);
		requires(rightIntakeWheel);
		requires(leftArm);
		requires(rightArm);
	}
	
	 @Override
	 protected void initialize() {
	    	timer.reset();
	    	timer.start();
	 }


	 @Override
	 protected void execute() {
//	    	double LEFT_ENC_RATE = CommandBase.drive.getEncoderLeftRate();
//	    	double RIGHT_ENC_RATE = CommandBase.drive.getEncoderLeftRate();
		 while(timer < 2) {
			 drive.backLeftDrive.set(ControlMode.PercentOutput, .4);
			 drive.frontLeftDrive.set(ControlMode.PercentOutput, .4);
			 drive.backRightDrive.set(ControlMode.PercentOutput, .4);
			 drive.frontRightDrive.set(ControlMode.PercentOutput, .4);
		 }
//	    	drive.backLeftDrive.set(ControlMode.PercentOutput, DRIVE_VALUE * (RIGHT_ENC_RATE / LEFT_ENC_RATE));
//	    	drive.frontLeftDrive.set(ControlMode.PercentOutput, DRIVE_VALUE * (RIGHT_ENC_RATE / LEFT_ENC_RATE));
//	    	drive.backRightDrive.set(ControlMode.PercentOutput, DRIVE_VALUE * -(LEFT_ENC_RATE / RIGHT_ENC_RATE));
//	    	drive.frontRightDrive.set(ControlMode.PercentOutput, DRIVE_VALUE * -(LEFT_ENC_RATE / RIGHT_ENC_RATE));
	 }

	 @Override
	 protected void end() {
		 drive.backLeftDrive.set(ControlMode.PercentOutput, 0);
		 drive.frontLeftDrive.set(ControlMode.PercentOutput, 0);
		 drive.backRightDrive.set(ControlMode.PercentOutput, 0);
		 drive.frontRightDrive.set(ControlMode.PercentOutput, 0);
	 }

	 @Override
	 protected void interrupted() {
	 	end();
	 }
	    
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
