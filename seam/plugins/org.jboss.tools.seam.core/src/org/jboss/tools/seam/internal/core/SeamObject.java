/******************************************************************************* 
 * Copyright (c) 2007 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.tools.seam.internal.core;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.jboss.tools.seam.core.ISeamElement;
import org.jboss.tools.seam.core.ISeamProject;
import org.jboss.tools.seam.core.event.Change;

/**
 * @author Viacheslav Kabanovich
 */
public class SeamObject implements ISeamElement {
	/**
	 * Object that allows to identify this object.
	 */
	protected Object id;

	/**
	 * Path of resource where this object is declared.
	 */
	protected IPath source;

	/**
	 * Resource where this object is declared.
	 */
	protected IResource resource = null;

	/**
	 * Parent seam object in the seam model.
	 */
	protected ISeamElement parent;
	
	public SeamObject() {}

	public ISeamProject getSeamProject() {
		return parent == null ? null : parent.getSeamProject();
	}

	public Object getId() {
		return id;
	}
	
	/**
	 * Sets id for this object.
	 * For most objects it is object of Java or XML model 
	 * from which this object is loaded.
	 */
	public void setId(Object id) {
		this.id = id;
	}

	/**
	 * Sets path of resource that declares this object.
	 */
	public void setSourcePath(IPath path) {
		source = path;
	}
	
	/**
	 * Returns path of resource that declares this object.
	 * @return
	 */
	public IPath getSourcePath() {
		if(source == null && parent != null) return parent.getSourcePath();
		return source;
	}

	public IResource getResource() {
		if(resource != null) return resource;
		if(source != null) {
			resource = ResourcesPlugin.getWorkspace().getRoot().getFile(source);
		}
		if(resource == null && parent != null) {
			return parent.getResource();
		}
		return resource;
	}

	/**
	 * Returns parent object of seam model.
	 * @return
	 */
	public ISeamElement getParent() {
		return parent;
	}
	
	public void setParent(ISeamElement parent) {
		this.parent = parent;
	}
	
	protected void adopt(ISeamElement child) {
		if(child.getSeamProject() != null && child.getSeamProject() != getSeamProject()) return;
		((SeamObject)child).setParent(this);
	}

	
	/**
	 * Merges loaded object into current object.
	 * If changes were done returns a list of changes.
	 * If there are no changes, null is returned, 
	 * which prevents creating a lot of unnecessary objects. 
	 * @param f
	 * @return list of changes
	 */
	public List<Change> merge(SeamObject s) {
		source = s.source;
		id = s.id;
		resource = s.resource;
		//If there are no changes, null is returned, 
		//which prevents creating a lot of unnecessary objects.
		//Subclasses and clients must check returned 
		//value for null, before using it.		
		return null;
	}
	
	public SeamObject clone() throws CloneNotSupportedException {
		SeamObject c = (SeamObject)super.clone();
		c.parent = null;
		//do not copy parent
		return c;
	}

	protected static ISeamElement doClone(ISeamElement object) throws CloneNotSupportedException {
		if(!(object instanceof SeamObject)) {
			throw new CloneNotSupportedException("Only instance of SeamObject can be cloned: " + object);
		}
		return ((SeamObject)object).clone();
	}

}
