// Generated by Structorizer 3.30-11 

// Copyright (C) 2019-10-10 Kay Gürtzig 
// License: GPLv3-link 
// GNU General Public License (V 3) 
// https://www.gnu.org/licenses/gpl.html 
// http://www.gnu.de/documents/gpl.de.html 

#include <string>
#include <iostream>
using std::string;

// function blank(h, colorNo) 

// Draws a blank for font height h, ignoring the colorNo 
// TODO: Revise the return type and declare the parameters. 
void blank(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	??? width = h/2.0;
	penUp();
	right(90);
	forward(width); // color = ffffff
	left(90);
}

// function forward(len, color) 

// TODO: Revise the return type and declare the parameters. 
void forward(??? len, ??? color)
{
	// TODO: Check and accomplish variable declarations: 

	switch (color) {
	case 1:
		forward(len); // color = ffffff
		break;
	case 2:
		forward(len); // color = ff8080
		break;
	case 3:
		forward(len); // color = ffff80
		break;
	case 4:
		forward(len); // color = 80ff80
		break;
	case 5:
		forward(len); // color = 80ffff
		break;
	case 6:
		forward(len); // color = 0080ff
		break;
	case 7:
		forward(len); // color = ff80c0
		break;
	case 8:
		forward(len); // color = c0c0c0
		break;
	case 9:
		forward(len); // color = ff8000
		break;
	case 10:
		forward(len); // color = 8080ff
		break;
	}
}

// function letterA(h, colorNo) 

// Draws letter A in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterA(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;
	double hypo;

	??? width = h/2.0;
	hypo = sqrt(h*h + width*width/4.0);
	rotAngle = toDegrees(atan(width/2.0/h));
	right(rotAngle);
	forward(hypo/2.0, colorNo);
	right(90 - rotAngle);
	forward(width/2.0, colorNo);
	penUp();
	backward(width/2.0); // color = ffffff
	penDown();
	left(90 - rotAngle);
	forward(hypo/2.0, colorNo);
	left(2*rotAngle);
	forward(-hypo, colorNo);
	right(rotAngle);
}

// function letterE(h, colorNo) 

// Draws letter E in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterE(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	??? width = h/2.0;
	forward(h, colorNo);
	right(90);
	forward(width, colorNo);
	right(90);
	penUp();
	forward(h/2.0); // color = ffffff
	right(90);
	penDown();
	forward(width, colorNo);
	left(90);
	penUp();
	forward(h/2.0); // color = ffffff
	left(90);
	penDown();
	forward(width, colorNo);
	left(90);
}

// function letterF(h, colorNo) 

// Draws letter F in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterF(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	??? width = h/2.0;
	forward(h, colorNo);
	right(90);
	forward(width, colorNo);
	right(90);
	penUp();
	forward(h/2.0); // color = ffffff
	right(90);
	penDown();
	forward(width, colorNo);
	left(90);
	penUp();
	forward(h/2.0); // color = ffffff
	left(90);
	forward(width); // color = ffffff
	penDown();
	left(90);
}

// function letterH(h, colorNo) 

// Draws letter H in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterH(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	??? width = h/2.0;
	forward(h, colorNo);
	penUp();
	right(90);
	forward(width); // color = ffffff
	right(90);
	penDown();
	forward(h/2.0, colorNo);
	right(90);
	forward(width, colorNo);
	penUp();
	backward(width); // color = ffffff
	left(90);
	penDown();
	forward(h/2.0, colorNo);
	left(180);
}

// function letterI(h, colorNo) 

// Draws letter I in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterI(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the corner triangle outside the octagon 
	??? c = b / sqrt(2.0);
	penUp();
	right(90);
	forward(c); // color = ffffff
	penDown();
	forward(b, colorNo);
	penUp();
	backward(b/2.0); // color = ffffff
	left(90);
	penDown();
	forward(h, colorNo);
	penUp();
	right(90);
	backward(b/2.0); // color = ffffff
	penDown();
	forward(b, colorNo);
	penUp();
	forward(b/2 + c); // color = ffffff
	left(90);
	backward(h); // color = ffffff
	penDown();
}

