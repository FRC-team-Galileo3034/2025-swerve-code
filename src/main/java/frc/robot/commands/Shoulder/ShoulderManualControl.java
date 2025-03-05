package frc.robot.commands.Shoulder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.Shoulder.Shoulder;

public class ShoulderManualControl extends Command {

    private final Shoulder shoulder;

    private final CommandJoystick commandJoystick;

    public ShoulderManualControl(Shoulder shoulder, CommandJoystick joystick2Joystick){
        this.shoulder=shoulder;
        commandJoystick=joystick2Joystick;
        addRequirements(shoulder);
    }
    
    @Override
    public void execute() {
        shoulder.setVoltage(-commandJoystick.getRawAxis(1) * RobotController.getBatteryVoltage() );
    }

    @Override
    public void end(boolean interrupted) {
        shoulder.setVoltage(0);
    }
}
