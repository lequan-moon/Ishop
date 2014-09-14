package com.nn.ishop.client.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import com.nn.ishop.client.gui.components.CButton2;
import com.nn.ishop.client.gui.components.CToggleButton;
import com.nn.ishop.client.gui.components.ScrollableBar;
import com.nn.ishop.client.util.Util;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.EntityRank;
import com.nn.ishop.server.dto.EntityRelation;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.PolarPoint;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.algorithms.shortestpath.ShortestPathUtils;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.DefaultVisualizationModel;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationModel;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.SatelliteVisualizationViewer;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.picking.PickedInfo;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.BasicEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.BasicEdgeRenderer;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.BasicVertexRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.BidirectionalTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformerDecorator;
import edu.uci.ics.jung.visualization.transform.shape.CRendererPane;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import edu.uci.ics.jung.visualization.transform.shape.ShapeTransformer;
import edu.uci.ics.jung.visualization.transform.shape.TransformingGraphics;
import edu.uci.ics.jung.visualization.util.Animator;
import edu.uci.ics.jung.visualization.util.VertexShapeFactory;

/**
 * Main network graph
 * @author connect.shift-think.com
 *
 */
public class NetworkGraphPane extends JPanel{
	protected final Color MARKED_VERTEX_OUTER_COLOR = new Color(0xFF,0x00,0x00);
	protected final Color NEIGHTBOUR_HIGHLIGHT_COLOR = new Color(0xFF,0x00,0x00);

	private static final long serialVersionUID = -5979632469120332084L;

	/** Use weight of edge vector as input data to draw edge based on each weight */
	protected EdgeWeightStrokeFunction<EntityRelation> ewcs;

	/** Keep all selected vertices by mouse double clicking on */
	protected Vector<AbstractIshopEntity> markedVertices = new Vector<AbstractIshopEntity>();

	/** Contain all start nodes */
	protected Vector<AbstractIshopEntity> startNodes = new Vector<AbstractIshopEntity>();

	/** Store references to selected Edge */
	protected Vector<EntityRelation> selectedEdges = new Vector<EntityRelation>();

	/** Define how to paint Edge and its neighbors */
	protected VertexStrokeHighlight<AbstractIshopEntity, EntityRelation> vertexStrokeHighlight;

	/** Overview panel for this graph*/
	private NetworkOverviewPanel overviewPanel = new NetworkOverviewPanel();

	/** Define visualization model*/
	private VisualizationModel<AbstractIshopEntity, EntityRelation> vm;

	/** A sub type of Directed sparse graph use for display Radial Layout */
	private DelegateForest<AbstractIshopEntity, EntityRelation> delegateForest;

	/** Radial Layout */
    private RadialTreeLayout<AbstractIshopEntity, EntityRelation> radialLayout;

    /**
     * a paint able object that can be integrated into VisualizationViewer for post painting actions
     * usage: vv1.addPreRenderPaintable(rings)
     */
    private VisualizationServer.Paintable rings;

    /**
     * vv1 is the main visualization
     */
    private VisualizationViewer<AbstractIshopEntity, EntityRelation> vv1;

    /**
     * vv2 is the satellite visualization (overview)
     */
    private SatelliteVisualizationViewer<AbstractIshopEntity, EntityRelation> vv2;

    /**
     * Zooming value
     */
    final JLabel kneValueLabel = new JLabel(" ");
    final JLabel zoomValueLabel = new JLabel("100%");
    private int currentZoomLevel = 100;

    // Slider bar for zooming action
    final JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL,10,1000,100);

    NodeLabelMode showNodeLabelMode = NodeLabelMode.HIDE;
    enum NodeLabelMode {ALL,ORGANIZATION,PERSON,HIDE};


    PersonDisplayMode showPersonMode = PersonDisplayMode.ALL;
    enum PersonDisplayMode {ALL,MALE,FEMALE,HIDE};

    private boolean showOrganisationNodes = true;

    EdgeLabelMode showEdgeLabelMode = EdgeLabelMode.HIDE;
    enum EdgeLabelMode {ALL,BUSINESS,FAMILY,PRIVATE,HIDE};

    KNELabelMode showKNELabelMode = KNELabelMode.HIDE;
    enum KNELabelMode {ALL,ORGANIZATION,PERSON,HIDE};

    /** define pane type*/
    private PaneType paneType = PaneType.ROOT;
    /** 04 type of this pane*/
    public static enum PaneType{ROOT,KNE,BY, BC,HUB,TAG}
    private boolean highlightKNE1 = true;

	/**
	 * store Edges on path
	 */
	private List<EntityRelation> shortestEdgesList = new LinkedList<EntityRelation>();
	/**
	 * store Vertices on path
	 */
	private List<AbstractIshopEntity> shortestVeticesList = new LinkedList<AbstractIshopEntity>();

	/**
	 * Vector contain GUI Listeners
	 */
	private Vector<GUIActionListener> guiListenerVector = new Vector<GUIActionListener>();

	/** variants use for shape function use for Analysis action*/
	protected final int MAX_ORGANISATION_SIZE = 92;
	protected final int MAX_PERSON_SIZE = 72;

	protected final int MIN_ORGANISATION_SIZE = 32;
	protected final int MIN_PERSON_SIZE = 22;

	protected int maxPersonRank = -1;
	protected int maxOrganisationRank = -1;
	protected final int MAX_VERTEX_FONT_SIZE = 48;
	protected final int MIN_VERTEX_FONT_SIZE = 11;

	private Vector<String> selectedTagNameVector = new Vector<String>();

	/** Current used layout class */
	private /*Class<? extends */Layout<AbstractIshopEntity, EntityRelation>/*>*/ layoutClass;

	public NetworkGraphPane(){
	}

	/**
	 * Build satellite visualization viewer which can be opened by context menu
	 */
	@SuppressWarnings("unchecked")
	final void buildSatelliteViewer()
	{
		vv2 = new SatelliteVisualizationViewer<AbstractIshopEntity, EntityRelation>(vv1);
        vv2.getRenderContext().setVertexStrokeTransformer(vertexStrokeHighlight);
        vv2.getRenderContext().setRendererPane(new CRendererPane());
        vv2.getRenderer().setVertexLabelRenderer(new CVertexLabelRenderer());
        vv2.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.N);
        vv2.getRenderer().setVertexRenderer(new CVertexRenderer());
        // Edge draw transformer
        vv2.getRenderContext().setEdgeDrawPaintTransformer(
        		new CEdgePaintFunction(
				vv1.getPickedEdgeState(),
				new Color(0xCC,0xCC,0xCC),
        		new Color(0x99,0x99,0x99)));

        vv2.getRenderContext().setVertexFillPaintTransformer(
        		new PickableVertexPaintTransformer<AbstractIshopEntity>(
        				vv1.getPickedVertexState(), new Color(0x66,0x99,0xFF), new Color(0x33,0x66,0xFF)));

        ScalingControl vv2Scaler = new CrossoverScalingControl();
        vv2.scaleToLayout(vv2Scaler);
        vv2.setVertexToolTipTransformer(new ToStringLabeller<AbstractIshopEntity>());

        vv2.getRenderContext().setVertexLabelTransformer(vv1.getRenderContext().getVertexLabelTransformer());

        // draw none solid arrow
        vv2.getRenderContext().setArrowFillPaintTransformer(new PickableEdgePaintTransformer<EntityRelation>(
				vv1.getPickedEdgeState(), new Color(0xD9,0xD9,0xD9), new Color(0x66,0x99,0xFF)));
        vv2.getRenderContext().setArrowDrawPaintTransformer(new ConstantTransformer(new Color(0xD9,0xD9,0xD9)));
        vv2.getRenderContext().setVertexShapeTransformer(new CVertexShapeFunction());
	}
	private NetworkSparseGraph<AbstractIshopEntity , EntityRelation> mainGraph;

	/**
	 * Load start nodes information from resource,
	 * currently it use the first vertex on Graph for start node
	 * the values stored in <code>startNode<code> vector
	 */
	void loadStartNodes()
	{
		Collection<AbstractIshopEntity> allVertices = this.mainGraph.getVertices();
		Iterator<AbstractIshopEntity> itr = allVertices.iterator();
		while(itr.hasNext())
		{
			startNodes.add(itr.next());
			break;
		}
	}
	GraphZoomScrollPane graphZoomScrollPane;
	/**
	 * Get graph panel base on this instance
	 * build all visualization, this must be manually invoked from outside of this class
	 * whith specified Graph object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void init(NetworkSparseGraph<AbstractIshopEntity , EntityRelation> mainGraph/*,
			Dimension preferedSize,
			PaneType paneMode*/)
	{
		this.mainGraph = mainGraph;
//		this.paneType = paneMode;
		loadStartNodes();
		// create one layout for the graph
		FRLayout<AbstractIshopEntity, EntityRelation> frLayout
        = new FRLayout<AbstractIshopEntity, EntityRelation>(mainGraph);
        frLayout.setMaxIterations(5);

        delegateForest = new DelegateForest(mainGraph);
        radialLayout = new RadialTreeLayout<AbstractIshopEntity, EntityRelation>(delegateForest);

    	Dimension d = null;

        	radialLayout.setSize(new Dimension(600, 600));

        rings = new Rings();
        	vm = new DefaultVisualizationModel<AbstractIshopEntity, EntityRelation>(frLayout);

        /** Main Visual Viewer  */
        	vv1 =  new VisualizationViewer<AbstractIshopEntity, EntityRelation>(
        			vm, getPreferredSize());
        vv1.setBackground(Color.white);

        // Define stroke transformer
        vertexStrokeHighlight = new VertexStrokeHighlight<AbstractIshopEntity,EntityRelation>(mainGraph,
        		vv1.getPickedVertexState());
        vertexStrokeHighlight.setHighlight(true);
        vv1.getRenderContext().setVertexStrokeTransformer(vertexStrokeHighlight);

        ewcs = new EdgeWeightStrokeFunction<EntityRelation>(mainGraph.getEdgeWeight());
        // Define labeller for Edge
        vv1.getRenderContext().setEdgeLabelTransformer(new EdgeStringLabeller<EntityRelation>());

        /** IMPORTANT: set custom RendererPane use for both edges and vertices */
        vv1.getRenderContext().setRendererPane(new CRendererPane());

        // Set DRAW transformer for Edge
        vv1.getRenderContext().setEdgeDrawPaintTransformer(
        		new CEdgePaintFunction(
        				vv1.getPickedEdgeState(),
        				new Color(0xCC,0xCC,0xCC),
                		new Color(0x99,0x99,0x99)));
        // set FILL transformer for vertex
        vv1.getRenderContext().setVertexFillPaintTransformer(
        		new CVertexPaintTransformer(
        				vv1.getPickedVertexState(),
        				new Color(0x66,0x99,0xFF),
        				new Color(0x33,0x66,0xFF)));
        // use toString labeller for vertex
        vv1.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<AbstractIshopEntity>());
        /** IMPORTANT: set custom LabelRenderer*/
        vv1.getRenderer().setVertexLabelRenderer(new CVertexLabelRenderer());
        // position of label for vertex
        vv1.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.N);
        // customize vertex renderer
        vv1.getRenderer().setVertexRenderer(new CVertexRenderer());
        vv1.getRenderer().setEdgeRenderer(new CEdgeRenderer());
        // customize edge label renderer just for foreground color
        vv1.getRenderer().setEdgeLabelRenderer(new CEdgeLabelRenderer());
        vv1.getRenderContext().setVertexShapeTransformer(new CVertexShapeFunction());

        /** <NOT USED> add default listener for ToolTips */
        //visualizationViewer.setVertexToolTipTransformer(new ToStringLabeller<AbstractIshopEntity>());

        vv1.getRenderContext().setEdgeStrokeTransformer(ewcs);

        // draw none solid arrow
        vv1.getRenderContext().setArrowFillPaintTransformer(
        		new CEdgePaintFunction(
				vv1.getPickedEdgeState(),
				new Color(0xCC,0xCC,0xCC),
        		new Color(0x99,0x99,0x99)));
        vv1.getRenderContext().setArrowDrawPaintTransformer(
        		new CEdgePaintFunction(
				vv1.getPickedEdgeState(),
				new Color(0xCC,0xCC,0xCC),
        		new Color(0x99,0x99,0x99)));

        //vv1.setToolTipText(Language.getInstance().getString("graph.tooltip"));
        // Show tooltip after 5 seconds
        ToolTipManager.sharedInstance().setDismissDelay(5000);

        // Use Zoom ScrollPane
        /*GraphZoomScrollPane*/ graphZoomScrollPane = new GraphZoomScrollPane(vv1);
        // Customize modal graph mouse
        DefaultModalGraphMouse<AbstractIshopEntity, EntityRelation> gm = new CGraphMouse();
        vv1.setGraphMouse(gm);

        CPopupGraphMousePlugin mousePluggin = new CPopupGraphMousePlugin();
        gm.add(mousePluggin);

        //==================== Overview tool bar is always hide after built ========//
        buildSatelliteViewer();
        overviewPanel.add(vv2);
        overviewPanel.pack();
        // Locate the overview
        Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

        overviewPanel.setLocation(new Point(scrDim.width - overviewPanel.getPreferredSize().width - 10,
        		scrDim.height - overviewPanel.getPreferredSize().height -60 ));
        /** Dialog just appear by context menu */
		overviewPanel.setVisible(false);
		overviewPanel.repaint();
        JPanel networkToolBarPanel = new JPanel(new BorderLayout());
        networkToolBarPanel.add(buildToolBar(), BorderLayout.NORTH);
        ScrollableBar mainScrollableBar = new ScrollableBar(networkToolBarPanel);
        JPanel networkPanel = new JPanel(new BorderLayout());
        networkPanel.add(graphZoomScrollPane, BorderLayout.CENTER);
        networkPanel.add(mainScrollableBar, BorderLayout.NORTH);

        setLayout(new BorderLayout());
        add(networkPanel, BorderLayout.CENTER);
        addComponentListener(new ComponentAdapter(){
        	@Override
        	public void componentResized(ComponentEvent e) {
        		if(!layoutCompleted && !paneType.equals(PaneType.KNE))
        		{
        			switchGraphLayout(new FRLayout<AbstractIshopEntity, EntityRelation>(getGraph()));
        			layoutCompleted = true;
        		}
        		super.componentResized(e);
        	}
        });