// function letterK(h, colorNo) 

// Draws letter K in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterK(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	??? width = h/2.0;
	??? diag = h/sqrt(2.0);
	forward(h, colorNo);
	penUp();
	right(90);
	forward(width); // color = ffffff
	right(135);
	penDown();
	forward(diag, colorNo);
	left(90);
	forward(diag, colorNo);
	left(135);
}

// function letterL(h, colorNo) 

// Draws letter L in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterL(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	??? width = h/2.0;
	forward(h, colorNo);
	penUp();
	backward(h); // color = ffffff
	right(90);
	penDown();
	forward(width, colorNo);
	left(90);
}

// function letterM(h, colorNo) 

// Draws letter M in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterM(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;

	??? width = h/2.0;
	??? hypo = sqrt(width*width + h*h)/2.0;
	rotAngle = toDegrees(atan(width/h));
	forward(h, colorNo);
	left(rotAngle);
	forward(-hypo, colorNo);
	right(2*rotAngle);
	forward(hypo, colorNo);
	left(rotAngle);
	forward(-h, colorNo);
}

// function letterN(h, colorNo) 

// Draws letter N in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterN(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;
	double hypo;

	??? width = h/2.0;
	hypo = sqrt(width*width + h*h);
	rotAngle = toDegrees(atan(width/h));
	forward(h, colorNo);
	left(rotAngle);
	forward(-hypo, colorNo);
	right(rotAngle);
	forward(h, colorNo);
	penUp();
	backward(h); // color = ffffff
	penDown();
}

// function letterT(h, colorNo) 

// Draws letter T in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterT(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	??? width = h/2.0;
	penUp();
	forward(h); // color = ffffff
	penDown();
	right(90);
	forward(width, colorNo);
	penUp();
	backward(width/2.0); // color = ffffff
	penDown();
	right(90);
	forward(h, colorNo);
	left(90);
	penUp();
	forward(width/2.0); // color = ffffff
	penDown();
	left(90);
}

// function letterV(h, colorNo) 

// Draws letter V in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterV(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;
	double hypo;

	??? width = h/2.0;
	hypo = sqrt(h*h + width*width/4.0);
	rotAngle = toDegrees(atan(width/2.0/h));
	penUp();
	forward(h); // color = ffffff
	left(rotAngle);
	penDown();
	forward(-hypo, colorNo);
	right(2*rotAngle);
	forward(hypo, colorNo);
	penUp();
	left(rotAngle);
	backward(h); // color = ffffff
	penDown();
}

// function letterW(h, colorNo) 

// Draws letter W in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterW(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;
	double hypo;

	??? width = h/2.0;
	??? width_3 = width/3.0;
	hypo = sqrt(width_3*width_3 + h*h);
	rotAngle = toDegrees(atan(width_3/h));
	penUp();
	forward(h); // color = ffffff
	left(rotAngle);
	penDown();
	forward(-hypo, colorNo);
	right(2*rotAngle);
	forward(hypo, colorNo);
	penUp();
	left(90+rotAngle);
	forward(width_3); // color = ffffff
	right(90-rotAngle);
	penDown();
	forward(-hypo, colorNo);
	right(2*rotAngle);
	forward(hypo, colorNo);
	penUp();
	left(rotAngle);
	backward(h); // color = ffffff
	penDown();
}

// function letterX(h, colorNo) 

// Draws letter X in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterX(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;
	double hypo;

	??? width = h/2.0;
	hypo = sqrt(width*width + h*h);
	rotAngle = toDegrees(atan(width/h));
	right(rotAngle);
	forward(hypo, colorNo);
	penUp();
	left(90+rotAngle);
	forward(width); // color = ffffff
	right(90-rotAngle);
	penDown();
	forward(-hypo, colorNo);
	right(rotAngle);
}

