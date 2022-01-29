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
    public static final int kTestMotorId = 1;
    public static final class Drive { // TODO: Everything is a stand in value
        // hardware IDs
        public static final int kPigeonID = -1;
        public static final int kLeftMotorID = -1;
        public static final int kRightMotorID = -1;
        public static final int kLeftFollowerMotorID = -1;
        public static final int kRightFollowerMotorID = -1;

        // sensor models
        public static final double kWheelDiameter = Double.NaN;
        public static final double kNativeUnitsPerRevolution = Double.NaN;

        // constructors
        public static final TriFunction<Integer, SensorModel, Integer, SpartronicsMotor> kMotorConstructor = SpartronicsMax::makeMotor;

        // output inversion
        public static final boolean kLeftOutputInverted = false;
        public static final boolean kRightOutputInverted = false;
        public static final boolean kLeftFollowerOutputInverted = false;
        public static final boolean kRightFollowerOutputInverted = false;

        // PID
        public static final double kP = 1;
        public static final double kD = 0;

        // current limits
        public static final int kMaxStatorCurrent = 30; // poof alakazam

        // controls
        public static final double kSlowModeMultiplier = 0.1;
        public static final double kLinearResponseCurveExponent = 5.0 / 3.0;
        public static final double kJoystickDeadzoneSize = 0.1;
    }

    public static final class Intake {
        public static final int kMotorId = 0;
        public static final SensorModel kSensorModel = SensorModel.fromMultiplier(1);
        public static final double kHarvestSpeed = 0.8; //I stole this from Infinite Recharge
        public static final double kEjectSpeed = -0.8; // Stole this from IR too, I don't know if these numbers are good
    }

    public static final class OIConstants {
        public static final int kArcadeStickPort = 1;
        public static final int kStartIntakeButton = 2;
        public static final int kStopIntakeButton = 3;

    }
}