//        if(!paneMode.equals(PaneType.KNE))
        	layoutClass = frLayout;
        // Adjust position of layout for KNE layout
//        if(paneMode.equals(PaneType.KNE))
//        	vv1.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).translate(
//        			Math.round((vv1.getSize().width - d.width)/2), 0);
	}
    boolean layoutCompleted = false;
	/**
	 * Overview dialog contain vv2 - a satellite visualization viewer
	 * @author connect@shift-think.com
	 *
	 */
	class NetworkOverviewPanel extends JDialog
	{
		private static final long serialVersionUID = 7279149358082912390L;
		public NetworkOverviewPanel() {
			setAlwaysOnTop(true);
			setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			getContentPane().setBackground(new Color(0, 255, 0, 125));
		}
	}

	/**
	 * Transform paint with pickable support and based on Entity Type, person or organization
	 * @author connect.shift-think.com
	 *
	 */
	class CVertexPaintTransformer extends PickableVertexPaintTransformer<AbstractIshopEntity>
	{
		final Color PERSON_NORMAL_COLOR = new Color(0x66,0x99,0xFF);
		final Color PERSON_HIGHLIGHT_COLOR = new Color(0x33,0x66,0xFF);

		final Color ORGANISATION_NORMAL_COLOR = new Color(0x00,0x33,0xCC);
		final Color ORGANISATION_HIGHLIGHT_COLOR = new Color(0x00,0x00,0x99);

		public CVertexPaintTransformer(PickedInfo<AbstractIshopEntity> pi,
				Paint fill_paint, Paint picked_paint) {
			super(pi, fill_paint, picked_paint);
		}
		public Paint transform(AbstractIshopEntity v)
	    {
	        if (pi.isPicked(v))
	        		return PERSON_HIGHLIGHT_COLOR;
	        else
	        		return PERSON_NORMAL_COLOR;
	    }

	}
	/**
	 * AND tag mode: false mean show any tag(OR),
	 * true mean show only entities which contain all selected tags
	 */
	private boolean andTaggedMode = false;

	private boolean isTaggedVertex(AbstractIshopEntity v)
	{
		boolean ret = false;
		Map<String, List<AbstractIshopEntity>> tagEntities = mainGraph.getTagsEntities();
		for(String tagName:selectedTagNameVector)
		{
			if(tagEntities.get(tagName) != null
					&& tagEntities.get(tagName).contains(v))
				ret = true;
		}
		if(andTaggedMode)// AND tag mode
		{
			boolean andRet = true;
			for(String tagName:selectedTagNameVector)
			{
				if(tagEntities.get(tagName) == null
						|| !tagEntities.get(tagName).contains(v))
					andRet = false;
			}
			ret = ret & andRet;
		}

		return ret;
	}
	/**
	 * Transform Shape for vertex as Ellipse Shape based on entity type
	 * (also implements some method support for highlight, mark,... vertices)
	 * @author connect.shift-think.com
	 * @see com.nn.ishop.client.gui.shiftthink.connect.client.gui.NetworkGraphPane.CVertexSizeFunction
	 * @see edu.uci.ics.jung.visualization.util.VertexShapeFactory.VertexShapeFactory
	 * @see edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer
	 */
	class CVertexShapeFunction extends EllipseVertexShapeTransformer<AbstractIshopEntity>
    {
		CVertexShapeFunction() {
			this.vsf = new CVertexSizeFunction(22);
            factory = new VertexShapeFactory<AbstractIshopEntity>(vsf, varf);
        }
		/**
		 * Create a circle surround the specified vertex as selected state
		 * @param v specified vertex
		 * @param x an X coordinator of vertex on graph layout
		 * @param y an Y coordinator of vertex on graph layout
		 * @return an ellipse shape surround the current vertex
		 */
		public Shape getSelectedShape(AbstractIshopEntity v, float x, float y)
		{
                int size = MIN_PERSON_SIZE;
               	size = calculatePersonNodeSize(v);

        		Ellipse2D shapeOutside = new Ellipse2D.Float();
        		float width = size + 8f;
                float height = width * varf.transform(v);
                // Max size of Person is 30
                shapeOutside.setFrame(x - (width/2), y - (width/2), width, height);
        		return shapeOutside;
        }


		/**
		 * Create a circle surround the specified vertex as founded state
		 * @param v specified vertex
		 * @param x an X coordinator of vertex on graph layout
		 * @param y an Y coordinator of vertex on graph layout
		 * @return an ellipse shape surround the current vertex
		 */
		public Shape getFoundedShape(AbstractIshopEntity v, float x, float y)
		{
                int size = MIN_PERSON_SIZE;
               	size = calculatePersonNodeSize(v);

        		Ellipse2D shapeOutside = new Ellipse2D.Float();
        		float width = size + 20f;
                float height = width * varf.transform(v);

                shapeOutside.setFrame(x-(width/2), y-(width/2), width, height);
        		return shapeOutside;
        }
		public Shape getTaggedShape(AbstractIshopEntity v, float x, float y)
		{
                int size = MIN_PERSON_SIZE;
               	size = calculatePersonNodeSize(v);

        		Ellipse2D shapeOutside = new Ellipse2D.Float();
        		float width = (size<30)?(size - 5f): size-10f;
                float height = width * varf.transform(v);

                shapeOutside.setFrame(x-(width/2), y-(width/2), width, height);
        		return shapeOutside;
        }
        @Override
        public Shape transform(AbstractIshopEntity v) {
            return super.transform(v);
        }
    }


	/**
	 * Transform size for vertex
	 * @author connect@shift-think.com
	 *
	 */
	class CVertexSizeFunction implements Transformer<AbstractIshopEntity,Integer>
	 {
	    	int size;
	    	int sizeStep;
	        public CVertexSizeFunction(Integer size) {
	            this.size = size;
	        }
	        public Integer transform(AbstractIshopEntity v) {
        		return new Integer(calculatePersonNodeSize(v));	            
	        }


	 }
	 /**
	  * Analysis method calculating person node size based on ranking
	 * @param v
	 * @return
	 */
	private int calculatePersonNodeSize(AbstractIshopEntity v)
     {
     	int ret = MIN_PERSON_SIZE;
     	int step = 0;
//     	try {
//				if (htPersonAnalysis.size() >0 ) {
//					int rank = htPersonAnalysis.get(v.getId()).getRank();
//					step = Math.round((MAX_PERSON_SIZE - MIN_PERSON_SIZE)/maxPersonRank);
//					if(step == 0) step = 1;
//					int size = MAX_PERSON_SIZE - (rank * step);
//					if(size < MIN_PERSON_SIZE) size = MIN_PERSON_SIZE;
//					return size;
//				}
//			} catch (Exception e) {
//				System.out.println("Error getting person node size "+v.getName());
//			}
			return ret;
     }

    /**
     * Analysis method calculating organization node size based on ranking
     * @param v
     * @return
     */
    private int calculateOrganizationNodeSize(AbstractIshopEntity v)
     {
     	int ret = MIN_ORGANISATION_SIZE;
//			int step = 0;
//     	try {
//				if (htOrganizationAnalysis.size() >0) {
//					int rank = htOrganizationAnalysis.get(v.getId()).getRank();
//					if((MAX_ORGANISATION_SIZE - MIN_ORGANISATION_SIZE)/maxOrganisationRank <1.0d)
//						step = 1;
//					else
//						step = Math.round((MAX_ORGANISATION_SIZE - MIN_ORGANISATION_SIZE)/maxOrganisationRank);
//					int size = MAX_ORGANISATION_SIZE - (rank * step);
//					if(size < MIN_ORGANISATION_SIZE) size = MIN_ORGANISATION_SIZE;
//					return size;
//
//				}
//			} catch (Exception e) {
//				System.out.println("Error getting size "+v.getName());
//			}
			return ret;
     }
    private int getMaxEntityRank(Hashtable<String, EntityRank> h)
    {
    	if(h.size()==0) return -1;
    	EntityRank[] ranks = new EntityRank[h.size()];
    	ranks = h.values().toArray(ranks);
    	Arrays.sort(ranks, EntityRank.rankComparatorAsd);
    	return ranks[ranks.length - 1].getRank();
    }

	/**
	 * Edge Label Renderer
	 * @author nghia
	 *
	 */
	class CEdgeLabelRenderer extends BasicEdgeLabelRenderer<AbstractIshopEntity,EntityRelation>
	{
		@Override
		public void labelEdge(RenderContext<AbstractIshopEntity, EntityRelation> rc,
				Layout<AbstractIshopEntity, EntityRelation> layout, EntityRelation e,
				String label) {
			Graph<AbstractIshopEntity, EntityRelation> graph = layout.getGraph();
			//== Check for enable or disable show edge o graph
	        Pair<AbstractIshopEntity> endpoints = graph.getEndpoints(e);
	        AbstractIshopEntity v1 = endpoints.getFirst();
	        AbstractIshopEntity v2 = endpoints.getSecond();

			PickedInfo<AbstractIshopEntity> vPi = rc.getPickedVertexState();
			PickedInfo<EntityRelation> ePi = rc.getPickedEdgeState();
    		boolean hasNeighbour = false;
    		//-- check only source vertex's neighbors
    		for(AbstractIshopEntity w : graph.getNeighbors(v1)) {
                if (vPi.isPicked(w))hasNeighbour = true;
            }
    		for(AbstractIshopEntity w : graph.getNeighbors(v2)) {
                if (vPi.isPicked(w))hasNeighbour = true;
            }
    		if(showEdgeLabelMode.equals(EdgeLabelMode.HIDE) && !ePi.isPicked(e) && !hasNeighbour)return;
    		if(showEdgeLabelMode.equals(EdgeLabelMode.BUSINESS) && !e.getEdgeType().equals(EntityRelation.EDGE_TYPE_BUSINESS)
    				&& !ePi.isPicked(e) && !hasNeighbour)return;
    		if(showEdgeLabelMode.equals(EdgeLabelMode.FAMILY) && !e.getEdgeType().equals(EntityRelation.EDGE_TYPE_FAMILY)
    				&& !ePi.isPicked(e) && !hasNeighbour)return;
    		if(showEdgeLabelMode.equals(EdgeLabelMode.PRIVATE) && !e.getEdgeType().equals(EntityRelation.EDGE_TYPE_PRIVATE)
    				&& !ePi.isPicked(e) && !hasNeighbour)return;



			if(label == null || label.length() == 0) return;

	        // don't draw edge if either incident vertex is not drawn
	        if (!rc.getEdgeIncludePredicate().evaluate(Context.<Graph<AbstractIshopEntity, EntityRelation>,EntityRelation>getInstance(graph,e)))
	            return;

	        if (!rc.getVertexIncludePredicate().evaluate(Context.<Graph<AbstractIshopEntity, EntityRelation>,AbstractIshopEntity>getInstance(graph,v1)) ||
	            !rc.getVertexIncludePredicate().evaluate(Context.<Graph<AbstractIshopEntity, EntityRelation>,AbstractIshopEntity>getInstance(graph,v2)))
	            return;

	        Point2D p1 = layout.transform(v1);
	        Point2D p2 = layout.transform(v2);
	        p1 = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p1);
	        p2 = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p2);
	        float x1 = (float) p1.getX();
	        float y1 = (float) p1.getY();
	        float x2 = (float) p2.getX();
	        float y2 = (float) p2.getY();

	        GraphicsDecorator g = rc.getGraphicsContext();
	        float distX = x2 - x1;
	        float distY = y2 - y1;
	        double totalLength = Math.sqrt(distX * distX + distY * distY);

	        double closeness = rc.getEdgeLabelClosenessTransformer().transform(
	        		Context.<Graph<AbstractIshopEntity, EntityRelation>,EntityRelation>getInstance(graph, e)).doubleValue();

	        int posX = (int) (x1 + (closeness) * distX);
	        int posY = (int) (y1 + (closeness) * distY);

	        int xDisplacement = (int) (rc.getLabelOffset() * (distY / totalLength));
	        int yDisplacement = (int) (rc.getLabelOffset() * (-distX / totalLength));

	        Component component = prepareRenderer(rc, rc.getEdgeLabelRenderer(), label,
	                rc.getPickedEdgeState().isPicked(e), e);

	        Dimension d = component.getPreferredSize();

	        Shape edgeShape = rc.getEdgeShapeTransformer().transform(Context.<Graph<AbstractIshopEntity, EntityRelation>,EntityRelation>getInstance(graph, e));

	        double parallelOffset = 1;

	        parallelOffset += rc.getParallelEdgeIndexFunction().getIndex(graph, e);

	        parallelOffset *= d.height;
	        if(edgeShape instanceof Ellipse2D) {
	            parallelOffset += edgeShape.getBounds().getHeight();
	            parallelOffset = -parallelOffset;
	        }


	        AffineTransform old = g.getTransform();
	        AffineTransform xform = new AffineTransform(old);
	        xform.translate(posX+xDisplacement, posY+yDisplacement);
	        double dx = x2 - x1;
	        double dy = y2 - y1;
	        if(rc.getEdgeLabelRenderer().isRotateEdgeLabels()) {
	            double theta = Math.atan2(dy, dx);
	            if(dx < 0) {
	                theta += Math.PI;
	            }
	            xform.rotate(theta);
	        }
	        if(dx < 0) {
	            parallelOffset = -parallelOffset;
	        }

	        xform.translate(-d.width/2, -(d.height/2-parallelOffset));
	        g.setTransform(xform);
	        if(!rc.getPickedEdgeState().isPicked(e)){
	        	g.drawEdgeLabel(component, (CRendererPane)rc.getRendererPane(), 0, 0, d.width, d.height, true, Color.LIGHT_GRAY);
		        g.setTransform(old);
	        }
	        else
	        {
	        	g.drawEdgeLabel(component, (CRendererPane)rc.getRendererPane(), 0, 0, d.width, d.height, true, new Color(0x66,0x99,0xFF));
		        g.setTransform(old);
	        }

		}
	}
	/**
	 * GraphMouse plugin for scaling action
	 * @author connect@shift-think.com
	 *
	 */
	class CScalingGraphMousePlugin extends ScalingGraphMousePlugin
	{
		public CScalingGraphMousePlugin(ScalingControl scaler, int modifiers,
				float in, float out) {
			super(scaler, modifiers, in, out);
			setZoomAtMouse(true);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			 boolean accepted = checkModifiers(e);
		        if(accepted == true) {
		        	int amount = e.getWheelRotation();
		        	if((currentZoomLevel <= zoomSlider.getMinimum() && amount <0)
		        			|| (currentZoomLevel >= zoomSlider.getMaximum() && amount > 0)
		        			) return;
			        if(amount > 0) {
			        	currentZoomLevel +=10;
			        } else if(amount < 0) {
			        	currentZoomLevel -=10;
			        }
			        updateZoomLevelValue();

		            VisualizationViewer<AbstractIshopEntity, EntityRelation>
		            vv = (VisualizationViewer<AbstractIshopEntity, EntityRelation>)e.getSource();
		            Point2D mouse = e.getPoint();
		            Point2D center = vv.getCenter();
		            if(zoomAtMouse) {
		                if(amount > 0) {
		                    scaler.scale(vv, in, mouse);
		                } else if(amount < 0) {
		                    scaler.scale(vv, out, mouse);
		                }
		            } else {
		                if(amount > 0) {
		                    scaler.scale(vv, in, center);
		                } else if(amount < 0) {
		                    scaler.scale(vv, out, center);
		                }
		            }
		            e.consume();
		            vv.repaint();
		        }
		}


	}
	/**
	 * CONNECT Graph mouse
	 * @author connect.shift-think.com
	 *
	 */
	class CGraphMouse extends DefaultModalGraphMouse<AbstractIshopEntity,EntityRelation>
	{
	    /* (non-Javadoc)
	     * @see edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse#loadPlugins()
	     */
	    protected void loadPlugins() {
	        pickingPlugin = new PickingGraphMousePlugin<AbstractIshopEntity,EntityRelation>();
	        animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<AbstractIshopEntity,EntityRelation>();
	        translatingPlugin = new TranslatingGraphMousePlugin(InputEvent.BUTTON1_MASK);
	        // replace scaling pluggin
	        scalingPlugin = new CScalingGraphMousePlugin(new CrossoverScalingControl(), 0, in, out);

	        rotatingPlugin = new RotatingGraphMousePlugin();
	        shearingPlugin = new ShearingGraphMousePlugin();

	        add(scalingPlugin);
	        setMode(Mode.TRANSFORMING);
	    }
	    /* (non-Javadoc)
	     * @see edu.uci.ics.jung.visualization.control.PluggableGraphMouse#mousePressed(java.awt.event.MouseEvent)
	     */
	    @SuppressWarnings("unchecked")
	    @Override
		public void mousePressed(MouseEvent e) {
	    	// go here
	    	final VisualizationViewer<AbstractIshopEntity,EntityRelation> vv =
	                (VisualizationViewer<AbstractIshopEntity,EntityRelation>)e.getSource();
            Point2D p = e.getPoint();

            GraphElementAccessor<AbstractIshopEntity,EntityRelation> pickSupport = vv.getPickSupport();
            final AbstractIshopEntity v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            final EntityRelation edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());

        	if(e.getClickCount() == 2 ){
                if(pickSupport != null) {
                    if(v!=null){
                    	if(markedVertices.contains(v)){
                    		markedVertices.remove(v);
//                    		callGUIActionListener(
//                    				new GUIActionEvent(NetworkGraphPane.this,
//                    						GUIActionEvent.NETWORK_REMOVE_SELECTED_NODE, v.getId()));
                    	}
                    	else
                    	{
                    		if(!startNodes.contains(v))
                    		{
                        		markedVertices.add(v);
//                        		callGUIActionListener(
//                        				new GUIActionEvent(NetworkGraphPane.this,
//                        						GUIActionEvent.NETWORK_ADD_SELECTED_NODE, v.getId()));
                    		}
                    	}

                    	vv.repaint();
                    }
                    /** No need to repaint vv1 use EdgePaintTransformer instead */
                    if(edge != null)selectedEdges.add(edge);
                }
        	}
        	if ((e.getClickCount() == 1) && (!e.isPopupTrigger()))
        	{
                 // change mode to picking if over a vertex
                 if(pickSupport != null) {
                     if( v!= null || edge != null)
                    	setMode(Mode.PICKING);
                     else
                    	setMode(Mode.TRANSFORMING);
                     /** No need to repaint vv1 use EdgePaintTransformer instead */
                     if(edge != null)selectedEdges.add(edge);
                 }
        	 }
	        super.mousePressed(e);
	    }
	    /* (non-Javadoc)
	     * @see edu.uci.ics.jung.visualization.control.PluggableGraphMouse#mouseReleased(java.awt.event.MouseEvent)
	     */
	    @Override
	    public void mouseReleased(MouseEvent e) {
	    	PickedState<AbstractIshopEntity> pickedState = vv1.getPickedVertexState();
	    	Set<AbstractIshopEntity> pickedSet = pickedState.getPicked();
	    	Vector<String> entityIdVector = new Vector<String>();

//	    	for(AbstractIshopEntity ent:pickedSet)
//	    		entityIdVector.add(ent.getId());

//	    	callGUIActionListener(
//    				new GUIActionEvent(NetworkGraphPane.this,
//    						GUIActionEvent.NETWORK_PICK_VERTEX, entityIdVector));

	    	super.mouseReleased(e);
	    }
	}

	/**
	 * Paint function for edge
	 * @author connect@shift-think.com
	 *
	 */
	class CEdgePaintFunction extends PickableEdgePaintTransformer<EntityRelation> {

		public CEdgePaintFunction(PickedInfo<EntityRelation> pi, Paint draw_paint,
				Paint picked_paint) {
			super(pi, draw_paint, picked_paint);
		}

		/* (non-Javadoc)
		 * @see edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer#transform(java.lang.Object)
		 */
		public Paint transform(EntityRelation e) {
			if(shortestEdgesList == null || shortestEdgesList.size() == 0)
			{
				if (pi.isPicked(e)) {
		            return picked_paint;
		        }
		        else {
		            return draw_paint;
		        }
			}
			if( isEdgeOnPath( e )) {
				return new Color(0x33, 0xCC, 0x00);
			} else {
				// lower color for highlight path
				return Color.LIGHT_GRAY;
			}
		}
	}


	/**
	 * Define weight of each for drawing when set weighted to true
	 * or do not draw weight of edge by setting weighted to false
	 * @author connect.shift-think.com
	 *
	 * @param <E>
	 */
	protected final static class EdgeWeightStrokeFunction<E>
    implements Transformer<EntityRelation,Stroke>
    {
        protected static final Stroke basic = new BasicStroke(1);
        protected static final Stroke heavy = new BasicStroke(1.4f);
        protected static final Stroke dotted = RenderContext.DOTTED;

        protected boolean weighted = false;
        protected Map<EntityRelation,Number> edge_weight;

        public EdgeWeightStrokeFunction(Map<EntityRelation,Number> edge_weight)
        {
            this.edge_weight = edge_weight;
        }

        public void setWeighted(boolean weighted)
        {
            this.weighted = weighted;
        }

        public Stroke transform(EntityRelation e)
        {
            if (weighted)
            {
                if (drawHeavy(e))
                    return heavy;
                else
                    return dotted;
            }
            else
                return basic;
        }

        protected boolean drawHeavy(EntityRelation e)
        {
            double value = edge_weight.get(e).doubleValue();
            if (value > 0.7)
                return true;
            else
                return false;
        }
    }

    /**
     * a GraphMousePlugin that offers pop up menu support
     */
    class CPopupGraphMousePlugin extends AbstractPopupGraphMousePlugin
    	implements MouseListener {

        public CPopupGraphMousePlugin() {
            this(MouseEvent.BUTTON3_MASK);
        }
        public CPopupGraphMousePlugin(int modifiers) {
            super(modifiers);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        	if(e.isPopupTrigger()){
        		handlePopup(e);
        	}else{
        		super.mouseReleased(e);
        	}
        }
        /**
         * If this event is over a Vertex, pop up a menu to
         * allow the user to do some action
         * @param e
         */
        @SuppressWarnings("unchecked")
        protected void handlePopup(MouseEvent e) {
            final VisualizationViewer<AbstractIshopEntity,EntityRelation> vv =
                (VisualizationViewer<AbstractIshopEntity,EntityRelation>)e.getSource();
            Point2D p = e.getPoint();
            GraphElementAccessor<AbstractIshopEntity,EntityRelation> pickSupport = vv.getPickSupport();

            if(pickSupport != null) {
                final AbstractIshopEntity v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
                final EntityRelation edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());

                /** Mouse event occurred on a node */
                if(v != null) {
                    JPopupMenu popup = new JPopupMenu();
                    Action toggleSelected = new AbstractAction("Mark/Unmark node") {
        				private static final long serialVersionUID = -9024628750954609359L;
        				public void actionPerformed(ActionEvent e) {
        					if(!markedVertices.contains(v) && !startNodes.contains(v))
        					{
        						markedVertices.add(v);
        						vv.repaint();
//        						callGUIActionListener(new GUIActionEvent(NetworkGraphPane.this,
//                						GUIActionEvent.NETWORK_ADD_SELECTED_NODE, v.getId()));

        					}
        					else{
        						markedVertices.remove(v);
        						vv.repaint();
//        						callGUIActionListener(new GUIActionEvent(NetworkGraphPane.this,
//                						GUIActionEvent.NETWORK_REMOVE_SELECTED_NODE, v.getId()));
        					}
        				}
                	};
                	JMenuItem selectNodeMenuItem = new JMenuItem(toggleSelected);
                	popup.add(selectNodeMenuItem);

                	Action kNeighborhood = new AbstractAction("Show KNE") {
        				private static final long serialVersionUID = -9024628750954609359L;
        				public void actionPerformed(ActionEvent e) {
//        					callGUIActionListener(new GUIActionEvent(
//        							CustomerNetworkGraphPane.this,
//        							GUIActionEvent.GUIType.RELATION,
//        							v
//        							));
        				}
                	};
                	JMenuItem kNeighborhoodMenuItem = new JMenuItem(kNeighborhood);
                	popup.add(kNeighborhoodMenuItem);


                	popup.addSeparator();
                	Action viewNodeImage = new AbstractAction("View '"+v.toString()+"' image") {
        				private static final long serialVersionUID = -9024628750954609359L;
        				public void actionPerformed(ActionEvent e) {

        				}
                	};
                	viewNodeImage.setEnabled(false);
                	popup.add(viewNodeImage);
                	Action viewNodeHistory = new AbstractAction("View '"+v.toString()+ "' history") {
        				private static final long serialVersionUID = -9024628750954609359L;
        				public void actionPerformed(ActionEvent e) {

        				}
                	};
                	viewNodeHistory.setEnabled(false);
                	popup.add(viewNodeHistory);

                    popup.show(vv, e.getX(), e.getY());
                } else if(edge != null) {/** Mouse event occurred on a edge */
                        JPopupMenu popup = new JPopupMenu();
                        popup.add(new AbstractAction("Relation: "+edge.toString()) {
							private static final long serialVersionUID = -9024628750954609359L;
							public void actionPerformed(ActionEvent e) {
                                System.err.println("got "+edge);
                            }
                        });
                        popup.add(new AbstractAction("Edit") {
							private static final long serialVersionUID = -9024628750954609359L;
							public void actionPerformed(ActionEvent e) {
                                System.err.println("got "+edge);
                            }
                        });
                        popup.show(vv, e.getX(), e.getY());
                }
	            else /** Not over any vertex or edge*/
	            {
	            	 JPopupMenu popup = new JPopupMenu();
	             	Action a1 = new AbstractAction("Clear marked nodes") {
	    				private static final long serialVersionUID = -9024628750954609359L;
	    				public void actionPerformed(ActionEvent e) {
	    					markedVertices.clear();
	    					vv.repaint();
	    				}
	            	};

	            	 Action a = new AbstractAction("Export as image") {
							private static final long serialVersionUID = -9024628750954609359L;
							public void actionPerformed(ActionEvent e) {
							}
	                 };
	            	 Action b = new AbstractAction("Print graph") {
							private static final long serialVersionUID = -9024628750954609359L;
							public void actionPerformed(ActionEvent e) {
							}
	            	 };
	            	 Action toggleMap = new AbstractAction("Show/Hide map") {
							private static final long serialVersionUID = -9024628750954609359L;
							public void actionPerformed(ActionEvent e) {
								overviewPanel.setVisible(!overviewPanel.isVisible());
							}
	                 };

	            	 JMenuItem menuItem0 = new JMenuItem(a1);
	            	 JMenuItem menuItem1 = new JMenuItem(a);
	            	 JMenuItem menuItem2 = new JMenuItem(b);
	            	 JMenuItem menuItem3 = new JMenuItem(toggleMap);

	            	 popup.add(menuItem0);
	            	 popup.addSeparator();
	            	 popup.add(menuItem1);
	                 popup.add(menuItem2);
	                 popup.addSeparator();
	                 popup.add(menuItem3);

	                 menuItem1.setEnabled(false);
	                 menuItem2.setEnabled(false);

	                 popup.show(vv, e.getX(), e.getY());
	            }
            }// end if(pickSuport != null)
        }
    }// end class mouse pluggin
    class CEdgeRenderer extends BasicEdgeRenderer<AbstractIshopEntity, EntityRelation>
    {
    	@Override
    	public void paintEdge(RenderContext<AbstractIshopEntity, EntityRelation> rc,
    			Layout<AbstractIshopEntity, EntityRelation> layout, EntityRelation e) {
    		super.paintEdge(rc, layout, e);
    	}
    }
    /**
     * CONNECT Vertex renderer
     * @author connect@shift-think.com
     *
     */
    protected class CVertexRenderer extends BasicVertexRenderer<AbstractIshopEntity,EntityRelation>
    {
    	final Color PERSON_TAG_COLOR = new Color(0xFF,0xFF,0xB9);
    	 protected void paintIconForVertex(RenderContext<AbstractIshopEntity,EntityRelation> rc, AbstractIshopEntity v, Layout<AbstractIshopEntity,EntityRelation> layout) {
    	        GraphicsDecorator g = rc.getGraphicsContext();
    	        boolean vertexHit = true;
    	        // get the shape to be rendered
    	        Shape shape = rc.getVertexShapeTransformer().transform(v);

    	        Point2D p = layout.transform(v);
    	        p = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p);
    	        float x = (float)p.getX();
    	        float y = (float)p.getY();
    	        // create a transform that translates to the location of
    	        // the vertex to be rendered
    	        AffineTransform xform = AffineTransform.getTranslateInstance(x,y);
    	        // transform the vertex shape with xtransform
    	        shape = xform.createTransformedShape(shape);

    	        vertexHit = vertexHit(rc, shape);
    	            //rc.getViewTransformer().transform(shape).intersects(deviceRectangle);

    	        if (vertexHit) {
    	        	if(rc.getVertexIconTransformer() != null) {
    	        		Icon icon = rc.getVertexIconTransformer().transform(v);
    	        		if(icon != null) {

    	           			g.draw(icon, rc.getScreenDevice(), shape, (int)x, (int)y);

    	        		} else {
    	        			// we customize method  paintShapeForVertex()
    	        			paintShapeForVertex(rc, v, shape,layout);
    	        		}
    	        	} else {
    	        		paintShapeForVertex(rc, v, shape,layout);
    	        	}
    	        }
    	    }

    	    protected boolean vertexHit(RenderContext<AbstractIshopEntity,EntityRelation> rc, Shape s) {
    	        JComponent vv = rc.getScreenDevice();
    	        Rectangle deviceRectangle = null;
    	        if(vv != null) {
    	            Dimension d = vv.getSize();
    	            deviceRectangle = new Rectangle(
    	                    0,0,
    	                    d.width,d.height);
    	        }
    	        MutableTransformer vt = rc.getMultiLayerTransformer().getTransformer(Layer.VIEW);
    	        if(vt instanceof MutableTransformerDecorator) {
    	        	vt = ((MutableTransformerDecorator)vt).getDelegate();
    	        }
    	        return vt.transform(s).intersects(deviceRectangle);
    	    }
    	/* (non-Javadoc)
	   	 * @see edu.uci.ics.jung.visualization.renderers.BasicVertexRenderer#paintShapeForVertex(edu.uci.ics.jung.visualization.RenderContext, java.lang.Object, java.awt.Shape)
	   	 */
	   	protected void paintShapeForVertex(RenderContext<AbstractIshopEntity,EntityRelation> rc, AbstractIshopEntity v, Shape shape,Layout<AbstractIshopEntity, EntityRelation> layout)
	   	{
   	        GraphicsDecorator g = rc.getGraphicsContext();
	        // Draw selected vertex with transparency
	        Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	        	       0.4f);
   	        Paint oldPaint = g.getPaint();
   	        Stroke oldStroke = g.getStroke();

   	        Paint fillPaint = new Color(0xBF,0xCF,0xFF);
