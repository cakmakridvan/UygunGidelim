package com.yolcuarama.jsonparser;





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.androidbegin.jsonparsetutorial.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loginpage.R;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Yolcuarama_SingleItemView extends Activity {
	
	private static final int ACTION_GOOGLE_PLAY_SERVICES = 100;
	
	String jsn = "";
	HttpClient client;
	HttpGet httpget;
	HttpResponse response;
	HttpEntity entity; 
	
	String get_Kayit_mesaj;
	
	final Context context = this;
	
	SharedPreferences prefs;
	
	 NetworkInfo ni;
	 
	 ConnectivityManager cm;
	
	// Declare Variables
	String yolculukid;
	String yolculuk_katilim;
	String yolculuk_katilim_mesaj;
	String cikis_latitude;
	String cikis_longitude;
	String varis_latitude;
	String varis_longitude;
	String mesafe;
	String boskoltuksayisi;
	String yolcu_adi;
	String yolcu_soyadi;
	String durum;
	String tip;
	String tarih;
	String rank;
	String country;
	String population;
	String flag;
	String position;
	
	String gelen_mesaj;
	
	 String hata_mesaj;
	
	Button myButton;
	
	JSONObject obj;
	
	String json;
	
	double cikis_latitude_doble;
	double cikis_longitude_double;
	double varis_latitude_double;
	double varis_longitude_double;
	Yolcuarama_ImageLoader imageLoader = new Yolcuarama_ImageLoader(this);
	
	ImageView imageView, imageView2;
	RoundImage roundedImage, roundedImage1;
	
	String get_uye_id;
	
	private GoogleMap map;
	private ArrayList<Marker> markers;
	private ArrayList<Polyline> polylines;
	private ArrayList<Place> places;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		
		// Get the view from singleitemview.xml
		setContentView(R.layout.yolcu_arama_singleitemview);
		
		int result = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		if (result == ConnectionResult.SUCCESS) {
			initialize();
			addDummyLocations();
			drawMarkers(places);
			drawPolylines(places);
		} else {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(result, this,
					ACTION_GOOGLE_PLAY_SERVICES);
			dialog.show();
		}
		



		
		if(yolculuk_katilim.equals("1")){
			
			myButton = new Button(this);
			myButton.setTextSize(20);
			myButton.setText(Html.fromHtml("<b>" + "Yolculuğa Katıl" + "</b>"));
			

			
			myButton.setBackgroundColor(Color.parseColor("#a0c635"));
			
			myButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					 cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					 ni = cm.getActiveNetworkInfo();
					
					
					    if (ni != null && ni.isConnected()) {
					    	
							
							  String urlTopass = makeURL(get_uye_id,yolculukid);
							  Log.i("@Log", "Asynctaskgiris");
						         new connectAsyncTask(urlTopass).execute();
						
						 
					   }
					
					    else{
					    	
					    	
					    	Toast.makeText(getApplicationContext(), "İnternet Bağlantınızı Kontrol ediniz", Toast.LENGTH_LONG).show();
					    	
//						     StringTokenizer st = new StringTokenizer(getDogum_data, ".");
//						        dtarihgun = st.nextToken();
//						        dtarihay = st.nextToken(); 
//						        dtarihyil = st.nextToken();

						 }
				 
					
					
					
					
				}
			});
			
			myButton.setOnTouchListener(new OnTouchListener() {

		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            if(event.getAction() == MotionEvent.ACTION_DOWN){
		                //Button Pressed
		            	
		            	myButton.setBackgroundColor(Color.parseColor("#6c8526"));
		            }
		            if(event.getAction() == MotionEvent.ACTION_UP){
		                 //finger was lifted
		            	
		            	myButton.setBackgroundColor(Color.parseColor("#a0c635"));
		            }
		            return false;
		        }
		    });

			LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ll.setGravity(Gravity.CENTER);
			ll.addView(myButton, lp);
		}
		else if(yolculuk_katilim.equals("0")){
			
			TextView mesaj = new TextView(this);
			mesaj.setText(Html.fromHtml("<b>" + "Yolculuk durumu: " + "</b>" + yolculuk_katilim_mesaj));
			mesaj.setTextColor(Color.parseColor("#000000"));
			mesaj.setTextSize(20);
		
			
			LinearLayout ll = (LinearLayout)findViewById(R.id.button_layout);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ll.setGravity(Gravity.CENTER);
			ll.addView(mesaj, lp);
		}

		
		//imageView settings
		imageView = (ImageView) findViewById(R.id.imageView1);
		

		
        imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				// custom dialog
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.custom_photo);
				dialog.setTitle(yolcu_adi +" "+ yolcu_soyadi);
				

				// set the custom dialog components - text, image and button
