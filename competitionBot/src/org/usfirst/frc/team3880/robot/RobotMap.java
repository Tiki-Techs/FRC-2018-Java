package org.usfirst.frc.team3880.robot;

public class RobotMap {
	
	public int BACK_RIGHT_DRIVE_TALON = 1;
	public int BACK_LEFT_DRIVE_TALON = 3;
	public int FRONT_RIGHT_DRIVE_TALON = 4;
	public int FRONT_LEFT_DRIVE_TALON = 2;
	
	public int LIFT_TALON = 6;
	
	public int CLIMB_LEFT_VICTOR = 0;
	public int CLIMB_RIGHT_VICTOR = 1;
	
	public int LEFT_INTAKE_VICTOR = 2;
	public int RIGHT_INTAKE_VICTOR = 4;
	
	public int COMPRESSOR = 0;
	
	public int SHIFT_MODULE_NUMBER = 0;
	public int SHIFT_FORWARD = 6;
	public int SHIFT_BACKWARD = 7;
	
	public int ARM_MODULE_NUMBER = 0;
	public int ARM_FORWARD = 4;
	public int ARM_BACKWARD = 5;
	
	public int CLIMB_PISTON = 3;
	
	public int UPPER_LIFT_LIMIT = 5;
	public int LOWER_LIFT_LIMIT = 4;
	
	public int WINDOW_MOTOR_VICTOR = 3;
	public int WINDOW_UPPER_LIMIT = 6;
	
	
	public int DRIVE_ENCODER_LEFT_ONE = 0;
	public int DRIVE_ENCODER_LEFT_TWO = 1;
	public int DRIVE_ENCODER_RIGHT_ONE = 2;
	public int DRIVE_ENCODER_RIGHT_TWO = 3;
	
	
	
    private static RobotMap instance;
    
	public static  RobotMap getInstance() {
        if (instance == null) {
            instance = new RobotMap();
        }
        return instance;
    }
	
	
}
