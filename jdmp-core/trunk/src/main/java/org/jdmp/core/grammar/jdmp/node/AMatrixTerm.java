/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.grammar.jdmp.node;

import org.jdmp.core.grammar.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class AMatrixTerm extends PTerm
{
    private PMatrix _matrix_;

    public AMatrixTerm()
    {
        // Constructor
    }

    public AMatrixTerm(
        @SuppressWarnings("hiding") PMatrix _matrix_)
    {
        // Constructor
        setMatrix(_matrix_);

    }

    @Override
    public Object clone()
    {
        return new AMatrixTerm(
            cloneNode(this._matrix_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMatrixTerm(this);
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._matrix_);
    }

    @Override
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

    @Override
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