package com.talax.drawing;
 
 
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
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
 
public class Wallet extends Activity {
    public int stage=1;
	public DisplayMetrics dm;
	 static final int MIN_DISTANCE = 100;
     private float downX, downY, upX, upY, moveX, moveY;
     private Bitmap wallet = null;
     public static Bitmap captureBmp;

 	private Activity ACTIVITY;
 	private PendingIntent RESTART_INTENT;
   
 //stage2 declarations	
 	private double rw2=.39875,rh2=.088495575,wsw2,wsh2,ww2=800,wh2=339, ridw2=rw2, ridh2=.530973;
 	private double pw2[]= {0,0,0,0,426,430,437,26,21,21};
 	private double ph2[]= {0,0,0,0,23,51,86,30,60,98};
 	private double[] pws2;
 	private double[] phs2;
 	private int exposedcardwidth2, exposedcardheight2;
 	private int[] cardxposition2, cardyposition2;
 	
 	
 //stage3 declarations
 	private double rw3=.39875,rh3=.050933786,wsw3,wsh3,ww3=800,wh3=589, ridw3=rw3, ridh3=.305602716;
 	private double pw3[]= {35,36,33,34,444,444,445,0,0,0};
 	private double ph3[]= {35,293,327,364,287,319,355,0,0,0};
 	private double[] pws3;
 	private double[] phs3;
 	
 	private int exposedcardwidth3, exposedcardheight3;
 	private int[] cardxposition3, cardyposition3;
 
 	private int idcardwidth2, idcardheight2, idcardwidth3, idcardheight3;
 	
 	private Bitmap addcard,removecard,preferences,backgroundblue, wallet1, wallet2, wallet3, wallet4, wallet5;
 	
 	private int buttonwidth, buttontopleft, addl, addt, reml, remt, prefl, preft, addr, addb, remr, remb, prefr, prefb;
 
 	private int limit;
 	
 	private Bitmap[] cardimage2, cardimage3, fullimage2, fullimage3;
 	
 	private int moveselection=-1, dropselection=-1;
 	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
//for button size and position find portrait width
        if (dm.widthPixels<dm.heightPixels){//portrait
        	buttonwidth=dm.widthPixels/4;
        	buttontopleft=dm.heightPixels-buttonwidth;
        	addl=0;
        	addt=buttontopleft;
        	reml=buttonwidth+buttonwidth/2;
        	remt=buttontopleft;
        	prefl=3*buttonwidth;
        	preft=buttontopleft;
        
        }else{								//landscape
        	buttonwidth=dm.heightPixels/4;
        	buttontopleft=dm.heightPixels-buttonwidth;
        	addl=dm.widthPixels-buttonwidth;
        	addt=0;
        	reml=dm.widthPixels-buttonwidth;
        	remt=buttonwidth*3/2;
        	prefl= dm.widthPixels-buttonwidth;
        	preft=buttontopleft;
        	
        }
        addr=addl+buttonwidth;
        addb=addt+buttonwidth;
        remr=reml+buttonwidth;
        remb=remt+buttonwidth;
        prefr=prefl+buttonwidth;
        prefb=preft+buttonwidth;
        
        
        
        pws2=new double[pw2.length];
        phs2=new double[ph2.length];
        
        pws3=new double[pw3.length];
        phs3=new double[ph3.length];

        
        cardxposition2=new int[pw2.length];
        cardyposition2=new int[ph2.length];
        