//   	        Paint strokePaint = Color.WHITE;
//   	        Paint middleStrokePaint = Color.WHITE;
   	        Paint  shortestPathPaint = new Color(0x33, 0xCC, 0x00);
   	        Stroke shortestPathStroke = new BasicStroke(0);

   	        Stroke strokeOutside = new BasicStroke(0);

   	        //===========================================
        	// get position of the shape to be rendered
	        Point2D p = layout.transform(v);
	        p = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, p);
	        float x = (float)p.getX();
	        float y = (float)p.getY();

   	        // use for founded, on path, tag
   	        Shape foundedShape = ((CVertexShapeFunction)rc.getVertexShapeTransformer()).getFoundedShape(v,x,y);
   	        // use for selected
   	        Shape selectedShape = ((CVertexShapeFunction)rc.getVertexShapeTransformer()).getSelectedShape(v,x,y);
   	        //===========================================
//        	if(v.getEntityType().equals(EntityType.PERSON)
//        			&& !showPersonMode.equals(PersonDisplayMode.HIDE)){
//        		if(showPersonMode.equals(PersonDisplayMode.ALL)
//        				|| (showPersonMode.equals(PersonDisplayMode.FEMALE) && ((EntityPersonDto)v).getEntitySex().equals("F"))
//        				|| (showPersonMode.equals(PersonDisplayMode.MALE) && ((EntityPersonDto)v).getEntitySex().equals("M"))
//        				)
//        		{
	        		strokeOutside = new BasicStroke(3);
	        		shortestPathStroke = new BasicStroke(5);

	        		fillPaint = rc.getVertexFillPaintTransformer().transform(v);
