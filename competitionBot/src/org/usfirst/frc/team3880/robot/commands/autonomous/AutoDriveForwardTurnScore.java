package org.usfirst.frc.team3880.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3880.robot.commands.CommandBase;

public class AutoDriveForwardTurnScore extends CommandBase {
	Timer timer;
	
	int phase;

	// Times, in seconds, when the phase changes. These values must be strictly increasing
	private double stepDownEndTime;
	private double step0EndCondition;
	
	double turnDirection;
	
	private double step1EndDistance;
	private double step2EndDistance;
	private double step3EndCondition;

	// Duty % for the drives in the various phases. Note that `step1DrivePct`
    // is single-value so that robot spins in place
	private double step0LeftPct;
	private double step0RighttPct;
	private double step1DrivePct;
	private double step2LeftPct;
	private double step2RightPct;

	// Sets the lift height (?) in step 1
	private double step1LiftPct;

	// Angle, in degrees, of desired clockwise turn
	private double step1ClockwiseAngle;
	// Stop turning if +- deadzone from desiredRobotAngle
	private double step1AngularDeadzone;

	// Read during initialization
	private double initialRobotAngle;
	// == `(initialRobotAngle + step1ClockwisAngle) modulo 360` (always in range[0 .. 360])
	private double desiredRobotAngle;

	public AutoDriveForwardTurnScore(double targetAngle) {
		requires(drive);
		requires(lift);
		requires(gyro);
		requires(leftIntakeWheel);
		requires(rightIntakeWheel);
		requires(intakeArms);
		
		step1ClockwiseAngle = targetAngle % 360;
	}

	@Override
	protected void initialize() {
		timer = new Timer();
		timer.reset();
		timer.start();
		
		phase = 0;

		/* Initialize all magic numbers, preferably by reading SmartDashboard,
		but with reasonable defaults
		*/
		stepDownEndTime = 1.0;
		step0EndCondition = SmartDashboard.getNumber("autoFTR0FinalTime", 4.5);
		step1EndDistance = SmartDashboard.getNumber("autoTurnScoreDistance", 100);
		step2EndDistance = SmartDashboard.getNumber("autoTurnScoreFinalDistance", 20);

		step0LeftPct = SmartDashboard.getNumber("autoFTR0LeftPct", -0.4);
		step0RighttPct = SmartDashboard.getNumber("autoFTR0RightPct", -0.4);

		// Motor duty-cycle for spin: left is set to `step1DrivePct` right to `-1 * step1DrivePct`
		step1DrivePct = SmartDashboard.getNumber("autoFTR1DrivePct", 0.4);
//		step1ClockwiseAngle = SmartDashboard.getNumber("autoFTR1ClockwiseAngle", 270.0);
		step1AngularDeadzone = SmartDashboard.getNumber("autoFTR1AngularDeadzone", 10.0);
		step1LiftPct = SmartDashboard.getNumber("autoFTR1RLiftPct", 0.7);

		step2LeftPct = SmartDashboard.getNumber("autoFTR2LeftPct", -0.4);
		step2RightPct = SmartDashboard.getNumber("autoFTR2RightPct", -0.4);

		// Read the gyro.
		if (step1ClockwiseAngle > 180) {
			turnDirection = -1; // left
		}
		else {
			turnDirection = 1; // right
		}
		initialRobotAngle = gyro.getGyroAngle();
		desiredRobotAngle = (initialRobotAngle + step1ClockwiseAngle) % 360;
	}

	/* Returns the phase for the given time (in seconds). Note that phases start at 0. */
//	private int phaseFor(double t) {
//		double left = drive.getEncoderLeftDist();
//		double right = drive.getEncoderRightDist();
//		if (t < stepDownEndTime) {
//			return -1;
//		}
//		if ((phase == -1 || phase == 0) && (left < step0EndCondition && right < step0EndCondition)) {
//			return 0;
//		}
//		else if ((phase == 0 || phase == 1) && (gyro.withinDeadZone(step1EndCondition))) {
//			return 1;
//		}
//		else if ((phase == 1 || phase == 2) && (left < step2EndCondition && right < step2EndCondition)) {
//			return 2;
//		}
//		else if ((phase == 2 || phase == 3)) {
//			return 3;
//		}
//		else {
//			return 4;
//		}
//	}

