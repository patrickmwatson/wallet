/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
//test
package com.lifehackinnovations.wallet;



import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple rows database access helper class. Defines the basic CRUD operations
 * for the rowpad example, and gives the ability to list all rows as well as
 * retrieve or modify a specific row.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class DbAdapter {
	
	public static final String COLUMN_ID = "_id";
    public static final String COLUMN_1 = "column1";
    public static final String COLUMN_2 = "column2";
    public static final String COLUMN_3 = "column3";
    public static final String COLUMN_4 = "column4";
    public static final String COLUMN_5 = "column5";
    public static final String COLUMN_6 = "column6";
    public static final String COLUMN_7 = "column7";
    public static final String COLUMN_8 = "column8";
    public static final String COLUMN_9 = "column9";
    
    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    /**
     * Database creation sql statement
     */
    private static final String TABLECREATESTRING(String tablename, int numberofcolumns){
		final String[] sequence = new String[numberofcolumns+1];
		String fullsequence="";
		for(int i=1; i<=numberofcolumns; i++){
			sequence[i]=",column"+String.valueOf(i)+" text not null ";
			fullsequence=fullsequence+sequence[i];
		}
    	String finalstring="create table "+tablename+" (_id integer  primary key autoincrement "+fullsequence+");";
    	return finalstring;
   
    }
    
    
    private static final String DATABASE_NAME = "data";
    
    
    private static final String TABLE1 = "chrono";
    private static final String TABLE2 = "wage";
    private static final String TABLE3 = "startdate";
    private static final String TABLE4 = "stopdate";
    private static final String TABLE5 = "bchrono";
    private static final String TABLE6 = "accumulatedbreak";
    private static final String TABLE7 = "breakstartdate";
    
    
    
    
    
    
    
    
    
    
    private static final String STATS_TABLE = "stats";
    private static final String SHIFT_TABLE = "shift";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	 db.execSQL(TABLECREATESTRING(TABLE1,1));
        	 db.execSQL(TABLECREATESTRING(TABLE2,1));
        	 db.execSQL(TABLECREATESTRING(TABLE3,1));
        	 db.execSQL(TABLECREATESTRING(TABLE4,1));
        	 db.execSQL(TABLECREATESTRING(TABLE5,1));
        	 db.execSQL(TABLECREATESTRING(TABLE6,1));
        	 db.execSQL(TABLECREATESTRING(TABLE7,1));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS rows");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the rows database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new row using the column1 and column2 provided. If the row is
     * successfully created return the new rowId for that row, otherwise return
     * a -1 to indicate failure.
     * 
     * @param column1 the column1 of the row
     * @param column2 the column2 of the row
     * @return rowId or -1 if failed
     */
    public long createRow(String column1, String column2, String column3, String column4, String column5, String column6, String column7, String column8, String column9) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_1, column1);
        initialValues.put(COLUMN_2, column2);
        initialValues.put(COLUMN_3, column3);
        initialValues.put(COLUMN_4, column4);
        initialValues.put(COLUMN_5, column5);
        initialValues.put(COLUMN_6, column6);
        initialValues.put(COLUMN_7, column7);
        initialValues.put(COLUMN_8, column8);
        initialValues.put(COLUMN_9, column9);
        return mDb.insert(STATS_TABLE, null, initialValues);
    }
    public long createShiftRow(String column1, String column2, String column3, String column4) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_1, column1);
        initialValues.put(COLUMN_2, column2);
        initialValues.put(COLUMN_3, column3);
        initialValues.put(COLUMN_4, column4);
        return mDb.insert(SHIFT_TABLE, null, initialValues);
    }
  
    /**
     * Delete the row with the given rowId
     * 
     * @param rowId id of row to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteRow(long rowId) {

        return mDb.delete(STATS_TABLE, COLUMN_ID + "=" + rowId, null) > 0;
    }
    public boolean deleteShiftRow(long rowId) {

        return mDb.delete(SHIFT_TABLE, COLUMN_ID + "=" + rowId, null) > 0;
    }
    public boolean delete(String table) {

        return mDb.delete(table,null, null) > 0;
    }
    /**
     * Return a Cursor over the list of all rows in the database
     * 
     * @return Cursor over all rows
     */
    public Cursor fetchAllRows() {

        return mDb.query(STATS_TABLE, new String[] {COLUMN_ID, COLUMN_1,
                COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6, COLUMN_7, COLUMN_8, COLUMN_9}, null, null, null, null, COLUMN_1+" DESC");
    }
    public Cursor fetchAllStats() {

        return mDb.query(STATS_TABLE, new String[] {COLUMN_ID, COLUMN_1,
                COLUMN_8, COLUMN_9}, null, null, null, null, COLUMN_1+" DESC");
    }
    public Cursor fetchAllShiftRows() {
    	
        return mDb.query(SHIFT_TABLE, new String[] {COLUMN_ID, COLUMN_1,
                COLUMN_2, COLUMN_3, COLUMN_4}, null, null, null, null,COLUMN_1+" DESC");
    }
    /**
     * Return a Cursor positioned at the row that matches the given rowId
     * 
     * @param rowId id of row to retrieve
     * @return Cursor positioned to matching row, if found
     * @throws SQLException if row could not be found/retrieved
     */
 
    public Cursor fetchRow(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, STATS_TABLE, new String[] {COLUMN_ID,
                        COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6, COLUMN_7}, COLUMN_ID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public Cursor fetchStats(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, STATS_TABLE, new String[] {COLUMN_ID,
                        COLUMN_1, COLUMN_8, COLUMN_9}, COLUMN_ID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public Cursor fetchShiftRow(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, SHIFT_TABLE, new String[] {COLUMN_ID,
                        COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4}, COLUMN_ID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
   
    /**
     * Update the row using the details provided. The row to be updated is
     * specified using the rowId, and it is altered to use the column1 and column2
     * values passed in
     * 
     * @param rowId id of row to update
     * @param column1 value to set row column1 to
     * @param column2 value to set row column2 to
     * @return true if the row was successfully updated, false otherwise
     */
    public boolean updateRow(long rowId, String column1, String column2, String column3, String column4, String column5, String column6, String column7, String column8, String column9) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_1, column1);
        args.put(COLUMN_2, column2);
        args.put(COLUMN_3, column3);
        args.put(COLUMN_4, column4);
        args.put(COLUMN_5, column5);
        args.put(COLUMN_6, column6);
        args.put(COLUMN_7, column7);
        args.put(COLUMN_8, column8);
        args.put(COLUMN_9, column9);
        return mDb.update(STATS_TABLE, args, COLUMN_ID + "=" + rowId, null) > 0;
    }
    public boolean updateShiftRow(long rowId, String column1, String column2, String column3, String column4) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_1, column1);
        args.put(COLUMN_2, column2);
        args.put(COLUMN_3, column3);
        args.put(COLUMN_4, column4);
        
        return mDb.update(SHIFT_TABLE, args, COLUMN_ID + "=" + rowId, null) > 0;
    }
    
    
    public Double getAverage(String table, String column){
    	Cursor cur= mDb.rawQuery(
                "SELECT AVG("+column+") FROM "+table, null);
            if(cur.moveToFirst()) {
                return cur.getDouble(0);
            }
     		return null;
    }
    public Double getTotal(String table, String column){
    	Cursor cur= mDb.rawQuery(
                "SELECT SUM("+column+") FROM "+table, null);
            if(cur.moveToFirst()) {
                return cur.getDouble(0);
            }
     		return null;
    }
   
    
   
    public Cursor fetch1ValueCursor(String table) throws SQLException {

        Cursor mCursor =

                mDb.query(true, table, new String[] {COLUMN_ID,
                        COLUMN_1}, COLUMN_ID + "=" + 1, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        
        return mCursor;
    }
    public String fetch1Value(String table){
    	String value=fetch1ValueCursor(table).getString(1);
		return value;
    }
    public boolean update1Value(String table,String value) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_1, value);
        
        return mDb.update(table, args, COLUMN_ID + "=" + 1, null) > 0;
    }
    public Cursor cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) throws SQLException{
		Cursor mCursor= mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		
		return mCursor;
    }
    public String[] fetchcolumnnames(String table) throws SQLException{
		Cursor mCursor = mDb.query(table, null, null, null, null, null, null);
		
		return mCursor.getColumnNames();
		
	}
    public int tablerowcount(String table){
		Cursor mCursor = mDb.query(table, null, null, null, null, null, null);
		return mCursor.getCount();
	}
    public String fetch(String table, int row, int column) throws SQLException{
		
		Cursor mCursor = mDb.query(table, null, null, null, null, null, null);
		
		//Row
		mCursor.moveToPosition(row-1);
		
		//Column
		String result=mCursor.getString(column);
		
		return result;
		
	}
 public String fetch(String table, int row, String[] column) throws SQLException{
		
		Cursor mCursor = mDb.query(table, column, null, null, null, null, null);
		
		//Row
		mCursor.moveToPosition(row);
		
		//Column
		String result=mCursor.getString(0);
		
		return result;
		
	}
	public boolean doesTblExist(String tblName){
		Cursor rs = null;
		try{
			rs = mDb.rawQuery("SELECT * FROM " + tblName + " WHERE _id=0", null );
			return true;
		}catch(Exception ex){
			return false;
		}finally{
			if (rs != null) rs.close();
		}
	}
	private static final String tablecreatestring(String tablename, int numberofcolumns){
		final String[] sequence = new String[numberofcolumns+1];
		String fullsequence="";
		for(int i=1; i<=numberofcolumns; i++){
			sequence[i]=",column"+String.valueOf(i) +" text not null ";
			fullsequence=fullsequence+sequence[i];
		}
		String finalstring="create table if not exists "+tablename+" (_id integer primary key autoincrement "+fullsequence+");";
		return finalstring;
		
	}
	
	private static final String tablecreatestring(String tablename, String[] columnnames){
		
		String fullsequence="";
		int i=1;
		while(i<=columnnames.length){
			fullsequence=fullsequence+","+columnnames[i-1] +" text not null ";
			i++;
		}
		String finalstring="create table "+tablename+" (_id integer primary key autoincrement "+fullsequence+");";
		return finalstring;
		
	}
