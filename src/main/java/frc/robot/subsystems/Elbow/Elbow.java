package frc.robot.subsystems.Elbow;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elbow extends SubsystemBase {

    private SparkMax motorElbowMax = new SparkMax(10, MotorType.kBrushless);



    public Elbow(){
        motorElbowMax.configure(
            new SparkMaxConfig().idleMode(IdleMode.kBrake), 
            null, 
            null
        );
    }

    public void setVoltage(double voltage){
        motorElbowMax.setVoltage(voltage);
    }

}
