package org.usfirst.frc.team3880.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;

import org.usfirst.frc.team3880.robot.subsystems.Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Drive extends Subsystem {

	public TalonSRX frontRightDrive;
	public TalonSRX centerRightDrive;	
	public TalonSRX backRightDrive;
	public TalonSRX frontLeftDrive;
	public TalonSRX centerLeftDrive;
	public TalonSRX backLeftDrive;
	
	private double encoder_left_factor = 1;
	private double encoder_right_factor = 1;

	double joystickSensitivity = 0.85;

	Encoder driveEncoderLeft;
	Encoder driveEncoderRight;

	RobotMap hardware;

	public static Drive instance;

    public static Drive getInstance() {

	   if (instance == null) {
		   instance = new Drive();
	   }

	   return instance;
    }


	private Drive() {
		super("DriveWheels");

        hardware = new RobotMap();

        try {
        	// TODO: add encoders to robotmap
        	driveEncoderLeft = new Encoder(hardware.DRIVE_ENCODER_LEFT_ONE,
        								   hardware.DRIVE_ENCODER_LEFT_TWO,
        								   false, Encoder.EncodingType.k4X);
        	driveEncoderRight = new Encoder(hardware.DRIVE_ENCODER_RIGHT_ONE,
        									hardware.DRIVE_ENCODER_RIGHT_TWO,
        									false, Encoder.EncodingType.k4X);

    		driveEncoderLeft.setDistancePerPulse(0.0092);
    		driveEncoderRight.setDistancePerPulse(0.0092);
    		
    		frontRightDrive = new TalonSRX(hardware.FRONT_RIGHT_DRIVE_TALON);
    		centerRightDrive = new TalonSRX(hardware.CENTER_RIGHT_DRIVE_TALON);
			backRightDrive = new TalonSRX(hardware.BACK_RIGHT_DRIVE_TALON);
			frontLeftDrive = new TalonSRX(hardware.FRONT_LEFT_DRIVE_TALON);
    		centerLeftDrive = new TalonSRX(hardware.CENTER_LEFT_DRIVE_TALON);
			backLeftDrive = new TalonSRX(hardware.BACK_LEFT_DRIVE_TALON);
			

    		setCurrentLimit(frontRightDrive, 40);
    		setCurrentLimit(centerRightDrive, 30);
    		setCurrentLimit(backRightDrive, 40);
    		setCurrentLimit(frontLeftDrive, 40);
    		setCurrentLimit(centerLeftDrive, 30);
    		setCurrentLimit(backLeftDrive, 40);

    		frontRightDrive.configOpenloopRamp(.1, 10);
    		centerRightDrive.configOpenloopRamp(.1, 10);
      		backRightDrive.configOpenloopRamp(.1, 10);
      		frontLeftDrive.configOpenloopRamp(.1, 10);
    		centerLeftDrive.configOpenloopRamp(.1, 10);
      		backLeftDrive.configOpenloopRamp(.1, 10);



        }

        catch (Exception e) {
            System.out.println(e);
            System.out.println("Drive failed");
        }

	}
	public void setCurrentLimit(TalonSRX talon, int ampLimit) {
		talon.configPeakCurrentLimit(0, 0);
		talon.configPeakCurrentDuration(0, 0);//immediately limit
		talon.configContinuousCurrentLimit(ampLimit, 10); //10 ms timeout
		talon.enableCurrentLimit(true);
	}
	public void drive(double left, double right) {

		left = (joystickSensitivity * Math.pow(left, 3)) + ((1 - joystickSensitivity) * left);
		right = (joystickSensitivity * Math.pow(right, 3)) + ((1 - joystickSensitivity) * right);
		
		double leftSide = left - right;
		double rightSide = left + right;
		
		if(CommandBase.OI_MODE == 1 || CommandBase.OI_MODE == 3 || CommandBase.OI_MODE == 4) {
			backLeftDrive.set(ControlMode.PercentOutput, -leftSide);
			centerLeftDrive.set(ControlMode.PercentOutput, leftSide);
			frontLeftDrive.set(ControlMode.PercentOutput, -leftSide);
			
			backRightDrive.set(ControlMode.PercentOutput, rightSide);
			centerRightDrive.set(ControlMode.PercentOutput, -rightSide);
			frontRightDrive.set(ControlMode.PercentOutput, -rightSide);
		}
    }

	public void set(double left, double right) {

			backLeftDrive.set(ControlMode.PercentOutput, -left);
			centerLeftDrive.set(ControlMode.PercentOutput, left);
			frontLeftDrive.set(ControlMode.PercentOutput, -left);
			backRightDrive.set(ControlMode.PercentOutput, right);
			centerRightDrive.set(ControlMode.PercentOutput, -right);
			frontRightDrive.set(ControlMode.PercentOutput, -right);

    }

	public double getEncoderLeftRate() {
		return driveEncoderLeft.getRate();
	}

	public double getEncoderLeftDist() {
		return driveEncoderLeft.getDistance() * encoder_left_factor;
	}

	public double getEncoderRightRate() {
		return driveEncoderRight.getRate();
	}

	public double getEncoderRightDist() {
		return  - (driveEncoderRight.getDistance() * encoder_right_factor);
	}

	public double getEncoderLeftDistancePerPulse() { return driveEncoderLeft.getDistancePerPulse(); }
	public void setEncoderLeftDistancePerPulse(double d) { driveEncoderLeft.setDistancePerPulse(d); }
	public double getEncoderRightDistancePerPulse() { return driveEncoderRight.getDistancePerPulse(); }
	public void setEncoderRighttDistancePerPulse(double d) { driveEncoderRight.setDistancePerPulse(d); }


	public boolean setHeading(double angle, double percent) {
        double left;
        double right;

        double globalGyroDeadzone = SmartDashboard.getNumber("globalGyroDeadzone", 5.0);

		double currentAngle = CommandBase.gyro.getGyroAngle();
        System.out.println("Target angle " + angle + ", current angle " + currentAngle);

		angle %= 360; if (angle < 0) { angle += 360; }
        double angleOffset = (angle - currentAngle) % 180;

        if (Math.abs(angleOffset) < globalGyroDeadzone) {
            // drive straight
            left = 1;
			right = 1;
			
			return true;
        }
        else {
            if (angleOffset > 0) {
                // fastest turn is clockwise
                left = 1;
    			right = 1-(Math.abs(angleOffset)/90);
            }
            else /* if (angleOffset < 180) */ {
                // fastest turn is anticlockwise
    			left = 1-(Math.abs(angleOffset)/90);
                right = 1;
            }
        }

		double finalLeft = left*percent;
		double finalRight = right*percent;

		System.out.println(finalLeft);
		System.out.println(finalRight);

		set(finalLeft, finalRight);
		
		return false;

	}
	
	public boolean turnDegrees(double targetAngle, double drivePercent) {
//		double driveValue = drivePercent * ((targetAngle - CommandBase.gyro.getGyroAngle()) /targetAngle);
		boolean left = false;
		
		if (targetAngle > 180) {
			targetAngle = targetAngle-360;
			left = true;
		}
		
		targetAngle += CommandBase.gyro.getGyroAngle();
		
		if(!CommandBase.gyro.withinDeadZone(targetAngle)) {
			if(left) {
				set(drivePercent, -drivePercent);
			}
			else {
				set(-drivePercent, drivePercent);
			}
			return false;

		}
		
		return true;
	}
	
	public boolean driveDistance(int distance, double driveValue) {
		
		if(getEncoderRightDist() < distance || getEncoderLeftDist() < distance) {
			setHeading(0, driveValue);
			return false;
		}
		else {
			set(0, 0);
			resetEncoders();
			return true;
		}
	}

	public void resetEncoders() {
		driveEncoderLeft.reset();
		driveEncoderRight.reset();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DriveStandard());
	}

}
