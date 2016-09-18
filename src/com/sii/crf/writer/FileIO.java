package com.sii.crf.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sii.crf.model.Sentence;
import com.sii.crf.model.Token;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;

public class FileIO {

	final static String DATA_DIR1 = "/Users/luca/Desktop/data/label/";
	final static String DATA_DIR2 = "/Users/luca/Desktop/data/input/";
	final static String DATA_DIR3 = "/Users/luca/Desktop/data/eva/";
	
	public static void createDirectoriesByPolarityLabel(List<Sentence> sentences) {
		File tmp = new File(DATA_DIR1);
		tmp.mkdirs();
		for(int i=0; i<sentences.size(); i++) {
			Sentence currSentence = sentences.get(i);
			String new_dir_path = DATA_DIR1 + currSentence.getOpinions().get(0).getPolarity();
			File new_dir = new File(new_dir_path);
			new_dir.mkdirs();
			File file = new File(new_dir.getAbsolutePath(), i + ".txt");
			try {
				if (file.createNewFile()) {
					BufferedWriter writer = null;
					writer = new BufferedWriter(new FileWriter(file));
		            writer.write(currSentence.getText());
					writer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static File createFileTxtHelper(List<String> sentences) {
		File tmp = new File(DATA_DIR2);
		tmp.mkdirs();
		File file = new File(tmp.getAbsolutePath(), "input"+sentences.hashCode()+".txt");
		try {
		if (file.createNewFile()) {
				BufferedWriter writer = null;
				writer = new BufferedWriter(new FileWriter(file));
				for (String s : sentences) {
			        writer.write(s);
			        writer.newLine();
				}
				writer.close();
		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return file;
	}
	
	public static File createFileTxt(String string) {
		List<String> sentences = new ArrayList<String>();
		String[] tmp = string.split("[.?!;]");
		for (String s : tmp) {
			sentences.add(s);
		}
		File f = createFileTxtHelper(sentences);
		return f;
	}
	
	public static File createEvaluationModel() {
		File tmp = new File(DATA_DIR3);
		tmp.mkdirs();
		File file = new File(tmp.getAbsolutePath(), "evaluation.txt");
		try {
			SentenceDAO dao = new SentenceDAOImplementation();
			List<Sentence> sentences = dao.findAllFinal();
			if (file.createNewFile()) {
				BufferedWriter writer = null;
				writer = new BufferedWriter(new FileWriter(file));
				for (Sentence s : sentences) {
					for (Token t : s.getTokens()) {
						String tmpString = t.getWord() + " " + t.getPos() + " " + t.getNer() + " " + t.getSpeaker() + " " +t.getLabel();
						writer.write(tmpString);
						writer.newLine();
					}
					writer.newLine();
				}
				writer.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	public static File createInputModel(List<Sentence> sentences) {
		File tmp = new File(DATA_DIR2);
		tmp.mkdirs();
		File file = new File(tmp.getAbsolutePath(), "inputModel"+sentences.get(0).hashCode()+".txt");
		try {
			if (file.createNewFile()) {
				BufferedWriter writer = null;
				writer = new BufferedWriter(new FileWriter(file));
				for (Sentence s : sentences) {
					for (Token t : s.getTokens()) {
						String tmpString = t.getWord() + " " + t.getPos() + " " + t.getNer() + " " + t.getSpeaker();
						writer.write(tmpString);
						writer.newLine();
					}
					writer.newLine();
				}
				writer.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	
	
}
