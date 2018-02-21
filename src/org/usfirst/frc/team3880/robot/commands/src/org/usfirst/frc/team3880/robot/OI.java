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
	public Button 	button1 = new JoystickButton(joy1, 1), 
					button2 = new JoystickButton(joy1, 2),
					button3 = new JoystickButton(joy1, 3), 
					button4 = new JoystickButton(joy1, 4),
					button5 = new JoystickButton(joy1, 5), 
					button6 = new JoystickButton(joy1, 6),
					button7 = new JoystickButton(joy1, 7), 
					button8 = new JoystickButton(joy1, 8),
					button9 = new JoystickButton(joy1, 9),
					button10 = new JoystickButton(joy1, 10),
					button11 = new JoystickButton(joy1, 11),
					button12 = new JoystickButton(joy1, 12);
		
	public Joystick xbox = new Joystick(1);
	public Button 	AButton = new JoystickButton(xbox, 0), 
					BButton = new JoystickButton(xbox, 1),
					XButton = new JoystickButton(xbox, 2), 
					YButton = new JoystickButton(xbox, 3),
					leftButton = new JoystickButton(xbox, 4),
					rightButton = new JoystickButton(xbox, 5),
					leftStickButton = new JoystickButton(xbox, 8),
					rightStickButton = new JoystickButton(xbox, 9);			
	
//	public Trigger 
	
	double DEAD_ZONE_Y = 0.1;
	double DEAD_ZONE_X = 0.1;
	
	public double getY() {
		if(Math.abs(joy1.getY()) >= DEAD_ZONE_Y ) {
			return joy1.getY();
		}
		else {
			return 0;
		}
	}
	
	public double getX() {
		if(Math.abs(joy1.getX()) >= DEAD_ZONE_X ) {
			return joy1.getX();
		}
		else {
			return 0;
		}
	}
	
	public int getPOV() {
		return joy1.getPOV();
	}
	
	public double getThrottle() {
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
	
	public double getXboxLeftTrigger() {
		return xbox.getRawAxis(2);
	}
	
	public double getXboxRightTrigger() {
		return xbox.getRawAxis(3);
	}
	
	public int getXboxPOV() {
		return xbox.getPOV();
	}
	
	
	public OI () {
		
		
		button1.whileHeld(new LiftUp());
		button2.whileHeld(new LiftDown());
		
		button3.whenPressed(new ShiftSpeed());
		button4.whenPressed(new ShiftTorque());
		
		button5.whileHeld(new CubeIntakeWheels());
		button6.whileHeld(new CubeOuttakeWheels());

		//button8
		
		button7.whileHeld(new ClimbUp());
		
		button9.whileHeld(new LeftArmIn());
		button10.whileHeld(new RightArmIn());
		button11.whileHeld(new LeftArmOut());
		button12.whileHeld(new RightArmOut());
		
		leftStickButton.whenPressed(new LeftArmJoystickControl());
		rightStickButton.whenPressed(new RightArmJoystickControl());

		
		// POV
		// Throttle
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