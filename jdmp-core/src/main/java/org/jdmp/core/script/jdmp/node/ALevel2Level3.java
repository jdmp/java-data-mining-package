/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.node;

import org.jdmp.core.script.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class ALevel2Level3 extends PLevel3
{
    private PLevel2 _level2_;

    public ALevel2Level3()
    {
        // Constructor
    }

    public ALevel2Level3(
        @SuppressWarnings("hiding") PLevel2 _level2_)
    {
        // Constructor
        setLevel2(_level2_);

    }

    
    public Object clone()
    {
        return new ALevel2Level3(
            cloneNode(this._level2_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALevel2Level3(this);
    }

    public PLevel2 getLevel2()
    {
        return this._level2_;
    }

    public void setLevel2(PLevel2 node)
    {
        if(this._level2_ != null)
        {
            this._level2_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._level2_ = node;
    }

    
    public String toString()
    {
        return ""
            + toString(this._level2_);
    }

    
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._level2_ == child)
        {
            this._level2_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._level2_ == oldChild)
        {
            setLevel2((PLevel2) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}