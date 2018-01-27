/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3880.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team3880.robot.commands.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Relay.Value.*;
import edu.wpi.first.wpilibj.command.Command;
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
	Command toggle;
	
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	TalonSRX frontRightDrive = new TalonSRX(1);
	TalonSRX middleRightDrive = new TalonSRX(2);
	TalonSRX backRightDrive = new TalonSRX(3);
	TalonSRX frontLeftDrive = new TalonSRX(4);
	TalonSRX middleLeftDrive = new TalonSRX(5);
	TalonSRX backLeftDrive = new TalonSRX(6);
	DoubleSolenoid shift = new DoubleSolenoid(0, 1);
	NetworkTable table;
	public static boolean turbo = false;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
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

		//toggle shift solenoid
		frontRightDrive.set(ControlMode.PercentOutput, -(OI.joy1.getY() - OI.joy1.getX()));
		middleRightDrive.set(ControlMode.PercentOutput, OI.joy1.getY() - OI.joy1.getX());
		backRightDrive.set(ControlMode.PercentOutput, -(OI.joy1.getY() - OI.joy1.getX()));
		// configure driver control for right side Y
		frontLeftDrive.set(ControlMode.PercentOutput, -(OI.joy1.getY() + OI.joy1.getX()));
		middleLeftDrive.set(ControlMode.PercentOutput, OI.joy1.getY() + OI.joy1.getX());
		backLeftDrive.set(ControlMode.PercentOutput, OI.joy1.getY() + OI.joy1.getX());
		// configure driver control for left side
		if (OI.button5.get()) {
			shift.set(DoubleSolenoid.Value.kForward);
		}
		if (OI.button6.get()) {
			shift.set(DoubleSolenoid.Value.kReverse);		
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
