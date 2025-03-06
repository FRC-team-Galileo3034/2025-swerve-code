package frc.robot.subsystems.Elbow;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Shoulder.ShoulderConstants;

public class Elbow extends SubsystemBase {

private SparkMax motorElbowMax = new SparkMax(ElbowConstets.ELBOW_ID, MotorType.kBrushless);
   private RelativeEncoder elbowEncoder;

    public Elbow(){
        motorElbowMax.configure(
            new SparkMaxConfig().idleMode(IdleMode.kBrake), 
            null, 
            null
        );
        elbowEncoder = motorElbowMax.getEncoder();
    
    }

    public void setVoltage(double voltage){
        motorElbowMax.setVoltage(voltage);
    }

    public double getAngle(){
        double angle = elbowEncoder.getPosition() *ShoulderConstants.MOTOR_GEAR_RATIO*360;

        return angle;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("angle",getAngle() );
        SmartDashboard.updateValues();
    }

}