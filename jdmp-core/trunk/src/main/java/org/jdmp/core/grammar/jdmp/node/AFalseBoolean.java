/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.grammar.jdmp.node;

import org.jdmp.core.grammar.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class AFalseBoolean extends PBoolean
{
    private PFalse _false_;

    public AFalseBoolean()
    {
        // Constructor
    }

    public AFalseBoolean(
        @SuppressWarnings("hiding") PFalse _false_)
    {
        // Constructor
        setFalse(_false_);

    }

    @Override
    public Object clone()
    {
        return new AFalseBoolean(
            cloneNode(this._false_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFalseBoolean(this);
    }

    public PFalse getFalse()
    {
        return this._false_;
    }

    public void setFalse(PFalse node)
    {
        if(this._false_ != null)
        {
            this._false_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._false_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._false_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._false_ == child)
        {
            this._false_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._false_ == oldChild)
        {
            setFalse((PFalse) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}