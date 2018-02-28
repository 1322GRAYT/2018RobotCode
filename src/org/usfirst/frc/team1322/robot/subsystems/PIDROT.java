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
public class PIDROT extends Subsystem {
	private Timer timer = new Timer();
	
	// Variable Declarations
	boolean EnblRqst;          // (boolean)
	boolean PIDRotEnbl;        // (boolean)
	boolean DirtcnIsClckWise;  // (boolean)
	boolean PstnErrWithInDB;   // (boolean)
	boolean PIDRotCondCmplt;   // (boolean)
	double PosActRaw;          // (degrees)
	double PosAct;             // (degrees)
    double PosDsrd;	           // (degrees)
    double PosErr;             // (degrees)
    double PosErrAccum;        // (degrees)
    double PropCorr;           // (percent)
    double IntglCorr;          // (percent)
    double PIDCmndPct;         // (percent power)
    double PIDRotCmnd;         // (normalized power)
    
    
    /**********************************************/
    /* Public Interface Definitions        */
    /**********************************************/

	
	
    /******************************************************/
    /* Manage Drive PID Control Subsystem Scheduler Task  */
    /******************************************************/
	
    /** Method: managePIDRotate - Scheduler Function for the Periodic
     * Drive System Rotate Control PI Control System.  */ 
   public void managePIDRotate() {

	dtrmnPIDRotEnbl();
	dtrmnRotDirctn();
	calcPosAct();
	   
   	if (PIDRotEnbl == true)
   	    {
   		calcPosErr();
   		calcPosErrAccum();
   		calcPropTerm();
   		calcIntglTerm();
   		calcPIDTotCorr();
   		dtrmnPIDRotTgtMet();
   		
   	    }
   	else  // (PIDEnbl == false)
   	    {
   	    resetPIDCntrlr();  
   	    }
   	
   } 	
    
    
    /**********************************************/
    /* Internal Class Methods                     */
    /**********************************************/
	

   /** Method: dtrmnPIDRotEnbl - YadaYada
    *  @param:  input info	(units)
    *  @return: output info (units) */	
   private void dtrmnPIDRotEnbl() {
	   boolean EnblTemp = false;
	   
	   if ((EnblRqst == true) && (PosDsrd != 0)) {
		   EnblTemp = true;
	   }
	   
       PIDRotEnbl = EnblTemp;
   }

   
   /** Method: dtrmnRotDirctn - YadaYada
    *  @param:  input info	(units)
    *  @return: output info (units) */	
   private void dtrmnRotDirctn() {
	   if (PosDsrd < 0) {
           // Turn Counter-ClockWise
		   DirtcnIsClckWise = false;	   
	   }
	   else {  /* PosDsrd >= 0) */
		   // Turn Clockwise
	       DirtcnIsClckWise = true;
	   }
   }   

   
   /** Method: calcPosAct - YadaYada
    *  @param:  input info	(units)
    *  @return: output info (units) */	
   private void calcPosAct() {
   	double PosActTemp;
   	
   	PosActRaw = Robot.kSENSORS.getGyroAngle();    	
   	if (DirtcnIsClckWise = false) {
   		// Turn Counter-ClockWise
   		PosActTemp = ((float)360) - PosActRaw;
   	}
   	else /* (DirtcnIsClckWise = true)  */ {
   		// Turn ClockWise
   		PosActTemp = PosActRaw;    		
   	}
   	
   	PosAct = PosActTemp;
   	
     } 
   
   
    /** Method: calcPosErr - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void calcPosErr() {
    	float ErrTemp;
    	boolean ErrInDB = false;
    	
    	ErrTemp = (float)(PosDsrd - PosAct);
    	
    	if (ErrTemp >= 0.0) {
    		// PosErr is Positive
    		if (ErrTemp >= K_PIDCal.KROT_Deg_PosErrDB)
    		    {
    		    ErrTemp = ErrTemp - K_PIDCal.KROT_Deg_PosErrDB;    			
    		    }
    		else
    	        {
    			ErrTemp = (float)0.0;
    			ErrInDB = true;
    	        }
    	}
    	else /* (ErrTemp < 0.0) */ {
    		// PosErr is Negative
    		if (ErrTemp <= -(K_PIDCal.KROT_Deg_PosErrDB))      // Less than the -DB
			    {
			    ErrTemp = ErrTemp + K_PIDCal.KROT_Deg_PosErrDB; // -Err - (-DB)   			
			    }
		else
		        {
				ErrTemp = (float)0.0;
				ErrInDB = true;
		        }    		
    	}
    	