// function letterY(h, colorNo) 

// Draws letter Y in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterY(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;

	??? width = h/2.0;
	??? hypo = sqrt(width*width + h*h)/2.0;
	rotAngle = toDegrees(atan(width/h));
	penUp();
	forward(h); // color = ffffff
	left(rotAngle);
	penDown();
	forward(-hypo, colorNo);
	right(rotAngle);
	penUp();
	backward(h/2.0); // color = ffffff
	penDown();
	forward(h/2.0, colorNo);
	right(rotAngle);
	forward(hypo, colorNo);
	left(rotAngle);
	penUp();
	backward(h); // color = ffffff
	penDown();
}

// function letterZ(h, colorNo) 

// Draws letter Z in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterZ(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;
	double hypo;

	??? width = h/2.0;
	hypo = sqrt(width*width + h*h);
	rotAngle = toDegrees(atan(width/h));
	penUp();
	forward(h); // color = ffffff
	right(90);
	penDown();
	forward(width, colorNo);
	left(90-rotAngle);
	forward(-hypo, colorNo);
	right(90-rotAngle);
	forward(width, colorNo);
	left(90);
}

// function polygonPart(a: double; n: integer; ctrclkws: boolean; nEdges: integer; color: int) 

// Draws nEdges edges of a regular n-polygon with edge length a 
// counter-clockwise, if ctrclkws is true, or clockwise if ctrclkws is false. 
// TODO: Revise the return type and declare the parameters. 
void polygonPart(double a, int n, bool ctrclkws, int nEdges, int color)
{
	// TODO: Check and accomplish variable declarations: 

	??? rotAngle = 360.0/n;
	if (ctrclkws) {
		rotAngle = -rotAngle;
	}
	for (int k = 1; k <= nEdges; k += (1)) {
		right(rotAngle);
		forward(a, color);
	}
}

// function charDummy(h, colorNo) 

// Draws a dummy character (small centered square) with font height h and 
// the colour encoded by colorNo 
// TODO: Revise the return type and declare the parameters. 
void charDummy(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	??? width = h / 2.0;
	// Octagon edge length (here: edge lengzh of the square) 
	??? b = width / (sqrt(2.0) + 1);
	// Cathetus of the corner triangle outside the octagon 
	??? c = (width - b) / 2.0;
	??? d = b / sqrt(2.0);
	penUp();
	forward(h/2.0-b/2.0); // color = ffffff
	right(90);
	forward(c); // color = ffffff
	right(90);
	penDown();
	// Draws the square with edge length b 
	polygonPart(b, 4, true, 4, colorNo);
	penUp();
	left(90);
	forward(b + c); // color = ffffff
	left(90);
	backward(h/2.0-b/2.0); // color = ffffff
	penDown();
}

// function comma(h, colorNo) 

// Draws a comma in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void comma(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle;

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	rotAngle = toDegrees(atan(0.5));
	??? hypo = c * sqrt(1.25);
	penUp();
	right(90);
	forward((c+b)/2.0 + c); // color = ffffff
	penDown();
	// Counterclockwise draw 3 edges of a square with edge length c 
	// in the colour endcoded by colorNo 
	polygonPart(c, 4, true, 3, colorNo);
	left(90);
	forward(c/2.0, colorNo);
	right(90);
	forward(c, colorNo);
	left(180 - rotAngle);
	forward(hypo, colorNo);
	penUp();
	right(90 - rotAngle);
	forward((c + b)/2.0); // color = ffffff
	left(90);
	penDown();
}

// function exclMk(h, colorNo) 

