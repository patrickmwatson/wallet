package com.lifehackinnovations.wallet;

import android.app.Activity;
import android.content.Context;

public class MasterDatabase extends Activity{
	
//Table name is variable name one table for every variable solves everything!!!	
	
	public static void DBsaveblob(String dbvariablename, byte[] blob, Context ctx){
		DbAdapter mDbHelper=new DbAdapter(ctx);
		mDbHelper.open();
		
		/////////////////////////////////////////////
		mDbHelper.set1Blob(dbvariablename, blob);
		/////////////////////////////////////////////
		
		mDbHelper.close();
	}
	
	
	
	public static byte[] DBfetchblob(String dbvariablename, Context ctx){
		DbAdapter mDbHelper=new DbAdapter(ctx);
		mDbHelper.open();
		
		///////////////////////////////////////////////
		byte[] blob=mDbHelper.fetch1Blob(dbvariablename);
		///////////////////////////////////////////////
		
		mDbHelper.close();
		return blob;
	}
	
	
	
	
	public static void DBsavestring(String dbvariablename, String Value, Context ctx){
		DbAdapter mDbHelper=new DbAdapter(ctx);
		mDbHelper.open();
		
		///////////////////////////////////////////////
		mDbHelper.set1(dbvariablename, Value);
		///////////////////////////////////////////////
		
		mDbHelper.close();
	}
	
	
	
	
	public static String DBfetchstring(String dbvariablename, Context ctx){
		DbAdapter mDbHelper=new DbAdapter(ctx);
		mDbHelper.open();
		
		///////////////////////////////////////////////
		String variable=mDbHelper.fetch1(dbvariablename);
		///////////////////////////////////////////////
		
		mDbHelper.close();
		return variable;
	}
	
}