package org.jdmp.core.sample;

import java.util.EventObject;


public class SampleEvent extends EventObject {
	private static final long serialVersionUID = 8931004980934374906L;

	public SampleEvent(Sample source) {
		super(source);
	}
}
