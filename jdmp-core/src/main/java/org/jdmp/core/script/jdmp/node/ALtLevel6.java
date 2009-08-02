/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.node;

import org.jdmp.core.script.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class ALtLevel6 extends PLevel6
{
    private PLevel6 _left_;
    private TLt _lt_;
    private PLevel5 _right_;

    public ALtLevel6()
    {
        // Constructor
    }

    public ALtLevel6(
        @SuppressWarnings("hiding") PLevel6 _left_,
        @SuppressWarnings("hiding") TLt _lt_,
        @SuppressWarnings("hiding") PLevel5 _right_)
    {
        // Constructor
        setLeft(_left_);

        setLt(_lt_);

        setRight(_right_);

    }

    @Override
    public Object clone()
    {
        return new ALtLevel6(
            cloneNode(this._left_),
            cloneNode(this._lt_),
            cloneNode(this._right_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALtLevel6(this);
    }

    public PLevel6 getLeft()
    {
        return this._left_;
    }

    public void setLeft(PLevel6 node)
    {
        if(this._left_ != null)
        {
            this._left_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._left_ = node;
    }

    public TLt getLt()
    {
        return this._lt_;
    }

    public void setLt(TLt node)
    {
        if(this._lt_ != null)
        {
            this._lt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lt_ = node;
    }

    public PLevel5 getRight()
    {
        return this._right_;
    }

    public void setRight(PLevel5 node)
    {
        if(this._right_ != null)
        {
            this._right_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._right_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._left_)
            + toString(this._lt_)
            + toString(this._right_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._left_ == child)
        {
            this._left_ = null;
            return;
        }

        if(this._lt_ == child)
        {
            this._lt_ = null;
            return;
        }

        if(this._right_ == child)
        {
            this._right_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._left_ == oldChild)
        {
            setLeft((PLevel6) newChild);
            return;
        }

        if(this._lt_ == oldChild)
        {
            setLt((TLt) newChild);
            return;
        }

        if(this._right_ == oldChild)
        {
            setRight((PLevel5) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}