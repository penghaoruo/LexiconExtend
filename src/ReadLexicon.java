import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadLexicon {
	
	String path = ""; // "/shared/corpora/corporaWeb/lorelei/";
	String file = path + "hausa/data/lexicon/hau_wordform_morph_analysis.tab";
	public Map<String, String> lex = new HashMap<String, String>();

	public void read() throws IOException {
		ArrayList<String> lines = IOManager.readLines(file);
		for (String line : lines) {
			String[] strs = line.split("\t");
			String word = strs[0].toLowerCase();
			String pos = strs[1].toLowerCase();
			if (!checkString(word)) {
				continue;
			}
			if (!lex.keySet().contains(word)) {
				lex.put(word, pos);
			}
			else {
				if (!pos.equals("none")) {
					lex.put(word, pos);
				}
			}
		}
		
		System.out.println("Total:" + lex.size());
		int count_noun = 0;
		int count_none = 0;
		
		BufferedWriter bw = IOManager.openWriter("lexicon.ha");
		for (String w : lex.keySet()) {
			if (lex.get(w).equals("noun")) {
				count_noun += 1;
				bw.write(w + "\n");
			}
			else {
				if (lex.get(w).equals("none")) {
					count_none += 1;
				}
			}
		}
		bw.close();
		System.out.println("Noun:" + count_noun);
		System.out.println("None:" + count_none);
	}

	private boolean checkString(String w) {
		for (int i = 0; i < w.length(); i++) {
			char c = w.charAt(i);
			if (c < 'a' || c > 'z') {
				return false;
			}
		}
		return true;
	}
	
	public void filter() throws IOException {
		ArrayList<String> words_ha = IOManager.readLines("lexicon.ha");
		ArrayList<String> words_en = IOManager.readLines("lexicon.en");
		ArrayList<String> words_ha_back = IOManager.readLines("lexicon-back.ha");
		
		assert(words_ha.size() == words_en.size());
		assert(words_ha_back.size() == words_en.size());
		
		BufferedWriter bw_1 = IOManager.openWriter("lexicon-filtered.ha");
		BufferedWriter bw_2 = IOManager.openWriter("lexicon-filtered.en");
		
		int index = 0;
		for (int i = 0; i < words_ha.size(); i++) {
			if (words_en.get(i).contains(" ")) {
				continue;
			}
			if (!words_ha.get(i).toLowerCase().equals(words_ha_back.get(i).toLowerCase())) {
				continue;
			}
			if (words_ha.get(i).toLowerCase().equals(words_en.get(i).toLowerCase())) {
				continue;
			}
			bw_1.write(words_ha.get(i).toLowerCase() + "\n");
			bw_2.write(words_en.get(i).toLowerCase() + "\n");
			index += 1;
		}
		System.out.println("Size:" + index);
		bw_1.close();
		bw_2.close();
	}
}