// Draws an exclamation mark in the colour encoded by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void exclMk(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	double rotAngle2;
	int rotAngle;
	double hypo;

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	??? width = h/2.0;
	??? length1 = h - (b+c)/2.0;
	??? length2 = length1 - 2*c;
	hypo = sqrt(width*width/16.0 + length2*length2);
	// 360°/8 
	rotAngle = 45;
	rotAngle2 = toDegrees(atan(width/4.0/length2));
	penUp();
	forward(length1); // color = ffffff
	right(90);
	forward(width/2.0); // color = ffffff
	left(90 + rotAngle);
	penDown();
	// Clockwise draw 5 edges of an octagon with edge length b/2 
	// in the colour endcoded by colorNo 
	polygonPart(b/2.0, 8, false, 5, colorNo);
	right(rotAngle2);
	forward(hypo, colorNo);
	left(2*rotAngle2);
	forward(-hypo, colorNo);
	penUp();
	forward(hypo); // color = ffffff
	right(rotAngle2);
	forward(c); // color = ffffff
	left(90);
	forward(c/2.0); // color = ffffff
	penDown();
	// Counterclockwise draw all 4 edges of a squarfe with edge length c 
	// in the colour endcoded by colorNo 
	polygonPart(c, 4, false, 4, colorNo);
	penUp();
	forward((c + b)/2.0); // color = ffffff
	left(90);
	backward(c); // color = ffffff
	penDown();
}

// function fullSt(h, colorNo) 

// Draws a full stop in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void fullSt(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	penUp();
	right(90);
	forward((c+b)/2.0 + c); // color = ffffff
	penDown();
	// Counterclockwise draw all 4 edges of a squarfe with edge length c 
	// in the colour endcoded by colorNo 
	polygonPart(c, 4, true, 4, colorNo);
	penUp();
	forward((c + b)/2.0); // color = ffffff
	left(90);
	penDown();
}

// function letterB(h, colorNo) 

// Draws letter B in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterB(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	forward(h, colorNo);
	right(90);
	forward(c+b, colorNo);
	// Clockwise draw 4 edges of an octagon with edge length b 
	polygonPart(b, 8, false, 4, colorNo);
	forward(c, colorNo);
	penUp();
	left(180);
	forward(b + c); // color = ffffff
	penDown();
	// Clockwise draw 4 edges of an octagon with edge length b 
	polygonPart(b, 8, false, 4, colorNo);
	forward(c, colorNo);
	penUp();
	left(180);
	forward(b + 2*c); // color = ffffff
	penDown();
	left(90);
}

// function letterC(h, colorNo) 

// Draws letter C in the colour encoded by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterC(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	int rotAngle;

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer trinagle at the octagon corner 
	??? c = b / sqrt(2.0);
	// 360°/8 
	rotAngle = 45;
	penUp();
	forward(c); // color = ffffff
	penDown();
	right(180);
	// Clockwise draws 3 edges of an octagon with edge length b in the colour 
	// encoded by colorNo 
	polygonPart(b, 8, true, 3, colorNo);
	left(rotAngle);
	penUp();
	forward(2*b + 2*c); // color = ffffff
	penDown();
	// Counterclockwise draws 4 edges of an octagon with edge length b 
	// iin the colour encoded by colorNo 
	polygonPart(b, 8, true, 4, colorNo);
	forward(b + 2*c, colorNo);
	penUp();
	forward(c); // color = ffffff
	left(90);
	forward(b + 2*c, colorNo);
	penDown();
	left(90);
}

// function letterD(h, colorNo) 

// Draws letter D in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterD(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	forward(h, colorNo);
	right(90);
	forward(c+b, colorNo);
	// Clockwise draw 2 edges of an octagon with edge length b in the colour 
	// encoded by colorNo 
	polygonPart(b, 8, false, 2, colorNo);
	forward(b + 2*c, colorNo);
	// Clockwise draw 2 edges of an octagon with edge length b in the colour 
	// encoded by colorNo 
	polygonPart(b, 8, false, 2, colorNo);
	forward(c, colorNo);
	penUp();
	left(180);
	forward(b + 2*c); // color = ffffff
	penDown();
	left(90);
}

