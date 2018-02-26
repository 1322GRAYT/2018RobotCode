package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.subsystems.TBLLOOKUP;
import org.usfirst.frc.team1322.robot.calibrations.K_PIDCal;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Class: PIDDRV - Performs P-I-D Control of the Drive System Motors based
 * on the Encoders Angular Speeds.
 */
public class PIDDRV extends Subsystem {
    
	private boolean PIDMstrEnbl;                         // boolean
	private boolean PIDEnbl[] = new boolean[4];         // boolean 
	private boolean PIDIntglRst[] = new boolean[4];     // boolean 
	private boolean DrvDrctnFwd[] = new boolean[4];     // boolean
	private double SpdProfTmr;                          // sec
	private double SpdProfSclr;                         // scalar   [ 0 to 10]
	private double SpdTgtRaw;                           // RPM
	private double SpdTgt[] = new double[4];            // RPM
	private double SpdErrRaw[] = new double[4];         // RPM
	private double SpdErr[] = new double[4];            // RPM
	private double SpdErrPrev[] = new double[4];        // RPM
	private double SpdErrFilt[] = new double[4];        // RPM	
	private double SpdErrFiltPrev[] = new double[4];    // RPM	
	private double SpdErrAccum[] = new double[4];       // RPM
	private double SpdErrChngRt[] = new double[4];      // RPM/sec
	private double SpdErrChngRtFilt[] = new double[4];  // RPM/sec	
	private double FdFwdCmnd[] = new double[4];         // Pct Pwr  [-100% to 100%]
	private double PropCorr[] = new double[4];          // Pct Pwr  [-100% to 100%]
	private double IntglCorr[] = new double[4];         // Pct Pwr  [-100% to 100%]
	private double DerivCorr[] = new double[4];         // Pct Pwr  [-100% to 100%] 
	private double PIDCorr[] = new double[4];           // Pct Pwr  [-100% to 100%] 
	private double PIDCmndPct[] = new double[4];        // Pct Pwr  [-100% to 100%] 
	private double PIDDrvCmnd[] = new double[4];        // Norm Pwr [-1 to 1]
	
  
    /**********************************************/
    /* Public Interface Definitions        */
    /**********************************************/

    /** Method: resetTgtProfTmr - Resets the Drive Speed
     * Target Profile Timer at the beginning of a Drive Segments.  */ 
    public void resetTgtProfTmr() {
    	SpdProfTmr = (double)0.0;
    }

    /** Method: putPIDDrvSpdTgt - Interface to Set the Encoder Motor
      * Target Speed for the Drive System PID Controller.
      *  @param1: Drive System Master Enable Select (boolean)	
      *  @param2: Drive System Desired Encoder Speed Target (rpm: double) */	
    public void putPIDDrvSpdTgt(boolean DrvSysEnbl, double DrvSpdTgt) {
    	PIDMstrEnbl = DrvSysEnbl; // boolean
    	SpdTgtRaw = DrvSpdTgt;    // rpm
    }  
    
    /** Method: getPIDDrvCmnd - Interface to Get the array of
	  * Encoder Motor Normalized Power Commands (-1 to 1).
      *  @return: Array of Drive System Motor Driver Normalized Power Commands (-1 to 1: double) */	
    public double[] getPIDDrvCmnd() {
    	double[] drvrcmnds = PIDDrvCmnd;
    	return drvrcmnds;
    }

    /** Method: resetPIDDrv - Interface to Reset the Drive System
	  * PID Controller, i.e. initialize PID variables.  */ 
    public void resetPIDDrv() {
    	int encdrIdx;
    	
    	for (encdrIdx = 0; encdrIdx < 4; encdrIdx++ )
    		resetPIDCntrlr(encdrIdx);
    }

    
    
    /******************************************************/
    /* Manage Drive PID Control Subsystem Scheduler Task  */
    /******************************************************/
	
    /** Method: managePIDDrive - Scheduler Function for the Periodic
      * Drive System Encoder Speed PIDF Control System.  */ 
    public void managePIDDrive() {
        int EncdrIdx;

		dtrmnTgtProf();
        
    	for (EncdrIdx=0; EncdrIdx<4; EncdrIdx++)
        {
            dtrmnPIDEnbl(EncdrIdx);       
    		dtrmnDrtcnIsFwd(EncdrIdx);
    		
    	if (PIDEnbl[EncdrIdx] == true)
    	    {
    		dtrmnIntglRst(EncdrIdx);
            calcSpdErr(EncdrIdx);
            calcSpdErrAccum(EncdrIdx);
            calcSpdChgRt(EncdrIdx);
            calcFdFwdTerm(EncdrIdx);
            calcPropTerm(EncdrIdx);
            calcIntglTerm(EncdrIdx);
            calcDerivTerm(EncdrIdx);
            calcPIDResult(EncdrIdx);  
    	    }
    	else  // (PIDEnbl[idx] == false)
    	    {
    	    resetPIDCntrlr(EncdrIdx);  
    	    }
        }    	
    	
    }    
    
    
    /**********************************************/
    /* Internal Class Methods                     */
    /**********************************************/

