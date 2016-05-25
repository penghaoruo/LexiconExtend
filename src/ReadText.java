import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReadText {
	String path = ""; // "/shared/corpora/corporaWeb/lorelei/";
	String dir = path + "hausa/data/monolingual_text/zipped/conll";
	
	public void genText() throws Exception {
		BufferedWriter bw = IOManager.openWriter("data.ha");
		
		File f = new File(dir);
		File[] files = f.listFiles();
		int size = 0;
		for (File file : files) {
			ArrayList<String> lines = IOManager.readLines(file.getAbsolutePath());
			for (String line : lines) {
				if (line.trim().equals("")) {
					bw.write("\n");
				}
				String[] strs = line.split("\t");
				if (strs.length > 5) {
					String token = strs[5].trim().toLowerCase();
					if (token.length() == 1 && notEnglish(token.charAt(0))) {
						continue;
					}
					bw.write(token + " ");
					size += 1;
				}
			}
		}
		bw.close();
		System.out.println("Token Size:" + size);
	}
	
	public void filter() throws Exception {
		BufferedWriter bw = IOManager.openWriter("data-filtered.ha");
		
		ArrayList<String> lines = IOManager.readLines("data.ha");
		ArrayList<String> words = IOManager.readLines("lexicon-filtered.ha");
		int size = 0;
		for (String line : lines) {
			String[] strs = line.split(" ");
			boolean flag = false;
			for (String token : strs) {
				if (words.contains(token)) {
					flag = true;
					bw.write(token + " ");
					size += 1;
				}
			}
			if (flag) {
				bw.write("\n");
			}
		}
		bw.close();
		System.out.println("Token Size:" + size);
	}

	private boolean notEnglish(char c) {
		if (c < 'a' || c > 'z') {
			return true;
		}
		return false;
	}
}
