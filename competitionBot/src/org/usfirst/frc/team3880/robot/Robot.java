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
import org.usfirst.frc.team3880.robot.commands.autonomous.*;

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
	//if false runs driveforward


	Command autonomousCommand;

	NetworkTable table;

	char robotPosition;


	private SendableChooser<Character> m_chooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		CommandBase.init();

		CameraServer.getInstance().startAutomaticCapture(0);

		m_chooser = new SendableChooser<Character>();


		CommandBase.gyro.gyro.calibrate();


		log();

		m_chooser.addDefault("Robot on left", 'L');
		m_chooser.addObject("Robot in center", 'C');
		m_chooser.addObject("Robot on right", 'R');
		SmartDashboard.putData("Auto choices", m_chooser);



        SmartDashboard.putNumber("autoFTR0FinalTime", 4.5);
        SmartDashboard.putNumber("autoFTR1FinalTime", 5.5);
        SmartDashboard.putNumber("autoFTR2FinalTime", 6);

        SmartDashboard.putNumber("autoFTR0LeftPct", -0.4);
        SmartDashboard.putNumber("autoFTR0RightPct", -0.4);

        // Motor duty-cycle for spin: left is set to `step1DrivePct` right to `-1 * step1DrivePct`
        SmartDashboard.putNumber("autoFTR1DrivePct", 0.4);
        SmartDashboard.putNumber("autoFTR1ClockwiseAngle", 90.0);
        SmartDashboard.putNumber("autoFTR1AngularDeadzone", 10.0);
        SmartDashboard.putNumber("autoFTR1RLiftPct", 0.7);

        SmartDashboard.putNumber("autoFTR2LeftPct", -0.4);
        SmartDashboard.putNumber("autoFTR2RightPct", -0.4);
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
		String gameData = null;

		char robotPosition = (char) m_chooser.getSelected();

		char switchPosition;
		char scalePosition;

		CommandBase.gyro.gyro.reset();
		CommandBase.drive.resetEncoders();

		gameData = DriverStation.getInstance().getGameSpecificMessage();


		//Defaulting to "LLL" to prevent null pointer
		if(gameData == null) {
			gameData = "LLL";
		}

		if (gameData.length() > 0) {
			switchPosition = gameData.charAt(0);
			scalePosition = gameData.charAt(1);

			if (switchPosition == robotPosition) {
				// autonomousCommand = new AutoLiftUp();
                autonomousCommand = new Autonomous_ForwardGyroForward();
			}
			else {
				autonomousCommand = new Autonomous_DriveStraight();
                // autonomousCommand = new Autonomous_ScoreAroundSwitch();
                // autonomousCommand = new Autonomous_ScoreScale();
			}
		}
		else {
			autonomousCommand = new Autonomous_DriveStraight();
		}

		if(autonomousCommand != null) {
			autonomousCommand.start();
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
        Scheduler.getInstance().add(new DriveStandard());
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
		SmartDashboard.putNumber("driveEncoderLeftRate", CommandBase.drive.getEncoderLeftRate());
		SmartDashboard.putNumber("driveEncoderLeftDist", CommandBase.drive.getEncoderLeftDist());
		SmartDashboard.putNumber("driveEncoderRightRate", CommandBase.drive.getEncoderRightRate());
		SmartDashboard.putNumber("driveEncoderRightDist", CommandBase.drive.getEncoderRightDist());

		SmartDashboard.putNumber("FRD current draw", CommandBase.drive.frontRightDrive.getOutputCurrent());
		SmartDashboard.putNumber("BRD current draw", CommandBase.drive.backRightDrive.getOutputCurrent());
		SmartDashboard.putNumber("FLD current draw", CommandBase.drive.frontLeftDrive.getOutputCurrent());
		SmartDashboard.putNumber("BLD current draw", CommandBase.drive.backLeftDrive.getOutputCurrent());


        SmartDashboard.putNumber("right DriveEnc pulse setting", CommandBase.drive.getEncoderRightDistancePerPulse());
        SmartDashboard.putNumber("left DriveEnc pulse setting", CommandBase.drive.getEncoderLeftDistancePerPulse());


		SmartDashboard.putNumber("liftEncoderVel", CommandBase.lift.getEncoderVelocity());
		SmartDashboard.putNumber("liftEncoderPos", CommandBase.lift.getEncoderPosition());

		SmartDashboard.putNumber("gyroAngleX", CommandBase.gyro.gyro.getAngleX());
		SmartDashboard.putNumber("gyroAngleY", CommandBase.gyro.gyro.getAngleY());
		SmartDashboard.putNumber("gyroAngleZ", CommandBase.gyro.gyro.getAngleZ());
        SmartDashboard.putNumber("gyro getAngle function", CommandBase.gyro.gyro.getAngle());
        SmartDashboard.putNumber("Gyro userdefined 'Angle'", CommandBase.gyro.getGyroAngle())

		SmartDashboard.putBoolean("lift upper limit", CommandBase.lift.getUpperLimit());
		SmartDashboard.putBoolean("lift lower limit", CommandBase.lift.getLowerLimit());

		SmartDashboard.putNumber("window potentiometer", CommandBase.windowMotor.getPot());
		SmartDashboard.putBoolean("window limit", CommandBase.windowMotor.getLimit());
	}
}
