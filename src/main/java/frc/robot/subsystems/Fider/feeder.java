package frc.robot.subsystems.Fider;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class feeder extends SubsystemBase {
        private WPI_TalonSRXmotorFeederSrx = new WPI_TalonSRX(FeederConstats.FEEDER_ID);
    private RelativeEncoder feederEncoder;


    public feeder(){
      motorFeederSrx.configure(
            new SparkMaxConfig().idleMode(IdleMode.kBrake), 
            null, 
            null
        );
        feederEncoder =motorFeederSRX.getEncoder();
    }

    public void setVoltage(double voltage){
      motorFeederSrx.setVoltage(voltage);
    }

    public double getAngle(){
        double angle = feederEncoder.getPosition();

        return angle;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("angle",getAngle() );
        SmartDashboard.updateValues();
    }
}
