package com.signinpage;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.androidbegin.jsonparsetutorial.MainActivity;

import com.gpstracking.AndroidGPSTrackingActivity;
import com.gpstracking.GPSTracker;
import com.loginpage.R;
import com.startupscreen.StartupPage;
import com.usermenu.UserInformation;
import com.uygun.gidelim.LocationSelector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProfilPage extends Activity {
	
	String jsn = "";
	HttpClient client;
	HttpGet httpget;
	HttpResponse response;
	HttpEntity entity; 
	InputStream is;
	BufferedReader reader;
	StringBuilder js;
	StringBuilder stringBuilder;
	JSONObject obj;
	String json;
	
	String gelen_mesaj;
	
	 String hata_mesaj;
	 
	 String get_mesaj;
	
	ImageButton btn_userinformation;
	ImageButton btn_panikbutton;
	ImageButton btn_yolculuklarim;
	ImageButton btn_yolculukara;
	ImageButton btn_hakkimizda;
	ImageButton btn_yardim;
	ImageButton btn_cikis;
	
	GPSTracker gps;
	SharedPreferences prefs;
	
	String get_uye_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		
		setContentView(R.layout.profil);
		
		prefs = getSharedPreferences("ex1",MODE_PRIVATE); 
		
		get_uye_id = prefs.getString("get_UyeID","UyeID alınamadı");
		Log.i("Get_Uye_id", get_uye_id);
		
		btn_userinformation = (ImageButton) findViewById(R.id.imageButton3);
		btn_panikbutton = (ImageButton) findViewById(R.id.imageButton7);
		btn_yolculuklarim = (ImageButton) findViewById(R.id.imageButton2);
		btn_yolculukara = (ImageButton) findViewById(R.id.imageButton1);
		btn_hakkimizda = (ImageButton) findViewById(R.id.imageButton4);
		btn_yardim = (ImageButton) findViewById(R.id.imageButton5);
		btn_cikis = (ImageButton) findViewById(R.id.imageButton6);
		
		btn_cikis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				Toast.makeText(getApplicationContext(), "Pek Yakında", Toast.LENGTH_SHORT).show();
				
				Intent exit = new Intent(getApplicationContext(),StartupPage.class);
				exit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(exit);
				
			}
		});
		
		btn_yardim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				Toast.makeText(getApplicationContext(), "Pek Yakında", Toast.LENGTH_SHORT).show();
				
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.uygungidelim.com/Cms.asp?ID=28"));
				startActivity(browserIntent);
			}
		});
		
		btn_hakkimizda.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				Toast.makeText(getApplicationContext(), "Pek Yakında", Toast.LENGTH_SHORT).show();
				
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.uygungidelim.com/Cms.asp?ID=26"));
				startActivity(browserIntent);
				
			}
		});
		
		btn_yolculukara.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				Toast.makeText(getApplicationContext(), "Pek Yakında", Toast.LENGTH_SHORT).show();
				
				Intent go_yolculuk_ara = new Intent(getApplicationContext(),LocationSelector.class);
		    	startActivity(go_yolculuk_ara);
				
			}
		});
		
		btn_yolculuklarim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent go_yolculuk = new Intent(getApplicationContext(),MainActivity.class);
		    	startActivity(go_yolculuk);
				
			}
		});
		
		btn_panikbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				gps = new GPSTracker(ProfilPage.this);
				
