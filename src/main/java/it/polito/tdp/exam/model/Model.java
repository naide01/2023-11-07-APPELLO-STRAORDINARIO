package it.polito.tdp.exam.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.exam.db.BaseballDAO;


public class Model {
	private BaseballDAO dao;
	private SimpleWeightedGraph<Team, DefaultWeightedEdge> grafo;
	private Map<String, Team> mappa;
	private Map<Team, Double> mappaSalari;
	
	public Model() {
		this.dao = new BaseballDAO();
		mappa = new HashMap<String, Team>();
		mappaSalari = new HashMap<Team, Double>();
		
	}
	public void creaGrafo(String anno) {
		this.grafo = new SimpleWeightedGraph<Team, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//RIEMPIO LA MAPPA:
		for (Team t: this.dao.siglaSquadra(anno)) {
			mappa.put(t.getTeamCode(), t);
		}
		this.mappaSalari = this.dao.arcoPeso(anno, mappa);
		
		//VERTICI = SQUADRE DI UN DETERMINATO ANNO
		Graphs.addAllVertices(this.grafo, this.listaSquadre(anno));
		
		//ARCHI = TUTTE LE COPPIE DI SQUADRE DISTINTE
		for (Team t1: this.grafo.vertexSet()) {
			for (Team t2: this.grafo.vertexSet()) {
				if (!t1.equals(t2)) {
					Graphs.addEdgeWithVertices(this.grafo, t1, t2, mappaSalari.get(t1) + mappaSalari.get(t2));
				}
			}
		}
	}
	public List<String> stampaAdiacenti(Team squadra) {
		//La classe Pair fornisce un modo conveniente per raggruppare due oggetti e trattarli come un'unica entità
		//List<Pair<Team, Double>> result = new ArrayList<>() ;
		List<String> result = new ArrayList<>();
		
		List<Team> adiacenti = Graphs.neighborListOf(this.grafo, squadra);
		for (Team t : adiacenti) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(t, squadra));
			result.add(new String(t + "" + peso));
			
		}
		return result;
	}
	

	public List<String> getAnno() {
		return this.dao.readYear();
	}
	public int getNmumSquadrePerAnno(String anno){
		return this.dao.numSquadre(anno);
	}
	public List<Team> listaSquadre(String anno){
		return this.dao.siglaSquadra(anno);
	}
	public int numVertex() {
		return this.grafo.vertexSet().size();
	}
	public int numEdge() {
		return this.grafo.edgeSet().size();
	}
}
