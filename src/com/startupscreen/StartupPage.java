package com.startupscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.crashlytics.android.Crashlytics;
import com.loginpage.Login;
import com.loginpage.R;
import com.signinpage.Signin;

public class StartupPage extends Activity{
	
	Button giris,uye_ol;
	Context context = this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Crashlytics.start(this);
		setContentView(R.layout.startup);
		
		    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    
		    if (netInfo != null && netInfo.isConnected()) {
		    	
				giris = (Button) findViewById(R.id.btn_giris);
				uye_ol = (Button) findViewById(R.id.btn_uyeol);
				
				giris.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
							
						Intent go_signin = new Intent(getApplicationContext(),Signin.class);
						go_signin.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(go_signin);
					}
				});
				
				uye_ol.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent go_login = new Intent(getApplicationContext(),Login.class);
						go_login.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(go_login);
						
					}
				});
		        
		    }
		    
		    else{
		    	
		    	
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

					// set title
					alertDialogBuilder.setTitle("Hata!!");

					// set dialog message
					alertDialogBuilder
						.setMessage("UygunGidelim uygulamasını kullanabilmeniz için internet bağlantınızın açık olması gerekmektedir.Lütfen internet ayarlarınızı kontrol ediniz...")
						.setCancelable(false)
						.setPositiveButton("Tamam",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
						        finish();
						        System.exit(0);
							}
						  });


						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();

						// show it
						alertDialog.show();
		    	
		    }

		

		

	}

	 

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
        finish();
        System.exit(0);
	}

}
