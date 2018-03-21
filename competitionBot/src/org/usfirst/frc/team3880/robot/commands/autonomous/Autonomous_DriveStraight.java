package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Autonomous_DriveStraight extends CommandBase {
	Timer timer;
	
	double DRIVE_VALUE = 0.1;
	public Autonomous_DriveStraight() {
		requires(drive);
		requires(lift);
		requires(gyro);
		requires(leftIntakeWheel);
		requires(rightIntakeWheel);
		requires(intakeArms);
	}
	
	 @Override
	 protected void initialize() {
		 timer  = new Timer();
	    	timer.reset();
	    	timer.start();
	 }


	 @Override
	 protected void execute() {
	   	 double LEFT_ENC_DISTANCE = CommandBase.drive.getEncoderLeftDist();
	     double RIGHT_ENC_DISTANCE = CommandBase.drive.getEncoderLeftDist();
		 if (timer.get() < 2.8) {
			 System.out.println(timer.get());
			 CommandBase.drive.set(-0.4,-0.4);
	 	}
		 
		 else {
			 CommandBase.drive.set(0, 0);
		 }
	 }

	 @Override
	 protected void end() {
		 drive.backLeftDrive.set(ControlMode.PercentOutput, 0);
		 drive.frontLeftDrive.set(ControlMode.PercentOutput, 0);
		 drive.backRightDrive.set(ControlMode.PercentOutput, 0);
		 drive.frontRightDrive.set(ControlMode.PercentOutput, 0);
		 
		 double encoderDistanceLeft = drive.getEncoderLeftDist();
		 double encoderDistanceRight = drive.getEncoderRightDist();
		 
		 SmartDashboard.putNumber("Left Encoder auto distance", encoderDistanceLeft);
		 SmartDashboard.putNumber("Right encoder auto distance", encoderDistanceRight);
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
