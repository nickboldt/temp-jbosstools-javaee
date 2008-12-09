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

import org.jboss.tools.jsf.vpe.richfaces.ComponentUtil;
import org.jboss.tools.jsf.vpe.richfaces.RichFacesTemplatesActivator;
import org.jboss.tools.vpe.editor.context.VpePageContext;
import org.jboss.tools.vpe.editor.template.VpeChildrenInfo;
import org.jboss.tools.vpe.editor.template.VpeCreationData;
import org.jboss.tools.vpe.editor.util.HTML;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Create template for rich:recursiveTreeNodesAdaptor element
 * 
 * @author dsakovich@exadel.com
 * 
 */
public class RichFacesRecursiveTreeNodesAdaptorTemplate extends
	RichFacesTreeNodeTemplate {

    private static final String TREE_NAME = "tree"; //$NON-NLS-1$

    private final static String TREE_NODE_NAME = "treeNode"; //$NON-NLS-1$

    public final static String TREE_NODES_ADAPTOR_NAME = "treeNodesAdaptor"; //$NON-NLS-1$

    public final static String RECURSIVE_TREE_NODES_ADAPTOR_NAME = "recursiveTreeNodesAdaptor"; //$NON-NLS-1$

    private static final String STYLE_PATH = "/tree/tree.css"; //$NON-NLS-1$

    public static final String ICON_DIV_LINE = "/tree/divLine.gif"; //$NON-NLS-1$

    private static final String ADAPTER_LINES_STYLE = "background-position: left center; background-repeat: repeat-y;"; //$NON-NLS-1$

    public static final String ID_ATTR_NAME = "ID"; //$NON-NLS-1$

    public static final String NODES_NAME = "nodes"; //$NON-NLS-1$

    public VpeCreationData create(VpePageContext pageContext, Node sourceNode,
	    nsIDOMDocument visualDocument) {
	if (isEmptyNode(sourceNode)) {
	    return super.create(pageContext, sourceNode, visualDocument);
	} else {
	    ComponentUtil.setCSSLink(pageContext, STYLE_PATH,
		    "treeNodesAdaptor"); //$NON-NLS-1$
	    nsIDOMElement visualElement = visualDocument
		    .createElement(HTML.TAG_DIV);
	    visualElement.setAttribute(ID_ATTR_NAME, TREE_NODES_ADAPTOR_NAME);
	    if (isHasParentAdapter(sourceNode)) {
		visualElement.setAttribute(HTML.ATTR_CLASS,
			"dr-tree-h-ic-div"); //$NON-NLS-1$
		if (getShowLinesAttr(sourceNode)
			&& (isAdapterBetweenNodes(sourceNode) || isHasNextParentAdaptorElement(sourceNode))) {
		    String path = RichFacesTemplatesActivator
			    .getPluginResourcePath()
			    + ICON_DIV_LINE;
		    visualElement.setAttribute(
			    HTML.ATTR_STYLE,
			    "background-image: url(file://" + path + "); " //$NON-NLS-1$ //$NON-NLS-2$
				    + ADAPTER_LINES_STYLE);
		}
	    }
	    VpeCreationData vpeCreationData = new VpeCreationData(visualElement);
	    parseTree(pageContext, sourceNode, visualDocument, vpeCreationData,
		    visualElement);
	    return vpeCreationData;
	}
    }

    /**
     * 
     * Function for parsing tree by tree nodes;
     * 
     * @param pageContext
     * @param sourceNode
     * @param visualDocument
     * @return
     */
    public void parseTree(VpePageContext pageContext, Node sourceNode,
	    nsIDOMDocument visualDocument, VpeCreationData vpeCreationData,
	    nsIDOMElement parentElement) {
	NodeList nodeList = sourceNode.getChildNodes();
	Element element = null;
	int lenght = nodeList.getLength();
	String treeNodeName = sourceNode.getPrefix() + ":" + TREE_NODE_NAME; //$NON-NLS-1$
	String treeNodesAdaptorName = sourceNode.getPrefix() + ":" //$NON-NLS-1$
		+ TREE_NODES_ADAPTOR_NAME;
	String recursiveTreeNodesAdaptorName = sourceNode.getPrefix() + ":" //$NON-NLS-1$
		+ RECURSIVE_TREE_NODES_ADAPTOR_NAME;
	VpeChildrenInfo vpeChildrenInfo = null;
	for (int i = 0; i < lenght; i++) {
	    if (!(nodeList.item(i) instanceof Element)) {
		continue;
	    }
	    element = (Element) nodeList.item(i);
	    if (element.getNodeName().equals(treeNodeName)
		    || element.getNodeName().equals(
			    recursiveTreeNodesAdaptorName)) {
		vpeChildrenInfo = new VpeChildrenInfo(parentElement);
		vpeCreationData.addChildrenInfo(vpeChildrenInfo);
		vpeChildrenInfo.addSourceChild(element);
	    } else if (element.getNodeName().equals(treeNodesAdaptorName)) {
		vpeChildrenInfo = new VpeChildrenInfo(parentElement);
		vpeCreationData.addChildrenInfo(vpeChildrenInfo);
		vpeChildrenInfo.addSourceChild(element);
	    }
	}
    }

    /**
     * 
     * @param sourceNode
     * @return
     */
    public boolean isHasParentAdapter(Node sourceNode) {
	String treeNodesAdaptorName = sourceNode.getPrefix() + ":" //$NON-NLS-1$
		+ TREE_NODES_ADAPTOR_NAME;
	String recursiveTreeNodesAdaptorName = sourceNode.getPrefix() + ":" //$NON-NLS-1$
		+ RECURSIVE_TREE_NODES_ADAPTOR_NAME;
	Node node = sourceNode.getParentNode();
	if (node.getNodeName().equals(treeNodesAdaptorName)
		|| node.getNodeName().equals(recursiveTreeNodesAdaptorName)) {
	    return true;
	}
	return false;
    }

    /**
     * Get showConnectingLines attribute
     * 
     * @param sourceNode
     * @return
     */
    private boolean getShowLinesAttr(Node sourceNode) {
	String treeName = sourceNode.getPrefix() + ":" + TREE_NAME; //$NON-NLS-1$
	do {
	    sourceNode = sourceNode.getParentNode();
	    if (!(sourceNode instanceof Element)) {
		return true;
	    }
	} while (!sourceNode.getNodeName().equals(treeName));

	String showLinesParam = ((Element) sourceNode)
		.getAttribute(RichFacesTreeTemplate.SHOW_LINES_ATTR_NAME);

	boolean showLinesValue = true;
	if (showLinesParam != null && showLinesParam.equalsIgnoreCase("false")) { //$NON-NLS-1$
	    showLinesValue = false;
	}
	return showLinesValue;
    }

    /**
     * Has Next element
     * 
     * @param sourceNode
     * @return
     */
    private boolean isHasNextParentAdaptorElement(Node sourceNode) {
	Node tree = sourceNode.getParentNode();
	if (!(tree instanceof Element)) {
	    return true;
	}
	Node parentTree = tree.getParentNode();
	if (!(parentTree instanceof Element)) {
	    return true;
	}
	NodeList childs = parentTree.getChildNodes();
	String treeNodeName = parentTree.getPrefix() + ":" //$NON-NLS-1$
		+ RichFacesTreeTemplate.TREE_NODE_NAME;
	String treeNodesAdaptorName = parentTree.getPrefix() + ":" //$NON-NLS-1$
		+ RichFacesTreeTemplate.TREE_NODES_ADAPTOR;
	String treeRecursiveNodesAdaptorName = parentTree.getPrefix() + ":" //$NON-NLS-1$
		+ RichFacesTreeTemplate.TREE_RECURSIVE_NODES_ADAPTOR;
	Node lastElement = null;
	Node el = null;

	for (int i = 0; i < childs.getLength(); i++) {
	    el = childs.item(i);
	    if (!(el instanceof Element)) {
		continue;
	    }

	    if (lastElement != null) {
		break;
	    }
	    if (el.equals(tree)) {
		lastElement = el;
	    }
	}

	if (el.getNodeName().equals(treeNodeName)
		|| el.getNodeName().equals(treeNodesAdaptorName)
		|| el.getNodeName().equals(treeRecursiveNodesAdaptorName)) {
	    return true;
	}
	return false;
    }

    /**
     * Is adapter between treeNodes
     * 
     * @param sourceNode
     * @return
     */
    private boolean isAdapterBetweenNodes(Node sourceNode) {
	Node parentNode = sourceNode.getParentNode();
	NodeList childs = parentNode.getChildNodes();
	Node beforeAdapterNode = null;
	Node afterAdapterNode = null;
	Node adapterNode = null;
	String treeNodeName = sourceNode.getPrefix() + ":" //$NON-NLS-1$
		+ RichFacesTreeTemplate.TREE_NODE_NAME;
	for (int i = 0; i < childs.getLength(); i++) {
	    Node el = childs.item(i);
	    if (!(el instanceof Element)) {
		continue;
	    }
	    if (el.equals(sourceNode)) {
		adapterNode = el;
	    } else {
		if (el.getNodeName().equals(treeNodeName)) {
		    if (adapterNode == null) {
			beforeAdapterNode = el;
		    } else {
			afterAdapterNode = el;
		    }
		}

	    }

	}

	if (beforeAdapterNode != null && afterAdapterNode != null) {
	    return true;
	}
	return false;
    }

    /**
     * 
     * @param sourceElement
     * @return
     */
    private boolean isEmptyNode(Node sourceNode) {

	NodeList childs = sourceNode.getChildNodes();
	for (int i = 0; i < childs.getLength(); i++) {
	    Node el = childs.item(i);
	    if (!(el instanceof Element)) {
		continue;
	    }
	    return false;
	}

	return true;
    }

    @Override
    public void setPseudoContent(VpePageContext pageContext,
	    Node sourceContainer, nsIDOMNode visualContainer,
	    nsIDOMDocument visualDocument) {
	// Empty
    }
}
