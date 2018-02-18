package org.usfirst.frc.team3880.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3880.robot.OI;
import org.usfirst.frc.team3880.robot.RobotMap;
import org.usfirst.frc.team3880.robot.Robot;
import org.usfirst.frc.team3880.robot.subsystems.*;


public abstract class CommandBase extends Command {

    public static OI oi;
    
    // Create a single static instance of all of your subsystems
    public static Drive drive;
    public static Climb climb;
    public static Intake intake;
    public static Lift lift;
    public static Pneumatics pneumatics;
    
    public static void init() {      
        drive = Drive.getInstance();
        climb = Climb.getInstance();
        intake = Intake.getInstance();
        lift = Lift.getInstance();
        pneumatics = Pneumatics.getInstance();

        //  ^^^ EVERY SUBSYSTEM MUST be init'd here.  don't move it
        
        oi = new OI();
    }

    public  CommandBase(String name) {
        super(name);
    }

    public  CommandBase() {
        super();
    }
}