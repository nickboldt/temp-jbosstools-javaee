 /*******************************************************************************
  * Copyright (c) 2008 Red Hat, Inc.
  * Distributed under license by Red Hat, Inc. All rights reserved.
  * This program is made available under the terms of the
  * Eclipse Public License v1.0 which accompanies this distribution,
  * and is available at http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributors:
  *     Red Hat, Inc. - initial API and implementation
  ******************************************************************************/
package org.jboss.tools.seam.ui.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jboss.tools.seam.core.ISeamComponent;
import org.jboss.tools.seam.internal.core.refactoring.RenameComponentProcessor;
import org.jboss.tools.seam.ui.widget.editor.IFieldEditor;
import org.jboss.tools.seam.ui.widget.editor.IFieldEditorFactory;

/**
 * @author Alexey Kazakov
 */
public class RenameComponentWizard extends RefactoringWizard {

	private ISeamComponent component;
	private String componentName;
	private IFieldEditor editor;

	public RenameComponentWizard(Refactoring refactoring, ISeamComponent component) {
		super(refactoring, WIZARD_BASED_USER_INTERFACE);
		this.component = component;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
	 */
	@Override
	protected void addUserInputPages() {
	    setDefaultPageTitle(getRefactoring().getName());
	    RenameComponentProcessor processor= (RenameComponentProcessor) getRefactoring().getAdapter(RenameComponentProcessor.class);
	    addPage(new RenameComponentWizardPage(processor));
	}
	
	class RenameComponentWizardPage extends UserInputWizardPage{
		private RenameComponentProcessor processor;
		
		public RenameComponentWizardPage(RenameComponentProcessor processor){
			super("");
			this.processor = processor;
		}

		public void createControl(Composite parent) {
			Composite container = new Composite(parent, SWT.NULL);
			container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	        GridLayout layout = new GridLayout();
	        container.setLayout(layout);
	        layout.numColumns = 2;
	        
	        String defaultName = component.getName();
	        editor = IFieldEditorFactory.INSTANCE.createTextEditor(componentName, "Seam component name:", defaultName);
	        editor.doFillIntoGrid(container);

	        setControl(container);
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#performFinish()
		 */
		protected boolean performFinish() {
			initializeRefactoring();
			return super.performFinish();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#getNextPage()
		 */
		public IWizardPage getNextPage() {
			initializeRefactoring();
			return super.getNextPage();
		}
		
		private void initializeRefactoring() {
			processor.setNewComponentName(editor.getValueAsString());
		}
		
	}
}