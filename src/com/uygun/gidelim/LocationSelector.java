package com.uygun.gidelim;

/*import com.google.android.gms.common.ConnectionResult;
 import com.google.android.gms.common.GooglePlayServicesUtil;
 import com.google.android.gms.common.api.GoogleApiClient;
 import com.google.android.gms.location.places.Places;

 import android.app.Activity;
 import android.os.Bundle;
 import android.util.Log;
 import android.widget.AutoCompleteTextView;

 public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
 GoogleApiClient.OnConnectionFailedListener{

 protected static final String TAG = "MainActivity";
 private GoogleApiClient mGoogleApiClient;

 AutoCompleteTextView autoView;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);

 autoView = (AutoCompleteTextView)findViewById(R.id.auot_text);

 mGoogleApiClient = new GoogleApiClient
 .Builder(this)
 .addApi(Places.GEO_DATA_API)
 .addApi(Places.PLACE_DETECTION_API)
 .addConnectionCallbacks(this)
 .addOnConnectionFailedListener(this)
 .build();
 Log.d("MainActivity", "..........onCreate");
 }
 @Override
 protected void onStart() {
 super.onStart();
 mGoogleApiClient.connect();
 }

 @Override
 protected void onStop() {
 mGoogleApiClient.disconnect();
 super.onStop();
 }

 @Override
 public void onConnectionFailed(ConnectionResult result) {
 // TODO Auto-generated method stub
 Log.d("MainActivity", "..........connection failed");
 System.out.println("..........connection failed ");
 if (!result.hasResolution()) {
 // show the localized error dialog.
 GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
 return;
 }
 // The failure has a resolution. Resolve it.
 // Called typically when the app is not yet authorized, and an
 // authorization
 // dialog is displayed to the user.
 }

 @Override
 public void onConnected(Bundle connectionHint) {
 // TODO Auto-generated method stub
 Log.d("MainActivity", "..........connection succesfull");
 //		PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
 //			    .getCurrentPlace(mGoogleApiClient, null);
 //			result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
 //			  @Override
 //			  public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
 //				  for (int i = 0; i < 10; i++) {
 //					Log.i(TAG, ".............................");
 //				}
 //				for (PlaceLikelihood placeLikelihood : likelyPlaces) {
 //			      Log.i(TAG, String.format("Place '%s' has likelihood: %g",
 //			          placeLikelihood.getPlace().getName(),
 //			          placeLikelihood.getLikelihood()));
 //			    }
 //			    likelyPlaces.release();
 //			  }
 //			});

 }

 @Override
 public void onConnectionSuspended(int cause) {
 // TODO Auto-generated method stub
 Log.d("MainActivity", "..........connection suspended");
 System.out.println("..........connection suspended");
 }
 }
 */

import java.util.Calendar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.loginpage.R;
import com.yolcuarama.jsonparser.Yolcuarama_MainActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LocationSelector extends Activity

implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,OnClickListener {

	/**
	 * 
	 * GoogleApiClient wraps our service connection to Google Play Services and
	 * 
	 * provides access to the user's sign in state as well as the Google's APIs.
	 */

	public static final String TAG = "SampleActivityBase";

	protected GoogleApiClient mGoogleApiClient;

	private PlaceAutocompleteAdapter mAdapter;

	private AutoCompleteTextView mAutocompleteViewFrom;

	private AutoCompleteTextView mAutocompleteViewTo;

	private FromTo[] fromTo = new FromTo[2];// holds from to points data

