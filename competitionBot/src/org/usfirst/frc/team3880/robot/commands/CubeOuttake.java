package org.usfirst.frc.team3880.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class CubeOuttake extends CommandBase {
	double OUTTAKE_VALUE = -.1;
	public CubeOuttake() {
    	requires(intake);
    }

    @Override
    protected void initialize() {
    	
    }

    /*
     * execute() - In our execute method we call a tankDrive method we have created in our subsystem. This method takes two speeds as a parameter which we get from methods in the OI class.
     * These methods abstract the joystick objects so that if we want to change how we get the speed later we can do so without modifying our commands
     * (for example, if we want the joysticks to be less sensitive, we can multiply them by .5 in the getLeftSpeed method and leave our command the same).
     */
    @Override
    protected void execute() {
    	intake.spin(OUTTAKE_VALUE);
    	
    	
    	if (!intake.getLeftOuterLimit()) {
    		intake.leftArm(OUTTAKE_VALUE);
    	}
    	else {
    		intake.leftArm(0);
    	}
    	
    	if (!intake.getRightOuterLimit()) {
    		intake.rightArm(-OUTTAKE_VALUE);
    	}
    	else {
    		intake.rightArm(0);
    	}
    }

    @Override
    protected void end() {
    	intake.spin(0);
    	intake.leftArm(0);
    	intake.rightArm(0);
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
