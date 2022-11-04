package admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TextEditor {
	static String path = System.getenv("APPDATA") + "/MyApuntes_setup";
	static File route = new File(path);

	public static void LoadData(Base base) throws IOException {
		route.mkdirs();
		File file = new File(path + "/data.txt");
		if (file.createNewFile() == true) {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			String start = "TUTORIAL: 1%CREAR APUNTE\r\n" + "/|\\ TÍTULO			/|\\ MATERIA\r\n"
					+ " |				 |\r\n" + "\r\n" + "\r\n" + "<--TITULOS\r\n" + "\r\n"
					+ "		TEXTO DE EJEMPLO\r\n" + "aSdFlKjH999";
			writer.write(start);
			writer.close();
		}
		base.getAPUNTS().removeAll(base.getAPUNTS());
		base.getMATERIAS().removeAll(base.getMATERIAS());
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String st, text, m = null, titMat[] = null;
		while ((st = br.readLine()) != null) {
			titMat = st.split("%");
			m = titMat[0];
			if (!base.getMATERIAS().contains(m)) {
				base.getMATERIAS().add(m);
			}
			text = "";
			boolean flag = true;
			while (flag == true) {
				if ((st = br.readLine()) != null) {
					if (!st.equals("aSdFlKjH999")) {
						text = text + st + "\n";
					} else {
						flag = false;
					}
				} else {
					flag = false;
				}
			}
			Apunte a = new Apunte(m, titMat[1], text);
			base.getAPUNTS().add(a);
		}
		br.close();
	}

	public static String[] LoadParams(int nParams) throws IOException {
		route.mkdirs();
		String[] answer = new String[nParams];
		File file = new File(path + "/params.txt");
		file.createNewFile();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String st;
		// predeterminados
		answer[0] = null;
		answer[1] = "false";
		answer[2] = "null";
		for (int i = 0; i < nParams; i++) {
			if ((st = br.readLine()) != null) {
				answer[i] = st;
			}
		}
		br.close();
		return answer;
	}

	private static void write(String a) throws IOException {
		route.mkdirs();
		File file = new File(path + "/data.txt");
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		writer.write(a);
		writer.close();
	}

	public static void write(String contra, boolean modoNoct, String lastOne) throws IOException {
		route.mkdirs();
		File file = new File(path + "/params.txt");
		file.createNewFile();
		String total = (contra) + "\n";
		if (modoNoct == true) {
			total = total + ("true") + "\n";
		} else {
			total = total + ("false") + "\n";
		}
		total = total + lastOne + "\n";
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		writer.write(total);
		writer.close();
	}

	public static void reload(Base base) {
		String total = "";
		if (base.getAPUNTS().isEmpty() == false) {
			for (Apunte a : base.getAPUNTS()) {
				total = total + a.getMateria() + "%" + a.getTitle() + "\n" + a.getText() + "\naSdFlKjH999\n";
			}
			total.substring(0, total.length() - 1);
			try {
				write(total);
				LoadData(base);
			} catch (IOException e) {
				return;
			}
		} else {
			try {
				write(total);
			} catch (IOException e) {
				return;
			}
		}
	}
}
