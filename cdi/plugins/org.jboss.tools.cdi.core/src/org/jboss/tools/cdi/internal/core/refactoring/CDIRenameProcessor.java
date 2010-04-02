/*******************************************************************************
  * Copyright (c) 2009 Red Hat, Inc.
  * Distributed under license by Red Hat, Inc. All rights reserved.
  * This program is made available under the terms of the
  * Eclipse Public License v1.0 which accompanies this distribution,
  * and is available at http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributors:
  *     Red Hat, Inc. - initial API and implementation
  ******************************************************************************/
package org.jboss.tools.cdi.internal.core.refactoring;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.ltk.internal.core.refactoring.Messages;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.jboss.tools.cdi.core.CDICoreMessages;
import org.jboss.tools.cdi.core.CDICorePlugin;
import org.jboss.tools.cdi.core.IBean;
import org.jboss.tools.common.el.core.model.ELInvocationExpression;
import org.jboss.tools.common.el.core.model.ELPropertyInvocation;
import org.jboss.tools.common.text.ITextSourceReference;
import org.jboss.tools.common.util.FileUtil;
import org.jboss.tools.jst.web.kb.refactoring.RefactorSearcher;

/**
 * @author Daniel Azarov
 */
public abstract class CDIRenameProcessor extends RenameProcessor {
	protected static final String JAVA_EXT = "java"; //$NON-NLS-1$
	protected static final String XML_EXT = "xml"; //$NON-NLS-1$
	protected static final String XHTML_EXT = "xhtml"; //$NON-NLS-1$
	protected static final String JSP_EXT = "jsp"; //$NON-NLS-1$
	protected static final String PROPERTIES_EXT = "properties"; //$NON-NLS-1$
	
	protected static final RefactoringParticipant[] EMPTY_REF_PARTICIPANT = new  RefactoringParticipant[0];	
	
	protected RefactoringStatus status;
	
	protected CompositeChange rootChange;
	protected TextFileChange lastChange;
	protected IFile declarationFile=null;
	
	private String newName;
	private String oldName;
	
	private CDISearcher searcher = null;
	protected IBean bean;
	
	protected CDISearcher getSearcher(){
		if(searcher == null){
			searcher = new CDISearcher(declarationFile, getOldName());
		}
		return searcher;
	}
	
	public void setNewName(String newName){
		this.newName = newName;
	}
	
	protected String getNewName(){
		return newName;
	}
	
	protected void setOldName(String oldName){
		this.oldName = oldName;
	}
	
	public String getOldName(){
		return oldName;
	}
	
	// lets collect all changes for the same files in one MultiTextEdit
	protected TextFileChange getChange(IFile file){
		if(lastChange != null && lastChange.getFile().equals(file))
			return lastChange;
		
		for(int i=0; i < rootChange.getChildren().length; i++){
			TextFileChange change = (TextFileChange)rootChange.getChildren()[i];
			if(change.getFile().equals(file)){
				lastChange = change;
				return lastChange;
			}
		}
		lastChange = new TextFileChange(file.getName(), file);
		MultiTextEdit root = new MultiTextEdit();
		lastChange.setEdit(root);
		rootChange.add(lastChange);
		
		return lastChange;
	}
	
	protected void findDeclarations(IBean bean) throws CoreException{
		changeDeclarations(bean);
	}
	
	
	private boolean isBadLocation(ITextSourceReference location, IFile file){
		boolean flag=false;
		return flag;
	}
	
	private void changeXMLNode(ITextSourceReference location, IFile file){
		if(isBadLocation(location, file))
			return;
		
		if(!isFileCorrect(file))
			return;

		String content = null;
		try {
			content = FileUtil.readStream(file);
		} catch (CoreException e) {
			CDICorePlugin.getDefault().logError(e);
		}

		String text = content.substring(location.getStartPosition(), location.getStartPosition()+location.getLength());
		if(text.startsWith("<")){ //$NON-NLS-1$
			int position = text.lastIndexOf("/>"); //$NON-NLS-1$
			if(position < 0){
				position = text.lastIndexOf(">"); //$NON-NLS-1$
			}
			change(file, location.getStartPosition()+position, 0, " name=\""+getNewName()+"\""); //$NON-NLS-1$ //$NON-NLS-2$
		}else{
			change(file, location.getStartPosition(), location.getLength(), getNewName());
		}
	}
	
