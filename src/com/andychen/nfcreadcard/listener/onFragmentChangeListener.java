package com.andychen.nfcreadcard.listener;

import android.os.Message;

public interface onFragmentChangeListener {
	/**
	 * 收集来自fragment的信息
	 * @param message
	 */
	public void onChange(Message message);

}
