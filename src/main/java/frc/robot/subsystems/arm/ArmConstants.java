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
    public static final double p = 200; // old 4
    public static final double i = 0;
    public static final double d = 0; // old 1.7
  }

  // FIXME: Needs to be tuned for new gear ratios
  public static final class FeedForwardValues {
    public static final double kS = 0; // old 0.07
    public static final double kG = 0.4; // old 0.4
    public static final double kV = 4; // old 3
  }

  /* Unit: rotations */
  public static final class ArmPositions {
    public static final double lower = 0;
    public static final double upper = 0.26;

    public static final double level = 0.02;

    public static final double podium = 0.1;
  }
}
