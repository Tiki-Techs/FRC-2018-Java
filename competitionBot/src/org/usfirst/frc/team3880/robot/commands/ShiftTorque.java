package org.usfirst.frc.team3880.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ShiftTorque extends CommandBase {

	boolean shifted = false;
	
    public ShiftTorque() {
    	requires(pneumatics);
    }

    protected void initialize() {
    	
    }

    /*
     * execute() - In our execute method we call a tankDrive method we have created in our subsystem. This method takes two speeds as a parameter which we get from methods in the OI class.
     * These methods abstract the joystick objects so that if we want to change how we get the speed later we can do so without modifying our commands
     * (for example, if we want the joysticks to be less sensitive, we can multiply them by .5 in the getLeftSpeed method and leave our command the same).
     */
    protected void execute() {
    	pneumatics.Shift(DoubleSolenoid.Value.kReverse);
    	shifted = true;
    }

    /*
     * isFinished - Our isFinished method always returns false meaning this command never completes on it's own. The reason we do this is that this command will be set as the default command for the subsystem. This means that whenever the subsystem is not running another command, it will run this command. If any other command is scheduled it will interrupt this command, then return to this command when the other command completes.
     */
    protected boolean isFinished() {
        return shifted;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}
