package com.talax.drawing;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class CardCrop extends Activity{
	private Bitmap alphaGray;
    private Bitmap mBitmap;
    public DisplayMetrics dm;
    public double w1,sw,h1,sh,cciw,ccih,ciw,cih,l,t,r,b,ow,oh,n, rh=.42;
    private Bitmap bitmap, bitmap2, cropped, backgroundblue, croppedback;
    private Context context;
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		context=getBaseContext();
		
		dm=getResources().getDisplayMetrics();
        
        String currentCardnumber=MasterDatabase.DBfetchstring("currentCardnumber", context);
        int currentCardnumberprime=u.i(currentCardnumber)-1;
        byte[] arg0=MasterDatabase.DBfetchblob("RawFront"+String.format("%05d",currentCardnumberprime), context);
        MasterDatabase.DBsavestring("selectedCard", String.format("%05d",currentCardnumberprime), context);
        
        mBitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
        alphaGray = BitmapFactory.decodeResource(getResources(), R.drawable.revisedcardcutterbaw);
        
        int wo=alphaGray.getWidth();
        int ho=alphaGray.getHeight();
        
        alphaGray=scaledtofitscreen(alphaGray, dm);
        
        w1=alphaGray.getWidth();
        sw=dm.widthPixels;
        h1=alphaGray.getHeight();
        sh=dm.heightPixels;
        
        ow=mBitmap.getWidth();
        oh=mBitmap.getHeight();
        
        n=sw*oh/ow;
        
        cciw=-(w1-sw)/2;
        ccih=-(h1-sh)/2;
       
        Log.w("calcs","");
        Log.w("calcs","int wo="+u.s(wo));
        Log.w("calcs"," int ho="+u.s(ho));
        
        						
        
        Log.w("calcs"," w1="+u.s((int)w1));
        Log.w("calcs"," sw="+u.s((int)sw));
        Log.w("calcs"," h1="+u.s((int)h1));
        Log.w("calcs","  sh="+u.s((int)sh));
        
        Log.w("calcs"," ow="+u.s((int)ow));
        Log.w("calcs"," oh="+u.s((int)oh));
        
        Log.w("calcs","  n="+u.s((int)n));
        
        Log.w("calcs","  cciw="+u.s((int)cciw));
        Log.w("calcs"," ccih="+u.s( (int)ccih));
        
        
        //l=u.i(MasterDatabase.DBfetchstring("l", context));
        //t=u.i(MasterDatabase.DBfetchstring("t", context));
        //r=u.i(MasterDatabase.DBfetchstring("r", context));
       // b=u.i(MasterDatabase.DBfetchstring("b", context));
       
        
        //ciw=u.i(MasterDatabase.DBfetchstring("Cardimagewidth", context));
       // cih=u.i(MasterDatabase.DBfetchstring("Cardimageheight", context));
        mBitmap=Bitmap.createScaledBitmap(mBitmap,(int)sw,(int)n,false);
        
        bitmap = Bitmap.createBitmap( (int)sw, (int)sh, Bitmap.Config.ARGB_8888);
        Canvas canvas1=new Canvas(bitmap);
        canvas1.drawBitmap(mBitmap, 0,-((int)n-(int)sh)/2, null);
        //canvas1.drawBitmap(alphaGray,cciw,ccih, null);
        mBitmap.recycle();
        bitmap2 = Bitmap.createBitmap( (int)sw, (int)sh, Bitmap.Config.ARGB_8888);
        Canvas canvas2=new Canvas(bitmap2);
        canvas2.drawBitmap(alphaGray,(int)cciw,(int)ccih, null);
        
        cropped=croppedCard(bitmap,bitmap2);
       
        
        bitmap.recycle();
        bitmap2.recycle();
        backgroundblue=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.backgroundblue), dm.widthPixels, dm.heightPixels, false);
        Toast.makeText(getBaseContext(), 
				"Card"+String.format("%05d",currentCardnumberprime)+" Saved!", 
	 		 200).show();
        
        double l,t,w,h;
        l=(w1-(w1*wo/2)/wo)/2;
        t=(h1-h1*rh)/2+ccih;
        w=(w1*wo/2)/wo;
        h=h1*rh;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        
        Log.w("calcs",(int)l+" , "+(int)t+"  , "+ (int)w+"  , "+(int)h);
        cropped=Bitmap.createBitmap(cropped,(int)l,(int)t, (int)w,(int)h);
        

        cropped.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        MasterDatabase.DBsaveblob("Card"+String.format("%05d",currentCardnumberprime), byteArray, getBaseContext());
       
        
        double exposedcardheight=cropped.getHeight()*9/54;
        Log.w("calcs","double exposedcardheight="+exposedcardheight);
        Log.w("calcs","cropped.getHeight()="+cropped.getHeight());
        
        Bitmap exposedcropped=Bitmap.createBitmap(cropped, 0, 0, cropped.getWidth(), (int)exposedcardheight);
        
        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        exposedcropped.compress(Bitmap.CompressFormat.PNG, 100, stream3);
        byte[] byteArray2 = stream3.toByteArray();

        MasterDatabase.DBsaveblob("exposedCard"+String.format("%05d",currentCardnumberprime), byteArray2, getBaseContext());
        exposedcropped.recycle();