//				TextView text = (TextView) dialog.findViewById(R.id.text);
//				text.setText("Android custom dialog example!");
				ImageView image = (ImageView) dialog.findViewById(R.id.image_custom_photo);
	        Picasso.with(getApplicationContext()).load(flag).into(image);
/*				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});*/

				dialog.show();
				
			}
		});


	    	    	 

	    	    	 
		try {
			
			Picasso.with(getApplicationContext()).load(flag).transform(new CircleTransform()).into(imageView);
			
//	        Picasso.with(getApplicationContext())
//            .load(flag)
//            .into(imageView);
			
//			imageLoader.DisplayImage(flag, imageView1);
		}
		catch (Exception e) {
		    Log.e("MyTag", "Failure to get drawable id.", e);
		}

	    			
	    			
	    			

		
		

		
		

		// Locate the TextViews in singleitemview.xml
		TextView txtrank = (TextView) findViewById(R.id.txt_nereden);
		TextView txtcountry = (TextView) findViewById(R.id.txt_nereye);
		TextView txtpopulation = (TextView) findViewById(R.id.txt_masraf);
		TextView txttarih = (TextView) findViewById(R.id.get_tarih);
		TextView txttip = (TextView) findViewById(R.id.get_Tip);
		TextView txtdurum = (TextView) findViewById(R.id.get_durum);
		TextView txtmesafe = (TextView) findViewById(R.id.get_mesafe);
		TextView txtboskoltuksayisi = (TextView) findViewById(R.id.get_boskoltuksayisi);
		TextView txtyolcuadi = (TextView) findViewById(R.id.get_yolcuadi);
		TextView txtyolcusoyadi = (TextView) findViewById(R.id.get_yolcusoyadi);
		

		// Locate the ImageView in singleitemview.xml
