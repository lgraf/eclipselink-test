package org.test.entity.shared;

import java.util.Set;


public interface Parent
{
	Set<? extends Child> getChildren();
}