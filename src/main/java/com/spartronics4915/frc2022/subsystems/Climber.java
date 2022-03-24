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
    private TalonFX mClimberMotor1;
    private TalonFX mClimberMotor2;
    private Solenoid mClimberSolenoid;

    private double mMotorSpeed;

    /** Creates a new Climber. */
    public Climber()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
            mClimberMotor1 = new TalonFX(kClimberMotor1Id);
            mClimberMotor2 = new TalonFX(kClimberMotor2Id);
            //mMotorSensors = new TalonFXSensorCollection()

            mClimberSolenoid = new Solenoid(Constants.kPCMId, PneumaticsModuleType.CTREPCM, kClimberSolenoidId);
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
        
        mClimberMotor1.setInverted(kMotor1IsInverted);
        mClimberMotor2.setInverted(kMotor1IsInverted);
        mClimberMotor1.setNeutralMode(NeutralMode.Brake); // set brake mode
        mClimberMotor2.setNeutralMode(NeutralMode.Brake);

        //disable current limit with first variable
        mClimberMotor1.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, kMaxCurrent, kMaxCurrent, 0));
        mClimberMotor2.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(false, kMaxCurrent, kMaxCurrent, 0));
    }

    // Subsystem methods - actions the robot can take - should be placed here.
    public void setMotors(double speed)
    {
        mMotorSpeed = speed;
        mClimberMotor1.set(TalonFXControlMode.PercentOutput, speed);
        mClimberMotor2.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setSolenoid(boolean isExtended)
    {
        // logInfo("Set Solenoid to " + isExtended);
        // mClimberMotor.get
        mClimberSolenoid.set(isExtended != kSolenoidIsInverted);
    }

    public double getCurrentRotations(){
        return mClimberMotor1.getSensorCollection().getIntegratedSensorPosition() / kNativeUnitsPerRevolution / kClimberGearRatio;
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
        logInfo("ROTATIONS " + getCurrentRotations());

        double rotations = getCurrentRotations();

        // logInfo("theta = " + rotations);

        if (mMotorSpeed > 0 && rotations > kMaxRotations)
            setMotors(0);

        if (mMotorSpeed < 0 && rotations < kMinRotations)
            setMotors(0);
    }

    public void zeroEncoder() {
        mClimberMotor1.getSensorCollection().setIntegratedSensorPosition(0, 100);
        mClimberMotor2.getSensorCollection().setIntegratedSensorPosition(0, 100);
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
