/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3880.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3880.robot.commands.*;
import org.usfirst.frc.team3880.robot.commands.CommandBase;
import org.usfirst.frc.team3880.robot.commands.autonomous.*;
import org.usfirst.frc.team3880.robot.commands.autonomous.Autonomous_DriveStraight;

import java.util.ArrayList;
import java.util.List;

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


    public List autoGyroLog;
    Command autonomousCommand;
    NetworkTable table;
    char robotPosition;
    boolean autoStarted;
    private SendableChooser<Character> m_chooser;
    private SendableChooser<Boolean> test_mode;
    private SendableChooser<Command> auto_selectable;
    private SendableChooser<String> auto_sameSideSelectable;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        try {
            SmartDashboard.putBoolean("window motor moved up during match", false);
            SmartDashboard.putBoolean("window motor moved down during match", false);

            if (CommandBase.gyro != null) {
                CommandBase.gyro.gyro.reset();
                CommandBase.gyro.setOffset();
            }
            autoGyroLog = new ArrayList();

            CameraServer.getInstance().startAutomaticCapture(0);

            m_chooser = new SendableChooser<Character>();

            test_mode = new SendableChooser<Boolean>();
            test_mode.addDefault("Manual Selection off", false);
            test_mode.addObject("Manual Selection on", true);
            SmartDashboard.putData("Manual Auto Selector", test_mode);

            auto_selectable = new SendableChooser<Command>();
            auto_selectable.addDefault("drive straight", new Autonomous_DriveStraight());
            auto_selectable.addObject("straight forward and score", new AutoLiftUp());
            auto_selectable.addObject("forward, turn left, and score", new AutoForwardTurnScore(270));
            auto_selectable.addObject("forward, turn right, and score", new AutoForwardTurnScore(90));
            auto_selectable.addObject("center two cube left", new AutoCenterTwoCube(315, 0, 90, 270, 0));
            auto_selectable.addObject("center two cube right", new AutoCenterTwoCube(45, 0, 270, 90, 0));
            SmartDashboard.putData("Test Mode Auto Selector", auto_selectable);

//            auto_sameSideSelectable = new SendableChooser<String>();
//            auto_sameSideSelectable.addDefault("ForwardTurnScore", "FTR");
//            auto_sameSideSelectable.addObject("DriveStraightAndBack", "StraightAndBack");
//            auto_sameSideSelectable.addObject("AutoLiftUp", "LiftUp");
//            SmartDashboard.putData("Select Mode Auto Selector", auto_sameSideSelectable);
            
            log();

            m_chooser.addDefault("Robot on left", 'L');
            m_chooser.addObject("Robot in center", 'C');
            m_chooser.addObject("Robot on right", 'R');
            m_chooser.addObject("drive straight", 'X');

            SmartDashboard.putNumber("globalGyroDeadzone", 5.0);

            SmartDashboard.putData("Auto choices", m_chooser);

            autoStarted = false;
        } catch (Throwable t) {
            SmartDashboard.putString("Exception caught in robotInit()", t.getMessage());
        }
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     * <p>
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        try {
            autoStarted = false;

            char robotPosition = (char) m_chooser.getSelected();

            System.out.println(robotPosition);

            CommandBase.gyro.gyro.reset();
//            CommandBase.gyro.setOffset();
            CommandBase.drive.resetEncoders();

            CommandBase.pneumatics.Shift(DoubleSolenoid.Value.kForward);

            String gameData = null;
            Timer autoTimeoutTimer = new Timer();
            while (autoTimeoutTimer.get() < 5) {
                // time out after 5 seconds trying to get game data
                String getData = DriverStation.getInstance().getGameSpecificMessage();
                if (getData != null) {
                    gameData = getData;
                    break;
                }
            }

            autonomousCommand = ((boolean) test_mode.getSelected()) ? (Command) auto_selectable.getSelected() : commandForGameDataAndSwitch(gameData, robotPosition);
            if (autonomousCommand != null) {
                autonomousCommand.start();
                autoStarted = true;
            } else {
                autonomousCommand = new Autonomous_DriveStraight();
                autonomousCommand.start();
                autoStarted = true;
            }

            System.out.println(autonomousCommand);

            SmartDashboard.putData(autonomousCommand);
            SmartDashboard.putString("auto data", robotPosition + " " + gameData);

            SmartDashboard.putNumber("auto start gyro", CommandBase.gyro.getGyroAngle());
        } catch (Throwable t) {
            SmartDashboard.putString("Exception caught in autoInit()", t.getMessage());
        }

    }

    private static Command commandForGameDataAndSwitch(String gameData, char robotPosition) {
        //Defaulting to "LLL" to prevent null pointer
        gameData = gameData == null ? "" : gameData;

        Command commandOrFallback = (gameData.length() > 1) ? commandForSwitchAndScale(gameData.charAt(0), gameData.charAt(1), robotPosition) : new Autonomous_DriveStraight();
        if (gameData.length() <= 1)
        {
            SmartDashboard.putString("auto", "drive straight");
        }
        return commandOrFallback;
    }

    private static Command commandForSwitchAndScale(char switchPosition, char scalePosition, char robotPosition) {
        SmartDashboard.putString("Switch Position", String.valueOf(switchPosition));

        Command commandBasedOnPosition = robotPosition == 'C' ? CommandForRobotInCenter(switchPosition) : CommandForRobotOnSide(switchPosition, robotPosition);
        return commandBasedOnPosition;
    }

    private static Command CommandForRobotOnSide(char switchPosition, char localRobotPosition) {
        Command sideCommand = null;
        if (switchPosition == localRobotPosition) {
            // robot on correct side: drive forward, turn, and score

            if (switchPosition == 'L') {
                sideCommand = new AutoForwardTurnScore(90);
                SmartDashboard.putString("auto", "L");
            } else if (switchPosition == 'R') {
                sideCommand = new AutoForwardTurnScore(270);
                SmartDashboard.putString("auto", "R");
            }
        } else {
            // robot on incorrect side: drive straight, come back
            sideCommand = new Autonomous_DriveStraight();
            SmartDashboard.putString("auto", "drive straight");
        }
        return sideCommand;
    }

    private static Command CommandForRobotInCenter(char switchPosition) {
        Command centeredRobotCommand = null;
        // robot in center
        if (switchPosition == 'L') {
            centeredRobotCommand = new AutoCenterOneCube(315);
            SmartDashboard.putString("auto", "center two cube left");

        } else if (switchPosition == 'R') {
            centeredRobotCommand = new AutoCenterOneCube(45);
            SmartDashboard.putString("auto", "center two cube right");

        } else {
            centeredRobotCommand = new Autonomous_DriveStraightAndBack();
            SmartDashboard.putString("auto", "drive straight");
        }
        return centeredRobotCommand;
    }



    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        log();

