package com.androidbegin.jsonparsetutorial;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.loginpage.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// Declare Variables
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;
	static String RANK = "rank";
	static String COUNTRY = "country";
	static String POPULATION = "population";
	static String TARIH = "tarih";
	static String FLAG = "flag";
	static String Adi = "yolcuadi";
	static String Soyadi = "yolcusoyadi";
	static String CikisLat = "cikislat";
	static String CikisLong = "cikislong";
	static String VarisLat = "varislat";
	static String VarisLong = "varislong";
	static String Mesafe = "mesafe";
	static String BosKoltuk = "boskoltuksayisi";
	
	SharedPreferences prefs;
	String get_uye_id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Get the view from listview_main.xml
		
		prefs = getSharedPreferences("ex1",MODE_PRIVATE); 
		
		get_uye_id = prefs.getString("get_UyeID","UyeID alınmadı");
		Log.i("Log_uyeidi", get_uye_id);
		
		setContentView(R.layout.listview_main);
		// Execute DownloadJSON AsyncTask
		new DownloadJSON().execute();
	}
	

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(MainActivity.this);
			// Set progressdialog title
			
		    mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.setCancelable(false);
			
			// Set progressdialog message
			mProgressDialog.setMessage("Lütfen Bekleyiniz...");
			mProgressDialog.setIndeterminate(true);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			
			
			
			jsonobject = JSONfunctions
					.getJSONfromURL("http://www.uygungidelim.com/mobile/YolculukService.asp?Islem=Yolculuklarim&UyeID="+get_uye_id);
			
			

			try {
				// Locate the array name in JSON
				String gelen_mesaj = jsonobject.getString("return");
				Log.i("Log_gelenmesaj", ""+gelen_mesaj);
				
				String hata = jsonobject.getString("hata");
				Log.i("Log_hata", ""+hata);
				
				JSONObject get_data = jsonobject.getJSONObject("pagination");
				String get_toplamsayfa = get_data.getString("toplamsayfa");
				Log.i("Log_Toplamsayfa", ""+get_toplamsayfa);
				String get_bulundugusayfa = get_data.getString("bulundugunsayfa");
				Log.i("Log_Bulunduğusayfa", ""+get_bulundugusayfa);
				
				
				
				jsonarray = jsonobject.getJSONArray("data");

				for (int i = 0; i < jsonarray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					jsonobject = jsonarray.getJSONObject(i);
					
					Log.i("@Logjsonobject", ""+jsonobject);
					// Retrive JSON Objects
					map.put("rank", jsonobject.getString("Nereden"));
					map.put("country", jsonobject.getString("Nereye"));
					map.put("population", jsonobject.getString("MasrafPayi"));
					map.put("tarih", jsonobject.getString("YolculukTarihi"));
					map.put("flag", jsonobject.getString("ProfilResmi"));
					map.put("yolcuadi", jsonobject.getString("Adi"));
					map.put("yolcusoyadi", jsonobject.getString("Soyadi"));
					map.put("cikislat", jsonobject.getString("CikisLat"));
					map.put("cikislong", jsonobject.getString("CikisLong"));
					map.put("varislat", jsonobject.getString("VarisLat"));
					map.put("varislong", jsonobject.getString("VarisLong"));
					map.put("mesafe", jsonobject.getString("MesafeKM"));
					map.put("boskoltuksayisi", jsonobject.getString("BoskoltukSayisi"));
					// Set the JSON Objects into the array
					arraylist.add(map);
				}
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
	
			
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(MainActivity.this, arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
}