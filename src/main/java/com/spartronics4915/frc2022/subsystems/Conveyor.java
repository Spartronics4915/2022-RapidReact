package com.spartronics4915.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.frc2022.commands.ConveyorCommands;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.hardware.motors.SpartronicsSRX;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;

import static com.spartronics4915.frc2022.Constants.Conveyor.*;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

/**
 * Detailed description of Conveyer.
 */
public class Conveyor extends SpartronicsSubsystem {
    // The subsystem's hardware is defined here...
    private SpartronicsMotor mBottomMotor;
    private SpartronicsMotor mTopMotor;

    private DigitalInput mBeamBreaker;

    /** Creates a new Conveyor. */
    public Conveyor() {
        boolean success = true;

        try {
            // ...and constructed here.
            mTopMotor = SpartronicsSRX.makeMotor(kTopMotorId);
            mBottomMotor = SpartronicsSRX.makeMotor(kBottomMotorId);

            mBeamBreaker = new DigitalInput(kBeamBreakerId);
        } catch (Exception exception) {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);

        mTopMotor.setOutputInverted(false);
        mBottomMotor.setOutputInverted(false);
    }

    // Subsystem methods - actions the robot can take - should be placed here.

    public boolean hasTopBall() {
        return mBeamBreaker.get();
    }

    public void setMotors(int bottom, int top) {
        mTopMotor.setPercentOutput(top * kMotorSpeed);
        mBottomMotor.setPercentOutput(bottom * kMotorSpeed);
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