	private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
			new LatLng(36, 28), new LatLng(41, 43));

	EditText get_tarih_gun, get_tarih_ay, get_tarih_yil;
	Button btn_aramayap;

	String get_trh_gun, get_trh_ay, get_trh_yil;
	
	private ImageButton ib;
	private Calendar cal;
	private int day;
	private int month;
	private int year;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Construct a GoogleApiClient

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addApi(Places.GEO_DATA_API)

				.addApi(Places.PLACE_DETECTION_API)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)

				.build();

		Log.d("MainActivity", "..........onCreate");

		setContentView(R.layout.locationselector_main);

		get_tarih_gun = (EditText) findViewById(R.id.edt_tarih_gun);
		get_tarih_ay = (EditText) findViewById(R.id.edt_tarih_ay);
		get_tarih_yil = (EditText) findViewById(R.id.edt_tarih_yil);

		btn_aramayap = (Button) findViewById(R.id.btn_search);
		
		ib = (ImageButton) findViewById(R.id.image_date_selector);
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		
		ib.setOnClickListener(this);

		btn_aramayap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {

					get_trh_gun = get_tarih_gun.getText().toString();
					Log.i("@Log_tarih_gün", get_trh_gun);
					get_trh_ay = get_tarih_ay.getText().toString();
					Log.i("@Log_tarih_ay", get_trh_ay);
					get_trh_yil = get_tarih_yil.getText().toString();
					Log.i("@Log_tarih_yil", get_trh_yil);

					if (get_trh_gun.equals("") || get_trh_ay.equals("")
							|| get_trh_yil.equals("")) {

						Toast.makeText(getApplicationContext(),
								"Lütfen Tarih Bilgilerini Tam Giriniz",
								Toast.LENGTH_SHORT).show();
					}

					else {

						Log.i("@Log_fromto[0]_lat", "" + fromTo[0].latitude);
						Log.i("@Log_fromto[0]_lat", "" + fromTo[0].longitude);

						Log.i("@Log_fromto[1]_lat", "" + fromTo[1].latitude);
						Log.i("@Log_fromto[1]_lat", "" + fromTo[1].latitude);

						Intent go_yolcuarama_parser = new Intent(
								getApplicationContext(),
								Yolcuarama_MainActivity.class);

						go_yolcuarama_parser.putExtra("latX",
								fromTo[0].latitude);
						go_yolcuarama_parser.putExtra("longX",
								fromTo[0].longitude);

						go_yolcuarama_parser.putExtra("latY",
								fromTo[1].latitude);
						go_yolcuarama_parser.putExtra("longY",
								fromTo[1].longitude);

						go_yolcuarama_parser.putExtra("trh_gun", get_trh_gun);
						go_yolcuarama_parser.putExtra("trh_ay", get_trh_ay);
						go_yolcuarama_parser.putExtra("trh_yil", get_trh_yil);

						startActivity(go_yolcuarama_parser);
					}
				} catch (Exception e) {

					Toast.makeText(getApplicationContext(),
							"Lütfen Eksik Bigileri Tamamlayınız",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		// Retrieve the AutoCompleteTextView that will display Place

		// suggestions.

		mAutocompleteViewFrom = (AutoCompleteTextView) findViewById(R.id.from);

		mAutocompleteViewTo = (AutoCompleteTextView) findViewById(R.id.to);

		// Register a listener that receives callbacks when a suggestion has

		// been selected

		mAutocompleteViewFrom
				.setOnItemClickListener(mAutocompleteClickListenerFrom);

		mAutocompleteViewTo
				.setOnItemClickListener(mAutocompleteClickListenerTo);

	}

	@Override
	protected void onStart() {

		super.onStart();

		mGoogleApiClient.connect();

	}

	@Override
	protected void onStop() {

		mGoogleApiClient.disconnect();

		super.onStop();

	}

	/**
	 * 
	 * Listener that handles selections from suggestions from the
	 * 
	 * AutoCompleteTextView that displays Place suggestions. Gets the place id
	 * 
	 * of the selected item and issues a request to the Places Geo Data API to
	 * 
	 * retrieve more details about the place.
	 * 
	 *
	 * 
	 * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
	 *      String...)
	 */

	private AdapterView.OnItemClickListener mAutocompleteClickListenerFrom = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			/*
			 * 
			 * Retrieve the place ID of the selected item from the Adapter. The
			 * 
			 * adapter stores each Place suggestion in a PlaceAutocomplete
			 * 
			 * object from which we read the place ID.
			 */

			final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter
					.getItem(position);

			final String placeId = String.valueOf(item.placeId);

			Log.i(TAG, "Autocomplete item selected: " + item.description);

			/*
			 * 
			 * Issue a request to the Places Geo Data API to retrieve a Place
			 * 
			 * object with additional details about the place.
			 */

			PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
					.getPlaceById(mGoogleApiClient, placeId);

			placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {

				@Override
				public void onResult(PlaceBuffer places) {

					if (!places.getStatus().isSuccess()) {

						// Request did not complete successfully

						Log.e(TAG, "Place query did not complete. Error: "
								+ places.getStatus().toString());

						places.release();

						return;

					}

					// Get the Place object from the buffer.

					final Place place = places.get(0);

					fromTo[0] = new FromTo(place.getLatLng().latitude, place
							.getLatLng().longitude, place.getName());

					Log.i(TAG, "Place details name: " + fromTo[0].name);

					Log.i(TAG, "Place details lat: " + fromTo[0].latitude);

					Log.i(TAG, "Place details longt: " + fromTo[0].longitude);

					places.release();

				}

			});

			// Toast.makeText(getApplicationContext(), "Clicked: " +
			// item.description, Toast.LENGTH_SHORT).show();

			Log.i(TAG, "Called getPlaceById to get Place details for "
					+ item.placeId);

		}

	};

	private AdapterView.OnItemClickListener mAutocompleteClickListenerTo = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			/*
			 * 
			 * Retrieve the place ID of the selected item from the Adapter. The
			 * 
			 * adapter stores each Place suggestion in a PlaceAutocomplete
			 * 
			 * object from which we read the place ID.
			 */

			final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter
					.getItem(position);

			final String placeId = String.valueOf(item.placeId);

			Log.i(TAG, "Autocomplete item selected: " + item.description);

			/*
			 * 
			 * Issue a request to the Places Geo Data API to retrieve a Place
			 * 
			 * object with additional details about the place.
			 */

			PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
					.getPlaceById(mGoogleApiClient, placeId);

			placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {

				@Override
				public void onResult(PlaceBuffer places) {

					if (!places.getStatus().isSuccess()) {

						// Request did not complete successfully

						Log.e(TAG, "Place query did not complete. Error: "
								+ places.getStatus().toString());

						places.release();

						return;

					}

					// Get the Place object from the buffer.

					final Place place = places.get(0);

					fromTo[1] = new FromTo(place.getLatLng().latitude, place
							.getLatLng().longitude, place.getName());

					Log.i(TAG, "Place details name: " + fromTo[1].name);

					Log.i(TAG, "Place details lat: " + fromTo[1].latitude);

					Log.i(TAG, "Place details longt: " + fromTo[1].longitude);

					places.release();

				}

			});

			// Toast.makeText(getApplicationContext(), "Clicked: " +
			// item.description, Toast.LENGTH_SHORT).show();

			Log.i(TAG, "Called getPlaceById to get Place details for "
					+ item.placeId);

		}

	};

	/**
	 * 
	 * Callback for results from a Places Geo Data API query that shows the
	 * 
	 * first place result in the details view on screen.
	 */

	@Override
	public void onConnectionFailed(ConnectionResult result) {

		Log.d("MainActivity", "..........connection failed");

		if (!result.hasResolution()) {

			// show the localized error dialog.

			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();

			return;

		}

	}

	@Override
	public void onConnected(Bundle connectionHint) {

		// TODO Auto-generated method stub

		Log.d("MainActivity", "..........connection succesfull");

		// Set up the adapter that will retrieve suggestions from the Places Geo

		// Data API that cover

		// the entire world.

		mAdapter = new PlaceAutocompleteAdapter(this,
				android.R.layout.simple_list_item_1, mGoogleApiClient,

				BOUNDS_GREATER_SYDNEY, null);

		mAutocompleteViewFrom.setAdapter(mAdapter);

		mAutocompleteViewTo.setAdapter(mAdapter);

	}

	@Override
	public void onConnectionSuspended(int cause) {

		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * Hold start and end point coordinates.
	 */

	class FromTo {

		double latitude;

		double longitude;

		CharSequence name;

		public FromTo(double latitude, double longitude, CharSequence name) {

			this.latitude = latitude;

			this.longitude = longitude;

			this.name = name;

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		showDialog(0);
	}
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			get_tarih_gun.setText(""+selectedDay);
			get_tarih_ay.setText((""+(selectedMonth + 1)));
			get_tarih_yil.setText(""+selectedYear);
		}
	};

}