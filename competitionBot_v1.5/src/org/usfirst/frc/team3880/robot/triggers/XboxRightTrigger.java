package org.usfirst.frc.team3880.robot.triggers;

import org.usfirst.frc.team3880.robot.OI;

import edu.wpi.first.wpilibj.buttons.Trigger;

public class XboxRightTrigger extends Trigger {

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return (OI.getXboxRightTrigger() > 0.5);
	}

}