        cardxposition3=new int[pw3.length];
        cardyposition3=new int[ph3.length];
        
        
      //get images ready
    	addcard=BitmapFactory.decodeResource(getResources(), R.drawable.addcard);
    	removecard=BitmapFactory.decodeResource(getResources(), R.drawable.removecard);
    	preferences=BitmapFactory.decodeResource(getResources(), R.drawable.preferencesicon);
    	backgroundblue=BitmapFactory.decodeResource(getResources(), R.drawable.backgroundblue);
    	

        
        setContentView(new Panel(this));
      
       
        ACTIVITY = this;
  	    Intent restart=u.intent("MainGallery");
  	    restart.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        RESTART_INTENT = PendingIntent.getActivity(this.getBaseContext(), 0, restart, getIntent().getFlags());
        
    	
    }
	@Override
 	public void onResume(){
 		super.onResume();
 		
 		setContentView(new Panel(this));
 		ACTIVITY = this;
  	    Intent restart=u.intent("MainGallery");
  	    restart.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        RESTART_INTENT = PendingIntent.getActivity(this.getBaseContext(), 0, restart, getIntent().getFlags());
        
 	}
	
 
    class Panel extends SurfaceView implements SurfaceHolder.Callback{
    	
    	private TutorialThread _thread;
        private int _x = dm.widthPixels/2;
        private int _y = dm.heightPixels/2;
       
       
        
        public Panel(Context context) {
            super(context);
            getHolder().addCallback(this);
            _thread = new TutorialThread(getHolder(), this);
            setFocusable(true);
            
            addcard = Bitmap.createScaledBitmap(addcard, buttonwidth, buttonwidth, false);
    		removecard = Bitmap.createScaledBitmap(removecard, buttonwidth, buttonwidth, false);
    		preferences = Bitmap.createScaledBitmap(preferences, buttonwidth, buttonwidth, false);
    		backgroundblue=Bitmap.createScaledBitmap(backgroundblue, dm.widthPixels, dm.heightPixels, false);
    		
        	wallet1 = BitmapFactory.decodeResource(getResources(), R.drawable.covertrans400);
        	wallet1 = u.scaledtofitscreen(wallet1, dm);
        	
        	wallet2 = BitmapFactory.decodeResource(getResources(), R.drawable.opentrans4002);
        	wallet2 = u.scaledtofitscreen(wallet2, dm);
        	wallet3 = BitmapFactory.decodeResource(getResources(), R.drawable.idopentrans400);
        	wallet3 = u.scaledtofitscreen(wallet3, dm);
        	wallet4 = BitmapFactory.decodeResource(getResources(), R.drawable.backcovertrans400);
        	wallet4 = u.scaledtofitscreen(wallet4, dm);
        	wallet5 = BitmapFactory.decodeResource(getResources(), R.drawable.toptrans400);
        	wallet5 = u.scaledtofitscreen(wallet5, dm);
        	
        	limit=u.i(MasterDatabase.DBfetchstring("currentCardnumber", getBaseContext()));
        	
        	cardimage2=new Bitmap[limit];
        	cardimage3=new Bitmap[limit];
        	fullimage2=new Bitmap[limit];
        	fullimage3=new Bitmap[limit];
 
//stage 2       	
        	int i=4;
    		while(i<=limit-1){	
    			
    			byte[] arg5=MasterDatabase.DBfetchblob("Card"+String.format("%05d", i), getBaseContext());
    			fullimage2[i]=BitmapFactory.decodeByteArray(arg5, 0, arg5.length);
    			
    			byte[] arg3=MasterDatabase.DBfetchblob("exposedCard"+String.format("%05d", i), getBaseContext());
    			cardimage2[i]=BitmapFactory.decodeByteArray(arg3, 0, arg3.length);
    			
            	Log.w("calcs","i="+i);
            	wsw2=wallet2.getWidth();
            	wsh2=wallet2.getHeight();
            	Log.w("calcs","pw2[i]="+pw2[i]);
            	Log.w("calcs","ph2[i]="+ph2[i]);
           	
            	double psw2=wsw2*rw2;
            	double psh2=wsh2*rh2;
            	
            	Log.w("calcs","wsw2="+wsw2);
            	Log.w("calcs","wsh2="+wsh2);
            	Log.w("calcs","rw2="+rw2);
            	Log.w("calcs","rh2="+rh2);
            	Log.w("calcs","psw2="+psw2);
            	Log.w("calcs","psh2="+psh2);
            	
            	Log.w("calcs","ww2="+ww2);
            	Log.w("calcs","wh2="+wh2);
    	
 		
            	pws2[i]=(pw2[i]/ww2)*wsw2;
            	phs2[i]=(ph2[i]/wh2)*wsh2;
     	
  
            	Log.w("calcs","i="+i);   	
            	Log.w("calcs","pws2[i]="+pws2[i]);
            	Log.w("calcs","phs2[i]="+phs2[i]);

//card position and size for stage 2             	
            	exposedcardwidth2=((int) (wsw2*rw2));
            	exposedcardheight2= ((int)(wsh2*rh2));
            	cardxposition2[i]=(int)pws2[i]+_x-wallet2.getWidth()/2;
            	cardyposition2[i]=(int)phs2[i]+_y-wallet2.getHeight()/2;
            	idcardwidth2=((int) (wsw2*ridw2));
            	idcardheight2 =((int)(wsh2*ridh2));
            	
            	fullimage2[i]=Bitmap.createScaledBitmap(fullimage2[i], idcardwidth2, idcardheight2, false);
            	cardimage2[i]=Bitmap.createScaledBitmap(cardimage2[i], exposedcardwidth2, exposedcardheight2, false);
            	
            	
            	i++;
    		}
 //stage 3   		
    		i=0;
    		while(i<=6 && i<=limit-1){	
    			
//show id as full size card            			
    			
    		
    			byte[] arg6=MasterDatabase.DBfetchblob("Card"+String.format("%05d", i), getBaseContext());
        		fullimage3[i]=BitmapFactory.decodeByteArray(arg6, 0, arg6.length);
    			
    			byte[] arg4=MasterDatabase.DBfetchblob("exposedCard"+String.format("%05d", i), getBaseContext());
    			cardimage3[i]=BitmapFactory.decodeByteArray(arg4, 0, arg4.length);
    			
    			
            	Log.w("calcs","i="+i);
            	wsw3=wallet3.getWidth();
            	wsh3=wallet3.getHeight();
            	Log.w("calcs","pw3[i]="+pw3[i]);
            	Log.w("calcs","ph3[i]="+ph3[i]);
            	
            	double psw3=wsw3*rw3;
            	double psh3=wsh3*rh3;
            	
            	Log.w("calcs","wsw3="+wsw3);
            	Log.w("calcs","wsh3="+wsh3);
            	Log.w("calcs","rw3="+rw3);
            	Log.w("calcs","rh3="+rh3);
            	Log.w("calcs","psw3="+psw3);
            	Log.w("calcs","psh3="+psh3);
            	
            	Log.w("calcs","ww3="+ww3);
            	Log.w("calcs","wh3="+wh3);
    	
 		
            	pws3[i]=(pw3[i]/ww3)*wsw3;
            	phs3[i]=(ph3[i]/wh3)*wsh3;
     	
  
            	Log.w("calcs","i="+i);   	
            	Log.w("calcs","pws3[i]="+pws3[i]);
            	Log.w("calcs","phs3[i]="+phs3[i]);

//card position and size for stage 3             	
            	exposedcardwidth3=((int) (wsw3*rw3));
            	exposedcardheight3= ((int)(wsh3*rh3));
            	cardxposition3[i]=(int)pws3[i]+_x-wallet3.getWidth()/2;
            	cardyposition3[i]=(int)phs3[i]+_y-wallet3.getHeight()/2;     
            	
            	idcardwidth3=((int) (wsw3*ridw3));
            	idcardheight3 =((int)(wsh3*ridh3));
            	
            	
            	fullimage3[i]=Bitmap.createScaledBitmap(fullimage3[i], idcardwidth3, idcardheight3, false);
            	if(i==0){
            		cardimage3[i]=fullimage3[i];
            	}else{
            		cardimage3[i]=Bitmap.createScaledBitmap(cardimage3[i], exposedcardwidth3, exposedcardheight3, false);
            	}
            	
            	
            	
            	i++;
         
    		}
    		
        }
 
        @Override
        public void onDraw(Canvas canvas) {
	
//Which wallet stage to be drawn        	
        	if(canvas!=null){
        	if (stage==1){
        		wallet = wallet1;
            }
        	if (stage==2){
        		wallet = wallet2;
        	}
        	if (stage==3){
        		wallet = wallet3;
        	}
        	if (stage==4){
        		wallet = wallet4;
        	}
        	if (stage==5){
        		wallet = wallet5;
        	}
       	
        	canvas.drawColor(Color.BLACK);
        	canvas.drawBitmap(backgroundblue, 0, 0, null);
        	canvas.drawBitmap(wallet, _x-wallet.getWidth()/2, _y-wallet.getHeight()/2, null);

//draw cards stage2
        	
        	
        	
        	if (stage==2){
        		int i=4;
        		while(i<=limit-1){
        			if (!(i==moveselection)){
        				canvas.drawBitmap(cardimage2[i], cardxposition2[i], cardyposition2[i], null);
        			}
        			//cardimage2[i].recycle();
                	i++;
             }
        	}
 //draw cards stage 3 
        		
        	if (stage==3){

            		int i=0;
            		while(i<=6 && i<=limit-1){	
            			if (!(i==moveselection)){
            				canvas.drawBitmap(cardimage3[i], cardxposition3[i], cardyposition3[i], null);
            			}
            			i++;
            		}
        	}
        	
//Draw Buttons
        	
        
        		canvas.drawBitmap(addcard, addl, addt, null);
        		canvas.drawBitmap(removecard, reml, remt, null);
        		canvas.drawBitmap(preferences, prefl, preft, null);
        		
        		if (moveselection>=0){
        			if (stage==2){
        				canvas.drawBitmap(fullimage2[moveselection], moveX-(downX-cardxposition2[moveselection]),moveY-(downY-cardyposition2[moveselection]),null);
        			}
        			if(stage==3){
        				canvas.drawBitmap(fullimage3[moveselection], moveX-(downX-cardxposition3[moveselection]),moveY-(downY-cardyposition3[moveselection]),null);
        			}
        		}
        	}
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
            getWindowManager().getDefaultDisplay();
            switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
  
//Card Buttons        
                if (stage==2){
            		int i=4;
            		while(i<=limit-1){
                	
            			if(downX>cardxposition2[i] && downY>cardyposition2[i] && downX<cardxposition2[i]+exposedcardwidth2 && downY<cardyposition2[i]+exposedcardheight2){
            				moveselection=i;
                    		
            			}
                	i++;
                	
            		}
                }
                if (stage==3){

            		int i=0;
            		while(i<=6 && i<=limit-1){	

            			if(i==0){
            				if(downX>cardxposition3[i] && downY>cardyposition3[i] && downX<cardxposition3[i]+idcardwidth3 && downY<cardyposition3[i]+idcardheight3){
            					moveselection=i;
            				
            				}
            			}else{
            			
            				if(downX>cardxposition3[i] && downY>cardyposition3[i] && downX<cardxposition3[i]+exposedcardwidth3 && downY<cardyposition3[i]+exposedcardheight3){
            					MasterDatabase.DBsavestring("selectedCard", String.format("%05d",i), getBaseContext());
            					moveselection=i;
            				}
            			}
                    	i++;
            		}
        	}

                return true;
            }
            case MotionEvent.ACTION_MOVE: {
               
            	moveX = event.getX();
                moveY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                
            	upX = event.getX();
                upY = event.getY();
                
                if (moveselection<0){
                	float deltaX = downX - upX;
                	float deltaY = downY - upY;

                	// swipe horizontal?
                	if(Math.abs(deltaX) > MIN_DISTANCE){
                		// left or right
                		if(deltaX < 0) { /*left to right*/ if(stage==2){ stage=1;return true;};
                										   if(stage==4){ stage=2;return true;};
                		}
                		
                		if(deltaX > 0) { /*right to left*/ if(stage==1){ stage=2;return true;};
                										   if(stage==2){ stage=4;return true;};
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
 //Add Card Button
                if(upX>addl && upY> addt && upX<addr && upY<addb){
                	takePhoto();
                }
              
//Remove Card Button        
               
                if(upX>reml && upY> remt && upX<remr && upY<remb){	
                    	AlarmManager mgr = (AlarmManager)ACTIVITY.getSystemService(Context.ALARM_SERVICE);
                      	mgr.set(AlarmManager.RTC, System.currentTimeMillis(), RESTART_INTENT);
                      	System.exit(2);
                }	
             

//Preferences Button        
                if(upX>prefl && upY> preft && upX<prefr && upY<prefb){	
                		startActivity(u.intent("Preferences"));
                }
//Card Buttons        
                if (stage==2){
            		int i=4;
            		while(i<=limit-1){
                	
            			if(upX>cardxposition2[i] && upY>cardyposition2[i] && upX<cardxposition2[i]+exposedcardwidth2 && upY<cardyposition2[i]+exposedcardheight2){
            				MasterDatabase.DBsavestring("selectedCard", String.format("%05d",i), getBaseContext());
                    		startActivity(u.intent("MainGallery"));
            			}
                	i++;
                	
            		}
                }
                if (stage==3){

            		int i=0;
            		while(i<=6 && i<=limit-1){	

            			if(i==0){
            				if(upX>cardxposition3[i] && upY>cardyposition3[i] && upX<cardxposition3[i]+idcardwidth3 && upY<cardyposition3[i]+idcardheight3){
            					MasterDatabase.DBsavestring("selectedCard", String.format("%05d",i), getBaseContext());
            					startActivity(u.intent("MainGallery"));
            				}
            			}else{
            			
            				if(upX>cardxposition3[i] && upY>cardyposition3[i] && upX<cardxposition3[i]+exposedcardwidth3 && upY<cardyposition3[i]+exposedcardheight3){
            					MasterDatabase.DBsavestring("selectedCard", String.format("%05d",i), getBaseContext());
            					startActivity(u.intent("MainGallery"));
            				}
            			}
                    	i++;
            		}
                }

              
               
  // move selection is positive a card is being moved and its up point is the drop position          	
                }else{
                	if (stage==2){
                		int i=4;
                		while(i<=limit-1){
                    	
                			if(upX>cardxposition2[i] && upY>cardyposition2[i] && upX<cardxposition2[i]+exposedcardwidth2 && upY<cardyposition2[i]+exposedcardheight2){
                				dropselection=i;
                        		swap();
                        		relayoutonecard(dropselection,_x,_y);
                        		relayoutonecard(moveselection,_x,_y);
                			}
                    	i++;
                    	
                		}
                    }
                    if (stage==3){

                		int i=0;
                		while(i<=6 && i<=limit-1){	

                			if(i==0){
                				if(upX>cardxposition3[i] && upY>cardyposition3[i] && upX<cardxposition3[i]+idcardwidth3 && upY<cardyposition3[i]+idcardheight3){
                					dropselection=i;
                            		swap();
                            		relayoutonecard(dropselection,_x,_y);
                            		relayoutonecard(moveselection,_x,_y);
                				}
                			}else{
                			
                				if(upX>cardxposition3[i] && upY>cardyposition3[i] && upX<cardxposition3[i]+exposedcardwidth3 && upY<cardyposition3[i]+exposedcardheight3){
                					dropselection=i;
                            		swap();
                            		relayoutonecard(dropselection,_x,_y);
                            		relayoutonecard(moveselection,_x,_y);
                				}
                			}
                        	i++;
                		}
                    }
                }
                dropselection=-1;
                moveselection=-1;
                return true;
            }
        	}
        return false;
      
        }
	
    }

    /*
    private int buttons(int upx, int upy){
	   int buttonnumber;
	   int limit=u.i(MasterDatabase.DBfetchstring("currentCardnumber", getBaseContext()));
   	int i;
   	if (stage==2){
   	
   		i=4;
   		while(i<=limit-1){	
   			
   			byte[] arg3=MasterDatabase.DBfetchblob("exposedCard"+String.format("%05d", i), getBaseContext());
   			testsquare=BitmapFactory.decodeByteArray(arg3, 0, arg3.length);
   			
           	Log.w("calcs","i="+i);
           	wsw2=wallet.getWidth();
           	wsh2=wallet.getHeight();
           	Log.w("calcs","pw2[i]="+pw2[i]);
           	Log.w("calcs","ph2[i]="+ph2[i]);
           	
           	double psw2=wsw2*rw2;
           	double psh2=wsh2*rh2;
           	
           	Log.w("calcs","wsw2="+wsw2);
           	Log.w("calcs","wsh2="+wsh2);
           	Log.w("calcs","rw2="+rw2);
           	Log.w("calcs","rh2="+rh2);
           	Log.w("calcs","psw2="+psw2);
           	Log.w("calcs","psh2="+psh2);
           	
           	Log.w("calcs","ww2="+ww2);
           	Log.w("calcs","wh2="+wh2);
   	
		
           	pws2[i]=(pw2[i]/ww2)*wsw2;
           	phs2[i]=(ph2[i]/wh2)*wsh2;
    	
 
           	Log.w("calcs","i="+i);   	
           	Log.w("calcs","pws2[i]="+pws2[i]);
           	Log.w("calcs","phs2[i]="+phs2[i]);
           	testsquare=Bitmap.createScaledBitmap(testsquare, ((int) (wsw2*rw2)), ((int)(wsh2*rh2)), false);
           	canvas.drawBitmap(testsquare, (int)pws2[i]+_x-wallet.getWidth()/2, (int)phs2[i]+_y-wallet.getHeight()/2, null);
           	testsquare.recycle();
           	i++;
        }
   	}
//draw cards stage 3 
   		
   	if (stage==3){
           	

       		
       		i=0;
       		while(i<=6 && i<=limit-1){	
       			
//show id as full size card            			
       			byte[] arg4;
       			if(i==0){
       				arg4=MasterDatabase.DBfetchblob("Card"+String.format("%05d", i), getBaseContext());
       				testsquare=BitmapFactory.decodeByteArray(arg4, 0, arg4.length);
       			}else{
       				arg4=MasterDatabase.DBfetchblob("exposedCard"+String.format("%05d", i), getBaseContext());
       				testsquare=BitmapFactory.decodeByteArray(arg4, 0, arg4.length);
       			}
       			
               	Log.w("calcs","i="+i);
               	wsw3=wallet.getWidth();
               	wsh3=wallet.getHeight();
               	Log.w("calcs","pw3[i]="+pw3[i]);
               	Log.w("calcs","ph3[i]="+ph3[i]);
               	
               	double psw3=wsw3*rw3;
               	double psh3=wsh3*rh3;
               	
               	Log.w("calcs","wsw3="+wsw3);
               	Log.w("calcs","wsh3="+wsh3);
               	Log.w("calcs","rw3="+rw3);
               	Log.w("calcs","rh3="+rh3);
               	Log.w("calcs","psw3="+psw3);
               	Log.w("calcs","psh3="+psh3);
               	
               	Log.w("calcs","ww3="+ww3);
               	Log.w("calcs","wh3="+wh3);
       	
    		
               	pws3[i]=(pw3[i]/ww3)*wsw3;
               	phs3[i]=(ph3[i]/wh3)*wsh3;
        	
     
               	Log.w("calcs","i="+i);   	
               	Log.w("calcs","pws3[i]="+pws3[i]);
               	Log.w("calcs","phs3[i]="+phs3[i]);
               	
               	if(i==0){
               		testsquare=Bitmap.createScaledBitmap(testsquare, ((int) (wsw3*ridw3)), ((int)(wsh3*ridh3)), false);
               	}else{
               		testsquare=Bitmap.createScaledBitmap(testsquare, ((int) (wsw3*rw3)), ((int)(wsh3*rh3)), false);
               	}
               	
               	canvas.drawBitmap(testsquare, (int)pws3[i]+_x-wallet.getWidth()/2, (int)phs3[i]+_y-wallet.getHeight()/2, null);
               	testsquare.recycle();
               	i++;
	   
	   
	   return buttonnumber;
	   
   }
   */
  
