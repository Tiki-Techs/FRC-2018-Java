package org.usfirst.frc.team3880.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
    
    int angleOne;
    int angleTwo;
    
    int phase;

	public AutoCenterTwoCube(int turnOne, int turnTwo) {
		requires(drive);
		requires(lift);
		requires(gyro);
		requires(leftIntakeWheel);
		requires(rightIntakeWheel);
		requires(intakeArms);
		
		angleOne = turnOne;
		angleTwo = turnTwo;
	}

	@Override
	protected void initialize() {

        // get constants from SmartDashboard
        step0FinalTime = SmartDashboard.getNumber("autoFTR0FinalTime", 4.5);
		step0LeftPct = SmartDashboard.getNumber("autoFTR0LeftPct", -0.4);
		step0RighttPct = SmartDashboard.getNumber("autoFTR0RightPct", -0.4);

		step1FinalTime = SmartDashboard.getNumber("autoFTR1FinalTime", 5.5);
		step1LeftPct = SmartDashboard.getNumber("autoFTR1LeftPct", -0.4);
		step1RighttPct = SmartDashboard.getNumber("autoFTR1RightPct", 0.4);
		step1LiftPct = SmartDashboard.getNumber("autoFTR1RLiftPct", 0.7);

		step2FinalTime = SmartDashboard.getNumber("autoFTR2FinalTime", 6);
		step2LeftPct = SmartDashboard.getNumber("autoFTR2LeftPct", -0.4);
		step2RighttPct = SmartDashboard.getNumber("autoFTR2RightPct", -0.4);


		timer = new Timer();
		timer.reset();
		timer.start();

		phase = 0;
	}
	
	private boolean ForwardOne(double time) {
		return drive.driveDistance(24, 1);
	}
	private boolean RotateOne(double time) {
		return drive.turnDegrees(angleOne);
	}
	private boolean ForwardTwo(double time) {
		return drive.driveDistance(80, 1);
	}
	private boolean RotateTwo(double time) {
		return drive.turnDegrees(angleTwo);
	}
	private boolean ForwardThree(double time) {
		windowMotor.setDegree(0);
		lift.set(.5);
		return drive.driveDistance(62, .5);

	}
	private boolean Score(double time) {
		rightIntakeWheel.spin(-1);
		leftIntakeWheel.spin(-1);
		drive.set(0, 0);
		lift.set(0);
		windowMotor.set(0);
		return time > .5;
	}
	
	@Override
	protected void execute() {
		System.out.println(timer.get());
		double time = timer.get();
		
//		int phase = phaseFor(time);
		// Call the behavior appropriate for the phase
		switch (phase) {
			case 0:
				if(ForwardOne(time)) {phase ++;}
				break;
			case 1:
				if(RotateOne(time)) {phase++;}
				break;
			case 2:
				if(ForwardTwo(time)) {phase++;}
				break;
			case 3:
				if(RotateTwo(time)) {phase++;}
				break;
			case 4:
				if(ForwardThree(time)) {phase++;}
				break;
			case 5:
				if(Score(time)) {
					phase++; 
					timer.reset();
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

        rightIntakeWheel.spin(0);
        leftIntakeWheel.spin(0);

        lift.set(0);
        
        windowMotor.set(0);
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
