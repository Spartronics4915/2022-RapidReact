package com.spartronics4915.frc2022.subsystems;

import com.spartronics4915.lib.hardware.motors.SpartronicsSRX;
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

    private boolean mToggleState;
    private int mAccumulator = 0;

    private int mTopMotorDirection;
    private int mBottomMotorDirection;

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

        mTopMotor.configPeakCurrentLimit(kMaxCurrent);
        mBottomMotor.configPeakCurrentLimit(kMaxCurrent);
    }

    // Subsystem methods - actions the robot can take - should be placed here.

    public boolean hasTopBall() {
        return !mBeamBreaker.get();
    }

    public void setMotors(int bottom, int top) {
        mBottomMotorDirection = bottom;
        mToggleState = bottom != 0 || top != 0;
    }

    private void setMotorsInternal(int bottom, int top) {
        mTopMotor.set(ControlMode.PercentOutput, top * kMotorSpeed);
        mBottomMotor.set(ControlMode.PercentOutput, bottom * kMotorSpeed);
    }

    public boolean toggleConveyors() {
        mToggleState = !mToggleState;
        return mToggleState;
    }

    public boolean getToggleState() {
        return mToggleState;
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
        mAccumulator++;
        mAccumulator %= kStopFrequency;
        if (mAccumulator == 0)
            setMotorsInternal(0, mTopMotorDirection);
        if (mAccumulator >= kStopLength)
            setMotorsInternal(mBottomMotorDirection, mTopMotorDirection);
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {
    }

    public void outputTelemetry() {
    }
}
