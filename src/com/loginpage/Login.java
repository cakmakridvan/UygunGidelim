package com.loginpage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

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
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	
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
	
	EditText get_Name,get_Surname,get_TcNumber,get_Eposta,get_Ceptel,get_DogumTarihi_gun,get_DogumTarihi_ay,get_DogumTarihi_yil,get_Parola,get_ParolaTekrar;
	Button Submit;
	
	String getName_data;
	String getSurname_data;
	String getTc_data;
	String getEposta_data;
	String getCeptel_data;
	String getDogum_gun_data;
	String getDogum_ay_data;
	String getDogum_yil_data;
	String getParola_data;
	String getParolatekrar_data;
	
	String dtarihgun;
	String dtarihay;
	String dtarihyil;
	
	 NetworkInfo ni;
	 
	 ConnectivityManager cm;
	 
		String gelen_mesaj;
		
		 String hata_mesaj;
		 
		 String get_yeniuye_id;
		 
		 SharedPreferences app_preferences;
		 
		 SharedPreferences.Editor editor;
		 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		
		setContentView(R.layout.login);
		
		//SharedPrefencesss
		app_preferences = getSharedPreferences("ex1", MODE_PRIVATE);
		
		get_Name = (EditText) findViewById(R.id.edt_txt_name);
		get_Surname = (EditText) findViewById(R.id.edt_txt_soyad);
		get_TcNumber = (EditText) findViewById(R.id.edt_txt_tckimilkno);
		get_Eposta = (EditText) findViewById(R.id.edt_txt_eposta);
		get_Ceptel = (EditText) findViewById(R.id.edt_txt_ceptel);
		get_DogumTarihi_gun = (EditText) findViewById(R.id.edt_txt_tarih_gun);
		get_DogumTarihi_ay = (EditText) findViewById(R.id.edt_txt_tarih_ay);
		get_DogumTarihi_yil = (EditText) findViewById(R.id.edt_txt_tarih_yil);
		get_Parola = (EditText) findViewById(R.id.edt_parola);
		get_ParolaTekrar = (EditText) findViewById(R.id.edt_parolatekrar);
		
		Submit = (Button) findViewById(R.id.btn_submit);
		Submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				 ni = cm.getActiveNetworkInfo();
				 
				 getName_data = get_Name.getText().toString();
				 getSurname_data = get_Surname.getText().toString();
				 getTc_data = get_TcNumber.getText().toString();
				 getEposta_data = get_Eposta.getText().toString();
				 getCeptel_data = get_Ceptel.getText().toString();
				 getDogum_gun_data = get_DogumTarihi_gun.getText().toString();
				 getDogum_ay_data = get_DogumTarihi_ay.getText().toString();
				 getDogum_yil_data = get_DogumTarihi_yil.getText().toString();
				 getParola_data = get_Parola.getText().toString();
				 getParolatekrar_data = get_ParolaTekrar.getText().toString();
				 
				 

					
				     if(getName_data.equals("") || getSurname_data.equals("") || getTc_data.equals("") || getEposta_data.equals("") || getCeptel_data.equals("") || getDogum_gun_data.equals("") || getDogum_ay_data.equals("") || getDogum_yil_data.equals("") || getParola_data.equals("") || getParolatekrar_data.equals("") )
						
					{
						
						Toast.makeText(getApplicationContext(), "Lütfen eksik bilgilerinizi giriniz.", Toast.LENGTH_LONG).show();
					}
				     
				     else if(getTc_data.length()<11){
				    	 
				    	 Toast.makeText(getApplicationContext(), "Tc numaranız 11 haneli olmalıdır", Toast.LENGTH_LONG).show();
				     }
					
					else if(!(getParola_data.equals(getParolatekrar_data))){
						
						Toast.makeText(getApplicationContext(), "Parolanız Uyuşmamaktadır,Lütfen Kontrol Ediniz.", Toast.LENGTH_LONG).show();
					}
				     
					else{
					    if (ni != null && ni.isConnected()) {
					    	
							  String urlTopass = makeURL(getName_data,getSurname_data,getTc_data,getEposta_data,getCeptel_data,getDogum_gun_data,getDogum_ay_data,getDogum_yil_data,getParola_data,getParolatekrar_data);
							  Log.i("@Log", "Asynctaskgiris");
						         new connectAsyncTask(urlTopass).execute();
						
						
					   }
					
					    else{
					    	
//						     StringTokenizer st = new StringTokenizer(getDogum_data, ".");
//						        dtarihgun = st.nextToken();
//						        dtarihay = st.nextToken(); 
//						        dtarihyil = st.nextToken();
						
					    	 Toast.makeText(getApplicationContext(), "İnternet Bağlantınızı Kontrol ediniz", Toast.LENGTH_LONG).show();
						 }
				 
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
            
            progressDialog = new ProgressDialog(Login.this);
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
            	
//            	Toast.makeText(getApplicationContext(), "Sistem giriş Başarılı", Toast.LENGTH_SHORT).show();
            	
            	// Go Authentication page
            	
            	Intent go_Authentication = new Intent(getApplicationContext(),AuthenticationPage.class);
            	go_Authentication.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            	go_Authentication.putExtra("send_uyeid",get_yeniuye_id);
            	startActivity(go_Authentication);
            }
            else{
            	
            	Toast.makeText(getApplicationContext(), hata_mesaj, Toast.LENGTH_SHORT).show();
            	
            }
            
        }
    }
	
	public String makeURL(String isim,String soyisim,String Tc,String eposta,String cepno,String t_gun,String t_ay,String t_yil,String parola,String parolayeniden) {
		Log.i("@Log", "MakeUrl1");
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://www.uygungidelim.com/mobile/LoginService.asp?Islem=Ekle&");
        Log.i("@Log", "MakeUrl2");
        urlString.append("Adi=" + isim + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("Soyadi=" + soyisim + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("TCNo=" + Tc + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("dtarihgun=" + t_gun + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("dtarihay=" + t_ay + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("dtarihyil=" + t_yil + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("EPosta=" + eposta + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("CepTel=" + cepno + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("Parola=" + parola + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("Parola2=" + parolayeniden);
        
        
        
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
            get_yeniuye_id = get_data.getString("YeniUyeID");
            Log.i("Log gelen_Login", ""+ get_yeniuye_id);
            
            String get_ActivationCode = get_data.getString("ActivationCode");
            Log.i("Log gelen_TokenID", ""+get_ActivationCode);
            
            String get_Uye_Adi = get_data.getString("Adi");
            Log.i("Log gelen_Uye_Adi", ""+get_Uye_Adi);
            
            String get_Uye_Soyadi = get_data.getString("Soyadi");
            Log.i("Log gelen_Uye_Soyadi", ""+get_Uye_Soyadi);
           
            String get_Uye_Eposta = get_data.getString("EPosta");
            Log.i("Log gelen_Uye_Eposta", ""+get_Uye_Eposta);
            
            String get_Uye_CepTel = get_data.getString("CepTel");
            Log.i("Log gelen_Uye_CepTel", ""+get_Uye_CepTel);
            
            //SharedPrefencesss
            editor = app_preferences.edit();
            
            editor.putString("get_UyeAdi", get_Uye_Adi);
            editor.putString("get_UyeSoyadi", get_Uye_Soyadi);
            editor.putString("get_UyeID", get_yeniuye_id);
            editor.putString("get_UyeEposta", get_Uye_Eposta);
            editor.putString("get_UyeCep", get_Uye_CepTel);
            
            editor.commit();
            
            

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
