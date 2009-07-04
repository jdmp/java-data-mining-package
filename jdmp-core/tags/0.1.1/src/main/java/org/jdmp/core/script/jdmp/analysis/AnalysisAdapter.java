/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.analysis;

import java.util.*;
import org.jdmp.core.script.jdmp.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    public void caseAScript(AScript node)
    {
        defaultCase(node);
    }

    public void caseAAssignmentCommand(AAssignmentCommand node)
    {
        defaultCase(node);
    }

    public void caseAStatementCommand(AStatementCommand node)
    {
        defaultCase(node);
    }

    public void caseAStatement(AStatement node)
    {
        defaultCase(node);
    }

    public void caseAIdentifierAssignment(AIdentifierAssignment node)
    {
        defaultCase(node);
    }

    public void caseAArrayAssignment(AArrayAssignment node)
    {
        defaultCase(node);
    }

    public void caseALevel10Expression(ALevel10Expression node)
    {
        defaultCase(node);
    }

    public void caseAStepsize(AStepsize node)
    {
        defaultCase(node);
    }

    public void caseALevel9Level10(ALevel9Level10 node)
    {
        defaultCase(node);
    }

    public void caseALogicalOrLevel10(ALogicalOrLevel10 node)
    {
        defaultCase(node);
    }

    public void caseALevel8Level9(ALevel8Level9 node)
    {
        defaultCase(node);
    }

    public void caseALogicalAndLevel9(ALogicalAndLevel9 node)
    {
        defaultCase(node);
    }

    public void caseALevel7Level8(ALevel7Level8 node)
    {
        defaultCase(node);
    }

    public void caseAOrLevel8(AOrLevel8 node)
    {
        defaultCase(node);
    }

    public void caseALevel6Level7(ALevel6Level7 node)
    {
        defaultCase(node);
    }

    public void caseAAndLevel7(AAndLevel7 node)
    {
        defaultCase(node);
    }

    public void caseALevel5Level6(ALevel5Level6 node)
    {
        defaultCase(node);
    }

    public void caseAEqLevel6(AEqLevel6 node)
    {
        defaultCase(node);
    }

    public void caseANeqLevel6(ANeqLevel6 node)
    {
        defaultCase(node);
    }

    public void caseALteqLevel6(ALteqLevel6 node)
    {
        defaultCase(node);
    }

    public void caseAGteqLevel6(AGteqLevel6 node)
    {
        defaultCase(node);
    }

    public void caseAGtLevel6(AGtLevel6 node)
    {
        defaultCase(node);
    }

    public void caseALtLevel6(ALtLevel6 node)
    {
        defaultCase(node);
    }

    public void caseALevel4Level5(ALevel4Level5 node)
    {
        defaultCase(node);
    }

    public void caseARangeLevel5(ARangeLevel5 node)
    {
        defaultCase(node);
    }

    public void caseALevel3Level4(ALevel3Level4 node)
    {
        defaultCase(node);
    }

    public void caseAPlusLevel4(APlusLevel4 node)
    {
        defaultCase(node);
    }

    public void caseAMinusLevel4(AMinusLevel4 node)
    {
        defaultCase(node);
    }

    public void caseALevel2Level3(ALevel2Level3 node)
    {
        defaultCase(node);
    }

    public void caseAMultLevel3(AMultLevel3 node)
    {
        defaultCase(node);
    }

    public void caseADotMultLevel3(ADotMultLevel3 node)
    {
        defaultCase(node);
    }

    public void caseARdivLevel3(ARdivLevel3 node)
    {
        defaultCase(node);
    }

    public void caseADotRdivLevel3(ADotRdivLevel3 node)
    {
        defaultCase(node);
    }

    public void caseALdivLevel3(ALdivLevel3 node)
    {
        defaultCase(node);
    }

    public void caseADotLdivLevel3(ADotLdivLevel3 node)
    {
        defaultCase(node);
    }

    public void caseALevel1Level2(ALevel1Level2 node)
    {
        defaultCase(node);
    }

    public void caseAComplementLevel2(AComplementLevel2 node)
    {
        defaultCase(node);
    }

    public void caseABitComplementLevel2(ABitComplementLevel2 node)
    {
        defaultCase(node);
    }

    public void caseAPlusLevel2(APlusLevel2 node)
    {
        defaultCase(node);
    }

    public void caseAMinusLevel2(AMinusLevel2 node)
    {
        defaultCase(node);
    }

    public void caseALevel0Level1(ALevel0Level1 node)
    {
        defaultCase(node);
    }

    public void caseATransposeLevel1(ATransposeLevel1 node)
    {
        defaultCase(node);
    }

    public void caseADotTransposeLevel1(ADotTransposeLevel1 node)
    {
        defaultCase(node);
    }

    public void caseAPowerLevel1(APowerLevel1 node)
    {
        defaultCase(node);
    }

    public void caseADotPowerLevel1(ADotPowerLevel1 node)
    {
        defaultCase(node);
    }

    public void caseALiteralLevel0(ALiteralLevel0 node)
    {
        defaultCase(node);
    }

    public void caseAMatrixLevel0(AMatrixLevel0 node)
    {
        defaultCase(node);
    }

    public void caseAFunctionLevel0(AFunctionLevel0 node)
    {
        defaultCase(node);
    }

    public void caseAIdentifierLevel0(AIdentifierLevel0 node)
    {
        defaultCase(node);
    }

    public void caseAExpressionLevel0(AExpressionLevel0 node)
    {
        defaultCase(node);
    }

    public void caseAEmptyFunction(AEmptyFunction node)
    {
        defaultCase(node);
    }

    public void caseAParameterFunction(AParameterFunction node)
    {
        defaultCase(node);
    }

    public void caseAExpressionArgumentList(AExpressionArgumentList node)
    {
        defaultCase(node);
    }

    public void caseAArgumentListArgumentList(AArgumentListArgumentList node)
    {
        defaultCase(node);
    }

    public void caseAIntegerLiteral(AIntegerLiteral node)
    {
        defaultCase(node);
    }

    public void caseAFloatingPointLiteral(AFloatingPointLiteral node)
    {
        defaultCase(node);
    }

    public void caseAStringLiteral(AStringLiteral node)
    {
        defaultCase(node);
    }

    public void caseABooleanLiteral(ABooleanLiteral node)
    {
        defaultCase(node);
    }

    public void caseASimpleName(ASimpleName node)
    {
        defaultCase(node);
    }

    public void caseAQualifiedName(AQualifiedName node)
    {
        defaultCase(node);
    }

    public void caseAEmptyMatrix(AEmptyMatrix node)
    {
        defaultCase(node);
    }

    public void caseAValueMatrix(AValueMatrix node)
    {
        defaultCase(node);
    }

    public void caseARowMatrix(ARowMatrix node)
    {
        defaultCase(node);
    }

    public void caseAColumnMatrix(AColumnMatrix node)
    {
        defaultCase(node);
    }

    public void caseAArrayMatrix(AArrayMatrix node)
    {
        defaultCase(node);
    }

    public void caseAArray(AArray node)
    {
        defaultCase(node);
    }

    public void caseAColumn(AColumn node)
    {
        defaultCase(node);
    }

    public void caseARow(ARow node)
    {
        defaultCase(node);
    }

    public void caseACommaValue(ACommaValue node)
    {
        defaultCase(node);
    }

    public void caseASemicolonValue(ASemicolonValue node)
    {
        defaultCase(node);
    }

    public void caseASemicolonRow(ASemicolonRow node)
    {
        defaultCase(node);
    }

    public void caseATrueBoolean(ATrueBoolean node)
    {
        defaultCase(node);
    }

    public void caseAFalseBoolean(AFalseBoolean node)
    {
        defaultCase(node);
    }

    public void caseTWhiteSpace(TWhiteSpace node)
    {
        defaultCase(node);
    }

    public void caseTTraditionalComment(TTraditionalComment node)
    {
        defaultCase(node);
    }

    public void caseTDocumentationComment(TDocumentationComment node)
    {
        defaultCase(node);
    }

    public void caseTEndOfLineComment(TEndOfLineComment node)
    {
        defaultCase(node);
    }

    public void caseTMatlabComment(TMatlabComment node)
    {
        defaultCase(node);
    }

    public void caseTTrue(TTrue node)
    {
        defaultCase(node);
    }

    public void caseTFalse(TFalse node)
    {
        defaultCase(node);
    }

    public void caseTNull(TNull node)
    {
        defaultCase(node);
    }

    public void caseTLParenthese(TLParenthese node)
    {
        defaultCase(node);
    }

    public void caseTRParenthese(TRParenthese node)
    {
        defaultCase(node);
    }

    public void caseTLBrace(TLBrace node)
    {
        defaultCase(node);
    }

    public void caseTRBrace(TRBrace node)
    {
        defaultCase(node);
    }

    public void caseTLBracket(TLBracket node)
    {
        defaultCase(node);
    }

    public void caseTRBracket(TRBracket node)
    {
        defaultCase(node);
    }

    public void caseTSemicolon(TSemicolon node)
    {
        defaultCase(node);
    }

    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    public void caseTDot(TDot node)
    {
        defaultCase(node);
    }

    public void caseTAssign(TAssign node)
    {
        defaultCase(node);
    }

    public void caseTComplement(TComplement node)
    {
        defaultCase(node);
    }

    public void caseTBitComplement(TBitComplement node)
    {
        defaultCase(node);
    }

    public void caseTAnd(TAnd node)
    {
        defaultCase(node);
    }

    public void caseTOr(TOr node)
    {
        defaultCase(node);
    }

    public void caseTLogicalAnd(TLogicalAnd node)
    {
        defaultCase(node);
    }

    public void caseTLogicalOr(TLogicalOr node)
    {
        defaultCase(node);
    }

    public void caseTQuestion(TQuestion node)
    {
        defaultCase(node);
    }

    public void caseTColon(TColon node)
    {
        defaultCase(node);
    }

    public void caseTEq(TEq node)
    {
        defaultCase(node);
    }

    public void caseTLt(TLt node)
    {
        defaultCase(node);
    }

    public void caseTGt(TGt node)
    {
        defaultCase(node);
    }

    public void caseTLteq(TLteq node)
    {
        defaultCase(node);
    }

    public void caseTGteq(TGteq node)
    {
        defaultCase(node);
    }

    public void caseTNeq(TNeq node)
    {
        defaultCase(node);
    }

    public void caseTPlus(TPlus node)
    {
        defaultCase(node);
    }

    public void caseTMinus(TMinus node)
    {
        defaultCase(node);
    }

    public void caseTMult(TMult node)
    {
        defaultCase(node);
    }

    public void caseTDotMult(TDotMult node)
    {
        defaultCase(node);
    }

    public void caseTRdiv(TRdiv node)
    {
        defaultCase(node);
    }

    public void caseTDotRdiv(TDotRdiv node)
    {
        defaultCase(node);
    }

    public void caseTLdiv(TLdiv node)
    {
        defaultCase(node);
    }

    public void caseTDotLdiv(TDotLdiv node)
    {
        defaultCase(node);
    }

    public void caseTTranspose(TTranspose node)
    {
        defaultCase(node);
    }

    public void caseTDotTranspose(TDotTranspose node)
    {
        defaultCase(node);
    }

    public void caseTPower(TPower node)
    {
        defaultCase(node);
    }

    public void caseTDotPower(TDotPower node)
    {
        defaultCase(node);
    }

    public void caseTInteger(TInteger node)
    {
        defaultCase(node);
    }

    public void caseTFloatingPoint(TFloatingPoint node)
    {
        defaultCase(node);
    }

    public void caseTString(TString node)
    {
        defaultCase(node);
    }

    public void caseTIdentifier(TIdentifier node)
    {
        defaultCase(node);
    }

    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    public void defaultCase(@SuppressWarnings("unused") Node node)
    {
        // do nothing
    }
}
