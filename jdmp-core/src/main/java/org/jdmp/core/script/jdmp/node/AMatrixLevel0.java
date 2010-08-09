/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.node;

import org.jdmp.core.script.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class AMatrixLevel0 extends PLevel0
{
    private PMatrix _matrix_;

    public AMatrixLevel0()
    {
        // Constructor
    }

    public AMatrixLevel0(
        @SuppressWarnings("hiding") PMatrix _matrix_)
    {
        // Constructor
        setMatrix(_matrix_);

    }

    
    public Object clone()
    {
        return new AMatrixLevel0(
            cloneNode(this._matrix_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMatrixLevel0(this);
    }

    public PMatrix getMatrix()
    {
        return this._matrix_;
    }

    public void setMatrix(PMatrix node)
    {
        if(this._matrix_ != null)
        {
            this._matrix_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._matrix_ = node;
    }

    
    public String toString()
    {
        return ""
            + toString(this._matrix_);
    }

    
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._matrix_ == child)
        {
            this._matrix_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._matrix_ == oldChild)
        {
            setMatrix((PMatrix) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}