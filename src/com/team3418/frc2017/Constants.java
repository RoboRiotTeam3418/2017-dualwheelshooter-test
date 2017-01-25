package com.team3418.frc2017;

/**
 * A list of constants used by the rest of the robot code.
 */

public class Constants {
	
	
    public static double kConstantVariableExample = 10.0;
   
    // do not change anything after this line, hardware ports should not change
    // TALONS

    // SOLENOIDS
    
    // PCM #, Solenoid #
    

    // Analog Inputs


    // DIGITAL IO
    

    // PWM
    
    //CAN
    public static int kShooterLeftId = 0;
    public static int kShooterRightId = 1;
    
 // Flywheel constants
    public static double kFlywheelOnTargetTolerance = 100.0;
    public static double kFlywheelRpmSetpoint = 4200.0;
    
    //PID gains for flywheel velocity
    public static double kFlywheelKp = 0.1;
    public static double kFlywheelKi = 0.00035;
    public static double kFlywheelKd = 2.3;
    public static double kFlywheelKf = 0.037;
    public static int kFlywheelIZone = (int) (1023.0 / kFlywheelKp);
    public static double kFlywheelRampRate = 0;
    public static int kFlywheelAllowableError = 0;
    
}