//Add Card    
    private void takePhoto(){
    	
    	startActivity(u.intent("CameraPreview"));
    	
    }
    private void swap(){
		
		
		 Context ctx=getBaseContext();
   		    
// step 1 determine which card is moving where, selected to drop		
		
			String selectedcard="Card"+String.format("%05d",moveselection);
			String selectedcard1="RawFront"+String.format("%05d",moveselection);
   		    String selectedcard2="RawBack"+String.format("%05d",moveselection);
   		    String selectedcard3="Back"+String.format("%05d",moveselection);
   		    String selectedcard4="exposedCard"+String.format("%05d",moveselection);
   		   
			//	File currentfile=new File(meteoDirectory_path, "/"+seriesname+ u.s(w)+".png");
       			String dropcard="Card"+String.format("%05d",dropselection);
       			String dropcard1="RawFront"+String.format("%05d",dropselection);
       			String dropcard2="RawBack"+String.format("%05d",dropselection);
       			String dropcard3="Back"+String.format("%05d",dropselection);
       		    String dropcard4="exposedCard"+String.format("%05d",dropselection);
       		//	nextfile.renameTo(currentfile);
       		 
// step 1.5 make temp names to store selectedcard   
       		    String temp="TempCard";
       		 	String temp1="TempRawFront";
    		    String temp2="TempRawBack";
    		    String temp3="TempBack";
    		    String temp4="TempexposedCard";
       		    
       		    
// step 2 back up selected card to temp so that slot is available to be filled
    		   u.renameTable(selectedcard, temp, ctx);
         	   u.renameTable(selectedcard1, temp1, ctx);
         	   u.renameTable(selectedcard2, temp2, ctx);
         	   u.renameTable(selectedcard3, temp3, ctx);
         	   u.renameTable(selectedcard4, temp4, ctx);
       		    
       		
        		   
// step 3 delete selected card, so drop card can replace it       			
        	   u.deleteTable(selectedcard, ctx);
        	   u.deleteTable(selectedcard1, ctx);
        	   u.deleteTable(selectedcard2, ctx);
        	   u.deleteTable(selectedcard, ctx);
        	   u.deleteTable(selectedcard, ctx);
 
// step 4 move drop card into (old) selectedcard's place
        	   u.renameTable(dropcard, selectedcard, ctx);
        	   u.renameTable(dropcard1, selectedcard1, ctx);
        	   u.renameTable(dropcard2, selectedcard2, ctx);
        	   u.renameTable(dropcard3, selectedcard3, ctx);
        	   u.renameTable(dropcard4, selectedcard4, ctx);

// step 5 delete dropcard so temp can fill it

        	   u.deleteTable(dropcard, ctx);
        	   u.deleteTable(dropcard1, ctx);
        	   u.deleteTable(dropcard2, ctx);
        	   u.deleteTable(dropcard3, ctx);
        	   u.deleteTable(dropcard4, ctx);
        	   
// step 6 rename temp to fill drop cards place
        	   
        	   u.renameTable(temp, dropcard, ctx);
        	   u.renameTable(temp1, dropcard1, ctx);
        	   u.renameTable(temp2, dropcard2, ctx);
        	   u.renameTable(temp3, dropcard3, ctx);
        	   u.renameTable(temp4, dropcard4, ctx);
        	   
        	// step 5 delete dropcard so temp can fill it

        	   u.deleteTable(temp, ctx);
        	   u.deleteTable(temp1, ctx);
        	   u.deleteTable(temp2, ctx);
        	   u.deleteTable(temp3, ctx);
        	   u.deleteTable(temp4, ctx);       	   
        	   
        	   
        	  
				
	}
	private void relayoutonecard(int i, int _x, int _y){
		//stage 2       	
    	
			
			byte[] arg0=MasterDatabase.DBfetchblob("Card"+String.format("%05d", i), getBaseContext());
			fullimage2[i]=BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
			
			byte[] arg1=MasterDatabase.DBfetchblob("exposedCard"+String.format("%05d", i), getBaseContext());
			cardimage2[i]=BitmapFactory.decodeByteArray(arg1, 0, arg1.length);
			
        	Log.w("calcs","i="+i);
        	wsw2=wallet2.getWidth();
        	wsh2=wallet2.getHeight();
        	Log.w("calcs","pw2[i]="+pw2[i]);
        	Log.w("calcs","ph2[i]="+ph2[i]);
       	
        	double psw2=wsw2*rw2;
        	double psh2=wsh2*rh2;
        	
        	Log.w("calcs","wsw2="+wsw2);
        	Log.w("calcs","wsh2="+wsh2);
        	Log.w("calcs","rw2="+rw2);
        	Log.w("calcs","rh2="+rh2);
        	Log.w("calcs","psw2="+psw2);
        	Log.w("calcs","psh2="+psh2);
        	
        	Log.w("calcs","ww2="+ww2);
        	Log.w("calcs","wh2="+wh2);
	
		
        	pws2[i]=(pw2[i]/ww2)*wsw2;
        	phs2[i]=(ph2[i]/wh2)*wsh2;
 	

        	Log.w("calcs","i="+i);   	
        	Log.w("calcs","pws2[i]="+pws2[i]);
        	Log.w("calcs","phs2[i]="+phs2[i]);

//card position and size for stage 2             	
        	exposedcardwidth2=((int) (wsw2*rw2));
        	exposedcardheight2= ((int)(wsh2*rh2));
        	cardxposition2[i]=(int)pws2[i]+_x-wallet2.getWidth()/2;
        	cardyposition2[i]=(int)phs2[i]+_y-wallet2.getHeight()/2;
        	idcardwidth2=((int) (wsw2*ridw2));
        	idcardheight2 =((int)(wsh2*ridh2));
        	
        	fullimage2[i]=Bitmap.createScaledBitmap(fullimage2[i], idcardwidth2, idcardheight2, false);
        	cardimage2[i]=Bitmap.createScaledBitmap(cardimage2[i], exposedcardwidth2, exposedcardheight2, false);
        	
        	
        	
//stage 3   		
		
//show id as full size card            			
			
		
			byte[] arg6=MasterDatabase.DBfetchblob("Card"+String.format("%05d", i), getBaseContext());
    		fullimage3[i]=BitmapFactory.decodeByteArray(arg6, 0, arg6.length);
			
			byte[] arg4=MasterDatabase.DBfetchblob("exposedCard"+String.format("%05d", i), getBaseContext());
			cardimage3[i]=BitmapFactory.decodeByteArray(arg4, 0, arg4.length);
			
			
        	Log.w("calcs","i="+i);
        	wsw3=wallet3.getWidth();
        	wsh3=wallet3.getHeight();
        	Log.w("calcs","pw3[i]="+pw3[i]);
        	Log.w("calcs","ph3[i]="+ph3[i]);
        	
        	double psw3=wsw3*rw3;
        	double psh3=wsh3*rh3;
        	
        	Log.w("calcs","wsw3="+wsw3);
        	Log.w("calcs","wsh3="+wsh3);
        	Log.w("calcs","rw3="+rw3);
        	Log.w("calcs","rh3="+rh3);
        	Log.w("calcs","psw3="+psw3);
        	Log.w("calcs","psh3="+psh3);
        	
        	Log.w("calcs","ww3="+ww3);
        	Log.w("calcs","wh3="+wh3);
	
		
        	pws3[i]=(pw3[i]/ww3)*wsw3;
        	phs3[i]=(ph3[i]/wh3)*wsh3;
 	

        	Log.w("calcs","i="+i);   	
        	Log.w("calcs","pws3[i]="+pws3[i]);
        	Log.w("calcs","phs3[i]="+phs3[i]);

//card position and size for stage 3             	
        	exposedcardwidth3=((int) (wsw3*rw3));
        	exposedcardheight3= ((int)(wsh3*rh3));
        	cardxposition3[i]=(int)pws3[i]+_x-wallet3.getWidth()/2;
        	cardyposition3[i]=(int)phs3[i]+_y-wallet3.getHeight()/2;     
        	
        	idcardwidth3=((int) (wsw3*ridw3));
        	idcardheight3 =((int)(wsh3*ridh3));
        	
        	
        	fullimage3[i]=Bitmap.createScaledBitmap(fullimage3[i], idcardwidth3, idcardheight3, false);
        	if(i==0){
        		cardimage3[i]=fullimage3[i];
        	}else{
        		cardimage3[i]=Bitmap.createScaledBitmap(cardimage3[i], exposedcardwidth3, exposedcardheight3, false);
        	}
        	
        	
        	
        	
     
		}
	
    
}