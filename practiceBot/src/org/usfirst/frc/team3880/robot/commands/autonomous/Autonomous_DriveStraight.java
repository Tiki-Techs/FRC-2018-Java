package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Autonomous_DriveStraight extends CommandBase {

	double DRIVE_VALUE = 0.1;
	public Autonomous_DriveStraight() {
		requires(lift);
		requires(intake);
	}
	
	 @Override
	 protected void initialize() {
	    	
	 }


	 @Override
	 protected void execute() {
	    	
	 }

	 @Override
	 protected void end() {
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
