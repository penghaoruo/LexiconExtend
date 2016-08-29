import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReadText {
	//String path = ""; // "/shared/corpora/corporaWeb/lorelei/";
	//String dir = path + "hausa/data/monolingual_text/zipped/conll";
	String dir = "/shared/preprocessed/resources/turkish/ltf-1";
	
	public void genTurText() throws Exception {
		BufferedWriter bw = IOManager.openWriter("data-add-1.tu");
		ArrayList<String> words = IOManager.readLines("lexicon.tu");
		
		File f = new File(dir);
		File[] files = f.listFiles();
		int size = 0;
		int index = 0;
		for (File file : files) {
			index++;
			ArrayList<String> lines = IOManager.readLines(file.getAbsolutePath());
			for (String line : lines) {
				if (line.startsWith("<TOKEN")) {
					int p = line.indexOf('>');
					int q = line.indexOf("</TOKEN>", p);
					if (p < q) {
						String str = line.substring(p+1, q).toLowerCase();
						if (words.contains(str)) {
							bw.write(str + " ");
							size += 1;
						}
					}
				}
			}
			if (index % 100 == 0) {
				System.out.print(index + " ");
			}
			bw.write("\n");
		}
		bw.close();
		System.out.println("Token Size:" + size);
	}
	
	public void genHaText() throws Exception {
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
		BufferedWriter bw = IOManager.openWriter("data.tu");
		
		ArrayList<String> lines = IOManager.readLines("wikicomp-2014_tr.txt");
		ArrayList<String> words = IOManager.readLines("lexicon.tu");
		int size = 0;
		for (String line : lines) {
			String[] strs = line.toLowerCase().split(" ");
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
