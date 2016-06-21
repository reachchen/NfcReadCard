package com.andychen.nfcreadcard.fragment;

import com.andychen.nfcread.db.CardMsgInfo;
import com.andychen.nfcreadcard.R;
import com.andychen.nfcreadcard.listener.onFragmentChangeListener;
import com.jc.xnfc.card.CardManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NFCInputFragment extends Fragment {
	private View inflate;
	private TextView tv_nfc_readcardnumber;
	private onFragmentChangeListener mOnFragmentChangeListener;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mOnFragmentChangeListener = (onFragmentChangeListener)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		inflate = inflater.inflate(R.layout.fragment_nfc_input_card, null);
		tv_nfc_readcardnumber = (TextView) inflate.findViewById(R.id.tv_nfc_readcardnumber);
		String cardNumber = CardMsgInfo.getCardMsgInfo().getCardNumber();
		tv_nfc_readcardnumber.setText(cardNumber);
		if(cardNumber.length()>0){
			Message message = new Message();
			message.what = 1;
			mOnFragmentChangeListener.onChange(message);
		}
		return inflate;
	}

}
