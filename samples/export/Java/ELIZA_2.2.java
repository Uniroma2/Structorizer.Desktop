// Generated by Structorizer 3.30-02 
//  
// Copyright (C) 2018-05-14 ??? 
// License: GPLv3-link 
// GNU General Public License (V 3) 
// https://www.gnu.org/licenses/gpl.html 
// http://www.gnu.de/documents/gpl.de.html 
//  

import java.util.Scanner;

import javax.swing.*;
/**
 * Concept and lisp implementation published by Joseph Weizenbaum (MIT):
 * "ELIZA - A Computer Program For the Study of Natural Language Communication Between Man and Machine" - In:
 * Computational Linguistis 1(1966)9, pp. 36-45
 * Revision history:
 * 2016-10-06 Initial version
 * 2017-03-29 Two diagrams updated (comments translated to English)
 * 2017-03-29 More keywords and replies added
 * 2019-03-14 Replies and mapping reorganised for easier maintenance
 * 2019-03-15 key map joined from keyword array and index map
 * 2019-03-28 Keyword "bot" inserted (same reply ring as "computer")
 */
public class ELIZA {


	private class KeyMapEntry{
		public String	keyword;
		public int	index;
		public KeyMapEntry(String p_keyword, int p_index)
		{
			keyword = p_keyword;
			index = p_index;
		}
	};
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// BEGIN initialization for "KeyMapEntry" 
		// END initialization for "KeyMapEntry" 
		
		// TODO: Check and accomplish variable declarations: 
		String varPart;
		// Converts the input to lowercase, cuts out interpunctation 
		// and pads the string 
		String userInput;
		String reply;
		int posAster;
		??? offsets;
		boolean isRepeated;
		boolean isGone;
		// Stores the last five inputs of the user in a ring buffer, 
		// the first element is the current insertion index 
		???[] history;
		int[] findInfo;
		
		
		// TODO: You may have to modify input instructions, 
		//       e.g. by replacing nextLine() with a more suitable call 
		//       according to the variable type, say nextInt(). 
		
