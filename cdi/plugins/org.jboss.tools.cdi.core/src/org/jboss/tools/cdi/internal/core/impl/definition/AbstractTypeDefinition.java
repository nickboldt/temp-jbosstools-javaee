package org.jboss.tools.cdi.internal.core.impl.definition;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IType;
import org.jboss.tools.common.model.util.EclipseJavaUtil;

public class AbstractTypeDefinition extends AbstractMemberDefinition {
	protected String qualifiedName;
	protected IType type;
	protected Set<IType> inheritedTypes = new HashSet<IType>();

	public AbstractTypeDefinition() {}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public IType getType() {
		return type;
	}

	public void setType(IType type, DefinitionContext context) {
		super.setAnnotatable(type, type,context);
	}

	@Override
	protected void init(IType contextType, DefinitionContext context) throws CoreException {
		this.type = contextType;
		super.init(contextType, context);
		qualifiedName = getType().getFullyQualifiedName();
		String sc = type.getSuperclassTypeSignature();
		if(sc != null) {
			sc = EclipseJavaUtil.resolveType(contextType, sc);
			if(sc != null && sc.length() > 0) {
				IType t = EclipseJavaUtil.findType(contextType.getJavaProject(), sc);
				if(t != null) inheritedTypes.add(t);
			}
		}
		String[] is = type.getSuperInterfaceTypeSignatures();
		if(is != null) for (int i = 0; i < is.length; i++) {
			String c = EclipseJavaUtil.resolveType(contextType, is[i]);
			if(c != null && c.length() > 0) {
				IType t = EclipseJavaUtil.findType(contextType.getJavaProject(), c);
				if(t != null) inheritedTypes.add(t);
			}
		}
	}

	public Set<IType> getInheritedTypes() {
		return inheritedTypes;
	}

}
