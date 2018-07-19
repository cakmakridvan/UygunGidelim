package com.loginpage;

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

import com.signinpage.ProfilPage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class AuthenticationPage extends Activity {
	
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
	
	String gelen_mesaj;
	String hata_mesaj;
	
	String json;
	
	String get_uyeid;
	EditText get_validate_code;
	String validate_code;
	Button send_login_service;
	
	 NetworkInfo ni;
	 ConnectivityManager cm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 


		
		setContentView(R.layout.authentication);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    get_uyeid = extras.getString("send_uyeid");
		    Log.i("Log_geted_uyeid", ""+get_uyeid);
		}
		
	    get_validate_code = (EditText) findViewById(R.id.edt_get_validate_code);
	    send_login_service = (Button) findViewById(R.id.btn_send_login_service);
 
	    send_login_service.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				 ni = cm.getActiveNetworkInfo();
				
			    validate_code = get_validate_code.getText().toString();
			    
			    Log.i("validate code", ""+validate_code);
			    
/*			    if(validate_code.equals("")){
			    	
			    	Toast.makeText(getApplicationContext(), "Lütfen kodunuzu giriniz.", Toast.LENGTH_LONG).show();
			    }*/
			    
			    if (ni != null && ni.isConnected()) {
			    	
					  String urlTopass = makeURL(get_uyeid,validate_code);
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
            
            progressDialog = new ProgressDialog(AuthenticationPage.this);
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
            	
            	//Go ProfilPage
            	
            	Toast.makeText(getApplicationContext(), "Kayıt işlemi tamamlanmıştır", Toast.LENGTH_SHORT).show();
            	
            	Intent goes_profil_page = new Intent(getApplicationContext(),ProfilPage.class);
            	startActivity(goes_profil_page);
            	

            }
            else{
            	
            	Toast.makeText(getApplicationContext(), hata_mesaj, Toast.LENGTH_SHORT).show();
            	
            }
            
        }
    }
	
	public String makeURL(String uye_id,String kod) {
		
		Log.i("@Log", "MakeUrl1");
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://www.uygungidelim.com/mobile/LoginService.asp?Islem=TelefonOnay&");
        Log.i("@Log", "MakeUrl2");
        urlString.append("UyeID=" + uye_id + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("TelefonOnay=" + kod);
        Log.i("@Log", "MakeUrl3");

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
//            
            JSONObject get_data = json.getJSONObject("data");
            String get_login_mesaj = get_data.getString("Login");
            Log.i("Log gelen_Login", ""+ get_login_mesaj);
            
            String get_token_id = get_data.getString("TokenID");
            Log.i("Log gelen_TokenID", ""+get_token_id);
            
            String get_Uye_Adi = get_data.getString("Adi");
            Log.i("Log gelen_Uye_Adi", ""+get_Uye_Adi);
            
            String get_Uye_Soyadi = get_data.getString("Soyadi");
            Log.i("Log gelen_Uye_Soyadi", ""+get_Uye_Soyadi);
           
            String get_Uye_ID = get_data.getString("ID");
            Log.i("Log gelen_Uye_ID", ""+get_Uye_ID);
            
            String get_Uye_Eposta = get_data.getString("EPosta");
            Log.i("Log gelen_Uye_EPosta", ""+get_Uye_Eposta);
            
            String get_Uye_CepTel = get_data.getString("CepTel");
            Log.i("Log gelen_Uye_CepTel", ""+get_Uye_CepTel);
//            
//            String e_posta = get_data.getString("EPosta");
//            Log.i("Log gelen_EPosta", ""+e_posta);
//            
//            String cep_tel = get_data.getString("CepTel");
//            Log.i("Log gelen_CepTel",""+cep_tel);
            
            
//              gelen_mesaj.setText(encodedString);

	
	

        } catch (Exception e) {
        		e.printStackTrace();
        }
    }


}
