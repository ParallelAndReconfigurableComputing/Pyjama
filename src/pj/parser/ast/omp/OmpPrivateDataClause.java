/*
 * Copyright (C) 2013-2016 Parallel and Reconfigurable Computing Group, University of Auckland.
 *
 * Authors: <http://homepages.engineering.auckland.ac.nz/~parallel/ParallelIT/People.html>
 * 
 * This file is part of Pyjama, a Java implementation of OpenMP-like directive-based 
 * parallelisation compiler and its runtime routines.
 *
 * Pyjama is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Pyjama is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Pyjama. If not, see <http://www.gnu.org/licenses/>.
 */

package pj.parser.ast.omp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import pj.parser.ast.expr.Expression;
import pj.parser.ast.symbolscope.ScopeInfo;
import pj.parser.ast.symbolscope.Symbol;
import pj.parser.ast.visitor.GenericVisitor;
import pj.parser.ast.visitor.SourcePrinter;
import pj.parser.ast.visitor.VoidVisitor;
import pj.parser.ast.visitor.dataclausehandler.DataClauseHandlerUtils;

public class OmpPrivateDataClause extends OmpDataClause{

	private Set<Expression> argumentSet;
	
	public OmpPrivateDataClause() {
		this.type = OmpDataClause.Type.Private;
		this.argumentSet = new HashSet<Expression>();
	}
	
	public OmpPrivateDataClause(Set<Expression> variableSet) {
		this.type = OmpDataClause.Type.Private;
		this.argumentSet = variableSet;
	}
	
	public OmpPrivateDataClause(int beginLine, int beginColumn, int endLine, int endColumn, Set<Expression> variableList) {
		super(beginLine, beginColumn, endLine, endColumn);
		this.type = OmpDataClause.Type.Private;
		this.argumentSet = variableList;
	}
	
	public Set<Expression> getArgumentSet() {
		return this.argumentSet;
	}
	
	public void addArgument(Expression argument) {
		this.argumentSet.add(argument);
	}
	
	public void printPrivateVariableDefination(OpenMPStatement n, SourcePrinter printer) {
		HashMap<String, String> args = this.getArgsTypes(n);
		for (String varName: args.keySet()) {
			String varType = args.get(varName);
			printer.print(varType + " " + varName);
			if (DataClauseHandlerUtils.isPrimitiveType(varType)) {
				printer.printLn(" = " + DataClauseHandlerUtils.getDefaultValuesForPrimitiveType(varType) + ";");
			}
			else {
				printer.printLn(" = null;");
			}
			//e.g. Point p=null;
			//e.g. int i=0;
		}
	}
	
	public void printPrivateVariableInitialisation(OpenMPStatement n, SourcePrinter printer) {
		HashMap<String, String> args = this.getArgsTypes(n);
		for (String varName: args.keySet()) {
			String varType = args.get(varName);
			if (DataClauseHandlerUtils.isPrimitiveType(varType)) {
				printer.printLn(varName + " = " + "(" + DataClauseHandlerUtils.autoBox(varType) + ")" 
						+ "OMP_inputList.get(" + "\"" + varName + "\");");
				//e.g. i = (Integer)this.inputlist.get("i");
				//directly cast object type to primitive type is unsupported in java1.6
			}
			else {
				printer.printLn(varName + " = " + "(" + varType + ")" 
						+ "OMP_inputList.get(" + "\"" + varName + "\");");
				//e.g. sp = (Point)this.inputList.get("sp");
				//e.g. sp1 = (String)this.inputList.get("sp1");
			}
		}
	}
	
	public HashMap<String, String> getArgsTypes(OpenMPStatement n) {
		HashMap<String, String> varTypes = new HashMap<String, String>();
		ScopeInfo scope = n.scope;
		LinkedList<Symbol> symbols = scope.getAllReachableSymbols();

		for (Expression v: this.argumentSet) {
			String varName = v.toString();
			boolean findVarName = false;
			for (Symbol s: symbols) {
				if (s.isVariableNameAs(varName)) {
					String varType = s.getSymbolDataType().toString();
					varTypes.put(varName, varType);
					findVarName = true;
					break;
				}
			}
			if (!findVarName) {
				throw new RuntimeException("Illegal variable '" + varName + "' in private data clause");
			}
		}
		return varTypes;
	}
	
	@Override
	public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
		return v.visit(this, arg);
	}

	@Override
	public <A> void accept(VoidVisitor<A> v, A arg) {
		v.visit(this, arg);	
	}

}
