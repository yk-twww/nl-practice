package npylm;

import java.util.*;
import npylm.Model;


public class NPYLM_train {
    private static double[] param_d = {0.2, 0.2, 0.2};
    private static double[] param_theta = {0.2, 0.2, 0.2};

    private static String BOS2 = "##<BOS2>##";
    private static String BOS1 = "##<BOS1>##";
    private static String EOS1 = "##<EOS1>##";
    private static String EOS2 = "##<EOS2>##";

    public Model train(List<List<String>> sentences) {
	List<List<String>> converted_sentences = this.convert_sentences(sentences);
	Map<Bigram, Map<String, Integer>> counter = this.count_trigram(converted_sentences);
	Restaurant_Franchise rf = new Restaurant_Franchise(counter);
	Model model = new Model(rf);

	return model;
    }

    public Map<Bigram, Map<String, Integer>>
	count_trigram2(List<List<String>> sentences) {
	Map<Bigram, Map<String, Integer>> counter = new HashMap<Bigram, Map<String, Integer>>();
	Map<String, Integer> local_counter;
	String word;
	List<String> sentence_cp = new ArrayList<String>();
	for (List<String> sentence : sentences) {
	    sentence_cp.add(BOS2);
	    sentence_cp.add(BOS1);
	    for (String iter_word : sentence) {
		sentence_cp.add(iter_word);
	    }
	    sentence_cp.add(EOS1);
	    sentence_cp.add(EOS2);

	    for (int i = 0, upper = sentence.size() + 2; i < upper; ++i) {
		Bigram bigram = new Bigram(sentence_cp.get(i), sentence_cp.get(i + 1));
		word = sentence_cp.get(i + 2);
		if (counter.containsKey(bigram)) {
		    local_counter = counter.get(bigram);
		    if (local_counter.containsKey(word)) {
			local_counter.put(word, local_counter.get(word) + 1);
		    } else {
			local_counter.put(word, 1);
		    }
		} else {
		    Map<String, Integer> new_local_counter = new HashMap<String, Integer>();
		    new_local_counter.put(word, 1);
		    counter.put(bigram, new_local_counter);
		}
	    }
	    sentence_cp.clear();
	}

	return counter;
    }

    public Map<Bigram, Map<String, Integer>>
	count_trigram(List<List<String>> c_sentences) {
	Map<Bigram, Map<String, Integer>> counter = new HashMap<Bigram, Map<String, Integer>>();
	Map<String, Integer> local_counter;
	String word;
	for (List<String> c_sentence : c_sentences) {
	    for (int i = 0, upper = c_sentence.size() - 2; i < upper; ++i) {
		Bigram bigram = new Bigram(c_sentence.get(i), c_sentence.get(i + 1));
		word = sentence_cp.get(i + 2);
		if (!counter.containsKey(bigram)) {
		    local_counter = new HashMap<String, Integer>();
		    counter.put(bigram, local_counter);
		} else {
		    local_counter = counter.get(bigram);
		}
		if (local_counter.containsKey(word)) {
		    local_counter.put(word, local_counter.get(word) + 1);
		} else {
		    local_counter.put(word, 1);
		}
	    }
	}

	return counter;
    }

    public List<List<String>> convert_sentences(List<List<String>> sentences) {
	List<List<String>> converted_sentences = new ArrayList<List<String>>();
	for (List<String> sentence : sentences) {
	    List<String> converted_sentence = new ArrayList<String>();
	    converted_sentence.add(BOS2);
	    converted_sentence.add(BOS1);
	    for (String word : sentence) {
		converted_sentence.add(word);
	    }
	    converted_sentence.add(EOS1);
	    converted_sentence.add(EOS2);

	    converted_sentences.add(converted_sentence);
	}

	return converted_sentences;
    }
}