    /** Method: dtrmnTgtProf - Determines the Desired Speed Target
      * Profile that will be fed into the PIDF Controller.  */ 
    private void dtrmnTgtProf() {
        int idx;
        float SpdAxisIdx;
        float TmAxisIdx;
        float SpdTgtTemp;
        
        // Service Profile Timer
        SpdProfTmr += (double)K_PIDCal.KDRV_t_PID_LoopRt;
        
	    // Shape the Target Speed Profile		    	
    	if (SpdTgtRaw == 0.0) 
    	{
    		SpdTgtTemp = (float)0.0;	
    	}
    	else
    	{	    		
	    	SpdAxisIdx = Robot.kTBLLOOKUP.AxisPieceWiseLinear_int((float)SpdTgtRaw, K_PIDCal.KDRV_n_TgtProfSpdAxis, (int)10);	
	    	TmAxisIdx = Robot.kTBLLOOKUP.AxisPieceWiseLinear_flt((float)SpdProfTmr, K_PIDCal.KDRV_t_TgtProfTmAxis, (int)10);
	    	SpdProfSclr = Robot.kTBLLOOKUP.XYZ_Lookup_flt(K_PIDCal.KDRV_n_TgtProf, TmAxisIdx, SpdAxisIdx, (int)10, (int)10);
	    	
	    	SpdTgtTemp = (float)SpdProfSclr * (float)(Math.abs(SpdTgtRaw));
    	}
    	
    	for (idx = 0; idx < 4; idx++)
        {
        SpdTgt[idx] = SpdTgtTemp; 	
        }
    }

    
    /** Method: dtrmnPIDEnbl - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void dtrmnPIDEnbl(int idx) {
    	if ((PIDMstrEnbl == false) || (SpdTgt[idx] == 0.0))
    	{
    		PIDEnbl[idx] = false;
    	}
    	else
    	{
    		PIDEnbl[idx] = true;    		    		
    	}    	
    }
    
       
    /** Method: dtrmnDrtcnIsFwd - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void dtrmnDrtcnIsFwd(int idx) {        
    	// Determine if Motors should Run in Forward or Reverse	
    	if (SpdTgtRaw >= 0)	
        	DrvDrctnFwd[idx] = true;
    	else
        	DrvDrctnFwd[idx] = false;    		
    }

    
    /** Method: dtrmnIntglRst - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void dtrmnIntglRst(int idx) {
    	
    	PIDIntglRst[idx] = false;
    }

    
    /** Method: calcSpdErr - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void calcSpdErr(int idx) {
    	double SpdErrTemp;
    	double DeltSpdErr;
    	double SpdAct[] = new double[4];
    	
    	SpdErrTemp = (double)0;
    	SpdAct = Robot.kSENSORS.getEncodersRPM();
    	SpdErrPrev[idx] = SpdErr[idx]; 
    	SpdErrFiltPrev[idx] = SpdErrFilt[idx]; 
    	    	
        SpdErrRaw[idx] = SpdTgt[idx] - SpdAct[idx];
        
        if(SpdErrRaw[idx] >= (double)0)
	      {
    	  if(SpdErrRaw[idx] >= K_PIDCal.KDRV_n_ErrDB[idx])
    	    {
    	    SpdErrTemp = SpdErrRaw[idx] - K_PIDCal.KDRV_n_ErrDB[idx];  	  
    	    }
	      }
	    else  // (SpdErrRaw < 0) 
	      {
      	  if(SpdErrRaw[idx] <= -(K_PIDCal.KDRV_n_ErrDB[idx]))
  	        {
  	        SpdErrTemp = SpdErrRaw[idx] + (K_PIDCal.KDRV_n_ErrDB[idx]);  	  
  	        }
	      }
	  
	    SpdErr[idx] = SpdErrTemp;
	    
	    DeltSpdErr = SpdErr[idx] - SpdErrFiltPrev[idx];
	    SpdErrFilt[idx] = SpdErrFiltPrev[idx] + (DeltSpdErr * K_PIDCal.KDRV_k_SpdErrFiltCoef[idx]);   
      }

    
    /** Method: calcSpdErrAccum - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void calcSpdErrAccum(int idx) {
    	boolean SignFlipRst = false;
      
      if (K_PIDCal.KDRV_b_IntglGxRstSgnFlipEnbl == true)
          {
	      if ((SpdErrAccum[idx] > 0) && (SpdErr[idx] < 0))
	        {
	        SignFlipRst = true;
	        }
	      else if ((SpdErrAccum[idx] < 0) && (SpdErr[idx] > 0))
	        {
	        SignFlipRst = true;
	        }
          }
      
	  if((PIDIntglRst[idx] == true) || (SignFlipRst == true))
	    {
	    SpdErrAccum[idx] = (double)0.0;  
	    }
	  else  // (AccumRst == false) 
	    {
     	SpdErrAccum[idx] += SpdErr[idx];  
    	}	    
    }

    
    /** Method: calcSpdChgRt - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void calcSpdChgRt(int idx) {
    	double DeltSpdErr;
    	double SpdErrRtFiltPrev;
    	double DeltSpdErrRt;

    	DeltSpdErr = SpdErrFilt[idx] - SpdErrFiltPrev[idx];
    	SpdErrChngRt[idx] = DeltSpdErr / (double)K_PIDCal.KDRV_t_PID_LoopRt;
    	
	    SpdErrRtFiltPrev = SpdErrChngRtFilt[idx];
	    DeltSpdErrRt = SpdErrChngRt[idx] - SpdErrRtFiltPrev;
	    SpdErrChngRtFilt[idx] = SpdErrRtFiltPrev + (DeltSpdErrRt * K_PIDCal.KDRV_k_SpdErrRtFiltCoef[idx]);       	
    }

    
    /** Method: calcFdFwdTerm - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
   private void calcFdFwdTerm(int idx) {
        float  DsrdSpd;
    	float  AxisIdx;
        float  FdFwdCalc;
        
        DsrdSpd = (float)SpdTgt[idx];
        
        AxisIdx = Robot.kTBLLOOKUP.AxisPieceWiseLinear_int(DsrdSpd, K_PIDCal.KDRV_n_FdFwdErrAxis[idx], 10);
    	FdFwdCalc = Robot.kTBLLOOKUP.XY_Lookup_flt(K_PIDCal.KDRV_Pct_FdFwdCmnd[idx], AxisIdx, 10);

    	FdFwdCmnd[idx] = (double)FdFwdCalc;
    }

   
   /** Method: calcPropTerm - YadaYada
    *  @param:  input info	(units)
    *  @return: output info (units) */	
    private void calcPropTerm(int idx) {
    	double P_Corr;
    	double LimMaxPos;
    	double LimMaxNeg;
    	
    	LimMaxPos = K_PIDCal.KDRV_Pct_PropCorrLimMax[idx];
    	LimMaxNeg = -(K_PIDCal.KDRV_Pct_PropCorrLimMax)[idx];
    	
    	P_Corr = K_PIDCal.KDRV_K_PropGx[idx] * SpdErr[idx];
    	
    	if (P_Corr > LimMaxPos) P_Corr = LimMaxPos;
    	else if (P_Corr < LimMaxNeg) P_Corr = LimMaxNeg;
    	    	
    	PropCorr[idx] = P_Corr;
    }

    
    /** Method: calcIntglTerm - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
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

    
    /** Method: calcDerivTerm - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void calcDerivTerm(int idx) {
    	boolean DerivEnbl;
    	double D_Corr;
    	double LimMaxPos;
    	double LimMaxNeg;
    	
    	
    	if ((Math.abs(SpdErrFilt[idx]) < K_PIDCal.KDRV_n_DerivCorrErrMinEnbl[idx]) ||
    		(Math.abs(SpdErrFilt[idx]) > K_PIDCal.KDRV_n_DerivCorrErrMaxEnbl[idx]) ||
    		((SpdErrFilt[idx] > 0.0) && (SpdErrChngRtFilt[idx] > 0.0)) ||
    		((SpdErrFilt[idx] < 0.0) && (SpdErrChngRtFilt[idx] < 0.0)) ||
    		((Math.abs(SpdErrFilt[idx])) < K_PIDCal.KDRV_dn_ErrChgRtDB[idx]))
    	{
    		DerivEnbl = false;
    	}
    	else
		{
			DerivEnbl = true;
		}

    	
    	if (DerivEnbl == true)
    	{	
	    	LimMaxPos = K_PIDCal.KDRV_Pct_DerivCorrLimMax[idx];
	    	LimMaxNeg = -(K_PIDCal.KDRV_Pct_DerivCorrLimMax)[idx];
	    	
	    	D_Corr = K_PIDCal.KDRV_K_DerivGx[idx] * SpdErrChngRtFilt[idx];
	    	
	    	if (D_Corr > LimMaxPos) D_Corr = LimMaxPos;
	      	else if (D_Corr < LimMaxNeg) D_Corr = LimMaxNeg;
	    	
	    	DerivCorr[idx] = D_Corr;
    	}
    	else
    	{
        	DerivCorr[idx] = 0.0;    		
    	}
    }

    
    /** Method: calcPIDResult - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
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
    
    
    /** Method: resetPIDCntrlr - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void resetPIDCntrlr(int idx) {
        SpdErrRaw[idx] = 0.0;
        SpdErr[idx] = 0.0;
        SpdErrPrev[idx] = 0.0;
        SpdErrFilt[idx] = 0.0;
        SpdErrFiltPrev[idx] = 0.0;
        SpdErrAccum[idx] = 0.0;
        SpdErrChngRt[idx] = 0.0;
        SpdErrChngRtFilt[idx] = 0.0;
        FdFwdCmnd[idx] = 0.0;
  	    PropCorr[idx] = 0.0;
  	    IntglCorr[idx] = 0.0;
  	    DerivCorr[idx] = 	 0.0;	  
    	PIDCorr[idx] =  0.0;
  	    PIDCmndPct[idx] =  0.0;
  	    PIDDrvCmnd[idx]=  0.0; 
    }
    
    
    /** Method: initDefaultCommand - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(resetPIDDrv());
    }
    
    
}

