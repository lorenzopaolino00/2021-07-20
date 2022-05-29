package it.polito.tdp.yelp.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	YelpDao dao;
	Graph<User, DefaultWeightedEdge> grafo;
	List<User> utenti;
	
	public Model(){
		dao = new YelpDao();
	}
	
	public void creaGrafo(int anno, int minRevisioni) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		utenti = dao.getVertex(anno);
		Graphs.addAllVertices(grafo, utenti);
		
		for(User u1: utenti) {
			for(User u2: utenti) {
				if(!u1.equals(u2)) {
					int sim = dao.getEdge(u1, u2, minRevisioni);
					if(sim > 0) {
						Graphs.addEdge(grafo, u1, u2, sim);
					}
				}
			}
		}
		
		System.out.print("i vertici sono: " + grafo.vertexSet().size());
		System.out.print("\ngli archi sono: " + grafo.edgeSet().size());
	}
	
}
