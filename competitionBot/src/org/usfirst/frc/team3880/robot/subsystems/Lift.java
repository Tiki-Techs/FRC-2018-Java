package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.OI;
import org.usfirst.frc.team3880.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends Subsystem {

	TalonSRX lift;

	DigitalInput liftUpperLimit;
	DigitalInput liftLowerLimit;

	RobotMap hardware;

	public static Lift instance;

    public static Lift getInstance() {

	   if (instance == null) {
		   instance = new Lift();
	   }

	   return instance;
    }

	private Lift() {
		hardware = new RobotMap();

		try {
			lift = new TalonSRX(hardware.LIFT_TALON);

			lift.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); /* PIDLoop=0, timeoutMs=0 */
			liftUpperLimit = new DigitalInput(hardware.UPPER_LIFT_LIMIT);
			liftLowerLimit = new DigitalInput(hardware.LOWER_LIFT_LIMIT);
		}

		catch (Exception e) {
            System.out.println(e);
            System.out.println("Lift init failed");
        }
	}

	public void set(double speed) {
		if(speed == 0) {
			lift.set(ControlMode.PercentOutput, 0.05);//stalls motor to keep up
		}
		else {
			lift.set(ControlMode.PercentOutput, speed);
		}

	}

	public boolean getUpperLimit() {
		return !liftUpperLimit.get();
	}

	public boolean getLowerLimit() {
		return !liftLowerLimit.get();
	}

	public void setPos(double target) {
		//target is position of encoder
		double differential = target - lift.getSelectedSensorPosition(0);
		
		if (Math.abs(differential) > SmartDashboard.getNumber("liftEncoderDeadZone", 10)) {
			set(1*Math.signum(differential));
		}
	}

	public void resetEncoder() {
		lift.setSelectedSensorPosition(0, 0, 0);
	}

	public int getEncoderPosition() {
		return -lift.getSelectedSensorPosition(0);
	}

	public int getEncoderVelocity() {
		return -lift.getSelectedSensorVelocity(0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
