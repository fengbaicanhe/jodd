// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.petite.data;

import jodd.petite.meta.PetiteBean;
import jodd.petite.tst.Foo;

@PetiteBean("biz")
public class DefaultBiz implements Biz {

	Foo foo;

	public Foo getFoo() {
		return foo;
	}

	public int initCount;

	public void init() {
		initCount++;
	}

	public void init2() {
		initCount++;
	}

	public void calculate() {
		System.out.println("DefaultBizImpl.calculate");
	}
}