// function letterG(h, colorNo) 

// Draws letter G in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterG(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the corner triangle outside the octagon. 
	??? c = b / sqrt(2.0);
	penUp();
	forward(c); // color = ffffff
	penDown();
	right(180);
	// Counterclockwise draw 4 edges of an octagon with edge length b in 
	// the colour encoded by colorNo 
	polygonPart(b, 8, true, 4, colorNo);
	forward(c, colorNo);
	left(90);
	forward(b/2.0 + c, colorNo);
	penUp();
	backward(b/2.0 + c); // color = ffffff
	right(90);
	forward(b + c); // color = ffffff
	penDown();
	// Counterclockwise draw 4 edges of an octagon with edge length b in 
	// the colour encoded by colorNo 
	polygonPart(b, 8, true, 4, colorNo);
	forward(b + 2*c, colorNo);
	penUp();
	forward(c); // color = ffffff
	left(90);
	forward(b + 2*c, colorNo);
	penDown();
	left(90);
}

// function letterJ(h, colorNo) 

// Draws letter J in colour encoded by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterJ(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	int rotAngle;

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	// 360°/8 
	rotAngle = 45;
	penUp();
	forward(c); // color = ffffff
	penDown();
	right(180);
	// Counterclockwise draw 3 edges of an octagon with edge length b in 
	// the colour encoded by colorNo 
	polygonPart(b, 8, true, 3, colorNo);
	left(rotAngle);
	forward(h - c, colorNo);
	penUp();
	backward(h); // color = ffffff
	penDown();
}

// function letterO(h, colorNo) 

// Draws letter O in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterO(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the corner triangle outside the octagon 
	??? c = b / sqrt(2.0);
	penUp();
	forward(c); // color = ffffff
	penDown();
	right(180);
	// Counterclockwise draw 4 edges of an octagon with edge length b 
	// in the colour endcoded by colorNo 
	polygonPart(b, 8, true, 4, colorNo);
	forward(b + 2*c, colorNo);
	// Counterclockwise draw 4 edges of an octagon with edge length b 
	// in the colour endcoded by colorNo 
	polygonPart(b, 8, true, 4, colorNo);
	forward(b + 2*c, colorNo);
	penUp();
	forward(c); // color = ffffff
	left(90);
	forward(b + 2*c); // color = ffffff
	penDown();
	left(90);
}

// function letterP(h, colorNo) 

// Draws letter P in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterP(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the corner triangle outside the octagon 
	??? c = b / sqrt(2.0);
	forward(h, colorNo);
	right(90);
	forward(c+b, colorNo);
	// Clockwise draw 4 edges of an octagon with edge length b 
	// in the colour endcoded by colorNo 
	polygonPart(b, 8, false, 4, colorNo);
	forward(c, colorNo);
	penUp();
	backward(b + 2*c); // color = ffffff
	left(90);
	forward(b + 2*c); // color = ffffff
	penDown();
	left(180);
}

// function letterQ(h, colorNo) 

// Draws letter Q in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterQ(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	int rotAngle;

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	// 360°/8 
	rotAngle = 45;
	penUp();
	forward(c); // color = ffffff
	penDown();
	right(180);
	// Counterclockwise draw 4 edges of an octagon with edge length b 
	// in the colour endcoded by colorNo 
	polygonPart(b, 8, true, 4, colorNo);
	forward(b + 2*c, colorNo);
	// Counterclockwise draw 4 edges of an octagon with edge length b 
	// in the colour endcoded by colorNo 
	polygonPart(b, 8, true, 4, colorNo);
	forward(b + 2*c, colorNo);
	penUp();
	forward(c); // color = ffffff
	left(90);
	forward(b + 2*c); // color = ffffff
	right(rotAngle);
	backward(b); // color = ffffff
	penDown();
	forward(b, colorNo);
	left(90 + rotAngle);
}

