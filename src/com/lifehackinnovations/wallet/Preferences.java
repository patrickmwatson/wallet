package com.lifehackinnovations.wallet;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

public class Preferences extends PreferenceActivity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setPreferenceScreen(walletpreferences());
	}
	private PreferenceScreen walletpreferences(){
		
		PreferenceScreen root=getPreferenceManager().createPreferenceScreen(this);
		
			PreferenceCategory databaseoptions= new PreferenceCategory(this);
			databaseoptions.setTitle("Database");
			root.addPreference(databaseoptions);
			
				PreferenceScreen deletedatabase= getPreferenceManager().createPreferenceScreen(this);
				deletedatabase.setIntent(u.intent("deletedatabase"));
				deletedatabase.setTitle("Delete Database");
				databaseoptions.addPreference(deletedatabase);
			
				PreferenceScreen backupdatabasetosd= getPreferenceManager().createPreferenceScreen(this);
				//backupdatabasetosd.setIntent(u.intent(""));
				backupdatabasetosd.setTitle("Back Up Database to SD Card");
				backupdatabasetosd.setEnabled(false);
				databaseoptions.addPreference(backupdatabasetosd);
				
				PreferenceScreen restoredatabasefromsd= getPreferenceManager().createPreferenceScreen(this);
				//restoredatabasefromsd.setIntent(u.intent(""));
				restoredatabasefromsd.setTitle("Restore Database from SD Card");
				restoredatabasefromsd.setEnabled(false);
				databaseoptions.addPreference(restoredatabasefromsd);
			
			PreferenceCategory customize= new PreferenceCategory(this);
			databaseoptions.setTitle("Customize");
			root.addPreference(customize);
				
				ListPreference defaultview = new ListPreference(this);
				defaultview.setEntries(new String[]{"Wallet","Gallery"});
        		defaultview.setEntryValues(new String[]{"wallet","gallery"});
        		defaultview.setDialogTitle("Default View");
        		defaultview.setKey("list_preference");
        		defaultview.setTitle("Default View");
        		defaultview.setSummary("Choose what you would like the default view to be");
        		defaultview.setEnabled(false);
        		customize.addPreference(defaultview);
			
		
		return root;
		
	}
}