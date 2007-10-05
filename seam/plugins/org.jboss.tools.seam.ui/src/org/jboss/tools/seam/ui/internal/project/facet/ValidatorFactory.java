/*******************************************************************************
 * Copyright (c) 2007 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.seam.ui.internal.project.facet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.corext.util.Messages;
import org.eclipse.ui.wizards.datatransfer.ZipFileStructureProvider;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.jboss.tools.seam.core.ISeamProject;
import org.jboss.tools.seam.core.SeamCorePlugin;
import org.jboss.tools.seam.internal.core.SeamProject;
import org.jboss.tools.seam.internal.core.project.facet.ISeamFacetDataModelProperties;
import org.jboss.tools.seam.ui.SeamUIMessages;
import org.jboss.tools.seam.ui.wizard.IParameter;

/**
 * 
 * @author eskimo
 * 
 */
public class ValidatorFactory {

	/**
	 * 
	 */
	static public Map<String, IValidator> validators 
											= new HashMap<String, IValidator>();

	/**
	 * 
	 */
	static public final Map<String, String> NO_ERRORS = Collections
			.unmodifiableMap(new HashMap<String, String>());

	/**
	 * 
	 */
	static public IValidator NO_ERRORS_VALIDATOR = new IValidator() {
		public Map<String, String> validate(Object value, Object context) {
			// TODO Auto-generated method stub
			return NO_ERRORS;
		}
	};

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static IValidator getValidator(String id) {
		IValidator validator = validators.get(id);
		return validator == null ? NO_ERRORS_VALIDATOR : validator;
	}

	/**
	 * 
	 * @return
	 */
	public static Map<String, String> createErrorMap() {
		return new HashMap<String, String>();
	}

	/**
	 * 
	 * @param text
	 * @return
	 */
	public static Map<String, String> createErrormessage(String text) {
		Map<String, String> map = createErrorMap();
		map.put(IValidator.DEFAULT_ERROR, text);
		return map;
	}

	/**
	 * 
	 * @param text
	 * @return
	 */
	public static Map<String, String> createErrormessage(String propertyName, 
																 String text) {
		Map<String, String> map = createErrorMap();
		map.put(propertyName, text);
		return map;
	}
	
	/**
	 * 
	 */
	public static final IValidator FILE_SYSTEM_FOLDER_EXISTS = new IValidator() {

		public Map<String, String> validate(Object value, Object context) {
			if (value == null)
				throw new IllegalArgumentException(
						SeamUIMessages.VALIDATOR_FACTORY_PATH_TO_A_FOLDER_CANNOT_BE_NULL);
			String folderPath = value.toString();
			File folder = new File(folderPath);

			if (!folder.exists())
				return createErrormessage(SeamUIMessages.VALIDATOR_FACTORY_FOLDER + folderPath
						+ SeamUIMessages.VALIDATOR_FACTORY_DOES_NOT_EXISTS);
			if (!folder.isDirectory())
				return createErrormessage(SeamUIMessages.VALIDATOR_FACTORY_PATH + folderPath
						+ SeamUIMessages.VALIDATOR_FACTORY_POINTS_TO_FILE);
			return NO_ERRORS;
		}
	};

	/**
	 * 
	 */
	public static IValidator JBOSS_SEAM_HOME_FOLDER_VALIDATOR = new IValidator() {
		public Map<String, String> validate(Object value, Object context) {
			Map<String, String> errors = FILE_SYSTEM_FOLDER_EXISTS.validate(
					value, context);
			if (errors.size() > 0) {
				errors = createErrorMap();
				errors.put(ISeamFacetDataModelProperties.JBOSS_SEAM_HOME,
						SeamUIMessages.VALIDATOR_FACTORY_SEAM_HOME_FOLDER_DOES_NOT_EXISTS);
				return errors;
			}
			File seamJarFile = new File(value.toString(), "jboss-seam.jar"); //$NON-NLS-1$
			if (!seamJarFile.isFile()) {
				errors = createErrorMap();
				errors.put(ISeamFacetDataModelProperties.JBOSS_SEAM_HOME,
					SeamUIMessages.VALIDATOR_FACTORY_HOME_FOLDER_POINTS_TO +
						SeamUIMessages.VALIDATOR_FACTORY_LOCATION_THAT_DOES_NOT_LOOK_LIKE_SEAM_HOME_FOLDER);
			}
			return errors;
		}
	};

