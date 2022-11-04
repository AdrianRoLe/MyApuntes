package admin;

import java.util.ArrayList;

public class Base {
	private ArrayList<String> MATERIAS;
	private ArrayList<Apunte> APUNTS;

	public Base() {
		MATERIAS = new ArrayList<String>();
		APUNTS = new ArrayList<Apunte>();
	}

	public ArrayList<String> getMATERIAS() {
		return this.MATERIAS;
	}

	public ArrayList<Apunte> getAPUNTS() {
		return this.APUNTS;
	}
}
