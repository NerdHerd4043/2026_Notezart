// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.arm.ArmConstants.ArmPositions;
import frc.robot.subsystems.arm.ArmConstants.FeedForwardValues;
import frc.robot.subsystems.arm.ArmConstants.PIDValues;

@Logged
public class Arm extends SubsystemBase {
  private SparkMax leftArmMotor = new SparkMax(ArmConstants.leftArmMotorID,
      MotorType.kBrushless);
  private SparkMax rightArmMotor = new SparkMax(ArmConstants.rightArmMotorID,
      MotorType.kBrushless);

  private CANcoder encoder = new CANcoder(ArmConstants.encoderID);

  private ArmFeedforward feedforward = new ArmFeedforward(FeedForwardValues.kS,
      FeedForwardValues.kG,
      FeedForwardValues.kV);

  private double ffOutput;
  private boolean podium = false;

  @SuppressWarnings("unused")
  private SparkLimitSwitch rightReverseLimitSwitch;

  private ProfiledPIDController pidController = new ProfiledPIDController(
      PIDValues.p,
      PIDValues.i,
      PIDValues.d,
      new TrapezoidProfile.Constraints(6, 5));

  // /** Creates a new ProfPIDArm. */
  public Arm() {
    // end network tables stuffs
    SparkMaxConfig leftArmMotorConfig = new SparkMaxConfig();
    SparkMaxConfig rightArmMotorConfig = new SparkMaxConfig();

    leftArmMotorConfig.idleMode(IdleMode.kBrake);
    rightArmMotorConfig.idleMode(IdleMode.kBrake);

    // tune the arm
    SmartDashboard.putData(this.pidController);

    // current limit
    leftArmMotorConfig.smartCurrentLimit(ArmConstants.motorCurrentLimit);
    rightArmMotorConfig.smartCurrentLimit(ArmConstants.motorCurrentLimit);

    // set follow
    leftArmMotorConfig.follow(ArmConstants.rightArmMotorID, true);
    // leftArmMotorConfig.inverted(true);
    // rightArmMotorConfig.inverted(false);

    rightArmMotorConfig.limitSwitch.reverseLimitSwitchType(LimitSwitchConfig.Type.kNormallyClosed);

    leftArmMotor.configure(leftArmMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightArmMotor.configure(rightArmMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    this.rightReverseLimitSwitch = this.rightArmMotor.getReverseLimitSwitch();

    this.pidController.setGoal(this.getEncoder());
  }

  private void useOutput(double output, TrapezoidProfile.State setpoint) {
    ffOutput = feedforward.calculate(setpoint.position, setpoint.velocity);
    rightArmMotor.setVoltage(ffOutput + output);
  }

  public void setTarget(double target) {
    // ArmPositions.upper is lower than ArmPositions.lower
    setTarget(target, false);
  }

  public void setTarget(double target, boolean podium) {
    // ArmPositions.upper is lower than ArmPositions.lower
    this.podium = podium;
    double clampedTarget = MathUtil.clamp(target, ArmPositions.lower, ArmPositions.upper);
    this.pidController.setGoal(clampedTarget);
  }

  public void adjustTarget(double delta) {
    if (Math.abs(delta) > 0.01) {
      podium = false;
    }

    setTarget(this.pidController.getGoal().position + delta, podium);
  }

  public void armPodium() {
    if (podium) {
      setTarget(ArmPositions.upper);
    } else {
      setTarget(ArmPositions.podium, true);
    }
  }

  public void armUp() {
    setTarget(ArmPositions.upper);
  }

  public void armDown() {
    setTarget(ArmPositions.lower);
  }

  public double getEncoder() {
    return encoder.getAbsolutePosition().getValueAsDouble();
  }

  public double getMeasurement() {
    // Return the process variable measurement here
    return getEncoder();
  }

  public Command armDownCommand() {
    return this.runOnce(() -> this.armDown());
  }

  @Override
  public void periodic() {
    useOutput(pidController.calculate(getMeasurement()),
        pidController.getSetpoint());

    SmartDashboard.putNumber("ArmGoal", this.pidController.getGoal().position);
    SmartDashboard.putNumber("pos", getMeasurement());
    SmartDashboard.putNumber("encoder", getEncoder());
  }
}