// function letterR(h, colorNo) 

// Zeichnet den Buchstaben R von der Turtleposition aus 
// mit Zeilenhöhe h 
// TODO: Revise the return type and declare the parameters. 
void letterR(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	int rotAngle;

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	// 360°/8 
	rotAngle = 45;
	forward(h, colorNo);
	right(90);
	forward(c+b, colorNo);
	// Clockwise draw 4 edges of an octagon with edge length b 
	// in the colour endcoded by colorNo 
	polygonPart(b, 8, false, 4, colorNo);
	forward(c, colorNo);
	left(90 + rotAngle);
	forward(sqrt(2.0)*(b + 2*c), colorNo);
	left(90 + rotAngle);
}

// function letterS(h, colorNo) 

// Draws letter S in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterS(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	int rotAngle;

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Side length of the (outer) corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	// 360°/8 
	rotAngle = 45;
	penUp();
	forward(c); // color = ffffff
	penDown();
	right(180);
	// Counterclockwise draw 6 edges of an octagon with edge length b 
	// in the colour encoded by colorNo 
	polygonPart(b, 8, true, 6, colorNo);
	// Clockwise draw 5 edges of an octagon with edge length b 
	// in the colour encoded by colorNo 
	polygonPart(b, 8, false, 5, colorNo);
	right(rotAngle);
	penUp();
	forward(2*b + 3*c); // color = ffffff
	penDown();
	left(180);
}

// function letterU(h, colorNo) 

// Draws letter U in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void letterU(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	int rotAngle;

	// edge length of a regular octagon 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	// 360°/8 
	rotAngle = 45;
	penUp();
	forward(c); // color = ffffff
	penDown();
	forward(h - c, colorNo);
	penUp();
	backward(h-c); // color = ffffff
	penDown();
	right(180);
	// Counterclockwise draw 3 edges of an octagoin with edge length b in colour specified by colorNo 
	polygonPart(b, 8, true, 3, colorNo);
	left(rotAngle);
	forward(h - c, colorNo);
	penUp();
	backward(h); // color = ffffff
	penDown();
}

// function qstnMk(h, colorNo) 

// Draws a question mark in colour specified by colorNo with font height h 
// from the current turtle position. 
// TODO: Revise the return type and declare the parameters. 
void qstnMk(??? h, ??? colorNo)
{
	// TODO: Check and accomplish variable declarations: 
	int rotAngle;

	// Octagon edge length 
	??? b = h * 0.5 / (sqrt(2.0) + 1);
	// Cathetus of the outer corner triangle of the octagon 
	??? c = b / sqrt(2.0);
	// 360°/8 
	rotAngle = 45;
	penUp();
	forward(h-c); // color = ffffff
	penDown();
	// Counterclockwise draw 5 edges of an octagon with edge length b 
	// in the colour endcoded by colorNo 
	polygonPart(b, 8, false, 5, colorNo);
	forward(c, colorNo);
	left(rotAngle);
	forward(b/2.0, colorNo);
	penUp();
	forward(c); // color = ffffff
	left(90);
	forward(c/2.0); // color = ffffff
	penDown();
	// Counterclockwise draw all 4 edges of a squarfe with edge length c 
	// in the colour endcoded by colorNo 
	polygonPart(c, 4, false, 4, colorNo);
	penUp();
	forward((c + b)/2.0); // color = ffffff
	left(90);
	backward(c); // color = ffffff
	penDown();
}

// function drawText(text: string; h: integer; c: integer) 

