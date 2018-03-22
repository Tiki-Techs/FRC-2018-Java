package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem {

	public ADIS16448_IMU gyro;
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
    	gyro = new ADIS16448_IMU();
    }

    public double getGyroAngle() {
        double adjAngle = gyro.getAngleZ() % 360;
        

        if (adjAngle < 0) {
            adjAngle += 360;
        }

        return adjAngle;
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
