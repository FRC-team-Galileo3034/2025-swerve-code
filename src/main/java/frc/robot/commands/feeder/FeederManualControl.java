package frc.robot.commands.feeder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.subsystems.Fider.feeder;

public class FeederManualControl extends Command {
    
    private final feeder feeder;

    private final CommandJoystick commandJoystick;

    public FeederManualControl(feeder feeder, CommandJoystick joystick2Joystick){
        this.feeder = feeder;
        commandJoystick=joystick2Joystick;
        addRequirements(feeder);
    }
    
    @Override
    public void execute() {
        feeder.move(commandJoystick.getRawAxis(2) - commandJoystick.getRawAxis(3));
    }

    @Override
    public void end(boolean interrupted) {
        feeder.move(0);
    }
}
