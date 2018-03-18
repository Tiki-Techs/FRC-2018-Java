package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import edu.wpi.first.wpilibj.Victor;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3880.robot.commands.*;

public class Climb extends Subsystem {
	
	Victor climbOne;
	Victor climbTwo;
	
	RobotMap hardware;
	
	public static Climb instance;

    public static Climb getInstance() {
    	
	   if (instance == null) {
		   instance = new Climb();
	   }
	
	   return instance;
    }
	
	private Climb() {
		super("Climber");
		
        hardware = new RobotMap();
        
        try {

            climbOne = new Victor(hardware.CLIMB_LEFT_VICTOR);
            climbTwo = new Victor(hardware.CLIMB_RIGHT_VICTOR);
        }
        
        catch (Exception e) {
            System.out.println(e);
            System.out.println("Climb failed");
        }
		
	}
	
	public void climbUp(double motorValue) {
		climbOne.set(motorValue);
		climbTwo.set(-motorValue);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}
