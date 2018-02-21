package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	
	Compressor c;
	DoubleSolenoid shift;
	
	RobotMap hardware;
	
	public static Pneumatics instance;

    public static Pneumatics getInstance() {
    	
	   if (instance == null) {
		   instance = new Pneumatics();
	   }
	
	   return instance;
    }
    
    private Pneumatics() {
    	
    	hardware = new RobotMap();
    	
    	c = new Compressor(hardware.COMPRESSOR);
    	
    	shift = new DoubleSolenoid(
    			hardware.SHIFT_MODULE_NUMBER, 
    			hardware.SHIFT_FORWARD, 
    			hardware.SHIFT_BACKWARD
    			);
    }
    
    public void Shift(DoubleSolenoid.Value value) {
    	shift.set(value);
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
