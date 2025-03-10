// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Elbow.ElbowManualControl;
import frc.robot.commands.Shoulder.ShoulderManualControl;
import frc.robot.commands.feeder.FeederManualControl;
import frc.robot.commands.feeder.newFeeder;
// import frc.robot.commands.feeder.FeederManualControl;
import frc.robot.subsystems.Elbow.Elbow;
import frc.robot.subsystems.Fider.feeder;
// import frc.robot.subsystems.Fider.feeder;
import frc.robot.subsystems.Shoulder.Shoulder;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import swervelib.SwerveInputStream;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  // Replace with CommandPS4Controller or CommandJoystick if needed
  final         CommandJoystick joystick1 = new CommandJoystick(0);

private CommandJoystick joystick2 = new CommandJoystick(1);

  private final Shoulder shoulder = new Shoulder();
  private final Elbow elbow = new Elbow();
  private final feeder m_feeder = new feeder();

  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem       drivebase  = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                                "swerve/neo"));



  /**
   * Converts driver input into a field-relative ChassisSpeeds that is controlled by angular velocity.
   */
  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                () -> joystick1.getRawAxis(1),
                                                                () -> joystick1.getRawAxis(0))
                                                            .withControllerRotationAxis(() -> joystick1.getRawAxis(4))
                                                            .deadband(OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);

  /**
   * Clone's the angular velocity input stream and converts it to a fieldRelative input stream.
   */
  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(() -> joystick1.getRawAxis(4),
                                                                                             () -> joystick1.getRawAxis(5))
                                                           .headingWhile(true);

  /**
   * Clone's the angular velocity input stream and converts it to a robotRelative input stream.
   */
  SwerveInputStream driveRobotOriented = driveAngularVelocity.copy().robotRelative(true)
                                                             .allianceRelativeControl(false);

  SwerveInputStream driveAngularVelocityKeyboard = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                        () -> -joystick1.getRawAxis(1),
                                                                        () -> -joystick1.getRawAxis(0))
                                                                    .withControllerRotationAxis(() -> joystick1.getRawAxis(
                                                                        2))
                                                                    .deadband(OperatorConstants.DEADBAND)
                                                                    .scaleTranslation(0.8)
                                                                    .allianceRelativeControl(true);
  // Derive the heading axis with math!
  SwerveInputStream driveDirectAngleKeyboard     = driveAngularVelocityKeyboard.copy()
                                                                               .withControllerHeadingAxis(() ->
                                                                                                              Math.sin(
                                                                                                                  joystick1.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2),
                                                                                                          () ->
                                                                                                              Math.cos(
                                                                                                                  joystick1.getRawAxis(
                                                                                                                      2) *
                                                                                                                  Math.PI) *
                                                                                                              (Math.PI *
                                                                                                               2))
                                                                               .headingWhile(true);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Configure the trigger bindings
    configureBindings();
    DriverStation.silenceJoystickConnectionWarning(true);
    NamedCommands.registerCommand("test", Commands.print("I EXIST"));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {

    Command driveFieldOrientedDirectAngle      = drivebase.driveFieldOriented(driveDirectAngle);
    Command driveFieldOrientedAnglularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);
    Command driveRobotOrientedAngularVelocity  = drivebase.driveFieldOriented(driveRobotOriented);
    Command driveSetpointGen = drivebase.driveWithSetpointGeneratorFieldRelative(
        driveDirectAngle);
    Command driveFieldOrientedDirectAngleKeyboard      = drivebase.driveFieldOriented(driveDirectAngleKeyboard);
    Command driveFieldOrientedAnglularVelocityKeyboard = drivebase.driveFieldOriented(driveAngularVelocityKeyboard);
    Command driveSetpointGenKeyboard = drivebase.driveWithSetpointGeneratorFieldRelative(
        driveDirectAngleKeyboard);

    if (RobotBase.isSimulation())
    {
      drivebase.setDefaultCommand(driveFieldOrientedDirectAngleKeyboard);
    } else
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    }

    if (Robot.isSimulation())
    {
      joystick1.button(8).onTrue(Commands.runOnce(() -> drivebase.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
      joystick1.button(1).whileTrue(drivebase.sysIdDriveMotorCommand());

    }
    if (DriverStation.isTest())
    {
      drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity); // Overrides drive command above!

      joystick1.button(3).whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
      joystick1.button(4).whileTrue(drivebase.driveToDistanceCommand(1.0, 0.2));
      joystick1.button(8).onTrue((Commands.runOnce(drivebase::zeroGyro)));
      joystick1.button(7).whileTrue(drivebase.centerModulesCommand());
      joystick1.button(5).onTrue(Commands.none());
      joystick1.button(6).onTrue(Commands.none());
    } else
    {
      joystick1.button(1).onTrue((Commands.runOnce(drivebase::zeroGyro)));

      new Trigger(() -> joystick2.getRawAxis(1) > 0.05).whileTrue(new ShoulderManualControl(shoulder, joystick2));
      new Trigger(() -> joystick2.getRawAxis(1) < -0.05).whileTrue(new ShoulderManualControl(shoulder, joystick2));

      new Trigger(() -> joystick2.getRawAxis(5) > 0.05).whileTrue(new ElbowManualControl(elbow, joystick2));
      new Trigger(() -> joystick2.getRawAxis(5) < -0.05).whileTrue(new ElbowManualControl(elbow, joystick2));

      new Trigger(()-> joystick2.getRawAxis(2) > 0.05).whileTrue(new FeederManualControl (m_feeder, joystick2));
      new Trigger(()-> joystick2.getRawAxis(3) < -0.05).whileTrue(new FeederManualControl (m_feeder, joystick2));

    //new Trigger(() -> joystick1.button(5).getAsBoolean()).onTrue(drivebase.sysIdAngleMotorCommand());
      System.out.println("Not in test mode");
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    return new RunCommand(() -> {
      drivebase.drive(new Translation2d(1, 0), 0, false);
    }, drivebase).withTimeout(15);
    // An example command will be run in autonomous
    // return Commands.run(() -> {
      

    //   SwerveInputStream inputStream = SwerveInputStream.of(drivebase.getSwerveDrive(), () -> 0.5, () -> 0).robotRelative(true);
    //   drivebase.drive(inputStream.get());
    // }).raceWith(new WaitCommand(2).andThen(Commands.run(() -> {
    //   drivebase.drive(new ChassisSpeeds(0,0,0));
    // })));
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
  
}