    	PosErr = (double)ErrTemp;
    	PstnErrWithInDB = ErrInDB;
      }

    
    /** Method: calcPosErrAccum - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void calcPosErrAccum() {
    	double ErrAccumTemp;
    	
    	ErrAccumTemp = PosErrAccum + PosErr;
    	
    	if (ErrAccumTemp > K_PIDCal.KROT_Pct_IntglCorrMax) {
    		ErrAccumTemp = K_PIDCal.KROT_Pct_IntglCorrMax;
    	}
    	else if (ErrAccumTemp < -(K_PIDCal.KROT_Pct_IntglCorrMax)) {
    		ErrAccumTemp = -(K_PIDCal.KROT_Pct_IntglCorrMax);
    	}
    		
     	PosErrAccum = ErrAccumTemp;  
    }

	
    /** Method: calcPropTerm - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
     private void calcPropTerm() {
     	double P_Corr;   // P_Corr
     	double CorrLimMin;  // percent power
     	double CorrLimMax;  // percent power
     	
     	CorrLimMin = (double)K_PIDCal.KROT_Pct_PropCorrMin;
     	CorrLimMax = (double)K_PIDCal.KROT_Pct_PropCorrMax;
     	
     	P_Corr = K_PIDCal.KROT_K_PropGx * PosErr;
     	
     	if (P_Corr < CorrLimMin) P_Corr = CorrLimMin;
     	else if (P_Corr > CorrLimMax) P_Corr = CorrLimMax;
     	    	
     	PropCorr = P_Corr;
     }

     
     /** Method: calcIntglTerm - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void calcIntglTerm() {
     	double I_Corr;     	
     	
     	I_Corr = K_PIDCal.KROT_K_IntglGx * PosErrAccum;
     	     	
     	IntglCorr = I_Corr;
     }

     
     /** Method: calcPIDTotCorr - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void calcPIDTotCorr() {
     	double CmndPct; // %
     	
     	CmndPct = PropCorr + IntglCorr;
 	    
 	    if (CmndPct > 100) CmndPct = 100;
 	    else if (CmndPct < 0) CmndPct = 0;
 	    
 	    if (DirtcnIsClckWise == false) CmndPct = -CmndPct;  // Desired Target Turn is Counter-ClockWise
 	
 	    PIDCmndPct = CmndPct;
 	    PIDRotCmnd= CmndPct/100;
     }
     

     /** Method: dtrmnPIDRotTgtMet - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void dtrmnPIDRotTgtMet() {
    	
    	 if (PstnErrWithInDB == false) {  // Error outside Target DB
    		timer.reset(); 
    	 } 
    	 else  {
    		 /* Do Nothing - Insided Target DB - Free-Running Timer */
    	 }

    	 if (timer.get() >= K_PIDCal.KROT_t_PstnTgtSyncMetThrsh) {
    		 PIDRotCondCmplt = true; 
    	 }
    	 else {
    		 PIDRotCondCmplt = false;  
    	 } 
     }
     
     
     /** Method: resetPIDCntrlr - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void resetPIDCntrlr() {
    	timer.reset();
    	PosDsrd = 0.0;	 
        PosErr = 0.0;
        PstnErrWithInDB = false;
        PosErrAccum = 0.0;
   	    PropCorr = 0.0;
   	    IntglCorr = 0.0;
   	    PIDCmndPct =  0.0;
   	    PIDRotCmnd =  0.0;
   	    PIDRotCondCmplt = false;
     }
     

     
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

