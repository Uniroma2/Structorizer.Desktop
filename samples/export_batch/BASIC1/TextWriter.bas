Rem Demo program for routine drawText() 
Rem Asks the user to enter a text, a wanted text height and colour, 
Rem and then draws this string onto the turtle screen. Places every 
Rem entered text to a new line. 
Rem Generated by Structorizer 3.30-11 

Rem Copyright (C) 2019-10-10 Kay Gürtzig 
Rem License: GPLv3-link 
Rem GNU General Public License (V 3) 
Rem https://www.gnu.org/licenses/gpl.html 
Rem http://www.gnu.de/documents/gpl.de.html 

Rem  
Rem program TextDemo
Rem TODO: Check and accomplish your variable declarations here: 
Dim y As Integer
Dim text As ???
Dim height As ???
Dim colour As ???
Rem  
PRINT "This is a demo program for text writing with Turleizer."
showTurtle()
penDown()
y = 0
Do
  PRINT "Enter some text (empty string to exit)"; : INPUT text
  Rem Make sure the content is interpreted as string 
  text = "" + text
  If text <> "" Then
    Do
      PRINT "Height of the text (pixels)"; : INPUT height
    Loop Until height >= 5
    Do
      PRINT "Colour (1=black, 2=red, 3=yellow, 4=green, 5=cyan, 6=blue, 7=pink, 8=gray, 9=orange, 10=violet)"; : INPUT colour
    Loop Until colour >= 1 AND colour <= 10
    y = y + height + 2
    gotoXY(0, y - 2)
    Call drawText(text, height, colour)
  End If
