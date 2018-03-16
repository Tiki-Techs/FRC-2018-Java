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
    public static LeftArm leftArm;
    public static RightArm rightArm;
    public static LeftIntakeWheel leftIntakeWheel;
    public static RightIntakeWheel rightIntakeWheel;
    public static Lift lift;
    public static Pneumatics pneumatics;
    public static Gyro gyro;
    
    
    public static int OI_MODE = 1;
    
    public static void init() {      
        drive = Drive.getInstance();
        climb = Climb.getInstance();
        leftArm = LeftArm.getInstance();
        rightArm = RightArm.getInstance();
        rightIntakeWheel = RightIntakeWheel.getInstance();
        leftIntakeWheel = LeftIntakeWheel.getInstance();
        lift = Lift.getInstance();
        pneumatics = Pneumatics.getInstance();
        gyro = Gyro.getInstance();

        //  ^^^ EVERY SUBSYSTEM MUST be init'd here.  don't move it
        
        oi = new OI(OI_MODE);
    }

    public  CommandBase(String name) {
        super(name);
    }

    public  CommandBase() {
        super();
    }
}