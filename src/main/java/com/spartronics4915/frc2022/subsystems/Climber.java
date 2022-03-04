package com.spartronics4915.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

import static com.spartronics4915.frc2022.Constants.Climber.*;

/**
 * Detailed description of Climber.
 */
public class Climber extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    private TalonFX mClimberMotor;
    private Solenoid mClimberSolenoid;

    /** Creates a new Climber. */
    public Climber()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
            mClimberMotor = new TalonFX(kClimberMotorId);
            mClimberMotor.setInverted(kIsInverted);
            mClimberMotor.setNeutralMode(NeutralMode.Brake); // set brake mode

            mClimberSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, kClimberSolenoidId);
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
    }

    // Subsystem methods - actions the robot can take - should be placed here.
    public void setMotor(double speed)
    {
        mClimberMotor.set(TalonFXControlMode.PercentOutput, speed);
    }

    public void setSolenoid(boolean isExtended)
    {
        mClimberSolenoid.set(isExtended);
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {}

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
