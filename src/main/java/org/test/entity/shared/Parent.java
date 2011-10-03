package org.test.entity.shared;

import java.util.Collection;


public interface Parent
{
	Collection<? extends Child> getChildren();
}