/** Purpose of Program: NLPUtility & Main used must tokenize input, display word map sorted by key (ascending).
 * Displays word map sorted by value (descending). Displays Sentiment Positive and Negative.
*Displays Most frequent word(s). Cannot modify Main
* Author: Melissa Mandyck Instructor: Duane Jarc  *Date: 06/11/2025 
*Modifications: none      
 */

// CMSC315PROJ2MandyckM Java File

import java.util.*;

public class NLPUtility {

    /**
     * Splits the given text into word tokens using one or more whitespace
     * or punctuation characters as delimiters.
     *
     * @param text the input string to be tokenized
     * @return an array of word tokens, excluding punctuation and whitespace
     */
    public static String[] splitTextIntoTokens(String text) {
        if (text == null || text.isEmpty()) {
            return new String[0];
        }

        // Token Splitting of One or More Whitespace or Punctuation Characters
        String[] rawTokens = text.split("[\\s\\p{P}]+"); 

        // Filter Empty tokens
        List<String> tokens = new ArrayList<>();
        for (String token : rawTokens) {
            if (!token.isEmpty()) {
                tokens.add(token);
            }
        }

        return tokens.toArray(new String[0]);
    }

    /**
     * Counts the frequency of words in the given array, excluding those present in
     * the specified set of stop words.
     * The comparison is case-insensitive, and results are stored in a
     * {@link TreeMap} sorted alphabetically by word.
     *
     * @param words     An array of tokenized words to analyze.
     * @param stopWords A set of words to exclude from the frequency count.
     * @return A {@link TreeMap} mapping each non-stop word to its frequency, sorted
     *         alphabetically.
     */
    public static TreeMap<String, Integer> countFilteredWords(String[] words, Set<String> stopWords) {
        TreeMap<String, Integer> result = new TreeMap<>();

        for (String word : words) {
            String lowerWord = word.toLowerCase();
            if (!stopWords.contains(lowerWord) && !lowerWord.isEmpty()) {
                result.put(lowerWord, result.getOrDefault(lowerWord, 0) + 1);
            }
        }

        return result;
    }

    /**
     * Sorts the entries of a map by their values in descending order.
     * The result is returned as a {@link LinkedHashMap} to preserve the order of
     * sorted entries.
     *
     * @param map A map containing keys and integer values to be sorted by value.
     * @return A {@link LinkedHashMap} containing the same entries as the input map,
     *         sorted in descending order by value.
     */
    public static LinkedHashMap<String, Integer> sortByValueDescending(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());

        entryList.sort((e1, e2) -> {
            int cmp = e2.getValue().compareTo(e1.getValue());
            if (cmp != 0) {
                return cmp;
            } else {
                return 0; // Insertion Order Maintained for ties
            }
        });

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     * Performs sentiment analysis by scanning the word-frequency map.
     * Adds up the total frequency of all words found in the predefined
     * positive and negative word sets.
     *
     * @param wordMap A map of words and their frequencies.
     * @return A summary string in the format: "Positive: X, Negative: Y"
     */
    public static String getSentiment(Map<String, Integer> wordMap, Set<String> positiveWords,
                                      Set<String> negativeWords) {
        int positiveCount = 0;
        int negativeCount = 0;

        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            String word = entry.getKey();
            int freq = entry.getValue();

            if (positiveWords.contains(word)) {
                positiveCount += freq;
            } else if (negativeWords.contains(word)) {
                negativeCount += freq;
            }
        }

        return "Positive: " + positiveCount + ", Negative: " + negativeCount; // Positive & Negative Words Displayed
    }

    /**
     * Finds the words with the highest frequency in the given map and returns a map
     * containing a sorted word list along with the maximum frequency value.
     *
     * @param wordMap A map of words and their corresponding frequencies.
     * @return A map containing:
     *         - "words": A list of words with the highest frequency, sorted
     *           alphabetically.
     *         - "frequency": The highest frequency value.
     */
    public static Map<String, Object> getWordsWithMaxFrequency(Map<String, Integer> wordMap) {
        Map<String, Object> result = new HashMap<>();

        int maxFreq = 0;
        for (int freq : wordMap.values()) {
            if (freq > maxFreq) {
                maxFreq = freq; // Maximum Frequency Value
            }
        }

        List<String> mostFrequentWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            if (entry.getValue() == maxFreq) {
                mostFrequentWords.add(entry.getKey()); 
            }
        }

        Collections.sort(mostFrequentWords);
        result.put("words", mostFrequentWords); // Gets Most Frequent Word(s)
        result.put("frequency", maxFreq); // Gets Highest Frequency Value

        return result;
    }
}


//ends CMSC315PROJ2MandyckM Java File
