/*
    Turtlebox

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

package lu.fisch.turtle.elements;

/******************************************************************************************************
 *
 *      Author:         Robert Fisch
 *
 *      Description:    Line - a visible line in the Turtle graphics window
 *
 ******************************************************************************************************
 *
 *      Revision List
 *
 *      Author          Date            Description
 *      ------          ----            -----------
 *      Kay Gürtzig     2020-12-11      Enh. #704 API extension: draw(Graphics2D, Rectangle, Dimension)
 *
 ******************************************************************************************************
 *
 *      Comment:
 *
 ******************************************************************************************************///

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author robertfisch
 */
public class Line extends Element
{

    public Line(Point from, Point to)
    {
        super(from,to);
    }

    public Line(Point from, Point to, Color color)
    {
        super(from,to,color);
    }

    @Override
    public void draw(Graphics2D graphics)
    {
        graphics.setColor(color);
        graphics.drawLine(from.x, from.y, to.x, to.y);
    }

    // START KGU#685 2020-12-11: Enh. #704
    @Override
    public void draw(Graphics2D graphics, Rectangle viewRect, Dimension dim)
    {
        graphics.setColor(color);
        if (viewRect == null || viewRect.intersects(this.getBounds())) {
            graphics.drawLine(from.x, from.y, to.x, to.y);
        }
        if (from.x > dim.width) {
            dim.width = from.x;
        }
        if (to.x > dim.width) {
            dim.width = to.x;
        }
        if (from.y > dim.height) {
            dim.height = from.y;
        }
        if (to.y > dim.height) {
            dim.height = to.y;
        }
    }

    protected void appendSpecificCSVInfo(StringBuilder sb, String separator)
    {
        sb.append(separator);
        sb.append(Integer.toHexString(color.getRGB()));
    }

    private Rectangle getBounds()
    {
        Rectangle bounds = new Rectangle(from);
        bounds.add(to);
        // We must avoid "empty" rectangles for intersection tests
        if (bounds.height == 0) {
            bounds.height = 1;
        }
        if (bounds.width == 0) {
            bounds.width = 1;
        }
        return bounds;
    }
    // END KGU#685 2020-12-11
}
