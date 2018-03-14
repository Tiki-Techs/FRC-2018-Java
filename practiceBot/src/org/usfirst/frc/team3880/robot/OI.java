/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3880.robot;

import edu.wpi.first.wpilibj.Joystick;

import org.usfirst.frc.team3880.robot.commands.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

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
		
	public Joystick xbox = new Joystick(2);
	public Button 	AButton = new JoystickButton(xbox, 1), 
					BButton = new JoystickButton(xbox, 2),
					XButton = new JoystickButton(xbox, 3), 
					YButton = new JoystickButton(xbox, 4),
					leftButton = new JoystickButton(xbox, 5),
					rightButton = new JoystickButton(xbox, 6),
					leftStickButton = new JoystickButton(xbox, 9),
					rightStickButton = new JoystickButton(xbox, 10);			
	
//	public Trigger 
	
	public OI (int mode) {
		
		IntakeWheelsIn testIntakeWheelsIn = new IntakeWheelsIn();
		
		if (mode == 1) {
			// 1 joystick + xbox
			
			/* MAIN JOYSTICK */
		
//			buttonR1.whileHeld(new LiftUp());
//			buttonR2.whileHeld(new LiftDown());
		
			
			buttonR1.whileHeld(new IntakeTiltUp());
			buttonR2.whileHeld(new IntakeTiltDown());
			
			buttonR3.whileHeld(new IntakeWheelsIn());
			buttonR4.whileHeld(new IntakeWheelsOut());
	
			buttonR5.whenPressed(new IntakeRightArmIn());
			buttonR5.whenPressed(new IntakeLeftArmIn());
			
			buttonR6.whenPressed(new IntakeLeftArmOut());
			buttonR6.whenPressed(new IntakeLeftArmOut());
			
			//button8
			
			
			// POV
			// Throttle
			
	
			/* XBOX */
			
		}


	}
	
	
	double DEAD_ZONE_Y = 0.1;
	double DEAD_ZONE_X = 0.1;
	
	double JOY2_DEAD_ZONE_Y = 0.1;
	double JOY2_DEAD_ZONE_X = 0.1;
	
	double XBOX_DEAD_ZONE_LEFT_Y = 0.1;
	double XBOX_DEAD_ZONE_LEFT_X = 0.1;
	double XBOX_DEAD_ZONE_RIGHT_Y = 0.1;
	double XBOX_DEAD_ZONE_RIGHT_X = 0.1;
	
	
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
		if(Math.abs(xbox.getRawAxis(0)) > XBOX_DEAD_ZONE_LEFT_X) {
			return xbox.getRawAxis(0);
		}
		else {
			return 0;
		}
	}
	
	public double getXboxLeftY() {
		return xbox.getRawAxis(1);
	}
	
	public double getXboxRightX() {
		
		if(Math.abs(xbox.getRawAxis(4)) > XBOX_DEAD_ZONE_RIGHT_X) {
			return xbox.getRawAxis(4);
		}
		else {
			return 0;
		}
	}
	
	public double getXboxRightY() {
		return xbox.getRawAxis(5);
	}
	
	public double getXboxLeftTrigger() {
		return xbox.getRawAxis(2);
	}
	
	public double getXboxRightTrigger() {
		return xbox.getRawAxis(3);
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
