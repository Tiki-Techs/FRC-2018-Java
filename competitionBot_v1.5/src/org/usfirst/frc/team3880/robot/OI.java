/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3880.robot;

import edu.wpi.first.wpilibj.Joystick;

import org.usfirst.frc.team3880.robot.triggers.*;

import org.usfirst.frc.team3880.robot.commands.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	double xboxSensivity = 0.85;

	public Joystick joy1 = new Joystick(0);
	public Button 	buttonR1 = new JoystickButton(joy1, 1), 
					buttonR2 = new JoystickButton(joy1, 2),
					buttonR3 = new JoystickButton(joy1, 3), 
					buttonR4 = new JoystickButton(joy1, 4),
					buttonR5 = new JoystickButton(joy1, 5), 
					buttonR6 = new JoystickButton(joy1, 6),
					buttonR7 = new JoystickButton(joy1, 7), 
					buttonR8 = new JoystickButton(joy1, 8),
					buttonR9 = new JoystickButton(joy1, 9),
					buttonR10 = new JoystickButton(joy1, 10),
					buttonR11 = new JoystickButton(joy1, 11),
					buttonR12 = new JoystickButton(joy1, 12);
	
	public Joystick joy2 = new Joystick(1);
	public Button 	buttonL1 = new JoystickButton(joy2, 1), 
					buttonL2 = new JoystickButton(joy2, 2),
					buttonL3 = new JoystickButton(joy2, 3), 
					buttonL4 = new JoystickButton(joy2, 4),
					buttonL5 = new JoystickButton(joy2, 5), 
					buttonL6 = new JoystickButton(joy2, 6),
					buttonL7 = new JoystickButton(joy2, 7), 
					buttonL8 = new JoystickButton(joy2, 8),
					buttonL9 = new JoystickButton(joy2, 9),
					buttonL10 = new JoystickButton(joy2, 10),
					buttonL11 = new JoystickButton(joy2, 11),
					buttonL12 = new JoystickButton(joy2, 12);
		
	public static Joystick xbox = new Joystick(2);
	public Button 	AButton = new JoystickButton(xbox, 1), 
					BButton = new JoystickButton(xbox, 2),
					XButton = new JoystickButton(xbox, 3), 
					YButton = new JoystickButton(xbox, 4),
					leftButton = new JoystickButton(xbox, 5),
					rightButton = new JoystickButton(xbox, 6),
					selectButton = new JoystickButton(xbox, 7),
					startButton = new JoystickButton(xbox, 8),
					leftStickButton = new JoystickButton(xbox, 9);	
	private Trigger xboxLeftTrigger = new XboxLeftTrigger();
	private Trigger xboxRightTrigger = new XboxRightTrigger();
	
	public OI (int mode) {
		
		if (mode == 1) {
			// 1 joystick + xbox
			
			/* MAIN JOYSTICK */
		
			buttonR1.whileHeld(new LiftUp());
			buttonR2.whileHeld(new LiftDown());
			
			buttonR3.whenPressed(new ShiftSpeed());
			buttonR4.whenPressed(new ShiftTorque());
			
			buttonR5.whileHeld(new CubeIntakeWheels());
			buttonR6.whileHeld(new CubeOuttakeWheels());
	
			//button8
			
			buttonR7.whileHeld(new ClimbUp());
			buttonR8.whileHeld(new ClimbDown());
			
			buttonR9.whileHeld(new ArmsIn());
			buttonR11.whileHeld(new LeftArmOut());
			
			// POV
			// Throttle
			
	
			/* XBOX */
		
			
			leftButton.whileHeld(new LeftWheelOut());
			rightButton.whileHeld(new RightWheelOut());
			
		}
		
		if (mode == 2) {
			// 2 joystick tank drive + joystick
			buttonR1.whileHeld(new LiftUp());
			buttonL1.whileHeld(new LiftDown());
			
			buttonR2.whileHeld(new ClimbUp());
			buttonR12.whileHeld(new ClimbDown());
			
			buttonR3.whenPressed(new ShiftSpeed());
			buttonR4.whenPressed(new ShiftTorque());
			
			buttonL2.whileHeld(new CubeIntakeWheels());
			buttonL3.whileHeld(new CubeOuttakeWheels());
			
			
			buttonL4.whileHeld(new LeftArmOut());
			buttonL5.whileHeld(new ArmsIn());

			
			leftButton.whileHeld(new LeftWheelOut());
			rightButton.whileHeld(new RightWheelOut());
		}
		if(mode == 3) {
			AButton.whileHeld(new LiftDown());
			YButton.whileHeld(new LiftUp());
			
			XButton.whenPressed(new ShiftTorque());
			BButton.whenPressed(new ShiftSpeed());
			
			leftButton.whileHeld(new CubeOuttakeWheels());
			rightButton.whileHeld(new CubeIntakeWheels());
			
			selectButton.whenPressed(new CubeIntakeArms());
			startButton.whenPressed(new CubeOuttakeArms());
			
			leftStickButton.whileHeld(new ClimbUp());
			
			
//			xboxRightTrigger.whileActive(new WindowMotorUp());
//			xboxLeftTrigger.whileActive(new WindowMotorDown());
			
			
			// controller 2- intake and lift
			buttonR3.whileHeld(new CubeIntakeWheels());
			buttonR4.whileHeld(new CubeOuttakeWheels());
			
			buttonR5.whenPressed(new CubeIntakeArms());
			buttonR6.whenPressed(new CubeOuttakeArms());
			
			buttonR1.whileHeld(new LiftUp());
			buttonR2.whileHeld(new LiftDown());
			
			buttonR9.whileHeld(new ClimbUp());
			buttonR10.whileHeld(new ClimbDown());
			
			
		}

	}
	
	
	double DEAD_ZONE_Y = 0.1;
	double DEAD_ZONE_X = 0.1;
	
	double JOY2_DEAD_ZONE_Y = 0.1;
	double JOY2_DEAD_ZONE_X = 0.1;
	
	
	
	public double getRightY() {
		if(Math.abs(joy1.getY()) >= DEAD_ZONE_Y ) {
			return joy1.getY();
		}
		else {
			return 0;
		}
	}
	
	public double getRightX() {
		if(Math.abs(joy1.getX()) >= DEAD_ZONE_X ) {
			return joy1.getX();
		}
		else {
			return 0;
		}
	}
	
	public int getRightPOV() {
		return joy1.getPOV();
	}
	
	public double getRightThrottle() {
		return joy1.getThrottle();
	}
	
	public double getLeftY() {
		if(Math.abs(joy2.getY()) >= JOY2_DEAD_ZONE_Y ) {
			return joy2.getY();
		}
		else {
			return 0;
		}
	}
	
	public double getLeftX() {
		if(Math.abs(joy2.getX()) >= JOY2_DEAD_ZONE_X ) {
			return joy2.getX();
		}
		else {
			return 0;
		}
	}
	
	public int getLeftPOV() {
		return joy1.getPOV();
	}
	
	public double getLeftThrottle() {
		return joy1.getThrottle();
	}
	
	
	public double getXboxLeftX() {
			return xbox.getRawAxis(0);
	}
	
	public double getXboxLeftY() {
		return xbox.getRawAxis(1);
	}
	
	public double getXboxRightX() {
		return xbox.getRawAxis(4);
	}
	
	public double getXboxRightY() {
		return xbox.getRawAxis(5);
	}
	
	public static double getXboxLeftTrigger() {
		if(Math.abs(xbox.getRawAxis(2)) >= 0.1) {
			return xbox.getRawAxis(2);
		}
		else {
			return 0;
		}
	}
	
	public static double getXboxRightTrigger() {
		if(Math.abs(xbox.getRawAxis(3)) >= 0.1) {
			return xbox.getRawAxis(3);
		}
		else {
			return 0;
		}
	}
	
	public int getXboxPOV() {
		return xbox.getPOV();
	}
	

	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
