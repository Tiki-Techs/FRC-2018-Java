/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3880.robot;
import edu.wpi.first.wpilibj.GyroBase;

import java.util.Arrays;
import java.util.stream.Stream;

import org.usfirst.frc.team3880.robot.commands.CommandBase;
import org.usfirst.frc.team3880.robot.commands.autonomous.Autonomous_DriveStraight;
import org.usfirst.frc.team3880.robot.subsystems.Lift;
import org.usfirst.frc.team3880.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

import org.usfirst.frc.team3880.robot.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
@SuppressWarnings("unused")
public class Robot extends IterativeRobot {
	
	Command autonomousCommand;
	
	NetworkTable table;
	
	char robotPosition;
		

	private SendableChooser<String> m_chooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		CommandBase.init();
		
		CameraServer.getInstance().startAutomaticCapture(0);
//		CameraServer.getInstance().startAutomaticCapture(1);
		
		m_chooser = new SendableChooser<>();
		
		
//		CommandBase.gyro.gyro.calibrate();
		
		
		log();
		
		// robotPosition = (L/C/R)
		
//		CommandBase.gyro.gyro.calibrate();
		
//		m_chooser.addDefault("Default Auto", "");
//		m_chooser.addObject("My Auto", kCustomAuto);
//		SmartDashboard.putData("Auto choices", m_chooser);
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
//		m_autoSelected = m_chooser.getSelected() = SmartDashboard.getString("Auto Selector",
		// defaultAuto);;
		// autoSelected
//		System.out.println("Auto selected: " + m_autoSelected);
		
		String gameData;

		char switchPosition;
		char scalePosition;
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		//Defaulting to "LLL" to prevent null pointer
		if(gameData == null) {
			gameData = "LLL";
		}
		
		if (gameData.length() > 0) {
			switchPosition = gameData.charAt(0);
			scalePosition = gameData.charAt(1);
				
			autonomousCommand = new Autonomous_DriveStraight();
			
//			if (robotPosition == 'L' && closeSwitchPosition == 'L' && scalePosition == 'L') {
//				autonomousCommand = new Autonomous_LeftLeftLeft();
//			}
//			if (robotPosition == 'C' && closeSwitchPosition == 'L' && scalePosition == 'L') {
//				autonomousCommand = new Autonomous_CenterLeftLeft();
//			}
//			if (robotPosition == 'R' && closeSwitchPosition == 'L' && scalePosition == 'L') {
//				autonomousCommand = new Autonomous_RightLeftLeft();
//			}
//			
//			if (robotPosition == 'L' && closeSwitchPosition == 'R' && scalePosition == 'L') {
//				autonomousCommand = new Autonomous_LeftRightLeft();
//			}
//			
//			if (robotPosition == 'R' && closeSwitchPosition == 'L' && scalePosition == 'L') {
//				autonomousCommand = new Autonomous_RightLeftLeft();
//			}
//			
//			if (robotPosition == 'L' && closeSwitchPosition == 'R' && scalePosition == 'L') {
//				autonomousCommand = new Autonomous_LeftRightLeft();
//			}
//			if (robotPosition == 'C' && closeSwitchPosition == 'R' && scalePosition == 'L') {
//				autonomousCommand = new Autonomous_CenterLeftLeft();
//			}
//			if (robotPosition == 'R' && closeSwitchPosition == 'R' && scalePosition == 'L') {
//				autonomousCommand = new Autonomous_RightRightLeft();
//			}
//			
//			if (robotPosition == 'L' && closeSwitchPosition == 'L' && scalePosition == 'R') {
//				autonomousCommand = new Autonomous_LeftLeftRight();
//			}
//			if (robotPosition == 'C' && closeSwitchPosition == 'L' && scalePosition == 'R') {
//				autonomousCommand = new Autonomous_CenterLeftRight();
//			}
//			if (robotPosition == 'R' && closeSwitchPosition == 'L' && scalePosition == 'R') {
//				autonomousCommand = new Autonomous_RightLeftRight();
//			}
//			
//			if (robotPosition == 'L' && closeSwitchPosition == 'R' && scalePosition == 'R') {
//				autonomousCommand = new Autonomous_LeftRightRight();
//			}
//			if (robotPosition == 'C' && closeSwitchPosition == 'R' && scalePosition == 'R') {
//				autonomousCommand = new Autonomous_CenterRightRight();
//			}
//			if (robotPosition == 'R' && closeSwitchPosition == 'R' && scalePosition == 'R') {
//				autonomousCommand = new Autonomous_RightRightRight();
//			}
			
		}
		else {
			autonomousCommand = new Autonomous_DriveStraight();
		}

        SmartDashboard.putData(autonomousCommand);

	}
	
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        log();
	}
	
	public void teleopInit()
    {
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to 
	// continue until interrupted by another command, remove
	// this line or comment it out.
//        Scheduler.getInstance().add(new DriveStandard());
    }
	
	/**
     * This function is called periodically during operator control
     */
	@Override
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
        log();
    }

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		log();
	}
	
	
	public void log() {
//		SmartDashboard.putNumber("driveEncoderLeftRate", CommandBase.drive.getEncoderLeftRate());
//		SmartDashboard.putNumber("driveEncoderLeftDist", CommandBase.drive.getEncoderLeftDist());
//		SmartDashboard.putNumber("driveEncoderRightRate", CommandBase.drive.getEncoderRightRate());
//		SmartDashboard.putNumber("driveEncoderRightDist", CommandBase.drive.getEncoderRightDist());

		
		SmartDashboard.putNumber("liftEncoderVel", CommandBase.lift.getEncoderVelocity());
		SmartDashboard.putNumber("liftEncoderPos", CommandBase.lift.getEncoderPosition());

//		SmartDashboard.putNumber("gyroAngleX", CommandBase.gyro.gyro.getAngleX());
//		SmartDashboard.putNumber("gyroAngleY", CommandBase.gyro.gyro.getAngleY());
//		SmartDashboard.putNumber("gyroAngleZ", CommandBase.gyro.gyro.getAngleZ());
		
		SmartDashboard.putBoolean("lift lower limit", CommandBase.lift.getLowerLimit());
		SmartDashboard.putBoolean("lift upper limit", CommandBase.lift.getUpperLimit());
		

	}
	
	
}
