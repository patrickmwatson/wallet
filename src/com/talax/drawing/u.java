package com.talax.drawing;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;

public class u extends Activity{
	public static Intent intent(String classname){
		Intent myIntent=new Intent();
		myIntent.setClassName(Wallet.class.getPackage().getName(), Wallet.class.getPackage().getName()+"."+classname);
		return myIntent;
	}
	public static String getScreenOrientation(DisplayMetrics dm)
    {
        
        String orientation = "undefined";
        if(dm.widthPixels==dm.heightPixels){
            orientation = "square";
        } else{ 
            if(dm.widthPixels < dm.heightPixels){
                orientation = "portrait";
            }else { 
                 orientation = "landscape";
            }
        }
        return orientation;
    }
    public static String getPictureOrientation(Bitmap bmp)
    {
        
        String orientation = "undefined";
        if(bmp.getWidth()==bmp.getHeight()){
            orientation = "square";
        } else{ 
            if(bmp.getWidth() < bmp.getHeight()){
                orientation = "portrait";
            }else { 
                 orientation = "landscape";
            }
        }
        return orientation;
    }
    public static Bitmap scaledtofitscreen(Bitmap originalimage, DisplayMetrics dm){
    	Bitmap newimage;
    	float rp=0,rs=0,hs = 0,ws = 0,h1 = 0,h2 = 0,w1 = 0,w2 = 0;
     	
        
        hs=dm.heightPixels;
 		ws=dm.widthPixels;
 		w1=originalimage.getWidth();
 		h1=originalimage.getHeight();
 		rp=w1/h1;
 		rs=ws/hs;
    	
    	
    	String screenorient=getScreenOrientation(dm);
    	String pictureorient=getPictureOrientation(originalimage);
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
    	}
//This line shows that if the image  width or height is not larger than the screen dont adjust size.
//Basically showing the image only scales down to fit on screen, but doesn't grow to fill screen, but the 
//grow to fill screen feature will be useful in the future, so dont discard
    	if (!(ws>w1) || !(hs>h1)){
    		newimage = Bitmap.createScaledBitmap(originalimage, (int)w2, (int)h2, true);
    	}else{
    		newimage=originalimage;
    	}
    	
    	return newimage;
    }
    public Bitmap scaledtofitscreensection(Bitmap originalimage, double sectionx, double sectiony, DisplayMetrics dm){
    	Bitmap newimage;
    	float h2 = 0,w2 = 0;
     	
        
        h2=dm.heightPixels*(float)sectiony;
 		w2=dm.widthPixels*(float)sectionx;
 		
    	

    	newimage = Bitmap.createScaledBitmap(originalimage, (int)w2, (int)h2, true);
    	return newimage;
    }
    public void cleardata(){
		deleteDatabase("data");
	}
	public static String appname(Context context){
	     Resources appR = context.getResources();
	     CharSequence txt = appR.getText(appR.getIdentifier("app_name",
	     "string", context.getPackageName()));
		return (String)txt;
	}
	public static String s(int i){
		String s=String.valueOf(i);
		return s;
	}
	public static int i(String s){
		int i=Integer.parseInt(s);
		return i;
	}
	 public static Bitmap scaledtofitscreenlarge(Bitmap originalimage, DisplayMetrics dm){
	    	Bitmap newimage;
	    	float rp=0,rs=0,hs = 0,ws = 0,h1 = 0,h2 = 0,w1 = 0,w2 = 0;
	     	
	        
	        hs=dm.heightPixels;
	 		ws=dm.widthPixels;
	 		w1=originalimage.getWidth();
	 		h1=originalimage.getHeight();
	 		rp=w1/h1;
	 		rs=ws/hs;
	    	
	    	
	    	String screenorient=getScreenOrientation(dm);
	    	String pictureorient=getPictureOrientation(originalimage);
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
	    	}
	//This line shows that if the image  width or height is not larger than the screen dont adjust size.
	//Basically showing the image only scales down to fit on screen, but doesn't grow to fill screen, but the 
	//grow to fill screen feature will be useful in the future, so dont discard
	    	
	    		newimage = Bitmap.createScaledBitmap(originalimage, (int)w2, (int)h2, true);
	    	
	    	return newimage;
	    }
	 public static void deleteTable(String table,Context context){
			DbAdapter mDbHelper;
			mDbHelper = new DbAdapter(context);
	        mDbHelper.open();
	        mDbHelper.deletetable(table);
	        mDbHelper.close();
		}
	 public static void renameTable(String table,String newtable, Context context){
			DbAdapter mDbHelper;
			mDbHelper = new DbAdapter(context);
	        mDbHelper.open();
	        mDbHelper.renametable(table, newtable);
	        mDbHelper.close();
		}
}

