// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.Fider.feeder;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class newFeeder extends Command {
  private feeder m_Feeder;
  private CommandJoystick m_controller;

  public newFeeder(feeder feeder, CommandJoystick controller) {
    m_Feeder = feeder;
    m_controller = controller;
    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Feeder.move(0);
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Feeder.move(m_controller.getRawAxis(2) - m_controller.getRawAxis(3));
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  m_Feeder.move(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
