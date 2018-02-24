package org.usfirst.frc.team3880.robot;

public class RobotMap {
	
	public int BACK_RIGHT_DRIVE_TALON = 1;
	public int BACK_LEFT_DRIVE_TALON = 3;
	public int FRONT_RIGHT_DRIVE_TALON = 4;
	public int FRONT_LEFT_DRIVE_TALON = 2;
	
	public int LIFT_TALON = 0;
	
	public int CLIMB_LEFT_VICTOR = 0;
	public int CLIMB_RIGHT_VICTOR = 1;
	
	public int LEFT_INTAKE_30 = 2;
	public int LEFT_INTAKE_20 = 3;
	public int RIGHT_INTAKE_30 = 4;
	public int RIGHT_INTAKE_20 = 5;
	
	public int COMPRESSOR = 0;
	
	public int GYRO = 1;
	
	public int SHIFT_MODULE_NUMBER = 0;
	public int SHIFT_FORWARD = 6;
	public int SHIFT_BACKWARD = 7;
	
	public int LOWER_LIFT_LIMIT = 4;
	public int UPPER_LIFT_LIMIT = 5;
	
	public int RIGHT_INTAKE_INNER_LIMIT = 6;
	public int LEFT_INTAKE_INNER_LIMIT = 7;
	public int LEFT_INTAKE_OUTER_LIMIT = 8;
	public int RIGHT_INTAKE_OUTER_LIMIT = 9;
	
	
    private static RobotMap instance;
    
	public static  RobotMap getInstance() {
        if (instance == null) {
            instance = new RobotMap();
        }
        return instance;
    }
	
	
}
