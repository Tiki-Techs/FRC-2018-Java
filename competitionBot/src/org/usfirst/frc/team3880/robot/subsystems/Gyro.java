package org.usfirst.frc.team3880.robot.subsystems;


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

	}

}
