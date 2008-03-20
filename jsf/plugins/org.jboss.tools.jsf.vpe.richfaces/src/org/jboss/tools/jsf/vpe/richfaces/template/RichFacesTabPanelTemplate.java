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
package org.jboss.tools.jsf.vpe.richfaces.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.tools.jsf.vpe.richfaces.ComponentUtil;
import org.jboss.tools.jsf.vpe.richfaces.HtmlComponentUtil;
import org.jboss.tools.vpe.editor.VpeVisualDomBuilder;
import org.jboss.tools.vpe.editor.context.VpePageContext;
import org.jboss.tools.vpe.editor.template.VpeAbstractTemplate;
import org.jboss.tools.vpe.editor.template.VpeCreationData;
import org.jboss.tools.vpe.editor.template.VpeToggableTemplate;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RichFacesTabPanelTemplate extends VpeAbstractTemplate implements VpeToggableTemplate {

	final static String RICH_FACES_TAB_PANEL = "richFacesTabPanel"; //$NON-NLS-1$
	final static String CSS_FILE_PATH = "tabPanel/tabPanel.css"; //$NON-NLS-1$
	final static String SPACER_FILE_PATH = "common/spacer.gif"; //$NON-NLS-1$

	private final String HEADER_ALINGMENT = "headerAlignment"; //$NON-NLS-1$
	private final String HEADER_SPACING = "headerSpacing"; //$NON-NLS-1$
	private final String SELECTED_TAB = "selectedTab"; //$NON-NLS-1$
	
	private final String CONTENT_CLASS = "contentClass"; //$NON-NLS-1$
	private final String CONTENT_STYLE = "contentStyle"; //$NON-NLS-1$
	private final String TAB_CLASS = "tabClass"; //$NON-NLS-1$
	private final String ACTIVE_TAB_CLASS = "activeTabClass"; //$NON-NLS-1$
	private final String INACTIVE_TAB_CLASS = "inactiveTabClass"; //$NON-NLS-1$
	private final String DISABLED_TAB_CLASS = "disabledTabClass"; //$NON-NLS-1$
	
	private final String CSS_PANEL = "rich-tabpanel"; //$NON-NLS-1$
	private final String CSS_CONTENT = "rich-tabpanel-content"; //$NON-NLS-1$
	private final String CSS_CONTENT_POSITION = "rich-tabpanel-content-position"; //$NON-NLS-1$
	private final String CSS_SIDE_BORDER = "rich-tabhdr-side-border"; //$NON-NLS-1$
	private final String CSS_SIDE_CELL = "rich-tabhdr-side-cell"; //$NON-NLS-1$
	
	private final String ZERO = "0"; //$NON-NLS-1$
	private final String ONE = "1"; //$NON-NLS-1$
	private final String TWO = "2"; //$NON-NLS-1$
	private final String SPACE = " "; //$NON-NLS-1$
	private final String EMPTY = ""; //$NON-NLS-1$
	
	private final String TAB = ":tab"; //$NON-NLS-1$
	private final String NAME = "name"; //$NON-NLS-1$
	
	private static Map toggleMap = new HashMap();

	public VpeCreationData create(VpePageContext pageContext, Node sourceNode, nsIDOMDocument visualDocument) {

		Element sourceElement = (Element)sourceNode;

		nsIDOMElement table = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TABLE);
		
		VpeCreationData creationData = new VpeCreationData(table);
		ComponentUtil.setCSSLink(pageContext, CSS_FILE_PATH, RICH_FACES_TAB_PANEL);
		table.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, CSS_PANEL + SPACE + ComponentUtil.getAttribute(sourceElement, HtmlComponentUtil.HTML_STYLECLASS_ATTR));
		table.setAttribute(HtmlComponentUtil.HTML_BORDER_ATTR, ZERO);
		table.setAttribute(HtmlComponentUtil.HTML_CELLPADDING_ATTR, ZERO);
		table.setAttribute(HtmlComponentUtil.HTML_CELLSPACING_ATTR, ZERO);
		table.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, getStyle(sourceElement));
		
		nsIDOMElement tbody = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TBODY);
		table.appendChild(tbody);
		nsIDOMElement tr = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TR);
		tbody.appendChild(tr);
		nsIDOMElement td = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TD);
		tr.appendChild(td);
		td.setAttribute(HtmlComponentUtil.HTML_ALIGN_ATTR, getHeaderAlignment(sourceElement));

		nsIDOMElement inerTable = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TABLE);
		td.appendChild(inerTable);
		inerTable.setAttribute(HtmlComponentUtil.HTML_BORDER_ATTR, ZERO);
		inerTable.setAttribute(HtmlComponentUtil.HTML_CELLPADDING_ATTR, ZERO);
		inerTable.setAttribute(HtmlComponentUtil.HTML_CELLSPACING_ATTR, ZERO);

		// Encode header
		nsIDOMElement inerTr = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TR);
		inerTable.appendChild(inerTr);
		nsIDOMElement inerTd = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TD);
		inerTr.appendChild(inerTd);
		nsIDOMElement img = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_IMG);
		inerTd.appendChild(img);
		ComponentUtil.setImg(img, SPACER_FILE_PATH);
		img.setAttribute(HtmlComponentUtil.HTML_WIDTH_ATTR, TWO);
		img.setAttribute(HtmlComponentUtil.HTML_HEIGHT_ATTR, ONE);
		img.setAttribute(HtmlComponentUtil.HTML_BORDER_ATTR, ZERO);

		List<Node> children = ComponentUtil.getChildren(sourceElement);
		int activeId = getActiveId(sourceElement, children);
		int i = 0;
		for (Node child : children) {
			boolean active = (i == activeId);
			
			if(child.getNodeName().endsWith(TAB)) {
				RichFacesTabTemplate.encodeHeader((Element) child,
						visualDocument, inerTr, active, ComponentUtil
								.getAttribute(sourceElement, ACTIVE_TAB_CLASS),
						ComponentUtil.getAttribute(sourceElement,
								INACTIVE_TAB_CLASS),
						ComponentUtil.getAttribute(sourceElement,
								DISABLED_TAB_CLASS), String.valueOf(i));
				i++;
				// Add <td><img src="#{spacer}" height="1" alt="" border="0" style="#{this:encodeHeaderSpacing(context, component)}"/></td>
				nsIDOMElement spaceTd = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TD);
				inerTr.appendChild(spaceTd);
				nsIDOMElement spaceImg = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_IMG);
				spaceTd.appendChild(spaceImg);
				ComponentUtil.setImg(spaceImg, SPACER_FILE_PATH);
				spaceImg.setAttribute(HtmlComponentUtil.HTML_HEIGHT_ATTR, ONE);
				spaceImg.setAttribute(HtmlComponentUtil.HTML_BORDER_ATTR, ZERO);
				String headerSpacing = sourceElement.getAttribute(HEADER_SPACING);
				if(headerSpacing==null) {
					headerSpacing = ONE;
				}
				spaceImg.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, "width: " + headerSpacing + "px"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}

		inerTd = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TD);
		inerTr.appendChild(inerTd);
		img = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_IMG);
		inerTd.appendChild(img);
		ComponentUtil.setImg(img, SPACER_FILE_PATH);
		img.setAttribute(HtmlComponentUtil.HTML_WIDTH_ATTR, ONE);
		img.setAttribute(HtmlComponentUtil.HTML_HEIGHT_ATTR, ONE);
		img.setAttribute(HtmlComponentUtil.HTML_BORDER_ATTR, ZERO);

		// Encode first child tab
		inerTr = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TR);
		tbody.appendChild(inerTr);
		children = ComponentUtil.getChildren(sourceElement);
		i = 0;
		for (Node child : children) {
			boolean active = (i == activeId);
			if(child.getNodeName().endsWith(TAB)) {
				i++;
				if (active) {
					RichFacesTabTemplate.encodeBody(creationData,
							(Element) child, visualDocument, inerTr, true,
							ComponentUtil.getAttribute(sourceElement,
									TAB_CLASS), ComponentUtil.getAttribute(
									sourceElement, ACTIVE_TAB_CLASS),
							ComponentUtil.getAttribute(sourceElement,
									INACTIVE_TAB_CLASS), ComponentUtil
									.getAttribute(sourceElement,
											DISABLED_TAB_CLASS),
							ComponentUtil.getAttribute(sourceElement,
									CONTENT_CLASS),
							ComponentUtil.getAttribute(sourceElement,
									CONTENT_STYLE));
					break;
				}
			}
		}

		return creationData;
	}

	private int getActiveId(Element sourceElement, List<Node> children) {
		int activeId = -1;
		try { 
			activeId = Integer.valueOf((String)toggleMap.get(sourceElement));
		} catch (NumberFormatException nfe) {
			activeId = -1;
		}

		if (activeId == -1) {
			activeId = getTabId(children, sourceElement.getAttribute(SELECTED_TAB));
		}
		
		if (activeId == -1) 
			activeId = 0;
			
		int count = getChildrenCount(children);
		if (count - 1 < activeId) {
			activeId = count - 1;
		}
		
		return activeId;
	}
	
	private int getChildrenCount(List<Node> children) {
		int count = 0;
		for (Node child : children) {
			if (child.getNodeName().endsWith(TAB)) {
				count++;
			}
		}
		return count;
	}

	private int getTabId(List<Node> children, String tabName) {
		if (tabName == null) return -1;
		int count = 0;
		for (Node child : children) {
			if (child.getNodeName().endsWith(TAB)) {
				if (!(child instanceof Element))
					continue;
				
				String name = ((Element)child).getAttribute(NAME);
				if (tabName.equals(name))
					return count;
				
				count++;
			}
		}
		return -1;
	}

	private String getStyle(Element sourceElement) {
	     
		String widthAttrValue = sourceElement.getAttribute(HtmlComponentUtil.HTML_WIDTH_ATTR);
		String heightAttrValue = sourceElement.getAttribute(HtmlComponentUtil.HTML_HEIGHT_ATTR);
		String styleAttrValue = sourceElement.getAttribute(HtmlComponentUtil.HTML_STYLE_ATTR);
		String style = styleAttrValue != null ? styleAttrValue : EMPTY;

		if (!ComponentUtil.parameterPresent(styleAttrValue, HtmlComponentUtil.HTML_WIDTH_ATTR)) {
			String width = (widthAttrValue != null && widthAttrValue.length() > 0) ? widthAttrValue : "100%"; //$NON-NLS-1$
			style = ComponentUtil.addParameter(style, "width:" + width); //$NON-NLS-1$
		}

		if (!ComponentUtil.parameterPresent(styleAttrValue, HtmlComponentUtil.HTML_HEIGHT_ATTR)) {
			String height = (heightAttrValue != null && heightAttrValue.length() > 0) ? heightAttrValue : EMPTY;
			if (height.length() > 0) {
				style =ComponentUtil.addParameter(style, "height:" + height); //$NON-NLS-1$
			}
		}
	  
		return style;
	}

	private String getHeaderAlignment(Element sourceElement) {
		String headerAlignment = sourceElement.getAttribute(HEADER_ALINGMENT);
		if(headerAlignment==null) {
			headerAlignment = HtmlComponentUtil.HTML_ALIGN_LEFT_VALUE; 
		}
		return headerAlignment;
	}

	public void toggle(VpeVisualDomBuilder builder, Node sourceNode, String toggleId) {
		toggleMap.put(sourceNode, toggleId);
	}

	public void stopToggling(Node sourceNode) {
		toggleMap.remove(sourceNode);
	}
}