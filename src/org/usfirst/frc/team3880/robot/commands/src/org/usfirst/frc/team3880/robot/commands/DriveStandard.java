package org.usfirst.frc.team3880.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStandard extends CommandBase {
	
    public DriveStandard() {
    	requires(drive);
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
    	drive.set(oi.getY(), oi.getX());
    }

    @Override
    protected void end() {
    	drive.backLeftDrive.set(ControlMode.Disabled, 0);
		drive.frontLeftDrive.set(ControlMode.Disabled, 0);
		drive.backRightDrive.set(ControlMode.Disabled, 0);
		drive.frontRightDrive.set(ControlMode.Disabled, 0);
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
