package com.team3418.frc2017;

import edu.wpi.first.wpilibj.Joystick;

/**
 * A basic framework for the control board Like the drive code, one instance of
 * the ControlBoard object is created upon startup, then other methods request
 * the singleton ControlBoard instance.
 */
public class ControlBoard {
    private static ControlBoard mInstance = new ControlBoard();

    public static ControlBoard getInstance() {
        return mInstance;
    }
    
    //create joystick object
    private final Joystick mDriverStick;
    
    
    
    //initialize joystick objects
    ControlBoard() {
        mDriverStick = new Joystick(0);
    }
    
    
    
    // return button / axis info
    // EXAMPLES
    /*
    
    public double getThrottle() {
        return -mDriverStick.getY();
    }
    
    public double getTurn() {
        return mOperatorStick.getX();
    }
    
    public double getExclusiveOptions() {
        if (mOtherThingie.getRawButton(11)) {
            return 1.0;
        } else if (mOtherThingie.getRawButton(12)) {
            return -1.0;
        } else {
            return 0.0;
        }
    }
    
    public boolean getRawAxisGreaterThan() {
        return mOtherThingie.getRawAxis(3) > 0.1;
    }
    
    public boolean getRawAxisLessThan() {
        return mOtherThingie.getRawAxis(2) < -0.1;
    }
    
    public boolean getButtonCombo() {
        return mOtherThingie.getRawButton(1) && mOtherThingie.getRawButton(2);
    }
    
    */
    
    
    // DRIVER CONTROLS (mDriverStick)
    
    public boolean startFeeder() {
    	return mDriverStick.getRawButton(1);
    }
    
    
    
    public boolean increaseShooterSetpointButton(){
    	return mDriverStick.getRawButton(2);
    }
    
    public boolean decreaseShooterSetpointButton(){
    	return mDriverStick.getRawButton(3);
    }
    
    public boolean increaseFeederSpeedButton(){
    	return mDriverStick.getRawButton(6);
    }
    
    public boolean decreaseFeederSpeedButton(){
    	return mDriverStick.getRawButton(5);
    }
    
    public boolean spoolShooter(){
    	return mDriverStick.getRawButton(4);
    }
    
    
    public double getLeftShooterSpeed(){
    	return mDriverStick.getRawAxis(2);
    }
    
    public double getRightShooterSpeed(){
    	return mDriverStick.getRawAxis(3);
    }
    
}
