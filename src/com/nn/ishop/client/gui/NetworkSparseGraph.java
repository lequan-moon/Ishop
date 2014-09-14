package com.nn.ishop.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.nn.ishop.client.DataChangeListener;
import com.nn.ishop.client.logic.admin.CustomerLogic;
import com.nn.ishop.server.dto.AbstractIshopEntity;
import com.nn.ishop.server.dto.EntityRelation;
import com.nn.ishop.server.dto.customer.Customer;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * This is the main data structure for connect
 * it is an extension of Jung2 DirectedSparseGraph class 
 * (with EntityWrappers as Nodes and RelationDtos as edges) 
 * with additional methods to access fast entities and related data
 */
public final class NetworkSparseGraph<V, E>
	extends DirectedSparseGraph<AbstractIshopEntity, EntityRelation> {

	private static final long serialVersionUID = -6225252278168055447L;
	/**
	 * main data for this graph,  entities just
	 */
	private HashMap<java.lang.String, AbstractIshopEntity> entities
	= new HashMap<java.lang.String, AbstractIshopEntity>();

	private boolean subGraph = false;

	/** Define weight of each for each Edge - it is a must now*/
	private Map<EntityRelation, Number> edgeWeight = new HashMap<EntityRelation, Number>();


	/** Entities belong to tag <tag name, {entities}>*/
	private Map<String, List<AbstractIshopEntity>> tagEntities = new HashMap<String, List<AbstractIshopEntity>>();

	/** Tag frequency <tag name,{frequency in network, frequency in project}>*/
	private Map<String, List<Number>> tagFrequency = new HashMap<String, List<Number>>();

	public NetworkSparseGraph(HashMap<String, AbstractIshopEntity> entities) {
		super();
		this.entities = entities;
	}

	public NetworkSparseGraph() {
		super();
		entities = new HashMap<String, AbstractIshopEntity>();
		// @deprecated Build demo graph data for person use case
		//buildGraph();// initialized it from Connect class to make suite for sub graph
	}
	public NetworkSparseGraph(boolean kne) {
		subGraph = kne;
	}

	public AbstractIshopEntity getEntityById(String id) {
		return entities.get(id);
	}

	/**
	 * This method use for updating data for specified entity
	 * not only entityData but also relation, tags, ...
	 * those change should be done outside this class
	 * @param ew
	 */
	public void removeTagFromEntity(AbstractIshopEntity ew, AbstractIshopEntity changedObject) {
	}

	public void updateEntity(AbstractIshopEntity entity) {	
	}

	public void addEntity(AbstractIshopEntity entity) {
		addVertex(entity);
	}

	public AbstractIshopEntity[] getAllEntities() {
		if(vertices == null ){
			System.out.println("Null entities");
		}
		Collection<AbstractIshopEntity> ents = entities.values();
		AbstractIshopEntity[] ret = ents.toArray(new AbstractIshopEntity[ents.size()]);
		return ret;
	}

	public String getEntityType() {
		return null;
	}

	public NetworkSparseGraph<V,E> getGraph() {
		return this;
	}

	public void removeEntity(String id) {
		removeVertex(id);
	}

	public void addDataChangeListener(DataChangeListener dcl) {
	}

	public void removeDataChangedListener(DataChangeListener dcl) {
	}

    /* (non-Javadoc)
     * @see edu.uci.ics.jung.graph.DirectedSparseGraph#getIncidentEdges(java.lang.Object)
     */
    public Collection<EntityRelation> getIncidentEdges(AbstractIshopEntity vertex)
    {
        if (!containsVertex(vertex)){
        	return null;
        }

        Collection<EntityRelation> incident_edges = new HashSet<EntityRelation>();
        incident_edges.addAll(getIncoming_internal(vertex));
        incident_edges.addAll(getOutgoing_internal(vertex));
        return Collections.unmodifiableCollection(incident_edges);
    }

    public boolean removeVertex(String vertexId) {
    	AbstractIshopEntity tempVertex = null;
        try{
        	for(AbstractIshopEntity vertex:vertices.keySet()){
        		if(vertex.getIdAsString().equals(vertexId))
        			tempVertex = vertex;
        	}
        }
        catch(Exception e){};
        if(tempVertex == null)
        	return false;
        removeVertex(tempVertex);
        return true;
    }

    /* (non-Javadoc)
     * @see edu.uci.ics.jung.graph.DirectedSparseGraph#removeVertex(java.lang.Object)
     */
    public boolean removeVertex(AbstractIshopEntity vertex) {
        if (!containsVertex(vertex))
            return false;

        // Copy to avoid concurrent modification in removeEdge
        ArrayList<EntityRelation> incident = new ArrayList<EntityRelation>(getIncoming_internal(vertex));
        incident.addAll(getOutgoing_internal(vertex));

        for (EntityRelation edge : incident)
            removeEdge(edge);
        vertices.remove(vertex);
        return true;
    }

    /* (non-Javadoc)
     * @see edu.uci.ics.jung.graph.DirectedSparseGraph#removeEdge(java.lang.Object)
     */
    public boolean removeEdge(EntityRelation edge) {
        if (!containsEdge(edge))
            return false;

        Pair<AbstractIshopEntity> endpoints = this.getEndpoints(edge);
        AbstractIshopEntity source = endpoints.getFirst();
        AbstractIshopEntity dest = endpoints.getSecond();

        // remove vertices from each others' adjacency maps
        vertices.get(source).getSecond().remove(dest);
        vertices.get(dest).getFirst().remove(source);

        edges.remove(edge);
        return true;
    }

	public Map<EntityRelation, Number> getEdgeWeight() {
		return edgeWeight;
	}

	public boolean isSubGraph() {
		return subGraph;
	}

	/**
	 * Build a demo graph, use only for Developed Period, not for Release
	 */
	
	public void buildGraph() {	
		List<Customer> customers = null;
		try {
			customers = CustomerLogic.getInstance().loadCustomer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Customer lastVertex = null;
		for(Customer c:customers ){
			entities.put(String.valueOf(c.getId()), c);
			addVertex(c);
			if(lastVertex != null)
				addEdge(new EntityRelation(),c,lastVertex);
			lastVertex = c;
		}
	}

	public Map<String, List<AbstractIshopEntity>> getTagsEntities() {
		tagEntities.clear();
		return tagEntities;
	}

	public Map<String, List<Number>> getTagsFrequency() {
		tagFrequency.clear();
		return tagFrequency;
	}
	public HashMap<String, AbstractIshopEntity> getEntityMap()
	{
		return this.entities;
	}
}