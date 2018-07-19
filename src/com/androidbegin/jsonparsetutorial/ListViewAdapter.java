package com.androidbegin.jsonparsetutorial;

import java.util.ArrayList;
import java.util.HashMap;

import com.loginpage.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView rank;
		TextView country;
		TextView population;
		TextView tarih;
		ImageView flag;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		rank = (TextView) itemView.findViewById(R.id.txt_nereden);
		country = (TextView) itemView.findViewById(R.id.txt_nereye);
		population = (TextView) itemView.findViewById(R.id.txt_masraf);
		tarih = (TextView) itemView.findViewById(R.id.txt_tarih);

		// Locate the ImageView in listview_item.xml
		flag = (ImageView) itemView.findViewById(R.id.flag);

		// Capture position and set results to the TextViews
		rank.setText(resultp.get(MainActivity.RANK));
		country.setText(resultp.get(MainActivity.COUNTRY));
		population.setText(resultp.get(MainActivity.POPULATION));
		tarih.setText(resultp.get(MainActivity.TARIH));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(MainActivity.FLAG), flag);
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				
				Toast.makeText(context, "Lütfen Bekleyiniz", Toast.LENGTH_SHORT).show();
				
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data rank
				intent.putExtra("rank", resultp.get(MainActivity.RANK));
				// Pass all data country
				intent.putExtra("country", resultp.get(MainActivity.COUNTRY));
				// Pass all data population
				intent.putExtra("population",resultp.get(MainActivity.POPULATION));
				
				intent.putExtra("tarih",resultp.get(MainActivity.TARIH));
				// Pass all data flag
				intent.putExtra("flag", resultp.get(MainActivity.FLAG));
				// Start SingleItemView Class
				intent.putExtra("yolcuadi", resultp.get(MainActivity.Adi));
				// Start SingleItemView Class
				intent.putExtra("yolcusoyadi", resultp.get(MainActivity.Soyadi));
				// Start SingleItemView Class
				intent.putExtra("cikislat", resultp.get(MainActivity.CikisLat));
				// Start SingleItemView Class
				intent.putExtra("cikislong", resultp.get(MainActivity.CikisLong));
				// Start SingleItemView Class
				intent.putExtra("varislat", resultp.get(MainActivity.VarisLat));
				// Start SingleItemView Class
				intent.putExtra("varislong", resultp.get(MainActivity.VarisLong));
				// Start SingleItemView Class
				intent.putExtra("mesafe", resultp.get(MainActivity.Mesafe));
				// Start SingleItemView Class
				intent.putExtra("boskoltuksayisi", resultp.get(MainActivity.BosKoltuk));
				// Start SingleItemView Class
				context.startActivity(intent);

			}
		});
		return itemView;
	}
}
