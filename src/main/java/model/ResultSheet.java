package model;

import java.util.Date;

public class ResultSheet {
	private int lottery_id;
	private String lottery_com;
	private Date lottery_date;
	private String giai8, giai7, giai6, giai5, giai4, giai3, giai2, giai1, gdb;

	public ResultSheet() {

	}

	public ResultSheet(int lottery_id, String lottery_com, Date lottery_date, String giai8, String giai7, String giai6,
			String giai5, String giai4, String giai3, String giai2, String giai1, String gdb) {
		this.lottery_id = lottery_id;
		this.lottery_com = lottery_com;
		this.lottery_date = lottery_date;
		this.giai8 = giai8;
		this.giai7 = giai7;
		this.giai6 = giai6;
		this.giai5 = giai5;
		this.giai4 = giai4;
		this.giai3 = giai3;
		this.giai2 = giai2;
		this.giai1 = giai1;
		this.gdb = gdb;
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

	public String getGiai8() {
		return giai8;
	}

	public void setGiai8(String giai8) {
		this.giai8 = giai8;
	}

	public String getGiai7() {
		return giai7;
	}

	public void setGiai7(String giai7) {
		this.giai7 = giai7;
	}

	public String getGiai6() {
		return giai6;
	}

	public void setGiai6(String giai6) {
		this.giai6 = giai6;
	}

	public String getGiai5() {
		return giai5;
	}

	public void setGiai5(String giai5) {
		this.giai5 = giai5;
	}

	public String getGiai4() {
		return giai4;
	}

	public void setGiai4(String giai4) {
		this.giai4 = giai4;
	}

	public String getGiai3() {
		return giai3;
	}

	public void setGiai3(String giai3) {
		this.giai3 = giai3;
	}

	public String getGiai2() {
		return giai2;
	}

	public void setGiai2(String giai2) {
		this.giai2 = giai2;
	}

	public String getGiai1() {
		return giai1;
	}

	public void setGiai1(String giai1) {
		this.giai1 = giai1;
	}

	public String getGdb() {
		return gdb;
	}

	public void setGdb(String gdb) {
		this.gdb = gdb;
	}
}
