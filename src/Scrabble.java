import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Scrabble {

	final static HashMap<Character, Integer> letterScore = new HashMap<Character, Integer>() {
		{
			put('A', 1);
			put('E', 1);
			put('I', 1);
			put('O', 1);
			put('U', 1);
			put('L', 1);
			put('N', 1);
			put('R', 1);
			put('S', 1);
			put('T', 1);
			put('D', 2);
			put('G', 2);
			put('B', 3);
			put('C', 3);
			put('M', 3);
			put('P', 3);
			put('F', 4);
			put('H', 4);
			put('W', 4);
			put('Y', 4);
			put('V', 4);
			put('K', 5);
			put('J', 8);
			put('X', 8);
			put('Q', 20);
			put('Z', 10);

		}
	};

	private static Set<String> allWords = new HashSet<String>();

	private String getSorted(String word) {
		char[] wordArray = word.toCharArray();
		Arrays.sort(wordArray);
		return String.valueOf(wordArray);
	}

	private void getAllWords(String fileName) throws IOException {
		
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = br.readLine()) != null) {
			allWords.add(getSorted(line));
		}
		br.close();
	}

	private boolean isWord(String word) {
		return allWords.contains(word);
	}

	private int getScore(String word) {
		int score = 0;
		if (isWord(word)) {
			for (int i = 0; i < word.length(); i++) {
				score += letterScore.get(word.charAt(i));
			}
		}

		return score;
	}

	private Set<String> getAllCombinations(String tileSet) {
		Set<String> words = new HashSet<String>();

		for (int i = 0; i < tileSet.length(); i++) {

			words = addLetter(tileSet.charAt(i), words);
		}

		return words;
	}

	private Set<String> addLetter(char charAt, Set<String> words) {
		Set<String> newWords = new HashSet<String>();
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				StringBuilder str = new StringBuilder(word);
				newWords.add(getSorted(str.insert(i, charAt).toString()));
			}
		}
		newWords.add(String.valueOf(charAt));
		newWords.addAll(words);
		return newWords;
	}

	public static void main(String[] args) throws IOException {
		Scrabble game = new Scrabble();
		String filePath = args[0];
		game.getAllWords(filePath);
		String tileSet = args[1];
		int output = game.getMaxScore(tileSet);
		System.out.println("Max Score : " + output);
	}

	private int getMaxScore(String word) {

		int maxScore = 0;
		Set<String> tileSetWords = getAllCombinations(word);
		for (String str : tileSetWords) {
			int score = getScore(str);
			if (maxScore < score) {
				maxScore = score;
			}
		}

		return maxScore;
	}
}
