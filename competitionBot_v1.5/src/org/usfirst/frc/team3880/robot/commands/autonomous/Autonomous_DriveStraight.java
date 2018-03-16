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
	   	 double LEFT_ENC_DISTANCE = CommandBase.drive.getEncoderLeftDist();
	     double RIGHT_ENC_DISTANCE = CommandBase.drive.getEncoderLeftDist();
		 while(timer.get() < 1.2) {
			 drive.backLeftDrive.set(ControlMode.PercentOutput, -.4);
			 drive.frontLeftDrive.set(ControlMode.PercentOutput, -.4);
			 drive.backRightDrive.set(ControlMode.PercentOutput, .4);
			 drive.frontRightDrive.set(ControlMode.PercentOutput, .4);

//			 if(LEFT_ENC_DISTANCE > RIGHT_ENC_DISTANCE) {
//				 drive.backLeftDrive.set(ControlMode.PercentOutput, .35);
//				 drive.frontLeftDrive.set(ControlMode.PercentOutput, .35);
//				 drive.backRightDrive.set(ControlMode.PercentOutput, .45);
//				 drive.frontRightDrive.set(ControlMode.PercentOutput, .45);
//			 }
//			 else if(LEFT_ENC_DISTANCE < RIGHT_ENC_DISTANCE) {
//				 drive.backLeftDrive.set(ControlMode.PercentOutput, .45);
//				 drive.frontLeftDrive.set(ControlMode.PercentOutput, .45);
//				 drive.backRightDrive.set(ControlMode.PercentOutput, .35);
//				 drive.frontRightDrive.set(ControlMode.PercentOutput, .35);
//			 }
//			 else {
//				 drive.backLeftDrive.set(ControlMode.PercentOutput, .4);
//				 drive.frontLeftDrive.set(ControlMode.PercentOutput, .4);
//				 drive.backRightDrive.set(ControlMode.PercentOutput, .4);
//				 drive.frontRightDrive.set(ControlMode.PercentOutput, .4);
//			 }
	 	}
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
