package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.subsystems.SENSORS;
import org.usfirst.frc.team1322.robot.calibrations.K_PIDCal;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Class: PIDDRV - Performs P-I-D Control of the Drive System Motors based
 * on the Encoders Angular Speeds.
 */
public class PIDDRV extends Subsystem {
    
	private double SpdTgt[] = new double[4];        // RPM
	private double SpdErr[] = new double[4];        // RPM
	private double SpdErrAccum[] = new double[4];   // RPM
    
	
	
    public void managePIDDrive() {
    	
    	calcSpdErr();
    	
    }

    
    public void calcSpdErr() {
        int idx;
    	double SpdErrRaw;
    	double SpdErrTemp;
    	double SpdAct[] = new double[4];
    	
    	SpdErrTemp = (double)0;
    	SpdAct = Robot.kSENSORS.getEncodersRPM();
    	
    	
    	for (idx=0; idx<4; idx++)
          {
    	  SpdErrRaw = SpdTgt[idx] - SpdAct[idx];
    	  if(SpdErrRaw >= (double)0)
    	    {
        	if(SpdErrRaw >= K_PIDCal.KDRV_n_ErrDeadBand[idx])
        	  {
        	  SpdErrTemp = SpdErrRaw - K_PIDCal.KDRV_n_ErrDeadBand[idx];  	  
        	  }
    	    }
    	  else  // (SpdErrRaw < 0) 
    	    {
          	if(SpdErrRaw <= -(K_PIDCal.KDRV_n_ErrDeadBand[idx]))
      	      {
      	      SpdErrTemp = SpdErrRaw + (K_PIDCal.KDRV_n_ErrDeadBand[idx]);  	  
      	      }
    	    }
    	  
    	  SpdErr[idx] = SpdErrTemp;
    	  SpdErrAccum[idx] += SpdErrTemp;
    	  
    	  }	
          
    }
       
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

