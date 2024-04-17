
package acme.entities.code_audits;

public enum Mark {

	A_PLUS(6), A(5), B(4), C(3), D(2), F(1), F_MINUS(0);


	private final int numericMark;


	Mark(int numericMark) {
		this.numericMark = numericMark;
	}

	public int getNumericMark() {
		return this.numericMark;
	}

	public static Mark byNumericMark(int numericMark) {
		Mark finalMark = Mark.F_MINUS;
		if (numericMark > 6)
			finalMark = Mark.A_PLUS;
		else if (numericMark > 0)
			for (Mark mark : Mark.values()) {
				if (mark.getNumericMark() == numericMark)
					finalMark = mark;
			}
		return finalMark;
	}
}