//        		}
//        	}
//        	else if(v.getEntityType().equals(EntityType.ORGANISATION)
//        			&& isShowOrganisationNodes())
//        	{
//        		strokeOutside = new BasicStroke(4);
//        		shortestPathStroke = new BasicStroke(5);
//        		fillPaint = rc.getVertexFillPaintTransformer().transform(v);
//        	}
        	if(isVertexOnPath(v) || isFoundVertex(v)){
        		g.setComposite(c);
        		if(shortestPathStroke != null)
        		{
        			g.setStroke(shortestPathStroke);
        		}
        		g.setPaint(shortestPathPaint);
    			g.setComposite(c);
   	        	g.draw(foundedShape);

   	        	g.setStroke(oldStroke);
   	        	g.setPaint(oldPaint);
        	}
   	        if(markedVertices.contains(v)/*|| pi.isPicked(v)*/)
   	        {
   	        	g.setPaint(new Color(0xFF,0x00,0x00));

   	        	if(strokeOutside != null) {
   	        		g.setStroke(strokeOutside);
   	        	}
   	        	g.setComposite(c);
   	        	g.draw(selectedShape/*shape*/);

   	        	g.setPaint(oldPaint);
   	        	g.setStroke(oldStroke);
   	        }
   	        // Start node painting
   	        if(startNodes.contains(v))
	        {
	        	g.setPaint(rc.getVertexFillPaintTransformer().transform(v));

	        	if(strokeOutside != null) {
	        		g.setStroke(strokeOutside);
	        	}
	        	g.setComposite(c);
	        	g.draw(selectedShape);

	        	g.setPaint(oldPaint);
	        	g.setStroke(oldStroke);
	        }

   	        if(fillPaint != null) {
   	        	c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
    	        	       1.0f);
   	        	g.setComposite(c);
	            g.setPaint(fillPaint);
	            g.fill(shape);
	            if(isTaggedVertex(v)){
	            	g.setPaint(PERSON_TAG_COLOR);
	            	Shape tagShape = ((CVertexShapeFunction)rc.getVertexShapeTransformer()).getTaggedShape(v, x, y);
	            	g.fill(tagShape);
	            }
	            g.setPaint(oldPaint);
	        }

   	        //paint rank and score
