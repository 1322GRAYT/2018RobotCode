package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_PIDCal;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Class: PIDROT - Performs P-I-D Control of the Rotation of the Robot
 * via Drive System Motors based on the Angular Position Feedback of the
 * Gyro.
 */
public class PID extends Subsystem {
	private Timer TgtCondTmr = new Timer();
	
	// Variable Declarations
	boolean PIDRotEnbl;        // (boolean)
	boolean PIDDrvEnbl;        // (boolean)
	boolean DirtcnIsClckWise;  // (boolean)
	boolean PstnErrWithInDB;   // (boolean)
	boolean PIDRotCondCmplt;   // (boolean)
	double  PstnAct;           // (degrees)
    double  PstnDsrd;	       // (degrees)
    double  PstnErr;           // (degrees)
    double  PstnErrAccum;      // (degrees)
    double  FdFwdCorr;         // (percent)
    double  PropCorr;          // (percent)
    double  IntglCorr;         // (percent)
    double  PIDCmndPct;        // (percent power)
    double  PIDCmndNorm;       // (normalized power)
    double  PIDDrvOnTgtTm;     // (seconds)
    double  PIDDrvOnTgtTmMax;  // (seconds)
    
    
    /**********************************************/
    /* Public Interface Definitions        */
    /**********************************************/
    
    /** Method: resetTgtCondTmr - Resets the Drive Speed
     * Target Profile Timer at the beginning of a Drive Segments.  */ 
    public void resetTgtCondTmr() {
    	TgtCondTmr.reset();
    }

    /** Method: putPIDRotPstnTgt - Interface to Set the Gyro Target
      * Position Angle for the Robot Rotate PID Controller. (degrees)
      * (+ degrees ClockWise, - degrees Counter-Clockwise)
      *  @param1: Drive Rotate PID Enable Select (boolean)	
      *  @param2: Drive Rotate Enable Select (boolean)	
      *  @param3: Drive System Desired Encoder Speed Target (rpm: double) */	
    public void putPIDRotPstnTgt(boolean RotSysEnbl,
    		                     boolean RotClckWise,
    		                     double  RotPstnTgt) {
    	PIDRotEnbl = RotSysEnbl;
    	DirtcnIsClckWise = RotClckWise;
    	PstnDsrd = RotPstnTgt;
    }  
    
    /** Method: getPIDRotCmnd - Interface to Get the Drive System Motor 
	  * Normalized Power Command for the Robot Rotate Control (-1 to 1).
      *  @return: Robot Rotate Motor Driver Normalized Power Command (-1 to 1: double) */	
    public double getPIDRotCmnd() {
    	return PIDCmndNorm;
    }

    /** Method: getPIDRotTgtCondMet - Interface to Get the Robot Rotate PI
      * Control System Target Acquired Condition Complete Indication. (boolean)
      *  @return: Robot Rotate Target Condition Complete (boolen) */	
    public boolean getPIDRotTgtCondMet() {
    	return PIDRotCondCmplt;
    }

    
    /** Method: resetPIDRot - Interface to Reset the Drive System
	  * Rotate PID Controller, i.e. initialize PID variables.  */ 
    public void resetPIDRot() {
        resetPIDCntrlr();
    }

    
    /** Method: putPIDDrvPstnTgt - Interface to Set the Gyro Target
     * Position Angle for the Robot Rotate PID Controller. (degrees)
     * (+ degrees ClockWise, - degrees Counter-Clockwise)
     *  @param1: Drive Course Heading PID Enable Select (boolean)	
     *  @param2: Drive Course Heading Angle Target (degree: double) */	
    public void putPIDDrvPstnTgt(boolean DrvSysEnbl,
   		                         double  DrvPstnTgt) {
    PIDDrvEnbl = DrvSysEnbl;
   	DirtcnIsClckWise = true;
   	PstnDsrd = DrvPstnTgt;
    }  

   
    /** Method: getPIDDrvCorrCmnd - Interface to Get the Drive System Motor 
	  * Normalized Power Command for the Robot Course Heading Correction Control (-1 to 1).
      *  @return: Robot Course Heading Correction Motor Driver Normalized Power Command (-1 to 1: double) */	
    public double getPIDDrvCorrCmnd() {
   	   return PIDCmndNorm;
    }

    // Below is for SmartDash Display   

    public double getPIDRotPstnAct() {
    	return PstnAct;
    }

    public double getPIDRotPstnErr() {
    	return PstnErr;
    }

    public double getPIDRotErrAccum() {
    	return PstnErrAccum;
    }

    public double getPIDRotFdFwdTerm() {
    	return FdFwdCorr;
    }    
    
    public double getPIDRotPropTerm() {
    	return PropCorr;
    }

