package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Autonomous_DriveStraight extends CommandBase {
	Timer timer;
	double autoTimerDuration;
	 double autoLeftDrivePercent;
	 double autoRightDrivePercent;

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
		 double LEFT_ENC_DISTANCE = CommandBase.drive.getEncoderLeftDist();
		 double RIGHT_ENC_DISTANCE = CommandBase.drive.getEncoderRightDist();
		 SmartDashboard.putNumber("Left Encoder distance at `execute()` -- expect 0", LEFT_ENC_DISTANCE);
		 SmartDashboard.putNumber("Right encoder auto distance at `execute()` -- expect 0", RIGHT_ENC_DISTANCE);
		 SmartDashboard.putNumber("Left Encoder distance per pulse", CommandBase.drive.getEncoderLeftDistancePerPulse());
		 SmartDashboard.putNumber("Right Encoder distance per pulse", CommandBase.drive.getEncoderRightDistancePerPulse());

		 //Get all the magic numbers from the dashboard
		 autoTimerDuration = SmartDashboard.getNumber("autoTimerDuration", 2.8);
		 autoLeftDrivePercent = SmartDashboard.getNumber("autoLeftDrivePercent", -0.4);
		 autoRightDrivePercent = SmartDashboard.getNumber("autoRightDrivePercent", -0.4);

		 // Change encoder distances based on SmartDashboard, or use current values
		 double newLeftDistancePerPulse = SmartDashboard.getNumber("autoLeftDistPerPulse", CommandBase.drive.getEncoderLeftDistancePerPulse());
		 double newRightDistancePerPulse = SmartDashboard.getNumber("autoRightDistPerPulse", CommandBase.drive.getEncoderRightDistancePerPulse());
		 CommandBase.drive.setEncoderLeftDistancePerPulse(newLeftDistancePerPulse);
		 CommandBase.drive.setEncoderRighttDistancePerPulse(newRightDistancePerPulse);

		 
		timer  = new Timer();
		timer.reset();
		timer.start();
	 }


	 @Override
	 protected void execute() {

		 if (timer.get() < autoTimerDuration) {
			 System.out.println(timer.get());
			 CommandBase.drive.set(autoLeftDrivePercent,autoRightDrivePercent);
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
