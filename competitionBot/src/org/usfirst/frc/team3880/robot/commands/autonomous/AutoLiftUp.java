package org.usfirst.frc.team3880.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3880.robot.commands.CommandBase;

public class AutoLiftUp extends CommandBase  {
	Timer timer;

	// Times, in seconds, when the phase changes. These values must be strictly increasing
	private double stepDownEndTime;
	private double step0EndTime;
	private double step1EndTime;
	private double step2EndTime;

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

	public AutoLiftUp() {
		requires(drive);
		requires(lift);
		requires(gyro);
		requires(leftIntakeWheel);
		requires(rightIntakeWheel);
		requires(intakeArms);
	}

	@Override
	protected void initialize() {
		timer = new Timer();
		timer.reset();
		timer.start();

		/* Initialize all magic numbers, preferably by reading SmartDashboard,
		but with reasonable defaults
		*/
		stepDownEndTime = 1.0;
		step0EndTime = SmartDashboard.getNumber("autoFTR0FinalTime", 4.5);
		step1EndTime = SmartDashboard.getNumber("autoFTR1FinalTime", 5.5);
		step2EndTime = SmartDashboard.getNumber("autoFTR2FinalTime", 6.25);


		step0LeftPct = SmartDashboard.getNumber("autoFTR0LeftPct", -0.4);
		step0RighttPct = SmartDashboard.getNumber("autoFTR0RightPct", -0.4);

		// Motor duty-cycle for spin: left is set to `step1DrivePct` right to `-1 * step1DrivePct`
		step1DrivePct = SmartDashboard.getNumber("autoFTR1DrivePct", 0.4);
		step1ClockwiseAngle = SmartDashboard.getNumber("autoFTR1ClockwiseAngle", 90.0);
		step1AngularDeadzone = SmartDashboard.getNumber("autoFTR1AngularDeadzone", 10.0);
		step1LiftPct = SmartDashboard.getNumber("autoFTR1RLiftPct", 1);

		step2LeftPct = SmartDashboard.getNumber("autoFTR2LeftPct", -0.4);
		step2RightPct = SmartDashboard.getNumber("autoFTR2RightPct", -0.4);

		// Read the gyro.
		initialRobotAngle = gyro.getGyroAngle();
		desiredRobotAngle = (initialRobotAngle + step1ClockwiseAngle) % 360;
	}

	/* Returns the phase for the given time (in seconds). Note that phases start at 0. */
	private int phaseFor(double t) {
		if (t < stepDownEndTime) {
			return -1;
		}
		if (t < step0EndTime)
		{
			return 0;
		}
		if (t < step1EndTime)
		{
			return 1;
		}
		if (t < step2EndTime)
		{
			return 2;
		}
		return 3;
	}

	/*
	Phase 0 behavior: drive forward
	*/
	private void WindowDown(double time) {
		windowMotor.set(-0.5);
	}
	private void ForwardInitial(double time)
	{
//		drive.setHeading(0, step0RighttPct);
		drive.set(step0LeftPct, step0RighttPct);
		windowMotor.set(0);

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
	private void Rotate(double time)
	{
		lift.set(step1LiftPct);

//        if (!gyro.withinDeadZone(desiredRobotAngle)){
//            drive.set(step1DrivePct, -step1DrivePct);
//        }
	}

	/* Phase 2 behavior: Drive forward, spin intake wheels */
	private void ForwardAgain(double time)
	{
		lift.set(0);

//		drive.setHeading(desiredRobotAngle, step2RightPct);
		drive.set(0, 0);
		rightIntakeWheel.spin(-1);
		leftIntakeWheel.spin(-1);
	}

	/* Phase 3 behavior : Shut down motors */
	private void Stop()
	{
		drive.set(0.0, 0.0);
		rightIntakeWheel.spin(0);
		leftIntakeWheel.spin(0);
	}


	@Override
	protected void execute() {
		double time = timer.get();
		int phase = phaseFor(time);
		// Call the behavior appropriate for the phase
		switch (phase) {
			case -1:
				WindowDown(time);
				break;

			case 0:
				ForwardInitial(time);
				break;
			case 1:
				Rotate(time);
				break;
			case 2:
				ForwardAgain(time);
				break;
			case 3:
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
