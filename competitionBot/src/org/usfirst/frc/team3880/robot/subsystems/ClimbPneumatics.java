package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimbPneumatics extends Subsystem {
	
	Solenoid climbPiston;
	
	RobotMap hardware;
	
	public static ClimbPneumatics instance;

    public static ClimbPneumatics getInstance() {
    	
	   if (instance == null) {
		   instance = new ClimbPneumatics();
	   }
	
	   return instance;
    }
    
    private ClimbPneumatics() {
    	
    	hardware = new RobotMap();
    	
    	climbPiston = new Solenoid(hardware.CLIMB_PISTON);
    }
    
    public void push() {
    	climbPiston.set(true);
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
