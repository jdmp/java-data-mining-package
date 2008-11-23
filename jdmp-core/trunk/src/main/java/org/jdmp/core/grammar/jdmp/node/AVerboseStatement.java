/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.grammar.jdmp.node;

import org.jdmp.core.grammar.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class AVerboseStatement extends PStatement
{
    private PExpression _expression_;
    private TSemicolonToken _semicolonToken_;

    public AVerboseStatement()
    {
        // Constructor
    }

    public AVerboseStatement(
        @SuppressWarnings("hiding") PExpression _expression_,
        @SuppressWarnings("hiding") TSemicolonToken _semicolonToken_)
    {
        // Constructor
        setExpression(_expression_);

        setSemicolonToken(_semicolonToken_);

    }

    @Override
    public Object clone()
    {
        return new AVerboseStatement(
            cloneNode(this._expression_),
            cloneNode(this._semicolonToken_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAVerboseStatement(this);
    }

    public PExpression getExpression()
    {
        return this._expression_;
    }

    public void setExpression(PExpression node)
    {
        if(this._expression_ != null)
        {
            this._expression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expression_ = node;
    }

    public TSemicolonToken getSemicolonToken()
    {
        return this._semicolonToken_;
    }

    public void setSemicolonToken(TSemicolonToken node)
    {
        if(this._semicolonToken_ != null)
        {
            this._semicolonToken_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semicolonToken_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._expression_)
            + toString(this._semicolonToken_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._expression_ == child)
        {
            this._expression_ = null;
            return;
        }

        if(this._semicolonToken_ == child)
        {
            this._semicolonToken_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
        }

        if(this._semicolonToken_ == oldChild)
        {
            setSemicolonToken((TSemicolonToken) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}