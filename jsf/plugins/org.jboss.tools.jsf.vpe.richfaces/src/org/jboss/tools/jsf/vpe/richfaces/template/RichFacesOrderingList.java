/**
 * 
 */
package org.jboss.tools.jsf.vpe.richfaces.template;

import java.util.ArrayList;
import java.util.List;

import org.jboss.tools.jsf.vpe.richfaces.ComponentUtil;
import org.jboss.tools.jsf.vpe.richfaces.HtmlComponentUtil;
import org.jboss.tools.jsf.vpe.richfaces.RichFacesTemplatesActivator;
import org.jboss.tools.vpe.editor.context.VpePageContext;
import org.jboss.tools.vpe.editor.template.VpeAbstractTemplate;
import org.jboss.tools.vpe.editor.template.VpeChildrenInfo;
import org.jboss.tools.vpe.editor.template.VpeCreationData;
import org.jboss.tools.vpe.editor.util.HTML;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author dmaliarevich
 * 
 */
public class RichFacesOrderingList extends VpeAbstractTemplate {

	final static String DEFAULT_LIST_HEIGHT = "150px";
	final static String DEFAULT_LIST_WIDTH = "250px";
	final static String DEFAULT_HEIGHT = "200px";
	final static String DEFAULT_WIDTH = "300px";
	
	final static String 	CAPTION_FACET = "caption";
	final static String 	TOP_CONTROL_FACET = "topControl";
	final static String 	UP_CONTROL_FACET = "upControl";
	final static String 	DOWN_CONTROL_FACET = "downControl";
	final static String 	BOTTOM_CONTROL_FACET = "bottomControl";
	
	final static String HEADER = "header";
	final static String HEADER_CLASS = "headerClass";
	final static String FOOTER = "footer";
	final static String FOOTER_CLASS = "footerClass";
	final static String CAPTION_CLASS = "captionClass";
	final static String CAPTION_STYLE = "captionStyle";
	final static String SPACE = " ";

	private static String STYLE_FOR_CAPTOION_LABEL = "white-space: normal; word-wrap: break-word; font-weight: bold; font-size:14px;";
	private static String STYLE_FOR_LOW_SCROLL = "overflow: scroll; width: 100%; height: 17px;";
	private static String STYLE_FOR_RIGHT_SCROLL = "overflow: scroll; width: 17px; height: 100%;";

	private static int NUM_ROW = 1;

	private static final String TOP_CONTROL_IMG = "orderingList/top.gif";
	private static final String UP_CONTROL_IMG = "orderingList/up.gif";
	private static final String DOWN_CONTROL_IMG = "orderingList/down.gif";
	private static final String BOTTOM_CONTROL_IMG = "orderingList/bottom.gif";

	private static final String BUTTON_BG = "orderingList/button_bg.gif";
	private static final String HEADER_CELL_BG = "orderingList/table_header_cell_bg.gif";

	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String LIST_WIDTH = "listWidth";
	private static final String LIST_HEIGHT = "listHeight";

	private static final String TOP_CONTROL_LABEL = "topControlLabel";
	private static final String UP_CONTROL_LABEL = "upControlLabel";
	private static final String DOWN_CONTROL_LABEL = "downControlLabel";
	private static final String BOTTOM_CONTROL_LABEL = "bottomControlLabel";

	private static final String TOP_CONTROL_LABEL_DEFAULT = "First";
	private static final String UP_CONTROL_LABEL_DEFAULT = "Up";
	private static final String DOWN_CONTROL_LABEL_DEFAULT = "Down";
	private static final String BOTTOM_CONTROL_LABEL_DEFAULT = "Last";

	private static final String CAPTION_LABEL = "captionLabel";
	
