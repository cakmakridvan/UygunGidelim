package com.androidbegin.jsonparsetutorial;


import java.util.ArrayList;

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
import com.androidbegin.jsonparsetutorial.Place;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleItemView extends Activity {
	// Declare Variables
	
	private static final int ACTION_GOOGLE_PLAY_SERVICES = 100;
	
	String mesafe;
	String boskoltuksayisi;
	String CikisLat;
	String CikisLong;
	String VarisLat;
	String VarisLong;
	String yolcu_Adi;
	String yolcu_Soyadi;
	String rank;
	String country;
	String population;
	String flag;
	String position;
	ImageLoader imageLoader = new ImageLoader(this);
	ImageView image_profil;
	
	double cikis_latitude_doble;
	double cikis_longitude_double;
	double varis_latitude_double;
	double varis_longitude_double;
	
	final Context context = this;
	
	private GoogleMap map;
	private ArrayList<Marker> markers;
	private ArrayList<Polyline> polylines;
	private ArrayList<Place> places;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);
		
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



		// Locate the TextViews in singleitemview.xml
		TextView txtrank = (TextView) findViewById(R.id.txt_nereden);
		TextView txtcountry = (TextView) findViewById(R.id.txt_nereye);
		TextView txtpopulation = (TextView) findViewById(R.id.txt_masraf);
		TextView txtyolcuadi = (TextView) findViewById(R.id.txt_name);
		TextView txtyolcusoyadi = (TextView) findViewById(R.id.txt_soyadi);
		TextView txtmesafe = (TextView) findViewById(R.id.txt_mesafe);
		TextView txtboskoltuksayisi = (TextView) findViewById(R.id.txt_boskoltuk);

		// Locate the ImageView in singleitemview.xml
		ImageView imgflag = (ImageView) findViewById(R.id.flag);

		// Set results to the TextViews
		txtrank.setText(rank);
		txtcountry.setText(country);
		txtpopulation.setText(population);
		txtyolcuadi.setText(yolcu_Adi);
		txtyolcusoyadi.setText(yolcu_Soyadi);
		txtmesafe.setText(mesafe);
		txtboskoltuksayisi.setText(boskoltuksayisi);
		
		image_profil = (ImageView) findViewById(R.id.image_profil);
		
		image_profil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// custom dialog
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.custom_photo);
				dialog.setTitle(yolcu_Adi +" "+ yolcu_Soyadi);
				

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
			
			Picasso.with(getApplicationContext()).load(flag).transform(new CircleTransform()).into(image_profil);
			
//	        Picasso.with(getApplicationContext())
//            .load(flag)
//            .into(imageView);
			
//			imageLoader.DisplayImage(flag, imageView1);
		}
		catch (Exception e) {
		    Log.e("MyTag", "Failure to get drawable id.", e);
		}

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
//		imageLoader.DisplayImage(flag, imgflag);
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
		yolcu_Adi = i.getStringExtra("yolcuadi");
		// Get the result of flag
		yolcu_Soyadi = i.getStringExtra("yolcusoyadi");
		// Get the result of flag
		mesafe = i.getStringExtra("mesafe");
		// Get the result of flag
		boskoltuksayisi = i.getStringExtra("boskoltuksayisi");
		// Get the result of flag
		CikisLat = i.getStringExtra("cikislat");
		// Get the result of flag
		CikisLong = i.getStringExtra("cikislong");
		// Get the result of flag
		VarisLat = i.getStringExtra("varislat");
		// Get the result of flag
		VarisLong = i.getStringExtra("varislong");
		// Get the result of flag
		flag = i.getStringExtra("flag");
		
		CikisLat= i.getStringExtra("cikislat");
		Log.i("@Log_Cikis_latitude", CikisLat);
		
		
		CikisLong = i.getStringExtra("cikislong");
		Log.i("@Log_Cikis_longitude", CikisLong);
		
		VarisLat = i.getStringExtra("varislat");
		Log.i("@Log_Varis_latitude", VarisLat);
		
		VarisLong = i.getStringExtra("varislong");
		Log.i("@Log_Varis_longitude", VarisLong);
		
		
		cikis_latitude_doble = Double.parseDouble(CikisLat);
		Log.i("@Log_Cikis_latitude_double", ""+cikis_latitude_doble);
		
		cikis_longitude_double = Double.parseDouble(CikisLong);
		Log.i("@Log_Cikis_latitude_double", ""+cikis_longitude_double);
		
		varis_latitude_double = Double.parseDouble(VarisLat);
		Log.i("@Log_Varis_latitude_double", ""+varis_latitude_double);
		
		varis_longitude_double = Double.parseDouble(VarisLong);
		Log.i("@Log_Varis_longitude_double", ""+varis_longitude_double);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_yolcululukarim))
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