package com.spartronics4915.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

/**
 * Detailed description of Intake.
 */
public class Intake extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
   // SpartronicsMotor mIntakeMotor;
    private CANSparkMax mMotor;

    /** Creates a new Intake.
     * @param SpartronicsMax */
    public Intake()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
           mMotor = new CANSparkMax(Constants.kTestMotorId, MotorType.kBrushless);
        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
    }

    // Subsystem methods - actions the robot can take - should be placed here.
    public void setSpeed(double speed) {
        mMotor.set(speed);
        logInfo("running");
    }

    public void startIntake(){
        mMotor.set(0.3);
        logInfo("running");
    }

    public void stop() {
        mMotor.set(0);
    }

    public void printSomething(){
        logInfo("Button is pressed");
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {}

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
