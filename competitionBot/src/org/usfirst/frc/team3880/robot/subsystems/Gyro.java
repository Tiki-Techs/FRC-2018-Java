package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		double adjAngle = gyro.getYaw() % 360;

		if (adjAngle < 0) {
			adjAngle += 360;
		}

		return adjAngle;
	}

	public boolean withinDeadZone(double target) {
		return Math.abs(getGyroAngle() - target) <= SmartDashboard.getNumber("globalGyroDeadzone", 10.0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
