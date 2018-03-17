package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;

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
	   	 double LEFT_ENC_DISTANCE = drive.getEncoderLeftDist();
	     double RIGHT_ENC_DISTANCE = drive.getEncoderLeftDist();
		 if (timer.get() < 2.8) {
			 System.out.println(timer.get());
			 drive.setHeading(-0.4);

	 	}
		 
		 if (2.8 < timer.get() && timer.get() < 3.2) {
			 drive.drive(0, 0);
			 rightIntakeWheel.spin(0);
			 leftIntakeWheel.spin(0);
			 
			 if (!lift.getUpperLimit()) {
				 lift.set(0.5);
			 }
			 
		 }
		 
		 if (timer.get() > 3.2 && timer.get() < 4) {
			 drive.drive(0, 0);

			 rightIntakeWheel.spin(-1);
			 leftIntakeWheel.spin(-1);
			 
			 lift.set(0);
		 }
		 else {
			 drive.drive(0, 0);
			 
			 rightIntakeWheel.spin(0);
			 leftIntakeWheel.spin(0);
			 lift.set(0);

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
