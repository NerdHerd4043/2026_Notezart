// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.config.ModuleConfig;

import frc.robot.subsystems.drivebase.DriveConstants;
import frc.robot.subsystems.drivebase.DriveConstants.ModuleLocations;
import edu.wpi.first.math.system.plant.DCMotor;
import com.pathplanner.lib.config.RobotConfig;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final class RobotConfigInfo {

    public static final ModuleConfig moduleConfig = new ModuleConfig(
        DriveConstants.WHEEL_DIAMETER,
        2.75,
        0.7,
        DCMotor.getNEO(1),
        DriveConstants.DRIVE_REDUCTION,
        DriveConstants.currentLimit,
        1);

    public static final RobotConfig robotConfig = new RobotConfig(
        66.68, 3.682,
        moduleConfig,
        ModuleLocations.frontLeft,
        ModuleLocations.frontRight,
        ModuleLocations.backLeft,
        ModuleLocations.backRight);
  }

  public static final class IntakeConstants {
    public static final int intakeMotorID = 7;
    public static final int kickupMotorID = 17;
    public static final double intakeSpeed = 0.7;
    public static final double kickupSpeed = 0.7;

    public static final int currentLimit = 30;
  }

  public static final class ClimberConstants {
    public static final int climberMotorID = 20;
  }

  public static final class ShooterConstants {
    public static final int topShootMotorID = 16;
    public static final int bottomShootMotorID = 6;
    public static final double shooterSpeed = -1;

    public static final double targetFlywheelVelocity = 3700;
    public static final int currentLimit = 50;
  }

  public static final class AutoConstants {
    public static final class XPID {
      public static final double p = 1.5;
      public static final double i = 0;
      public static final double d = 0;
    }

    public static final class YPID {
      public static final double p = 1.5;
      public static final double i = 0;
      public static final double d = 0;
    }

    public static final class RPID {
      public static final double p = 0.0015;
      public static final double i = 0;
      public static final double d = 0.0002;
    }

    public static final int medianFilter = 5;
  }

  public static final class PathPlannerConstants {
    public static final class TranslationPID {
      public static final double p = 5;
      public static final double i = 0;
      public static final double d = 0;
    }

    public static final class RotationPID {
      public static final double p = 6;
      public static final double i = 0;
      public static final double d = 0;
    }
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class CANdleConstants {
    public static final int id = 50;
    public static final int ledCount = 50;
  }
}
