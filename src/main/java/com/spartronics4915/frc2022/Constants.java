package com.spartronics4915.frc2022;

import com.spartronics4915.lib.hardware.motors.SensorModel;
import com.spartronics4915.lib.hardware.motors.SpartronicsMax;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.util.TriFunction;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static).
 * Do not put anything functional in this class.
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants
{
    public static final class Drive { // TODO: some is a stand in value
        // hardware IDs
        public static final int kPigeonID = 12;
        
        public static final int kRightMotorID = 1;
        public static final int kRightFollowerMotorID = 2;

        public static final int kLeftMotorID = 3;
        public static final int kLeftFollowerMotorID = 4;

        // sensor models
        public static final double kWheelDiameter = 0.1524; // in meters
        public static final double kNativeUnitsPerRevolution = 1;

        // constructors
        public static final TriFunction<Integer, SensorModel, Integer, SpartronicsMotor> kMotorConstructor = SpartronicsMax::makeMotor;

        // output inversion
        public static final boolean kLeftOutputInverted = false;
        public static final boolean kRightOutputInverted = true;
        public static final boolean kLeftFollowerOutputInverted = false;
        public static final boolean kRightFollowerOutputInverted = true;

        // PID
        public static final double kP = 1;
        public static final double kD = 0;

        // current limits
        public static final int kMaxCurrent = 30; // poof alakazam

        // controls
        public static final double kSlowModeMultiplier = 0.1;
        public static final double kLinearResponseCurveExponent = 5.0 / 3.0;
        public static final double kJoystickDeadzoneSize = 0.1;
    }

    public static final class Intake {
        public static final int kIntakeMotorId = 7;
        public static final SensorModel kSensorModel = SensorModel.fromMultiplier(1);
        public static final double kHarvestSpeed = 0.3; //I stole this from Infinite Recharge
        public static final double kEjectSpeed = -0.3; // Stole this from IR too, I don't know if these numbers are good
        public static final int kIntakeSolenoidId = 0;
        public static final boolean kIntakeMotorInverted = true;
        public static final double kRetractIntakeDelay = 0.3;

        public static final int kMaxCurrent = 30;
    }

    public static class Conveyor {
    
        public static final int kBottomMotorId = 8;
        public static final int kTopMotorId = 9;

        public static final int kBeamBreakerId = 0;

        public static final double kMotorSpeed = 0.45;
        
        public static final double kTopConveyorTime = 2.0;
        public static final double kBottomConveyorTime = 1.0;

        public static final int kStopFrequency = (int)(1 / 0.02);
        public static final int kStopLength = (int)(0.1 / 0.02);

        public static final int kMaxCurrent = 30;
        
        public static final double kStopConveyorsDelay = 1.0;
    }
    public static class Launcher {
        
        public static class Flywheel {
            public static final int kId = 5;
            public static final double kP = 0.03;
            public static final double kD = 0;
            
            //public static final int kId = 5;
            //public static final double kP = 0.18366;
            //public static final double kD = 0;
            public static final boolean kInverted = false;
            
            public static final double kRPS = 28.07;
            public static final double kFarRPS = 10;

            public static final int kMaxCurrent = 40;
        }

        public static class SpinMotor {
            public static final boolean kInverted = false;
            public static final int kId = 6;
            public static final double kSpeed = 0.1;

            public static final double kP = 0;
            public static final double kD = 0;
            
            public static final double kSpeedFactor = .5;

            public static final int kMaxCurrent = 40;
        }
        // public static class Spin {
        //     public static final double kP = 0.03;
        //     public static final double kS = 0.0286; // 0.0654;
        //     public static final double kV = 7.86; // 7.18;
        //     public static final double kA = 5.16;
        // }
    }
    public static final class Climber
    {
        public static final int kClimberMotorId = 10; // we dk
        public static final double kClimberMotorSpeed = 1.0;
        public static final boolean kMotorIsInverted = true;
        public static final int kClimberSolenoidId = 1;
        public static final boolean kSolenoidIsInverted = false;
        /**
         * The delay in seconds between disengaging the ratchet and starting the motor, or stopping the motor and 
         * engaging the ratchet.
         */
        public static final double kDelay = 0.1; // adjust 1st number for ms

        public static final int kMaxCurrent = 40;
    }

    public static final class OIConstants {
        public static final int kArcadeStickPort = 1;
        public static final int kJoystickPort = 0;

        public static final int kConveyorReverseBothButton = 3;
        public static final int kConveyorReverseBottomButton = 4;
        public static final int kConveyorReverseTopButton = 5;

        public static final int kLauncherShootButton = 6;
        public static final int kLauncherToggleButton = 8;
        public static final int kLauncherShootFarButton = 7;
        
        public static final int kIntakeToggleButton = 2;
        public static final int kIntakeReverseButton = 1;
        
        public static final int kClimberExtendButton = 9;
        public static final int kClimberRetractButton = 10;

    }

    public static final class Trajectory {
        //TODO: obtén TODOS los valores
        public static final double kMaxVelocity = 2;
        public static final double kMaxAcceleration = .05;
        
        //Feedforward constants (will get from SysId)
        public static final double kS = 0;
        public static final double kV = 0;
        public static final double kA = 0;

        //Robot size (meters)
        public static final double kTrackWidth = 1; 
    }

    public static final int kPCMId = 11;
}