//		ImageView imgflag = (ImageView) findViewById(R.id.flag);

		// Set results to the TextViews
		txtrank.setText(rank);
		txtcountry.setText(country);
		txtpopulation.setText(population);
		txttarih.setText(tarih);
		txttip.setText(tip);
		txtdurum.setText(durum);
		txtmesafe.setText(mesafe);
		txtboskoltuksayisi.setText(boskoltuksayisi);
		txtyolcuadi.setText(yolcu_adi);
		txtyolcusoyadi.setText(yolcu_soyadi);

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
//		imageLoader.DisplayImage(flag, imgflag);
	}
	

	 
	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;

        connectAsyncTask(String urlPass) {
            url = urlPass;
            
            progressDialog = new ProgressDialog(Yolcuarama_SingleItemView.this);
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
            	
             	Toast.makeText(getApplicationContext(), get_Kayit_mesaj, Toast.LENGTH_LONG).show();
            	
            	// Go Yolculuklarım(SingleItemView) in com.androidbegin.jsonparsetutorial page
             	
             	new Handler().postDelayed(new Runnable() {
             	    @Override
             	    public void run() {
             	        
             	    	Intent go_yolculuklarim = new Intent(getApplicationContext(),MainActivity.class);
             	    	startActivity(go_yolculuklarim);
             	    }
             	}, 2000);
            	

            }
            else{
            	
            	Toast.makeText(getApplicationContext(), hata_mesaj, Toast.LENGTH_LONG).show();
            	
            }
            
        }
    }
	
	
	public String makeURL(String uye_id,String yolculuk_id) {
		Log.i("@Log", "MakeUrl1");
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://www.uygungidelim.com/mobile/YolculukService.asp?Islem=RezervasyonYap&");
        Log.i("@Log", "MakeUrl2");
        urlString.append("UyeID=" + uye_id + "&");
        Log.i("@Log", "MakeUrl3");
        urlString.append("YolculukID=" + yolculuk_id);
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
     
            get_Kayit_mesaj = get_data.getString("Mesaj");
            Log.i("Log gelen_Uye_CepTel", ""+get_Kayit_mesaj);
            


        } catch (Exception e) {
        		e.printStackTrace();
        }
    }
	
	

	private void initialize() {
		
		Intent i = getIntent();
		// Get the result of rank
		rank = i.getStringExtra("rank");
		// Get the result of country
		country = i.getStringExtra("country");
		// Get the result of population
		population = i.getStringExtra("population");
		// Get the result of flag
		tarih = i.getStringExtra("tarih");
		
		tip = i.getStringExtra("tip");
		
		durum = i.getStringExtra("durum");
		
		flag = i.getStringExtra("flag");
		
		mesafe = i.getStringExtra("mesafe");
		
		boskoltuksayisi = i.getStringExtra("boskoltuk");
		
		yolcu_adi = i.getStringExtra("yolcuadi");
		
		yolcu_soyadi = i.getStringExtra("yolcusoyadi");
		
		yolculuk_katilim = i.getStringExtra("yolculukkatilim");
		Log.i("@Log_Yolculuk_katilim", yolculuk_katilim);
		
		yolculuk_katilim_mesaj = i.getStringExtra("yolculukkatilimmesaj");
		Log.i("@Log_Yolculuk_katilim_mesaj", yolculuk_katilim_mesaj);
		
		cikis_latitude = i.getStringExtra("cikislat");
		Log.i("@Log_Cikis_latitude", cikis_latitude);
		
		
		cikis_longitude = i.getStringExtra("cikislong");
		Log.i("@Log_Cikis_longitude", cikis_longitude);
		
		varis_latitude = i.getStringExtra("varislat");
		Log.i("@Log_Varis_latitude", varis_latitude);
		
		varis_longitude = i.getStringExtra("varislong");
		Log.i("@Log_Varis_longitude", varis_longitude);
		
		yolculukid = i.getStringExtra("yolculukid");
		Log.i("@Log_Yolculuk_ID_In_SingleView_Arama", yolculukid);
		
		prefs = getSharedPreferences("ex1",MODE_PRIVATE); 
		
		//get_Uye_Id fom SharedPreferences
		get_uye_id = prefs.getString("get_UyeID","UyeID alınamadı");
        Log.i("@Log_GetUyeid_InYolcuArama_Single", get_uye_id);
		
		cikis_latitude_doble = Double.parseDouble(cikis_latitude);
		Log.i("@Log_Cikis_latitude_double", ""+cikis_latitude_doble);
		
		cikis_longitude_double = Double.parseDouble(cikis_longitude);
		Log.i("@Log_Cikis_latitude_double", ""+cikis_longitude_double);
		
		varis_latitude_double = Double.parseDouble(varis_latitude);
		Log.i("@Log_Varis_latitude_double", ""+varis_latitude_double);
		
		varis_longitude_double = Double.parseDouble(varis_longitude);
		Log.i("@Log_Varis_longitude_double", ""+varis_longitude_double);
		


		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.getUiSettings().setMyLocationButtonEnabled(true);

		markers = new ArrayList<Marker>();
		polylines = new ArrayList<Polyline>();
		places = new ArrayList<Place>();

	}
	
	private void addDummyLocations() {
		

		
		//
		places.add(new Place(rank,cikis_latitude_doble, cikis_longitude_double));
		places.add(new Place(country, varis_latitude_double, varis_longitude_double));
//		places.add(new Place("Amritsar", 31.631442, 74.869766));
	}
	
	private void drawMarkers(ArrayList<Place> places) {

		// Remove previous markers if any
		for (int i = 0; i < markers.size(); i++) {
			markers.get(i).remove();
		}
		// clear the markers list
		markers.clear();

		Marker marker;
		Place place;
		// draw markers
		for (int i = 0; i < places.size(); i++) {
			place = places.get(i);
			marker = map.addMarker(new MarkerOptions()
					.position(
							new LatLng(place.getLatitude(), place
									.getLongitude()))
					.title(place.getName())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.flagmark)));
			markers.add(marker);
		}

		// move center to second place
		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(places
				.get(1).getLatitude(), places.get(1).getLongitude()));
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(6);

		map.moveCamera(center);
		map.animateCamera(zoom);

	}
	
	private void drawPolylines(ArrayList<Place> places) {
		Place from, to;
		Polyline polyline;

		// Remove all previous polylines if any
		for (int i = 0; i < polylines.size(); i++) {
			polylines.get(i).remove();
		}

		// Clear polyline list
		polylines.clear();

		for (int i = 0; i < (places.size() - 1); i++) {
			from = places.get(i);
			to = places.get(i + 1);
			polyline = map.addPolyline(new PolylineOptions()
					.add(new LatLng(from.getLatitude(), from.getLongitude()),
							new LatLng(to.getLatitude(), to.getLongitude()))
					.width(8).color(Color.RED));
			polylines.add(polyline);
		}

	}
	
	public class CircleTransform implements Transformation {
	    @Override
	    public Bitmap transform(Bitmap source) {
	        int size = Math.min(source.getWidth(), source.getHeight());

	        int x = (source.getWidth() - size) / 2;
	        int y = (source.getHeight() - size) / 2;

	        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
	        if (squaredBitmap != source) {
	            source.recycle();
	        }

	        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

	        Canvas canvas = new Canvas(bitmap);
	        Paint paint = new Paint();
	        BitmapShader shader = new BitmapShader(squaredBitmap,
	                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
	        paint.setShader(shader);
	        paint.setAntiAlias(true);

	        float r = size / 2f;
	        canvas.drawCircle(r, r, r, paint);

	        squaredBitmap.recycle();
	        return bitmap;
	    }

	    @Override
	    public String key() {
	        return "circle";
	    }
	}
}