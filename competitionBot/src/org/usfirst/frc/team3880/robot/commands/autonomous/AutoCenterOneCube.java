package org.usfirst.frc.team3880.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3880.robot.commands.CommandBase;

public class AutoCenterOneCube extends CommandBase {
	Timer timer;
	
	int phase;
	boolean left;
	// Times, in seconds, when the phase changes. These values must be strictly increasing
	private double stepDownEndTime;
	private double step0EndCondition;
	
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

	public AutoCenterOneCube(double targetAngle) {
		requires(drive);
		requires(lift);
		requires(gyro);
		requires(leftIntakeWheel);
		requires(rightIntakeWheel);
		requires(intakeArms);
		
		step1ClockwiseAngle = targetAngle;
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
		step1DrivePct = SmartDashboard.getNumber("autoFTR1DrivePct", -0.4);
//		step1ClockwiseAngle = SmartDashboard.getNumber("autoFTR1ClockwiseAngle", 270.0);
		step1AngularDeadzone = SmartDashboard.getNumber("autoFTR1AngularDeadzone", 10.0);
		step1LiftPct = SmartDashboard.getNumber("autoFTR1RLiftPct", 0.8);

		step2LeftPct = SmartDashboard.getNumber("autoFTR2LeftPct", -0.4);
		step2RightPct = SmartDashboard.getNumber("autoFTR2RightPct", -0.4);

		// Read the gyro.
		initialRobotAngle = gyro.getGyroAngle();
		SmartDashboard.putNumber("auto start gyro", CommandBase.gyro.getGyroAngle());
		if (step1ClockwiseAngle < 180) {
			desiredRobotAngle = initialRobotAngle + step1ClockwiseAngle;
			left = false;
		}
		else {
			desiredRobotAngle = initialRobotAngle + (step1ClockwiseAngle-360);
			left = true;
		}
		System.out.println("initial angle: "+ initialRobotAngle + ", desired angle: " + desiredRobotAngle);
//		desiredRobotAngle = (initialRobotAngle + step1ClockwiseAngle) % 360;
	}


	/*
	Phase 0 behavior: drive forward
	*/
	private boolean WindowDown(double time) {
		windowMotor.set(-0.5);
		
		return time > 1;
	}
	
	private boolean Rotate(double time)
	{

        if (!gyro.withinDeadZone(desiredRobotAngle)){
            if (left) {
            	drive.set(-step1DrivePct, step1DrivePct);
            }
            else {
            	drive.set(step1DrivePct, -step1DrivePct);
            }
            return false;
        }
        else {
        	drive.set(0, 0);
        }
        
    	return true;
        
	}
	
	private boolean ForwardInitial(double time)
	{
		drive.setHeading(desiredRobotAngle, step0RighttPct);
		windowMotor.set(0);
		
//		return leftDistance > step1EndDistance && rightDistance > step1EndDistance;
		return time > 1.5;
	}
	
	private boolean ForwardTwo(double time)
	{
		drive.set(step0LeftPct, step0RighttPct);
		windowMotor.set(0);
		
//		return leftDistance > step2EndDistance && rightDistance > step2EndDistance;
		return time > 2;

	}

	
	private boolean RotateBack(double time)
	{

        if (!gyro.withinDeadZone(0)){
            if (left) {
            	drive.set(step1DrivePct, -step1DrivePct);
            }
            else {
            	drive.set(-step1DrivePct, step1DrivePct);
            }
            return false;
        }
        else {
        	drive.set(0, 0);
        }
        
    	return true;
        
	}
	
	private boolean LiftUp(double time) {
		lift.set(step1LiftPct);

		if (time > 1.5) {
			lift.set(0);
		}
		else {
			return false;
		}
		
		return true;
	}

	/* Phase 2 behavior: Drive forward, spin intake wheels */
	private boolean ForwardAgain(double time)
	{
		drive.set(step0LeftPct, step0RighttPct);
		windowMotor.set(0);
		double rightDistance = drive.getEncoderRightDist();
		double leftDistance = drive.getEncoderLeftDist();
		
//		return leftDistance > step2EndDistance && rightDistance > step2EndDistance;
		return time > 2;

	}
	
	private boolean Score(double time) {
		drive.set(0, 0);

		rightIntakeWheel.spin(-1);
		leftIntakeWheel.spin(-1);
		
		return time > 1;
	}
	
	/* Phase 6 behavior: Drive backwards */
	private boolean BackUp(double time)
	{
		drive.set(-step0LeftPct, -step0RighttPct);
		windowMotor.set(0);

		return time > 1.75;

	}
	
	private void Stop() {
		drive.set(0,0);

		rightIntakeWheel.spin(0);
		leftIntakeWheel.spin(0);

		lift.set(0);

		windowMotor.set(0);
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
			if (ForwardTwo(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
				drive.resetEncoders();
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
			if (RotateBack(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
				drive.resetEncoders();
			}
			break;
		case 6:
			if (LiftUp(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
			}
			break;
		case 7:
			if (ForwardAgain(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
				drive.resetEncoders();
			}
			break;
		case 8:
			if (Score(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
			}
			break;
			
		case 9:
			if (BackUp(time)) {
				phase++;
				System.out.println("Phase changed to " + phase);
				timer.reset();
				timer.start();
			}
			break;
		
		case 10:
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
