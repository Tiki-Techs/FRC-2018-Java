package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro extends Subsystem {

	public ADIS16448_IMU gyro;
	RobotMap hardware;
	
	double offset;

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
		gyro.reset();
		offset = getGyroAngle();
	}

	public double getGyroAngle() {
		double adjAngle = ((2*gyro.getYaw()) % 360) - offset;
		
		System.out.println(adjAngle);

		adjAngle = Math.abs(360-adjAngle);
		
		if (adjAngle < 0) {
			adjAngle += 360;
		}

		return adjAngle;
	}
	
	public void setOffset() {
		offset = 0;
		gyro.reset();
		offset = getGyroAngle();
		System.out.println(getGyroAngle());
	}

	public boolean withinDeadZone(double target) {
		return Math.abs(getGyroAngle() - target) <= SmartDashboard.getNumber("globalGyroDeadzone", 5.0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