	private static final String CONTROLS_TYPE = "controlsType";
	private static final String CONTROLS_VERTICAL_ALIGN = "controlsVerticalAlign";
	private static final String CONTROLS_HORIZONTAL_ALIGN = "controlsHorizontalAlign";
	private static final String SHOW_BUTTON_LABELS = "showButtonLabels";
	private static final String FAST_ORDER_CONTROL_VISIBLE = "fastOrderControlsVisible";
	private static final String ORDER_CONTROL_VISIBLE = "orderControlsVisible";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.tools.vpe.editor.template.VpeTemplate#create(org.jboss.tools.vpe.editor.context.VpePageContext,
	 *      org.w3c.dom.Node, org.mozilla.interfaces.nsIDOMDocument)
	 */
	public VpeCreationData create(VpePageContext pageContext, Node sourceNode,
			nsIDOMDocument visualDocument) {

		Element sourceElement = (Element) sourceNode;

		String listWidth = sourceElement.getAttribute(LIST_WIDTH);
		String listHeight = sourceElement.getAttribute(LIST_HEIGHT);
		String width = sourceElement.getAttribute(WIDTH);
		String height = sourceElement.getAttribute(HEIGHT);

		String controlsType = sourceElement
		.getAttribute(CONTROLS_TYPE);
		String controlsHorizontalAlign = sourceElement
			.getAttribute(CONTROLS_HORIZONTAL_ALIGN);
		String controlsVerticalAlign = sourceElement
				.getAttribute(CONTROLS_VERTICAL_ALIGN);
		String captionLabel = sourceElement.getAttribute(CAPTION_LABEL);

		// --------------------- COMMON TABLE ------------------------
		nsIDOMElement tableCommon = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TABLE);

		VpeCreationData creationData = new VpeCreationData(tableCommon);

		nsIDOMElement captionRow = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TR);
		nsIDOMElement dataRow = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TR);
		
		tableCommon.setAttribute(HtmlComponentUtil.HTML_ATR_WIDTH, (width == null ? DEFAULT_WIDTH : width));
		tableCommon.setAttribute(HtmlComponentUtil.HTML_ATR_HEIGHT, (height == null ? DEFAULT_HEIGHT : height));
		
		
		tableCommon.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "rich-ordering-list-body");
		tableCommon.appendChild(captionRow);
		tableCommon.appendChild(dataRow);

		// ---------------------row1------------------------
		nsIDOMElement captionRow_TD = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TD);
		captionRow.appendChild(captionRow_TD);

		nsIDOMElement captionRow_TD_DIV = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		captionRow_TD_DIV.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "rich-ordering-list-caption");
		captionRow_TD_DIV.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR,
				/*STYLE_FOR_CAPTOION_LABEL +*/ "width: "
						+ (listWidth == null ? DEFAULT_LIST_WIDTH : listWidth)
						+ "px");
		
		Element captionFacet = ComponentUtil.getFacet(sourceElement, CAPTION_FACET);
		if (null != captionFacet) {
			// Creating table caption with facet content
			nsIDOMElement fecetDiv = encodeFacetsToDiv(captionFacet, false, creationData, visualDocument);
			captionRow_TD_DIV.appendChild(fecetDiv);
		} else {
			captionRow_TD_DIV.appendChild(visualDocument.createTextNode(captionLabel));
		}
		
		captionRow_TD.appendChild(captionRow_TD_DIV);

		// ---------------------row2 ---- with list table and buttons------------------------
		nsIDOMElement dataRow_leftTD = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TD);
		dataRow.appendChild(dataRow_leftTD);

		nsIDOMElement dataRow_rightTD = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TD);
		dataRow.appendChild(dataRow_rightTD);
		
		nsIDOMElement tableListTD;
		nsIDOMElement buttonsTD;
		
		if ("left".equalsIgnoreCase(controlsHorizontalAlign)) {
			buttonsTD = dataRow_leftTD;
			tableListTD = dataRow_rightTD;
			buttonsTD.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, "width: 1%;");
		} else {
			tableListTD = dataRow_leftTD;
			buttonsTD = dataRow_rightTD;
		}

		// ---------------------buttons------------------------
		if (!"none".equalsIgnoreCase(controlsType)) {
			nsIDOMElement controlsDiv = createControlsDiv(creationData, visualDocument, sourceElement);
			buttonsTD.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "rich-ordering-list-button-valign");
			buttonsTD.setAttribute(HtmlComponentUtil.HTML_ALIGN_ATTR, "center");
			buttonsTD.setAttribute(HtmlComponentUtil.HTML_ATTR_VALIGN, ("center"
					.equalsIgnoreCase(controlsVerticalAlign) ? "middle"
							: controlsVerticalAlign));
			buttonsTD.appendChild(controlsDiv);
		}
		// --------------------------------------------

		// ---------------------listTable------------------------
		tableListTD.appendChild(createListDiv(visualDocument, sourceElement, creationData, pageContext));
		// --------------------------------------------
		
		return creationData;
	}
	
	private nsIDOMElement createListDiv(nsIDOMDocument visualDocument, 
			Element sourceElement, VpeCreationData creationData, VpePageContext pageContext) {
		nsIDOMElement listTable = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TABLE);
		nsIDOMElement tr1 = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TR);
		nsIDOMElement tr2 = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TR);

		listTable.appendChild(tr1);
		listTable.appendChild(tr2);

		// ---------------------tr1------------------------
		nsIDOMElement tr1_TD1 = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TD);
		tr1.appendChild(tr1_TD1);

		nsIDOMElement tr1_TD2 = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TD);
		tr1.appendChild(tr1_TD2);

		nsIDOMElement tr1_TD2_DIV = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		tr1_TD2_DIV.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR,
				STYLE_FOR_RIGHT_SCROLL);
		tr1_TD2.appendChild(tr1_TD2_DIV);

		// -------------------------------------------------------

		// ---------------------tr2------------------------
		nsIDOMElement tr2_TD = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_TD);
		tr2.appendChild(tr2_TD);

		nsIDOMElement tr2_TD_DIV = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		tr2_TD_DIV.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR,
				STYLE_FOR_LOW_SCROLL);
		tr2_TD.appendChild(tr2_TD_DIV);

		// --------------------------------------------

		ComponentUtil.setCSSLink(pageContext, "orderingList/orderingList.css",
				"richFacesOrderingList");
		nsIDOMElement contentDiv = createOutputList(creationData, visualDocument,
				sourceElement);
		tr1_TD1.appendChild(contentDiv);

		return listTable;
	}
	
	private nsIDOMElement createControlsDiv( VpeCreationData creationData, nsIDOMDocument visualDocument, 
			Element sourceElement) {
		
		String topControlLabel = sourceElement.getAttribute(TOP_CONTROL_LABEL);
		String upControlLabel = sourceElement.getAttribute(UP_CONTROL_LABEL);
		String downControlLabel = sourceElement
				.getAttribute(DOWN_CONTROL_LABEL);
		String bottomControlLabel = sourceElement
				.getAttribute(BOTTOM_CONTROL_LABEL);

		String showButtonLabelsStr = sourceElement
				.getAttribute(SHOW_BUTTON_LABELS);
		String fastOrderControlsVisibleStr = sourceElement
				.getAttribute(FAST_ORDER_CONTROL_VISIBLE);
		String orderControlsVisibleStr = sourceElement
				.getAttribute(ORDER_CONTROL_VISIBLE);
		boolean showButtonLabels = ComponentUtil
				.string2boolean(showButtonLabelsStr);
		boolean fastOrderControlsVisible = ComponentUtil
				.string2boolean(fastOrderControlsVisibleStr);
		boolean orderControlsVisible = ComponentUtil
				.string2boolean(orderControlsVisibleStr);

		nsIDOMElement buttonsDiv = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		buttonsDiv.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
				"rich-ordering-list-button-layout");

		Element top_control_facet = ComponentUtil.getFacet(sourceElement, TOP_CONTROL_FACET);
		Element up_control_facet = ComponentUtil.getFacet(sourceElement, UP_CONTROL_FACET);
		Element down_control_facet = ComponentUtil.getFacet(sourceElement, DOWN_CONTROL_FACET);
		Element bottom_control_facet = ComponentUtil.getFacet(sourceElement, BOTTOM_CONTROL_FACET);
		
		if (fastOrderControlsVisible) {
			nsIDOMElement btnTopDiv = createButtonDiv(creationData, visualDocument,
					(null == topControlLabel ? TOP_CONTROL_LABEL_DEFAULT
							: topControlLabel), TOP_CONTROL_IMG, new Boolean(
							showButtonLabels).booleanValue(), top_control_facet);
			buttonsDiv.appendChild(btnTopDiv);
		}

		if (orderControlsVisible) {
			nsIDOMElement btnUpDiv = createButtonDiv(creationData, visualDocument,
					(null == upControlLabel ? UP_CONTROL_LABEL_DEFAULT
							: upControlLabel), UP_CONTROL_IMG, new Boolean(
							showButtonLabels).booleanValue(), up_control_facet);
			nsIDOMElement btnDownDiv = createButtonDiv(creationData, visualDocument,
					(null == downControlLabel ? DOWN_CONTROL_LABEL_DEFAULT
							: downControlLabel), DOWN_CONTROL_IMG, new Boolean(
							showButtonLabels).booleanValue(), down_control_facet);
			buttonsDiv.appendChild(btnUpDiv);
			buttonsDiv.appendChild(btnDownDiv);
		}

		if (fastOrderControlsVisible) {
			nsIDOMElement btnBottomDiv = createButtonDiv(creationData, visualDocument,
					(null == bottomControlLabel ? BOTTOM_CONTROL_LABEL_DEFAULT
							: bottomControlLabel), BOTTOM_CONTROL_IMG,
					new Boolean(showButtonLabels).booleanValue(), bottom_control_facet);
			buttonsDiv.appendChild(btnBottomDiv);

		}
		return buttonsDiv;
	}

	/**
	 * Creates control button with image and label.
	 * 
	 * @param visualDocument
	 *            visual document
	 * @param btnName
	 *            the button label
	 * @param imgName
	 *            path to the image
	 * @param showButtonLabels
	 *            show button label flag
	 * 
	 * @return the button
	 */
	private nsIDOMElement createButtonDiv(VpeCreationData creationData, nsIDOMDocument visualDocument,
			String btnName, String imgName, boolean showButtonLabels, Element buttonFacet) {
		
		nsIDOMElement div1 = visualDocument
			.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		nsIDOMElement div2 = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		nsIDOMElement a = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_A);
		nsIDOMElement div3 = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		nsIDOMElement img = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_IMG);
		
		div1.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "rich-ordering-controls");
		div2.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "rich-ordering-list-button");
		
		a.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "rich-ordering-list-button-selection");
		div3.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "rich-ordering-list-button-content");
		
		String  resourceFolder = RichFacesTemplatesActivator.getPluginResourcePath();
		String divStyle = "background-image: url(file://" + resourceFolder + BUTTON_BG + ");";
		div2.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, divStyle);
		div1.appendChild(div2);
		
		if (null != buttonFacet) {
			// Creating button with facet content
			nsIDOMElement fecetDiv = encodeFacetsToDiv(buttonFacet, true,  creationData, visualDocument);;
			div2.appendChild(fecetDiv);
		} else {
			div2.appendChild(a);
			a.appendChild(div3);
			// Creating button with image and label 
			img.setAttribute("src", "file://" + resourceFolder + imgName);
			img.setAttribute(HTML.ATTR_WIDTH, "15");
			img.setAttribute(HTML.ATTR_HEIGHT, "15");
			div3.appendChild(img);
			if (showButtonLabels) {
				div3.appendChild(visualDocument.createTextNode(btnName));
			}
		}
		return div1;
	}

	
	private nsIDOMElement createOutputList(VpeCreationData creationData, nsIDOMDocument visualDocument,
			Element sourceElement) {
		nsIDOMElement outputDiv = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		nsIDOMElement contentDiv = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_DIV);

		nsIDOMElement contentTable = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_TABLE);
		nsIDOMElement thead = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_THEAD);
		nsIDOMElement tfoot = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_TFOOT);
		nsIDOMElement tbody = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_TBODY);
		
		ArrayList<Element> columns = getColumns(sourceElement);
		int columnsLength = getColumnsCount(sourceElement, columns);
		
		// ---------- HEADER -----------
		// Encode Header
		Element header = ComponentUtil.getFacet(sourceElement, HEADER);
		ArrayList<Element> columnsHeaders = getColumnsWithFacet(columns, HEADER);
		if (header != null || !columnsHeaders.isEmpty()) {
			String headerClass = (String) sourceElement
					.getAttribute(HEADER_CLASS);
			if (header != null) {
				encodeTableHeaderOrFooterFacet(creationData, thead,
						columnsLength, visualDocument, header,
						"dr-table-header rich-ordering-list-table-header",
						"dr-table-header-continue rich-ordering-list-header-continue",
						"dr-table-headercell rich-ordering-list-table-header-cell",
						headerClass, HtmlComponentUtil.HTML_TAG_TD);
			}
			if (!columnsHeaders.isEmpty()) {
				nsIDOMElement tr = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TR);
				thead.appendChild(tr);
				String styleClass = encodeStyleClass(null,
						"dr-table-subheader rich-table-subheader", null,
						headerClass);
				if (styleClass != null) {
					tr.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
							styleClass);
				}
				encodeHeaderOrFooterFacets(creationData, tr, visualDocument,
						columnsHeaders,
						"rich-ordering-list-table-header rich-ordering-list-table-header-cell",
						headerClass, HEADER, HtmlComponentUtil.HTML_TAG_TD);
			}
		}

		// ---------- FOOTER -----------
		// Encode Footer
		Element footer = ComponentUtil.getFacet(sourceElement, FOOTER);
		ArrayList<Element> columnsFooters = getColumnsWithFacet(columns, FOOTER);
		if (footer != null || !columnsFooters.isEmpty()) {
			String footerClass = (String) sourceElement
					.getAttribute(FOOTER_CLASS);
			if (!columnsFooters.isEmpty()) {
				nsIDOMElement tr = visualDocument
						.createElement(HtmlComponentUtil.HTML_TAG_TR);
				tfoot.appendChild(tr);
				String styleClass = encodeStyleClass(null,
						"dr-table-subfooter rich-table-subfooter", null,
						footerClass);
				if (styleClass != null) {
					tr.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
							styleClass);
				}
				encodeHeaderOrFooterFacets(creationData, tr, visualDocument,
						columnsFooters,
						"rich-ordering-list-table-header rich-ordering-list-table-header-cell",
						footerClass, FOOTER, HtmlComponentUtil.HTML_TAG_TD);
			}
			if (footer != null) {
				encodeTableHeaderOrFooterFacet(creationData, tfoot,
						columnsLength, visualDocument, footer,
						"dr-table-footer rich-ordering-list-table-header",
						"dr-table-footer-continue rich-table-footer-continue",
						"dr-table-footercell rich-ordering-list-table-header-cell",
						footerClass, HtmlComponentUtil.HTML_TAG_TD);
			}
		}
		
		// ---------- CONTENT -----------
		
		String listWidth = sourceElement.getAttribute(LIST_WIDTH);
		String listHeight = sourceElement.getAttribute(LIST_HEIGHT);
		
		String divStyle = HtmlComponentUtil.HTML_WIDTH_ATTR + " : "
		+ (listWidth == null ? DEFAULT_LIST_WIDTH : listWidth) + ";"
		+ HtmlComponentUtil.HTML_HEIGHT_ATTR + " : "
		+ (listHeight == null ? DEFAULT_LIST_HEIGHT : listHeight) + ";";

		contentDiv.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, divStyle);
		
		/*
		// Encode colgroup definition.
		nsIDOMElement colgroup = visualDocument
				.createElement(HtmlComponentUtil.HTML_TAG_COLGROUP);
		colgroup.setAttribute(HtmlComponentUtil.HTML_TAG_SPAN, String
				.valueOf(columnsLength));
		table.appendChild(colgroup);
		
		// Encode Caption
		encodeCaption(creationData, sourceElement, visualDocument, table);
		 */
		contentDiv.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
		"rich-ordering-list-content");
		contentTable.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
		"rich-ordering-list-items");
		
		ComponentUtil.copyAttributes(sourceElement, contentTable);
		contentTable.removeAttribute(HtmlComponentUtil.HTML_ATR_HEIGHT);
		contentTable.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, "width: 100%;");
		
		// Create mapping to Encode body
		for (int i = 0; i < NUM_ROW; i++) {
			List<Node> children = ComponentUtil.getChildren(sourceElement);
			nsIDOMElement tr = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TR);
			tr.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
			"rich-ordering-list-row");
			VpeChildrenInfo trInfo = new VpeChildrenInfo(tr);
			tbody.appendChild(tr);
			creationData.addChildrenInfo(trInfo);

			for (Node child : children) {
				if (child.getNodeName().endsWith(":column")) {
					trInfo.addSourceChild(child);
				} else if (child.getNodeName().endsWith(":columnGroup")) {
					RichFacesColumnGroupTemplate.DEFAULT_INSTANCE.encode(
							creationData, (Element) child, visualDocument,
							tbody);
					tr = null;
					trInfo = null;
				} else if (child.getNodeName().endsWith(":subTable")) {
					RichFacesSubTableTemplate.DEFAULT_INSTANCE.encode(
							creationData, (Element) child, visualDocument,
							tbody);
					tr = null;
					trInfo = null;
				} else {
					VpeChildrenInfo childInfo = new VpeChildrenInfo(tbody);
					childInfo.addSourceChild(child);
					creationData.addChildrenInfo(childInfo);
					tr = null;
					trInfo = null;
				}
			}
		}
		
		// ---------- FINILAZING -----------
		contentTable.appendChild(thead);
		contentTable.appendChild(tbody);
		contentTable.appendChild(tfoot);
		contentDiv.appendChild(contentTable);
		outputDiv.appendChild(contentDiv);
		
		return outputDiv;
	}
	
	protected nsIDOMElement encodeFacetsToDiv(Element facetBody, boolean isControlFacet, VpeCreationData creationData, nsIDOMDocument visualDocument) {
		nsIDOMElement fecetDiv = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_DIV);
		nsIDOMElement table = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_TABLE);
		nsIDOMElement tbody = visualDocument
		.createElement(HtmlComponentUtil.HTML_TAG_TBODY);
		
		boolean isColumnGroup = facetBody.getNodeName()
				.endsWith(":columnGroup");
		boolean isSubTable = facetBody.getNodeName().endsWith(":subTable");
		if (isColumnGroup) {
			RichFacesColumnGroupTemplate.DEFAULT_INSTANCE.encode(creationData,
					facetBody, visualDocument, tbody);
		} else if (isSubTable) {
			RichFacesSubTableTemplate.DEFAULT_INSTANCE.encode(creationData,
					facetBody, visualDocument, tbody);
		} else {
			nsIDOMElement tr = visualDocument
					.createElement(HtmlComponentUtil.HTML_TAG_TR);
			tbody.appendChild(tr);

			nsIDOMElement td = visualDocument.createElement(HtmlComponentUtil.HTML_TAG_TD);
			tr.appendChild(td);
			
			td.setAttribute(HtmlComponentUtil.HTML_SCOPE_ATTR,
					HtmlComponentUtil.HTML_TAG_COLGROUP);

			VpeChildrenInfo child = new VpeChildrenInfo(td);
			child.addSourceChild(facetBody);
			creationData.addChildrenInfo(child);
		}
		
		if (isControlFacet) {
			table.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "dr-default-control-button-text rich-ordering-list-button-content");
		} else {
			table.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, "dr-default-caption-text rich-ordering-list-caption");
		}
		
		table.appendChild(tbody);
		fecetDiv.appendChild(table);
		return fecetDiv;
	}
	
	/**
	 * 
	 * @param creationData
	 * @param parentTheadOrTfood
	 * @param columns
	 * @param visualDocument
	 * @param facetBody
	 * @param skinFirstRowClass
	 * @param skinRowClass
	 * @param skinCellClass
	 * @param facetBodyClass
	 * @param element
	 */
	protected void encodeTableHeaderOrFooterFacet(VpeCreationData creationData,
			nsIDOMElement parentTheadOrTfood, int columns,
			nsIDOMDocument visualDocument, Element facetBody,
			String skinFirstRowClass, String skinRowClass,
			String skinCellClass, String facetBodyClass, String element) {
		boolean isColumnGroup = facetBody.getNodeName()
				.endsWith(":columnGroup");
		boolean isSubTable = facetBody.getNodeName().endsWith(":subTable");
		if (isColumnGroup) {
			RichFacesColumnGroupTemplate.DEFAULT_INSTANCE.encode(creationData,
					facetBody, visualDocument, parentTheadOrTfood);
		} else if (isSubTable) {
			RichFacesSubTableTemplate.DEFAULT_INSTANCE.encode(creationData,
					facetBody, visualDocument, parentTheadOrTfood);
		} else {
			nsIDOMElement tr = visualDocument
					.createElement(HtmlComponentUtil.HTML_TAG_TR);
			parentTheadOrTfood.appendChild(tr);

			String styleClass = encodeStyleClass(null, skinFirstRowClass,
					facetBodyClass, null);
			if (styleClass != null) {
				tr.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, styleClass);
			}
			String style = ComponentUtil.getHeaderBackgoundImgStyle();
			tr.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, style);

			nsIDOMElement td = visualDocument.createElement(element);
			tr.appendChild(td);

			styleClass = encodeStyleClass(null, skinCellClass, facetBodyClass,
					null);
			if (styleClass != null) {
				td.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, styleClass);
			}

			if (columns > 0) {
				td.setAttribute(HtmlComponentUtil.HTML_TABLE_COLSPAN, String
						.valueOf(columns));
			}
			td.setAttribute(HtmlComponentUtil.HTML_SCOPE_ATTR,
					HtmlComponentUtil.HTML_TAG_COLGROUP);

			VpeChildrenInfo child = new VpeChildrenInfo(td);
			child.addSourceChild(facetBody);
			creationData.addChildrenInfo(child);
		}
	}
	
	/**
	 * 
	 * @param creationData
	 * @param parentTr
	 * @param visualDocument
	 * @param headersOrFooters
	 * @param skinCellClass
	 * @param headerClass
	 * @param facetName
	 * @param element
	 */
	public static void encodeHeaderOrFooterFacets(VpeCreationData creationData,
			nsIDOMElement parentTr, nsIDOMDocument visualDocument,
			ArrayList<Element> headersOrFooters, String skinCellClass,
			String headerClass, String facetName, String element) {
		for (Element column : headersOrFooters) {
			String classAttribute = facetName + "Class";
			String columnHeaderClass = column.getAttribute(classAttribute);
			nsIDOMElement td = visualDocument.createElement(element);
			parentTr.appendChild(td);
			String styleClass = encodeStyleClass(null, skinCellClass,
					headerClass, columnHeaderClass);
			td.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, styleClass);
			td.setAttribute("scop", "col");
			
			nsIDOMElement div1 = visualDocument
					.createElement(HtmlComponentUtil.HTML_TAG_DIV);
			String resourceFolder = RichFacesTemplatesActivator
					.getPluginResourcePath();
			String div1Style = "background-image: url(file://" + resourceFolder
					+ HEADER_CELL_BG + ");";
			div1.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
					"rich-ordering-list-table-header-cell");
			div1.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, div1Style);
			td.appendChild(div1);
			
			String colspan = column
					.getAttribute(HtmlComponentUtil.HTML_TABLE_COLSPAN);
			if (colspan != null && colspan.length() > 0) {
				td.setAttribute(HtmlComponentUtil.HTML_TABLE_COLSPAN, colspan);
			}
			Element facetBody = ComponentUtil.getFacet(column, facetName);

