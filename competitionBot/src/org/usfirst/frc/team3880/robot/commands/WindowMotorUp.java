package org.usfirst.frc.team3880.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class WindowMotorUp extends CommandBase {
	
	double POWER = 0.8;

	public WindowMotorUp() {
    	requires(windowMotor);
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
    	if (!windowMotor.getLimit()) {
    		if ( oi.getXboxRightTrigger() != 0 || oi.getXboxLeftTrigger() != 0) {
    	
    		windowMotor.set(oi.xbox.getRawAxis(3) - oi.xbox.getRawAxis(2));
    		}
    		else if (oi.joy1.getRawButton(7)){
        		windowMotor.set(1);
        	}
    		else if(oi.joy1.getRawButton(8)) {
        		windowMotor.set(-1);
        	}
    		else {
    			windowMotor.set(0);
    		}
    	}    
    	
    	else {
    		windowMotor.set(0);
    	}
    }

    @Override
    protected void end() {
    	windowMotor.set(0);
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
