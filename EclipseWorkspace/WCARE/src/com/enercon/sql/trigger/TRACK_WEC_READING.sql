create or replace TRIGGER Track_Wec_Reading Before
      INSERT OR
      UPDATE OF N_Value, N_Actual_Value, S_Remarks OR
      DELETE ON TBL_WEC_READING FOR EACH Row 
      DECLARE 
      V_New_Value VARCHAR2(20);
      v_Old_value VARCHAR2(20);
      V_value varchar2(20);
      V_Wec_Id  tbl_wec_master.S_wec_ID%type;
      V_Reading_date  tbl_wec_reading.D_reading_date%type;
      V_Mp_Id  tbl_wec_reading.S_mp_id%type;
      V_Remark tbl_wec_reading.S_remarks%type := 'NA';
      IS_MA boolean := false;
      V_MA number;
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
      BEGIN
            --Dbms_Output.Put_Line('Track_Wec_Reading Invoked');
            IF Inserting THEN
                  --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                  IF(:New.S_Mp_Id = '0808000023' OR :New.S_Mp_Id = '0808000022') THEN
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        V_New_Value := :New.N_Value || ', ' || :New.N_Actual_Value;
                        
                  ELSE
                  		reading_summary.populate_true(:New.S_mp_id, IS_MA, IS_RAV ,IS_LULL ,IS_MIA ,IS_GIA , IS_GA ,IS_GEA );
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        V_New_Value := :New.N_Actual_Value;
                        
                  END IF;
                  
                  V_old_value := 'NA';
                  V_value := V_new_value;
                  V_wec_id := :new.S_wec_id;
                  V_mp_id := :new.S_mp_id;
                  V_reading_date := :new.D_reading_date;
                  
                  INSERT
                  INTO  Tbl_Wec_Reading_Tracker
                        (
                              D_Reading_Date, S_Wec_Id, S_Mp_Id, S_Activity_Name,  T_Activity_Time,
                              S_Old_Value, S_New_Value, S_Modified_By
                        )
                        VALUES
                        (
                              :New.D_Reading_Date, :New.S_Wec_Id, :New.S_Mp_Id, 'Insert', Localtimestamp,
                              V_old_value, V_new_value, :new.S_created_by
                        );
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
                        reading_summary.populate_true(:New.S_mp_id, IS_MA, IS_RAV ,IS_LULL ,IS_MIA ,IS_GIA , IS_GA ,IS_GEA );
                        V_New_Value := :New.N_Actual_Value;
                        V_old_value := :old.N_Actual_Value;
                  END IF;
                  V_Value := V_new_value;
                  V_wec_id := :new.S_wec_id;
                  V_mp_id := :new.S_mp_id;
                  V_reading_date := :new.D_reading_date;
                  IF(V_New_Value <> V_Old_Value) THEN
                        INSERT
                        INTO  Tbl_Wec_Reading_Tracker
                              (
                                    D_Reading_Date, S_Wec_Id, S_Mp_Id, S_Activity_Name, T_Activity_Time,
                                    S_Old_Value, S_New_Value, S_Modified_By
                              )
                              VALUES
                              (
                                    :New.D_Reading_Date, :New.S_Wec_Id, :New.S_Mp_Id, 'Update', Localtimestamp,
                                    V_Old_Value, V_New_Value, :New.S_Created_By
                              );
                        
                  END IF;
                  
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
                  		reading_summary.populate_true(:old.S_mp_id, IS_MA, IS_RAV ,IS_LULL ,IS_MIA ,IS_GIA , IS_GA ,IS_GEA );
                        --Dbms_Output.Put_Line('Value Inserted into Tbl_wec_reading');
                        V_New_Value := 'NA';
                        V_old_value := :old.N_Actual_Value;
                        V_Value := '0';
                  END IF;
--                  V_Value := V_old_value;
                  V_wec_id := :old.S_wec_id;
                  V_mp_id := :old.S_mp_id;
                  V_reading_date := :old.D_reading_date;
                  INSERT
                  INTO  Tbl_Wec_Reading_Tracker
                        (
                              D_Reading_Date, S_Wec_Id, S_Mp_Id, S_Activity_Name, T_Activity_Time,
                              S_Old_Value, S_New_Value, S_Modified_By
                        )
                        VALUES
                        (
                              :old.D_Reading_Date, :old.S_Wec_Id, :old.S_Mp_Id, 'Delete', Localtimestamp,
                              V_old_value, V_new_value, :old.S_created_by
                        );
                        
                        if(:old.S_remarks is not null) then
                              V_remark := 'NA';
                              reading_summary.insert_remark(V_wec_id, V_reading_date, V_remark);
                        end if;
            END IF;
            reading_summary.insert_value(V_wec_id, V_reading_date, V_MP_ID, V_Value);
      END;