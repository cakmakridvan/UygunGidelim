 package com.signinpage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;








import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


import com.loginpage.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Signin extends Activity  {
	
	EditText get_eposta,get_parola;
	Button giris;
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
	
	String get_Eposta_data;
	String get_parola_data;
	
	String json;
	
	String gelen_mesaj;
	
	 String hata_mesaj;
	 
	 NetworkInfo ni;
	 
	 ConnectivityManager cm;
	 
	 SharedPreferences app_preferences;
	 
	 SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		
		setContentView(R.layout.signin);
		
		//SharedPrefencesss
		app_preferences = getSharedPreferences("ex1", MODE_PRIVATE);
		
		get_eposta = (EditText) findViewById(R.id.edt_signin_eposta);
		get_parola = (EditText) findViewById(R.id.edt_signin_parola);
		
		giris = (Button) findViewById(R.id.btn_giris);
		

			  
			   // There are no active networks.

			 
		
		giris.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				  ni = cm.getActiveNetworkInfo();
				
				
				get_Eposta_data = get_eposta.getText().toString();
				get_parola_data = get_parola.getText().toString();
				
				if (ni != null && ni.isConnected()) {
					
					 String urlTopass = makeURL(get_Eposta_data,get_parola_data);
					 Log.i("@Log", "Asynctaskgiris");
				        new connectAsyncTask(urlTopass).execute();
					
					
				}
				
				else{
				
					Toast.makeText(getApplicationContext(), "İnternet Bağlantınızı Kontrol ediniz", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
	
	}

	 

	
	// Motion
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		View view = getCurrentFocus();
		boolean ret = super.dispatchTouchEvent(event);

		if (view instanceof EditText) {
			View w = getCurrentFocus();
			int scrcoords[] = new int[2];
			w.getLocationOnScreen(scrcoords);
			float x = event.getRawX() + w.getLeft() - scrcoords[0];
			float y = event.getRawY() + w.getTop() - scrcoords[1];

			if (event.getAction() == MotionEvent.ACTION_UP
					&& (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
							.getBottom())) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
			}
		}
		return ret;
	}
	

	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;

        connectAsyncTask(String urlPass) {
        	
            url = urlPass;
            
            progressDialog = new ProgressDialog(Signin.this);
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
            	
//            	Toast.makeText(getApplicationContext(), "Sistem giriş Başarılı", Toast.LENGTH_SHORT).show();
            	
            	Intent go_profil_page = new Intent(getApplicationContext(),ProfilPage.class);
            	go_profil_page.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            	
            	startActivity(go_profil_page);
            }
            else{
            	
            	Toast.makeText(getApplicationContext(), hata_mesaj, Toast.LENGTH_SHORT).show();
            	
            }
            
        }
    }
	
	public String makeURL(String sayi,String num) {
		Log.i("@Log", "MakeUrl1");
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://www.uygungidelim.com/mobile/LoginService.asp?Islem=Login&EPosta=");
        Log.i("@Log", "MakeUrl2");
        urlString.append(sayi +"&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("Parola="+num);
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
            String get_login = get_data.getString("Login");
            Log.i("Log gelen_Login", ""+ get_login);
            
            String get_Tokenid = get_data.getString("TokenID");
            Log.i("Log gelen_TokenID", ""+get_Tokenid);
            
            String get_Adi = get_data.getString("Adi");
            Log.i("Log gelen_Adi", ""+get_Adi);
            
            String get_Soyadi = get_data.getString("Soyadi");
            Log.i("Log gelen_Soyadi", ""+get_Soyadi);
            
            String get_UyeID = get_data.getString("UyeID");
            Log.i("Log gelen_UyeID", ""+get_UyeID);
            
            String e_posta = get_data.getString("EPosta");
            Log.i("Log gelen_EPosta", ""+e_posta);
            
            String cep_tel = get_data.getString("CepTel");
            Log.i("Log gelen_CepTel",""+cep_tel);
            
            //SharedPrefencesss
            editor = app_preferences.edit();
            
            editor.putString("get_UyeAdi", get_Adi);
            editor.putString("get_UyeSoyadi", get_Soyadi);
            editor.putString("get_UyeID", get_UyeID);
            editor.putString("get_UyeEposta", e_posta);
            editor.putString("get_UyeCep", cep_tel);
            
            editor.commit();
            
            
//              gelen_mesaj.setText(encodedString);

	
	

        } catch (Exception e) {
        		e.printStackTrace();
        }
    }


}