// Has the turtle draw the given string 'text´ with font height 'h´ (in 
// pixels) and the colour coded by integer 'c´ from the current Turtle 
// position to the Turtle canvas. If the turtle looks North then 
// the text will be written rightwards. In the event, the turtle will be 
// placed behind the text in original orientation (such that the next text 
// would be written like a continuation. Colour codes: 
// 1 = black 
// 2 = red 
// 3 = yellow 
// 4 = green 
// 5 = cyan 
// 6 = blue 
// 7 = pink 
// 8 = grey 
// 9 = orange 
// 10 = violet 
// All letters (ASCII) will be converted to uppercase, digits cannot 
// be represented, the set of representable special characters is: 
// '.', ',', '!', '?'. Other characters will be shown as a small 
// centred square (dummy character). 
// TODO: Revise the return type and declare the parameters. 
void drawText(string text, int h, int c)
{
	// TODO: Check and accomplish variable declarations: 
	string letter;

	??? gap = h/10.0;
	for (int k = 1; k <= length(text); k += (1)) {
		letter = uppercase(copy(text, k, 1));
		if (letter == ",") {
			comma(h,c);
		}
		else {
			// "," cannot be chacked against because the comma is misinterpreted 
			// as selector list separator. 
			switch (letter) {
			case "A":
				letterA(h,c);
				break;
			case "B":
				letterB(h,c);
				break;
			case "C":
				letterC(h,c);
				break;
			case "D":
				letterD(h,c);
				break;
			case "E":
				letterE(h,c);
				break;
			case "F":
				letterF(h,c);
				break;
			case "G":
				letterG(h,c);
				break;
			case "H":
				letterH(h,c);
				break;
			case "I":
				letterI(h,c);
				break;
			case "J":
				letterJ(h,c);
				break;
			case "K":
				letterK(h,c);
				break;
			case "L":
				letterL(h,c);
				break;
			case "M":
				letterM(h,c);
				break;
			case "N":
				letterN(h,c);
				break;
			case "O":
				letterO(h,c);
				break;
			case "P":
				letterP(h,c);
				break;
			case "Q":
				letterQ(h,c);
				break;
			case "R":
				letterR(h,c);
				break;
			case "S":
				letterS(h,c);
				break;
			case "T":
				letterT(h,c);
				break;
			case "U":
				letterU(h,c);
				break;
			case "V":
				letterV(h,c);
				break;
			case "W":
				letterW(h,c);
				break;
			case "X":
				letterX(h,c);
				break;
			case "Y":
				letterY(h,c);
				break;
			case "Z":
				letterZ(h,c);
				break;
			case " ":
				blank(h,c);
				break;
			case "!":
				exclMk(h,c);
				break;
			case "?":
				qstnMk(h,c);
				break;
			case ".":
				fullSt(h,c);
				break;
			default:
				charDummy(h,c);
			}
		}
		right(90);
		penUp();
		forward(gap); // color = ffffff
		penDown();
		left(90);
	}
}
// = = = = 8< = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 

// program TextDemo 

// Demo program for routine drawText() 
// Asks the user to enter a text, a wanted text height and colour, 
// and then draws this string onto the turtle screen. Places every 
// entered text to a new line. 
int main(void)
{
	// TODO: Check and accomplish variable declarations: 
	int y;
	??? height;
	??? colour;

	std::cout << "This is a demo program for text writing with Turleizer." << std::endl;
	showTurtle();
	penDown();
	y = 0;
	do {
		std::cout << "Enter some text (empty string to exit)"; std::cin >> text;
		// Make sure the content is interpreted as string 
		??? text = "" + text;
		if (text != "") {
			do {
				std::cout << "Height of the text (pixels)"; std::cin >> height;
			} while (! (height >= 5));
			do {
				std::cout << "Colour (1=black, 2=red, 3=yellow, 4=green, 5=cyan, 6=blue, 7=pink, 8=gray, 9=orange, 10=violet)"; std::cin >> colour;
			} while (! (colour >= 1 && colour <= 10));
			y = y + height + 2;
			gotoXY(0, y - 2);
			drawText(text, height, colour);
		}
	} while (! (text == ""));
	gotoXY(0, y + 15);
	drawText("Thank you, bye.", 10, 4);
	hideTurtle();

	return 0;
}
