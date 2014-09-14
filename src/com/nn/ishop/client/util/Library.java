/*****************************************************************************/
/* File Description  : Library                                               */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : Library.java                                          */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project                                       */
/*****************************************************************************/

package com.nn.ishop.client.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
 
 /**
  * @author connect.shift-think.com
  */
public class Library {
	
	public static final Color DEFAULT_FORM_BACKGROUND =  new Color(255,255,255);//new Color(248,249,239);
	public static final Color DEFAULT_WINDOW_AND_MENU_BACKGROUND =  new Color(226,226,226);
	public static final Color DEFAULT_FORM_ACTION_BACKGROUND =  new Color(238,238,233);// new Color(228,228,223);
	
	public static final Color DEFAULT_BORDER_COLOR =  new Color(187,187,187);
	
	public static final String PERSON_OVERVIEW_HTML_PATH = "templates/html/personOverview.html";
	/* background color */
	public static final Color UI_SIC_GRAY = new Color(236, 233, 216);
	
	public static final Font HEADER_TITLE_FONT = new Font("Arial", Font.BOLD, 14);
	public static final Font TABLE_HEADER_FONT = new Font("Arial", Font.BOLD, 11);
	public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 11);

	public static final Color TBL_BORDER_COLOR = new Color(236,233,216);
	/** Data type */
	public static Insets COMMON_DATA_INSETS = new Insets(2,2,2,2);
	
	public static final Border LINE_BORDER = new LineBorder(Library.C_LINE_COLOR);
	
	/** Data type */
	public static final String DATA_TYPE_CSV = "CSV";
	public static final String DATA_TYPE_NETML = "NetML";
    /**
     * constant represents image path
     */
    public static final String IMAGES_PATH = "img/";

