package com.yolcuarama.jsonparser;

import java.util.ArrayList;
import java.util.HashMap;

import com.loginpage.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yolcuarama.jsonparser.Yolcuarama_SingleItemView.CircleTransform;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Yolcuarama_ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	Yolcuarama_ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
	

	public Yolcuarama_ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new Yolcuarama_ImageLoader(context);
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
		TextView tip;
		TextView durum;
		TextView rank;
		TextView country;
		TextView population;
		TextView tarih;
		ImageView flag;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.yolcu_arama_listview_item, parent, false);
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
		rank.setText(resultp.get(Yolcuarama_MainActivity.RANK));
		country.setText(resultp.get(Yolcuarama_MainActivity.COUNTRY));
		population.setText(resultp.get(Yolcuarama_MainActivity.POPULATION));
		tarih.setText(resultp.get(Yolcuarama_MainActivity.TARIH));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
//		imageLoader.DisplayImage(resultp.get(Yolcuarama_MainActivity.FLAG), flag);
		
		try {
			
			Picasso.with(context.getApplicationContext()).load(resultp.get(Yolcuarama_MainActivity.FLAG)).transform(new CircleTransform()).into(flag);
			
//	        Picasso.with(getApplicationContext())
//            .load(flag)
//            .into(imageView);
			
//			imageLoader.DisplayImage(flag, imageView1);
		}
		catch (Exception e) {
		    Log.e("MyTag", "Failure to get drawable id.", e);
		}
		
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
//				Toast.makeText(context, "Lütfen Bekleyiniz", Toast.LENGTH_SHORT).show();
				
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, Yolcuarama_SingleItemView.class);
				// Pass all data rank
				intent.putExtra("rank", resultp.get(Yolcuarama_MainActivity.RANK));
				// Pass all data country
				intent.putExtra("country", resultp.get(Yolcuarama_MainActivity.COUNTRY));
				// Pass all data population
				intent.putExtra("population",resultp.get(Yolcuarama_MainActivity.POPULATION));
				
				intent.putExtra("tarih",resultp.get(Yolcuarama_MainActivity.TARIH));
				// Pass all data flag
				intent.putExtra("tip",resultp.get(Yolcuarama_MainActivity.TIP));
				// Pass all data flag
				intent.putExtra("durum",resultp.get(Yolcuarama_MainActivity.DURUM));
				// Pass all data flag
				intent.putExtra("flag", resultp.get(Yolcuarama_MainActivity.FLAG));
				// Start SingleItemView Class
				intent.putExtra("mesafe", resultp.get(Yolcuarama_MainActivity.Mesafe));
				// Start SingleItemView Class
				intent.putExtra("boskoltuk", resultp.get(Yolcuarama_MainActivity.BosKoltuk));
				// Start SingleItemView Class
				intent.putExtra("yolcuadi", resultp.get(Yolcuarama_MainActivity.YolcuAdi));
				// Start SingleItemView Class
				intent.putExtra("yolcusoyadi", resultp.get(Yolcuarama_MainActivity.YolcuSoyadi));
				// Start SingleItemView Class
				intent.putExtra("yolculukkatilim", resultp.get(Yolcuarama_MainActivity.YolculugaKatilim));
				// Start SingleItemView Class
				intent.putExtra("yolculukkatilimmesaj", resultp.get(Yolcuarama_MainActivity.YolculugaKatilimMesaj));
				// Start SingleItemView Class
				intent.putExtra("cikislat", resultp.get(Yolcuarama_MainActivity.CikisLat));
				// Start SingleItemView Class
				intent.putExtra("cikislong", resultp.get(Yolcuarama_MainActivity.CikisLong));
				// Start SingleItemView Class
				intent.putExtra("varislat", resultp.get(Yolcuarama_MainActivity.VarisLat));
				// Start SingleItemView Class
				intent.putExtra("varislong", resultp.get(Yolcuarama_MainActivity.VarisLong));
				// Start SingleItemView Class
				intent.putExtra("yolculukid", resultp.get(Yolcuarama_MainActivity.YolculukID));
				// Start SingleItemView Class
				context.startActivity(intent);

			}
		});
		return itemView;
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
