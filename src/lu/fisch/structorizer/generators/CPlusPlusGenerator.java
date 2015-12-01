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

package lu.fisch.structorizer.generators;

/******************************************************************************************************
*
*      Author:         Kay Gürtzig
*
*      Description:    This class generates C++ code (mainly based on ANSI C code except for IO).
*
******************************************************************************************************
*
*      Revision List
*
*      Author          	Date			Description
*      ------			----			-----------
*      Kay Gürtzig      2010.08.31      First Issue
*      Kay Gürtzig      2015.11.01      Adaptations to new decomposed preprocessing
*      Kay Gürtzig      2015.11.30      Jump mechanisms (KGU#78) and root export revised 
*
******************************************************************************************************
*
*      Comment:
*      2015.11.01 Simplified, drastically reduced to CGenerator as parent class

*      2010.08.31 Initial version
*      - root handling overridden - still too much copied code w.r.t. CGenerator, should be
*        parameterized
*
******************************************************************************************************///

import lu.fisch.structorizer.elements.Root;
import lu.fisch.structorizer.parsers.D7Parser;
import lu.fisch.utils.BString;
import lu.fisch.utils.StringList;

public class CPlusPlusGenerator extends CGenerator {

    /************ Fields ***********************/
    protected String getDialogTitle()
    {
            return "Export C++ ...";
    }

    protected String getFileDescription()
    {
            return "C++ Source Code";
    }

    protected String[] getFileExtensions()
    {
            String[] exts = {"cpp"};
            return exts;
    }

    /************ Code Generation **************/
	// START KGU#18/KGU#23 2015-11-01 Transformation decomposed
	/**
	 * A pattern how to embed the variable (right-hand side of an input instruction)
	 * into the target code
	 * @return a regex replacement pattern, e.g. "$1 = (new Scanner(System.in)).nextLine()"
	 */
	protected String getInputReplacer()
	{
		return "std::cin >> $1";
	}

	/**
	 * A pattern how to embed the expression (right-hand side of an output instruction)
	 * into the target code
	 * @return a regex replacement pattern, e.g. "System.out.println($1)"
	 */
	protected String getOutputReplacer()
	{
		return "std::cout << $1 << std::endl";
	}


// KGU#74 (2015-11-30): Now we only override some of the decomposed methods below
//    @Override
//    public String generateCode(Root _root, String _indent)
//    {
//		// START KGU#78 2015-11-30: Prepare the label associations
//		this.alwaysReturns = this.mapJumps(_root.children);
//		// END KGU#78 2015-11-30
//
//		String fnName = _root.getMethodName();
//		// START KGU 2015-11-29: More informed generation attempts
//		// Get all local variable names
//		StringList varNames = _root.getVarNames(_root, false, true);
//		// code.add(pr+" "+_root.getText().get(0)+";");
//        // add comment
//    	insertComment(_root, "");
//
//        String pr = _root.isProgram ? "program" : "function";
//        insertComment(pr + " " + fnName, "");
//        code.add("#include <iostream>");
//        code.add("");
//        
//        if (_root.isProgram)
//        	code.add("int main(void)");
//        else {
//			// START KGU 2015-11-29: We may get more informed information
//			// String fnHeader = _root.getText().get(0).trim();
//			// if(fnHeader.indexOf('(')==-1 || !fnHeader.endsWith(")"))
//			// fnHeader=fnHeader+"(void)";
//			this.isResultSet = varNames.contains("result", false);
//			this.isFunctionNameSet = varNames.contains(fnName);
//			String fnHeader = transformType(_root.getResultType(),
//					((returns || isResultSet || isFunctionNameSet) ? "int" : "void"));
//			fnHeader += " " + fnName + "(";
//			StringList paramNames = new StringList();
//			StringList paramTypes = new StringList();
//			_root.collectParameters(paramNames, paramTypes);
//			for (int p = 0; p < paramNames.count(); p++) {
//				if (p > 0)
//					fnHeader += ", ";
//				fnHeader += (transformType(paramTypes.get(p), "/*type?*/") + " " + paramNames
//						.get(p)).trim();
//			}
//			fnHeader += ")";
//			// END KGU 2015-11-29
//            insertComment("TODO Revise the return type and declare the parameters!", _indent);
//            
//        	code.add(fnHeader);
//        }
//        // END KGU 2010-08-31
//        code.add("{");
//        insertComment("TODO Don't forget to declare your variables!", this.getIndent());
//        // START KGU 2015-11-30: List the variables to be declared
//		for (int v = 0; v < varNames.count(); v++) {
//			insertComment(varNames.get(v), this.getIndent());
//		}
//		// END KGU 2015-11-30
//		code.add(this.getIndent());
//
//        code.add(this.getIndent());
//        generateCode(_root.children, this.getIndent());
//        code.add(this.getIndent());
//        // START KGU#78 2015-11-30: Adaptive return generation on demand
//        //code.add(this.getIndent() + "return 0;");
//		if (_root.isProgram) {
//			code.add(this.getIndent());
//			code.add(this.getIndent() + "return 0;");
//		} else if ((returns || _root.getResultType() != null || isFunctionNameSet || isResultSet) && !alwaysReturns) {
//			String result = "0";
//			if (isFunctionNameSet) {
//				result = fnName;
//			} else if (isResultSet) {
//				int vx = varNames.indexOf("result", false);
//				result = varNames.get(vx);
//			}
//			code.add(this.getIndent());
//			code.add(this.getIndent() + "return " + result + ";");
//		}
//		// END KGU#78 2015-11-30
//        code.add("}");
//
//        return code.getText();
//    }
    