//        	int s = v.getEntityType().equals(EntityType.PERSON)?calculatePersonNodeSize(v)
//        			:calculateOrganizationNodeSize(v);
//   	        if(htPersonAnalysis.size() >0 )
//   	        {
//   	        	// draw rank
//   	        	JLabel l = buildRankComponent(v);
//	   	        g.draw(l, rc.getRendererPane(), (int)(x - s/2), (int)y - (int)s/2, s, s, true);
//   	        }
//   	        if(kneLabels.size() >0 && !showKNELabelMode.equals(KNELabelMode.HIDE)
//   	        		&& ( showKNELabelMode.equals(KNELabelMode.ALL)
//   	        				|| (showKNELabelMode.equals(KNELabelMode.ORGANIZATION) && v.getEntityType().equals(EntityType.ORGANISATION))
//   	        				|| (showKNELabelMode.equals(KNELabelMode.PERSON) && v.getEntityType().equals(EntityType.PERSON)))
//   	        ){
//   	        	CircleLabel circle = new CircleLabel(String.valueOf(kneLabels.get(v.getId()).intValue()), v.getId());
//   	        	int width = String.valueOf(kneLabels.get(v.getId()).intValue()).length()<=2?14
//   	        			:String.valueOf(kneLabels.get(v.getId()).intValue()).length() * 7;
//   	        	g.drawKNELabel(circle, (CRendererPane)rc.getRendererPane(), (int)x + s/4, (int)y - s/2, width, width, true);
//        	}
	   	}
   }// end class Vertex renderer

   /**
    * This build a label with text is the rank of specified Entity
    * The font size based on the rank also
    * @param v
    * @return
    */
   private JLabel buildRankComponent(AbstractIshopEntity v){

	   JLabel l = new JLabel("Test");
	   l.setForeground(isTaggedVertex(v)?Color.LIGHT_GRAY:Color.WHITE);
	   l.setHorizontalAlignment(JLabel.CENTER);
	   int size = 11;
	   int rank = -1;
	   int step=1;
//	   if(v.getEntityType().equals(EntityType.PERSON))
//	   {
//		   EntityRank entityRank = (htPersonAnalysis.size()>0)?htPersonAnalysis.get(v.getId())
//				   :null;
//		   if(entityRank == null)return l;
//		   rank = entityRank.getRank();
//		   step = Math.round((MAX_VERTEX_FONT_SIZE - MIN_VERTEX_FONT_SIZE)/maxPersonRank);
//	   }
//	   else
//	   {
//		   EntityRank entityRank = (htOrganizationAnalysis.size()>0) ? htOrganizationAnalysis.get(v.getId())
//				   :null;
//		   if(entityRank == null)return l;
//		   rank = entityRank.getRank();
//		   step = Math.round((MAX_VERTEX_FONT_SIZE - MIN_VERTEX_FONT_SIZE)/maxOrganisationRank);
//	   }
//	   if(step == 0) step = 1;
//	   size = MAX_VERTEX_FONT_SIZE - ((int)rank * step);
//	   if(size < MIN_VERTEX_FONT_SIZE) size = MIN_VERTEX_FONT_SIZE;
//
//	   if(rank != -1)
//		   l.setText(String.valueOf(rank));
//	   l.setFont(new Font("Arial", Font.PLAIN, size));

	   return l;
   }
    /**
     * Highlight vertex based on its pick state and neighbor relationship
     * @author connect@shift-think.com
     * @param <V>
     * @param <E>
     */
    private final class VertexStrokeHighlight<V,E> implements
    Transformer<AbstractIshopEntity,Stroke>
    {
    	// Person selected highlight
        protected boolean highlight = false;
        protected Stroke heavyPerson = new BasicStroke(3);
        // Person neighbor highlight
        protected Stroke mediumPerson = new BasicStroke(0);//old value = 5

        // Organization selected highlight
        protected Stroke heavyOrganisation = new BasicStroke(4);
        // Organization neighbor highlight
        protected Stroke mediumOrganisation = new BasicStroke(0);//old value = 6


        // 0 means we did not Draw anything on the vertex
        protected Stroke light = new BasicStroke(0);
        protected PickedInfo<AbstractIshopEntity> pi;
        protected Graph<AbstractIshopEntity,EntityRelation> graph;

        public VertexStrokeHighlight(Graph<AbstractIshopEntity,EntityRelation> graph, PickedInfo<AbstractIshopEntity> pi)
        {
        	this.graph = graph;
            this.pi = pi;
        }

        public void setHighlight(boolean highlight)
        {
            this.highlight = highlight;
        }

        public Stroke transform(AbstractIshopEntity v)
        {
            if (highlight)
            {
                if (pi.isPicked(v))
//                	if(v.getEntityType().equals(EntityType.PERSON))
                		return heavyPerson;
//                	else
//                		return heavyOrganisation;
                else
                {
                	if(graph.getNeighbors(v) == null) return light;
                	for(AbstractIshopEntity w : graph.getNeighbors(v)) {
                        if (pi.isPicked(w))
//                        	if(v.getEntityType().equals(EntityType.PERSON))
//                        		if(showPersonMode.equals(PersonDisplayMode.HIDE))
                        			return light;
//                        		else
//                        			if(isHighlightKNE1())
//                        				return new BasicStroke(1);
//                        			else
//                        				return mediumPerson;
                        	else
                        		if(!isShowOrganisationNodes())
                        			return light;
                        		else
                        			if(isHighlightKNE1())
                        				return new BasicStroke(2);
                        			else
                        			return mediumOrganisation;
                    }
                    return light;
                }
            }
            else
                return light;
        }

    }
    class EdgeStringLabeller<E> implements Transformer<E,String>{
        public String transform(E e) {
            return e.toString();
        }
    }

    /**
     * Customize VertexLabelRenderer
     * @author connect.shift-think.com
     *
     */
    class CVertexLabelRenderer extends BasicVertexLabelRenderer<AbstractIshopEntity, EntityRelation>{
    	protected Position position = Position.SE;
    	private Positioner positioner = new OutsidePositioner();
		@Override
    	public void labelVertex(RenderContext<AbstractIshopEntity, EntityRelation> rc,
    			Layout<AbstractIshopEntity, EntityRelation> layout, AbstractIshopEntity v,
    			String label) {
    		//-- return if not allow to show vertex labels
    		Graph<AbstractIshopEntity, EntityRelation> graph = layout.getGraph();
    		PickedInfo<AbstractIshopEntity> pi = rc.getPickedVertexState();
    		boolean hasNeighbour = false;
    		for(AbstractIshopEntity w : graph.getNeighbors(v)) {
                if (pi.isPicked(w))hasNeighbour = true;
            }
    		if(showNodeLabelMode.equals(NodeLabelMode.HIDE) && !pi.isPicked(v) && !hasNeighbour && !isFoundVertex(v))return;

            if (rc.getVertexIncludePredicate().evaluate(Context.<Graph<AbstractIshopEntity, EntityRelation>,
            		AbstractIshopEntity>getInstance(graph,v)) == false)return;

            Point2D pt = layout.transform(v);
            pt = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, pt);

            float x = (float) pt.getX();
            float y = (float) pt.getY();

            Component component = prepareRenderer(rc, rc.getVertexLabelRenderer(), label,
            		rc.getPickedVertexState().isPicked(v), v);
            GraphicsDecorator g = rc.getGraphicsContext();
            Dimension d = component.getPreferredSize();
            AffineTransform xform = AffineTransform.getTranslateInstance(x, y);

        	Shape shape = rc.getVertexShapeTransformer().transform(v);
        	shape = xform.createTransformedShape(shape);
        	if(rc.getGraphicsContext() instanceof TransformingGraphics) {
        		BidirectionalTransformer transformer
        		= ((TransformingGraphics)rc.getGraphicsContext()).getTransformer();
        		if(transformer instanceof ShapeTransformer) {
        			ShapeTransformer shapeTransformer = (ShapeTransformer)transformer;
        			shape = shapeTransformer.transform(shape);
        		}
        	}
        	Rectangle2D bounds = shape.getBounds2D();

        	Point p = null;
        	// shiftTHINK define Position = Position.SE
        	if(position == Position.AUTO) {
        		Dimension vvd = rc.getScreenDevice().getSize();
        		if(vvd.width == 0 || vvd.height == 0) {
        			vvd = rc.getScreenDevice().getPreferredSize();
        		}
        		p = getAnchorPoint(bounds, d, positioner.getPosition(x, y, vvd));
        	} else {
        		p = getAnchorPoint(bounds, d, position);
        	}
            g.drawVertexLabel(component, (CRendererPane)rc.getRendererPane(), p.x, p.y, d.width, d.height, true);
    	}
    }
    class Rings implements VisualizationServer.Paintable {

    	Collection<Double> depths;

    	public Rings() {
    		depths = getDepths();
    	}

    	private Collection<Double> getDepths() {
    		Set<Double> depths = new HashSet<Double>();
    		Map<AbstractIshopEntity,PolarPoint> polarLocations = radialLayout.getPolarLocations();
    		for(AbstractIshopEntity v : delegateForest.getVertices()) {
    			PolarPoint pp = polarLocations.get(v);
    			depths.add(pp.getRadius());
    		}
    		return depths;
    	}

		public void paint(Graphics g) {
			g.setColor((paneType.equals(PaneType.KNE))?new Color(0xCC,0xCC,0xCC):Color.lightGray);

			Graphics2D g2d = (Graphics2D)g;
			if(paneType.equals(PaneType.KNE)){
				g2d.setStroke(new BasicStroke(8));
			}
			Point2D center = radialLayout.getCenter();


			Ellipse2D ellipse = new Ellipse2D.Double();
			for(double d : depths) {
				ellipse.setFrameFromDiagonal(center.getX()-d, center.getY()-d,
						center.getX()+d, center.getY()+d);
				Shape shape = vv1.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).transform(ellipse);
				g2d.draw(shape);
			}
		}

		public boolean useTransform() {
			return true;
		}
    }
    /**
     * @deprecated
     */
    @SuppressWarnings("unused")
	private void setLefttoRight() {
    	Layout<AbstractIshopEntity,EntityRelation> layout = vv1.getModel().getGraphLayout();
    	Dimension d = layout.getSize();
    	Point2D center = new Point2D.Double(d.width/2, d.height/2);
    	vv1.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).rotate(Math.PI, center);
    }
    /**
     * Change layout using LayoutTransition
     * @param layoutClass
     * @param vv
     */
    @SuppressWarnings("unchecked")
	private void switchGraphLayout(/*Class<? extends*/ Layout<AbstractIshopEntity, EntityRelation>/*>*/ l/*Class*/){
    	try {
			vv1.removePreRenderPaintable(rings);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
//			Constructor<? extends Layout<AbstractIshopEntity, EntityRelation>> constructor = layoutClass
//					.getConstructor(new Class[] { Graph.class });
//			Object o = constructor.newInstance(vv1.getGraphLayout().getGraph());
			//Layout<AbstractIshopEntity, EntityRelation> l = (Layout<AbstractIshopEntity, EntityRelation>) o;
			l.setInitializer(vv1.getGraphLayout());
			l.setSize(vv1.getSize());
			LayoutTransition<AbstractIshopEntity, EntityRelation> lt = new LayoutTransition<AbstractIshopEntity, EntityRelation>(
					vv1, vv1.getGraphLayout(), l);
			Animator animator = new Animator(lt);
			animator.start();
			vv1.getRenderContext().getMultiLayerTransformer().setToIdentity();
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentZoomLevel = 100;
		updateZoomLevelValue();
		this.layoutClass = l;//Class;
    }
    final CButton2 personButton = new CButton2(Util.getIcon("network/icon_persons.png"));
	final CToggleButton organisationButton = new CToggleButton(Util.getIcon("network/icon_organizations.png"));
	final CToggleButton showTagButton = new CToggleButton(Util.getIcon("network/icon_tags_2.png"));
	final CButton2 labelNodeButton = new CButton2(Util.getIcon("network/icon_node_labels.png"));
	final CButton2 kneLabelButton = new CButton2(Util.getIcon("network/icon_kne_labels_off.png"));
	final CButton2 labelEdgeButton = new CButton2(Util.getIcon("network/icon_edge_labels.png"));
	final CToggleButton negativeEdgeButton = new CToggleButton(Util.getIcon("network/icon_negative_edges.png"));
	final CToggleButton multipleEdgeButton = new CToggleButton(Util.getIcon("network/icon_multiple_edges.png"));

    void refresh()
    {
    	clear();
    	showEdgeLabelMode = EdgeLabelMode.HIDE;showNodeLabelMode = NodeLabelMode.HIDE;
    	showPersonMode = PersonDisplayMode.ALL; showOrganisationNodes = true;
    	showKNELabelMode = KNELabelMode.ALL;
    	personButton.setSelected(false);organisationButton.setSelected(false);
    	labelNodeButton.setSelected(false);labelEdgeButton.setSelected(false);
    	negativeEdgeButton.setSelected(false);multipleEdgeButton.setSelected(false);
    	kneLabelButton.setSelected(false);showTagButton.setSelected(false);

    	// reset analysis result
    	htPersonAnalysis.clear();
    	htOrganizationAnalysis.clear();
    	maxPersonRank = -1;
    	maxOrganisationRank = -1;
    }
    public void clear()
    {
		markedVertices.clear();
    	selectedEdges.clear();
    	vv1.getPickedVertexState().clear();
    	vv1.getPickedEdgeState().clear();
    	shortestEdgesList.clear();
    	shortestVeticesList.clear();
    	searchFoundVector.clear();
    	kneLabels.clear();
    	selectedTagNameVector.clear();
    	startNodes.clear();

    }

    //-- Do not renew following hash tables
    /** <String represent for EntityId, EntityRank> */
    private Hashtable<String , EntityRank> htPersonAnalysis = new Hashtable<String , EntityRank>();
    private Hashtable<String , EntityRank> htOrganizationAnalysis = new Hashtable<String , EntityRank>();

    /**
     * Use to contain kne labels of all visible vertices
     */
    private Hashtable<String, Integer> kneLabels = new Hashtable<String, Integer>();

    /**
	 * Build the toolbar for this Graph panel
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected JToolBar buildToolBar(){
		final JToolBar networkToolBar = new JToolBar(JToolBar.HORIZONTAL);
		final JPopupMenu popup = new JPopupMenu();
		networkToolBar.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
		networkToolBar.setBackground(new Color(242,242,242));
		networkToolBar.setFloatable(false);

		final ScalingControl scaler = new CrossoverScalingControl();

        //===== REFRESH ====================//
		CButton2 refresh = new CButton2("");
		refresh.setIcon(Util.getIcon("network/icon_reload_data.png"));
		refresh.setSelectedIcon(Util.getIcon("network/icon_reload_data.png"));
		refresh.setToolTipText(Language.getInstance().getString("Refresh graph"));

		refresh.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 6551596201518687121L;
			public void actionPerformed(ActionEvent e) {
//				callGUIActionListener(new GUIActionEvent(
//						NetworkGraphPane.this,
//						GUIActionEvent.NETWORK_REFRESH,
//						null
//						));
            }
        });
        networkToolBar.add(refresh);
      //===== CLEAR ====================//
		CButton2 clear = new CButton2("");
		clear.setIcon(Util.getIcon("network/icon_clear.png"));
		clear.setSelectedIcon(Util.getIcon("network/icon_clear.png"));
		clear.setToolTipText(Language.getInstance().getString("Clear"));
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	callGUIActionListener(new GUIActionEvent(
//						NetworkGraphPane.this,
//						GUIActionEvent.NETWORK_CLEAR,
//						null
//						));
            }
        });
        networkToolBar.add(clear);

      //===== CLEAR ====================//
		CButton2 centerNode = new CButton2("");
		centerNode.setIcon(Util.getIcon("network/icon_start_node.png"));
		centerNode.setSelectedIcon(Util.getIcon("network/icon_start_node.png"));
		centerNode.setToolTipText(Language.getInstance().getString("Use the picked node as start node (can be multiple)"));
		centerNode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//verticesAnimatedCentering(vv1, markedVertices);
            	PickedState pickState = vv1.getPickedVertexState();
            	for(AbstractIshopEntity v:mainGraph.getVertices())
            	{
            		if(pickState.isPicked(v))
            		{
            			if(!startNodes.contains(v))
            			{
                			if(markedVertices.contains(v))
                			{
                				markedVertices.remove(v);
//                				callGUIActionListener(
//                        				new GUIActionEvent(NetworkGraphPane.this,
//                        						GUIActionEvent.NETWORK_REMOVE_SELECTED_NODE, v.getId()));
                			}
            				startNodes.add(v);
//                			callGUIActionListener(
//                    				new GUIActionEvent(NetworkGraphPane.this,
//                    						GUIActionEvent.NETWORK_ADD_START_NODE, v.getId()));
            			}
            			else
            			{
            				startNodes.remove(v);
//            				callGUIActionListener(
//                    				new GUIActionEvent(NetworkGraphPane.this,
//                    						GUIActionEvent.NETWORK_REMOVE_START_NODE, v.getId()));
            			}
            			vv1.repaint();
            			break;
            		}
            	}
            }
        });
        networkToolBar.add(centerNode);

        //===== Person ====================//
		personButton.setToolTipText("Show/Hide person on graph");
		personButton.setBackground(networkToolBar.getBackground());
		personButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(popup.isVisible()){
					return;
				}
				popup.removeAll();
				popup.setBorder(new EmptyBorder(1,1,1,1));
				popup.setBorderPainted(false);
				Point p = personButton.getLocation();
            	CButton2 buttonPerson = new CButton2(Util.getIcon("network/icon_persons.png"));
            	buttonPerson.setToolTipText(Language.getInstance().getString("Show all person"));
            	buttonPerson.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -4514136465203508342L;

					public void actionPerformed(ActionEvent e) {
            			if(!showPersonMode.equals(PersonDisplayMode.ALL))
            			{
                			showPersonMode = PersonDisplayMode.ALL;
                			personButton.setIcon(Util.getIcon("network/icon_persons.png"));
                			vv1.repaint();
            			}
    					popup.setVisible(false);

            		}
            	});
            	buttonPerson.setToolTipText("Show all");
            	popup.add(buttonPerson);
            	CButton2 buttonPersonFemale = new CButton2(Util.getIcon("network/icon_persons_female.png"));
            	buttonPersonFemale.setToolTipText(Language.getInstance().getString("Show Female only"));
            	buttonPersonFemale.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = 5535580108444652500L;

					public void actionPerformed(ActionEvent e) {
            			if(!showPersonMode.equals(PersonDisplayMode.FEMALE))
            			{
                			showPersonMode = PersonDisplayMode.FEMALE;
                			personButton.setIcon(Util.getIcon("network/icon_persons_female.png"));
                			vv1.repaint();
            			}
    					popup.setVisible(false);

            		}
            	});
            	popup.add(buttonPersonFemale);

            	CButton2 buttonPersonMale = new CButton2(Util.getIcon("network/icon_persons_male.png"));
            	buttonPersonMale.setToolTipText(Language.getInstance().getString("Show Male only"));
            	buttonPersonMale.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -1394916943070308617L;

					public void actionPerformed(ActionEvent e) {
            			if(!showPersonMode.equals(PersonDisplayMode.MALE))
            			{
                			showPersonMode = PersonDisplayMode.MALE;
                			personButton.setIcon(Util.getIcon("network/icon_persons_male.png"));
                			vv1.repaint();
            			}
    					popup.setVisible(false);

            		}
            	});
            	popup.add(buttonPersonMale);

            	CButton2 buttonPersonOff = new CButton2(Util.getIcon("network/icon_persons_off.png"));
            	buttonPersonOff.setToolTipText(Language.getInstance().getString("Hide person on graph"));
            	buttonPersonOff.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -4456577311477722428L;

					public void actionPerformed(ActionEvent e) {
            			if(!showPersonMode.equals(PersonDisplayMode.HIDE))
            			{
                			showPersonMode = PersonDisplayMode.HIDE;
                			personButton.setIcon(Util.getIcon("network/icon_persons_off.png"));
                			vv1.repaint();
            			}
    					popup.setVisible(false);

            		}
            	});
            	popup.add(buttonPersonOff);

            	// important
            	popup.show(networkToolBar, p.x -1, p.y + personButton.getPreferredSize().height);
            }
        });
        networkToolBar.add(personButton);

        //===== Organization ====================//

		organisationButton.setToolTipText(Language.getInstance().getString("Show/Hide Organisation on graph"));
		organisationButton.setSelectedIcon(Util.getIcon("network/icon_organizations_off.png"));
		organisationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showOrganisationNodes = !showOrganisationNodes;
            	vv1.repaint();
            }
        });
        networkToolBar.add(organisationButton);

        //===== Org ====================//

        showTagButton.setSelectedIcon(Util.getIcon("network/icon_tags_1.png"));
		showTagButton.setToolTipText(Language.getInstance().getString("Show/Hide tag on graph"));
		showTagButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        networkToolBar.add(showTagButton);

        networkToolBar.addSeparator();
        //==============================//

        labelNodeButton.setToolTipText(Language.getInstance().getString("Show/Hide node label"));
		labelNodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(popup.isVisible()){
					return;
				}
            	popup.removeAll();
				popup.setBorder(new EmptyBorder(1,1,1,1));
				popup.setBorderPainted(false);
				Point p = labelNodeButton.getLocation();
            	CButton2 buttonNodeLabelNormal = new CButton2(Util.getIcon("network/icon_node_labels.png"));
            	buttonNodeLabelNormal.setToolTipText(Language.getInstance().getString("Label all"));
            	buttonNodeLabelNormal.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = 103625910885323293L;

					public void actionPerformed(ActionEvent e) {
            			if(!showNodeLabelMode.equals(NodeLabelMode.ALL))
            			{
                			showNodeLabelMode = NodeLabelMode.ALL;
                        	vv1.repaint();
                			labelNodeButton.setIcon(Util.getIcon("network/icon_node_labels.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonNodeLabelNormal);
            	CButton2 buttonNodeLabelOrg = new CButton2(Util.getIcon("network/icon_node_labels_organizations.png"));
            	buttonNodeLabelOrg.setToolTipText(Language.getInstance().getString("Label Organization only"));
            	buttonNodeLabelOrg.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -5012576360800450312L;

					public void actionPerformed(ActionEvent e) {
            			if(!showNodeLabelMode.equals(NodeLabelMode.ORGANIZATION))
            			{
                			showNodeLabelMode = NodeLabelMode.ORGANIZATION;
                        	vv1.repaint();
                			labelNodeButton.setIcon(Util.getIcon("network/icon_node_labels_organizations.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonNodeLabelOrg);

            	CButton2 buttonNodeLabelPerson = new CButton2(Util.getIcon("network/icon_node_labels_persons.png"));
            	buttonNodeLabelPerson.setToolTipText(Language.getInstance().getString("Label person only"));
            	buttonNodeLabelPerson.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = 102707089418056083L;

					public void actionPerformed(ActionEvent e) {
            			if(!showNodeLabelMode.equals(NodeLabelMode.PERSON))
            			{
                			showNodeLabelMode = NodeLabelMode.PERSON;
                        	vv1.repaint();
                			labelNodeButton.setIcon(Util.getIcon("network/icon_node_labels_persons.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonNodeLabelPerson);
            	CButton2 buttonNodeLabelOff = new CButton2(Util.getIcon("network/icon_node_labels_off.png"));
            	buttonNodeLabelOff.setToolTipText(Language.getInstance().getString("Turn off node label"));
            	buttonNodeLabelOff.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -5071461307800806279L;

					public void actionPerformed(ActionEvent e) {
            			if(!showNodeLabelMode.equals(NodeLabelMode.HIDE)){
                			showNodeLabelMode = NodeLabelMode.HIDE;
                        	vv1.repaint();
                			labelNodeButton.setIcon(Util.getIcon("network/icon_node_labels_off.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonNodeLabelOff);

            	// important
            	popup.show(networkToolBar, p.x -1, p.y + labelNodeButton.getPreferredSize().height);
            }
        });
        networkToolBar.add(labelNodeButton);

        //====== KNE labels ======================//

		kneLabelButton.setToolTipText("Show hide KNE");
		kneLabelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(popup.isVisible()){
					return;
				}
            	popup.removeAll();
				popup.setBorder(new EmptyBorder(1,1,1,1));
				popup.setBorderPainted(false);
				Point p = kneLabelButton.getLocation();
            	CButton2 buttonKNELabelNormal = new CButton2(Util.getIcon("network/icon_kne_labels.png"));
            	buttonKNELabelNormal.setToolTipText(Language.getInstance().getString("Show all"));
            	buttonKNELabelNormal.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -5935075407419820446L;

					public void actionPerformed(ActionEvent e) {
//            			if(!showKNELabelMode.equals(KNELabelMode.ALL)){
//                			showKNELabelMode = KNELabelMode.ALL;
//                			if(!showKNELabelMode.equals(KNELabelMode.HIDE)){
//            	            	for (AbstractIshopEntity v:vv1.getGraphLayout().getGraph().getVertices())
//            	            	{
//            	            		Collection<AbstractIshopEntity> vCollection =  vv1.getGraphLayout().getGraph().getNeighbors(v);
//            	            		Integer idx = (vCollection == null)?new Integer(0):new Integer(vCollection.size());
//            	            		kneLabels.put(v.getId(), idx);
//            	            	}
//                        	}
//                			vv1.repaint();
//                        	kneLabelButton.setIcon(Util.getIcon("network/icon_kne_labels.png"));
//            			}
//    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonKNELabelNormal);
            	CButton2 buttonKNELabelOrg = new CButton2(Util.getIcon("network/icon_kne_labels_organizations.png"));
            	buttonKNELabelOrg.setToolTipText(Language.getInstance().getString("Show for organization only"));
            	buttonKNELabelOrg.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = 4941156009207424600L;

					public void actionPerformed(ActionEvent e) {
//            			if(!showKNELabelMode.equals(KNELabelMode.ORGANIZATION)){
//                			showKNELabelMode = KNELabelMode.ORGANIZATION;
//                			if(!showKNELabelMode.equals(KNELabelMode.HIDE)){
//            	            	for (AbstractIshopEntity v:vv1.getGraphLayout().getGraph().getVertices())
//            	            	{
//            	            		Collection<AbstractIshopEntity> vCollection =  vv1.getGraphLayout().getGraph().getNeighbors(v);
//            	            		Integer idx = (vCollection == null)?new Integer(0):new Integer(vCollection.size());
//            	            		kneLabels.put(v.getId(), idx);
//            	            	}
//                        	}
//                			vv1.repaint();
//                        	kneLabelButton.setIcon(Util.getIcon("network/icon_kne_labels_organizations.png"));
//            			}
//    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonKNELabelOrg);

            	CButton2 buttonKNELabelPerson = new CButton2(Util.getIcon("network/icon_kne_labels_persons.png"));
            	buttonKNELabelPerson.setToolTipText(Language.getInstance().getString("Show for person only"));
            	buttonKNELabelPerson.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -3711876200430439662L;

					public void actionPerformed(ActionEvent e) {
//            			if(!showKNELabelMode.equals(KNELabelMode.PERSON)){
//                			showKNELabelMode = KNELabelMode.PERSON;
//                			if(!showKNELabelMode.equals(KNELabelMode.HIDE)){
//            	            	for (AbstractIshopEntity v:vv1.getGraphLayout().getGraph().getVertices())
//            	            	{
//            	            		Collection<AbstractIshopEntity> vCollection =  vv1.getGraphLayout().getGraph().getNeighbors(v);
//            	            		Integer idx = (vCollection == null)?new Integer(0):new Integer(vCollection.size());
//            	            		kneLabels.put(v.getId(), idx);
//            	            	}
//                        	}
//                        	vv1.repaint();
//                        	kneLabelButton.setIcon(Util.getIcon("network/icon_kne_labels_persons.png"));
//            			}
//    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonKNELabelPerson);
            	CButton2 buttonKNELabelOff = new CButton2(Util.getIcon("network/icon_kne_labels_off.png"));
            	buttonKNELabelOff.setToolTipText(Language.getInstance().getString("Turn off KNE label"));
            	buttonKNELabelOff.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = 231818608957004698L;

					public void actionPerformed(ActionEvent e) {
						if(!showKNELabelMode.equals(KNELabelMode.HIDE)){
	            			showKNELabelMode = KNELabelMode.HIDE;
	                    	vv1.repaint();
	                    	kneLabelButton.setIcon(Util.getIcon("network/icon_kne_labels_off.png"));
						}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonKNELabelOff);
            	// important
            	popup.show(networkToolBar, p.x -1, p.y + kneLabelButton.getPreferredSize().height);
            }
        });
        networkToolBar.add(kneLabelButton);
        //===== == ====================//

		labelEdgeButton.setToolTipText(Language.getInstance().getString("Show/Hide Edge label"));
		labelEdgeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(popup.isVisible()){
					return;
				}
				popup.removeAll();
				popup.setBorder(new EmptyBorder(1,1,1,1));
				popup.setBorderPainted(false);
				Point p = labelEdgeButton.getLocation();

				CButton2 buttonEdgeLabelNormal = new CButton2(Util.getIcon("network/icon_edge_labels.png"));
				buttonEdgeLabelNormal.setToolTipText(Language.getInstance().getString("Show label on all relations"));
				buttonEdgeLabelNormal.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = 5185839462636859076L;

					public void actionPerformed(ActionEvent e) {
            			if(!showEdgeLabelMode.equals(EdgeLabelMode.ALL)){
                			showEdgeLabelMode = EdgeLabelMode.ALL;
    		            	vv1.repaint();
    		            	labelEdgeButton.setIcon(Util.getIcon("network/icon_edge_labels.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonEdgeLabelNormal);
            	CButton2 buttonEdgeLabelBusiness = new CButton2(Util.getIcon("network/icon_edge_labels_business.png"));
            	buttonEdgeLabelBusiness.setToolTipText(Language.getInstance().getString("Show business relation only"));
            	buttonEdgeLabelBusiness.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -7144836950526866987L;

					public void actionPerformed(ActionEvent e) {
            			if(!showEdgeLabelMode.equals(EdgeLabelMode.BUSINESS)){
                			showEdgeLabelMode = EdgeLabelMode.BUSINESS;
    		            	vv1.repaint();
    		            	labelEdgeButton.setIcon(Util.getIcon("network/icon_edge_labels_business.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonEdgeLabelBusiness);

            	CButton2 buttonEdgeLabelFamily = new CButton2(Util.getIcon("network/icon_edge_labels_family.png"));
            	buttonEdgeLabelFamily.setToolTipText(Language.getInstance().getString("Show family relation only"));
            	buttonEdgeLabelFamily.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = 4041142498334634804L;

					public void actionPerformed(ActionEvent e) {
            			if(!showEdgeLabelMode.equals(EdgeLabelMode.FAMILY)){
                			showEdgeLabelMode = EdgeLabelMode.FAMILY;
    		            	vv1.repaint();
    		            	labelEdgeButton.setIcon(Util.getIcon("network/icon_edge_labels_family.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonEdgeLabelFamily);
            	CButton2 buttonEdgeLabelPrivate = new CButton2(Util.getIcon("network/icon_edge_labels_private.png"));
            	buttonEdgeLabelPrivate.setToolTipText(Language.getInstance().getString("Show private relation only"));
            	buttonEdgeLabelPrivate.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -648830168139172450L;

					public void actionPerformed(ActionEvent e) {
            			// do something for k
            			if(!showEdgeLabelMode.equals(EdgeLabelMode.PRIVATE)){
                			showEdgeLabelMode = EdgeLabelMode.PRIVATE;
    		            	vv1.repaint();
    		            	labelEdgeButton.setIcon(Util.getIcon("network/icon_edge_labels_private.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonEdgeLabelPrivate);

            	CButton2 buttonEdgeLabelOff = new CButton2(Util.getIcon("network/icon_edge_labels_off.png"));
            	buttonEdgeLabelOff.setToolTipText(Language.getInstance().getString("Turn off edge label"));
            	buttonEdgeLabelOff.addActionListener(new AbstractAction(){
					private static final long serialVersionUID = -5313478827490666197L;

					public void actionPerformed(ActionEvent e) {
            			if(!showEdgeLabelMode.equals(EdgeLabelMode.HIDE)){
                			showEdgeLabelMode = EdgeLabelMode.HIDE;
    		            	vv1.repaint();
    		            	labelEdgeButton.setIcon(Util.getIcon("network/icon_edge_labels_off.png"));
            			}
    					popup.setVisible(false);
            		}
            	});
            	popup.add(buttonEdgeLabelOff);

            	// important
            	popup.show(networkToolBar, p.x -1, p.y + labelEdgeButton.getPreferredSize().height);
            }
        });
        networkToolBar.add(labelEdgeButton);

        //===== == ====================//
        negativeEdgeButton.setToolTipText(Language.getInstance().getString("Show/hide negative Edge"));
		negativeEdgeButton.setSelectedIcon(Util.getIcon("network/icon_negative_edges_off.png"));
		negativeEdgeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        networkToolBar.add(negativeEdgeButton);


      //===== == ====================//
        multipleEdgeButton.setToolTipText(Language.getInstance().getString("Show/hide multiple edge"));
		multipleEdgeButton.setSelectedIcon(Util.getIcon("network/icon_multiple_edges_off.png"));
		multipleEdgeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        networkToolBar.add(multipleEdgeButton);

        networkToolBar.addSeparator();
        //===== == ====================//
		CButton2 layoutFRButton = new CButton2("");
		layoutFRButton.setToolTipText(Language.getInstance().getString("Switch to Fruchterman-Reingold (FR) layout"));
		layoutFRButton.setIcon(Util.getIcon("network/icon_layout_fr.png"));
		layoutFRButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	switchGraphLayout((Class<? extends Layout<AbstractIshopEntity, EntityRelation>>)FRLayout.class);
            	switchGraphLayout(new FRLayout<AbstractIshopEntity,EntityRelation>(getGraph()));
            }
        });
        networkToolBar.add(layoutFRButton);


      //===========================//
		CButton2 layoutKKButton = new CButton2("");
		layoutKKButton.setToolTipText(Language.getInstance().getString("Switch to KK layout"));
		layoutKKButton.setIcon(Util.getIcon("network/icon_layout_kk.png"));
		layoutKKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	switchGraphLayout((Class<? extends Layout<AbstractIshopEntity, EntityRelation>>)KKLayout.class);
            	switchGraphLayout(new KKLayout<AbstractIshopEntity,EntityRelation>(getGraph()));
            }
        });
        networkToolBar.add(layoutKKButton);

        //===========================//
		CButton2 layoutSMButton = new CButton2("");
		layoutSMButton.setToolTipText(Language.getInstance().getString("Switch to SM layout"));
		layoutSMButton.setIcon(Util.getIcon("network/icon_layout_sm.png"));
		layoutSMButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	switchGraphLayout((Class<? extends Layout<AbstractIshopEntity, EntityRelation>>)ISOMLayout.class);
            	switchGraphLayout(new ISOMLayout<AbstractIshopEntity,EntityRelation>(getGraph()));
            }
        });
        networkToolBar.add(layoutSMButton);


        //===========================//
		CButton2 layoutSPButton = new CButton2("");
		layoutSPButton.setToolTipText(Language.getInstance().getString("Switch to spring layout"));
		layoutSPButton.setIcon(Util.getIcon("network/icon_layout_sp.png"));
		layoutSPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	switchGraphLayout((Class<? extends Layout<AbstractIshopEntity, EntityRelation>>)SpringLayout2.class);
            	switchGraphLayout(new SpringLayout2<AbstractIshopEntity,EntityRelation>(getGraph()));
            }
        });
        networkToolBar.add(layoutSPButton);

        //===========================//
		CButton2 layoutCRButton = new CButton2("");
		layoutCRButton.setToolTipText(Language.getInstance().getString("Switch to CR layout"));
		layoutCRButton.setIcon(Util.getIcon("network/icon_layout_cr.png"));
		layoutCRButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	switchGraphLayout((Class<? extends Layout<AbstractIshopEntity, EntityRelation>>)CircleLayout.class);
            	switchGraphLayout(new CircleLayout<AbstractIshopEntity,EntityRelation>(getGraph()));
            }
        });
        networkToolBar.add(layoutCRButton);

        //===========================//
		CButton2 layoutKNButton = new CButton2("");
		layoutKNButton.setToolTipText(Language.getInstance().getString("Switch to KN layout"));
		layoutKNButton.setIcon(Util.getIcon("network/icon_layout_kn.png"));
		layoutKNButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int size = Math.min(vv1.getSize().width,
            			vv1.getSize().height);
            	Dimension d = new Dimension(size, size);
            	radialLayout.setSize(d);
            	vv1.removePreRenderPaintable(rings);
            	rings = new Rings();
				vv1.addPreRenderPaintable(rings);
				LayoutTransition<AbstractIshopEntity, EntityRelation> lt =
					new LayoutTransition<AbstractIshopEntity, EntityRelation>(vv1, vv1.getGraphLayout(), radialLayout);
				Animator animator = new Animator(lt);
				animator.start();
				vv1.getRenderContext().getMultiLayerTransformer().setToIdentity();
		    	vv1.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).translate(
		    			Math.round((vv1.getSize().width - d.width)/2), 0);
				currentZoomLevel = 100;
				updateZoomLevelValue();
            }
        });

        networkToolBar.add(layoutKNButton);

        networkToolBar.addSeparator();

        //===========================//
		CButton2 anaHBButton = new CButton2("");
		anaHBButton.setToolTipText(Language.getInstance().getString("Hubness analysis"));
		anaHBButton.setIcon(Util.getIcon("network/icon_analysis_hb.png"));
		anaHBButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	htPersonAnalysis.clear();
//            	htOrganizationAnalysis.clear();
//            	htPersonAnalysis = GraphAnalysis.getBrokers(
//            			vv1.getGraphLayout().getGraph(), EntityType.PERSON);
//            	htOrganizationAnalysis = GraphAnalysis.getBrokers(
//            			vv1.getGraphLayout().getGraph(), EntityType.ORGANISATION);
//            	maxOrganisationRank = getMaxEntityRank(htOrganizationAnalysis);
//            	maxPersonRank = getMaxEntityRank(htPersonAnalysis);
//            	vv1.repaint();
            }
        });
        networkToolBar.add(anaHBButton);


        //===========================//
		CButton2 anaBYButton = new CButton2("");
		anaBYButton.setToolTipText(Language.getInstance().getString("Bary center analysis"));
		anaBYButton.setIcon(Util.getIcon("network/icon_analysis_by.png"));
		anaBYButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	htPersonAnalysis.clear();
//            	htOrganizationAnalysis.clear();
//            	htPersonAnalysis = GraphAnalysis.getBarycenters(
//            			vv1.getGraphLayout().getGraph(), EntityType.PERSON);
//            	htOrganizationAnalysis = GraphAnalysis.getBarycenters(
//            			vv1.getGraphLayout().getGraph(), EntityType.ORGANISATION);
//            	maxOrganisationRank = getMaxEntityRank(htOrganizationAnalysis);
//            	maxPersonRank = getMaxEntityRank(htPersonAnalysis);
//            	vv1.repaint();
            }
        });
        networkToolBar.add(anaBYButton);

        //===========================//
		CButton2 anaBCButton2 = new CButton2("");
		anaBCButton2.setToolTipText(Language.getInstance().getString("Betweenness Centrality Analysis"));
		anaBCButton2.setIcon(Util.getIcon("network/icon_analysis_bc.png"));
		anaBCButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	htPersonAnalysis.clear();
//            	htOrganizationAnalysis.clear();
//
//            	htOrganizationAnalysis = GraphAnalysis.getBetweennessCentrality(
//            			vv1.getGraphLayout().getGraph(), EntityType.ORGANISATION);
//            	htPersonAnalysis = GraphAnalysis.getBetweennessCentrality(
//            			vv1.getGraphLayout().getGraph(), EntityType.PERSON);
//
//            	maxOrganisationRank = getMaxEntityRank(htOrganizationAnalysis);
//            	maxPersonRank = getMaxEntityRank(htPersonAnalysis);
//
//            	vv1.repaint();
            }
        });
        networkToolBar.add(anaBCButton2);


        //===========================//
		CButton2 anaUSPButton = new CButton2("");
		anaUSPButton.setToolTipText(Language.getInstance().getString("Unweighted Shortest Path Analysis"));
		anaUSPButton.setIcon(Util.getIcon("network/icon_analysis_usp.png"));
		anaUSPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	if(markedVertices.size()!=2){
//					CommonDialog.showMessageDialog(null, Language
//							.getInstance().getString(
//									"Selected vertices must be 02"),
//							CommonDialog.SHOW_OK_OPTION);
//					return;
//            	}
//            	paintUnweightedShortestPath(markedVertices.get(0), markedVertices.get(1));
//            	vv1.repaint();
            }
        });
        networkToolBar.add(anaUSPButton);


      //===========================//
		CButton2 anaDSPButton = new CButton2("");
		anaDSPButton.setToolTipText(Language.getInstance().getString("Dijsktra Shortest Path Analysis"));
		anaDSPButton.setIcon(Util.getIcon("network/icon_analysis_dsp.png"));
		anaDSPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	if(markedVertices.size()!=2){
//					CommonDialog.showMessageDialog(null, Language
//							.getInstance().getString(
//									"Selected vertices must be 02"),
//							CommonDialog.SHOW_OK_OPTION);
//					return;
//            	}
//            	paintDijstraShortestPath(markedVertices.get(0), markedVertices.get(1));
//            	vv1.repaint();
            }
        });
        networkToolBar.add(anaDSPButton);
        networkToolBar.addSeparator();

      //===========================//
		CButton2 fitWindowButton = new CButton2("");
		fitWindowButton.setToolTipText(Language.getInstance().getString("Fit graph to window"));
		fitWindowButton.setIcon(Util.getIcon("network/icon_fit_window.png"));
		fitWindowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// TODO
            	System.out.println("graphZoomScrollPane width "+graphZoomScrollPane.getSize().width);
            	System.out.println("graphZoomScrollPane height "+graphZoomScrollPane.getSize().height);
            	System.out.println("Pane width "+getSize().width);
            	System.out.println("Pane height "+getSize().height);

            	System.out.println("VV width "+vv1.getSize().width);
            	System.out.println("VV height "+vv1.getSize().height);

            	System.out.println("Layout width "+vv1.getGraphLayout().getSize().width);
            	System.out.println("Layout height "+vv1.getGraphLayout().getSize().height);
            	//vv1.setSize();
            }
        });
        networkToolBar.add(fitWindowButton);


        //===========================//
		CButton2 fullNetworkButton = new CButton2("");
		fullNetworkButton.setToolTipText(Language.getInstance().getString("Full screen mode"));
		fullNetworkButton.setIcon(Util.getIcon("network/icon_full_network.png"));
		fullNetworkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        networkToolBar.add(fullNetworkButton);

        networkToolBar.addSeparator(new Dimension(20, 20));
        //=================================//
        JLabel kneLabel = new JLabel("KNE: ");

        networkToolBar.add(kneLabel);
        networkToolBar.add(kneValueLabel);
		//===== PLUS =======================//
        CButton2 minus = new CButton2("");
        minus.setIcon(Util.getIcon("network/icon_minus.png"));
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(currentZoomLevel <= zoomSlider.getMinimum())
            		return;
            	scaler.scale(vv1, 1/1.1f, vv1.getCenter());
            	currentZoomLevel -= 10;
                updateZoomLevelValue();
            }
        });
        networkToolBar.add(minus);
        networkToolBar.addSeparator();
		CButton2 plus = new CButton2("");
        plus.setIcon(Util.getIcon("network/icon_plus.png"));
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(currentZoomLevel >= zoomSlider.getMaximum())
            		return;

            	scaler.scale(vv1, 1.1f, vv1.getCenter());
                currentZoomLevel += 10;
                updateZoomLevelValue();
            }
        });
        networkToolBar.add(plus);

        networkToolBar.addSeparator(new Dimension(20, 20));
        JLabel zoomLabel = new JLabel("ZOOM: ");
        networkToolBar.add(zoomLabel);
        networkToolBar.add(zoomValueLabel);

        CButton2 sliderMinus = new CButton2("");
        sliderMinus.setIcon(Util.getIcon("network/icon_minus.png"));
        sliderMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(currentZoomLevel <= zoomSlider.getMinimum())
            		return;

            	scaler.scale(vv1, 1/1.1f, vv1.getCenter());
                currentZoomLevel -= 10;
                updateZoomLevelValue();
            }
        });
        networkToolBar.add(sliderMinus);
        zoomSlider.setBackground(networkToolBar.getBackground());
        networkToolBar.add(zoomSlider);
        zoomSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				zoomSlider.setValue(currentZoomLevel);				
				try {
					scaler.scale(vv1, (float) zoomSlider.getValue()
									/ currentZoomLevel, vv1.getCenter());
					currentZoomLevel = zoomSlider.getValue();
					zoomValueLabel.setText(String.valueOf(currentZoomLevel)
							+ "%");
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
			}

        });

        CButton2 sliderPlus = new CButton2("");
        sliderPlus.setIcon(Util.getIcon("network/icon_plus.png"));
        sliderPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(currentZoomLevel >= zoomSlider.getMaximum())
            		return;
            	scaler.scale(vv1, 1.1f, vv1.getCenter());
                currentZoomLevel += 10;
                updateZoomLevelValue();
            }
        });
        networkToolBar.add(sliderPlus);

        //
        zoomValueLabel.setPreferredSize(new Dimension(40,20));

		return networkToolBar;
	}
	 /**
     * move Vertices to center
     *
     * @param vertexSet Set of type Vertex
     */
    public void verticesAnimatedCentering(final VisualizationViewer<AbstractIshopEntity, EntityRelation> viewer,
    		final Vector<AbstractIshopEntity> selectedVertices) {
    	if(selectedVertices.size() <= 0 )return;
        Thread animationThread = null;

        if (animationThread != null) {
            animationThread.interrupt();
            animationThread = null;
        }

        animationThread = new Thread() {
            public void run() {
                Layout<AbstractIshopEntity, EntityRelation> layout = viewer.getGraphLayout();
                Point2D p = new Point(0, 0);

                for(AbstractIshopEntity e:selectedVertices) {
                    Point2D q = layout.transform(e);
                    p.setLocation(p.getX() + q.getX(), p.getY() + q.getY());
                }

                p.setLocation(p.getX() / selectedVertices.size(), p.getY() / selectedVertices.size());

                final MutableTransformer viewTransformer
            	= viewer.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW);

                Point2D lvc = viewer.getRenderContext().getMultiLayerTransformer().inverseTransform(viewer.getCenter());

                final double dx = (lvc.getX() - p.getX()) / 10;
                final double dy = (lvc.getY() - p.getY()) / 10;

                Runnable animator = new Runnable() {
                        public void run() {
                            for (int i = 0; i < 10; i++) {
                            	viewTransformer.translate(dx, dy);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException ex) {
                                }
                            }
                        }
                    };

                Thread thread = new Thread(animator);
                thread.start();
            }

            public void interrupt() {
                //keepGoing = false;
                super.interrupt();
            }
        };
        animationThread.start();
    }
    private void updateZoomLevelValue()
    {
    	zoomValueLabel.setText(String.valueOf(currentZoomLevel) + "%");
    	zoomSlider.setValue(currentZoomLevel);
    }

	public boolean isShowOrganisationNodes() {
		return showOrganisationNodes;
	}

	public void addGUIActionListener(GUIActionListener guiActionListioner)
	{
		guiListenerVector.add(guiActionListioner);
	}
	public void removeGUIActionListener(GUIActionListener guiActionListioner)
	{
		guiListenerVector.remove(guiActionListioner);
	}
	private void callGUIActionListener(GUIActionEvent action){
		for(GUIActionListener guiActionListioner:guiListenerVector)
			guiActionListioner.guiActionPerformed(action);
	}
	public void paintDijstraShortestPath(AbstractIshopEntity sourceVertex, AbstractIshopEntity targetVertex)
	{
		if(shortestEdgesList != null)shortestEdgesList.clear();
		else
			shortestEdgesList = new LinkedList<EntityRelation>();

		if(shortestVeticesList != null)
			shortestVeticesList.clear();
		else
			shortestVeticesList = new LinkedList<AbstractIshopEntity>();

		DijkstraShortestPath<AbstractIshopEntity,EntityRelation> dsp =
			new DijkstraShortestPath<AbstractIshopEntity,EntityRelation>(vv1.getGraphLayout().getGraph());
		dsp.reset();
		shortestEdgesList = dsp.getPath(sourceVertex, targetVertex);
		for(EntityRelation e:shortestEdgesList)
		{
			Pair<AbstractIshopEntity> endpoints = vv1.getGraphLayout().getGraph().getEndpoints(e);
			AbstractIshopEntity v1 = endpoints.getFirst();
			AbstractIshopEntity v2 = endpoints.getSecond();
			shortestVeticesList.add(v1);
			shortestVeticesList.add(v2);
		}
	}
	public void paintUnweightedShortestPath(AbstractIshopEntity sourceVertex, AbstractIshopEntity targetVertex)
	{
		if(shortestEdgesList != null)
			shortestEdgesList.clear();
		else
			shortestEdgesList = new LinkedList<EntityRelation>();

		if(shortestVeticesList != null)
			shortestVeticesList.clear();
		else
			shortestVeticesList = new LinkedList<AbstractIshopEntity>();

		UnweightedShortestPath<AbstractIshopEntity,EntityRelation> usp =
			new UnweightedShortestPath<AbstractIshopEntity,EntityRelation>(vv1.getGraphLayout().getGraph());
		usp.reset();
		shortestEdgesList = ShortestPathUtils.getPath(
				vv1.getGraphLayout().getGraph(),
				usp,
				sourceVertex,
				targetVertex);
		for(EntityRelation e:shortestEdgesList)
		{
			Pair<AbstractIshopEntity> endpoints = vv1.getGraphLayout().getGraph().getEndpoints(e);
			AbstractIshopEntity v1 = endpoints.getFirst();
			AbstractIshopEntity v2 = endpoints.getSecond();
			shortestVeticesList.add(v1);
			shortestVeticesList.add(v2);
		}
	}
    private boolean isEdgeOnPath(EntityRelation e ){
		return shortestEdgesList != null && shortestEdgesList.contains(e);

    }
    private boolean isVertexOnPath(AbstractIshopEntity v ){
		return shortestVeticesList != null && shortestVeticesList.contains(v);

    }
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString()
	{
		return String.valueOf(paneType) + String.valueOf(serialVersionUID);
	}
	private Vector<AbstractIshopEntity> searchFoundVector = new Vector<AbstractIshopEntity>();
	boolean isFoundVertex(AbstractIshopEntity v)
	{
		return searchFoundVector.contains(v);
	}
	

	public void searchVertices(String vName) {
		searchFoundVector.clear();
		Graph<AbstractIshopEntity, EntityRelation> graph = vv1.getGraphLayout().getGraph();
		Collection<AbstractIshopEntity> verticesCollection = graph.getVertices();
		Iterator<AbstractIshopEntity> itr = verticesCollection.iterator();
		while(itr.hasNext())
		{
			AbstractIshopEntity v = itr.next();
			//if(v.getName().toLowerCase().contains(vName.toLowerCase()))
				searchFoundVector.add(v);
		}
		vv1.repaint();
	}
	public NetworkSparseGraph<AbstractIshopEntity,EntityRelation> getGraph(){
		// cache mainGraph to avoid return Forest
		return this.mainGraph;
	}
	public VisualizationViewer<AbstractIshopEntity, EntityRelation> getVisualizationViewer()
	{
		return vv1;
	}

	public Vector<AbstractIshopEntity> getMarkedVertices() {
		return markedVertices;
	}

	public List<AbstractIshopEntity> getShortestVeticesList() {
		return shortestVeticesList;
	}

	public Vector<String> getSelectedTagNameVector() {
		return selectedTagNameVector;
	}

	public void setSelectedTagNameVector(Vector<String> selectedTagNameVector) {
		this.selectedTagNameVector.clear();
		this.selectedTagNameVector = selectedTagNameVector;
		vv1.repaint();
	}

	public boolean isAndTaggedMode() {
		return andTaggedMode;
	}

	public void setAndTaggedMode(boolean andTaggedMode) {
		if(this.andTaggedMode != andTaggedMode){
			this.andTaggedMode = andTaggedMode;
			vv1.repaint();
		}


	}
	public Vector<AbstractIshopEntity> getStartNodes() {
		return startNodes;
	}
	public void addSelectedNode(String entityId)
	{
//		AbstractIshopEntity v = getGraph().getEntityMap().get(entityId).getEntityData();
//		markedVertices.add(v);
//		vv1.repaint();
	}
	public void removeSelectedNode(String entityId)
	{
//		AbstractIshopEntity v = getGraph().getEntityMap().get(entityId).getEntityData();
//		markedVertices.remove(v);
//		vv1.repaint();
	}
	public void addStartNode(String entityId)
	{
//		AbstractIshopEntity v = getGraph().getEntityMap().get(entityId).getEntityData();
//		startNodes.add(v);
//		vv1.repaint();
	}
	public void removeStartNode(String entityId)
	{

	}

	/**
	 * Vertices multiple picking
	 * @param entityIdVector
	 */
	public void pickVertices(Vector<String> entityIdVector)
	{
	}

	public boolean isHighlightKNE1() {
		return highlightKNE1;
	}

	public void setHighlightKNE1(boolean highlightKNE1) {
		this.highlightKNE1 = highlightKNE1;
	}

	public Layout<AbstractIshopEntity, EntityRelation> getLayoutClass() {
		return layoutClass;
	}
}
