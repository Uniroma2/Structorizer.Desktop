/*
    Structorizer
    A little tool which you can use to create Nassi-Schneiderman Diagrams (NSD)

    Copyright (C) 2009  Bob Fisch

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or any
    later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package lu.fisch.graphics;

import java.awt.Rectangle;

/******************************************************************************************************
 *
 *      Author:         Bob Fisch
 *
 *      Description:    Class to represent a rectangle zone on the screen.
 *						Works like a "TRect" in Delphi
 *
 ******************************************************************************************************
 *
 *      Revision List
 *
 *      Author          Date			Description
 *      ------			----			-----------
 *      Bob Fisch       2007.12.09      First Issue
 *      Kay Gürtzig     2015.11.24      Conversions to and from java.awt.Rectangle
 *      Kay Gürtzig     2018.02.17      Method to generate a scaled copy added (to facilitate enh. #512)
 *
 ******************************************************************************************************
 *
 *      Comment:		/
 *
 ******************************************************************************************************/


public class Rect{

	public int top;
	public int left;
	public int right;
	public int bottom;
	
	public Rect(int _left, int _top, int _right, int _bottom)
	{
		this.left=_left;
		this.top=_top;
		this.bottom=_bottom;
		this.right=_right;
	}
	
	public Rect()
	{		
		this.left=0;
		this.top=0;
		this.bottom=0;
		this.right=0;
	}
	
	/**
	 * @param rect - a java.awt.Rectangle proving the dimensions
	 */
	public Rect(Rectangle _rect)
	{
		this.left = _rect.x;
		this.top = _rect.y;
		this.bottom = _rect.height + _rect.y;
		this.right = _rect.width + _rect.x;
	}
	
	public Rect copy()
	{
		return new Rect(this.left, this.top, this.right, this.bottom);
	}
	
	// START KGU 2018-02-17
	/**
	 * Creates a scaled copy of this with scale factor {@code factor}
	 * related to the coordinate origin.
	 * @param factor - the scale factor to be applied.
	 * @return an equivalent java.awt.Rectangle
	 */
	public Rect scale(double factor)
	{
		Rect scaledRect = this.copy();
		scaledRect.left *= factor;
		scaledRect.top *= factor;
		scaledRect.right *= factor;
		scaledRect.bottom *= factor;
		// Avoid degradation with 0 < factor < 1
		if (factor > 0) {
			if (right - left > 0 && scaledRect.right - scaledRect.left <= 0) {
				scaledRect.right = scaledRect.left + 1;
			}
			if (bottom - top > 0 && scaledRect.bottom - scaledRect.top <= 0) {
				scaledRect.bottom = scaledRect.top + 1;
			}
		}
		return scaledRect;
	}
	// END KGU 2018-02-17

	/**
	 * Convert myself into a java.awt.Rectangle
	 * @return an equivalent java.awt.Rectangle
	 */
	public Rectangle getRectangle()
	{
		return new Rectangle(left, top, right-left, bottom - top);
	}

	@Override
	public String toString()
	{
		return "Rect = ["+left+","+top+","+right+","+bottom+"]";
	}
}
