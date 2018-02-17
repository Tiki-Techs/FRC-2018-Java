/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3880.robot;

import java.util.Arrays;
import java.util.stream.Stream;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	
	TalonSRX frontRightDrive = new TalonSRX(4);
	TalonSRX backRightDrive = new TalonSRX(1);


	TalonSRX frontLeftDrive = new TalonSRX(2);
	TalonSRX backLeftDrive = new TalonSRX(3);
	
	
	TalonSRX lift = new TalonSRX(0);
	
	Victor climbOne = new Victor(0);
	Victor climbTwo = new Victor(1);
	
	Victor intakeOne = new Victor(2);
	Victor intakeTwo = new Victor(3);
	Victor intakeThree = new Victor(4);
	Victor intakeFour = new Victor(5);
	
	
	Compressor c = new Compressor(0);
	DoubleSolenoid shift = new DoubleSolenoid(0, 6, 7);
	
	
	Encoder driveEncoderOne = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	Encoder driveEncoderTwo = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	
	NetworkTable table;
	AnalogGyro gyro;
	
	
	public static OI oi;
    
    
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		oi = new OI();
		
		driveEncoderOne.setDistancePerPulse(0.0092);
		driveEncoderTwo.setDistancePerPulse(0.0092);
		
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	public void drive(double forward, double turn, int time) {
		backLeftDrive.set(ControlMode.PercentOutput, - (forward - turn));
		frontLeftDrive.set(ControlMode.PercentOutput, -(forward - turn));
		backRightDrive.set(ControlMode.PercentOutput, forward + turn);
		frontRightDrive.set(ControlMode.PercentOutput, forward + turn);
		try {
			Thread.sleep(time);
		}
		catch(InterruptedException e) {
			System.out.println("guess i'll die");
		}
	}
	
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		if(oi.joy1.getRawButton(9)) 
		{
			intakeOne.set(1);
		}
		else
		{
			intakeOne.set(0);
		}
		if(oi.joy1.getRawButton(10)) 
		{
			intakeTwo.set(1);
		}
		else
		{
			intakeTwo.set(0);
		}		
		if(oi.joy1.getRawButton(11)) 
		{
			intakeThree.set(1);
		}
		else
		{
			intakeThree.set(0);
		}		
		if(oi.joy1.getRawButton(12)) 
		{
			intakeFour.set(1);
		}
		else
		{
			intakeFour.set(0);
		}
		
		backLeftDrive.set(ControlMode.PercentOutput, -(oi.joy1.getY() - oi.joy1.getX()));
		frontLeftDrive.set(ControlMode.PercentOutput, -(oi.joy1.getY() - oi.joy1.getX()));
		backRightDrive.set(ControlMode.PercentOutput, oi.joy1.getY() + oi.joy1.getX());
		frontRightDrive.set(ControlMode.PercentOutput, oi.joy1.getY() + oi.joy1.getX());
		
		if(oi.joy1.getRawButton(3)) {
			shift.set(DoubleSolenoid.Value.kForward);
		}
		else if(oi.joy1.getRawButton(4)) {
			shift.set(DoubleSolenoid.Value.kReverse);
		}
		
		if(oi.joy1.getRawButton(1)) {
			lift.set(ControlMode.PercentOutput, .3);
		}
		else if(oi.joy1.getRawButton(2)) {
			lift.set(ControlMode.PercentOutput, -.3);
		}
		else {
			lift.set(ControlMode.PercentOutput, 0);
		}
		
		
		if(oi.joy1.getRawButton(7)) {
			climbOne.set(-1);
			climbTwo.set(1);
		}
		else {
			climbOne.set(0);
			climbTwo.set(0);
		}
		
		SmartDashboard.putNumber("driveEncoderOne", driveEncoderOne.getDistance());
		SmartDashboard.putNumber("driveEncoderTwo", driveEncoderTwo.getDistance());

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