    public double getPIDRotIntglTerm() {
    	return IntglCorr;
    }
    
    public double getPIDRotPIDCmndPct() {
    	return PIDCmndPct;
    }
  
    // Above is for SmartDash Display   
    
    
    /******************************************************/
    /* Manage Rotate PID Control Subsystem Scheduler Task  */
    /******************************************************/
	
    /** Method: managePIDRotate - Scheduler Function for the Periodic
     * Drive System Rotate Control PI Control System.  */ 
   public void managePIDRotate() {

	   PstnAct = Robot.kSENSORS.getGyroAngle();
	   
   	if (PIDRotEnbl == true)
   	    {
   	    PstnErr = calcErrSignal(DirtcnIsClckWise, PstnDsrd, PstnAct,
   	    		                K_PIDCal.KROT_Deg_PosErrDB);
   	    PstnErrWithInDB = dtrmnErrInDB(PstnErr, K_PIDCal.KROT_Deg_PosErrDB); 
   	    PstnErrAccum = calcErrAccum(PstnErrAccum, PstnErr, K_PIDCal.KROT_Deg_IntglErrDsblMin);
   	    FdFwdCorr = calcFdFwdTerm(PstnErr);
   	    PropCorr = calcPropTerm(PstnErr, K_PIDCal.KROT_K_PropGx,
   	    		                K_PIDCal.KROT_Pct_PropCorrMax);
   	    IntglCorr = calcIntglTerm(PstnErrAccum, K_PIDCal.KROT_K_IntglGx,
   	    		                  K_PIDCal.KROT_Pct_IntglCorrMax);
   	    PIDCmndPct = calcPIDTotCorr(DirtcnIsClckWise, FdFwdCorr, PropCorr, IntglCorr);
   	    PIDCmndNorm = PIDCmndPct/100;
   	    PIDRotCondCmplt = dtrmnTgtCondMet(PstnErrWithInDB, TgtCondTmr,
   	    		                          K_PIDCal.KROT_t_PstnTgtSyncMetThrsh);
   	    }
   	else  // (PIDEnbl == false)
   	    {
   	    resetPIDCntrlr();  
   	    }
   } 	

   
   /******************************************************/
   /* Manage Drive PID Control Subsystem Scheduler Task  */
   /******************************************************/
	
   /** Method: managePIDDrive - Scheduler Function for the Periodic
    * Drive System Course Heading Control PI Control System.  */ 
  public void managePIDDrive() {

	   PstnAct = Robot.kSENSORS.getGyroAngle();
	   
  	if (PIDDrvEnbl == true)
  	    {
   	    PstnErr = calcErrSignal(DirtcnIsClckWise, PstnDsrd, PstnAct,
   	    		                K_PIDCal.KDRV_Deg_PosErrDB);
   	    PstnErrWithInDB = dtrmnErrInDB(PstnErr, K_PIDCal.KDRV_Deg_PosErrDB); 
   	    PstnErrAccum = calcErrAccum(PstnErrAccum, PstnErr, K_PIDCal.KDRV_Deg_IntglErrDsblMin);
   	    FdFwdCorr = 0.0;
   	    PropCorr = calcPropTerm(PstnErr, K_PIDCal.KDRV_K_PropGx,
   	    		                K_PIDCal.KDRV_Pct_PropCorrMax);
   	    IntglCorr = calcIntglTerm(PstnErrAccum, K_PIDCal.KDRV_K_IntglGx,
   	    		                  K_PIDCal.KDRV_Pct_IntglCorrMax);
   	    PIDCmndPct = calcPIDTotCorr(DirtcnIsClckWise, FdFwdCorr, PropCorr, IntglCorr);
   	    PIDCmndNorm = PIDCmndPct/100;
   	    PIDDrvOnTgtTm = dtrmnDrvOnCourse(PstnErrWithInDB, TgtCondTmr);
   	    if (PIDDrvOnTgtTm > PIDDrvOnTgtTmMax)
   	        {
   	    	PIDDrvOnTgtTmMax = PIDDrvOnTgtTm;
   	        }
  	    }
  	else  // (PIDDrvEnbl == false)
  	    {
  	    resetPIDCntrlr();  
  	    }
    }
   
   
    /**********************************************/
    /* Internal Class Methods                     */
    /**********************************************/
   
