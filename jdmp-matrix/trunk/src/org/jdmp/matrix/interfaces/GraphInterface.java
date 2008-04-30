package org.jdmp.matrix.interfaces;

import java.util.List;

public interface GraphInterface<N,E> {
  
	public List<N> getNodeList();
	
	public boolean isDirected();
	
	public void setDirected(boolean directed);

	/**
	 * @holger Waere es nicht besser, eine Liste von Kanten zurückzuliefern?
	 * Oder einen Iterator über Kanten?
	 * @return
	 */
	public List<long[]> getEdgeList();

	public E getEdgeValue(long nodeIndex1, long nodeIndex2);
	
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public E getEdgeValue(N node1, N node2);

	/**
	 * @holger Methode macht keinen Sinn, da Kante nich erzeugt werden kann.
	 * @param node1
	 * @param node2
	 * @deprecated
	 */
	public void addEdge(N node1, N node2);
	
	/**
	 * @holger Methode macht keinen Sinn, da Kante nich erzeugt werden kann.
	 * @param nodeIndex1
	 * @param nodeIndex2
	 * @deprecated
	 */
	public void addEdge(long nodeIndex1, long nodeIndex2);
	
	/**
	 * @holger Methode macht keinen Sinn, da Kante nich erzeugt werden kann.
	 * @param node1
	 * @param node2
	 * @deprecated
	 */
	public void addDirectedEdge(N node1, N node2);

    /**
     * @holger Methode macht keinen Sinn, da Kante nich erzeugt werden kann.
     * @param nodeIndex1
     * @param nodeIndex2
     * @deprecated
     */
	public void addDirectedEdge(long nodeIndex1, long nodeIndex2);

	/**
	 * @holger Wuerde ich weglassen
	 * @param edgeObject
	 * @param nodeIndex1
	 * @param nodeIndex2
	 * @deprecated
	 */
	public void setDirectedEdge(E edgeObject, long nodeIndex1, long nodeIndex2);
	
	public void setEdge(E edgeObject, long nodeIndex1, long nodeIndex2);
	
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public void setDirectedEdge(E edgeObject, N node1, N node2);
	
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public void setEdge(E edgeObject, N node1, N node2);
	
	public void setUndirectedEdge(E edgeObject, long nodeIndex1, long nodeIndex2);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public void setUndirectedEdge(E edgeObject, N node1, N node2);

    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public void addUndirectedEdge(N node1, N node2);

    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public void addUndirectedEdge(long nodeIndex1, long nodeIndex2);

    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public void removeDirectedEdge(long nodeIndex1, long nodeIndex2);
	
	public void removeEdge(long nodeIndex1, long nodeIndex2);
	
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public void removeDirectedEdge(N node1, N node2);
	
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public void removeEdge(N node1, N node2);
	
	public void removeUndirectedEdge(long nodeIndex1, long nodeIndex2);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public void removeUndirectedEdge(N node1, N node2);

    public N getNode(long index);
    
	public void addNode(N node);
	
	public void setNode(N node, long index);
	
    /**
     * @holger Wuerde ich weglassen, es gibt ja addNode und setNode
     * @deprecated
     */
	public void insertNode(N node, long index);

	public void removeNode(N node);
	
	public void removeNode(long node);

	public boolean isConnected(long nodeIndex1, long nodeIndex2);
	
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public boolean isConnected(N node1, N node2);

	public long getIndexOfNode(N node);

	public int getEdgeCount();

	public int getNodeCount();
	
	public int getChildCount(long nodeIndex);
	
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
	public int getChildCount(N node);
	
	public int getParentCount(long nodeIndex);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public int getParentCount(N node);
    
    public int getDegree(long nodeIndex);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public int getDegree(N node);
    
    public List<N> getChildren(long nodeIndex);
    
    public List<Long> getChildIndices(long nodeIndex);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public List<Long> getChildIndices(N node);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public List<N> getChildren(N node);
    
    public List<N> getParents(long nodeIndex);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public List<N> getParents(N node);
    
    public List<Long> getParentIndices(long nodeIndex);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public List<Long> getParentIndices(N node);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public void addChild(N node, N child);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public void addChild(long nodeIndex, long childIndex);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public void addParent(N node, N parent);
    
    /**
     * @holger Wuerde ich weglassen
     * @deprecated
     */
    public void addParent(long nodeIndex, long parentIndex);

}
