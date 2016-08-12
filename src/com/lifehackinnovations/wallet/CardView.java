package com.lifehackinnovations.wallet;
 
 
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
 
public class CardView extends Activity {
    public int stage=0;
	public DisplayMetrics dm;
	 static final int MIN_DISTANCE = 100;
     private float downX, downY, upX, upY;
     private Bitmap wallet = null;
     public static Bitmap captureBmp;
     public String currentCardnumber;
 	private Activity ACTIVITY;
 	private PendingIntent RESTART_INTENT;
 	public Context context;
 	public Bitmap mBitmap;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        setContentView(new cPanel(this));
      /*  
        currentCardnumber=MasterDatabase.DBfetchstring("selectedCard", context);
        
      
        
        ACTIVITY = this;
  	    Intent restart=u.intent("MainGallery");
  	    restart.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        RESTART_INTENT = PendingIntent.getActivity(this.getBaseContext(), 0, restart, getIntent().getFlags());
        */
    	
    }
	@Override
 	public void onResume(){
 		super.onResume();
 		/*
 		setContentView(new cPanel(this));
 		ACTIVITY = this;
  	    Intent restart=u.intent("MainGallery");
  	    restart.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        RESTART_INTENT = PendingIntent.getActivity(this.getBaseContext(), 0, restart, getIntent().getFlags());
        */
 	}
	
 
    class cPanel extends SurfaceView implements SurfaceHolder.Callback{
    	
    	private CardViewThread _thread;
        private int _x = dm.widthPixels/2;
        private int _y = dm.heightPixels/2;
       
       
        
        public cPanel(Context context) {
            super(context);
            getHolder().addCallback(this);
            _thread = new CardViewThread(getHolder(), this);
            setFocusable(true);
        }
 
        @Override
        public void onDraw(Canvas canvas) {
            currentCardnumber=MasterDatabase.DBfetchstring("selectedCard", getContext());
        	byte[] arg0=MasterDatabase.DBfetchblob("Card"+String.format("%05d",u.i(currentCardnumber)), context);
            mBitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
    /*         
        	dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            Display getOrient = getWindowManager().getDefaultDisplay();
        	
 //get images ready
        	Bitmap addcard=BitmapFactory.decodeResource(getResources(), R.drawable.addcard);
        	Bitmap removecard=BitmapFactory.decodeResource(getResources(), R.drawable.removecard);
        	Bitmap preferences=BitmapFactory.decodeResource(getResources(), R.drawable.preferencesicon);
        	Bitmap backgroundblue=BitmapFactory.decodeResource(getResources(), R.drawable.backgroundblue);


        	
        	
//Which wallet stage to be drawn        	
        	
        	if (stage==1){
        		wallet = BitmapFactory.decodeResource(getResources(), R.drawable.covertrans400);
            }
        	if (stage==2){
        		wallet = BitmapFactory.decodeResource(getResources(), R.drawable.opentrans400);
        	}
        	if (stage==3){
        		wallet = BitmapFactory.decodeResource(getResources(), R.drawable.idopentrans400);
        	}
        	if (stage==4){
        		wallet = BitmapFactory.decodeResource(getResources(), R.drawable.backcovertrans400);
        	}
        	if (stage==5){
        		wallet = BitmapFactory.decodeResource(getResources(), R.drawable.toptrans400);
        	}
//Scale if too big to fit on screen
        	
        	wallet = u.scaledtofitscreen(wallet, dm);
 
//Cleans Screen (Apparently)        	
        	canvas.drawColor(Color.BLACK);
 
 //resize then draw background
        	backgroundblue=Bitmap.createScaledBitmap(backgroundblue, dm.widthPixels, dm.heightPixels, false);
        	canvas.drawBitmap(backgroundblue, 0, 0, null);
        	
        	canvas.drawBitmap(mBitmap,0, 0, null);
        	
//Draw Buttons
        	
        	if (u.getScreenOrientation(dm).equals("portrait")){
        		//set scale
        		int buttonwidth=dm.widthPixels/4;
        		//rescale buttons
        		addcard = Bitmap.createScaledBitmap(addcard, buttonwidth, buttonwidth, false);
        		removecard = Bitmap.createScaledBitmap(removecard, buttonwidth, buttonwidth, false);
        		preferences = Bitmap.createScaledBitmap(preferences, buttonwidth, buttonwidth, false);
        		
        		//actually draw them
        		int buttontop=dm.heightPixels-buttonwidth;
        		canvas.drawBitmap(addcard, 0, buttontop, null);
        		canvas.drawBitmap(removecard, buttonwidth+buttonwidth/2, buttontop, null);
        		//canvas.drawBitmap(bitmap, left, top, paint);
        		canvas.drawBitmap(preferences, 3*buttonwidth, buttontop, null);
        	}else{
        		int buttonwidth=dm.heightPixels/4;
        		//rescale buttons
        		addcard = Bitmap.createScaledBitmap(addcard, buttonwidth, buttonwidth, false);
        		removecard = Bitmap.createScaledBitmap(removecard, buttonwidth, buttonwidth, false);
        		preferences = Bitmap.createScaledBitmap(preferences, buttonwidth, buttonwidth, false);
        		
        		//actually draw them
        		int buttontop=dm.heightPixels-buttonwidth;
        		canvas.drawBitmap(addcard, dm.widthPixels-buttonwidth, 0, null);
        		canvas.drawBitmap(removecard, dm.widthPixels-buttonwidth, buttonwidth*3/2, null);
        		//canvas.drawBitmap(bitmap, left, top, paint);
        		canvas.drawBitmap(preferences, dm.widthPixels-buttonwidth, buttontop, null);
        	}
        	
        	
        	
        	//Bitmap addcardbutton = BitmapFactory.decodeResource(getResources(), R.drawable.testsquare);
        	//addcardbutton = scaledtofitscreensection(addcardbutton,.25,.25);
        	//float topofbuttons=dm.heightPixels-addcardbutton.getHeight();
        	//canvas.drawBitmap(addcardbutton, dm.widthPixels*1/2, topofbuttons, null);
       		*/
        }
 
        
        
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // TODO Auto-generated method stub
        	
        }
 
        public void surfaceCreated(SurfaceHolder holder) {
            _thread.setRunning(true);
            _thread.start();
        }
 
        public void surfaceDestroyed(SurfaceHolder holder) {
            // simply copied from sample application LunarLander:
            // we have to tell thread to shut down & wait for it to finish, or else
            // it might touch the Surface after we return and explode
            boolean retry = true;
            _thread.setRunning(false);
            while (retry) {
                try {
                    _thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    // we will try it again and again...
                }
            }
        }
       
        @Override
        public boolean onTouchEvent(MotionEvent event) {
        	dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            Display getOrient = getWindowManager().getDefaultDisplay();
            switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe horizontal?
                if(Math.abs(deltaX) > MIN_DISTANCE){
                    // left or right
                	 if(deltaX < 0) { /*left to right*/if (stage==0){ byte[] arg0=MasterDatabase.DBfetchblob("Back"+String.format("%05d",u.i(currentCardnumber)), getContext());
						mBitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
						stage=1;}
                	 if (stage==1){ byte[] arg0=MasterDatabase.DBfetchblob("Card"+String.format("%05d",u.i(currentCardnumber)), getContext());
		  				mBitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
		  				stage=0;}
                	 }

                	 if(deltaX > 0) { /*right to left*/if (stage==0){ byte[] arg0=MasterDatabase.DBfetchblob("Back"+String.format("%05d",u.i(currentCardnumber)), getContext());
						mBitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
						stage=1;}
                	 if (stage==1){ byte[] arg0=MasterDatabase.DBfetchblob("Card"+String.format("%05d",u.i(currentCardnumber)), getContext());
		  				mBitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
		  				stage=0;}
}
                } else { /*shorter than MIN_DISTANCE*/ }

                // swipe vertical?
                if(Math.abs(deltaY) > MIN_DISTANCE){
                    // top or down
                    if(deltaY < 0) {/*top to bottom*/ if(stage==3){ stage=2;return true;};
                    								  if(stage==2){ stage=5;return true;};
                    }
                    if(deltaY > 0) { /*bottom to top*/ if(stage==2){ stage=3;return true;};
                    								   if(stage==5){ stage=2;return true;};
                    }
                } else { /*shorter than MIN_DISTANCE*/ }
 
                
 //Button Push  
                
                
                if (u.getScreenOrientation(dm).equals("portrait")){
                	int buttonwidth=dm.widthPixels/4;
                	int buttontop=dm.heightPixels-buttonwidth;
                	if (upX<buttonwidth && upY>buttontop){
                		takePhoto();
                	
                	}
                	//startActivity(u.intent("addimage"));
                }else{
                	int buttonwidth=dm.heightPixels/4;
                	int buttonleft=dm.widthPixels-buttonwidth;
                	if (upX>buttonleft && upY<buttonwidth){
                		takePhoto();
                		
                	}
                }
//Remove Card Button        
                if (u.getScreenOrientation(dm).equals("portrait")){
                	int buttonwidth=dm.widthPixels/4;
                	int buttontop=dm.heightPixels-buttonwidth;
                	if (upX>buttonwidth*3/2 && upY>buttontop && upX<buttonwidth*5/2){
                		
                	
                    	AlarmManager mgr = (AlarmManager)ACTIVITY.getSystemService(Context.ALARM_SERVICE);
                      	mgr.set(AlarmManager.RTC, System.currentTimeMillis(), RESTART_INTENT);
                      	System.exit(2);
                	
                	}
                	
                }else{
                	int buttonwidth=dm.heightPixels/4;
                	int buttonleft=dm.widthPixels-buttonwidth;
                	if (upX>buttonleft && upY>buttonwidth*3/2 && upY<buttonwidth*5/2){
                		
                		AlarmManager mgr = (AlarmManager)ACTIVITY.getSystemService(Context.ALARM_SERVICE);
                      	mgr.set(AlarmManager.RTC, System.currentTimeMillis(), RESTART_INTENT);
                      	System.exit(2);
                	
                	}
                }

//Preferences Button        
                if (u.getScreenOrientation(dm).equals("portrait")){
                	int buttonwidth=dm.widthPixels/4;
                	int buttontop=dm.heightPixels-buttonwidth;
                	if (upX>buttonwidth*3 && upY>buttontop){
                		cleardatabase();
                		
                	}
                	
                }else{
                	int buttonwidth=dm.heightPixels/4;
                	int buttonleft=dm.widthPixels-buttonwidth;
                	if (upX>buttonleft && upY>buttonwidth*3){
                		
                		cleardatabase();
                		
                	}
                }
                
                return true;
            }
        	}
        return false;
      
        }
	
    }
    
    private void cleardatabase(){
    	int i=0;
	    while(i<=u.i(MasterDatabase.DBfetchstring("currentCardnumber", getBaseContext()))+1){
	    u.deleteTable("RawFront"+String.format("%05d",i), getBaseContext());
	    u.deleteTable("RawBack"+String.format("%05d",i), getBaseContext());
	    u.deleteTable("Back"+String.format("%05d",i), getBaseContext());
	    u.deleteTable("Card"+String.format("%05d",i), getBaseContext());
	    i++;
	    }
	    
	    MasterDatabase.DBsavestring("currentCardnumber", String.format("%05d",0), getBaseContext());
    }
//Add Card    
    private void takePhoto(){
    	
    	startActivity(u.intent("CameraPreview"));
    	
    	}
    
}