//				Intent go_panikbuton = new Intent(getApplicationContext(),AndroidGPSTrackingActivity.class);
//				startActivity(go_panikbuton);
				
				// check if GPS enabled		
		        if(gps.canGetLocation()){
		        	
		        	double latitude = gps.getLatitude();
		        	double longitude = gps.getLongitude();
		        	
					 String urlTopass = makeURL(get_uye_id,latitude,longitude);
					 Log.i("@Log", "Asynctaskgiris");
				        new connectAsyncTask(urlTopass).execute();
		        	
		        	// \n is for new line
//		        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
		        }else{
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings
		        	gps.showSettingsAlert();
		        }
				
			}
		});
		
		btn_userinformation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent go = new Intent(getApplicationContext(),UserInformation.class);
				startActivity(go);
				
			}
		});
		

	}



	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;

        connectAsyncTask(String urlPass) {
        	
            url = urlPass;
            
            progressDialog = new ProgressDialog(ProfilPage.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
            
            Log.i("@Log", "AsynctaskGirildi");
            progressDialog.setMessage("Lütfen bekleyiniz...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            
            
        }    

        @Override
        protected String doInBackground(Void... params) {
        	Log.i("@Log", "doInbackgroundgirildi");
            JSONParser jParser = new JSONParser();
             json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            
           result = json;
         
            	
            	Log.i("@Log", "GetInfoya git");
                getInfo(result);
            
            Log.i("@Log", "progressDialog dismiss");
            progressDialog.dismiss();
            
            
            if(gelen_mesaj.equals("ok")){
            	
            	// Go Profil menu
            	
            	Toast.makeText(getApplicationContext(), get_mesaj, Toast.LENGTH_LONG).show();
            	
            	Intent go_profil_page = new Intent(getApplicationContext(),ProfilPage.class);
            	go_profil_page.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            	
            	startActivity(go_profil_page);
            }
            else{
            	
            	Toast.makeText(getApplicationContext(), hata_mesaj, Toast.LENGTH_SHORT).show();
            	
            }
            
        }
    }
	
	
	public String makeURL(String uye_id,double sayi1,double sayi2) {
		Log.i("@Log", "MakeUrl1");
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://www.uygungidelim.com/mobile/PanicService.asp?Islem=Panic&");
        Log.i("@Log", "MakeUrl2");
        urlString.append("UyeID=" + uye_id +"&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("CX="+sayi1 + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("CY="+sayi2);
        return urlString.toString();
       
    }
	
	public class JSONParser {

        InputStream is = null;
        JSONObject jObj = null;
        

        // constructor
        public JSONParser() {
        }

        public String getJSONFromUrl(String url) {

        	 try {
                 // defaultHttpClient
                 DefaultHttpClient httpClient = new DefaultHttpClient();
                  httpget = new HttpGet(url);

                 HttpResponse httpResponse = httpClient.execute(httpget);
                 HttpEntity httpEntity = httpResponse.getEntity();
                 is = httpEntity.getContent();

             } catch (UnsupportedEncodingException e) {
            	 
            	 Toast.makeText(getApplicationContext(), "Servis Bağlantısı Başarısız", Toast.LENGTH_SHORT).show();
            	 
                 e.printStackTrace();
             } catch (ClientProtocolException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 BufferedReader reader = new BufferedReader(
                         new InputStreamReader(is));
                 StringBuilder sb = new StringBuilder();
                 String line = null;
                 while ((line = reader.readLine()) != null) {
                     sb.append(line + "\n");
                 }

                 jsn = sb.toString();
                 is.close();
             } catch (Exception e) {
                 Log.e("Buffer Error", "Error converting result " + e.toString());
             }
             return jsn;

        }
        
        
        
	}
	
    public void getInfo(String result) {

        try {
        	
        	Log.i("@Log", "GetInfo");
            // Tranform the string into a json object
        	
            final JSONObject json = new JSONObject(result);
            Log.i("@Log", "GetInfogiris");
            
             gelen_mesaj = json.getString("return");
            Log.i("Log gelen_mesaj", ""+ gelen_mesaj);
            
             hata_mesaj = json.getString("hata");
            Log.i("Log hata", ""+hata_mesaj);
            
            JSONObject get_data = json.getJSONObject("data");
            get_mesaj = get_data.getString("Mesaj");
            Log.i("Log gelen_Login", ""+ get_mesaj);
            

            

            
//              gelen_mesaj.setText(encodedString);

	
	

        } catch (Exception e) {
        		e.printStackTrace();
        }
    }
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		Intent go_startup = new Intent(getApplicationContext(),StartupPage.class);
		go_startup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(go_startup);
	}

}
