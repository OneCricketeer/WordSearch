import java.util.Hashtable;

public class CharScanner extends AbstractScanner<Character> {
	private final StringBuilder sb;
	private static Hashtable<String, Short> wordList;

	public CharScanner(Character[][] arr2D, int size, AbstractScanner.Dir dir) {
		super(arr2D, size, dir);
		this.sb = new StringBuilder();
	}

	public Hashtable<String, Short> getWordList() {
		return CharScanner.wordList;
	}

	public void setWordList(Hashtable<String, Short> wordList) {
		CharScanner.wordList = wordList;
	}

	public String getString() {
		for (int i = 0; i < super.data.size(); i++) {
			char c = Character.toLowerCase(super.data.get(i));
			sb.append(c);
		}

		checkWord();

		return sb.toString();

	}

	private void checkWord() {
		// Get the word in both directions
		String str = sb.toString();
		String rev = sb.reverse().toString();

		// For-each non-found word
		for (String word : wordList.keySet()) {
			if (wordList.get(word) == 0) {
				// If word found forwards or backwards
				if (str.equalsIgnoreCase(word) || rev.equalsIgnoreCase(word)) {
					// Increment the word found counter
					wordList.put(word, (short) 1);
					System.out.println(super.getInfo() + " " + word.toUpperCase());
				}
			}
		}
	}

	@Override
	protected void empty() {
		getString();
		if (sb.length() != 0) {
			sb.delete(0, sb.length());
		}
		super.data.clear();
	}

	public int getNumFound() {
		int found = 0;
		for (Short s : CharScanner.wordList.values()) {
			if (s > 0) {
				found++;
			}
		}
		return found;
	}

}
