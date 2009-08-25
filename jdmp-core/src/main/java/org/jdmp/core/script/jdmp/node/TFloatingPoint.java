/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.node;

import org.jdmp.core.script.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class TFloatingPoint extends Token
{
    public TFloatingPoint(String text)
    {
        setText(text);
    }

    public TFloatingPoint(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    
    public Object clone()
    {
      return new TFloatingPoint(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTFloatingPoint(this);
    }
}