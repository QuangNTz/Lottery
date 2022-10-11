package controller;

public class CheckInputResultSheet {	
	
	public boolean checkInput(String g8, String g7, String g6_1, String g6_2, String g6_3, 
								String g5, String g4_1, String g4_2, String g4_3, String g4_4, String g4_5, String g4_6, String g4_7, String g3_1, String g3_2, 
								String g2, String g1, String gdb) {
		
		return (checkGiai_8Input(g8) || checkGiai_7Input(g7) || checkGiai_5_6Input(g6_1) || checkGiai_5_6Input(g6_2) || checkGiai_5_6Input(g6_3)
				|| checkGiai_5_6Input(g5) 
				|| checkGiai_1_4Input(g4_1) || checkGiai_1_4Input(g4_2) || checkGiai_1_4Input(g4_3)	
				|| checkGiai_1_4Input(g4_4)	|| checkGiai_1_4Input(g4_5) || checkGiai_1_4Input(g4_6) || checkGiai_1_4Input(g4_7)
				|| checkGiai_1_4Input(g3_1) || checkGiai_1_4Input(g3_2)	
				|| checkGiai_1_4Input(g2)
				|| checkGiai_1_4Input(g1)
				|| checkGiai_dbInput(gdb));
	}

	public boolean checkGiai_8Input(String str) {
		if (str.length() == 2) {
			//System.out.println("string Giai_8: " + str + " - string length: " + str.length());
			try {
				//System.out.println("Parse str: " + Integer.parseInt(str));
				Integer.parseInt(str);

				return true;
			} catch (Exception e) {
				System.out.println("Giai_8 data input error");
			}
		} else {
			System.out.println("string Giai_8 co do dai khong dung");
			
		}
		return false;
	}

	public boolean checkGiai_7Input(String str) {
		if (str.length() == 3) {
			//System.out.println("string Giai_7: " + str + " - string length: " + str.length());
			try {
				//System.out.println("Parse str: " + Integer.parseInt(str));
				Integer.parseInt(str);

				return true;
			} catch (Exception e) {
				System.out.println("Giai_7 data input error");
			}
		} else {
			System.out.println("string Giai_7 co do dai khong dung");
		}
		return false;
	}

	public boolean checkGiai_5_6Input(String str) {
		if (str.length() == 4) {
			//System.out.println("string Giai_5_6: " + str + " - string length: " + str.length());
			try {
				//System.out.println("Parse str: " + Integer.parseInt(str));
				Integer.parseInt(str);

				return true;
			} catch (Exception e) {
				System.out.println("Giai_5_6 data input error");
			}
		} else {
			System.out.println("string Giai_5_6 co do dai khong dung");
		}
		return false;
	}

	public boolean checkGiai_1_4Input(String str) {
		if (str.length() == 5) {
			//System.out.println("string Giai_1_4: " + str + " - string length: " + str.length());
			try {
				//System.out.println("Parse str: " + Integer.parseInt(str));
				Integer.parseInt(str);

				return true;
			} catch (Exception e) {
				System.out.println("Giai_1_4 data input error");
			}
		} else {
			System.out.println("string Giai_1_4 co do dai khong dung");
		}
		return false;
	}

	public boolean checkGiai_dbInput(String str) {
		if (str.length() == 6) {
			//System.out.println("string Giai_db: " + str + " - string length: " + str.length());
			try {
				//System.out.println("Parse str: " + Integer.parseInt(str));
				Integer.parseInt(str);

				return true;
			} catch (Exception e) {
				System.out.println("Giai_db data input error");
			}
		} else {
			System.out.println("string Giai_db co do dai khong dung");
		}
		return false;
	}
}
