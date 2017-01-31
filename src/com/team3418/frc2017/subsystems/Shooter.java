package com.team3418.frc2017.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team3418.frc2017.Constants;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {

	
	static Shooter mInstance = new Shooter();

    public static Shooter getInstance() {
        return mInstance;
    }
    
    CANTalon mLeftShooterTalon;
	CANTalon mRightShooterTalon;
	VictorSP mFeederVictorSP;
    
    public Shooter() {
    	//initialize shooter hardware settings
		System.out.println("Shooter Initialized");
		
		//Feeder Motor Controller
		mFeederVictorSP = new VictorSP(2);
		mFeederVictorSP.setInverted(false);
		
		
		//Left Talon Motor Controller
		mLeftShooterTalon = new CANTalon(Constants.kShooterLeftId);			
		mLeftShooterTalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		mLeftShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
		mLeftShooterTalon.set(0);
		mLeftShooterTalon.setPID(Constants.kFlywheelKp, Constants.kFlywheelKi, Constants.kFlywheelKd, Constants.kFlywheelKf,
                Constants.kFlywheelIZone, Constants.kFlywheelRampRate, 0);
		mLeftShooterTalon.setProfile(0);
		//mLeftShooterTalon.reverseSensor(false);
		//mLeftShooterTalon.reverseOutput(true);
		mLeftShooterTalon.setInverted(true);
		
		mLeftShooterTalon.setVoltageRampRate(0);
		mLeftShooterTalon.enableBrakeMode(false);
		mLeftShooterTalon.clearStickyFaults();
		
		mLeftShooterTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		mLeftShooterTalon.configPeakOutputVoltage(+0.0f, -12.0f);
		mLeftShooterTalon.setAllowableClosedLoopErr(Constants.kFlywheelAllowableError);		
		
		
		//Right Talon Motor Controller
		mRightShooterTalon = new CANTalon(Constants.kShooterRightId);
		mRightShooterTalon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		mRightShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
		mRightShooterTalon.set(0);
		mRightShooterTalon.setPID(Constants.kFlywheelKp, Constants.kFlywheelKi, Constants.kFlywheelKd, Constants.kFlywheelKf,
                Constants.kFlywheelIZone, Constants.kFlywheelRampRate, 0);
		mRightShooterTalon.setProfile(0);
		//mRightShooterTalon.reverseSensor(true);
		//mRightShooterTalon.reverseOutput(false);
		mRightShooterTalon.setInverted(false);
		
		mRightShooterTalon.setVoltageRampRate(0);
		mRightShooterTalon.enableBrakeMode(false);
		mRightShooterTalon.clearStickyFaults();
		
		mRightShooterTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		mRightShooterTalon.configPeakOutputVoltage(+12.0f, -.0f);
		mRightShooterTalon.setAllowableClosedLoopErr(Constants.kFlywheelAllowableError);		
		
		mTargetRpm = 1900;
		mTargetFeederSpeed = 0;
		}
    
    public enum ShooterReadyState {
    	READY, NOT_READY
    }

    
    //
    private double mTargetRpm;
    private double mTargetFeederSpeed;
       
    public void setTargetRpm(double rpm){
    	mTargetRpm = rpm;
    }
    
    public double getTargetRpm(){
    	return mTargetRpm;
    }
    
    public void setTargetFeederSpeed(double speed){
    	mTargetFeederSpeed = speed;
    }
    
    public double getTargetFeederSpeed(){
    	return mTargetFeederSpeed;
    }
    
    //
    
    //
    private ShooterReadyState mShooterReadyState;
    
    public ShooterReadyState getShooterState(){
    	return mShooterReadyState;
    }
    
    public ShooterReadyState getShooterReadyState(){
    	return mShooterReadyState;
    }
    //
    
    
    
	@Override
	public void updateSubsystemState() {
		
		//printShooterInfo();
		outputToSmartDashboard();
		
		if (bothIsOnTarget()){
			mShooterReadyState = ShooterReadyState.READY;
		} else {
			mShooterReadyState = ShooterReadyState.NOT_READY;
		}
	}
	
	
	
	/*print shooter info to console
	private void printShooterInfo(){
		System.out.println(" out: "+getMotorOutput()+
				   " spd: "+mMasterShooterTalon.getSpeed()+
		           " err: "+mMasterShooterTalon.getClosedLoopError()+
		           " trgrpm: "+getTargetRpm()+
		           " trgspd: "+getTargetSpeed());
	}
	*/
	
	
	//get shooter speed info methods
	private double getLeftRpm(){
		return mLeftShooterTalon.getSpeed();
	}
	
	private double getRightRpm(){
		return mRightShooterTalon.getSpeed();
	}
	
	
	private double getLeftSetpoint(){
		return mLeftShooterTalon.getSetpoint();
	}
	
	private double getRightSetpoint(){
		return mRightShooterTalon.getSetpoint();
	}
	//
	
	//set shooter speed methods
	public void setRpm(double rpm){
		mLeftShooterTalon.changeControlMode(TalonControlMode.Speed);
		mLeftShooterTalon.set(rpm);
		mRightShooterTalon.changeControlMode(TalonControlMode.Speed);
		mRightShooterTalon.set(rpm);
	}
	
	public void setLeftOpenLoop(double speed){
		mLeftShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
		mLeftShooterTalon.set(-speed);
	}
	
	public void setRightOpenLoop(double speed){
		mRightShooterTalon.changeControlMode(TalonControlMode.PercentVbus);
		mRightShooterTalon.set(speed);
	}
	
	public void setFeederSpeed(double speed){
		if (bothIsOnTarget()){
			mFeederVictorSP.set(speed);
		}
		else{
			mFeederVictorSP.set(0);
		}
	}
	
	public void stop(){
		setLeftOpenLoop(0);
		setRightOpenLoop(0);
		setFeederSpeed(0);
	}
	//
	
	//set shooter ready state
	private boolean leftIsOnTarget(){
		return (mLeftShooterTalon.getControlMode() == CANTalon.TalonControlMode.Speed
                && Math.abs(getLeftRpm() - getLeftSetpoint()) < Constants.kFlywheelOnTargetTolerance);
	}
	
	private boolean RightIsOnTarget(){
		return (mRightShooterTalon.getControlMode() == CANTalon.TalonControlMode.Speed
                && Math.abs(getRightRpm() - getRightSetpoint()) < Constants.kFlywheelOnTargetTolerance);
	}
	
	private boolean bothIsOnTarget(){
		return (leftIsOnTarget() && RightIsOnTarget());
	}
	//
	
	

	@Override
	public void outputToSmartDashboard() {
        SmartDashboard.putBoolean("Flywheel_On_Target", bothIsOnTarget());
        
		SmartDashboard.putNumber("Left_Flywheel_rpm", getLeftRpm());
		SmartDashboard.putNumber("Right_Flywheel_rpm", getRightRpm());
		
		SmartDashboard.putNumber("Target_rpm", getTargetRpm());
		
        SmartDashboard.putNumber("Left_Flywheel_Setpoint", getLeftSetpoint());
        SmartDashboard.putNumber("Right_Flywheel_Setpoint", getRightSetpoint());
        
        SmartDashboard.putNumber("Left_Flywheel_Closed_Loop_error", mLeftShooterTalon.getClosedLoopError());
        SmartDashboard.putNumber("Left_Flywheel_Closed_Loop_error_Number", mLeftShooterTalon.getClosedLoopError());
        SmartDashboard.putNumber("Right_Flywheel_Closed_Loop_error", mRightShooterTalon.getClosedLoopError());
        SmartDashboard.putNumber("Right_Flywheel_Closed_Loop_error_Number", mRightShooterTalon.getClosedLoopError());
        
        SmartDashboard.putNumber("Left_Flywheel_Output_Current", mLeftShooterTalon.getOutputCurrent());
        SmartDashboard.putNumber("Right_Flywheel_Output_Current", mRightShooterTalon.getOutputCurrent());
        
        SmartDashboard.putNumber("Left_Encoder_Velocity", mLeftShooterTalon.getEncVelocity());
        SmartDashboard.putNumber("Right_Encoder_Velocity", mRightShooterTalon.getEncVelocity());
        
        SmartDashboard.putNumber("Left_Closed_Loop_Ramp_Rate", mLeftShooterTalon.getCloseLoopRampRate());
        SmartDashboard.putNumber("Right_Closed_Loop_Ramp_Rate", mRightShooterTalon.getCloseLoopRampRate());
	}
    
    
    
    
}

