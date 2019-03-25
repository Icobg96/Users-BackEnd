package com.user.entity;

import java.io.Serializable;

public interface Identifiable <T extends Serializable > {
	public T getId();
}
