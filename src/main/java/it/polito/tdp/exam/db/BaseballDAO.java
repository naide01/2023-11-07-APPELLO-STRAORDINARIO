package it.polito.tdp.exam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.exam.model.People;
import it.polito.tdp.exam.model.Team;

public class BaseballDAO {
	
	public List<String> readYear() {
		String sql = "select distinct year "
				+ "from teams t "
				+ "where year>= 1980 ";
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("year"));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database", e);
		}
	}
	

	public List<People> readAllPlayers() {
		String sql = "SELECT * " + "FROM people";
		List<People> result = new ArrayList<People>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new People(rs.getString("playerID"), rs.getString("birthCountry"), rs.getString("birthCity"),
						rs.getString("deathCountry"), rs.getString("deathCity"), rs.getString("nameFirst"),
						rs.getString("nameLast"), rs.getInt("weight"), rs.getInt("height"), rs.getString("bats"),
						rs.getString("throws"), getBirthDate(rs), getDebutDate(rs), getFinalGameDate(rs),
						getDeathDate(rs)));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database", e);
		}
	}

//	public List<Team> readAllTeams(String anno) {
//		String sql = "select distinct name "
//				+ "from teams "
//				+ "where year = ? ";
//		List<Team> result = new ArrayList<Team>();
//
//		try {
//			Connection conn = DBConnect.getConnection();
//			PreparedStatement st = conn.prepareStatement(sql);
//			st.setString(1, anno);
//			ResultSet rs = st.executeQuery();
//
//			while (rs.next()) {
//				result.add(new Team(rs.getInt("iD"), rs.getInt("year"), rs.getString("teamCode"), rs.getString("divID"),
//						rs.getInt("div_ID"), rs.getInt("teamRank"), rs.getInt("games"), rs.getInt("gamesHome"),
//						rs.getInt("wins"), rs.getInt("losses"), rs.getString("divisionWinnner"),
//						rs.getString("leagueWinner"), rs.getString("worldSeriesWinnner"), rs.getInt("runs"),
//						rs.getInt("hits"), rs.getInt("homeruns"), rs.getInt("stolenBases"), rs.getInt("hitsAllowed"),
//						rs.getInt("homerunsAllowed"), rs.getString("name"), rs.getString("park")));
//			}
//
//			conn.close();
//			return result;
//
//		} catch (SQLException e) {
//			System.out.println("Errore connessione al database");
//			throw new RuntimeException("Errore lettura squadre! ", e);
//		}
//	}
	
	public int numSquadre(String anno){
		String sql = "select count(*) as c "
				+ "from teams "
				+ "where year = ? ";
		int count = 0;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, anno);
			ResultSet rs = st.executeQuery();
		
			while (rs.next()) {
				count = rs.getInt("c");
			}
		
			conn.close();
			return count;
		
		} catch (SQLException e) {
			System.out.println("Errore connessione al database");
		throw new RuntimeException("Errore lettura squadre! ", e);
			}
	}
	
	public List<Team> siglaSquadra(String anno) {
		String sql = "select * "
				+ "from teams "
				+ "where year = ? ";
		
		List<Team> squadre = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, anno);
			ResultSet rs = st.executeQuery();
		
			while (rs.next()) {
				squadre.add(new Team(rs.getInt("ID"), rs.getInt("year"), rs.getString("teamCode"), rs.getString("divID"),
						rs.getInt("div_ID"), rs.getInt("teamRank"), rs.getInt("games"), rs.getInt("gamesHome"),
						rs.getInt("wins"), rs.getInt("losses"), rs.getString("divisionWinnner"),
						rs.getString("leagueWinner"), rs.getString("worldSeriesWinnner"), rs.getInt("runs"),
						rs.getInt("hits"), rs.getInt("homeruns"), rs.getInt("stolenBases"), rs.getInt("hitsAllowed"),
						rs.getInt("homerunsAllowed"), rs.getString("name"), rs.getString("park")));
			}
		
			conn.close();
			return squadre;
		
		} catch (SQLException e) {
			System.out.println("Errore connessione al database");
		throw new RuntimeException("Errore lettura squadre! ", e);
			}
	}
	public Map<Team, Double> arcoPeso(String anno, Map<String, Team> idMap){
		String sql = "select t.teamCode , sum(s.salary) as salario "
				+ "from salaries s , people p , appearances a , teams t "
				+ "where s.playerID = p.playerID "
				+ "and p.playerID = a.playerID "
				+ "and t.ID = a.teamID "
				+ "and a.year =t.year "
				+ "and t.year = ? "
				+ "group by t.teamCode ";
		
		Map<Team, Double> arco = new HashMap<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				arco.put(idMap.get(rs.getString("teamCode")), rs.getDouble("salario"));
			}
			
			conn.close();
			return arco;
		
		} catch (SQLException e) {
			System.out.println("Errore connessione al database");
		throw new RuntimeException("Errore peso arco!", e);
		
		}	
		
	}
	

	// =================================================================
	// ==================== HELPER FUNCTIONS =========================
	// =================================================================

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getBirthDate(ResultSet rs) {
		try {
			if (rs.getDate("birth_date") != null) {
				return rs.getDate("birth_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getDebutDate(ResultSet rs) {
		try {
			if (rs.getDate("debut_date") != null) {
				return rs.getDate("debut_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getFinalGameDate(ResultSet rs) {
		try {
			if (rs.getDate("finalgame_date") != null) {
				return rs.getDate("finalgame_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getDeathDate(ResultSet rs) {
		try {
			if (rs.getDate("death_date") != null) {
				return rs.getDate("death_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
