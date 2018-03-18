package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class AutoDriveForwardTurnRight extends CommandBase {
	Timer timer;
	
	double DRIVE_VALUE = 0.1;
	public AutoDriveForwardTurnRight() {
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
		
		 if(timer.get() < 0.5) {
			 windowMotor.set(-1);
		 }
		 else if(timer.get() < 4.5) {
			 drive.set(-.4, -.4);
		 }
		 else if(timer.get() < 5.5){
			 drive.set(.4,  -.4);
			 lift.set(.7);
		 }
		 else if(timer.get() < 6){
			 drive.set(-.4, -.4);
		 }
		 else if(timer.get() < 6.5) {
			 rightIntakeWheel.spin(-1);
			 leftIntakeWheel.spin(1);
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
