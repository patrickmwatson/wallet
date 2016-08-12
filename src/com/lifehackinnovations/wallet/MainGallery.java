/*
F * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lifehackinnovations.wallet;







import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


public class MainGallery extends Activity implements
        AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory, OnItemLongClickListener, OnClickListener, OnLongClickListener {
	private Bitmap[] bm;
	private Gallery g;
	private Context ctx;
    private int swtch=0;
	private AlertDialog alert;
	private int deletenum;
	private int limit;
	private BitmapFactory.Options opts;
	private String pauseactivity;
	private ImageSwitcher mSwitcher;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
     
        opts=new BitmapFactory.Options();
		opts.inSampleSize=4;
        pauseactivity="Wallet";
        //ACTIVITY = this;
  	    //Intent restart=u.intent("Wallet");
  	    //restart.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //RESTART_INTENT = PendingIntent.getActivity(this.getBaseContext(), 0, restart, getIntent().getFlags());
        
        
        ctx=getBaseContext();
                                                                           
        limit=u.i(MasterDatabase.DBfetchstring("currentCardnumber",ctx));
        
        
  
       
        LoadThumbnailsfromDb();
        displayeverything();
        int position=u.i(MasterDatabase.DBfetchstring("selectedCard", ctx));
        g.setSelection(position);
                
        
    }
    public boolean onItemLongClick(AdapterView parent, View v, final int position, long id) {
    	
    	deletecard(position);
    	return false;
    }
    public void onItemSelected(AdapterView parent, View v, int position, long id) {
    	
    	
		byte[] arg2=MasterDatabase.DBfetchblob("Card"+String.format("%05d",position), ctx);
    	mSwitcher.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(arg2, 0, arg2.length)));
    	MasterDatabase.DBsavestring("selectedCard",String.format("%05d",position) , ctx);
    	swtch=0;
    }

    public void onNothingSelected(AdapterView parent) {
    	
    }

    public View makeView() {
        ImageView i = new ImageView(this);
        i.setBackgroundResource(R.drawable.backgroundblue);
        //i.setBackgroundColor(0x00000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        return i;
    }

   

    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return bm.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);
            
            i.setImageBitmap(bm[position]);
            i.setAdjustViewBounds(true);
            i.setLayoutParams(new Gallery.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            //i.setBackgroundResource(R.drawable.pictureframe);
            
            
            
            return i;
        }

        private Context mContext;
             }

   

   
     
    
    private void rearrange(){
		
		int limit=u.i(MasterDatabase.DBfetchstring("currentCardnumber",ctx));
		Log.w("calcs1","limit="+limit);
		Log.w("calcs1","deletenum="+deletenum);
		
		Log.w("calcs1","beforeloop: currentCardnumber"+String.format("%05d",limit));
 		Log.w("calcs1","beforeloop: selectedCard"+String.format("%05d",limit));
   		    
		for(int w=deletenum;w<=limit-1;w++){
		
			String nextfile="Card"+String.format("%05d",w+1);
			String nextfile1="RawFront"+String.format("%05d",w+1);
   		    String nextfile2="RawBack"+String.format("%05d",w+1);
   		    String nextfile3="Back"+String.format("%05d",w+1);
   		    String nextfile4="exposedCard"+String.format("%05d",w+1);
   		   
			//	File currentfile=new File(meteoDirectory_path, "/"+seriesname+ u.s(w)+".png");
       			String currentfile="Card"+String.format("%05d",w);
       			String currentfile1="RawFront"+String.format("%05d",w);
       			String currentfile2="RawBack"+String.format("%05d",w);
       			String currentfile3="Back"+String.format("%05d",w);
       		    String currentfile4="exposedCard"+String.format("%05d",w);
       		//	nextfile.renameTo(currentfile);
       		   
       		Log.w("calcs1","loop: currentCardnumber"+String.format("%05d",limit));
     		Log.w("calcs1","loop: selectedCard"+String.format("%05d",limit));
       		    
       		    
       		Log.w("calcs1","String nextfile=Card"+String.format("%05d",w+1));
       		Log.w("calcs1","String nextfile1=RawFront"+String.format("%05d",w+1));
       		Log.w("calcs1","String nextfile2=RawBack"+String.format("%05d",w+1));
       		Log.w("calcs1","String nextfile3=Back"+String.format("%05d",w+1));
       		Log.w("calcs1","String nextfile4=exposedCard"+String.format("%05d",w+1));
    		   
 			//	File currentfile=new File(meteoDirectory_path, "/"+seriesname+ u.s(w)+".png");
       		Log.w("calcs1","String currentfile=Card"+String.format("%05d",w));
       		Log.w("calcs1","String currentfile1=RawFront"+String.format("%05d",w));
       		Log.w("calcs1","String currentfile2=RawBack"+String.format("%05d",w));
       		Log.w("calcs1","String currentfile3=Back"+String.format("%05d",w));
       		Log.w("calcs1","String currentfile4=exposedCard"+String.format("%05d",w));
        		//	nextfile.renameTo(currentfile);
        		   
       			
        	   u.deleteTable(currentfile, ctx);
        	   u.deleteTable(currentfile1, ctx);
        	   u.deleteTable(currentfile2, ctx);
        	   u.deleteTable(currentfile3, ctx);
        	   u.deleteTable(currentfile4, ctx);
        	   
        	   u.renameTable(nextfile, currentfile, ctx);
        	   u.renameTable(nextfile1, currentfile1, ctx);
        	   u.renameTable(nextfile2, currentfile2, ctx);
        	   u.renameTable(nextfile3, currentfile3, ctx);
        	   u.renameTable(nextfile4, currentfile4, ctx);
			
        	   Log.w("calcs1","passed the delete/rename stage!!");
        	   /*
       			int i=0;
       	    	while(i<=u.i(MasterDatabase.DBfetchstring("currentCardnumber", getBaseContext()))+1){
       	    	u.deleteTable("RawFront"+String.format("%05d",i), getBaseContext());
       	    	u.deleteTable("RawBack"+String.format("%05d",i), getBaseContext());
       	    	u.deleteTable("Back"+String.format("%05d",i), getBaseContext());
       	    	u.deleteTable("Card"+String.format("%05d",i), getBaseContext());
       	    	u.deleteTable("exposedCard"+String.format("%05d",i), getBaseContext());
       	    	u.deleteTable("selectedCard"+String.format("%05d",i), getBaseContext());
       	  
       	    	i++;
       	    	}
       	    	u.deleteTable("currentCardnumber"+String.format("%05d",i), getBaseContext());
        	    */
	
				
		}
		
		MasterDatabase.DBsavestring("currentCardnumber", String.format("%05d",limit-1), ctx);
		MasterDatabase.DBsavestring("selectedCard", String.format("%05d",limit-1), ctx);
		Log.w("calcs1","afterloop: currentCardnumber"+String.format("%05d",limit-1));
		Log.w("calcs1","afterloop: selectedCard"+String.format("%05d",limit-1));
	}
   
    public void displayeverything(){
    	Toast.makeText(MainGallery.this,"Longpress Image to Delete",1000).show();
        setContentView(R.layout.image_switcher_1);

        mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        mSwitcher.setOnClickListener(this);
        mSwitcher.setOnLongClickListener(this);
        
        
        
        g = (Gallery) findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(this));
        g.setOnItemSelectedListener(this);
        g.setOnItemLongClickListener(this);
      
    }
    public void onPause(){
    	super.onPause();
    	startActivity(u.intent(pauseactivity));
    	
    
    }
    public void onResume(){
    	super.onResume();
    	
    	
        displayeverything();
        
        
        limit=u.i(MasterDatabase.DBfetchstring("currentCardnumber",ctx));
       
        
     
        LoadThumbnailsfromDb();
        int position=u.i(MasterDatabase.DBfetchstring("selectedCard", ctx));
        g.setSelection(position);
    }
    private void ClearBitmaps(){
    	
    	int limit=u.i(MasterDatabase.DBfetchstring("currentCardnumber",ctx));
        
    	
    	bm=new Bitmap[limit];
    	for(int i=0;i<limit;i++){
        bm[i]=null; 
    	
    	}
	
	
}
    public void LoadThumbnailsfromDb(){
    	ClearBitmaps();
    	
    	limit=u.i(MasterDatabase.DBfetchstring("currentCardnumber",ctx));
        
    
    	
    		bm=new Bitmap[limit];
    		for(int i=0;i<limit;i++){
    			byte[] arg0=MasterDatabase.DBfetchblob("Card"+String.format("%05d",i), ctx);
    			bm[i]= BitmapFactory.decodeByteArray(arg0, 0, arg0.length, opts);
    		
    			arg0=null;
    	
    		}
    	

    }
   
	public void onClick(View v) {
		if (swtch==0){
			int position=u.i(MasterDatabase.DBfetchstring("selectedCard", ctx));
			byte[] arg1=MasterDatabase.DBfetchblob("Back"+String.format("%05d",position), ctx);
		
			mSwitcher.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(arg1, 0, arg1.length)));
			// TODO Auto-generated method stub
			swtch=1;
		}else{
			int position=u.i(MasterDatabase.DBfetchstring("selectedCard", ctx));
			byte[] arg1=MasterDatabase.DBfetchblob("Card"+String.format("%05d",position), ctx);
		
			mSwitcher.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(arg1, 0, arg1.length)));
			swtch=0;
		}
	}
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		final int position=u.i(MasterDatabase.DBfetchstring("selectedCard", ctx));
		deletecard(position);
		return false;
	}
	
	public void deletecard(final int position){
		MasterDatabase.DBsavestring("deletenum", u.s(position),ctx);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete Card"+String.format("%05d",position))
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   deletenum=position;
                	 
                	   ClearBitmaps();
                	   displayeverything();
                	   
                	    rearrange();
                	    
                	    ClearBitmaps();
                	    displayeverything();
            			Log.w("calcs1","passed everything but loadthumbnailsfromdb");
            			LoadThumbnailsfromDb();
            		
                   } 
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                   }
               });
        alert = builder.create();

    	//CustomizeDialog customizeDialog = new CustomizeDialog(this);
        alert.show();
	}
}
