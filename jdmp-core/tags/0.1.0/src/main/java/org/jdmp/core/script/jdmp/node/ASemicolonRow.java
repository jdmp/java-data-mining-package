/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.node;

import org.jdmp.core.script.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class ASemicolonRow extends PSemicolonRow
{
    private TSemicolon _semicolon_;
    private PRow _row_;

    public ASemicolonRow()
    {
        // Constructor
    }

    public ASemicolonRow(
        @SuppressWarnings("hiding") TSemicolon _semicolon_,
        @SuppressWarnings("hiding") PRow _row_)
    {
        // Constructor
        setSemicolon(_semicolon_);

        setRow(_row_);

    }

    @Override
    public Object clone()
    {
        return new ASemicolonRow(
            cloneNode(this._semicolon_),
            cloneNode(this._row_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASemicolonRow(this);
    }

    public TSemicolon getSemicolon()
    {
        return this._semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if(this._semicolon_ != null)
        {
            this._semicolon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semicolon_ = node;
    }

    public PRow getRow()
    {
        return this._row_;
    }

    public void setRow(PRow node)
    {
        if(this._row_ != null)
        {
            this._row_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._row_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._semicolon_)
            + toString(this._row_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._semicolon_ == child)
        {
            this._semicolon_ = null;
            return;
        }

        if(this._row_ == child)
        {
            this._row_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

        if(this._row_ == oldChild)
        {
            setRow((PRow) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}