		// Title information 
		System.out.println("************* ELIZA **************");
		System.out.println("* Original design by J. Weizenbaum");
		System.out.println("**********************************");
		System.out.println("* Adapted for Basic on IBM PC by");
		System.out.println("* - Patricia Danielson");
		System.out.println("* - Paul Hashfield");
		System.out.println("**********************************");
		System.out.println("* Adapted for Structorizer by");
		System.out.println("* - Kay Gürtzig / FH Erfurt 2016");
		System.out.println("* Version: 2.2 (2019-03-28)");
		System.out.println("**********************************");
		// Stores the last five inputs of the user in a ring buffer, 
		// the first element is the current insertion index 
		history = new ???[]{0, "", "", "", "", ""};
		final String[][] replies = setupReplies();
		final String[][] reflexions = setupReflexions();
		final String[][] byePhrases = setupGoodByePhrases();
		final KeyMapEntry[] keyMap = setupKeywords();
		offsets[length(keyMap)-1] = 0;
		isGone = false;
		// Starter 
		System.out.println("Hi! I\'m your new therapist. My name is Eliza. What\'s your problem?");
		do {
			userInput = (new Scanner(System.in)).nextLine();
			// Converts the input to lowercase, cuts out interpunctation 
			// and pads the string 
			// Converts the input to lowercase, cuts out interpunctation 
			// and pads the string 
			userInput = normalizeInput(userInput);
			isGone = checkGoodBye(userInput, byePhrases);
			if (! isGone) {
				reply = "Please don\'t repeat yourself!";
				isRepeated = checkRepetition(history, userInput);
				if (! isRepeated) {
					findInfo = findKeyword(keyMap, userInput);
					keyIndex = findInfo[0];
					if (keyIndex < 0) {
						// Should never happen... 
						keyIndex = length(keyMap)-1;
					}
					KeyMapEntry entry = keyMap[keyIndex];
					// Variable part of the reply 
					varPart = "";
					if (length(entry.keyword) > 0) {
						varPart = conjugateStrings(userInput, entry.keyword, findInfo[1], reflexions);
					}
					replyRing = replies[entry.index];
					reply = replyRing[offsets[keyIndex]];
					offsets[keyIndex] = (offsets[keyIndex] + 1) % length(replyRing);
					posAster = pos("*", reply);
					if (posAster > 0) {
						if (varPart == " ") {
							reply = "You will have to elaborate more for me to help you.";
						}
						else {
							delete(reply, posAster, 1);
							insert(varPart, reply, posAster);
						}
					}
					reply = adjustSpelling(reply);
				}
				System.out.println(reply);
			}
		} while (!(isGone));
	}

	/**
	 * Cares for correct letter case among others
	 * @param sentence
	 * @return 
	 */
	private static String adjustSpelling(String sentence) {
		// TODO: Check and accomplish variable declarations: 
		String word;
		String start;
		String result;
		int position;
		
		
		result = sentence;
		position = 1;
		while ((position <= length(sentence)) && (copy(sentence, position, 1) == " ")) {
			position = position + 1;
		}
		if (position <= length(sentence)) {
			start = copy(sentence, 1, position);
			delete(result, 1, position);
			insert(uppercase(start), result, 1);
		}
		for (String word : new String[]{" i ", " i\'"}) {
			position = pos(word, result);
			while (position > 0) {
				delete(result, position+1, 1);
				insert("I", result, position+1);
				position = pos(word, result);
			}
		}
		
		return result;
	}

	/**
	 * Checks whether the given text contains some kind of
	 * good-bye phrase inducing the end of the conversation
	 * and if so writes a correspding good-bye message and
	 * returns true, otherwise false
	 * @param text
	 * @param phrases
	 * @return 
	 */
	private static boolean checkGoodBye(String text, String[][] phrases) {
		// TODO: Check and accomplish variable declarations: 
		boolean saidBye;
		String[] pair;
		
		
		for (@String pair : phrases) {
			if (pos(pair[0], text) > 0) {
				saidBye = true;
				System.out.println(pair[1]);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether newInput has occurred among the last
	 * length(history) - 1 input strings and updates the history
	 * @param history
	 * @param newInput
	 * @return 
	 */
	private static boolean checkRepetition(array[] history, String newInput) {
		// TODO: Check and accomplish variable declarations: 
		boolean hasOccurred;
		
		
		hasOccurred = false;
		if (length(newInput) > 4) {
			currentIndex = history[0];;
			for (int i = 1; i <= length(history)-1; i += (1)) {
				if (newInput == history[i]) {
					hasOccurred = true;
				}
			}
			history[history[0]+1] = newInput;
			history[0] = (history[0] + 1) % (length(history) - 1);
		}
		return hasOccurred;
	}

	/**
	 * @param sentence
	 * @param key
	 * @param keyPos
	 * @param flexions
	 * @return 
	 */
	private static String conjugateStrings(String sentence, String key, int keyPos, String[][] flexions) {
		// TODO: Check and accomplish variable declarations: 
		String right;
		String result;
		int position;
		String[] pair;
		String left;
		
		
		result = " " + copy(sentence, keyPos + length(key), length(sentence)) + " ";
		for (@String pair : flexions) {
			left = "";
			right = result;
			position = pos(pair[0], right);
			while (position > 0) {
				left = left + copy(right, 1, position-1) + pair[1];
				right = copy(right, position + length(pair[0]), length(right));
				position = pos(pair[0], right);
			}
			result = left + right;
		}
		// Eliminate multiple spaces 
		position = pos("  ", result);
		while (position > 0) {
			result = copy(result, 1, position-1) + copy(result, position+1, length(result));
			position = pos("  ", result);
		}
		
		return result;
	}

	/**
	 * Looks for the occurrence of the first of the strings
	 * contained in keywords within the given sentence (in
	 * array order).
	 * Returns an array of
	 * 0: the index of the first identified keyword (if any, otherwise -1),
	 * 1: the position inside sentence (0 if not found)
	 * @param keyMap
	 * @param sentence
	 * @return 
	 */
	private static int[] findKeyword(final array of KeyMapEntry keyMap, String sentence) {
		// TODO: Check and accomplish variable declarations: 
		int[] result;
		int position;
		int i;
		
		
		// Contains the index of the keyword and its position in sentence 
		result = new int[]{-1, 0};
		i = 0;
		while ((result[0] < 0) && (i < length(keyMap))) {
			KeyMapEntry entry = keyMap[i];
			position = pos(entry.keyword, sentence);
			if (position > 0) {
				result[0] = i;
				result[1] = position;
			}
			i = i+1;
		}
		
		return result;
	}

	/**
	 * Converts the sentence to lowercase, eliminates all
	 * interpunction (i.e. ',', '.', ';'), and pads the
	 * sentence among blanks
	 * @param sentence
	 * @return 
	 */
	private static String normalizeInput(String sentence) {
		// TODO: Check and accomplish variable declarations: 
		String symbol;
		String result;
		int position;
		
		
		sentence = lowercase(sentence);
		for (String symbol : new String[]{'.', ',', ';', '!', '?'}) {
			position = pos(symbol, sentence);
			while (position > 0) {
				sentence = copy(sentence, 1, position-1) + copy(sentence, position+1, length(sentence));
				position = pos(symbol, sentence);
			}
		}
		result = " " + sentence + " ";
		
		return result;
	}

	/**
	 * @return 
	 */
	private static String[][] setupGoodByePhrases() {
		// TODO: Check and accomplish variable declarations: 
		??? phrases;
		
		
		phrases[0] = new Object[]{" shut", "Okay. If you feel that way I\'ll shut up. ... Your choice."};
		phrases[1] = new Object[]{"bye", "Well, let\'s end our talk for now. See you later. Bye."};
		return phrases;
	}

	/**
	 * The lower the index the higher the rank of the keyword (search is sequential).
	 * The index of the first keyword found in a user sentence maps to a respective
	 * reply ring as defined in `setupReplies()´.
	 * @return 
	 */
	private static KeyMapEntry[] setupKeywords() {
		// TODO: Check and accomplish variable declarations: 
		??? keywords;
		
		
		// The empty key string (last entry) is the default clause - will always be found 
		keywords[39] = new KeyMapEntry("", 29);
		keywords[0] = new KeyMapEntry("can you ", 0);
		keywords[1] = new KeyMapEntry("can i ", 1);
		keywords[2] = new KeyMapEntry("you are ", 2);
		keywords[3] = new KeyMapEntry("you\'re ", 2);
		keywords[4] = new KeyMapEntry("i don't ", 3);
		keywords[5] = new KeyMapEntry("i feel ", 4);
		keywords[6] = new KeyMapEntry("why don\'t you ", 5);
		keywords[7] = new KeyMapEntry("why can\'t i ", 6);
		keywords[8] = new KeyMapEntry("are you ", 7);
		keywords[9] = new KeyMapEntry("i can\'t ", 8);
		keywords[10] = new KeyMapEntry("i am ", 9);
		keywords[11] = new KeyMapEntry("i\'m ", 9);
		keywords[12] = new KeyMapEntry("you ", 10);
		keywords[13] = new KeyMapEntry("i want ", 11);
		keywords[14] = new KeyMapEntry("what ", 12);
		keywords[15] = new KeyMapEntry("how ", 12);
		keywords[16] = new KeyMapEntry("who ", 12);
		keywords[17] = new KeyMapEntry("where ", 12);
		keywords[18] = new KeyMapEntry("when ", 12);
		keywords[19] = new KeyMapEntry("why ", 12);
		keywords[20] = new KeyMapEntry("name ", 13);
		keywords[21] = new KeyMapEntry("cause ", 14);
		keywords[22] = new KeyMapEntry("sorry ", 15);
		keywords[23] = new KeyMapEntry("dream ", 16);
		keywords[24] = new KeyMapEntry("hello ", 17);
		keywords[25] = new KeyMapEntry("hi ", 17);
		keywords[26] = new KeyMapEntry("maybe ", 18);
		keywords[27] = new KeyMapEntry(" no", 19);
		keywords[28] = new KeyMapEntry("your ", 20);
		keywords[29] = new KeyMapEntry("always ", 21);
		keywords[30] = new KeyMapEntry("think ", 22);
		keywords[31] = new KeyMapEntry("alike ", 23);
		keywords[32] = new KeyMapEntry("yes ", 24);
		keywords[33] = new KeyMapEntry("friend ", 25);
		keywords[34] = new KeyMapEntry("computer", 26);
		keywords[35] = new KeyMapEntry("bot ", 26);
		keywords[36] = new KeyMapEntry("smartphone", 27);
		keywords[37] = new KeyMapEntry("father ", 28);
		keywords[38] = new KeyMapEntry("mother ", 28);
		return keywords;
	}

	/**
	 * Returns an array of pairs of mutualy substitutable 
	 * @return 
	 */
	private static String[][] setupReflexions() {
		// TODO: Check and accomplish variable declarations: 
		??? reflexions;
		
		
		reflexions[0] = new Object[]{" are ", " am "};
		reflexions[1] = new Object[]{" were ", " was "};
		reflexions[2] = new Object[]{" you ", " I "};
		reflexions[3] = new Object[]{" your", " my"};
		reflexions[4] = new Object[]{" i\'ve ", " you\'ve "};
		reflexions[5] = new Object[]{" i\'m ", " you\'re "};
		reflexions[6] = new Object[]{" me ", " you "};
		reflexions[7] = new Object[]{" my ", " your "};
		reflexions[8] = new Object[]{" i ", " you "};
		reflexions[9] = new Object[]{" am ", " are "};
		return reflexions;
	}

	/**
	 * This routine sets up the reply rings addressed by the key words defined in
	 * routine `setupKeywords()´ and mapped hitherto by the cross table defined
	 * in `setupMapping()´
	 * @return 
	 */
	private static String[][] setupReplies() {
		// TODO: Check and accomplish variable declarations: 
		String[][] setupReplies;
		
		
		String[][] replies;
		// We start with the highest index for performance reasons 
		// (is to avoid frequent array resizing) 
		replies[29] = new Object[]{"Say, do you have any psychological problems?", "What does that suggest to you?", "I see.", "I'm not sure I understand you fully.", "Come come elucidate your thoughts.", "Can you elaborate on that?", "That is quite interesting."};
		replies[0] = new Object[]{"Don't you believe that I can*?", "Perhaps you would like to be like me?", "You want me to be able to*?"};
		replies[1] = new Object[]{"Perhaps you don't want to*?", "Do you want to be able to*?"};
		replies[2] = new Object[]{"What makes you think I am*?", "Does it please you to believe I am*?", "Perhaps you would like to be*?", "Do you sometimes wish you were*?"};
		replies[3] = new Object[]{"Don't you really*?", "Why don't you*?", "Do you wish to be able to*?", "Does that trouble you*?"};
		replies[4] = new Object[]{"Do you often feel*?", "Are you afraid of feeling*?", "Do you enjoy feeling*?"};
		replies[5] = new Object[]{"Do you really believe I don't*?", "Perhaps in good time I will*.", "Do you want me to*?"};
		replies[6] = new Object[]{"Do you think you should be able to*?", "Why can't you*?"};
		replies[7] = new Object[]{"Why are you interested in whether or not I am*?", "Would you prefer if I were not*?", "Perhaps in your fantasies I am*?"};
		replies[8] = new Object[]{"How do you know you can't*?", "Have you tried?", "Perhaps you can now*."};
		replies[9] = new Object[]{"Did you come to me because you are*?", "How long have you been*?", "Do you believe it is normal to be*?", "Do you enjoy being*?"};
		replies[10] = new Object[]{"We were discussing you--not me.", "Oh, I*.", "You're not really talking about me, are you?"};
		replies[11] = new Object[]{"What would it mean to you if you got*?", "Why do you want*?", "Suppose you soon got*...", "What if you never got*?", "I sometimes also want*."};
		replies[12] = new Object[]{"Why do you ask?", "Does that question interest you?", "What answer would please you the most?", "What do you think?", "Are such questions on your mind often?", "What is it that you really want to know?", "Have you asked anyone else?", "Have you asked such questions before?", "What else comes to mind when you ask that?"};
		replies[13] = new Object[]{"Names don't interest me.", "I don't care about names -- please go on."};
		replies[14] = new Object[]{"Is that the real reason?", "Don't any other reasons come to mind?", "Does that reason explain anything else?", "What other reasons might there be?"};
		replies[15] = new Object[]{"Please don't apologize!", "Apologies are not necessary.", "What feelings do you have when you apologize?", "Don't be so defensive!"};
		replies[16] = new Object[]{"What does that dream suggest to you?", "Do you dream often?", "What persons appear in your dreams?", "Are you disturbed by your dreams?"};
		replies[17] = new Object[]{"How do you do ...please state your problem."};
		replies[18] = new Object[]{"You don't seem quite certain.", "Why the uncertain tone?", "Can't you be more positive?", "You aren't sure?", "Don't you know?"};
		replies[19] = new Object[]{"Are you saying no just to be negative?", "You are being a bit negative.", "Why not?", "Are you sure?", "Why no?"};
		replies[20] = new Object[]{"Why are you concerned about my*?", "What about your own*?"};
		replies[21] = new Object[]{"Can you think of a specific example?", "When?", "What are you thinking of?", "Really, always?"};
		replies[22] = new Object[]{"Do you really think so?", "But you are not sure you*?", "Do you doubt you*?"};
		replies[23] = new Object[]{"In what way?", "What resemblance do you see?", "What does the similarity suggest to you?", "What other connections do you see?", "Could there really be some connection?", "How?", "You seem quite positive."};
		replies[24] = new Object[]{"Are you sure?", "I see.", "I understand."};
		replies[25] = new Object[]{"Why do you bring up the topic of friends?", "Do your friends worry you?", "Do your friends pick on you?", "Are you sure you have any friends?", "Do you impose on your friends?", "Perhaps your love for friends worries you."};
		replies[26] = new Object[]{"Do computers worry you?", "Are you talking about me in particular?", "Are you frightened by machines?", "Why do you mention computers?", "What do you think machines have to do with your problem?", "Don't you think computers can help people?", "What is it about machines that worries you?"};
		replies[27] = new Object[]{"Do you sometimes feel uneasy without a smartphone?", "Have you had these phantasies before?", "Does the world seem more real for you via apps?"};
		replies[28] = new Object[]{"Tell me more about your family.", "Who else in your family*?", "What does family relations mean for you?", "Come on, How old are you?"};
		setupReplies = replies;
		
		return setupReplies;
	}

}