package org.usfirst.frc.team3880.robot.commands;

import org.usfirst.frc.team3880.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Toggle extends Command {
	
	private boolean finished = false;
	
	public Toggle() {
		// Use requires() here to declare subsystem dependencies
//		requires(Robot.o);
		super("Toggle");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("initialized the stupid buton");
				
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.turbo = !Robot.turbo;
		SmartDashboard.putBoolean("toggleTest", Robot.turbo);
		System.out.println("Toggled to " + Robot.turbo);
		if(Robot.turbo) {
			Robot.gearIntake.set(DoubleSolenoid.Value.kForward);
		}
		else if(!Robot.turbo) {
			Robot.gearIntake.set(DoubleSolenoid.Value.kReverse);
		}
		
		finished = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		SmartDashboard.putBoolean("commandTest", finished);
		return finished;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
