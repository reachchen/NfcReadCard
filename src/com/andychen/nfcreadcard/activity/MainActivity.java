package com.andychen.nfcreadcard.activity;

import java.util.HashMap;
import java.util.Map;

import com.andychen.nfcread.db.CardMsgInfo;
import com.andychen.nfcreadcard.R;
import com.andychen.nfcreadcard.fragment.NFCCardFragment;
import com.andychen.nfcreadcard.fragment.NFCInputFragment;
import com.andychen.nfcreadcard.listener.onFragmentChangeListener;
import com.andychen.nfcreadcard.view.ShowToast;
import com.jc.xnfc.card.CardManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
/**
 * 
 * @author  chenrui
 *
 * 2016年6月20日
 */
public class MainActivity extends FragmentActivity implements onFragmentChangeListener{

	private FrameLayout layout_reg_contain;
	private FrameLayout layout_parent_fragmenthost;
	private NFCCardFragment mNfcCardFragment;
	private NfcAdapter nfcAdapter;
	private PendingIntent pendingIntent;
	private boolean flagRead;
	private NFCInputFragment mInputFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout_reg_contain = (FrameLayout) findViewById(R.id.layout_reg_contain);
		layout_parent_fragmenthost = (FrameLayout) findViewById(R.id.layout_parent_fragmenthost);
		mNfcCardFragment = new NFCCardFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.layout_parent_fragmenthost, mNfcCardFragment).commitAllowingStateLoss();
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
		NfcAdapter adapter = manager.getDefaultAdapter();
		if (adapter != null && adapter.isEnabled()) {
			
		}else if (adapter == null) {
			ShowToast.showToast(this, "您的设备不支持NFC功能");
		} else if (adapter != null && !adapter.isEnabled()) {
			ShowToast.showToast(this, "请打开NFC功能");
		}
		Log.e("TAG--->", "onCreate");
	}
	@Override
	protected void onResume() {
		if (nfcAdapter != null)
			nfcAdapter.enableForegroundDispatch(this, pendingIntent,
					CardManager.FILTERS, CardManager.TECHLISTS);
		Log.e("TAG--->", "onResume");
		super.onResume();
	}
	@Override
	protected void onPause() {
		if (nfcAdapter != null)
			nfcAdapter.disableForegroundDispatch(this);
		super.onPause();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		Log.e("TAG--->", "onNewIntent");
		Parcelable p = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		Map<String, String> mMap = new HashMap<String, String>();
		mMap.put("transAmount", "0.00");
		mMap.put("transType", "00");
		Map<String,String> saleResult = CardManager.qPbocOnline(p, getResources(), mMap);
		if(saleResult !=null){
			if (saleResult.get("iccTrack") != null) {
				flagRead = true;
				String iccTrack = saleResult.get("iccTrack").replace("F", "");
				String iccExpDate = saleResult.get("iccExpDate"); // TemEmvMessageDealer.aflCardData.get("5F24");
				String iccAsnSn = saleResult.get("iccAsnSn"); // TemEmvMessageDealer.aflCardData.get("5F34");
				String icc55Field = saleResult.get("icc55Field");
				String cardnumber = saleResult.get("iccTrack").split("D")[0];
				CardMsgInfo.getCardMsgInfo().setCardNumber(cardnumber);
				if (iccExpDate == null)
					iccExpDate = "";
				if (iccAsnSn == null)
					iccAsnSn = "";
				OnStep_2_replace();
			}else{
				ShowToast.showToast(
						this,
						(saleResult.get("errorCode") == null || saleResult.get(
								"errorCode").equals("")) ? "读卡错误，请重新读卡"
								: saleResult.get("errorCode"));
			}
		}else {
			ShowToast.showToast(this, "读卡错误，请重新读卡");
			this.finish();
		}
		
		
		super.onNewIntent(intent);
	}

	private void OnStep_2_replace() {
		// TODO Auto-generated method stub
		mInputFragment = new NFCInputFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.layout_parent_fragmenthost, mInputFragment);
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void onChange(Message message) {
		// TODO Auto-generated method stub
		switch (message.what) {
		case 1:
			ShowToast.showSuccessToast(this, "读取卡片信息成功");
			break;

		default:
			break;
		}
	}

	
}
