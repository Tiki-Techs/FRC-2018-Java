package org.usfirst.frc.team3880.robot.subsystems;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.AnalogGyro;
import org.usfirst.frc.team3880.robot.commands.CommandBase;

public class Gyro extends CommandBase {

	public AnalogGyro gyro;
	
	public Gyro() {
		requires(drive);
	}
	
	 @Override
    protected void initialize() {
    	gyro = new AnalogGyro(1);
    }

    /*
     * execute() - In our execute method we call a tankDrive method we have created in our subsystem. This method takes two speeds as a parameter which we get from methods in the OI class.
     * These methods abstract the joystick objects so that if we want to change how we get the speed later we can do so without modifying our commands
     * (for example, if we want the joysticks to be less sensitive, we can multiply them by .5 in the getLeftSpeed method and leave our command the same).
     */
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
=======

import org.usfirst.frc.team3880.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem {
	
	public AnalogGyro gyro;
	RobotMap hardware;
	
	public static Gyro instance;

    public static Gyro getInstance() {
    	
	   if (instance == null) {
		   instance = new Gyro();
	   }
	
	   return instance;
    }
    
    private Gyro() {
    	
    	hardware = new RobotMap();
    	
    	gyro = new AnalogGyro(hardware.GYRO);
    	
    }
    
    

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

>>>>>>> c51b9ebd4d058e6b8851e7122ec18eb67adfb5a7
	}

}