	/**
	 * 
	 */
	public static IValidator JBOSS_AS_HOME_FOLDER_VALIDATOR = new IValidator() {
		public Map<String, String> validate(Object value, Object context) {
			Map<String, String> errors = FILE_SYSTEM_FOLDER_EXISTS.validate(
					value, context);
			if (errors.size() > 0) {
				errors = createErrorMap();
				errors.put(ISeamFacetDataModelProperties.JBOSS_AS_HOME,
						SeamUIMessages.VALIDATOR_FACTORY_JBOSS_AS_HOME_FOLDER_DOES_NOT_EXIST);
				return errors;
			}
			if (!new File(value.toString(), "bin/twiddle.jar").isFile()) { //$NON-NLS-1$
				errors.put(
					ISeamFacetDataModelProperties.JBOSS_AS_HOME,
					SeamUIMessages.VALIDATOR_FACTORY_JBOSS_AS_HOME_FOLDER_POINT_TO_LOCATION_THAT_DOES_NOT +
					SeamUIMessages.VALIDATOR_FACTORY_LOOK_LIKE_JBOSS_AS_HOME_FOLDER);
			}
			return errors;
		}
	};

	/**
	 * 
	 */
	public static IValidator CLASS_QNAME_VALIDATOR = new IValidator() {
		public Map<String, String> validate(Object value, Object context) {
			String classDecl = "class " + value.toString() + " {}"; //$NON-NLS-1$ //$NON-NLS-2$
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(classDecl.toCharArray());
			parser.setProject((IJavaProject) context);
			CompilationUnit compilationUnit = (CompilationUnit) parser
					.createAST(null);
			IProblem[] problems = compilationUnit.getProblems();
			if (problems.length > 0) {
				return createErrormessage(Messages.format(
						SeamUIMessages.VALIDATOR_FACTORY_COMPONENT_NAME_IS_NOT_VALID, problems[0]
								.getMessage()));
			}
			return ValidatorFactory.NO_ERRORS;
		}
	};

