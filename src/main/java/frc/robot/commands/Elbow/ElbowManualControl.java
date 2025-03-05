package frc.robot.commands.Elbow;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.Elbow.Elbow;


public class ElbowManualControl extends Command {
    
    private final Elbow elbow;

    private final CommandJoystick commandJoystick;

    public ElbowManualControl(Elbow elbow, CommandJoystick joystick2Joystick){
        this.elbow=elbow;
        commandJoystick=joystick2Joystick;
        addRequirements(elbow);
    }
    
    @Override
    public void execute() {
        elbow.setVoltage(-commandJoystick.getRawAxis(5) * RobotController.getBatteryVoltage() );
    }

    @Override
    public void end(boolean interrupted) {
        elbow.setVoltage(0);
    }
}