private static final String blobtablecreatestring(String tablename, String[] columnnames){
		
		String fullsequence="";
		int i=1;
		while(i<=columnnames.length){
			fullsequence=fullsequence+","+columnnames[i-1] +" blob not null ";
			i++;
		}
		String finalstring="create table "+tablename+" (_id integer primary key autoincrement "+fullsequence+");";
		return finalstring;
		
	}
	

	

		
	
	public long createRow(String table, String[] columns, String[] values){
		ContentValues initialValues = new ContentValues();
		int i=1;
		int difference=columns.length-values.length;
		while(i<=columns.length){
			
				
				if (i<=values.length){
					initialValues.put(columns[i-1], values[i-1]);
				}else{
					initialValues.put(columns[i-1], "");
				}
			
			
			i++;
		}
		
		return mDb.insert(table, null, initialValues);
		
	}
	public long createRowBlob(String table, String[] columns, byte[] baf){
		ContentValues dataToInsert = new ContentValues();  
		dataToInsert.put(columns[0],baf);
		
		return mDb.insert(table, null, dataToInsert);
		
	}
	public void renametable(String tablename, String newtablename){
		if(doesTblExist(tablename)){
			mDb.execSQL("alter table "+tablename+" rename to "+ newtablename);
		}
	}
	public boolean updateRowBlob(String table, String[] columns, byte[] baf, long rowId){
		ContentValues dataToInsert = new ContentValues();  
		dataToInsert.put(columns[0],baf);
		return mDb.update(table, dataToInsert, "_id="+rowId, null)>0;
	}
	
	
	public boolean deleteRow(String table, long rowId) {
		return mDb.delete(table, "_id="+ rowId, null)>0;
	}
	public boolean updateRow(String table, String[] columns, String[] values, long rowId){
		ContentValues initialValues = new ContentValues();
		rowId=rowId+1;
		int i=1;
		while(i<=columns.length){
			initialValues.put(columns[i-1], values[i-1]);
			i++;
		}
		return mDb.update(table, initialValues, "_id="+rowId, null)>0;
	}
	public void deletetable(String table){
		if(doesTblExist(table))
			mDb.execSQL("DROP TABLE IF EXISTS "+table);
	}
	public void createtable(String table, String[] columns){
		mDb.execSQL(tablecreatestring(table,columns));
	}
	
	public Cursor tablecursor(String table){
		
		Cursor mCursor = mDb.query(table, null, null, null, null, null, null);
		return mCursor;
		
	}
	
	
	
	public Cursor simplecursor(String table){
			Cursor mCursor= mDb.query(table, null, null, null, null, null, null);
		
		return mCursor;
	}
	public void set(String table, String[] columns, String[] values, int rowId){
		if(!doesTblExist(table))
			createtable(table, columns);
		if (!simplecursor(table).moveToFirst())
			createRow(table, columns, values);
		
		while(!simplecursor(table).moveToPosition(rowId-1))
			createRow(table, columns, values);
			
		updateRow(table, columns, values, rowId-1);
	}
	

	
	public void set1(String table, String value){
		String[] columns={"col"};
		String[] values={value};
		if(!doesTblExist(table))
			createtable(table, columns);
		if (!simplecursor(table).moveToFirst())
			createRow(table, columns, values);
		
		while(!simplecursor(table).moveToPosition(0))
			createRow(table, columns, values);
			
		updateRow(table, columns, values, 0);
	}

	public String fetch1(String table) throws SQLException{
		String result;
		if(doesTblExist(table)){
		Cursor mCursor = mDb.query(table, null, null, null, null, null, null);
		
		//Row
		mCursor.moveToPosition(0);
		
		//Column
		result=mCursor.getString(1);
		}else{
		result="0";
		}
		return result;
		
	}
	public void deletedatabase(Context ctx){
    	
		ContextWrapper wrapper=new ContextWrapper(ctx);
    	wrapper.deleteDatabase("data");
    }
	
	public void createtableBlob(String table, String[] columns){
		mDb.execSQL(blobtablecreatestring(table,columns));
	}
	public void set1Blob(String table, byte[] baf){
		String[] columns={"col"};
		
		if(!doesTblExist(table))
			createtableBlob(table, columns);
		if (!simplecursor(table).moveToFirst())
			createRowBlob(table, columns, baf);
		
		while(!simplecursor(table).moveToPosition(0))
			createRowBlob(table, columns, baf);
			
		updateRowBlob(table, columns, baf, 0);
	}
	public void set1Blobfirst(String table, byte[] baf){
		String[] columns={"col"};
		;
		if(!doesTblExist(table)){
			createtable(table, columns);
		if (!simplecursor(table).moveToFirst())
			createRowBlob(table, columns, baf);
		
		while(!simplecursor(table).moveToPosition(0))
			createRowBlob(table, columns, baf);
			
		updateRowBlob(table, columns, baf, 0);
		}
	}
	public byte[] fetch1Blob(String table) throws SQLException{
		byte[] result = null;
		if(doesTblExist(table)){
		Cursor mCursor = mDb.query(table, null, null, null, null, null, null);
		
		//Row
		mCursor.moveToPosition(0);
		
		//Column
		result=mCursor.getBlob(1);
		}
		
		
		return result;
		
	}
	public byte[] fetchBlob(String table, int rowid) throws SQLException{
		byte[] result = null;
		if(doesTblExist(table)){
		Cursor mCursor = mDb.query(table, null, null, null, null, null, null);
		rowid=rowid-1;
		//Row
		mCursor.moveToPosition(0);
		
		//Column
		result=mCursor.getBlob(1);
		}
		
		
		return result;
		
	}
	public void deleteDatabase() {
	      mDbHelper.close();
	      mDb.close();
	      if (mCtx.deleteDatabase(DATABASE_NAME)) {
	        Log.d(TAG, "deleteDatabase(): database deleted.");
	      } else {
	        Log.d(TAG, "deleteDatabase(): database NOT deleted.");
	      }
	    } 
	
}
    
