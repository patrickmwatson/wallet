/*
 * Copyright (C) 2007 The Android Open Source Project
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

package com.talax.drawing;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;





// Need the following import to get access to the app resources, since this
// class is in a sub-package.


// ----------------------------------------------------------------------

public class CameraPreview extends Activity {
	static int flasher=0;
	
	
	boolean previewing = false;
	LayoutInflater controlInflater = null;
	ImageView buttonTakePicture;
	TextView wait;
	
	final int RESULT_SAVEIMAGE = 0;
    public static int increment;
	public Context ctx;
	private Preview mPreview;
    Camera mCamera;
    int numberOfCameras;
    int cameraCurrentlyLocked;
    public String tablename="Card";
    public int face=0; 
    LinearLayout layoutBackground;
    // The first rear facing camera
    int defaultCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ctx=this.getBaseContext();
 
        
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create a RelativeLayout container that will hold a SurfaceView,
        // and set it as the content of our activity.
        mPreview = new Preview(this);
        setContentView(mPreview);

        // Find the total number of cameras available
        numberOfCameras = Camera.getNumberOfCameras();

        // Find the ID of the default camera
        CameraInfo cameraInfo = new CameraInfo();
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                    defaultCameraId = i;
                }
            }
            controlInflater = LayoutInflater.from(getBaseContext());
            
            View viewControl = controlInflater.inflate(R.layout.control, null);
            
            LayoutParams layoutParamsControl 
            	= new LayoutParams(LayoutParams.FILL_PARENT, 
            	LayoutParams.FILL_PARENT);
            this.addContentView(viewControl, layoutParamsControl);
            
           
            
            
            layoutBackground = (LinearLayout)findViewById(R.id.background);
            buttonTakePicture = (ImageView)findViewById(R.id.accepticon);
            buttonTakePicture.setOnClickListener(new Button.OnClickListener(){

    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				
    				layoutBackground.setClickable(false);
    				//layoutBackground.setVisibility(View.GONE);
    				
    				buttonTakePicture.setVisibility(View.GONE);	
    				//wait.setVisibility(View.VISIBLE);				
    				
    				mCamera.takePicture(myShutterCallback, 
    						myPictureCallback_RAW, myPictureCallback_JPG);
    				
    				layoutBackground.setVisibility(View.VISIBLE);
    			}});
            
            
            layoutBackground.setOnClickListener(new LinearLayout.OnClickListener(){

    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				layoutBackground.setClickable(false);
    				buttonTakePicture.setClickable(false);
    				buttonTakePicture.setEnabled(false);
    				mCamera.autoFocus(myAutoFocusCallback);
    				layoutBackground.setClickable(true);
    			}});
        }
    
    AutoFocusCallback myAutoFocusCallback = new AutoFocusCallback(){

		public void onAutoFocus(boolean arg0, Camera arg1) {
			// TODO Auto-generated method stub
			buttonTakePicture.setEnabled(true);
			buttonTakePicture.setClickable(true);
		}};
    
    ShutterCallback myShutterCallback = new ShutterCallback(){

		public void onShutter() {
			// TODO Auto-generated method stub
			
		}};
		
	PictureCallback myPictureCallback_RAW = new PictureCallback(){

		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub
			
		}};
		
	PictureCallback myPictureCallback_JPG = new PictureCallback(){

		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub
			mCamera.startPreview();
			BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
			
			Save_to_Database(arg0, tablename);
			//camerapreview.finishActivity(0);
			//startActivity(u.intent("PreviewCard"));
		    //Save_to_SD(bitmapPicture,seriesname);
			//Save_to_Database(arg0, foldername);
		    buttonTakePicture.setVisibility(View.VISIBLE);
			//wait.setVisibility(View.GONE);
			buttonTakePicture.setClickable(true);
			buttonTakePicture.setEnabled(true);
			layoutBackground.setClickable(true);
			
		}};
	private void Save_to_Database (byte[] arg0, String image_name){
		
		/*
		Bitmap bm=croppedCard(arg0);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG, 0, bos);
		byte[] bitmapdata = bos.toByteArray();
		*/
		if(face==0){
		
			String currentCardnumber=MasterDatabase.DBfetchstring("currentCardnumber", ctx);
			MasterDatabase.DBsaveblob("RawFront"+String.format("%05d",u.i(currentCardnumber)), arg0, ctx);
			Toast.makeText(CameraPreview.this, 
				"Raw Front"+currentCardnumber+" Saved!", 
	 		 200).show();
		
		
			
		}else{
			
			String currentCardnumber=MasterDatabase.DBfetchstring("currentCardnumber", ctx);
			MasterDatabase.DBsaveblob("RawBack"+String.format("%05d",u.i(currentCardnumber)), arg0, ctx);
			Toast.makeText(CameraPreview.this, 
				"Raw Back"+currentCardnumber+" Saved!", 
	 		 200).show();
		
		
			MasterDatabase.DBsavestring("currentCardnumber", String.format("%05d",u.i(currentCardnumber)+1), ctx);
			
			
			
			startActivity(u.intent("CardCrop"));
		}
		face++;
		
	/*
		//increment=u.i(u.fetch("count", ctx));
		 increment=0; 	 
		u.setBlob(tablename+String.format("%05d",increment), arg0, ctx, 1);
		 Toast.makeText(CameraPreview.this, 
				"Image saved: to internal database: "+tablename+String.format("%05d",increment), 
	 		 200).show();
		  	  
		//increment++;
		  	  
		//u.set("count", u.s(increment), ctx);
	*/	  	  
		  	  
	} 
	
    @Override
    protected void onResume() {
        super.onResume();

        // Open the default i.e. the first rear facing camera.
        mCamera = Camera.open();
        cameraCurrentlyLocked = defaultCameraId;
        mPreview.setCamera(mCamera);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
        if (mCamera != null) {
            mPreview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
        System.exit(0);
    }
    
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate our menu which can gather user input for switching camera
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.camera_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.switch_cam:
            // check for availability of multiple cameras
            if (numberOfCameras == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(this.getString(R.string.camera_alert))
                       .setNeutralButton("Close", null);
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }

            // OK, we have multiple cameras.
            // Release this camera -> cameraCurrentlyLocked
            if (mCamera != null) {
                mCamera.stopPreview();
                mPreview.setCamera(null);
                mCamera.release();
                mCamera = null;
            }

            // Acquire the next camera and request Preview to reconfigure
            // parameters.
            mCamera = Camera
                    .open((cameraCurrentlyLocked + 1) % numberOfCameras);
            cameraCurrentlyLocked = (cameraCurrentlyLocked + 1)
                    % numberOfCameras;
            mPreview.switchCamera(mCamera);

            // Start the preview
            mCamera.startPreview();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }*/
}

// ----------------------------------------------------------------------


    
