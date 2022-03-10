package com.spartronics4915.frc2022.subsystems;

import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;

import static com.spartronics4915.frc2022.Constants.Conveyor.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Detailed description of Conveyer.
 */
public class Conveyor extends SpartronicsSubsystem {
    // The subsystem's hardware is defined here...
    private TalonSRX mBottomMotor;
    private TalonSRX mTopMotor;

    private DigitalInput mBeamBreaker;

    /** Creates a new Conveyor. */
    public Conveyor() {
        boolean success = true;

        try {
            // ...and constructed here.
            mTopMotor = new TalonSRX(kTopMotorId);
            mBottomMotor = new TalonSRX(kBottomMotorId);

            mBeamBreaker = new DigitalInput(kBeamBreakerId);
        } catch (Exception exception) {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);

        mTopMotor.setInverted(false);
        mBottomMotor.setInverted(false);
    }

    // Subsystem methods - actions the robot can take - should be placed here.

    public boolean hasTopBall() {
        return mBeamBreaker.get();
    }

    public void setMotors(int bottom, int top) {
        mTopMotor.set(ControlMode.PercentOutput, top * kMotorSpeed);
        mBottomMotor.set(ControlMode.PercentOutput, bottom * kMotorSpeed);
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {
    }

    public void outputTelemetry() {
    }
}
