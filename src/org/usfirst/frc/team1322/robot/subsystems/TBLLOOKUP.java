package org.usfirst.frc.team1322.robot.subsystems;

public class TBLLOOKUP {
    public static final int MIN_INT =  0x8000;
    public static final int MAX_INT =  0x7FFF;	

    
	/** Method: AxisPieceWiseLinear_int - This function will return an rescaled
	  * axis index value from a Piece-Wise Linear tunable axis of type Integer.
	  * @param1: Axis Input Value in engineering units (float)
	  * @param2: Axis Array Object Reference (reference to array of ints)
	  * @param3: Table Size (int)
	  * @return: Rescaled Axis Output value in a normalized index value (float) */
	float AxisPieceWiseLinear_int(float  InpVal,
	                              int [] AxisArray,
	                              int    TblSize)
	  {
	  int    InpValInt;
      int    TblSegs;
	  int    ArrayIdx;
	  float  InterpFrac;
	  float  AxisOutVal;

	  if (TblSize > 2) TblSegs = TblSize - 1;
	  else TblSegs = 1;

	  // Convert input value from float to int
	  if (InpVal <= (float)MIN_INT)
	    {
		InpValInt = MIN_INT;
	    }
	  else if (InpVal >= (float)MAX_INT)
	    {
		InpValInt = MAX_INT;
	    }
	  else
	    {
		InpValInt = (int)InpVal;
	    }

      /* determine where the input value is in the array based on the calibrated
       * cell values, and linearly interpolate between cells if necessary. */
	  if (InpValInt <= AxisArray[0])
	      {
		  AxisOutVal = (float)0.0;
	      }
	  else if (InpValInt >= AxisArray[TblSegs])
	      {
		  AxisOutVal = (float)1.0;
	      }
	  else
	      {
	      for (ArrayIdx = 1;
	    	   InpValInt >= AxisArray[ArrayIdx];
	    	   ArrayIdx++)
	        {}

	      ArrayIdx--;

	      InterpFrac = InterpCoefFrac(InpVal,
	                                  ((float)(AxisArray[ArrayIdx])),
	                                  ((float)(AxisArray[ArrayIdx + 1])));

	      AxisOutVal = ((float)ArrayIdx + InterpFrac) / (float)TblSegs;
	      }

	  return AxisOutVal;
	  }

	
	/** Method: AxisPieceWiseLinear_flt - This function will return an rescaled
	  * axis index value from a Piece-Wise Linear tunable axis of type float.
	  * @param1: Axis Input Value in engineering units (float)
	  * @param2: Axis Array Object Reference (reference to array of floats)
	  * @param3: Table Size (int)
	  * @return: Rescaled Axis Output value in a normalized index value (float) */	
	public static float AxisPieceWiseLinear_flt(float  InpVal,
			                                    float [] AxisArray,
			                                    int    TblSize)
	  {
      int    TblSegs;
	  int    ArrayIdx;
	  float  InterpFrac;
	  float  AxisOutVal;

	  if (TblSize > 2) TblSegs = TblSize - 1;
	  else TblSegs = 1;

	  if (InpVal <= AxisArray[0])
	    {
		AxisOutVal = (float)0.0;
	    }
	  else if (InpVal >= AxisArray[TblSegs])
	    {
		AxisOutVal = (float)1.0;
	    }
	  else
	    {
	    for (ArrayIdx = 1;
	    	 InpVal >= AxisArray[ArrayIdx];
	    	 ArrayIdx++)
	    {}

	    ArrayIdx--;


	    InterpFrac = InterpCoefFrac(InpVal,
	    		                    AxisArray[ArrayIdx],
	    		                    AxisArray[ArrayIdx + 1]);

	    AxisOutVal = ((float)ArrayIdx + InterpFrac) / (float)TblSegs;
	    }

	  return AxisOutVal;
	  }

	
	/** Method: AxisLinear_flt - This function will return an interpolated
	 * linear scalar value between two axis limit values (LwrRef and UprRef).
     * @param1: Axis Input Value in engineering units (float)
     * @param2: Axis Lower Bound Reference Value in engineering units (float)
     * @param3: Axis Upper Bound Reference Value in engineering units (float)
	 * @return: Rescaled Axis Output value in a normalized index value (float) */	
	public static float AxisLinear_flt(float InpVal,
	                                   float LwrRef,
	                                   float UprRef)
	  {
	  float AxisOutVal;      

	  if (InpVal <= LwrRef)
	    {	  
		AxisOutVal = (float)0.0;
	    }	  
	  else if (InpVal >= UprRef)
        {
		AxisOutVal = (float)1.0;
        }
	  else
	    {
		AxisOutVal = InterpCoefFrac(InpVal, LwrRef, UprRef);
	    }

	  return AxisOutVal;
	  }
	
	
	/** Method: InterpCoefFrac - This function will return the Fractional
	 *  Interpolation Coefficient value of a floating point value between two 
	 *  floating point reference values.
     * @param1: Interpolation Input Value (float)
     * @param2: Lower Reference value to Interpolate Between (float)
     * @param3: Upper Reference value to Interpolate Between (float)
	 * @return: Fractional Interpolated Output Value (float) */	
	public static float InterpCoefFrac(float Inp,
	                                   float LwrRef,
	                                   float UprRef)
	  {
	  return ((Inp - LwrRef)/(UprRef - LwrRef));
	  }

	
	/** Method: InterpCoef - This function will return the Fractional
	 *  Interpolation Coefficient value of a floating point value between two 
	 *  floating point reference values.	
     * @param1: Interpolation Input Value (float)
     * @param2: Lower Reference value to Interpolate Between (float)
     * @param3: Upper Reference value to Interpolate Between (float)
	 * @return: Fractional Interpolated Output Value (float) */	
/* 	 (Inp, LwrRef, UprRev) -> ((Inp - LwrRef)/(UprRef - LwrRef)); */

	
	
	/** Method: XY_Lookup_flt - This function will return an interpolated
	 * table look-up value based on a rescaled normalized axis index value.
     * @param1: Look-Up Table Array Object Reference (reference to array of floats)
     * @param2: Normalized Axis Index Input value (float)
     * @param3: Table Size (int)
	 * @return: Table Look-Up Output value in engineering units (float) */	
	float XY_Lookup_flt(float [] TblArray,
	                    float  AxisInpIdx,
	                    int    TblSize)
	  {
	  int    TblSegs;
	  int    ArrayIdx;
	  float  InterpFrac;
	  float  TblOutVal;

	  if (TblSize > 2) TblSegs = TblSize - 1;
	  else TblSegs = 1;	  
	  
	  if (AxisInpIdx <= (float)0.0)
	      {
		  TblOutVal = TblArray[0];
	      }
	  else if (AxisInpIdx >= (float)1.0)
	      {
		  TblOutVal = TblArray[TblSegs];
	      }
	  else
	      {
	      /* ArrayIdx     = floor(AxisInpIdx * (LeSize - 1)) */
		  InterpFrac   = AxisInpIdx * (float)TblSegs;
		  ArrayIdx = (int)InterpFrac;

	      /* LeInterpFrac     = (LeTableIndex * (LeSize - 1)) - LeArrayIndex */
	      InterpFrac  -= (float)ArrayIdx;

	      /* LeY = LeLower + (LeInterpFrac * (LeUpper - LeLower)) */
	      TblOutVal = InterpCoefFrac(TblArray[ArrayIdx],
	    		                     TblArray[ArrayIdx + 1],
	    		                     InterpFrac);
	      }

	  return TblOutVal;
	  }

	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	
}
