package com.usermenu;


import com.loginpage.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class UserInformation extends Activity {
	
	SharedPreferences prefs;
	
	TextView info_adi,info_soyadi,info_eposta,info_cep;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		
		setContentView(R.layout.user_information);
		
		info_adi = (TextView) findViewById(R.id.txt_uye_adi);
		info_soyadi = (TextView) findViewById(R.id.txt_uye_soyadi);
//		info_id = (TextView) findViewById(R.id.txt_uye_id);
		info_eposta = (TextView) findViewById(R.id.txt_uye_eposta);
		info_cep = (TextView) findViewById(R.id.txt_uye_cep);
		
		prefs = getSharedPreferences("ex1",MODE_PRIVATE); 
		
		String get_uye_adi = prefs.getString("get_UyeAdi","İsim alınamadı");
		String get_uye_soyadi = prefs.getString("get_UyeSoyadi","Soyad alınamadı");
		String get_uye_id = prefs.getString("get_UyeID","UyeID alınamadı");
		String get_uye_eposta = prefs.getString("get_UyeEposta","UyeEposta alınamadı");
		String get_uye_cep = prefs.getString("get_UyeCep","UyeCep alınamadı");
		
		
		info_adi.setText(get_uye_adi);
		info_soyadi.setText(get_uye_soyadi);
//		info_id.setText(get_uye_id);
		info_eposta.setText(get_uye_eposta);
		info_cep.setText(get_uye_cep);
		
		
		
		
	}

	 


}
