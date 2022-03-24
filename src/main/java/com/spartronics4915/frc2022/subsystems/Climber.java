package com.spartronics4915.frc2022.subsystems;

import static com.spartronics4915.frc2022.Constants.Climber.*;
import com.spartronics4915.frc2022.Constants;

import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Detailed description of Climber.
 */
public class Climber extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    private TalonFX mClimberMotor;
    private Solenoid mClimberSolenoid;

    private double mMotorSpeed;

    private boolean isInitialized;

    /** Creates a new Climber. */
    public Climber()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
            mClimberMotor = new TalonFX(kClimberMotorId);
            mClimberMotor.setInverted(kMotorIsInverted);
            mClimberMotor.setNeutralMode(NeutralMode.Brake); // set brake mode
	    //disable current limit with first variable
	    mClimberMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, kMaxCurrent, kMaxCurrent, 0));

            //mMotorSensors = new TalonFXSensorCollection()

            mClimberSolenoid = new Solenoid(Constants.kPCMId, PneumaticsModuleType.CTREPCM, kClimberSolenoidId);
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
	// Set a flag so there is no use of this module if initialization failed
        this.isInitialized = success;	// Store the state of this module
    }

    // Subsystem methods - actions the robot can take - should be placed here.
    public void setMotor(double speed)
    {
	// Only access the mClimberMotor if initialization was successful
	if (isInitialized)
	{
	    mMotorSpeed = speed;
	    mClimberMotor.set(TalonFXControlMode.PercentOutput, speed);	// If motor follower, this should be fine
	}
	else
	{
	    mMotorSpeed = 0;
	}
    }

    public void setSolenoid(boolean isExtended)
    {
	// Only access the mClimberSolenoid if initialization was successful
	if (isInitialized)
	{
	    // logInfo("Set Solenoid to " + isExtended);
	    // mClimberMotor.get
	    mClimberSolenoid.set(isExtended != kSolenoidIsInverted);
	}
    }

    public double getCurrentRotations()
    {
	// Only access the mClimberMotor if initialization was successful
	if (isInitialized)
	{
	    return mClimberMotor.getSensorCollection().getIntegratedSensorPosition() / kNativeUnitsPerRevolution / kClimberGearRatio;
	}
	else
	{
	    return 0.0;
	}
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
        logInfo("ROTATIONS " + getCurrentRotations());

        double rotations = getCurrentRotations();

        // logInfo("theta = " + rotations);

        if (mMotorSpeed > 0 && rotations > kMaxRotations)
            setMotor(0);

        if (mMotorSpeed < 0 && rotations < kMinRotations)
            setMotor(0);
    }

    public void zeroEncoder() {
	// Only access the mClimberMotor if initialization was successful
	if (isInitialized)
	{
	    mClimberMotor.getSensorCollection().setIntegratedSensorPosition(0, 100);
	}
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