//Back

        
        
        
        byte[] arg1=MasterDatabase.DBfetchblob("RawBack"+String.format("%05d",currentCardnumberprime), context);
        mBitmap = BitmapFactory.decodeByteArray(arg1, 0, arg1.length);
       
        mBitmap=Bitmap.createScaledBitmap(mBitmap,(int)sw,(int)n,false);
        
        bitmap = Bitmap.createBitmap((int) sw,(int) sh, Bitmap.Config.ARGB_8888);
        canvas1=new Canvas(bitmap);
        canvas1.drawBitmap(mBitmap, 0,-((int)n-(int)sh)/2, null);
        //canvas1.drawBitmap(alphaGray,cciw,ccih, null);
        mBitmap.recycle();
        bitmap2 = Bitmap.createBitmap( (int)sw,(int) sh, Bitmap.Config.ARGB_8888);
        canvas2=new Canvas(bitmap2);
        canvas2.drawBitmap(alphaGray,(int)cciw,(int)ccih, null);
        
        croppedback=croppedCard(bitmap,bitmap2);
        bitmap.recycle();
        bitmap2.recycle();
        backgroundblue=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.backgroundblue), dm.widthPixels, dm.heightPixels, false);
        Toast.makeText(getBaseContext(), 
				"Card"+String.format("%05d",currentCardnumberprime)+" Saved!", 
	 		 200).show();
        
        stream = new ByteArrayOutputStream();
        croppedback=Bitmap.createBitmap(croppedback,(int)l,(int)t, (int)w,(int)h);
        croppedback.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();

        MasterDatabase.DBsaveblob("Back"+String.format("%05d",currentCardnumberprime), byteArray, getBaseContext());
        startActivity(u.intent("MainGallery"));
        
	}
	 private Bitmap croppedCard(Bitmap originalimage, Bitmap alphaGray)
	    {
	        BitmapFactory.Options opt = new BitmapFactory.Options();
	        opt.inPreferredConfig = Config.ARGB_8888;
	     
	       
	        
	        
	        
	        int width = originalimage.getWidth();
	        int height = originalimage.getHeight();
	     
	        //alphaGray=Bitmap.createScaledBitmap(alphaGray, width, height, false);
	        
	        Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	        result.eraseColor(Color.BLACK);
	  
	        Canvas c = new Canvas(result);
	        c.drawBitmap(originalimage, 0, 0, null);
	     
	        Bitmap alpha = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	        int[] alphaPix = new int[width * height];
	        alphaGray.getPixels(alphaPix, 0, width, 0, 0, width, height);
	     
	        int count = width * height;
	        for (int i = 0; i < count; ++i)
	        {
	            alphaPix[i] = alphaPix[i] << 8;
	        }
	        alpha.setPixels(alphaPix, 0, width, 0, 0, width, height);
	     
	        Paint alphaP = new Paint();
	        alphaP.setAntiAlias(true);
	        alphaP.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
	     
	        c.drawBitmap(alpha, 0, 0, alphaP);
	     
	      
	        alphaGray.recycle();
	        alpha.recycle();
	        
	        
	        return result;
	    }
	    public static Bitmap scaledtofitscreen(Bitmap originalimage, DisplayMetrics dm){
	    	Bitmap newimage = null;
	    	float hs = 0,ws = 0,h1 = 0,h2 = 0,w1 = 0,w2 = 0;
	     	
	        
	        hs=dm.heightPixels;
	 		ws=dm.widthPixels;
	 		w1=originalimage.getWidth();
	 		h1=originalimage.getHeight();
	 		/*
	    	String screenorient=u.getScreenOrientation(dm);
	    	String pictureorient=u.getPictureOrientation(originalimage);
	    	if(pictureorient.equals("landscape")){
	    		if (screenorient==("landscape")){
	    			if (rp>rs){
	    				w2=ws;
	            		h2=ws*h1/w1;
	    			}else{
	    				h2=hs;
	    				w2=hs*w1/h1;
	    			}
	    		}else{
	    			w2=ws;
	            	h2=ws*h1/w1;
	    			
	    		}
	    	}else{
	    		if (screenorient==("landscape")){
	    			h2=hs;
	    			w2=hs*w1/h1;
	    		}else{
	    			if(rp>rs){
	    				w2=ws;
	                	h2=ws*h1/w1;
	    			}else{
	    				h2=hs;
	    				w2=hs*w1/h1;
	    			}
	    		}	
	    	}*/
	    	//This line shows that if the image  width or height is not larger than the screen dont adjust size.
	    	//Basically showing the image only scales down to fit on screen, but doesn't grow to fill screen, but the 
	    	//grow to fill screen feature will be useful in the future, so dont disCard
	 		w2=ws;
        	h2=ws*h1/w1;
	 				
	    	    		newimage = Bitmap.createScaledBitmap(originalimage, (int)w2, (int)h2, true);
	    	    	
	    	    		//newimage=originalimage;
	    	    	
	    	    	
	    	    	return newimage;
	    	    }
}