Loop Until text = ""
gotoXY(0, y + 15)
Call drawText("Thank you, bye.", 10, 4)
hideTurtle()
End
Rem  
Rem Draws a blank for font height h, ignoring the colorNo 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub blank(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Rem  
  width = h/2.0
  penUp()
  right(90)
  forward(width) : Rem color = ffffff
  left(90)
End Sub
Rem  
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub forward(len, color)
  Rem TODO: Check and accomplish your variable declarations here: 
  Rem  
  Select Case color
    Case 1
      forward(len) : Rem color = ffffff
    Case 2
      forward(len) : Rem color = ff8080
    Case 3
      forward(len) : Rem color = ffff80
    Case 4
      forward(len) : Rem color = 80ff80
    Case 5
      forward(len) : Rem color = 80ffff
    Case 6
      forward(len) : Rem color = 0080ff
    Case 7
      forward(len) : Rem color = ff80c0
    Case 8
      forward(len) : Rem color = c0c0c0
    Case 9
      forward(len) : Rem color = ff8000
    Case 10
      forward(len) : Rem color = 8080ff
  End Select
End Sub
Rem  
Rem Draws letter A in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterA(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim rotAngle As double
  Dim hypo As double
  Rem  
  width = h/2.0
  hypo = sqrt(h*h + width*width/4.0)
  rotAngle = toDegrees(atan(width/2.0/h))
  right(rotAngle)
  Call forward(hypo/2.0, colorNo)
  right(90 - rotAngle)
  Call forward(width/2.0, colorNo)
  penUp()
  backward(width/2.0) : Rem color = ffffff
  penDown()
  left(90 - rotAngle)
  Call forward(hypo/2.0, colorNo)
  left(2*rotAngle)
  Call forward(-hypo, colorNo)
  right(rotAngle)
End Sub
Rem  
Rem Draws letter E in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterE(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Rem  
  width = h/2.0
  Call forward(h, colorNo)
  right(90)
  Call forward(width, colorNo)
  right(90)
  penUp()
  forward(h/2.0) : Rem color = ffffff
  right(90)
  penDown()
  Call forward(width, colorNo)
  left(90)
  penUp()
  forward(h/2.0) : Rem color = ffffff
  left(90)
  penDown()
  Call forward(width, colorNo)
  left(90)
End Sub
Rem  
Rem Draws letter F in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterF(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Rem  
  width = h/2.0
  Call forward(h, colorNo)
  right(90)
  Call forward(width, colorNo)
  right(90)
  penUp()
  forward(h/2.0) : Rem color = ffffff
  right(90)
  penDown()
  Call forward(width, colorNo)
  left(90)
  penUp()
  forward(h/2.0) : Rem color = ffffff
  left(90)
  forward(width) : Rem color = ffffff
  penDown()
  left(90)
End Sub
Rem  
Rem Draws letter H in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterH(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Rem  
  width = h/2.0
  Call forward(h, colorNo)
  penUp()
  right(90)
  forward(width) : Rem color = ffffff
  right(90)
  penDown()
  Call forward(h/2.0, colorNo)
  right(90)
  Call forward(width, colorNo)
  penUp()
  backward(width) : Rem color = ffffff
  left(90)
  penDown()
  Call forward(h/2.0, colorNo)
  left(180)
End Sub
Rem  
Rem Draws letter I in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterI(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the corner triangle outside the octagon 
  c = b / sqrt(2.0)
  penUp()
  right(90)
  forward(c) : Rem color = ffffff
  penDown()
  Call forward(b, colorNo)
  penUp()
  backward(b/2.0) : Rem color = ffffff
  left(90)
  penDown()
  Call forward(h, colorNo)
  penUp()
  right(90)
  backward(b/2.0) : Rem color = ffffff
  penDown()
  Call forward(b, colorNo)
  penUp()
  forward(b/2 + c) : Rem color = ffffff
  left(90)
  backward(h) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws letter K in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterK(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim diag As ???
  Rem  
  width = h/2.0
  diag = h/sqrt(2.0)
  Call forward(h, colorNo)
  penUp()
  right(90)
  forward(width) : Rem color = ffffff
  right(135)
  penDown()
  Call forward(diag, colorNo)
  left(90)
  Call forward(diag, colorNo)
  left(135)
End Sub
Rem  
Rem Draws letter L in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterL(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Rem  
  width = h/2.0
  Call forward(h, colorNo)
  penUp()
  backward(h) : Rem color = ffffff
  right(90)
  penDown()
  Call forward(width, colorNo)
  left(90)
End Sub
Rem  
Rem Draws letter M in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterM(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim rotAngle As double
  Dim hypo As ???
  Rem  
  width = h/2.0
  hypo = sqrt(width*width + h*h)/2.0
  rotAngle = toDegrees(atan(width/h))
  Call forward(h, colorNo)
  left(rotAngle)
  Call forward(-hypo, colorNo)
  right(2*rotAngle)
  Call forward(hypo, colorNo)
  left(rotAngle)
  Call forward(-h, colorNo)
End Sub
Rem  
Rem Draws letter N in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterN(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim rotAngle As double
  Dim hypo As double
  Rem  
  width = h/2.0
  hypo = sqrt(width*width + h*h)
  rotAngle = toDegrees(atan(width/h))
  Call forward(h, colorNo)
  left(rotAngle)
  Call forward(-hypo, colorNo)
  right(rotAngle)
  Call forward(h, colorNo)
  penUp()
  backward(h) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws letter T in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterT(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Rem  
  width = h/2.0
  penUp()
  forward(h) : Rem color = ffffff
  penDown()
  right(90)
  Call forward(width, colorNo)
  penUp()
  backward(width/2.0) : Rem color = ffffff
  penDown()
  right(90)
  Call forward(h, colorNo)
  left(90)
  penUp()
  forward(width/2.0) : Rem color = ffffff
  penDown()
  left(90)
End Sub
Rem  
Rem Draws letter V in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterV(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim rotAngle As double
  Dim hypo As double
  Rem  
  width = h/2.0
  hypo = sqrt(h*h + width*width/4.0)
  rotAngle = toDegrees(atan(width/2.0/h))
  penUp()
  forward(h) : Rem color = ffffff
  left(rotAngle)
  penDown()
  Call forward(-hypo, colorNo)
  right(2*rotAngle)
  Call forward(hypo, colorNo)
  penUp()
  left(rotAngle)
  backward(h) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws letter W in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterW(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width_3 As ???
  Dim width As ???
  Dim rotAngle As double
  Dim hypo As double
  Rem  
  width = h/2.0
  width_3 = width/3.0
  hypo = sqrt(width_3*width_3 + h*h)
  rotAngle = toDegrees(atan(width_3/h))
  penUp()
  forward(h) : Rem color = ffffff
  left(rotAngle)
  penDown()
  Call forward(-hypo, colorNo)
  right(2*rotAngle)
  Call forward(hypo, colorNo)
  penUp()
  left(90+rotAngle)
  forward(width_3) : Rem color = ffffff
  right(90-rotAngle)
  penDown()
  Call forward(-hypo, colorNo)
  right(2*rotAngle)
  Call forward(hypo, colorNo)
  penUp()
  left(rotAngle)
  backward(h) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws letter X in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterX(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim rotAngle As double
  Dim hypo As double
  Rem  
  width = h/2.0
  hypo = sqrt(width*width + h*h)
  rotAngle = toDegrees(atan(width/h))
  right(rotAngle)
  Call forward(hypo, colorNo)
  penUp()
  left(90+rotAngle)
  forward(width) : Rem color = ffffff
  right(90-rotAngle)
  penDown()
  Call forward(-hypo, colorNo)
  right(rotAngle)
End Sub
Rem  
Rem Draws letter Y in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterY(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim rotAngle As double
  Dim hypo As ???
  Rem  
  width = h/2.0
  hypo = sqrt(width*width + h*h)/2.0
  rotAngle = toDegrees(atan(width/h))
  penUp()
  forward(h) : Rem color = ffffff
  left(rotAngle)
  penDown()
  Call forward(-hypo, colorNo)
  right(rotAngle)
  penUp()
  backward(h/2.0) : Rem color = ffffff
  penDown()
  Call forward(h/2.0, colorNo)
  right(rotAngle)
  Call forward(hypo, colorNo)
  left(rotAngle)
  penUp()
  backward(h) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws letter Z in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterZ(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim rotAngle As double
  Dim hypo As double
  Rem  
  width = h/2.0
  hypo = sqrt(width*width + h*h)
  rotAngle = toDegrees(atan(width/h))
  penUp()
  forward(h) : Rem color = ffffff
  right(90)
  penDown()
  Call forward(width, colorNo)
  left(90-rotAngle)
  Call forward(-hypo, colorNo)
  right(90-rotAngle)
  Call forward(width, colorNo)
  left(90)
End Sub
Rem  
Rem Draws nEdges edges of a regular n-polygon with edge length a 
Rem counter-clockwise, if ctrclkws is true, or clockwise if ctrclkws is false. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub polygonPart(a AS double, n AS integer, ctrclkws AS boolean, nEdges AS integer, color AS Integer)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As ???
  Dim k As Integer
  Rem  
  rotAngle = 360.0/n
  If ctrclkws Then
    rotAngle = -rotAngle
  End If
  For k = 1 To nEdges
    right(rotAngle)
    Call forward(a, color)
  Next k
End Sub
Rem  
Rem Draws a dummy character (small centered square) with font height h and 
Rem the colour encoded by colorNo 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub charDummy(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim d As ???
  Dim c As ???
  Dim b As ???
  Rem  
  width = h / 2.0
  Rem Octagon edge length (here: edge lengzh of the square) 
  b = width / (sqrt(2.0) + 1)
  Rem Cathetus of the corner triangle outside the octagon 
  c = (width - b) / 2.0
  d = b / sqrt(2.0)
  penUp()
  forward(h/2.0-b/2.0) : Rem color = ffffff
  right(90)
  forward(c) : Rem color = ffffff
  right(90)
  penDown()
  Rem Draws the square with edge length b 
  Call polygonPart(b, 4, true, 4, colorNo)
  penUp()
  left(90)
  forward(b + c) : Rem color = ffffff
  left(90)
  backward(h/2.0-b/2.0) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws a comma in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub comma(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As double
  Dim hypo As ???
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  rotAngle = toDegrees(atan(0.5))
  hypo = c * sqrt(1.25)
  penUp()
  right(90)
  forward((c+b)/2.0 + c) : Rem color = ffffff
  penDown()
  Rem Counterclockwise draw 3 edges of a square with edge length c 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(c, 4, true, 3, colorNo)
  left(90)
  Call forward(c/2.0, colorNo)
  right(90)
  Call forward(c, colorNo)
  left(180 - rotAngle)
  Call forward(hypo, colorNo)
  penUp()
  right(90 - rotAngle)
  forward((c + b)/2.0) : Rem color = ffffff
  left(90)
  penDown()
End Sub
Rem  
Rem Draws an exclamation mark in the colour encoded by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub exclMk(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim width As ???
  Dim rotAngle2 As double
  Dim rotAngle As Integer
  Dim length2 As ???
  Dim length1 As ???
  Dim hypo As double
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  width = h/2.0
  length1 = h - (b+c)/2.0
  length2 = length1 - 2*c
  hypo = sqrt(width*width/16.0 + length2*length2)
  Rem 360°/8 
  rotAngle = 45
  rotAngle2 = toDegrees(atan(width/4.0/length2))
  penUp()
  forward(length1) : Rem color = ffffff
  right(90)
  forward(width/2.0) : Rem color = ffffff
  left(90 + rotAngle)
  penDown()
  Rem Clockwise draw 5 edges of an octagon with edge length b/2 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(b/2.0, 8, false, 5, colorNo)
  right(rotAngle2)
  Call forward(hypo, colorNo)
  left(2*rotAngle2)
  Call forward(-hypo, colorNo)
  penUp()
  forward(hypo) : Rem color = ffffff
  right(rotAngle2)
  forward(c) : Rem color = ffffff
  left(90)
  forward(c/2.0) : Rem color = ffffff
  penDown()
  Rem Counterclockwise draw all 4 edges of a squarfe with edge length c 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(c, 4, false, 4, colorNo)
  penUp()
  forward((c + b)/2.0) : Rem color = ffffff
  left(90)
  backward(c) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws a full stop in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub fullSt(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  penUp()
  right(90)
  forward((c+b)/2.0 + c) : Rem color = ffffff
  penDown()
  Rem Counterclockwise draw all 4 edges of a squarfe with edge length c 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(c, 4, true, 4, colorNo)
  penUp()
  forward((c + b)/2.0) : Rem color = ffffff
  left(90)
  penDown()
End Sub
Rem  
Rem Draws letter B in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterB(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  Call forward(h, colorNo)
  right(90)
  Call forward(c+b, colorNo)
  Rem Clockwise draw 4 edges of an octagon with edge length b 
  Call polygonPart(b, 8, false, 4, colorNo)
  Call forward(c, colorNo)
  penUp()
  left(180)
  forward(b + c) : Rem color = ffffff
  penDown()
  Rem Clockwise draw 4 edges of an octagon with edge length b 
  Call polygonPart(b, 8, false, 4, colorNo)
  Call forward(c, colorNo)
  penUp()
  left(180)
  forward(b + 2*c) : Rem color = ffffff
  penDown()
  left(90)
End Sub
Rem  
Rem Draws letter C in the colour encoded by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterC(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As Integer
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer trinagle at the octagon corner 
  c = b / sqrt(2.0)
  Rem 360°/8 
  rotAngle = 45
  penUp()
  forward(c) : Rem color = ffffff
  penDown()
  right(180)
  Rem Clockwise draws 3 edges of an octagon with edge length b in the colour 
  Rem encoded by colorNo 
  Call polygonPart(b, 8, true, 3, colorNo)
  left(rotAngle)
  penUp()
  forward(2*b + 2*c) : Rem color = ffffff
  penDown()
  Rem Counterclockwise draws 4 edges of an octagon with edge length b 
  Rem iin the colour encoded by colorNo 
  Call polygonPart(b, 8, true, 4, colorNo)
  Call forward(b + 2*c, colorNo)
  penUp()
  forward(c) : Rem color = ffffff
  left(90)
  Call forward(b + 2*c, colorNo)
  penDown()
  left(90)
End Sub
Rem  
Rem Draws letter D in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterD(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  Call forward(h, colorNo)
  right(90)
  Call forward(c+b, colorNo)
  Rem Clockwise draw 2 edges of an octagon with edge length b in the colour 
  Rem encoded by colorNo 
  Call polygonPart(b, 8, false, 2, colorNo)
  Call forward(b + 2*c, colorNo)
  Rem Clockwise draw 2 edges of an octagon with edge length b in the colour 
  Rem encoded by colorNo 
  Call polygonPart(b, 8, false, 2, colorNo)
  Call forward(c, colorNo)
  penUp()
  left(180)
  forward(b + 2*c) : Rem color = ffffff
  penDown()
  left(90)
End Sub
Rem  
Rem Draws letter G in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterG(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the corner triangle outside the octagon. 
  c = b / sqrt(2.0)
  penUp()
  forward(c) : Rem color = ffffff
  penDown()
  right(180)
  Rem Counterclockwise draw 4 edges of an octagon with edge length b in 
  Rem the colour encoded by colorNo 
  Call polygonPart(b, 8, true, 4, colorNo)
  Call forward(c, colorNo)
  left(90)
  Call forward(b/2.0 + c, colorNo)
  penUp()
  backward(b/2.0 + c) : Rem color = ffffff
  right(90)
  forward(b + c) : Rem color = ffffff
  penDown()
  Rem Counterclockwise draw 4 edges of an octagon with edge length b in 
  Rem the colour encoded by colorNo 
  Call polygonPart(b, 8, true, 4, colorNo)
  Call forward(b + 2*c, colorNo)
  penUp()
  forward(c) : Rem color = ffffff
  left(90)
  Call forward(b + 2*c, colorNo)
  penDown()
  left(90)
End Sub
Rem  
Rem Draws letter J in colour encoded by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterJ(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As Integer
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  Rem 360°/8 
  rotAngle = 45
  penUp()
  forward(c) : Rem color = ffffff
  penDown()
  right(180)
  Rem Counterclockwise draw 3 edges of an octagon with edge length b in 
  Rem the colour encoded by colorNo 
  Call polygonPart(b, 8, true, 3, colorNo)
  left(rotAngle)
  Call forward(h - c, colorNo)
  penUp()
  backward(h) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws letter O in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterO(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the corner triangle outside the octagon 
  c = b / sqrt(2.0)
  penUp()
  forward(c) : Rem color = ffffff
  penDown()
  right(180)
  Rem Counterclockwise draw 4 edges of an octagon with edge length b 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(b, 8, true, 4, colorNo)
  Call forward(b + 2*c, colorNo)
  Rem Counterclockwise draw 4 edges of an octagon with edge length b 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(b, 8, true, 4, colorNo)
  Call forward(b + 2*c, colorNo)
  penUp()
  forward(c) : Rem color = ffffff
  left(90)
  forward(b + 2*c) : Rem color = ffffff
  penDown()
  left(90)
End Sub
Rem  
Rem Draws letter P in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterP(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the corner triangle outside the octagon 
  c = b / sqrt(2.0)
  Call forward(h, colorNo)
  right(90)
  Call forward(c+b, colorNo)
  Rem Clockwise draw 4 edges of an octagon with edge length b 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(b, 8, false, 4, colorNo)
  Call forward(c, colorNo)
  penUp()
  backward(b + 2*c) : Rem color = ffffff
  left(90)
  forward(b + 2*c) : Rem color = ffffff
  penDown()
  left(180)
End Sub
Rem  
Rem Draws letter Q in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterQ(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As Integer
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  Rem 360°/8 
  rotAngle = 45
  penUp()
  forward(c) : Rem color = ffffff
  penDown()
  right(180)
  Rem Counterclockwise draw 4 edges of an octagon with edge length b 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(b, 8, true, 4, colorNo)
  Call forward(b + 2*c, colorNo)
  Rem Counterclockwise draw 4 edges of an octagon with edge length b 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(b, 8, true, 4, colorNo)
  Call forward(b + 2*c, colorNo)
  penUp()
  forward(c) : Rem color = ffffff
  left(90)
  forward(b + 2*c) : Rem color = ffffff
  right(rotAngle)
  backward(b) : Rem color = ffffff
  penDown()
  Call forward(b, colorNo)
  left(90 + rotAngle)
End Sub
Rem  
Rem Zeichnet den Buchstaben R von der Turtleposition aus 
Rem mit Zeilenhöhe h 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterR(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As Integer
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  Rem 360°/8 
  rotAngle = 45
  Call forward(h, colorNo)
  right(90)
  Call forward(c+b, colorNo)
  Rem Clockwise draw 4 edges of an octagon with edge length b 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(b, 8, false, 4, colorNo)
  Call forward(c, colorNo)
  left(90 + rotAngle)
  Call forward(sqrt(2.0)*(b + 2*c), colorNo)
  left(90 + rotAngle)
End Sub
Rem  
Rem Draws letter S in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterS(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As Integer
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Side length of the (outer) corner triangle of the octagon 
  c = b / sqrt(2.0)
  Rem 360°/8 
  rotAngle = 45
  penUp()
  forward(c) : Rem color = ffffff
  penDown()
  right(180)
  Rem Counterclockwise draw 6 edges of an octagon with edge length b 
  Rem in the colour encoded by colorNo 
  Call polygonPart(b, 8, true, 6, colorNo)
  Rem Clockwise draw 5 edges of an octagon with edge length b 
  Rem in the colour encoded by colorNo 
  Call polygonPart(b, 8, false, 5, colorNo)
  right(rotAngle)
  penUp()
  forward(2*b + 3*c) : Rem color = ffffff
  penDown()
  left(180)
End Sub
Rem  
Rem Draws letter U in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub letterU(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As Integer
  Dim c As ???
  Dim b As ???
  Rem  
  Rem edge length of a regular octagon 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  Rem 360°/8 
  rotAngle = 45
  penUp()
  forward(c) : Rem color = ffffff
  penDown()
  Call forward(h - c, colorNo)
  penUp()
  backward(h-c) : Rem color = ffffff
  penDown()
  right(180)
  Rem Counterclockwise draw 3 edges of an octagoin with edge length b in colour specified by colorNo 
  Call polygonPart(b, 8, true, 3, colorNo)
  left(rotAngle)
  Call forward(h - c, colorNo)
  penUp()
  backward(h) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Draws a question mark in colour specified by colorNo with font height h 
Rem from the current turtle position. 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub qstnMk(h, colorNo)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim rotAngle As Integer
  Dim c As ???
  Dim b As ???
  Rem  
  Rem Octagon edge length 
  b = h * 0.5 / (sqrt(2.0) + 1)
  Rem Cathetus of the outer corner triangle of the octagon 
  c = b / sqrt(2.0)
  Rem 360°/8 
  rotAngle = 45
  penUp()
  forward(h-c) : Rem color = ffffff
  penDown()
  Rem Counterclockwise draw 5 edges of an octagon with edge length b 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(b, 8, false, 5, colorNo)
  Call forward(c, colorNo)
  left(rotAngle)
  Call forward(b/2.0, colorNo)
  penUp()
  forward(c) : Rem color = ffffff
  left(90)
  forward(c/2.0) : Rem color = ffffff
  penDown()
  Rem Counterclockwise draw all 4 edges of a squarfe with edge length c 
  Rem in the colour endcoded by colorNo 
  Call polygonPart(c, 4, false, 4, colorNo)
  penUp()
  forward((c + b)/2.0) : Rem color = ffffff
  left(90)
  backward(c) : Rem color = ffffff
  penDown()
End Sub
Rem  
Rem Has the turtle draw the given string 'text´ with font height 'h´ (in 
Rem pixels) and the colour coded by integer 'c´ from the current Turtle 
Rem position to the Turtle canvas. If the turtle looks North then 
Rem the text will be written rightwards. In the event, the turtle will be 
Rem placed behind the text in original orientation (such that the next text 
Rem would be written like a continuation. Colour codes: 
Rem 1 = black 
Rem 2 = red 
Rem 3 = yellow 
Rem 4 = green 
Rem 5 = cyan 
Rem 6 = blue 
Rem 7 = pink 
Rem 8 = grey 
Rem 9 = orange 
Rem 10 = violet 
Rem All letters (ASCII) will be converted to uppercase, digits cannot 
Rem be represented, the set of representable special characters is: 
Rem '.', ',', '!', '?'. Other characters will be shown as a small 
Rem centred square (dummy character). 
Rem TODO: Check (and specify if needed) the argument and result types! 
Sub drawText(text AS String, h AS integer, c AS integer)
  Rem TODO: Check and accomplish your variable declarations here: 
  Dim letter As String
  Dim k As Integer
  Dim gap As ???
  Rem  
  gap = h/10.0
  For k = 1 To length(text)
    letter = uppercase(copy(text, k, 1))
    If letter = "," Then
      Call comma(h,c)
    Else
      Rem "," cannot be chacked against because the comma is misinterpreted 
      Rem as selector list separator. 
      Select Case letter
        Case "A"
          Call letterA(h,c)
        Case "B"
          Call letterB(h,c)
        Case "C"
          Call letterC(h,c)
        Case "D"
          Call letterD(h,c)
        Case "E"
          Call letterE(h,c)
        Case "F"
          Call letterF(h,c)
        Case "G"
          Call letterG(h,c)
        Case "H"
          Call letterH(h,c)
        Case "I"
          Call letterI(h,c)
        Case "J"
          Call letterJ(h,c)
        Case "K"
          Call letterK(h,c)
        Case "L"
          Call letterL(h,c)
        Case "M"
          Call letterM(h,c)
        Case "N"
          Call letterN(h,c)
        Case "O"
          Call letterO(h,c)
        Case "P"
          Call letterP(h,c)
        Case "Q"
          Call letterQ(h,c)
        Case "R"
          Call letterR(h,c)
        Case "S"
          Call letterS(h,c)
        Case "T"
          Call letterT(h,c)
        Case "U"
          Call letterU(h,c)
        Case "V"
          Call letterV(h,c)
        Case "W"
          Call letterW(h,c)
        Case "X"
          Call letterX(h,c)
        Case "Y"
          Call letterY(h,c)
        Case "Z"
          Call letterZ(h,c)
        Case " "
          Call blank(h,c)
        Case "!"
          Call exclMk(h,c)
        Case "?"
          Call qstnMk(h,c)
        Case "."
          Call fullSt(h,c)
        Case Else
          Call charDummy(h,c)
      End Select
    End If
    right(90)
    penUp()
    forward(gap) : Rem color = ffffff
    penDown()
    left(90)
  Next k
End Sub

Rem = = = = 8< = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 

