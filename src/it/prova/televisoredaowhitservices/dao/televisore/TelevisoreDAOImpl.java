package it.prova.televisoredaowhitservices.dao.televisore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import it.prova.televisoredaowhitservices.dao.AbstractMySQLDAO;
import it.prova.televisoredaowhitservices.model.Televisore;


public class TelevisoreDAOImpl extends AbstractMySQLDAO implements TelevisoreDAO{

	@Override
	public List<Televisore> list() throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
				if (isNotActive())
					throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

				ArrayList<Televisore> result = new ArrayList<Televisore>();

				try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from televisore")) {

					while (rs.next()) {
						Televisore teleTemp = new Televisore();
						teleTemp.setId(rs.getLong("ID"));
						teleTemp.setMarca(rs.getString("marca"));
						teleTemp.setModello(rs.getString("modello"));
						teleTemp.setPollici(rs.getInt("pollici"));
						teleTemp.setDataproduzione(rs.getDate("dataproduzione"));
						
						result.add(teleTemp);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return result;
	}

	@Override
	public Televisore get(Long idInput) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Televisore teleTemp=null;
		try (PreparedStatement ps = connection.prepareStatement("select * from Televisore where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					teleTemp = new Televisore();
					teleTemp.setId(rs.getLong("ID"));
					teleTemp.setMarca(rs.getString("marca"));
					teleTemp.setModello(rs.getString("modello"));
					teleTemp.setPollici(rs.getInt("pollici"));
					teleTemp.setDataproduzione(rs.getDate("dataproduzione"));
				} else {
					teleTemp = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return teleTemp;
	}

	@Override
	public int update(Televisore input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("update televisore set marca=?, modello=?, pollici=?, dataproduzione=? where id=?;")) {
			ps.setString(1, input.getMarca());
			ps.setString(2, input.getModello());
			ps.setInt(3, input.getPollici());
			ps.setDate(4, new java.sql.Date(input.getDataproduzione().getTime()));
			ps.setLong(5, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Televisore input) throws Exception {
				if (isNotActive())
					throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

				if (input == null)
					throw new Exception("Valore di input non ammesso.");

				int result = 0;
				try (PreparedStatement ps = connection.prepareStatement(
						"insert into televisore (marca, modello, pollici, dataproduzione) values (?,?,?,?)")) {
					ps.setString(1, input.getMarca());
					ps.setString(2, input.getModello());
					ps.setInt(3, input.getPollici());
					ps.setDate(4, new java.sql.Date(input.getDataproduzione().getTime()));
					result = ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return result;
	}

	@Override
	public int delete(Televisore input) throws Exception {
				if (isNotActive())
					throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

				if (input == null || input.getId() == null || input.getId() < 1)
					throw new Exception("Valore di input non ammesso.");

				int result = 0;
				try (PreparedStatement ps = connection.prepareStatement("DELETE FROM televisore WHERE ID=?")) {
					ps.setLong(1, input.getId());
					result = ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return result;
	}

	@Override
	public List<Televisore> findByExample(Televisore input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Televisore> result = new ArrayList<Televisore>();

		String query = "select * from televisore where 1=1 ";
		if (input.getMarca() != null && !input.getMarca().isEmpty()) {
			query += " and marca like '" + input.getMarca() + "%' ";
		}

		if (input.getModello() != null && !input.getModello().isEmpty()) {
			query += " and modello like '" + input.getModello() + "%' ";
		}

		if (input.getPollici() != null && input.getPollici() != 0) {
			query += " and pollici = '" + input.getPollici() + "' ";
		}

		if (input.getDataproduzione() != null) {
			query += " and dataproduzione='" + new java.sql.Date(input.getDataproduzione().getTime()) + "' ";
		}
		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				Televisore televisoreTemp = new Televisore();
				televisoreTemp.setMarca(rs.getString("marca"));
				televisoreTemp.setModello(rs.getString("modello"));
				televisoreTemp.setPollici(rs.getInt("pollici"));
				televisoreTemp.setDataproduzione(rs.getDate("dataproduzione"));
				televisoreTemp.setId(rs.getLong("id"));
				result.add(televisoreTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Televisore> allTVsProductsInAIntervalOfdate(Date iniziale, Date finale) throws Exception {
		if(isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (iniziale == null || finale == null)
			throw new Exception("Valore di input non ammesso.");
		List<Televisore>result = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement("select * from televisore where dataproduzione between ? and ?")) {
			ps.setDate(1, new java.sql.Date(iniziale.getTime()));
			ps.setDate(2, new java.sql.Date(finale.getTime()));
			
			try(ResultSet rs = ps.executeQuery()){
			while (rs.next()) {
				Televisore teleTemp = new Televisore();
				teleTemp.setId(rs.getLong("ID"));
				teleTemp.setMarca(rs.getString("marca"));
				teleTemp.setModello(rs.getString("modello"));
				teleTemp.setPollici(rs.getInt("pollici"));
				teleTemp.setDataproduzione(rs.getDate("dataproduzione"));
				
				result.add(teleTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		}
		return result;
	}

	@Override
	public Televisore biggerTv() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Televisore> listOfBrandsTelevisionsProductsInTheLastSixMonths() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
