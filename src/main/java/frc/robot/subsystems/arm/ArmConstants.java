package frc.robot.subsystems.arm;

public final class ArmConstants {
  public static final int leftArmMotorID = 5;
  public static final int rightArmMotorID = 15;
  public static final int encoderID = 25;
  public static final double raiseArmSpeed = 1;
  public static final int lowerArmSpeed = -1;
  public static final int motorCurrentLimit = 40;

  // FIXME: Needs to be tuned for new gear ratios
  public static final class PIDValues {
    public static final double p = 4;
    public static final double i = 0;
    public static final double d = 1.7;
  }

  // FIXME: Needs to be tuned for new gear ratios
  public static final class FeedForwardValues {
    public static final double kS = 0.07;
    public static final double kG = 0.4;
    public static final double kV = 3;
  }

  public static final class ArmPositions {
    public static final double lower = 0.6285;
    public static final double lowerRad = lower;
    public static final double upper = 2.008;
    public static final double upperRad = upper;

    public static final double podium = 1.2;
  }
}