	/**
	 * Composes the heading for the program or function according to the
	 * C language specification.
	 * @param _root - The diagram root
	 * @param _indent - the initial indentation string
	 * @param _procName - the procedure name
	 * @param paramNames - list of the argument names
	 * @param paramTypes - list of corresponding type names (possibly null) 
	 * @param resultType - result type name (possibly null)
	 * @return the default indentation string for the subsequent stuff
	 */
	@Override
	protected String generateHeader(Root _root, String _indent, String _procName,
			StringList _paramNames, StringList _paramTypes, String _resultType)
	{

        // add comment
    	insertComment(_root, _indent);

        String pr = _root.isProgram ? "program" : "function";
        insertComment(pr + " " + _root.getText().get(0), _indent);
        code.add("#include <iostream>");
        code.add("");
        
        if (_root.isProgram)
        	code.add(_indent + "int main(void)");
        else {
			String fnHeader = transformType(_resultType,
					((returns || isResultSet || isFunctionNameSet) ? "int" : "void"));
			fnHeader += " " + _procName + "(";
			for (int p = 0; p < _paramNames.count(); p++) {
				if (p > 0)
					fnHeader += ", ";
				fnHeader += (transformType(_paramTypes.get(p), "/*type?*/") + " " +
					_paramNames.get(p)).trim();
			}
			fnHeader += ")";
			// END KGU 2015-11-29
            insertComment("TODO Revise the return type and declare the parameters!", _indent);
            
        	code.add(fnHeader);
        }
		
		code.add("{");
		
		return _indent + this.getIndent();
	}

	/**
	 * Generates some preamble (i.e. comments, language declaration section etc.)
	 * and adds it to this.code.
	 * @param _root - the diagram root element
	 * @param _indent - the current indentation string
	 * @param varNames - list of variable names introduced inside the body
	 */
	@Override
	protected String generatePreamble(Root _root, String _indent, StringList varNames)
	{
		code.add(_indent);
		insertComment("TODO: declare your variables here:", _indent);
        // START KGU 2015-11-30: List the variables to be declared
		for (int v = 0; v < varNames.count(); v++) {
			insertComment(varNames.get(v), _indent);
		}
		// END KGU 2015-11-30
		code.add(_indent);
		return _indent;
	}
    


}
