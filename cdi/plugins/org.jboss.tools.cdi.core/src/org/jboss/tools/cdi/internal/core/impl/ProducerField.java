package org.jboss.tools.cdi.internal.core.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.jboss.tools.cdi.core.CDIConstants;
import org.jboss.tools.cdi.core.CDICoreNature;
import org.jboss.tools.cdi.core.CDICorePlugin;
import org.jboss.tools.cdi.core.IAnnotationDeclaration;
import org.jboss.tools.cdi.core.IBean;
import org.jboss.tools.cdi.core.IInjectionPoint;
import org.jboss.tools.cdi.core.IProducerField;
import org.jboss.tools.cdi.core.IStereotypeDeclaration;
import org.jboss.tools.cdi.core.ITypeDeclaration;
import org.jboss.tools.cdi.internal.core.impl.definition.AnnotationDefinition;
import org.jboss.tools.common.java.IJavaSourceReference;
import org.jboss.tools.common.model.util.EclipseJavaUtil;
import org.jboss.tools.common.text.ITextSourceReference;

public class ProducerField extends BeanField implements IProducerField {

	public ProducerField() {}

	public Set<ITypeDeclaration> getAllTypeDeclarations() {
		Set<ITypeDeclaration> result = new HashSet<ITypeDeclaration>();
		if(typeDeclaration != null) {
			result.add(typeDeclaration);
		}
		return result;
	}

	public IAnnotationDeclaration getAlternativeDeclaration() {
		return alternative;
	}

	public IType getBeanClass() {
		return typeDeclaration != null ? typeDeclaration.getType() : null;
	}

	public Set<IInjectionPoint> getInjectionPoints() {
		return new HashSet<IInjectionPoint>();
	}

	public Set<IType> getLegalTypes() {
		// TODO 
		return null;
	}

	public String getName() {
		String name = getField().getElementName();
		if(named == null) {
			return name;
		}
		IAnnotation a = named.getDeclaration();
		try {
			IMemberValuePair[] vs = a.getMemberValuePairs();
			if(vs == null || vs.length == 0) {
				return name;
			} else {
				Object value = vs[0].getValue();
				if(value != null && value.toString().trim().length() > 0) {
					return value.toString().trim();
				}
			}
		} catch (JavaModelException e) {
			CDICorePlugin.getDefault().logError(e);
		}
		return name;
	}

	public ITextSourceReference getNameLocation() {
		final IField f = getField();
		return new IJavaSourceReference(){
		
			public int getStartPosition() {
				try {
					ISourceRange r = f.getSourceRange();
					return r == null ? -1 : r.getOffset();
				} catch (JavaModelException e) {
					CDICorePlugin.getDefault().logError(e);
					return -1;
				}
			}
		
			public int getLength() {
				try {
					ISourceRange r = f.getSourceRange();
					return r == null ? 0 : r.getLength();
				} catch (JavaModelException e) {
					CDICorePlugin.getDefault().logError(e);
					return 0;
				}
			}
		
			public IMember getSourceMember() {
				return f;
			}
		};
	}

	public Set<IAnnotationDeclaration> getQualifierDeclarations() {
		Set<IAnnotationDeclaration> result = new HashSet<IAnnotationDeclaration>();
		for(AnnotationDeclaration a: definition.getAnnotations()) {
			int k = getCDIProject().getNature().getDefinitions().getAnnotationKind(a.getType());
			if(k == AnnotationDefinition.QUALIFIER) {
				result.add(a);
			}
		}
		return result;
	}