	private void changeAnnotation(ITextSourceReference location, IFile file){
		if(isBadLocation(location, file))
			return;
		
		if(!isFileCorrect(file))
			return;

		String content = null;
		try {
			content = FileUtil.readStream(file);
		} catch (CoreException e) {
			CDICorePlugin.getDefault().logError(e);
		}

		String text = content.substring(location.getStartPosition(), location.getStartPosition()+location.getLength());
		int openBracket = text.indexOf("("); //$NON-NLS-1$
		int openQuote = text.indexOf("\""); //$NON-NLS-1$
		if(openBracket >= 0){
			int closeBracket = text.indexOf(")", openBracket); //$NON-NLS-1$
			
			int equals = text.indexOf("=", openBracket); //$NON-NLS-1$
			int value = text.indexOf("value", openBracket); //$NON-NLS-1$
			
			if(closeBracket == openBracket+1){ // empty brackets
				String newText = "\""+getNewName()+"\""; //$NON-NLS-1$ //$NON-NLS-2$
				change(file, location.getStartPosition()+openBracket+1, 0, newText);
			}else if(value > 0){ // construction value="name" found so change name
				String newText = text.replace(getOldName(), getNewName());
				change(file, location.getStartPosition(), location.getLength(), newText);
			}else if(equals > 0){ // other parameters are found
				String newText = "value=\""+getNewName()+"\","; //$NON-NLS-1$ //$NON-NLS-2$
				change(file, location.getStartPosition()+openBracket+1, 0, newText);
			}else{ // other cases
				String newText = text.replace(getOldName(), getNewName());
				change(file, location.getStartPosition(), location.getLength(), newText);
			}
		}else if(openQuote >= 0){
			int closeQuota = text.indexOf("\"", openQuote); //$NON-NLS-1$
			
			if(closeQuota == openQuote+1){ // empty quotas
				String newText = "\""+getNewName()+"\""; //$NON-NLS-1$ //$NON-NLS-2$
				change(file, location.getStartPosition()+openQuote+1, 0, newText);
			}else{ // the other cases
				String newText = text.replace(getOldName(), getNewName());
				change(file, location.getStartPosition(), location.getLength(), newText);
			}
		}else{
			String newText = "(\""+getNewName()+"\")"; //$NON-NLS-1$ //$NON-NLS-2$
			change(file, location.getStartPosition()+location.getLength(), 0, newText);
		}
	}

	
	private void changeDeclarations(IBean bean) throws CoreException{
	}
	
	protected void checkDeclarations(IBean bean) throws CoreException{
	}
	
	protected boolean isFileCorrect(IFile file){
			if(!file.isSynchronized(IResource.DEPTH_ZERO)){
				status.addFatalError(Messages.format(CDICoreMessages.CDI_RENAME_PROCESSOR_OUT_OF_SYNC_FILE, file.getFullPath().toString()));
				return false;
			}else if(file.isPhantom()){
				status.addFatalError(Messages.format(CDICoreMessages.CDI_RENAME_PROCESSOR_ERROR_PHANTOM_FILE, file.getFullPath().toString()));
				return false;
			}else if(file.isReadOnly()){
				status.addFatalError(Messages.format(CDICoreMessages.CDI_RENAME_PROCESSOR_ERROR_READ_ONLY_FILE, file.getFullPath().toString()));
				return false;
			}
			return true;
	}
	
	
	protected void renameBean(IProgressMonitor pm, IBean bean)throws CoreException{
		pm.beginTask("", 3);
		
		clearChanges();
		
		findDeclarations(bean);
		
		if(status.hasFatalError())
			return;
		
		pm.worked(1);
		
		getSearcher().findELReferences();
		
		pm.done();
	}
	
	ArrayList<String> keys = new ArrayList<String>();
	
	private void clearChanges(){
		keys.clear();
	}
	
	private void change(IFile file, int offset, int length, String text){
		//System.out.println("change file - "+file.getFullPath()+" offset - "+offset+" len - "+length+" text"+text);
		String key = file.getFullPath().toString()+" "+offset;
		if(!keys.contains(key)){
			TextFileChange change = getChange(file);
			TextEdit edit = new ReplaceEdit(offset, length, text);
			change.addEdit(edit);
			keys.add(key);
		}
	}
	
	class CDISearcher extends RefactorSearcher{
		public CDISearcher(IFile declarationFile, String oldName){
			super(declarationFile, oldName);
		}

		@Override
		protected boolean isFileCorrect(IFile file) {
			return CDIRenameProcessor.this.isFileCorrect(file);
		}

		@Override
		protected void match(IFile file, int offset, int length) {
			change(file, offset, length, newName);
		}
		
		protected ELInvocationExpression findComponentReference(ELInvocationExpression invocationExpression){
			if(bean != null)
				return invocationExpression;
			
			ELInvocationExpression invExp = invocationExpression;
			while(invExp != null){
				if(invExp instanceof ELPropertyInvocation){
					if(((ELPropertyInvocation)invExp).getQualifiedName() != null && ((ELPropertyInvocation)invExp).getQualifiedName().equals(propertyName))
						return invExp;
					else
						invExp = invExp.getLeft();
					
				}else{
					invExp = invExp.getLeft();
				}
			}
			return null;
		}

		@Override
		protected IProject[] getProjects() {
			return null;
		}

		@Override
		protected IContainer getViewFolder(IProject project) {
			return null;
		}
	}
}