	/**
	 * 
	 */
	public static IValidator FILESYSTEM_FILE_EXISTS_VALIDATOR = new IValidator() {
		public java.util.Map<String, String> validate(Object value,
				Object context) {
			return ValidatorFactory.NO_ERRORS;
		};
	};
	/**
	 * 
	 * @author eskimo
	 * 
	 */
	public static IValidator SEAM_COMPONENT_NAME_VALIDATOR = new IValidator() {

		public Map<String, String> validate(Object value, Object context) {
			IStatus status = JavaConventions.validateClassFileName(value.toString()+".class", "5.0", "5.0"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			if (!status.isOK()) {
				return createErrormessage(SeamUIMessages.VALIDATOR_FACTORY_NAME_IS_NOT_VALID);
			}

			return NO_ERRORS;
		}
	};

	/**
	 * 
	 * @author eskimo
	 * 
	 */
	public static IValidator SEAM_JAVA_INTEFACE_NAME_CONVENTION_VALIDATOR = new IValidator() {

		public Map<String, String> validate(Object value, Object context) {
			String targetName = null;
			IProject project = null;
			if (context instanceof Object[]) {
				Object[] contextArray = ((Object[]) context);
				targetName = contextArray[0].toString();
				project = (IProject) contextArray[1];
			}
			IJavaProject jProject = JavaCore.create(project);

			String sourceLevel = jProject.getOption(JavaCore.COMPILER_SOURCE,
					true);
			String compliance = jProject.getOption(
					JavaCore.COMPILER_COMPLIANCE, true);
			IStatus status = JavaConventions.validateJavaTypeName(value
					.toString(), sourceLevel, compliance);
			if (status.getSeverity() == IStatus.WARNING) {
				return createErrormessage(SeamUIMessages.VALIDATOR_FACTORY_LOCAL_INTERFACE_NAME_IS_NOT_VALID
						+ status.getMessage());
			}
			return NO_ERRORS;
		}
	};

	public static IValidator SEAM_METHOD_NAME_VALIDATOR = new IValidator() {

		public Map<String, String> validate(Object value, Object context) {
			String targetName = null;
			IProject project = null;

			if (context instanceof Object[]) {
				Object[] contextArray = ((Object[]) context);
				targetName = contextArray[0].toString();
				project = (IProject) contextArray[1];
			}

			CompilationUnit compilationUnit = createCompilationUnit(
					"class ClassName {public void " //$NON-NLS-1$
					+ value.toString() + "() {}}",project); //$NON-NLS-1$
			
			IProblem[] problems = compilationUnit.getProblems();

			if (problems.length > 0) {
				return createErrormessage(targetName + SeamUIMessages.VALIDATOR_FACTORY_NAME_IS_NOT_VALID2);
			}

			return NO_ERRORS;
		}
	};

	public static IValidator FILE_NAME_VALIDATOR = new IValidator() {

		public Map<String, String> validate(Object value, Object context) {
			String targetName = null;
			IProject project = null;

			if (context instanceof Object[]) {
				Object[] contextArray = ((Object[]) context);
				targetName = contextArray[0].toString();
				project = (IProject) contextArray[1];
			}
			if ("".equals(value) //$NON-NLS-1$
					|| !project.getLocation().isValidSegment(value.toString()))
				return createErrormessage(targetName + SeamUIMessages.VALIDATOR_FACTORY_NAME_IS_NOT_VALID2);

			return NO_ERRORS;
		}
	};

	public static IValidator SEAM_PROJECT_NAME_VALIDATOR = new IValidator() {
		public Map<String, String> validate(Object value, Object context) {

			IResource project = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(value.toString());

			if (project == null || !(project instanceof IProject)
					|| !project.exists()) {
				return createErrormessage(
						SeamUIMessages.VALIDATOR_FACTORY_PROJECT + value	+ SeamUIMessages.VALIDATOR_FACTORY_DOES_NOT_EXIST);
			} else {
				IProject selection = (IProject)project;
				try {
					if (!selection.hasNature(SeamProject.NATURE_ID) 
							|| SeamCorePlugin.getSeamPreferences(selection)==null
							|| selection.getAdapter(IFacetedProject.class)==null
							|| !((IFacetedProject)selection.getAdapter(IFacetedProject.class)).hasProjectFacet(ProjectFacetsManager.getProjectFacet("jst.web"))) { //$NON-NLS-1$
						return createErrormessage(
								SeamUIMessages.VALIDATOR_FACTORY_SELECTED_PROJECT + project.getName() + SeamUIMessages.VALIDATOR_FACTORY_IS_NOT_A_SEAM_WEB_PROJECT);
					}
				} catch (CoreException e) {
					SeamCorePlugin.getPluginLog().logError(e);
				}
			}
			return NO_ERRORS;
		}
	};

	public static IValidator CONNECTION_PROFILE_VALIDATOR = 
															new IValidator() {
		public Map<String, String> validate(Object value, Object context) {
			if (value == null || "".equals(value.toString().trim())) { //$NON-NLS-1$
				return createErrormessage(
						ISeamFacetDataModelProperties.SEAM_CONNECTION_PROFILE,
						SeamUIMessages.VALIDATOR_FACTORY_CONNECTION_PROFILE_IS_NOT_SELECTED);
			} else {
				IConnectionProfile connProfile 
					= ProfileManager.getInstance().getProfileByName(value.toString());
				Properties props = connProfile.getBaseProperties();
				Object driverClass 
					= props.get("org.eclipse.datatools.connectivity.db.driverClass"); //$NON-NLS-1$

				if(driverClass==null || "".equals(driverClass)) { //$NON-NLS-1$
					return createErrormessage(
							ISeamFacetDataModelProperties.SEAM_CONNECTION_PROFILE,
							SeamUIMessages.VALIDATOR_FACTORY_DRIVER_CLASS_PROPERTY_IS_EMPTY_FOR_SELECTED 
							+ value + SeamUIMessages.VALIDATOR_FACTORY_CONNECTION_PROFILE);
				}
			}
			return NO_ERRORS;
		}
	};
	
	public static IValidator JBOSS_SEAM_HOME_IS_NOT_SELECTED = new IValidator() {
		public Map<String, String> validate(Object value, Object context) {
			if (value == null || "".equals(value.toString().trim())) { //$NON-NLS-1$
				return createErrormessage(
						ISeamFacetDataModelProperties.SEAM_RUNTIME_NAME,
						SeamUIMessages.VALIDATOR_FACTORY_SEAM_RUNTIME_IS_NOT_SELECTED);
			}
			return NO_ERRORS;
		}
	};
	
	public static CompilationUnit createCompilationUnit(String classDecl, 
															IProject project) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(classDecl.toCharArray());
		parser.setProject(JavaCore.create(project));
		CompilationUnit compilationUnit = (CompilationUnit) parser
				.createAST(null);
		return compilationUnit;
	}
}
