/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3880.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.buttons.Button;
import org.usfirst.frc.team3880.robot.commands.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public static OI oi;
	Command tog;

	
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	TalonSRX frontRightDrive = new TalonSRX(1);
	TalonSRX middleRightDrive = new TalonSRX(2);
	TalonSRX backRightDrive = new TalonSRX(3);
	Stream<TalonSRX> rightWheels = Arrays.stream(new TalonSRX[] { frontRightDrive, middleRightDrive, backRightDrive });


	TalonSRX frontLeftDrive = new TalonSRX(4);
	TalonSRX middleLeftDrive = new TalonSRX(5);
	TalonSRX backLeftDrive = new TalonSRX(6);
    Stream<TalonSRX> leftWheels = Arrays.stream(new TalonSRX[] { frontLeftDrive, middleLeftDrive, backLeftDrive });

    DoubleSolenoid shift = new DoubleSolenoid(7, 0, 1);
	public static DoubleSolenoid gearIntake = new DoubleSolenoid(7, 4, 5);
	DoubleSolenoid passiveGear = new DoubleSolenoid(7, 2, 3);
	// 7 is PCM ID number
	NetworkTable table;
	public static boolean turbo = false;
	Compressor c = new Compressor(7);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		tog = new Toggle();

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
     * Gets the X & Y values of the joystick
     * @param joy The joystick to read
     * @return An array whose first element is the X value of the joystick and whose second element is the Y value
     */
	protected double[] getJoystickDrive(Joystick joy)
    {
        return new double[] {joy.getX(), joy.getY() };
    }

    /**
     * Sets the duty-cycle % of the `rightWheels` to `drive` and that of the `leftWheels` to `-drive`
     * @param drive Must be in the range [0..1]
     */
    protected void setDriveForward(double drive)
    {
        rightWheels.forEach(d -> d.set(ControlMode.PercentOutput, drive));
        leftWheels.forEach(d -> d.set(ControlMode.PercentOutput, -drive));
    }

    /**
     * Returns the desired driving direction. Note that if both `forwardToggle` and `backwardToggle` are `true`,
     * `backwardToggle` "wins" because it is evaluated second
     * @param forwardToggle
     * @param backwardToggle
     * @param currentState
     * @return
     */
    protected DoubleSolenoid.Value getDirection(boolean forwardToggle, boolean backwardToggle, DoubleSolenoid.Value currentState)
    {
        if (forwardToggle && currentState.equals(DoubleSolenoid.Value.kReverse))
        {
            return DoubleSolenoid.Value.kForward;
        }

        if (backwardToggle && currentState.equals(DoubleSolenoid.Value.kForward))
        {
            return  DoubleSolenoid.Value.kReverse;
        }

        // No request has been made or the request is for the current direction
        return currentState;
    }

    /**
     * Modifies the state of the `solenoid` if `toggleRequested` is true
     * @param solenoid
     * @param toggleRequested
     * @return
     */
    protected DoubleSolenoid.Value toggleGearIntake(DoubleSolenoid solenoid, boolean toggleRequested)
    {
        DoubleSolenoid.Value currentDirection = solenoid.get();
        if (toggleRequested)
        {
            DoubleSolenoid.Value newDirection = currentDirection == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward;
            solenoid.set(newDirection);
            return newDirection;
        }
        else
        {
            return currentDirection;
        }
    }

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	    teleopPeriodicJoystick();
        teleopPeriodicForwardBack();
        teleopPeriodicGearIntake();
    }

    private void teleopPeriodicGearIntake() {
        // Gear intake
        boolean toggleGearIntakeRequested = oi.joy1.getRawButtonPressed(5);
        DoubleSolenoid.Value newToggleGearDirection = toggleGearIntake(passiveGear, toggleGearIntakeRequested);
    }

    private void teleopPeriodicForwardBack() {
        // Forward-back
        DoubleSolenoid.Value currentDirection = shift.get();
        DoubleSolenoid.Value requestedDirection = getDirection(oi.button3.get(), oi.button4.get(), currentDirection);

        if (requestedDirection.equals(currentDirection) == false)
        {
            shift.set(requestedDirection);
        }
    }

    private void teleopPeriodicJoystick() {
        // Joystick direction
        double[] joystickInput = getJoystickDrive(oi.joy1);
        double joystickDrive = joystickInput[1] - joystickInput[0];
        setDriveForward(joystickDrive);
    }

    /**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