//    /**
//     * constant represents CSS path
//     */
//    public static final String CSS_PATH = "css/";

    /**
     * constant represents PROGRAM LOGO
     */
    public static final String PROGRAM_LOGO = "logo32.png";//from logo16

    // icon filenames
    /**
     * constant represents icon path
     */
    public static final String ICONS_PATH = "img/";

    /**
     * constant represents button add
     */
    public static final String BUTTON_ADD = "helpers/icon_add_small_enabled.png";

    /**
     * constant represents button delete small
     */
    public static final String BUTTON_DEL_SMALL = "clist/icon_delete_small_black_8.png";
 
    /**
     * constant represents button delete small hi
     */
    public static final String BUTTON_DEL_SMALL_HI = "clist/icon_delete_small_white_8.png";
   
    /** constant represents kne button*/
    public static final String BUTTON_KNE 
    							= "analysis/icon_analysis_kne.png";
    
    /** constant represents usp button*/
    public static final String BUTTON_USP 
								= "analysis/icon_analysis_usp.png";
    
    /** constant represents dsp button*/
    public static final String BUTTON_DSP 
								= "analysis/icon_analysis_dsp.png";
    
    /** constant represents ste button*/
    public static final String BUTTON_STE 
								= "analysis/icon_analysis_ste.png";
    
    /** constant represents bcc button*/
    public static final String BUTTON_BCC 
								= "analysis/icon_analysis_bcc.png";
    
    /** constant represents ebc button*/
    public static final String BUTTON_EBC 
								= "analysis/icon_analysis_ebc.png";
    
    /** constant represents wcc button*/
    public static final String BUTTON_WCC 
								= "analysis/icon_analysis_wcc.png";
    
    /** constant represents hub button*/
    public static final String BUTTON_HUB 
								= "analysis/icon_analysis_hub.png";
    
    /** constant represents btc button*/
    public static final String BUTTON_BTC 
								= "analysis/icon_analysis_btc.png";
    
    /** constant represents bac button*/
    public static final String BUTTON_BAC 
								= "analysis/icon_analysis_bac.png";
    /**
     * constant represents button zoom in
     */
    public static final String BUTTON_ZOOM_IN = "helpers/icon_zoom_in_off.png";
    
    /** constant represents button zoom in when being clicked*/
    public static final String BUTTON_ZOOM_IN_CLICKED 
    							= "helpers/icon_zoom_in_on.png";
    /**
     * constant represents button zoom out	
     */
    public static final String BUTTON_ZOOM_OUT = "helpers/icon_zoom_out_off.png";
    
    /**constant represents button zoom out when being clicked*/
    public static final String BUTTON_ZOOM_OUT_CLICKED 
    							= "helpers/icon_zoom_out_on.png";
    
    /**
     * constant represents button rotate
     */
    public static final String BUTTON_ROTATE = "helpers/icon_rotate_enabled.png";
    
    /** constant represents button rotate when being clicked*/
    public static final String BUTTON_ROTATE_CLICKED 
    							= "helpers/icon_rotate_on.png";
    
    /**
     * constant represents button center
     */
    public static final String BUTTON_CENTER = "helpers/icon_center_node_off.png";
    
    /** constant represents button center when being clicked*/
    public static final String BUTTON_CENTER_CLICKED 
    							= "helpers/icon_center_node_on.png";
    
    /**
     * constant represents button refresh
     */
    public static final String BUTTON_REFRESH = "helpers/icon_refresh_enabled.png";

    /**
     * constant represents button layout fr
     */
    public static final String BUTTON_LAYOUT_FR = "analysis/icon_layout_fr_off.png";
    
    /**
     * constant represents button layout fr when being clicked
     */
    public static final String BUTTON_LAYOUT_FR_CLICKED 
    							= "analysis/icon_layout_fr.png";

    /**
     * constant represents button circle
     */
    public static final String BUTTON_LAYOUT_CIRCLE = "analysis/icon_layout_cr_off.png";

    /**
     * constant represents button circle when being clicked
     */
    public static final String BUTTON_LAYOUT_CIRCLE_CLICKED
    						= "analysis/icon_layout_cr.png";
    
    /**
     * constant represents button layout kk
     */
    public static final String BUTTON_LAYOUT_KK = "analysis/icon_layout_kk_off.png";

    /**
     * constant represents button layout kk when being clicked
     */
    public static final String BUTTON_LAYOUT_KK_CLICKED 
    							= "analysis/icon_layout_kk.png";
    /**
     * constant represents button layout isom
     */
    public static final String BUTTON_LAYOUT_ISOM = "analysis/icon_layout_som_off.png";
    
    /**
     * constant represents button layout isom when being clicked
     */
    public static final String BUTTON_LAYOUT_ISOM_CLICKED 
    							= "analysis/icon_layout_som.png";
    
    /**
     * constant represents button layout dag
     */
    public static final String BUTTON_LAYOUT_DAG = "analysis/icon_layout_kne_off.png";
    /**
     * constant represents button layout dag
     */
    public static final String BUTTON_LAYOUT_DAG_CLICKED = "analysis/icon_layout_kne.png";

    /**
     * constant represents button layout spring
     */
    public static final String BUTTON_LAYOUT_SPRING = "analysis/icon_layout_sp_off.png";
    
    /**
     * constant represents button layout spring when being clicked
     */
    public static final String BUTTON_LAYOUT_SPRING_CLICKED 
    							= "analysis/icon_layout_sp.png";
    /**
     * constant represents button graph clear selection
     */
    public static final String BUTTON_GRAPH_CLEAR_SELECTIONS = "helpers/icon_clear_enabled.png";        
    
    /** constant represents button graph clear selection when being clicked*/
    public static final String BUTTON_GRAPH_CLEAR_SELECTIONS_CLICKED 
    				= "helpers/icon_clear_on.png";
    
    /**
     * constant represents button graph ego node
     */
    public static final String BUTTON_GRAPH_EGO_NODE = "analysis/icon_ego_node_off.png";
    
    /**
     * constant represents button graph ego node on
     */
    public static final String BUTTON_GRAPH_EGO_NODE_CLICKED = "analysis/icon_ego_node_on.png";

    /**
     * constant represents button graph show labels
     */
    public static final String BUTTON_GRAPH_SHOW_LABELS = "helpers/icon_labels_enabled.png";
    public static final String BUTTON_GRAPH_SHOW_LABELS_OFF = "helpers/icon_labels_off.png";
    
    /**
     * constant represents button graph show labels when being clicked
     */
    public static final String BUTTON_GRAPH_SHOW_LABELS_CLICKED 
    							= "helpers/icon_labels_enabled.png";

    /**
     * constant represents button graph person
     */
    //public static final String BUTTON_GRAPH_PERSONS = "helpers/icon_entity_person_off.png";
    public static final String BUTTON_GRAPH_PERSONS_ENABLE = "helpers/icon_entity_person_enabled.png";
    public static final String BUTTON_GRAPH_PERSONS_DISABLE = "helpers/icon_entity_person_off.png";

    /**
     * constant represents button graph organization
     */
    //public static final String BUTTON_GRAPH_ORGANIZATIONS = "helpers/icon_entity_org_off.png";
    public static final String BUTTON_GRAPH_ORGANIZATIONS_ENABLE = "helpers/icon_entity_org_enabled.png";
    public static final String BUTTON_GRAPH_ORGANIZATIONS_DISABLE = "helpers/icon_entity_org_off.png";

    /**
     * constant represents button simple decoration
     */
    public static final String BUTTON_SIMPLE_DECORATION_OFF = "helpers/icon_simple_nodes_off.png";
    /**
     * constant represents button simple decoration when being clicked
     */
    public static final String BUTTON_SIMPLE_DECORATION_CLICKED 
    						= "helpers/icon_simple_nodes_enabled.png";
    /**
     * constant represents button antialias
     */
    public static final String BUTTON_ANTIALIAS = "helpers/icon_antialias_off.png";
    
    /**
     * constant represents button antialias when being clicked
     */
    public static final String BUTTON_ANTIALIAS_CLICKED 
    							= "helpers/icon_antialias_enabled.png";

    // logger
    /**
     * name of logger
     */
    public static final String LOGGER_NAME = com.nn.ishop.client.Main.class.getName();

    /**
     * an array that represents levels of log
     */
    public static final String[] LOG_LEVELS = { "ALL", "DEBUG", "WARN", "ERROR", "FATAL", "OFF" };

    /**
     * fond of active entity label
     */
    public static final Font UI_ACTIVE_ENTITY_LABEL_FONT = new Font("Arial", Font.BOLD, 18);

    /**
     * font of  clist title
     */
    public static final Font UI_CLIST_TITLE_FONT = new Font("Arial", Font.BOLD, 18);

    /**
     * fond of ana title
     */
    public static final Font UI_ANA_TITLE_FONT = new Font("Arial", Font.BOLD, 14);

    /**
     * fond of subtitle
     */
    public static final Font UI_SUBTITLE_FONT = new Font("Arial", Font.BOLD, 12);

    /**
     * default fond
     */
    public static final Font UI_DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    // networks display
    /**
     * monospace fond
     */
    public static final String MONOSPACED_FONT1 = "Courier New";

    /**
     * fr layout
     */
    public static final int FR_LAYOUT = 1;

    /**
     * circle layout
     */
    public static final int CIRCLE_LAYOUT = 2;

    /**
     * spring layout
     */
    public static final int SPRING_LAYOUT = 3;

    /**
     * dag layout
     */
    public static final int DAG_LAYOUT = 4;

    /**
     * isom layout
     */
    public static final int ISOM_LAYOUT = 5;

    /**
     * kk layout
     */
    public static final int KK_LAYOUT = 6;

    /**
     * map of URL
     */
    public static final String mapURL = "res\\geo\\worldmap\\country_col_region.shp"; //"D:\\CRYPTO\\repository\\coding\\java_technology\\gis\\world\\country_col_region.shp";

    /**
     * html label header
     */
    public static final String HTML_LABEL_HEADER = "<html>";

    /**
     * html label footer
     */
    public static final String HTML_LABEL_FOOTER = "</html>";

    /**
     * empty
     */
    public static final String EMPTY = "unknown";

    /**
     * none
     */
    public static final String NONE = "- none -";

    /**
     * tag none
     */
    public static final String TAG_NONE = "[No Tags]";

    /**
     * vv decoration default
     */
    public static final int VV_DECORATION_DEFAULT = 0;

    /**
     * vv decoration simple
     */
    public static final int VV_DECORATION_SIMPLE = 1;

    /**
     * vv decoration full
     */
    public static final int VV_DECORATION_FULL = 2;

    /**
     * dashboard width
     */
    public static final int DASHBOARD_VV_WIDTH = 1024;

    /**
     * dashboard height
     */
    public static final int DASHBOARD_VV_HEIGHT = 768;

    /**
     * id of entity
     */
    public static final String V_ENTITY_ID = "eid";

    /**
     * label
     */
    public static final String V_LABEL = "vlabel";

    /**
     * type
     */
    public static final String V_TYPE = "vtype";

    /**
     * tagged
     */
    public static final String V_TAGGED = "tagged";

    /**
     * pressed
     */
    public static final String V_PRESSED = "PRESSED";

    /**
     * selected
     */
    public static final String V_SELECTED = "SELECTED";

    /**
     * neighbour
     */
    public static final String V_NEIGHBOUR = "NEIGHBOUR";

    /**
     * on path
     */
    public static final String EDGE_ID = "EDGE_ID";

    /**
     * on path
     */
    public static final String E_ON_PATH = "ON_PATH";

    /**
     * tags
     */
    public static final String V_TAGS = "TAGS";

    /**
     * ties number
     */
    public static final String E_TIES_NUMBER = "NUMBER OF TIES";

    /**
     * tie weight
     */
    public static final String E_TIE_WEIGHT = "TIE WEIGHT";

    /**
     * ascending
     */
    public static final String ASCENDING = "ASCENDING";

    /**
     * descending
     */
    public static final String DESCENDING = "DESCENDING";

    /**
     * true value
     */
    public static final Boolean TRUE = new Boolean(true);

    /**
     * false value
     */
    public static final Boolean FALSE = new Boolean(false);

    // relations strength
    /**
     * minimum value of strength
     */
    public static final int REL_STRENGTH_MIN = -5;

    /**
     * maximum value of strength
     */
    public static final int REL_STRENGTH_MAX = 5;

    /**
     * default value of strength
     */
    public static final int REL_STRENGTH_DEFAULT = 0;

    // Lists
    /**
     * selected none
     */
    public static final String SELECTED_NONE = "none";

    /**
     * selected actual
     */
    public static final String SELECTED_ACTUAL = "actual";

    /**
     * selected first
     */
    public static final String SELECTED_FIRST = "first";

    /**
     * selected new
     */
    public static final String SELECTED_NEW = "new";

    /**
     * selected main address
     */
    public static final String SELECTED_MAIN_ADDRESS = "main address";

    // TABS CODES
    /**
     * tab project
     */
    public static final int TAB_PROJECTS = 0;

    /**
     * tab entity person
     */
    public static final int TAB_ENTITY_PERSONS = 1;

    /**
     * tab entity orgs
     */
    public static final int TAB_ENTITY_ORGS = 2;

    /**
     * tab networks
     */
    public static final int TAB_NETWORKS = 3;

    /**
     * tab admin
     */
    public static final int TAB_ADMIN = 4;

    /**
     * tab log
     */
    public static final int TAB_LOG = 5;

    /**
     * tab entity overview
     */
    public static final int TAB_ENTITY_OVERVIEW = 0;

    /**
     * tab entity data
     */
    public static final int TAB_ENTITY_DATA = 1;

    /**
     * tab entity addresses
     */
    public static final int TAB_ENTITY_ADDRESSES = 2;

    /**
     * tab entity relations
     */
    public static final int TAB_ENTITY_RELATIONS = 3;

    /**
     * tab entity tags
     */
    public static final int TAB_ENTITY_TAGS = 4;

    /**
     * tab project overview
     */
    public static final int TAB_PROJECT_OVERVIEW = 0;

    /**
     * tab project data
     */
    public static final int TAB_PROJECT_DATA = 1;

    /**
     * tab project persons
     */
    public static final int TAB_PROJECT_PERSONS = 2;

    /**
     * tab project organizations
     */
    public static final int TAB_PROJECT_ORGANIZATIONS = 3;

    /**
     * tab project security
     */
    public static final int TAB_PROJECT_SECURITY = 4;

    /**
     * tab net control info
     */
    public static final int TAB_NET_CONTROL_INFO = 0;

    /**
     * tab net control tags
     */
    public static final int TAB_NET_CONTROL_TAGS = 1;

    /**
     * tab net control analysis
     */
    public static final int TAB_NET_CONTROL_ANALYSIS = 2;

    /**
     * tab adm users
     */
    public static final int TAB_ADM_USERS = 0;

    /**
     * tab adm groups
     */
    public static final int TAB_ADM_GROUPS = 1;

    // styles
    /**
     * transparent
     */
    public static final Color TRANSPARENT = new Color(255, 255, 255, 0);

    /**
     * viz full ref
     */
    public static final Color VIZ_FULL_RED = new Color(0xff, 0x00, 0x00);

    /**
     * viz light grey
     */
    public static final Color VIZ_LIGHT_GREY = new Color(0xdd, 0xdd, 0xdd);

    /**
     * viz grey
     */
    public static final Color VIZ_GREY = new Color(0x8d, 0x8d, 0x8d);

    /**
     * viz medium blue
     */
    public static final Color VIZ_MEDIUM_BLUE = new Color(0x00, 0x99, 0xff);

    /**
     * viz light blue
     */
    public static final Color VIZ_LIGHT_BLUE = new Color(0xb9, 0xe7, 0xff);

    

    /**
     * ui st transparent
     */
    public static final Color UI_SIC_TRANSPARENT = new Color(255, 255, 255, 0);


    /**
     * ui textfield demension
     */
    public static final Dimension UI_TF_DIMENSION = new Dimension(200, 20);

    /**
     * ui textfield border
     */
    public static final Border UI_TF_BORDER = new CompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, UI_SIC_GRAY),
            BorderFactory.createEmptyBorder(0, 2, 0, 0));

    /**
     * ui label border
     */
    public static final Border UI_LABEL_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 5);
    
    /** TODO font/color definition*/
    
    /** window background*/
    public static final Color C_WINDOW_BG = 
    	new Color(224, 223, 227);
    
    /** active top buttons, tabs or rows on the left side*/
    public static final Color C_ACTIVE_TOP_BTR = 
    	new Color(153, 186, 243);//new Color(102, 102, 102);
    
    /** inactive tabs, rows on the left*/
    public static final Color C_INACTIVE_TOP_TR = 
    	new Color(224, 223, 227);//Color(233, 233, 233);
    
    /** inactive tab border*/
    public static final Color C_INACTIVE_TAB_BD = 
    	new Color(187, 187, 187);
    
    /** button bar background*/
    public static final Color C_BUTTON_BAR_BG =  new Color(233, 233, 233);
    
    /** row headings background*/
    public static final Color C_ROW_HEADINGS_BG = 
    	new Color(187, 187, 187);
    
    /** button background*/
    public static final Color C_BUTTON_BG = 
    	new Color(241, 241, 241);
    
    /** button border*/
    public static final Color C_BUTTON_BD = 
    	new Color(187,187, 187);
    
    /** active row*/
    public static final Color C_UNASSOCIATED_ROW = 
