package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimbPneumatics extends Subsystem {
	
	DoubleSolenoid climbPiston;
	
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
    	
    	climbPiston = new DoubleSolenoid(
    			hardware.CLIMB_PISTON_MODULE_NUMBER, 
    			hardware.CLIMB_PISTON_FORWARD, 
    			hardware.CLIMB_PISTON_REVERSE
    			);    }
    
    public void pull() {
    	climbPiston.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void push() {
    	climbPiston.set(DoubleSolenoid.Value.kForward);
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