	public Set<ITypeDeclaration> getRestrictedTypeDeclaratios() {
		Set<ITypeDeclaration> result = new HashSet<ITypeDeclaration>();
		if(typed != null) {
			IAnnotation a = typed.getDeclaration();
			try {
				IMemberValuePair[] ps = a.getMemberValuePairs();
				if(ps == null || ps.length == 0) return result;
				Object value = ps[0].getValue();
				if(value instanceof Object[]) {
					Object[] os = (Object[])value;
					for (int i = 0; i < os.length; i++) {
						String typeName = os[i].toString();
						IType type = EclipseJavaUtil.findType(getField().getJavaProject(), typeName);
						if(type != null) {
							result.add(new TypeDeclaration(type, -1, 0));
						}
					}
				} else if(value != null) {
					String typeName = value.toString();
					IType type = EclipseJavaUtil.findType(getField().getJavaProject(), typeName);
					if(type != null) {
						result.add(new TypeDeclaration(type, -1, 0));
					}
				}
			} catch (JavaModelException e) {
				CDICorePlugin.getDefault().logError(e);
			}
		}
		return result;
	}

	public IBean getSpecializedBean() {
		return null;
	}

	public IAnnotationDeclaration getSpecializesAnnotationDeclaration() {
		return null;
	}

	public Set<IStereotypeDeclaration> getStereotypeDeclarations() {
		Set<IStereotypeDeclaration> result = new HashSet<IStereotypeDeclaration>();
		for (AnnotationDeclaration d: definition.getAnnotations()) {
			if(d instanceof IStereotypeDeclaration) {
				StereotypeDeclaration sd = (StereotypeDeclaration)d;
				StereotypeElement s = getCDIProject().getStereotype(d.getTypeName());
				sd.setStereotype(s);
				result.add(sd);
			}
		}
		return result;
	}

	public boolean isAlternative() {
		return alternative != null;
	}

	public boolean isDependent() {
		IType scope = getScope();
		return scope != null && CDIConstants.DEPENDENT_ANNOTATION_TYPE_NAME.equals(scope.getFullyQualifiedName());
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isSpecializing() {
		return false;
	}

	public IType getScope() {
		Set<IAnnotationDeclaration> ds = getScopeDeclarations();
		if(!ds.isEmpty()) {
			return ds.iterator().next().getType();
		}
		List<AnnotationDeclaration> ds2 = definition.getAnnotations();
		for (AnnotationDeclaration d: ds2) {
			int k = getCDIProject().getNature().getDefinitions().getAnnotationKind(d.getType());
			if(k == AnnotationDefinition.STEREOTYPE) {
				AnnotationDefinition a = getCDIProject().getNature().getDefinitions().getAnnotation(d.getType());
				ds = getScopeDeclarations(getCDIProject().getNature(), a.getAnnotations());
				if(!ds.isEmpty()) {
					return ds.iterator().next().getType();
				}
			}
		}
		try {
			return EclipseJavaUtil.findType(getField().getJavaProject(), CDIConstants.DEPENDENT_ANNOTATION_TYPE_NAME);
		} catch (JavaModelException e) {
			CDICorePlugin.getDefault().logError(e);
		}
		return null;
	}

	public Set<IAnnotationDeclaration> getScopeDeclarations() {
		return getScopeDeclarations(getCDIProject().getNature(), definition.getAnnotations());
	}

	public static Set<IAnnotationDeclaration> getScopeDeclarations(CDICoreNature n, List<? extends IAnnotationDeclaration> ds) {
		Set<IAnnotationDeclaration> result = new HashSet<IAnnotationDeclaration>();
		for (IAnnotationDeclaration d: ds) {
			int k = n.getDefinitions().getAnnotationKind(d.getType());
			if(k == AnnotationDefinition.SCOPE) {
				result.add(d);
			}
		}
		return result;
	}

	public static Set<IAnnotationDeclaration> getStereotypeDeclarations(CDICoreNature n, List<? extends IAnnotationDeclaration> ds) {
		Set<IAnnotationDeclaration> result = new HashSet<IAnnotationDeclaration>();
		for (IAnnotationDeclaration d: ds) {
			int k = n.getDefinitions().getAnnotationKind(d.getType());
			if(k == AnnotationDefinition.STEREOTYPE) {
				result.add(d);
			}
		}
		return result;
	}

}
