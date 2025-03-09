package frc.robot.subsystems.Fider;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class feeder extends SubsystemBase {
    private TalonFX motorFeederSrx = new TalonFX(FeederConstats.FEEDER_ID);



    public feeder(){
        motorFeederSrx.setNeutralMode(NeutralModeValue.Brake, 0);
    }

    public void move(double speed){
      motorFeederSrx.set(speed);
    }
}
