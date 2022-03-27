package com.spartronics4915.frc2022.subsystems;

import static com.spartronics4915.frc2022.Constants.Climber.*;
import com.spartronics4915.frc2022.Constants;

import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
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
    private TalonFX mMotor1;
    //private TalonFX mMotor2;
    
    private Solenoid mSolenoid;

    private double mMotorSpeed;

    private boolean mIsInitialized;

    /** Creates a new Climber. */
    public Climber()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
            mMotor1 = new TalonFX(kClimberMotorId);
            //mMotor2 = new TalonFX(kClimberFollowerId); 
            mMotor1.setInverted(kMotor1IsInverted);
            //mMotor2.setInverted(kMotor2IsInverted);
            mMotor1.setNeutralMode(NeutralMode.Brake); // set brake mode
            //mMotor2.setNeutralMode(NeutralMode.Brake); // set brake mode

            //mMotorSensors = new TalonFXSensorCollection()

            mSolenoid = new Solenoid(Constants.kPCMId, PneumaticsModuleType.CTREPCM, kClimberSolenoidId);
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
        mIsInitialized = success;
    }

    // Subsystem methods - actions the robot can take - should be placed here.
    public void setMotor(double speed)
    {
        mMotorSpeed = speed;
        if (mIsInitialized)
        {
            mMotor1.set(TalonFXControlMode.PercentOutput, speed);
            //mMotor2.set(TalonFXControlMode.PercentOutput, -speed);
        }
    }

    public void setSolenoid(boolean isExtended)
    {
        // logInfo("Set Solenoid to " + isExtended);
        if (mIsInitialized)
        {
            mSolenoid.set(isExtended != kSolenoidIsInverted);
        }
    }

    public double getCurrentRotations(){
        if (mIsInitialized)
        {
           return mMotor1.getSensorCollection().getIntegratedSensorPosition() / kNativeUnitsPerRevolution / kClimberGearRatio;
        }
        return 0.0;
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
        // logInfo("ROTATIONS " + getCurrentRotations());

        double rotations = getCurrentRotations();

        // logInfo("theta = " + rotations);

        if (mMotorSpeed > 0 && rotations > kMaxRotations)
            setMotor(0);

        if (mMotorSpeed < 0 && rotations < kMinRotations)
            setMotor(0);
    }

    public void zeroEncoder() {
        if (mIsInitialized)
        {
           mMotor1.getSensorCollection().setIntegratedSensorPosition(0, 100);
        }
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
