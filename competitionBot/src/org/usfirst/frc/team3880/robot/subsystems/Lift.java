package org.usfirst.frc.team3880.robot.subsystems;

import org.usfirst.frc.team3880.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {

	TalonSRX lift;
	
	DigitalInput liftLowerLimit;
	DigitalInput liftUpperLimit;
	
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

			lift.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0); /* PIDLoop=0, timeoutMs=0 */
			liftLowerLimit = new DigitalInput(hardware.LOWER_LIFT_LIMIT);
			liftUpperLimit = new DigitalInput(hardware.UPPER_LIFT_LIMIT);
			
		}
		
		catch (Exception e) {
            System.out.println(e);
            System.out.println("Intake failed");
        }
	}
	
	public void set(double speed) {
		lift.set(ControlMode.PercentOutput, speed);
	}
	
	public boolean getUpperLimit() {
		return liftUpperLimit.get();
	}
	
	
//
	public double getEncoderPosition() {
		return -lift.getSelectedSensorPosition(0);
	}
	
	public double getEncoderVelocity() {
		return -lift.getSelectedSensorVelocity(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