//			VpeChildrenInfo child = new VpeChildrenInfo(td);
			VpeChildrenInfo child = new VpeChildrenInfo(div1);
			child.addSourceChild(facetBody);
			creationData.addChildrenInfo(child);
		}
	}
	
	
	/**
	 * 
	 * @param creationData
	 * @param sourceElement
	 * @param visualDocument
	 * @param table
	 */
	protected void encodeCaption(VpeCreationData creationData,
			Element sourceElement, nsIDOMDocument visualDocument,
			nsIDOMElement table) {
		// Encode caption
		Element captionFromFacet = ComponentUtil.getFacet(sourceElement,
				HtmlComponentUtil.HTML_TAG_CAPTION);
		if (captionFromFacet != null) {
			String captionClass = (String) table.getAttribute(CAPTION_CLASS);
			String captionStyle = (String) table.getAttribute(CAPTION_STYLE);

			nsIDOMElement caption = visualDocument
					.createElement(HtmlComponentUtil.HTML_TAG_CAPTION);
			table.appendChild(caption);
			if (captionClass != null && captionClass.length() > 0) {
				captionClass = "dr-table-caption rich-table-caption "
						+ captionClass;
			} else {
				captionClass = "dr-table-caption rich-table-caption";
			}
			caption.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
					captionClass);
			if (captionStyle != null && captionStyle.length() > 0) {
				caption.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR,
						captionStyle);
			}

			VpeChildrenInfo cap = new VpeChildrenInfo(caption);
			cap.addSourceChild(captionFromFacet);
			creationData.addChildrenInfo(cap);
		}

	}

	/**
	 * 
	 * @param creationData
	 * @param parentTr
	 * @param visualDocument
	 * @param headersOrFooters
	 * @param skinCellClass
	 * @param headerClass
	 * @param facetName
	 * @param element
	 */
	public static void encodeHeaderFacets(VpeCreationData creationData,
			nsIDOMElement parentTr, nsIDOMDocument visualDocument,
			ArrayList<Element> headersOrFooters, String skinCellClass,
			String headerClass, String facetName, String element) {
		for (Element column : headersOrFooters) {
			String classAttribute = facetName + "Class";
			String columnHeaderClass = column.getAttribute(classAttribute);
			nsIDOMElement td = visualDocument.createElement(element);
			parentTr.appendChild(td);
			String styleClass = encodeStyleClass(null, skinCellClass,
					headerClass, columnHeaderClass);
			td.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR, styleClass);
			
			nsIDOMElement div1 = visualDocument
					.createElement(HtmlComponentUtil.HTML_TAG_DIV);
			String resourceFolder = RichFacesTemplatesActivator
					.getPluginResourcePath();
			String div1Style = "background-image: url(file://" + resourceFolder
					+ HEADER_CELL_BG + ");";
			div1.setAttribute(HtmlComponentUtil.HTML_CLASS_ATTR,
					"rich-ordering-list-table-header-cell");
			div1.setAttribute(HtmlComponentUtil.HTML_STYLE_ATTR, div1Style);
			td.appendChild(div1);

			td.setAttribute("scop", "col");
			String colspan = column
					.getAttribute(HtmlComponentUtil.HTML_TABLE_COLSPAN);
			if (colspan != null && colspan.length() > 0) {
				td.setAttribute(HtmlComponentUtil.HTML_TABLE_COLSPAN, colspan);
			}
			Element facetBody = ComponentUtil.getFacet(column, facetName);

			VpeChildrenInfo child = new VpeChildrenInfo(div1);
			child.addSourceChild(facetBody);
			creationData.addChildrenInfo(child);
		}
	}

	/**
	 * 
	 * @param parentSourceElement
	 * @return list of columns
	 */
	public static ArrayList<Element> getColumns(Element parentSourceElement) {
		ArrayList<Element> columns = new ArrayList<Element>();
		NodeList children = parentSourceElement.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if ((child instanceof Element)
					&& child.getNodeName().endsWith(":column")) {
				columns.add((Element) child);
			}
		}
		return columns;
	}

	/**
	 * 
	 * @param columns
	 * @param facetName
	 * @return list of columns with facet
	 */
	public static ArrayList<Element> getColumnsWithFacet(
			ArrayList<Element> columns, String facetName) {
		ArrayList<Element> columnsWithFacet = new ArrayList<Element>();
		for (Element column : columns) {
			Element body = ComponentUtil.getFacet(column, facetName);
			if (body != null) {
				columnsWithFacet.add(column);
			}
		}
		return columnsWithFacet;
	}

	/**
	 * 
	 * @param parentPredefined
	 * @param predefined
	 * @param parent
	 * @param custom
	 * @return
	 */
	public static String encodeStyleClass(Object parentPredefined,
			Object predefined, Object parent, Object custom) {
		StringBuffer styleClass = new StringBuffer();
		// Construct predefined classes
		if (null != parentPredefined) {
			styleClass.append(parentPredefined).append(SPACE);
		} else if (null != predefined) {
			styleClass.append(predefined).append(SPACE);
		}
		// Append class from parent component.
		if (null != parent) {
			styleClass.append(parent).append(SPACE);
		}
		if (null != custom) {
			styleClass.append(custom);
		}
		if (styleClass.length() > 0) {
			return styleClass.toString();
		}
		return null;
	}

	/**
	 * 
	 * @param sourceElement
	 * @param columns
	 * @return
	 */
	protected int getColumnsCount(Element sourceElement,
			ArrayList<Element> columns) {
		int count = 0;
		// check for exact value in component
		Integer span = null;
		try {
			span = Integer.valueOf(sourceElement.getAttribute("columns"));
		} catch (Exception e) {
			// Ignore bad attribute
		}
		if (null != span && span.intValue() != Integer.MIN_VALUE) {
			count = span.intValue();
		} else {
			// calculate max html columns count for all columns/rows children.
			count = calculateRowColumns(sourceElement, columns);
		}
		return count;
	}

	/*
	 * Calculate max number of columns per row. For rows, recursive calculate
	 * max length.
	 */
	private int calculateRowColumns(Element sourceElement,
			ArrayList<Element> columns) {
		int count = 0;
		int currentLength = 0;
		for (Element column : columns) {
			if (ComponentUtil.isRendered(column)) {
				if (column.getNodeName().endsWith(":columnGroup")) {
					// Store max calculated value of previous rows.
					if (currentLength > count) {
						count = currentLength;
					}
					// Calculate number of columns in row.
					currentLength = calculateRowColumns(sourceElement,
							getColumns(column));
					// Store max calculated value
					if (currentLength > count) {
						count = currentLength;
					}
					currentLength = 0;
				} else if (column.getNodeName().equals(
						sourceElement.getPrefix() + ":column")) {
					String breakBeforeStr = column.getAttribute("breakBefore");
					boolean breakBefore = false;
					if (breakBeforeStr != null) {
						try {
							breakBefore = Boolean.getBoolean(breakBeforeStr);
						} catch (Exception e) {
							// Ignore bad attribute
						}
					}
					// For new row, save length of previous.
					if (breakBefore) {
						if (currentLength > count) {
							count = currentLength;
						}
						currentLength = 0;
					}
					String colspanStr = column
							.getAttribute(HtmlComponentUtil.HTML_TABLE_COLSPAN);
					Integer colspan = null;
					try {
						colspan = Integer.valueOf(colspanStr);
					} catch (Exception e) {
						// Ignore
					}
					// Append colspan of this column
					if (null != colspan
							&& colspan.intValue() != Integer.MIN_VALUE) {
						currentLength += colspan.intValue();
					} else {
						currentLength++;
					}
				} else if (column.getNodeName().endsWith(":column")) {
					// UIColumn always have colspan == 1.
					currentLength++;
				}

			}
		}
		if (currentLength > count) {
			count = currentLength;
		}
		return count;
	}

	/**
	 * Checks, whether it is necessary to re-create an element at change of
	 * attribute
	 * 
	 * @param pageContext
	 *            Contains the information on edited page.
	 * @param sourceElement
	 *            The current element of the source tree.
	 * @param visualDocument
	 *            The document of the visual tree.
	 * @param visualNode
	 *            The current node of the visual tree.
	 * @param data
	 *            The arbitrary data, built by a method <code>create</code>
	 * @param name
	 *            Attribute name
	 * @param value
	 *            Attribute value
	 * @return <code>true</code> if it is required to re-create an element at
	 *         a modification of attribute, <code>false</code> otherwise.
	 */
	public boolean isRecreateAtAttrChange(VpePageContext pageContext,
			Element sourceElement, nsIDOMDocument visualDocument,
			nsIDOMElement visualNode, Object data, String name, String value) {
		return true;
	}
}
