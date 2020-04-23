10 REM NOTE: 
20 REM This first module of the file is a library module providing common resources 
30 REM for the following modules, which are separated by comment lines like 
40 REM "======= 8< =======...". 
50 REM You may have to cut this file apart at these lines in order to get the parts 
60 REM running, since the following modules may form sort of mutually independent 
70 REM applications or programs the coexistence of which in a single file might not 
80 REM be sensible. 
90 REM Generated by Structorizer 3.30-08 
100 
110 REM Copyright (C) 2020-04-23 kay 
120 
130 REM  
140 REM TODO: Add type-specific suffixes where necessary! 
150 SUB FileApiGroupTest_arrz()
160   REM TODO: add the respective type suffixes to your variable names if required 
170 END
180 
190 REM  
200 REM Draws a bar chart from the array "values" of size nValues. 
210 REM Turtleizer must be activated and will scale the chart into a square of 
220 REM 500 x 500 pixels 
230 REM Note: The function is not robust against empty array or totally equal values. 
240 
250 REM Copyright (C) 2020-03-22 Kay Gürtzig 
260 REM License: GPLv3-link 
270 
280 REM TODO: Add type-specific suffixes where necessary! 
290 SUB drawBarChart(values AS array of double, nValues)
300   REM TODO: add the respective type suffixes to your variable names if required 
310   REM Used range of the Turtleizer screen 
320   LET xSize = 500
330   LET ySize = 500
340   LET kMin = 0
350   LET kMax = 0
360   FOR k = 1 TO nValues-1
370     IF values(k) > values(kMax) THEN
380       LET kMax = k
390     ELSE
400       IF values(k) < values(kMin) THEN
410         LET kMin = k
420       END IF
430     END IF
440   NEXT k
450   LET valMin = values(kMin)
460   LET valMax = values(kMax)
470   LET yScale = valMax * 1.0 / (ySize - 1)
480   LET yAxis = ySize - 1
490   IF valMin < 0 THEN
500     IF valMax > 0 THEN
510       LET yAxis = valMax * ySize * 1.0 / (valMax - valMin)
520       LET yScale = (valMax - valMin) * 1.0 / (ySize - 1)
530     ELSE
540       LET yAxis = 1
550       LET yScale = valMin * 1.0 / (ySize - 1)
560     END IF
570   END IF
580   REM draw coordinate axes 
590   gotoXY(1, ySize - 1)
600   forward(ySize -1) : REM color = ffffff
610   penUp()
620   backward(yAxis) : REM color = ffffff
630   right(90)
640   penDown()
650   forward(xSize -1) : REM color = ffffff
660   penUp()
670   backward(xSize-1) : REM color = ffffff
680   LET stripeWidth = xSize / nValues
690   FOR k = 0 TO nValues-1
700     LET stripeHeight = values(k) * 1.0 / yScale
710     SELECT CASE k % 3
720       CASE 0
730         setPenColor(255,0,0)
740       CASE 1
750         setPenColor(0, 255,0)
760       CASE 2
770         setPenColor(0, 0, 255)
780     END SELECT
790     fd(1) : REM color = ffffff
800     left(90)
810     penDown()
820     fd(stripeHeight) : REM color = ffffff
830     right(90)
840     fd(stripeWidth - 1) : REM color = ffffff
850     right(90)
860     forward(stripeHeight) : REM color = ffffff
870     left(90)
880     penUp()
890   NEXT k
900 END SUB
910 
920 REM  
930 REM Writes a text file with name fileName, consisting of count lines, each containing 
940 REM a random number in the range from minVal to maxVal (both inclusive). 
950 REM Returns either the number of written number if all went well or some potential 
960 REM error code if something went wrong. 
970 
980 REM Copyright (C) 2020-03-21 Kay Gürtzig 
990 REM License: GPLv3-link 
1000 
1010 REM TODO: Add type-specific suffixes where necessary! 
1020 FUNCTION generateRandomNumberFile(fileName AS String, count AS Integer, minVal AS integer, maxVal AS integer) AS integer
1030   REM TODO: add the respective type suffixes to your variable names if required 
1040   LET fileNo = fileCreate(fileName)
1050   IF fileNo <= 0 THEN
1060     RETURN fileNo
1070   END IF
1080   ON ERROR GOTO 1150
1090   FOR k = 1 TO count
1100     LET number = minVal + random(maxVal - minVal + 1)
1110     fileWriteLine(fileNo, number)
1120   NEXT k
1130   GOTO 1180
1140   REM Start of error handler, FIXME: variable 'error' should conatain error info ... 
1150     PRINT error
1160     RETURN -7
1170   REM End of error handler, resume here ... 
1180   ON ERROR GOTO 0
1190   fileClose(fileNo)
1200   RETURN count
1210 END FUNCTION
1220 
1230 REM  
1240 REM Tries to read as many integer values as possible upto maxNumbers 
1250 REM from file fileName into the given array numbers. 
1260 REM Returns the number of the actually read numbers. May cause an exception. 
1270 
1280 REM Copyright (C) 2020-03-21 Kay Gürtzig 
1290 REM License: GPLv3-link 
1300 
1310 REM TODO: Add type-specific suffixes where necessary! 
1320 FUNCTION readNumbers(fileName AS String, numbers AS array of integer, maxNumbers AS integer) AS integer
1330   REM TODO: add the respective type suffixes to your variable names if required 
1340   LET nNumbers = 0
1350   LET fileNo = fileOpen(fileName)
1360   IF fileNo <= 0 THEN
1370     REM FIXME: Only a number is allowed as parameter: 
1380     ERROR "File could not be opened!"
1390   END IF
1400   ON ERROR GOTO 1480
1410   DO WHILE NOT fileEOF(fileNo) AND nNumbers < maxNumbers
1420     LET number = fileReadInt(fileNo)
1430     LET numbers(nNumbers) = number
1440     LET nNumbers = nNumbers + 1
1450   LOOP
1460   GOTO 1510
1470   REM Start of error handler, FIXME: variable 'error' should conatain error info ... 
1480     REM FIXME: Only a number is allowed as parameter: 
1490     ERROR 
1500   REM End of error handler, resume here ... 
1510   ON ERROR GOTO 0
1520   fileClose(fileNo)
1530   RETURN nNumbers
1540 END FUNCTION
1550 
1560 REM ======= 8< =========================================================== 
1570 
1580 REM Computes the sum and average of the numbers read from a user-specified 
1590 REM text file (which might have been created via generateRandomNumberFile(4)). 
1600 REM  
1610 REM This program is part of an arrangement used to test group code export (issue 
1620 REM #828) with FileAPI dependency. 
1630 REM The input check loop has been disabled (replaced by a simple unchecked input 
1640 REM instruction) in order to test the effect of indirect FileAPI dependency (only the 
1650 REM called subroutine directly requires FileAPI now). 
1660 REM Generated by Structorizer 3.30-08 
1670 
1680 REM Copyright (C) 2020-03-21 Kay Gürtzig 
1690 REM License: GPLv3-link 
1700 REM GNU General Public License (V 3) 
1710 REM https://www.gnu.org/licenses/gpl.html 
1720 REM http://www.gnu.de/documents/gpl.de.html 
1730 
1740 REM  
1750 REM program ComputeSum
1760 REM TODO: add the respective type suffixes to your variable names if required 
1770 LET fileNo = 1000
1780 REM Disable this if you enable the loop below! 
1790 PRINT "Name/path of the number file"; : INPUT file_name
1800 REM If you enable this loop, then the preceding input instruction is to be disabled 
1810 REM and the fileClose instruction in the alternative below is to be enabled. 
1820 REM DO 
1830 REM   PRINT "Name/path of the number file"; : INPUT file_name 
1840 REM   LET fileNo = fileOpen(file_name) 
1850 REM LOOP UNTIL fileNo > 0 OR file_name = "" 
1860 IF fileNo > 0 THEN
1870   REM This should be enabled if the input check loop above gets enabled. 
1880 REM   fileClose(fileNo) 
1890   REM TODO: Check indexBase value (automatically generated) 
1900   LET indexBase = 0
1910   LET nValues = 0
1920   ON ERROR GOTO 1960
1930   LET nValues = readNumbers(file_name, values, 1000)
1940   GOTO 1990
1950   REM Start of error handler, FIXME: variable 'failure' should conatain error info ... 
1960     PRINT failure
1970     STOP
1980   REM End of error handler, resume here ... 
1990   ON ERROR GOTO 0
2000   LET sum = 0.0
2010   FOR k = 0 TO nValues-1
2020     LET sum = sum + values(k)
2030   NEXT k
2040   PRINT "sum = "; sum
2050   PRINT "average = "; sum / nValues
2060 END IF
2070 END
2080 
2090 REM ======= 8< =========================================================== 
2100 
2110 REM Reads a random number file and draws a histogram accotrding to the 
2120 REM user specifications 
2130 REM Generated by Structorizer 3.30-08 
2140 
2150 REM Copyright (C) 2020-03-21 Kay Gürtzig 
2160 REM License: GPLv3-link 
2170 REM GNU General Public License (V 3) 
2180 REM https://www.gnu.org/licenses/gpl.html 
2190 REM http://www.gnu.de/documents/gpl.de.html 
2200 
2210 REM  
2220 REM program DrawRandomHistogram
2230 REM TODO: add the respective type suffixes to your variable names if required 
2240 LET fileNo = -10
2250 DO
2260   PRINT "Name/path of the number file"; : INPUT file_name
2270   LET fileNo = fileOpen(file_name)
2280 LOOP UNTIL fileNo > 0 OR file_name = ""
2290 IF fileNo > 0 THEN
2300   fileClose(fileNo)
2310   PRINT "number of intervals"; : INPUT nIntervals
2320   REM Initialize the interval counters 
2330   FOR k = 0 TO nIntervals-1
2340     LET count(k) = 0
2350   NEXT k
2360   REM Index of the most populated interval 
2370   LET kMaxCount = 0
2380   REM TODO: Check indexBase value (automatically generated) 
2390   LET indexBase = 0
2400   LET nObtained = 0
2410   ON ERROR GOTO 2450
2420   LET nObtained = readNumbers(file_name, numberArray, 10000)
2430   GOTO 2470
2440   REM Start of error handler, FIXME: variable 'failure' should conatain error info ... 
2450     PRINT failure
2460   REM End of error handler, resume here ... 
2470   ON ERROR GOTO 0
2480   IF nObtained > 0 THEN
2490     LET min = numberArray(0)
2500     LET max = numberArray(0)
2510     FOR i = 1 TO nObtained-1
2520       IF numberArray(i) < min THEN
2530         LET min = numberArray(i)
2540       ELSE
2550         IF numberArray(i) > max THEN
2560           LET max = numberArray(i)
2570         END IF
2580       END IF
2590     NEXT i
2600     REM Interval width 
2610     LET width = (max - min) * 1.0 / nIntervals
2620     FOR i = 0 TO nObtained - 1
2630       LET value = numberArray(i)
2640       LET k = 1
2650       DO WHILE k < nIntervals AND value > min + k * width
2660         LET k = k + 1
2670       LOOP
2680       LET count(k-1) = count(k-1) + 1
2690       IF count(k-1) > count(kMaxCount) THEN
2700         LET kMaxCount = k-1
2710       END IF
2720     NEXT i
2730     CALL drawBarChart(count, nIntervals)
2740     PRINT "Interval with max count: "; kMaxCount; " ("; count(kMaxCount); ")"
2750     FOR k = 0 TO nIntervals-1
2760       PRINT count(k); " numbers in interval "; k; " ("; min + k * width; " ... "; min + (k+1) * width; ")"
2770     NEXT k
2780   ELSE
2790     PRINT "No numbers read."
2800   END IF
2810 END IF
2820 END
