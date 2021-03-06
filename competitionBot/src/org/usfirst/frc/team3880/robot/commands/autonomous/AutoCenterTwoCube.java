package org.usfirst.frc.team3880.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3880.robot.commands.CommandBase;

public class AutoCenterTwoCube extends CommandBase {
	Timer timer;

	double DRIVE_VALUE = 0.1;

	double step0FinalTime;
	double step0LeftPct;
	double step0RighttPct;

	double step1FinalTime;
	double step1LeftPct;
	double step1RighttPct;
	double step1LiftPct;

	double step2FinalTime;
	double step2LeftPct;
	double step2RighttPct;

	double angleOne;
	double angleTwo;
	double angleThree;
	double angleFour;
	double angleFive;

	int phase;

	public AutoCenterTwoCube(double turnOne, double turnTwo, double turnThree, double turnFour, double turnFive) {
		requires(drive);
		requires(lift);
		requires(gyro);
		requires(leftIntakeWheel);
		requires(rightIntakeWheel);
		requires(intakeArms);

		angleOne = turnOne;
		angleTwo = turnTwo;
		angleThree = turnThree;
		angleFour = turnFour;
		angleFive = turnFive;
	}

	@Override
	protected void initialize() {

		timer = new Timer();
		timer.reset();
		timer.start();

		phase = 0;
		
        CommandBase.pneumatics.Shift(DoubleSolenoid.Value.kReverse);

	}

	/* Phase 2 behavior: Drive forward, spin intake wheels */
	private boolean ForwardOne(double time)
	{
		drive.set(step0LeftPct, step0RighttPct);
		windowMotor.set(0);
		double rightDistance = drive.getEncoderRightDist();
		double leftDistance = drive.getEncoderLeftDist();
		
//		return leftDistance > step2EndDistance && rightDistance > step2EndDistance;
		return time > 1.75;

	}

	private boolean RotateOne(double time) {
		return drive.setHeading(angleOne, 0.5);// 45 degrees or 315
	}

	private boolean ForwardTwo(double time) {
		return drive.driveDistance(25, -0.5);
	}

	private boolean RotateTwo(double time) {
		return drive.turnDegrees(angleTwo, 0.5); // 0 degrees
	}

	private boolean ForwardThree(double time) {
		windowMotor.set(.3);
		lift.set(.5);
		
		return drive.driveDistance(62, -.5);

	}

	private boolean Score(double time) {
		rightIntakeWheel.spin(-1);
		leftIntakeWheel.spin(-1);
		
		drive.set(0, 0);
		
		lift.set(0);
		windowMotor.set(0);
		
		return time > .5;
	}

	private boolean BackwardOne(double time) {
		drive.set(.4, .4);
		
		rightIntakeWheel.spin(0);
		leftIntakeWheel.spin(0);
		
		return time > 0.2;
	}

	private boolean RotateThree(double time) {
		return drive.turnDegrees(angleThree, 0.5); // 90 degrees or 270
	}

	private boolean ForwardFour(double time) {
		rightIntakeWheel.spin(1);
		leftIntakeWheel.spin(1);
		
		return drive.driveDistance(48, -.5);
	}

	private boolean RotateFour(double time) {
		rightIntakeWheel.spin(0);
		leftIntakeWheel.spin(0);
		return drive.turnDegrees(angleFour, 0.5); //90 or 270
	}

	private boolean ForwardFive(double time) {
		return drive.driveDistance(48, -.5);
	}

	private boolean RotateFive(double time) {
		return drive.turnDegrees(angleFive, 0.5); // 0 degrees
	}
	
	private void Stop() {
		drive.backLeftDrive.set(ControlMode.PercentOutput, 0);
		drive.frontLeftDrive.set(ControlMode.PercentOutput, 0);
		drive.backRightDrive.set(ControlMode.PercentOutput, 0);
		drive.frontRightDrive.set(ControlMode.PercentOutput, 0);

		rightIntakeWheel.spin(0);
		leftIntakeWheel.spin(0);

		lift.set(0);

		windowMotor.set(0);
	}

	@Override
	protected void execute() {
		System.out.println(timer.get());
		double time = timer.get();

		// int phase = phaseFor(time);
		// Call the behavior appropriate for the phase
		switch (phase) {
		case 0:
			if (ForwardOne(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				drive.resetEncoders();

			}
			break;
		case 1:
			if (RotateOne(time)) {
				timer.reset();
				timer.start();
				drive.resetEncoders();

				phase++;
				System.out.println("Phase changed to " + phase);
			}
			break;
		case 2:
			if (ForwardTwo(time)) {
				timer.reset();
				timer.start();
				drive.resetEncoders();

				phase++;
				System.out.println("Phase changed to " + phase);
			}
			break;
		case 3:
			if (RotateTwo(time)) {
				timer.reset();
				timer.start();
				drive.resetEncoders();
				phase++;
				System.out.println("Phase changed to " + phase);
				
			}
			break;
		case 4:
			if (ForwardThree(time)) {
				timer.reset();
				timer.start();
				drive.resetEncoders();

				phase++;
				System.out.println("Phase changed to " + phase);
			}
			break;
		case 5:
			if (Score(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				drive.resetEncoders();

				timer.reset();
				timer.start();
			}
			break;
		case 6:
			if (BackwardOne(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				drive.resetEncoders();

				timer.reset();
				timer.start();
			}
			break;
		case 7:
			if (RotateThree(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
			}
			break;
		case 8:
			if (ForwardFour(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				drive.resetEncoders();

				timer.reset();
				timer.start();
			}
			break;
		case 9:
			if (RotateFour(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				drive.resetEncoders();

				timer.reset();
				timer.start();
			}
			break;
		case 10:
			if (ForwardFive(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				drive.resetEncoders();

				timer.reset();
				timer.start();
			}
			break;
		case 11:
			if (RotateFive(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				drive.resetEncoders();

				timer.reset();
				timer.start();
			}
			break;
		case 12:
			if (Score(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				drive.resetEncoders();

				timer.reset();
				timer.start();
			}
			break;
		case 13:
			Stop();
			break;
		}

	}

	@Override
	protected void end() {
		Stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
