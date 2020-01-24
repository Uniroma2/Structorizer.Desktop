10 REM Concept and lisp implementation published by Joseph Weizenbaum (MIT): 
20 REM "ELIZA - A Computer Program For the Study of Natural Language Communication Between Man and Machine" - In: 
30 REM Computational Linguistis 1(1966)9, pp. 36-45 
40 REM Revision history: 
50 REM 2016-10-06 Initial version 
60 REM 2017-03-29 Two diagrams updated (comments translated to English) 
70 REM 2017-03-29 More keywords and replies added 
80 REM 2019-03-14 Replies and mapping reorganised for easier maintenance 
90 REM 2019-03-15 key map joined from keyword array and index map 
100 REM 2019-03-28 Keyword "bot" inserted (same reply ring as "computer") 
110 REM Generated by Structorizer 3.30-02 
120 REM  
130 REM Copyright (C) 2018-05-14 ??? 
140 REM License: GPLv3-link 
150 REM GNU General Public License (V 3) 
160 REM https://www.gnu.org/licenses/gpl.html 
170 REM http://www.gnu.de/documents/gpl.de.html 
180 REM  
190 REM  
200 REM program ELIZA
210 REM TODO: add the respective type suffixes to your variable names if required 
220 REM Title information 
230 PRINT "************* ELIZA **************"
240 PRINT "* Original design by J. Weizenbaum"
250 PRINT "**********************************"
260 PRINT "* Adapted for Basic on IBM PC by"
270 PRINT "* - Patricia Danielson"
280 PRINT "* - Paul Hashfield"
290 PRINT "**********************************"
300 PRINT "* Adapted for Structorizer by"
310 PRINT "* - Kay Gürtzig / FH Erfurt 2016"
320 PRINT "* Version: 2.2 (2019-03-28)"
330 PRINT "**********************************"
340 REM Stores the last five inputs of the user in a ring buffer, 
350 REM the first element is the current insertion index 
360 REM TODO: Check indexBase value (automatically generated) 
370 LET indexBase = 0
380 LET history(indexBase + 0) = 0
390 LET history(indexBase + 1) = ""
400 LET history(indexBase + 2) = ""
410 LET history(indexBase + 3) = ""
420 LET history(indexBase + 4) = ""
430 LET history(indexBase + 5) = ""
440 LET CALL const replies = setupReplies()
450 LET CALL const reflexions = setupReflexions()
460 LET CALL const byePhrases = setupGoodByePhrases()
470 LET CALL const keyMap = setupKeywords()
480 LET offsets(length(keyMap)-1) = 0
490 LET isGone = false
500 REM Starter 
510 PRINT "Hi! I\'m your new therapist. My name is Eliza. What\'s your problem?"
520 DO
530   INPUT userInput
540   REM Converts the input to lowercase, cuts out interpunctation 
550   REM and pads the string 
560   LET CALL userInput = normalizeInput(userInput)
570   LET CALL isGone = checkGoodBye(userInput, byePhrases)
580   IF NOT isGone THEN
590     LET reply = "Please don\'t repeat yourself!"
600     LET CALL isRepeated = checkRepetition(history, userInput)
610     IF NOT isRepeated THEN
620       LET CALL findInfo = findKeyword(keyMap, userInput)
630       LET keyIndex = findInfo(0)
640       IF keyIndex < 0 THEN
650         REM Should never happen... 
660         LET keyIndex = length(keyMap)-1
670       END IF
680       LET var entry: KeyMapEntry = keyMap(keyIndex)
690       REM Variable part of the reply 
700       LET varPart = ""
710       IF length(entry.keyword) > 0 THEN
720         LET CALL varPart = conjugateStrings(userInput, entry.keyword, findInfo(1), reflexions)
730       END IF
740       LET replyRing = replies(entry.index)
750       LET reply = replyRing(offsets(keyIndex))
760       LET offsets(keyIndex) = (offsets(keyIndex) + 1) % length(replyRing)
770       LET posAster = pos("*", reply)
780       IF posAster > 0 THEN
790         IF varPart = " " THEN
800           LET reply = "You will have to elaborate more for me to help you."
810         ELSE
820           delete(reply, posAster, 1)
830           insert(varPart, reply, posAster)
840         END IF
850       END IF
860       LET CALL reply = adjustSpelling(reply)
870     END IF
880     PRINT reply
890   END IF
900 LOOP UNTIL isGone
910 END
920 REM  
930 REM Cares for correct letter case among others 
940 REM TODO: Add type-specific suffixes where necessary! 
950 FUNCTION adjustSpelling(sentence AS String) AS String
960   REM TODO: add the respective type suffixes to your variable names if required 
970   LET result = sentence
980   LET position = 1
990   DO WHILE (position <= length(sentence)) AND (copy(sentence, position, 1) = " ")
1000     LET position = position + 1
1010   LOOP
1020   IF position <= length(sentence) THEN
1030     LET start = copy(sentence, 1, position)
1040     delete(result, 1, position)
1050     insert(uppercase(start), result, 1)
1060   END IF
1070   DIM array6e2d7c61() AS String = {" i ", " i\'"}
1080   FOR EACH word IN array6e2d7c61
1090     LET position = pos(word, result)
1100     DO WHILE position > 0
1110       delete(result, position+1, 1)
1120       insert("I", result, position+1)
1130       LET position = pos(word, result)
1140     LOOP
1150   NEXT word
1160   RETURN result
1170 END FUNCTION
1180 REM  
1190 REM Checks whether the given text contains some kind of 
1200 REM good-bye phrase inducing the end of the conversation 
1210 REM and if so writes a correspding good-bye message and 
1220 REM returns true, otherwise false 
1230 REM TODO: Add type-specific suffixes where necessary! 
1240 FUNCTION checkGoodBye(text AS String, phrases AS array of array[0..1] of string) AS boolean
1250   REM TODO: add the respective type suffixes to your variable names if required 
1260   FOR EACH pair IN phrases
1270     IF pos(pair(0), text) > 0 THEN
1280       LET saidBye = true
1290       PRINT pair(1)
1300       RETURN true
1310     END IF
1320   NEXT pair
1330   return false
1340 END FUNCTION
1350 REM  
1360 REM Checks whether newInput has occurred among the last 
1370 REM length(history) - 1 input strings and updates the history 
1380 REM TODO: Add type-specific suffixes where necessary! 
1390 FUNCTION checkRepetition(history AS array, newInput AS String) AS boolean
1400   REM TODO: add the respective type suffixes to your variable names if required 
1410   LET hasOccurred = false
1420   IF length(newInput) > 4 THEN
1430     LET currentIndex = history(0);
1440     FOR i = 1 TO length(history)-1
1450       IF newInput = history(i) THEN
1460         LET hasOccurred = true
1470       END IF
1480     NEXT i
1490     LET history(history(0)+1) = newInput
1500     LET history(0) = (history(0) + 1) % (length(history) - 1)
1510   END IF
1520   return hasOccurred
1530 END FUNCTION
1540 REM  
1550 REM TODO: Add type-specific suffixes where necessary! 
1560 FUNCTION conjugateStrings(sentence AS String, key AS String, keyPos AS integer, flexions AS array of array[0..1] of string) AS String
1570   REM TODO: add the respective type suffixes to your variable names if required 
1580   LET result = " " + copy(sentence, keyPos + length(key), length(sentence)) + " "
1590   FOR EACH pair IN flexions
1600     LET left = ""
1610     LET right = result
1620     LET position = pos(pair(0), right)
1630     DO WHILE position > 0
1640       LET left = left + copy(right, 1, position-1) + pair(1)
1650       LET right = copy(right, position + length(pair(0)), length(right))
1660       LET position = pos(pair(0), right)
1670     LOOP
1680     LET result = left + right
1690   NEXT pair
1700   REM Eliminate multiple spaces 
1710   LET position = pos("  ", result)
1720   DO WHILE position > 0
1730     LET result = copy(result, 1, position-1) + copy(result, position+1, length(result))
1740     LET position = pos("  ", result)
1750   LOOP
1760   RETURN result
1770 END FUNCTION
1780 REM  
1790 REM Looks for the occurrence of the first of the strings 
1800 REM contained in keywords within the given sentence (in 
1810 REM array order). 
1820 REM Returns an array of 
1830 REM 0: the index of the first identified keyword (if any, otherwise -1), 
1840 REM 1: the position inside sentence (0 if not found) 
1850 REM TODO: Add type-specific suffixes where necessary! 
1860 FUNCTION findKeyword(keyMap AS const array of KeyMapEntry, sentence AS String) AS array[0..1] of integer
1870   REM TODO: add the respective type suffixes to your variable names if required 
1880   REM Contains the index of the keyword and its position in sentence 
1890   REM TODO: Check indexBase value (automatically generated) 
1900   LET indexBase = 0
1910   LET result(indexBase + 0) = -1
1920   LET result(indexBase + 1) = 0
1930   LET i = 0
1940   DO WHILE (result(0) < 0) AND (i < length(keyMap))
1950     LET var entry: KeyMapEntry = keyMap(i)
1960     LET position = pos(entry.keyword, sentence)
1970     IF position > 0 THEN
1980       LET result(0) = i
1990       LET result(1) = position
2000     END IF
2010     LET i = i+1
2020   LOOP
2030   RETURN result
2040 END FUNCTION
2050 REM  
2060 REM Converts the sentence to lowercase, eliminates all 
2070 REM interpunction (i.e. ',', '.', ';'), and pads the 
2080 REM sentence among blanks 
2090 REM TODO: Add type-specific suffixes where necessary! 
2100 FUNCTION normalizeInput(sentence AS String) AS String
2110   REM TODO: add the respective type suffixes to your variable names if required 
2120   LET sentence = lowercase(sentence)
2130   REM TODO: Specify an appropriate element type for the array! 
2140   DIM array45d0a8fd() AS FIXME_45d0a8fd = {'.', ',', ';', '!', '?'}
2150   FOR EACH symbol IN array45d0a8fd
2160     LET position = pos(symbol, sentence)
2170     DO WHILE position > 0
2180       LET sentence = copy(sentence, 1, position-1) + copy(sentence, position+1, length(sentence))
2190       LET position = pos(symbol, sentence)
2200     LOOP
2210   NEXT symbol
2220   LET result = " " + sentence + " "
2230   RETURN result
2240 END FUNCTION
2250 REM  
2260 REM TODO: Add type-specific suffixes where necessary! 
2270 FUNCTION setupGoodByePhrases() AS array of array[0..1] of string
2280   REM TODO: add the respective type suffixes to your variable names if required 
2290   REM TODO: Check indexBase value (automatically generated) 
2300   LET indexBase = 0
2310   LET phrases(0)(indexBase + 0) = " shut"
2320   LET phrases(0)(indexBase + 1) = "Okay. If you feel that way I\'ll shut up. ... Your choice."
2330   REM TODO: Check indexBase value (automatically generated) 
2340   LET indexBase = 0
2350   LET phrases(1)(indexBase + 0) = "bye"
2360   LET phrases(1)(indexBase + 1) = "Well, let\'s end our talk for now. See you later. Bye."
2370   return phrases
2380 END FUNCTION
2390 REM  
2400 REM The lower the index the higher the rank of the keyword (search is sequential). 
2410 REM The index of the first keyword found in a user sentence maps to a respective 
2420 REM reply ring as defined in `setupReplies()´. 
2430 REM TODO: Add type-specific suffixes where necessary! 
2440 FUNCTION setupKeywords() AS array of KeyMapEntry
2450   REM TODO: add the respective type suffixes to your variable names if required 
2460   REM The empty key string (last entry) is the default clause - will always be found 
2470   LET keywords(39) = KeyMapEntry{"", 29}
2480   LET keywords(0) = KeyMapEntry{"can you ", 0}
2490   LET keywords(1) = KeyMapEntry{"can i ", 1}
2500   LET keywords(2) = KeyMapEntry{"you are ", 2}
2510   LET keywords(3) = KeyMapEntry{"you\'re ", 2}
2520   LET keywords(4) = KeyMapEntry{"i don't ", 3}
2530   LET keywords(5) = KeyMapEntry{"i feel ", 4}
2540   LET keywords(6) = KeyMapEntry{"why don\'t you ", 5}
2550   LET keywords(7) = KeyMapEntry{"why can\'t i ", 6}
2560   LET keywords(8) = KeyMapEntry{"are you ", 7}
2570   LET keywords(9) = KeyMapEntry{"i can\'t ", 8}
2580   LET keywords(10) = KeyMapEntry{"i am ", 9}
2590   LET keywords(11) = KeyMapEntry{"i\'m ", 9}
2600   LET keywords(12) = KeyMapEntry{"you ", 10}
2610   LET keywords(13) = KeyMapEntry{"i want ", 11}
2620   LET keywords(14) = KeyMapEntry{"what ", 12}
2630   LET keywords(15) = KeyMapEntry{"how ", 12}
2640   LET keywords(16) = KeyMapEntry{"who ", 12}
2650   LET keywords(17) = KeyMapEntry{"where ", 12}
2660   LET keywords(18) = KeyMapEntry{"when ", 12}
2670   LET keywords(19) = KeyMapEntry{"why ", 12}
2680   LET keywords(20) = KeyMapEntry{"name ", 13}
2690   LET keywords(21) = KeyMapEntry{"cause ", 14}
2700   LET keywords(22) = KeyMapEntry{"sorry ", 15}
2710   LET keywords(23) = KeyMapEntry{"dream ", 16}
2720   LET keywords(24) = KeyMapEntry{"hello ", 17}
2730   LET keywords(25) = KeyMapEntry{"hi ", 17}
2740   LET keywords(26) = KeyMapEntry{"maybe ", 18}
2750   LET keywords(27) = KeyMapEntry{" no", 19}
2760   LET keywords(28) = KeyMapEntry{"your ", 20}
2770   LET keywords(29) = KeyMapEntry{"always ", 21}
2780   LET keywords(30) = KeyMapEntry{"think ", 22}
2790   LET keywords(31) = KeyMapEntry{"alike ", 23}
2800   LET keywords(32) = KeyMapEntry{"yes ", 24}
2810   LET keywords(33) = KeyMapEntry{"friend ", 25}
2820   LET keywords(34) = KeyMapEntry{"computer", 26}
2830   LET keywords(35) = KeyMapEntry{"bot ", 26}
2840   LET keywords(36) = KeyMapEntry{"smartphone", 27}
2850   LET keywords(37) = KeyMapEntry{"father ", 28}
2860   LET keywords(38) = KeyMapEntry{"mother ", 28}
2870   return keywords
2880 END FUNCTION
2890 REM  
2900 REM Returns an array of pairs of mutualy substitutable  
2910 REM TODO: Add type-specific suffixes where necessary! 
2920 FUNCTION setupReflexions() AS array of array[0..1] of string
2930   REM TODO: add the respective type suffixes to your variable names if required 
2940   REM TODO: Check indexBase value (automatically generated) 
2950   LET indexBase = 0
2960   LET reflexions(0)(indexBase + 0) = " are "
2970   LET reflexions(0)(indexBase + 1) = " am "
2980   REM TODO: Check indexBase value (automatically generated) 
2990   LET indexBase = 0
3000   LET reflexions(1)(indexBase + 0) = " were "
3010   LET reflexions(1)(indexBase + 1) = " was "
3020   REM TODO: Check indexBase value (automatically generated) 
3030   LET indexBase = 0
3040   LET reflexions(2)(indexBase + 0) = " you "
3050   LET reflexions(2)(indexBase + 1) = " I "
3060   REM TODO: Check indexBase value (automatically generated) 
3070   LET indexBase = 0
3080   LET reflexions(3)(indexBase + 0) = " your"
3090   LET reflexions(3)(indexBase + 1) = " my"
3100   REM TODO: Check indexBase value (automatically generated) 
3110   LET indexBase = 0
3120   LET reflexions(4)(indexBase + 0) = " i\'ve "
3130   LET reflexions(4)(indexBase + 1) = " you\'ve "
3140   REM TODO: Check indexBase value (automatically generated) 
3150   LET indexBase = 0
3160   LET reflexions(5)(indexBase + 0) = " i\'m "
3170   LET reflexions(5)(indexBase + 1) = " you\'re "
3180   REM TODO: Check indexBase value (automatically generated) 
3190   LET indexBase = 0
3200   LET reflexions(6)(indexBase + 0) = " me "
3210   LET reflexions(6)(indexBase + 1) = " you "
3220   REM TODO: Check indexBase value (automatically generated) 
3230   LET indexBase = 0
3240   LET reflexions(7)(indexBase + 0) = " my "
3250   LET reflexions(7)(indexBase + 1) = " your "
3260   REM TODO: Check indexBase value (automatically generated) 
3270   LET indexBase = 0
3280   LET reflexions(8)(indexBase + 0) = " i "
3290   LET reflexions(8)(indexBase + 1) = " you "
3300   REM TODO: Check indexBase value (automatically generated) 
3310   LET indexBase = 0
3320   LET reflexions(9)(indexBase + 0) = " am "
3330   LET reflexions(9)(indexBase + 1) = " are "
3340   return reflexions
3350 END FUNCTION
3360 REM  
3370 REM This routine sets up the reply rings addressed by the key words defined in 
3380 REM routine `setupKeywords()´ and mapped hitherto by the cross table defined 
3390 REM in `setupMapping()´ 
3400 REM TODO: Add type-specific suffixes where necessary! 
3410 FUNCTION setupReplies() AS array of array of string
3420   REM TODO: add the respective type suffixes to your variable names if required 
3430   var replies: array of array of String
3440   REM We start with the highest index for performance reasons 
3450   REM (is to avoid frequent array resizing) 
3460   LET replies(29) = {\
3470   "Say, do you have any psychological problems?",\
3480   "What does that suggest to you?",\
3490   "I see.",\
3500   "I'm not sure I understand you fully.",\
3510   "Come come elucidate your thoughts.",\
3520   "Can you elaborate on that?",\
3530   "That is quite interesting."}
3540   LET replies(0) = {\
3550   "Don't you believe that I can*?",\
3560   "Perhaps you would like to be like me?",\
3570   "You want me to be able to*?"}
3580   LET replies(1) = {\
3590   "Perhaps you don't want to*?",\
3600   "Do you want to be able to*?"}
3610   LET replies(2) = {\
3620   "What makes you think I am*?",\
3630   "Does it please you to believe I am*?",\
3640   "Perhaps you would like to be*?",\
3650   "Do you sometimes wish you were*?"}
3660   LET replies(3) = {\
3670   "Don't you really*?",\
3680   "Why don't you*?",\
3690   "Do you wish to be able to*?",\
3700   "Does that trouble you*?"}
3710   LET replies(4) = {\
3720   "Do you often feel*?",\
3730   "Are you afraid of feeling*?",\
3740   "Do you enjoy feeling*?"}
3750   LET replies(5) = {\
3760   "Do you really believe I don't*?",\
3770   "Perhaps in good time I will*.",\
3780   "Do you want me to*?"}
3790   LET replies(6) = {\
3800   "Do you think you should be able to*?",\
3810   "Why can't you*?"}
3820   LET replies(7) = {\
3830   "Why are you interested in whether or not I am*?",\
3840   "Would you prefer if I were not*?",\
3850   "Perhaps in your fantasies I am*?"}
3860   LET replies(8) = {\
3870   "How do you know you can't*?",\
3880   "Have you tried?","Perhaps you can now*."}
3890   LET replies(9) = {\
3900   "Did you come to me because you are*?",\
3910   "How long have you been*?",\
3920   "Do you believe it is normal to be*?",\
3930   "Do you enjoy being*?"}
3940   LET replies(10) = {\
3950   "We were discussing you--not me.",\
3960   "Oh, I*.",\
3970   "You're not really talking about me, are you?"}
3980   LET replies(11) = {\
3990   "What would it mean to you if you got*?",\
4000   "Why do you want*?",\
4010   "Suppose you soon got*...",\
4020   "What if you never got*?",\
4030   "I sometimes also want*."}
4040   LET replies(12) = {\
4050   "Why do you ask?",\
4060   "Does that question interest you?",\
4070   "What answer would please you the most?",\
4080   "What do you think?",\
4090   "Are such questions on your mind often?",\
4100   "What is it that you really want to know?",\
4110   "Have you asked anyone else?",\
4120   "Have you asked such questions before?",\
4130   "What else comes to mind when you ask that?"}
4140   LET replies(13) = {\
4150   "Names don't interest me.",\
4160   "I don't care about names -- please go on."}
4170   LET replies(14) = {\
4180   "Is that the real reason?",\
4190   "Don't any other reasons come to mind?",\
4200   "Does that reason explain anything else?",\
4210   "What other reasons might there be?"}
4220   LET replies(15) = {\
4230   "Please don't apologize!",\
4240   "Apologies are not necessary.",\
4250   "What feelings do you have when you apologize?",\
4260   "Don't be so defensive!"}
4270   LET replies(16) = {\
4280   "What does that dream suggest to you?",\
4290   "Do you dream often?",\
4300   "What persons appear in your dreams?",\
4310   "Are you disturbed by your dreams?"}
4320   LET replies(17) = {\
4330   "How do you do ...please state your problem."}
4340   LET replies(18) = {\
4350   "You don't seem quite certain.",\
4360   "Why the uncertain tone?",\
4370   "Can't you be more positive?",\
4380   "You aren't sure?",\
4390   "Don't you know?"}
4400   LET replies(19) = {\
4410   "Are you saying no just to be negative?",\
4420   "You are being a bit negative.",\
4430   "Why not?",\
4440   "Are you sure?",\
4450   "Why no?"}
4460   LET replies(20) = {\
4470   "Why are you concerned about my*?",\
4480   "What about your own*?"}
4490   LET replies(21) = {\
4500   "Can you think of a specific example?",\
4510   "When?",\
4520   "What are you thinking of?",\
4530   "Really, always?"}
4540   LET replies(22) = {\
4550   "Do you really think so?",\
4560   "But you are not sure you*?",\
4570   "Do you doubt you*?"}
4580   LET replies(23) = {\
4590   "In what way?",\
4600   "What resemblance do you see?",\
4610   "What does the similarity suggest to you?",\
4620   "What other connections do you see?",\
4630   "Could there really be some connection?",\
4640   "How?",\
4650   "You seem quite positive."}
4660   LET replies(24) = {\
4670   "Are you sure?",\
4680   "I see.",\
4690   "I understand."}
4700   LET replies(25) = {\
4710   "Why do you bring up the topic of friends?",\
4720   "Do your friends worry you?",\
4730   "Do your friends pick on you?",\
4740   "Are you sure you have any friends?",\
4750   "Do you impose on your friends?",\
4760   "Perhaps your love for friends worries you."}
4770   LET replies(26) = {\
4780   "Do computers worry you?",\
4790   "Are you talking about me in particular?",\
4800   "Are you frightened by machines?",\
4810   "Why do you mention computers?",\
4820   "What do you think machines have to do with your problem?",\
4830   "Don't you think computers can help people?",\
4840   "What is it about machines that worries you?"}
4850   LET replies(27) = {\
4860   "Do you sometimes feel uneasy without a smartphone?",\
4870   "Have you had these phantasies before?",\
4880   "Does the world seem more real for you via apps?"}
4890   LET replies(28) = {\
4900   "Tell me more about your family.",\
4910   "Who else in your family*?",\
4920   "What does family relations mean for you?",\
4930   "Come on, How old are you?"}
4940   LET setupReplies = replies
4950   RETURN setupReplies
4960 END FUNCTION