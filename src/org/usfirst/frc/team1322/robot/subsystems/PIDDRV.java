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
    
	private boolean PIDEnbl;                        // boolean 
	private boolean PIDIntglRst;                    // boolean 
	private boolean DrvDrctnFwd[] = new boolean[4]; // boolean 
	private double SpdTgt[] = new double[4];        // RPM
	private double SpdErrRaw[] = new double[4];     // RPM
	private double SpdErr[] = new double[4];        // RPM
	private double SpdErrAccum[] = new double[4];   // RPM
	private double SpdErrChngRt[] = new double[4];  // RPM/sec
	private double FdFwdCmnd[] = new double[4];     // Pct Pwr  [-100% to 100%]
	private double PropCorr[] = new double[4];      // Pct Pwr  [-100% to 100%]
	private double IntglCorr[] = new double[4];     // Pct Pwr  [-100% to 100%]
	private double DerivCorr[] = new double[4];     // Pct Pwr  [-100% to 100%] 
	private double PIDCorr[] = new double[4];       // Pct Pwr  [-100% to 100%] 
	private double PIDCmndPct[] = new double[4]; // Pct Pwr  [-100% to 100%] 
	private double PIDDrvCmnd[] = new double[4];    // Norm Pwr [-1 to 1]
	

	
    public void managePIDDrive() {
        int EncdrIdx;

        dtrmnPIDEnbl();       
    	for (EncdrIdx=0; EncdrIdx<4; EncdrIdx++)
        {
    	if (PIDEnbl == true)
    	    {
    	    dtrmnPIDDrctn(EncdrIdx);
            calcSpdErr(EncdrIdx);
            calcSpdErrAccum(EncdrIdx);
            calcSpdChgRt(EncdrIdx);
            calcFdFwdTerm(EncdrIdx);
            calcPropTerm(EncdrIdx);
            calcIntglTerm(EncdrIdx);
            calcDerivTerm(EncdrIdx);
            calcPIDResult(EncdrIdx);  
    	    }
    	else
    	    {
    	    resetPIDCntrlr(EncdrIdx);  
    	    }
        }    	
    	
    }

    
    /** Method: getPIDCmnd - Interface to Get the array of
	 * Encoder Motor Normalized Power Commands (-1 to 1).
     *  @return: Array of Drive System Motor Driver Normalized Power Commands (-1 to 1) */	
    public double[] getPIDCmnd() {
    	double[] drvrcmnds = PIDDrvCmnd;
    	return drvrcmnds;
    }


    private void dtrmnPIDEnbl() {
        
    	// todo	
    	
    	PIDEnbl = false;
    	PIDIntglRst = false;
    	
    }

    
    private void dtrmnPIDDrctn(int idx) {
    
    	// todo	
    	
    	DrvDrctnFwd[idx] = true;
    	
    }
    
    private void calcSpdErr(int idx) {
    	double SpdErrTemp;
    	double SpdAct[] = new double[4];
    	
    	SpdErrTemp = (double)0;
    	SpdAct = Robot.kSENSORS.getEncodersRPM();
    	    	
        SpdErrRaw[idx] = SpdTgt[idx] - SpdAct[idx];
        
        if(SpdErrRaw[idx] >= (double)0)
	      {
    	  if(SpdErrRaw[idx] >= K_PIDCal.KDRV_n_ErrDeadBand[idx])
    	    {
    	    SpdErrTemp = SpdErrRaw[idx] - K_PIDCal.KDRV_n_ErrDeadBand[idx];  	  
    	    }
	      }
	    else  // (SpdErrRaw < 0) 
	      {
      	  if(SpdErrRaw[idx] <= -(K_PIDCal.KDRV_n_ErrDeadBand[idx]))
  	        {
  	        SpdErrTemp = SpdErrRaw[idx] + (K_PIDCal.KDRV_n_ErrDeadBand[idx]);  	  
  	        }
	      }
	  
	    SpdErr[idx] = SpdErrTemp;
      }

    
    private void calcSpdErrAccum(int idx) {
    	boolean SignFlipRst = false;
      
      
      if ((SpdErrAccum[idx] > 0) && (SpdErrRaw[idx] < 0)) SignFlipRst = true;
      else if ((SpdErrAccum[idx] < 0) && (SpdErrRaw[idx] > 0)) SignFlipRst = true;
    	
	  if((PIDIntglRst == true) || (SignFlipRst == true))
	    {
	    SpdErrAccum[idx] = (double)0.0;  
	    }
	  else  // (AccumRst == false) 
	    {
     	SpdErrAccum[idx] += SpdErr[idx];  
    	}	    
    }

    
    private void calcSpdChgRt(int idx) {

    	// todo
   	   
    	SpdErrChngRt[idx] = 0.0;
    }
    
    
    private void calcFdFwdTerm(int idx) {
    
    	// todo 
    	
    	FdFwdCmnd[idx] = 0.0;
    }

    
    private void calcPropTerm(int idx) {
    	double P_Corr;
    	double LimMaxPos;
    	double LimMaxNeg;
    	
    	LimMaxPos = K_PIDCal.KDRV_Pct_PropCorrLimMax[idx];
    	LimMaxNeg = -(K_PIDCal.KDRV_Pct_PropCorrLimMax)[idx];
    	
    	P_Corr = K_PIDCal.KDRV_K_PropGain[idx] * SpdErr[idx];
    	
    	if (P_Corr > LimMaxPos) P_Corr = LimMaxPos;
    	else if (P_Corr < LimMaxNeg) P_Corr = LimMaxNeg;
    	    	
    	PropCorr[idx] = P_Corr;
    }
    
    
    private void calcIntglTerm(int idx) {
    	double I_Corr;
    	double LimMaxPos;
    	double LimMaxNeg;
    	
    	LimMaxPos = K_PIDCal.KDRV_Pct_IntglCorrLimMax[idx];
    	LimMaxNeg = -(K_PIDCal.KDRV_Pct_IntglCorrLimMax)[idx];
    	
    	I_Corr = K_PIDCal.KDRV_K_IntglGx[idx] * SpdErrAccum[idx];
    	
    	if (I_Corr > LimMaxPos) I_Corr = LimMaxPos;
      	else if (I_Corr < LimMaxNeg) I_Corr = LimMaxNeg;
    	
    	IntglCorr[idx] = I_Corr;
    }
    
    
    private void calcDerivTerm(int idx) {
    	double D_Corr;
    	double LimMaxPos;
    	double LimMaxNeg;
    	
    	LimMaxPos = K_PIDCal.KDRV_Pct_DerivCorrLimMax[idx];
    	LimMaxNeg = -(K_PIDCal.KDRV_Pct_DerivCorrLimMax)[idx];
    	
    	D_Corr = K_PIDCal.KDRV_K_DerivGx[idx] * SpdErrChngRt[idx];
    	
    	if (D_Corr > LimMaxPos) D_Corr = LimMaxPos;
      	else if (D_Corr < LimMaxNeg) D_Corr = LimMaxNeg;
    	
    	DerivCorr[idx] = D_Corr; 	
    }
    
    private void calcPIDResult(int idx) {
    	double CmndPct; // %
    	
	    PIDCorr[idx] = PropCorr[idx] + IntglCorr[idx] + DerivCorr[idx];
	    CmndPct = FdFwdCmnd[idx] + PIDCorr[idx];
	    if (CmndPct > 100) CmndPct = 100;
	    else if (CmndPct < 0) CmndPct = 0;
	    if (DrvDrctnFwd[idx] == false) CmndPct = -CmndPct;
	
	    PIDCmndPct[idx] = CmndPct;
	    PIDDrvCmnd[idx]= CmndPct/100;

    }
    

    private void resetPIDCntrlr(int idx) {
        DrvDrctnFwd[idx] = true; 
        SpdTgt[idx] = 0.0;
        SpdErrRaw[idx] = 0.0;
        SpdErr[idx] = 0.0;
        SpdErrAccum[idx] = 0.0;
        SpdErrChngRt[idx] = 0.0;
        FdFwdCmnd[idx] = 0.0;
  	    PropCorr[idx] = 0.0;
  	    IntglCorr[idx] = 0.0;
  	    DerivCorr[idx] = 	 0.0;	  
    	PIDCorr[idx] =  0.0;
  	    PIDCmndPct[idx] =  0.0;
  	    PIDDrvCmnd[idx]=  0.0; 
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
}

