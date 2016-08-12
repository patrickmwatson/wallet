package com.talax.drawing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class deletedatabase extends Activity{
	private AlertDialog alert;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete all card images?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   cleardatabase();
                	   finish();
                	 
                   } 
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                   }
               });
        alert = builder.create();

    	//CustomizeDialog customizeDialog = new CustomizeDialog(this);
        alert.show();
        //this.finish();
		
	}
	private void cleardatabase(){
    	ContextWrapper cw= new ContextWrapper(this);
    	cw.deleteDatabase("data");
		
		
    }
	
}