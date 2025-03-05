package frc.robot.subsystems.Shoulder;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shoulder extends SubsystemBase {

    private SparkMax motorShoulderMax = new SparkMax(ShoulderConstants.SHOULDER_ID, MotorType.kBrushless);
    private RelativeEncoder shoulderEncoder;


    public Shoulder(){
        motorShoulderMax.configure(
            new SparkMaxConfig().idleMode(IdleMode.kBrake), 
            null, 
            null
        );
        shoulderEncoder = motorShoulderMax.getEncoder();
    }

    public void setVoltage(double voltage){
        motorShoulderMax.setVoltage(voltage);
    }

    public double getAngle(){
        double angle = shoulderEncoder.getPosition() *ShoulderConstants.MOTOR_GEAR_RATIO *ShoulderConstants.GEAR_RATIO*360;

        return angle;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("angle",getAngle() );
        SmartDashboard.updateValues();
    }

}