    /** Method: calcErrSgnl - Calculates Position Error taking
      * into account a symmetrical error dead-band around the
      * zero-error point.
      * @param1: DirctnIsCW - Desired Rotation is ClockWise (boolean)
      * @param2: SetPoint - Controller Target Set Point Value (double)
      * @param3: ProcessVal - Controller Actual Feedback Process Value (double)
      * @param4: ThrshDB - Dead-Band Threshold (float)
      * @return: ErrSignal - Dead-Band Adjusted Error Value (double)  */
     private double calcErrSignal(boolean DirctnIsCW,
    		                      double  SetPoint,
    		                      double  ProcessVal,
    		                      float   ThrshDB) {
    	float ErrSignal;
    	
    	ErrSignal = (float)(SetPoint - ProcessVal);
        if (DirctnIsCW == false) {
        	// Direction Counter-ClockWise, Flip the Sign of the Error Signal
        	ErrSignal = -(ErrSignal);
        }
    	
    	if (ErrSignal >= 0.0) {
    		if (ErrSignal > ThrshDB)  {
    			ErrSignal = ErrSignal - ThrshDB;    			
    		} else {
    			ErrSignal = (float)0.0;
    	    }
    	}
    	else {  // (ErrSignal < 0.0)  
    		if (ErrSignal < -(ThrshDB)) {
    			ErrSignal = ErrSignal + ThrshDB;
    		} else {
    			ErrSignal = (float)0.0; 		
    	    }
    	}
    	
    	return (double)ErrSignal;
     } 


    /** Method: dtrmnErrInDB - Determines if Controller Error
      * is within the targeted Dead-Band.  Used when when rotating
      * the robot to execute a turn when determining if the
      * desired target position is being attained.
      * @param1: Controller Target Set Point Value (double)
      * @param2: Controller Actual Feedback Process Value (double)
      * @return: Dead-Band Adjusted Error Value (double)  */
     private boolean dtrmnErrInDB(double ErrSignal,
    		                      float  ThrshDB) {
    	boolean ErrInDB = false;
    	
    	if ((ErrSignal >= 0.0) && (ErrSignal <= ThrshDB))
    			ErrInDB = true;
    	else if ((ErrSignal < 0.0) && (ErrSignal >= -ThrshDB))
				ErrInDB = true;
    	
    	return ErrInDB;
     }
    
    
    /** Method: calcErrAccum - Calculate the error accumulation
      * signal for use by the Integral Controller.
      * @param1: Controller Accumulated Error Signal (double)
      * @param2: Controller Error Signal (double)
      * @param3: Min Error Signal Thresh Aboge which Error will not
      *          be accumulated.
      * @return: Updated Controller Accumulated Error Signal (double)  */
     private double calcErrAccum(double ErrAccum,
    		                     double ErrSignal,
    		                     float ErrDsblThrshMin) {
    	 double  ErrAccumTemp;
    	 double  ErrSignalAbs;
    	 boolean SignFlipRst = false;

         ErrSignalAbs = Math.abs(ErrSignal);
    	
  	     if ((ErrAccum > 0) && (ErrSignal < 0)) {
  	         SignFlipRst = true;
  	     } else if ((ErrAccum < 0) && (ErrSignal > 0)) {
  	         SignFlipRst = true;
  	     }
        
  	     if(SignFlipRst == true) {
  	         ErrAccumTemp = (double)0.0;  
  	     } else if (ErrSignalAbs >= (double)ErrDsblThrshMin) {
  	    	 ErrAccumTemp = ErrAccum;
  	     } else {
  	         // (SignFlipRst == false) 
  	         ErrAccumTemp = ErrAccum + ErrSignal;  
         }	    
   	     return ErrAccumTemp;  	  
     }

     
     /** Method: calcFdFwdTerm - Calculate the Feed-Forward
      * Correction Term.
      * @return: Feed-Forward Correction Term (double)  */
     private double calcFdFwdTerm(double ErrSignal) {
     	 float ErrAxis;   // scalar
    	 double FF_Corr;  // percent power
     	
     	ErrAxis = Robot.kTBLLOOKUP.AxisPieceWiseLinear_int((float)ErrSignal,
     			                                           K_PIDCal.KROT_Deg_FdFwdErrAxis,
     			                                           (int)10);
     	
     	FF_Corr = Robot.kTBLLOOKUP.XY_Lookup_flt(K_PIDCal.KROT_Pct_FdFwdCorr,
     			                                 ErrAxis,
     		   	                                 (int)10);
     	
     	if (FF_Corr < 0.0) {
     		FF_Corr = 0.0;
     	} else if (FF_Corr > 100.0) {
     		FF_Corr = 100.0;;
     	}

     	return FF_Corr;
     }
     
	
    /** Method: calcPropTerm - Calculate and Limit
      * the Controller Proportional Correction Term.
      * @param1: Controller Error Signal (double)
      * @param2: Controller Proportional Gain (double)
      * @param3: Proportional Correction Maximum Limit (double)
      * @return: Proportional Correction Term (double)  */
     private double calcPropTerm(double ErrSignal,
    		                     float  PropGx,
                                 float  CorrLimMax) {
     	double P_Corr;  // percent power
     	
     	P_Corr = (double)PropGx * ErrSignal;
     	
     	if (P_Corr > (double)CorrLimMax) {
     		P_Corr = (double)CorrLimMax;
     	}
     	else if (P_Corr < (double)(-CorrLimMax)) {
     		P_Corr = (double)(-CorrLimMax);
     	}
     	return P_Corr;
     }

     
     /** Method: calcIntglTerm - Calculate and Limit
       * the Controller Integral Correction Term.
       * @param1: Controller Accumulated Error Signal (double)
       * @param2: Controller Integral Gain (double)
       * @param3: Proportional Correction Maximum Limit (double)
       * @return: Integral Correction Term (double)  */
     private double calcIntglTerm(double ErrAccum,
    		                      float  IntglGx,
    		                      float  CorrLimMax) {
     	double I_Corr;     	
     	
     	I_Corr = (double)IntglGx * ErrAccum;
     	     	
    	if (I_Corr > CorrLimMax)
    		    I_Corr = CorrLimMax;

    	else if (I_Corr < -CorrLimMax)
                I_Corr = -CorrLimMax;

     	return I_Corr;
     }

     
     /** Method: calcPIDTotCorr - Calculate Total Proportional
       * - Integral Controller Correction.
       * @param1: Desired Rotation is ClockWise (boolean)
       * @param2: Feed-Forward Correction Term - Percent Correction (double)
       * @param3: Proportional Correction Term - Percent Correction (double)
       * @param4: Integral Correction Term - Percent Correction (double)
       * @return: Total P-I Correction Term - Percent Correction (double)  */
     private double calcPIDTotCorr(boolean DirctnIsCW,
    		                       double  FdFwdTerm,
    		                       double  PropTerm,
    		                       double  IntglTerm) {
     	double CmndPct; // %
     	
     	CmndPct = FdFwdTerm + PropTerm + IntglTerm;
 	    
 	    if (CmndPct > 100) {
 	    	CmndPct = 100;
 	    }
 	    else if (CmndPct < 0) {
 	    	CmndPct = 0;
 	    }
 	    
 	    if (DirctnIsCW == false) {
 	    	// Desired Direction Counter-ClockWise, Flip Sign of Power Command
 	    	CmndPct = -(CmndPct);
 	    }
 	    return CmndPct;
     }
     

