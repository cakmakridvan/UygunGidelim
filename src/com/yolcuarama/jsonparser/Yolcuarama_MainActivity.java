package com.yolcuarama.jsonparser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import com.loginpage.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class Yolcuarama_MainActivity extends Activity {
	// Declare Variables
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	Yolcuarama_ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;
	static String RANK = "rank";
	static String COUNTRY = "country";
	static String POPULATION = "population";
	static String TARIH = "tarih";
	static String TIP = "tip";
	static String DURUM = "durum";
	static String FLAG = "flag";
	static String Mesafe = "mesafe";
	static String BosKoltuk = "boskoltuk";
	static String YolcuAdi = "yolcuadi";
	static String YolcuSoyadi = "yolcusoyadi";
	static String YolculugaKatilim = "yolculukkatilim";
	static String YolculugaKatilimMesaj = "yolculukkatilimmesaj";
	static String CikisLat = "cikislat";
	static String CikisLong = "cikislong"; 	
	static String VarisLat = "varislat"; 	
	static String VarisLong = "varislong"; 	
	static String YolculukID = "yolculukid";
	
	Double getX_lat;
	Double getX_long;
	Double getY_lat;
	Double getY_long;
	String get_gun,get_ay,get_yil;
	
	String hata_mesajı;
	String gelen_mesaj;
	String yolcu_id;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		
		// Get the view from listview_main.xml
		setContentView(R.layout.yolcu_arama_listview_main);
		// Execute DownloadJSON AsyncTask
		
		Intent in = getIntent();
		Bundle b = in.getExtras();
		
		getX_lat = b.getDouble("latX");
		Log.i("@Log_Yolcu_arama_getX_lat", ""+getX_lat);
		getX_long = b.getDouble("longX");
		Log.i("@Log_Yolcu_arama_getX_long", ""+getX_long);
		getY_lat = b.getDouble("latY");
		Log.i("@Log_Yolcu_arama_getY_lat", ""+getY_lat);
		getY_long = b.getDouble("longY");
		Log.i("@Log_Yolcu_arama_getY_lat", ""+getY_long);
		
		get_gun = b.getString("trh_gun");
		Log.i("@Log_Yolcu_arama_trhgun", ""+get_gun);
		get_ay = b.getString("trh_ay");
		Log.i("@Log_Yolcu_arama_trhay", ""+get_ay);
		get_yil = b.getString("trh_yil");
		Log.i("@Log_Yolcu_arama_trhyıl", ""+get_yil);
		
		
		new DownloadJSON().execute();
	}

	 


	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(Yolcuarama_MainActivity.this);
			// Set progressdialog title
		    mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.setCancelable(false);
			
			mProgressDialog.setTitle("Uygun Gidelim");
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
			
			
			
			jsonobject = Yolcuarama_JSONfunctions
					.getJSONfromURL("http://www.uygungidelim.com/mobile/YolculukService.asp?Islem=Arama&CikisCX=" + getX_lat + "&" +  "CikisCY=" + getX_long + "&" +  "VarisCX=" + getY_lat + "&" +  "VarisCY=" + getY_long + "&" + "Tarih=" + get_yil + "-" + get_ay + "-" + get_gun);

			try {
				
				gelen_mesaj = jsonobject.getString("return");
				Log.i("@Log_gelenmesaj", ""+gelen_mesaj);
				hata_mesajı = jsonobject.getString("hata");
				Log.i("@Log_hatamesaj", ""+hata_mesajı);
				
				// Locate the array name in JSON
				jsonarray = jsonobject.getJSONArray("data");

				for (int i = 0; i < jsonarray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					jsonobject = jsonarray.getJSONObject(i);
					// Retrive JSON Objects
					map.put("rank", jsonobject.getString("Nereden"));
					map.put("country", jsonobject.getString("Nereye"));
					map.put("population", jsonobject.getString("MasrafPayi"));
					map.put("tarih", jsonobject.getString("YolculukTarihi"));
					map.put("tip", jsonobject.getString("Tip"));
					map.put("durum", jsonobject.getString("Durum"));
					map.put("flag", jsonobject.getString("ProfilResmi"));
					map.put("mesafe",jsonobject.getString("MesafeKM"));
					map.put("boskoltuk",jsonobject.getString("BoskoltukSayisi"));
					map.put("yolcuadi",jsonobject.getString("Adi"));
					map.put("yolcusoyadi",jsonobject.getString("Soyadi"));
					map.put("yolculukkatilim",jsonobject.getString("YolculugaKatilim"));
					map.put("yolculukkatilimmesaj",jsonobject.getString("YolculugaKatilimMesaj"));
					map.put("cikislat",jsonobject.getString("CikisLat"));
					map.put("cikislong",jsonobject.getString("CikisLong"));
					map.put("varislat",jsonobject.getString("VarisLat"));
					map.put("varislong",jsonobject.getString("VarisLong"));
					map.put("yolculukid",jsonobject.getString("YolculukID"));
					
					yolcu_id = jsonobject.getString("YolculukID");
					Log.i("@Log_yolculuk_id", ""+yolcu_id);
					
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
	if(gelen_mesaj.equals("ok")){
		
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new Yolcuarama_ListViewAdapter(Yolcuarama_MainActivity.this, arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			
			// Close the progressdialog
			mProgressDialog.dismiss();
		}else{
			
			Toast.makeText(getApplicationContext(), hata_mesajı, Toast.LENGTH_LONG).show();
			
			mProgressDialog.dismiss();
		}

		}
	}
}