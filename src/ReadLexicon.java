import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ReadLexicon {
	
	String path = "/shared/corpora/corporaWeb/lorelei/";
	String file = path + "hausa/lexicon/hau_wordform_morph_analysis.tab";
	public Map<String, String> lex = new HashMap<String, String>();

	public void read() {
		ArrayList<String> lines = IOManager.readLines(file);
		for (String line : lines) {
			String[] strs = line.split("\t");
			String word = strs[0].toLowerCase();
			String pos = strs[1].toLowerCase();
			if (!lex.keySet().contains(word)) {
				lex.put(word, pos);
			}
			else {
				if (!pos.equals("none")) {
					lex.put(word, pos);
				}
			}
		}
	}
}
