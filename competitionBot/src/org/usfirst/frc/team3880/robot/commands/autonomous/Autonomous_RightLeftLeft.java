package org.usfirst.frc.team3880.robot.commands.autonomous;

import org.usfirst.frc.team3880.robot.commands.CommandBase;

public class Autonomous_RightLeftLeft extends CommandBase {

	public Autonomous_RightLeftLeft() {
		requires(drive);
		requires(lift);
		requires(gyro);
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
