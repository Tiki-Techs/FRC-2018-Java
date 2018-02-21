package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;

public class Autonomous_CenterRightLeft extends CommandBase {

	public Autonomous_CenterRightLeft() {
		requires(drive);
		requires(lift);
		requires(gyro);
		requires(intakeWheels);
		requires(leftArm);
		requires(rightArm);	}
	
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