     /** Method: dtrmnTgtCondMet - Determine that the Target Condition
       * has been met for the minimum amount of time to be considered
       * satisfied. 
       * @param1: Indication of basic target condition has been met (boolean)
       * @param2: Condition Timer (double)
       * @param3: Target Condition Time Threshold (float)
       * @return: Indication that target condition time threshold has been met (boolean)  */
     private boolean dtrmnTgtCondMet(boolean CondMet,
    		                         Timer   CondTmr,
    		                         float   CondMetThrsh) {
    	 boolean TgtCondCmptd = false;
    	
    	 if (CondMet == false)  { // Error outside Target DB
    		 CondTmr.reset(); 
    	 }
    	 else {
    		 /* Do Nothing - Inside Target DB - Free-Running Timer */
    	 }

    	 
    	 if (CondTmr.get() >= CondMetThrsh) {
    		 TgtCondCmptd = true; 
    	 }
    	 
    	 return TgtCondCmptd;
     }

     
     /** Method: dtrmnTmeOnCourse - Determine the amount of time that the Drive
       * Course Heading System has been on course. 
       * @param1: Indication of basic target condition has been met (boolean)
       * @param2: Condition Timer (double)
       * @return: Indication that target condition time threshold has been met (boolean)  */
    private double dtrmnDrvOnCourse(boolean CondMet,
   		                            Timer   CondTmr) {
   	
        double OnTgtTm;	
    	
   	    if (CondMet == false) { // Error outside Target DB
   		     CondTmr.reset(); 
   	    }
   	    else  {
   		     /* Do Nothing - Insided Target DB - Free-Running Timer */
   	    }
   	    	
   	    OnTgtTm = CondTmr.get();
   	 
   	    return OnTgtTm;
    }
     
     
     
     /** Method: resetPIDCntrlr - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void resetPIDCntrlr() {
    	 PstnAct = 0.0;
    	 PstnDsrd = 0.0;
    	 TgtCondTmr.reset();
         PstnErr = 0.0;
         PstnErrWithInDB = false;
         PstnErrAccum = 0.0;
         FdFwdCorr = 0.0;
   	     PropCorr = 0.0;
   	     IntglCorr = 0.0;
   	     PIDCmndPct =  0.0;
   	     PIDCmndNorm =  0.0;
   	     PIDRotCondCmplt = false;
   	     PIDDrvOnTgtTm = 0.0;
   	     PIDDrvOnTgtTmMax = 0.0;
     }
     
     
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

