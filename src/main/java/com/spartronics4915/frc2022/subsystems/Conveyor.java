package com.spartronics4915.frc2022.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.lib.hardware.motors.SpartronicsMax;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

/**
 * Detailed description of Conveyer.
 */
public class Conveyor extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    private SpartronicsMotor mMotor;

    /** Creates a new Conveyer. */
    public Conveyor()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
            mMotor = SpartronicsMax.makeMotor(Constants.kTestMotorId);
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
        mMotor.setVelocity(speed);
        logInfo("running");
    }

    public void startConveyor(){
        mMotor.setVelocity(0.3);
        logInfo("running");
    }

    public void stopConveyor() {
        mMotor.setVelocity(0);
        logInfo("stopped");}
    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {}

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
