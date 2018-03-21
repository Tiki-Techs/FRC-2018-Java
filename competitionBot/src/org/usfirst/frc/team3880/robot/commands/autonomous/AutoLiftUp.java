package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class AutoLiftUp extends CommandBase {
	Timer timer;
	
	double DRIVE_VALUE = 0.1;
	public AutoLiftUp() {
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
		 System.out.println(timer.get());
		
		 if(timer.get() <= .8) {
			 windowMotor.set(-.3);
			 //drop intake down
		 }
		 else if (timer.get() <= 4) {
			 CommandBase.drive.set(-0.4,-0.4);
			 lift.set(.3);
			 windowMotor.set(0);
			 //drive to switch
	 	}
		 
		 else if(timer.get() <= 4.5) {
			drive.backLeftDrive.set(ControlMode.Disabled, 0);
			drive.frontLeftDrive.set(ControlMode.Disabled, 0);
			drive.backRightDrive.set(ControlMode.Disabled, 0);
			drive.frontRightDrive.set(ControlMode.Disabled, 0); 
			lift.set(0.1);
			
			rightIntakeWheel.spin(-1);
			leftIntakeWheel.spin(1);
			//shoot 
			 
		 }
		 
		 else if (timer.get() <= 5.5) {
			 drive.set(0.4, 0.4);

			 rightIntakeWheel.spin(-0);
			 leftIntakeWheel.spin(-0);
		 }
		 else {
			 rightIntakeWheel.spin(0);
			 leftIntakeWheel.spin(0);
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
