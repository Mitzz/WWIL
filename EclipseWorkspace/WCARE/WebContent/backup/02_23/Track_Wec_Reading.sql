create or replace TRIGGER Track_Wec_Reading Before
      INSERT OR
      UPDATE OF N_Value, N_Actual_Value, S_Remarks OR
      DELETE ON TBL_WEC_READING FOR EACH Row 
      DECLARE 
      V_New_Value VARCHAR2(20);
      v_Old_value VARCHAR2(20);
      V_Activity_Type  VARCHAR2(20);
      V_Modified_by tbl_wec_reading.S_Last_Modified_by%type;
      
      V_value varchar2(20);
      V_Wec_Id  tbl_wec_master.S_wec_ID%type;
      V_Reading_date  tbl_wec_reading.D_reading_date%type;
      V_Mp_Id  tbl_wec_reading.S_mp_id%type;
      V_Remark tbl_wec_reading.S_remarks%type := 'NA';
      V_WS number;
      IS_MA boolean := false;
      V_MA number(10, 2);
      IS_GEA boolean := false;
      V_GEA number;
      IS_RAV boolean := false;
      V_RAV number;
      IS_MIA boolean := false;
      V_MIA number;
      IS_GA boolean := false;
      V_GA number;
      IS_GIA boolean := false;
      V_GIA number;
      IS_SA boolean := false;
      V_SA number;
      IS_Lull boolean := false;
      V_Lull number;
      IS_CF boolean := false;
      V_CF number;
      BEGIN
            --Dbms_Output.Put_Line('Track_Wec_Reading Invoked');
            IF Inserting THEN
                  --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                  IF(:New.S_Mp_Id = '0808000023' OR :New.S_Mp_Id = '0808000022') THEN
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        V_New_Value := :New.N_Value || ', ' || :New.N_Actual_Value;
                        
--                        If(:New.S_CREATED_BY = 'SYSTEM')then                                       
--                          V_WS :=  param_calculator.ws(:new.S_wec_id, :new.D_reading_date);
--                          reading_summary.insert_ws(V_WS, :new.S_wec_id, :new.D_reading_date );                     
--                        end if;
                  ELSE
                  		
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        V_New_Value := :New.N_Actual_Value;
                        
                  END IF;
                  
                  V_old_value := 'NA';
                  V_value := V_new_value;
                  V_wec_id := :new.S_wec_id;
                  V_mp_id := :new.S_mp_id;
                  V_reading_date := :new.D_reading_date;
                  V_Activity_Type  := 'Insert';
                  V_Modified_by := :new.S_created_by;
                   
                   if(:new.S_remarks is not null) then
                        V_remark := :new.S_remarks;
                        reading_summary.insert_remark(V_wec_id, V_reading_date, V_remark);
                  end if;
                  
            END IF;
            IF Updating THEN
                  --Dbms_Output.Put_Line('Value Updated into Tbl_wec_reading');
                  IF(:New.S_Mp_Id = '0808000023' OR :New.S_Mp_Id = '0808000022') THEN
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        V_New_Value := :New.N_Value || ', ' || :New.N_Actual_Value;
                        V_Old_Value := :Old.N_Value || ', ' || :Old.N_Actual_Value;
                  ELSE
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        
                        V_New_Value := :New.N_Actual_Value;
                        V_old_value := :old.N_Actual_Value;
                  END IF;
                  V_Value := V_new_value;
                  V_wec_id := :new.S_wec_id;
                  V_mp_id := :new.S_mp_id;
                  V_reading_date := :new.D_reading_date;
                  V_Activity_Type  := 'Update';
                  V_Modified_by := :new.S_created_by;
                  
                  if(:new.S_remarks is not null) then
                        V_remark := :new.S_remarks;
                        reading_summary.insert_remark(V_wec_id, V_reading_date, V_remark);
                  end if;
            END IF;
            
            IF Deleting THEN
                  --Dbms_Output.Put_Line('Value Deleted from Tbl_wec_reading');
                  IF(:old.S_Mp_Id = '0808000023' OR :old.S_Mp_Id = '0808000022') THEN
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        V_New_Value := 'NA';
                        V_Old_Value := :Old.N_Value || ', ' || :Old.N_Actual_Value;
                        V_Value := '0, 0';
                  ELSE
                  		
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        V_New_Value := 'NA';
                        V_old_value := :old.N_Actual_Value;
                        V_Value := '0';
                        
                  END IF;
