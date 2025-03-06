// package frc.robot.subsystems.Fider;

// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
// import com.revrobotics.spark.config.SparkMaxConfig;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;


// public class feeder extends SubsystemBase {
//         private SparkMax motorFeederMax = new SparkMax(FeederConstats.FEEDER_ID, MotorType.kBrushless);
//     private RelativeEncoder feederEncoder;


//     public feeder(){
//         motorFeederMax.configure(
//             new SparkMaxConfig().idleMode(IdleMode.kBrake), 
//             null, 
//             null
//         );
//         feederEncoder = motorFeederMax.getEncoder();
//     }

//     public void setVoltage(double voltage){
//         motorFeederMax.setVoltage(voltage);
//     }

//     public double getAngle(){
//         double angle = feederEncoder.getPosition();

//         return angle;
//     }

//     @Override
//     public void periodic() {
//         SmartDashboard.putNumber("angle",getAngle() );
//         SmartDashboard.updateValues();
//     }
// }