	/*
	Phase 0 behavior: drive forward
	*/
	private boolean WindowDown(double time) {
		windowMotor.set(-0.5);
		
		return time > 1;
	}
	private boolean ForwardInitial(double time)
	{
		drive.set(step0LeftPct, step0RighttPct);
		windowMotor.set(0);
		double rightDistance = drive.getEncoderRightDist();
		double leftDistance = drive.getEncoderLeftDist();
		
		return leftDistance > step1EndDistance && rightDistance > step1EndDistance;
	}

	/*

	Phase 1 behavior: raise lift, rotate to `desiredRobotAngle`

	I can imagine a couple of things being wrong with this:

	First, it assumes that the gyro angle never is negative: that it's always in [0 .. 360]
	(Note that `desiredRobotAngle` is mod 360, so it will always be in that range

	Second, I worry about how frequently this is called, versus how fast the angle (might)
	be changing. If `step1LeftPct` and `step2LeftPct` are too high, the robot could rotate
	so fast that this function never runs while the robot is in the `angularDeadZone`. Or,
	if they are too low, it could be that the duration of `step1FinalTime - step0FinalTime`
	is not enough time to perform the turn.

	Honestly, this _is_ what you want to use PID control for, but that adds a lot of complexity
	that I don't think we have time to deal with.
	 */
	
	private boolean LiftUp(double time) {
		lift.set(step1LiftPct);

		if (time > 1) {
			lift.set(0);
		}
		else {
			return false;
		}
		
		return true;
	}
	private boolean Rotate(double time)
	{

        if (!gyro.withinDeadZone(desiredRobotAngle)){
            drive.set(turnDirection * step1DrivePct, -turnDirection * step1DrivePct);
            return true;
        }
        else {
        	drive.set(0, 0);
        }
        
    	return false;
        
	}

	/* Phase 2 behavior: Drive forward, spin intake wheels */
	private boolean ForwardAgain(double time)
	{
		drive.set(step0LeftPct, step0RighttPct);
		windowMotor.set(0);
		double rightDistance = drive.getEncoderRightDist();
		double leftDistance = drive.getEncoderLeftDist();
		
		return leftDistance > step2EndDistance && rightDistance > step2EndDistance;

	}
	
	private boolean Score(double time) {
		drive.set(0, 0);

		rightIntakeWheel.spin(-1);
		leftIntakeWheel.spin(-1);
		
		return time > 1;
	}


	@Override
	protected void execute() {
		double time = timer.get();
		// Call the behavior appropriate for the phase
		System.out.println(timer.get());
 
		// int phase = phaseFor(time);
		// Call the behavior appropriate for the phase
		switch (phase) {
		case 0:
			if (WindowDown(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
			}
			break;
		case 1:
			if (ForwardInitial(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
				drive.resetEncoders();
			}
			break;
		case 2:
			if (Rotate(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
				drive.resetEncoders();
			}
			break;
		case 3:
			if (LiftUp(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
			}
			break;
		case 4:
			if (ForwardAgain(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
				drive.resetEncoders();
			}
			break;
		case 5:
			if (Score(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
			}
			break;
		
		case 6:
			end();
			break;
		}

	}

	@Override
	protected void end() {
		drive.backLeftDrive.set(ControlMode.PercentOutput, 0);
		drive.frontLeftDrive.set(ControlMode.PercentOutput, 0);
		drive.backRightDrive.set(ControlMode.PercentOutput, 0);
		drive.frontRightDrive.set(ControlMode.PercentOutput, 0);

        lift.set(0);

        rightIntakeWheel.spin(0);
		leftIntakeWheel.spin(0);

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
