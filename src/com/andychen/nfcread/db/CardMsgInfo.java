package com.andychen.nfcread.db;

public class CardMsgInfo {
	
	public String CardNumber;
	
	private static CardMsgInfo cardmsginfo;
	
	
	public static CardMsgInfo getCardMsgInfo(){
		if(cardmsginfo==null){
			cardmsginfo = new CardMsgInfo();
		}
		return cardmsginfo;
		
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}
	

}