--                  V_Value := V_old_value;
                  V_wec_id := :old.S_wec_id;
                  V_mp_id := :old.S_mp_id;
                  V_reading_date := :old.D_reading_date;
                  V_Activity_Type  := 'Delete';
                  V_Modified_by := :old.S_created_by;
                        
                        if(:old.S_remarks is not null) then
                              V_remark := 'NA';
                              reading_summary.insert_remark(V_wec_id, V_reading_date, V_remark);
                        end if;
            END IF;
            IF(V_New_Value <> V_Old_Value) THEN
                        INSERT
                        INTO  Tbl_Wec_Reading_Tracker
                              (
                                    D_Reading_Date, S_Wec_Id, S_Mp_Id, S_Activity_Name, T_Activity_Time,
                                    S_Old_Value, S_New_Value, S_Modified_By
                              )
                              VALUES
                              (
                                    V_Reading_date, V_WEC_ID, V_Mp_Id, v_activity_type, Localtimestamp,
                                    V_Old_Value, V_New_Value, v_modified_by
                              );
                        
                  END IF;
            reading_summary.insert_value(V_wec_id, V_reading_date, V_MP_ID, V_Value);
            if inserting then
                  IS_CF := true;  
                  IS_RAV := TRUE;
                  IS_MA := TRUE;
                  IS_MIA := TRUE;
                  IS_LULL := TRUE; 
                  IS_GIA := TRUE;
                  IS_GA := TRUE;
                  IS_GEA := TRUE;                                              
                  IS_SA := TRUE;  
--                  reading_summary.populate_true(V_mp_id, IS_MA, IS_RAV ,IS_LULL ,IS_MIA ,IS_GIA , IS_GA ,IS_GEA ,IS_SA, IS_CF);
            else
                  reading_summary.populate_true(V_mp_id, IS_MA, IS_RAV ,IS_LULL ,IS_MIA ,IS_GIA , IS_GA ,IS_GEA ,IS_SA, IS_CF);
            end if;
            If(IS_MA) then               
                         V_MA := param_calculator.ma(V_wec_id, V_reading_date);
                         reading_summary.insert_ma( V_MA ,V_wec_id, V_reading_date);
            End if;
            IF(IS_GA) then               
                         V_GA := param_calculator.GA(V_wec_id, V_reading_date);
                         reading_summary.insert_GA( V_GA ,V_wec_id, V_reading_date);		
            End if;
            IF(IS_GEA) then               
                         V_GEA := param_calculator.GEA(V_wec_id, V_reading_date);
                         reading_summary.insert_GEA( V_GEA ,V_wec_id, V_reading_date);	
            End if;
            IF(IS_RAV) then               
                         V_RAV := param_calculator.RA(V_wec_id, V_reading_date);
                         reading_summary.insert_RAV( V_RAV ,V_wec_id, V_reading_date);		
            End if;
            IF(IS_SA) then               
                         V_SA := param_calculator.SA(V_wec_id, V_reading_date);
                         reading_summary.insert_SA( V_SA ,V_wec_id, V_reading_date);
            End if;
            IF(IS_MIA) then               
                         V_MIA := param_calculator.MIA(V_wec_id, V_reading_date);
                               reading_summary.insert_mia( V_MIA ,V_wec_id, V_reading_date);	
            End if;
            IF(IS_GIA) then               
                         V_GIA := param_calculator.GIA(V_wec_id, V_reading_date);
                         reading_summary.insert_Gia( V_GIA ,V_wec_id, V_reading_date);
            End if;
           
            IF(IS_LULL) then               
                         V_LULL := param_calculator.Lull_hour(V_wec_id, V_reading_date);
                         reading_summary.insert_lull( V_Lull ,V_wec_id, V_reading_date);			 
            end if;
            IF(IS_CF) then               
                         V_CF := param_calculator.cf(V_wec_id, V_reading_date);
                         reading_summary.insert_cf( V_CF,V_wec_id, V_reading_date);
            end if;
      END;