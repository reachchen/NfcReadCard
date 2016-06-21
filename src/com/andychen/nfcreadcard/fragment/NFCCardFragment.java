package com.andychen.nfcreadcard.fragment;

import com.andychen.nfcreadcard.R;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class NFCCardFragment extends Fragment {
	
	private ImageView img_nfc_card;
	private AnimationDrawable animationDrawable;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View inflate = inflater.inflate(R.layout.fragment_nfc_card,null);
		img_nfc_card = (ImageView) inflate.findViewById(R.id.img_nfc_card);
		img_nfc_card.setBackgroundResource(R.drawable.img_nfc_card);
		animationDrawable = (AnimationDrawable) img_nfc_card.getBackground();
		animationDrawable.start();
		return inflate;
	}

}
