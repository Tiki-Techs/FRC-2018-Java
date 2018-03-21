package org.usfirst.frc.team3880.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3880.robot.commands.CommandBase;

public class AutoDriveForwardTurnRight extends CommandBase {
	Timer timer;

	double DRIVE_VALUE = 0.1;

	public AutoDriveForwardTurnRight() {
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
	}


	@Override
	protected void execute() {
		System.out.println(timer.get());
		double step0FinalTime = SmartDashboard.getNumber("autoFTR0FinalTime", 4.5);
		double step0LeftPct = SmartDashboard.getNumber("autoFTR0LeftPct", -0.4);
		double step0RighttPct = SmartDashboard.getNumber("autoFTR0RightPct", -0.4);

		double step1FinalTime = SmartDashboard.getNumber("autoFTR1FinalTime", 5.5);
		double step1LeftPct = SmartDashboard.getNumber("autoFTR1LeftPct", -0.4);
		double step1RighttPct = SmartDashboard.getNumber("autoFTR1RightPct", 0.4);
		double step1LiftPct = SmartDashboard.getNumber("autoFTR1RLiftPct", 0.7);

		double step2FinalTime = SmartDashboard.getNumber("autoFTR2FinalTime", 6);
		double step2LeftPct = SmartDashboard.getNumber("autoFTR2LeftPct", -0.4);
		double step2RighttPct = SmartDashboard.getNumber("autoFTR2RightPct", -0.4);

		if (timer.get() < 0.5) {
			windowMotor.set(-1);
		} else if (timer.get() < step0FinalTime) {
			drive.set(step0LeftPct, step0RighttPct);
		} else if (timer.get() < step1FinalTime) {
			drive.set(step1LeftPct, step1RighttPct);
			lift.set(step1LiftPct);
		} else if (timer.get() < step2FinalTime) {
			drive.set(step2LeftPct, step2RighttPct);
		} else if (timer.get() < 6.5) {
			rightIntakeWheel.spin(-1);
			leftIntakeWheel.spin(1);
		}

	}

	@Override
	protected void end() {
		drive.backLeftDrive.set(ControlMode.PercentOutput, 0);
		drive.frontLeftDrive.set(ControlMode.PercentOutput, 0);
		drive.backRightDrive.set(ControlMode.PercentOutput, 0);
		drive.frontRightDrive.set(ControlMode.PercentOutput, 0);
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
