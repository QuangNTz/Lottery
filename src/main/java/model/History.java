package model;

import java.util.Date;

public class History {
	private int history_id;
	private Date history_date;
	private String user_mail;
	private int lottery_id;
	private String lottery_com;
	private Date lottery_date;
	private String history_number;
	private String history_result;
	
	public History(int history_id, Date history_date, String user_mail, int lottery_id, String lottery_com,
			Date lottery_date, String history_number, String history_result) {
		this.history_id = history_id;
		this.history_date = history_date;
		this.user_mail = user_mail;
		this.lottery_id = lottery_id;
		this.lottery_com = lottery_com;
		this.lottery_date = lottery_date;
		this.history_number = history_number;
		this.history_result = history_result;
	}

	public int getHistory_id() {
		return history_id;
	}

	public void setHistory_id(int history_id) {
		this.history_id = history_id;
	}

	public Date getHistory_date() {
		return history_date;
	}

	public void setHistory_date(Date history_date) {
		this.history_date = history_date;
	}

	public String getUser_mail() {
		return user_mail;
	}

	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}

	public int getLottery_id() {
		return lottery_id;
	}

	public void setLottery_id(int lottery_id) {
		this.lottery_id = lottery_id;
	}

	public String getLottery_com() {
		return lottery_com;
	}

	public void setLottery_com(String lottery_com) {
		this.lottery_com = lottery_com;
	}

	public Date getLottery_date() {
		return lottery_date;
	}

	public void setLottery_date(Date lottery_date) {
		this.lottery_date = lottery_date;
	}

	public String getHistory_number() {
		return history_number;
	}

	public void setHistory_number(String history_number) {
		this.history_number = history_number;
	}

	public String getHistory_result() {
		return history_result;
	}

	public void setHistory_result(String history_result) {
		this.history_result = history_result;
	}	
}
