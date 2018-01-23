/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3880.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.networktables.*;
import java.util.concurrent.TimeUnit;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */


public class Robot extends IterativeRobot{
	boolean turbotoggle = true;
	Joystick leftJoy = new Joystick(0);
	Button 	button1 = new JoystickButton(leftJoy, 1), 
			button2 = new JoystickButton(leftJoy, 2),
			button3 = new JoystickButton(leftJoy, 3), 
			button4 = new JoystickButton(leftJoy, 4),
			button5 = new JoystickButton(leftJoy, 5), 
			button6 = new JoystickButton(leftJoy, 6),
			button7 = new JoystickButton(leftJoy, 7), 
			button8 = new JoystickButton(leftJoy, 8);
	Victor victorZero = new Victor(0);
	Victor victorOne = new Victor(1);
	Victor victorTwo = new Victor(2);
	Victor victorThree = new Victor(3);
	public Gyro gyro = new AnalogGyro(0);
	NetworkTable table;
	
	private Timer m_timer = new Timer();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		gyro.reset();
		gyro.calibrate();
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		m_timer.reset();
		m_timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		/**
		while(m_timer.get() < 2) {
			victorZero.set(1);
			victorOne.set(1);
			victorTwo.set(1);
			victorThree.set(1);
		}
		while(m_time.get() < 2) {
			victorZero.set(1);
			victorOne.set(1);
			victorTwo.set(-1);
			victorThree.set(-1);
		}**/
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
		victorZero.set(0);
		victorOne.set(0);
		victorTwo.set(0);
		victorThree.set(0);
		gyro.reset();
		gyro.calibrate();
	}
	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		Thread toggle= new Thread(() -> {
		    while (!Thread.interrupted()) {
		    	//Button Press Code
				if (button1.get()) {
					if (turbotoggle == true) {
						turbotoggle = false;
						try {
							TimeUnit.MILLISECONDS.sleep(150);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						turbotoggle = true;
						try {
							TimeUnit.MILLISECONDS.sleep(150);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		    }});
		
		toggle.start();
		
		SmartDashboard.putNumber("GyroValue", gyro.getAngle());
		victorZero.set(leftJoy.getY() + leftJoy.getX());
		victorOne.set(leftJoy.getY() + leftJoy.getX());
		victorTwo.set(-leftJoy.getY() + leftJoy.getX());
		victorThree.set(-leftJoy.getY() + leftJoy.getX()); 
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}