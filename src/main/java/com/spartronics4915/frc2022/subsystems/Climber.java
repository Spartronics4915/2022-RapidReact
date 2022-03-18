package com.spartronics4915.frc2022.subsystems;

import static com.spartronics4915.frc2022.Constants.Climber.*;
import com.spartronics4915.frc2022.Constants;

import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Detailed description of Climber.
 */
public class Climber extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    private CANSparkMax mClimberMotor;
    private Solenoid mClimberSolenoid;

    private double mMotorSpeed;

    /** Creates a new Climber. */
    public Climber()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
            mClimberMotor = new CANSparkMax(kClimberMotorId, MotorType.kBrushless);
            mClimberMotor.setInverted(kMotorIsInverted);
            mClimberMotor.setIdleMode(IdleMode.kBrake); // set brake mode

            //mMotorSensors = new TalonFXSensorCollection()

            mClimberSolenoid = new Solenoid(Constants.kPCMId, PneumaticsModuleType.CTREPCM, kClimberSolenoidId);
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
        
        // mClimberMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, kMaxCurrent, kMaxCurrent, 0));
        mClimberMotor.getEncoder().setPosition(0);

    }

    // Subsystem methods - actions the robot can take - should be placed here.
    public void setMotor(double speed)
    {
        mMotorSpeed = speed;
        mClimberMotor.set(speed);
    }

    public void setSolenoid(boolean isExtended)
    {
        // logInfo("Set Solenoid to " + isExtended);
        // mClimberMotor.get
        mClimberSolenoid.set(isExtended != kSolenoidIsInverted);
    }

    public double getCurrentRotations(){
        return mClimberMotor.getEncoder().getPosition() / kNativeUnitsPerRevolution / kClimberGearRatio;
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

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
