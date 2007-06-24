/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.jsf.model.handlers;

import java.util.*;
import org.jboss.tools.common.meta.action.impl.AbstractHandler;
import org.jboss.tools.common.meta.key.WizardKeys;
import org.jboss.tools.common.model.*;
import org.jboss.tools.jsf.messages.JSFUIMessages;

public class ChangeValueKindHandler extends AbstractHandler {
	public static String MESSAGE_KEY = "JSFManagedProperty_ChangeValueKind";
	
	public static boolean isNewValueKind(XModelObject object, String kind) {
		String objectKind = object.getAttributeValue("value-kind");
		return objectKind != null && kind != null && !objectKind.equals(kind);
	}

	public static boolean checkChangeSignificance(XModelObject object) {
		String kind = object.getAttributeValue("value-kind");
		if("null-value".equals(kind)) return true;
		if("value".equals(kind)) {
			if(object.getAttributeValue("value").length() == 0) return true;
		} if("map-entries".equals(kind) || "list-entries".equals(kind)) {
			XModelObject c = object.getChildByPath("Entries");
			if(c == null) return true;
			//check attributes
			if(c.getChildren().length == 0) return true;  
		}
		return openConfirmation(object.getModel());
	}

	public static boolean openConfirmation(XModel model) {
		String message = "" + WizardKeys.getMessage(MESSAGE_KEY);
		ServiceDialog d = model.getService();
		int q = d.showDialog(JSFUIMessages.CONFIRMATION, message, new String[]{JSFUIMessages.OK, JSFUIMessages.CANCEL}, null, ServiceDialog.QUESTION);
		return q == 0;
	}
	
	public boolean isEnabled(XModelObject object) {
		return object != null && object.isObjectEditable() && isNewValueKind(object, action.getProperty("value-kind"));
	}
	
	public void executeHandler(XModelObject object, Properties p) throws Exception {
		if(!isEnabled(object)) return;
		if(!checkChangeSignificance(object)) return;
		String targetValueKind = action.getProperty("value-kind");
		object.getModel().changeObjectAttribute(object, "value-kind", targetValueKind);
	}	
	
}
