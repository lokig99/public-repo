package GameLogic;

public interface SaperCaller {

	void callAllFields(int ActionCode);
	void callField(int x, int y, int ActionCode);
	void addSaperListener(SaperListener listener);

	final int REVEAL_FIELD = 0;
}
