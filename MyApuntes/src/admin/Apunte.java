package admin;

public class Apunte implements Comparable<Object> {

	private String TITLE, TEXT, MATERIA;

	public Apunte(String materia, String title, String text) {
		this.MATERIA = materia;
		this.TITLE = title;
		this.TEXT = text;
	}

	public String getTitle() {
		return this.TITLE;
	}

	public String getText() {
		return this.TEXT;
	}

	public String getMateria() {
		return this.MATERIA;
	}

	public void setText(String s) {
		this.TEXT = s;
	}

	@Override
	public int compareTo(Object arg0) {
		if (this.getMateria() == ((Apunte) arg0).getMateria()) {
			return (this.getTitle().compareTo(((Apunte) arg0).getTitle()));
		} else {
			return (this.getMateria().compareTo(((Apunte) arg0).getMateria()));
		}
	}
}