//    	new Color(237, 239, 243);
    new Color(255, 255, 255);
    
    /** active row*/
    public static final Color C_ACTIVE_ROW = 
    	new Color(255, 255, 255);
    
    /** every second row*/
    public static final Color C_SECOND_ROW = 
    	new Color(245, 250, 252);
    
    /** progress bar*/
    public static final Color C_PROGRESS_BAR = 
    	Color.WHITE;//new Color(166, 235, 158);
    
    /** form fields top and left*/
    public static final Color C_FORM_FIELD_TL = 
    	new Color(153, 153, 153);
    
    /** form field button and right*/
    public static final Color C_FORM_FIELD_BR = 
    	new Color(204, 204, 204);
    
    /** form field button and right*/
    public static final Color C_LINE_COLOR = 
    	new Color(208, 208, 208);
    /** white background*/
//    public static final Color C_WHITE_BACKGROUND =
//    	new Color(255, 255, 255);	
    
    /** white background*/
    public static final Color C_BLACK_FOREGROUND =
    	new Color(0, 0, 0);
    
    
    /** blue color in column name*/
    public static final Color C_BLUE_NAME_COLOR =
    	new Color(63, 132, 202);//new Color(139, 182, 224);//new Color(134, 178, 222);
    
    /** table border color */
    public static final Color C_TABLE_BORDER_COLOR =
    	new Color(220,220,220);
    
    /** blue color in column name*/
    public static final Color C_TOOLBAR_BACKGROUND_COLOR =
    	new Color(224, 223, 227);
    
    /** grey color in selected row*/
    public static final Color C_SELECTED_ROW_COLOR =
    	new Color(/*161, 190, 240*/150,182,212);
    
    /** grey color in selected row*/
    public static final Color C_COMMON_BACKGROUND_COLOR =
    	new Color(242, 242, 242);
    
    /** grey color in selected row*/
    public static final Color C_COMMON_SELECTED_TAB_COLOR =
    	new Color(102, 102, 102);
    //NGHIA18122007 - button color
    /** background for button bar*/
    public static final Color C_BUTTONBAR_BACKGROUND_COLOR =
    	new Color(229, 229, 229);
    
    /** background for button bar*/
    public static final Color C_TABLELINE_BACKGROUND_COLOR =
    	new Color(237, 237, 237);
    
    /** project tab/details tab */
    public static final Font COMMON_TITLE_HEADING_FONT = 
    	new Font("Arial", Font.BOLD, 15);
    
    public static final Font COMMON_LABEL_FONT = 
    	new Font("Arial", Font.BOLD, 12);
    
    public static final Font COMMON_LABEL_PLAIN_FONT = 
    	new Font("Arial", Font.PLAIN, 12);
    
    public static final Font COMMON_SMALL_LABEL_FONT = 
    	new Font("Arial", Font.BOLD, 10); 
    public static final Font TAG_CMD_SMALL_LABEL_FONT = 
    	new Font("Arial", Font.BOLD, 8); 
    
    public static final Font PAGING_BUTTON_FONT = 
    	new Font("Arial", Font.PLAIN, 11);
	
	public static final Border TIGHT_BORDER = BorderFactory.createEtchedBorder();


}