//        if (!autoStarted) {
//            autonomousInit();
//        }
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        Scheduler.getInstance().add(new DriveStandard());

        double encoderDistanceLeft = CommandBase.drive.getEncoderLeftDist();
        double encoderDistanceRight = CommandBase.drive.getEncoderRightDist();
        SmartDashboard.putNumber("Left Encoder auto distance", encoderDistanceLeft);
        SmartDashboard.putNumber("Right encoder auto distance", encoderDistanceRight);
        SmartDashboard.putNumber("teleop start gyro", CommandBase.gyro.getGyroAngle());

    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
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

    @Override
    public void disabledInit() {
        System.out.println(autoGyroLog);
        autoGyroLog = new ArrayList();
        autoStarted = false;
        CommandBase.gyro.gyro.reset();
    }


    public void log() {
        autoGyroLog.add(CommandBase.gyro.getGyroAngle());

        double encoderDistanceLeft = CommandBase.drive.getEncoderLeftDist();
        double encoderDistanceRight = CommandBase.drive.getEncoderRightDist();

        SmartDashboard.putNumber("driveEncoderLeftRate", CommandBase.drive.getEncoderLeftRate());
        SmartDashboard.putNumber("driveEncoderLeftDist", encoderDistanceLeft);
        SmartDashboard.putNumber("driveEncoderRightRate", CommandBase.drive.getEncoderRightRate());
        SmartDashboard.putNumber("driveEncoderRightDist", encoderDistanceRight);

//		SmartDashboard.putNumber("FRD current draw", CommandBase.drive.frontRightDrive.getOutputCurrent());
//		SmartDashboard.putNumber("BRD current draw", CommandBase.drive.backRightDrive.getOutputCurrent());
//		SmartDashboard.putNumber("FLD current draw", CommandBase.drive.frontLeftDrive.getOutputCurrent());
//		SmartDashboard.putNumber("BLD current draw", CommandBase.drive.backLeftDrive.getOutputCurrent());


        SmartDashboard.putNumber("right DriveEnc pulse setting", CommandBase.drive.getEncoderRightDistancePerPulse());
        SmartDashboard.putNumber("left DriveEnc pulse setting", CommandBase.drive.getEncoderLeftDistancePerPulse());


        SmartDashboard.putNumber("liftEncoderVel", CommandBase.lift.getEncoderVelocity());
        SmartDashboard.putNumber("liftEncoderPos", CommandBase.lift.getEncoderPosition());

        SmartDashboard.putNumber("gyro getYaw", CommandBase.gyro.gyro.getYaw());
        SmartDashboard.putNumber("gyro getAngle", CommandBase.gyro.gyro.getAngle());
        SmartDashboard.putNumber("gyroAngleZ", CommandBase.gyro.gyro.getAngleZ());
//        SmartDashboard.putNumber("gyro getAngle function", CommandBase.gyro.gyro.getAngle());
        SmartDashboard.putNumber("Gyro my getGyroAngle", CommandBase.gyro.getGyroAngle());

        SmartDashboard.putBoolean("lift upper limit", CommandBase.lift.getUpperLimit());
        SmartDashboard.putBoolean("lift lower limit", CommandBase.lift.getLowerLimit());

        SmartDashboard.putNumber("window potentiometer", CommandBase.windowMotor.getPot());
        SmartDashboard.putBoolean("window limit", CommandBase.windowMotor.getLimit());

        SmartDashboard.putBoolean("auto has been run?", autoStarted);
//        SmartDashboard.putNumber("brock Gyro Angle", CommandBase.gyro.adjustedGyroAngle());
    }
}
