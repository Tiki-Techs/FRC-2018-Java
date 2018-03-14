package org.usfirst.frc.team3880.robot;

public class RobotMap {
	
	public int BACK_RIGHT_DRIVE_TALON = 1;
	public int BACK_LEFT_DRIVE_TALON = 3;
	public int FRONT_RIGHT_DRIVE_TALON = 4;
	public int FRONT_LEFT_DRIVE_TALON = 2;
	
	public int LIFT_TALON = 6;
	
	public int CLIMB_LEFT_VICTOR = 0;
	public int CLIMB_RIGHT_VICTOR = 1;
	
	public int INTAKE_LEFT_TALON= 0;
	public int INTAKE_RIGHT_TALON = 1;
	public int TEST_TALON = 0;
	
	public int INTAKE_TILT_LIMIT = 0;
	
	public int COMPRESSOR = 0;
	
	public int SHIFT_MODULE_NUMBER = 0;
	public int SHIFT_FORWARD = 6;
	public int SHIFT_BACKWARD = 7;
	
	public int LEFTARM_PISTON_MODULE_NUMBER = 0;
	public int LEFTARM_PISTON_FORWARD = 0;
	public int LEFTARM_PISTON_BACKWARD = 1;
	
	public int RIGHTARM_PISTON_MODULE_NUMBER = 0;
	public int RIGHTARM_PISTON_FORWARD = 2;
	public int RIGHTARM_PISTON_BACKWARD = 3;
	
	public int LOWER_LIFT_LIMIT = 5;
	public int UPPER_LIFT_LIMIT = 4;
	
	public int RIGHT_INTAKE_INNER_LIMIT = 6;
	public int LEFT_INTAKE_INNER_LIMIT = 7;
	
	
	
    private static RobotMap instance;
    
	public static  RobotMap getInstance() {
        if (instance == null) {
            instance = new RobotMap();
        }
        return instance;
    }
	
	
}
