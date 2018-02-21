package org.usfirst.frc.team3880.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class CubeIntakeArms extends CommandBase {
	
	double INTAKE_VALUE = .5;
	
	public CubeIntakeArms() {
    	requires(leftArm);
    	requires(rightArm);
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
      	
    	
    	if (!leftArm.getInnerLimit()) {
    		leftArm.set(-INTAKE_VALUE);
    	}
    	else {
    		leftArm.set(0);
    	}
    
    	if (!rightArm.getInnerLimit()) {
    		rightArm.set(INTAKE_VALUE);
    	}
    	else {
    		rightArm.set(0);
    	}
    }

    @Override
    protected void end() {
    	leftArm.set(0);
    	rightArm.set(0);
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
