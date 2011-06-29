package org.jboss.tools.cdi.core;

import java.util.Set;

import org.eclipse.jdt.core.IMethod;
import org.jboss.tools.common.java.IAnnotationDeclaration;
import org.jboss.tools.common.java.IAnnotationType;

/**
 * Common interface for an annotation interface.
 * 
 * @author Viacheslav Kabanovich
 * 
 */
public interface ICDIAnnotation extends ICDIElement, IAnnotationType {

	/**
	 * Returns the declaration of @Inherited declaration of this annotation
	 * type. If the interface doesn't have the @Inherited declaration then null
	 * will be returned.
	 * 
	 * @return the declaration of @Inherited declaration of this bean
	 */
	IAnnotationDeclaration getInheritedDeclaration();

	/**
	 * Returns set of members annotated with @Nonbinding
	 * 
	 * @return set of members annotated with @Nonbinding
	 */
	Set<IMethod> getNonBindingMethods();
}