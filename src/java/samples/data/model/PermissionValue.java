/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samples.data.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import samples.core.SqlDatabase;

/**
 *
 * @author SargTeX
 */
public class PermissionValue implements IDataModel {
	
	private int id;
	private String value;
	private String permission;
	private int objectId;
	private String objectType;
	private boolean existing = false;

	@Override
	public boolean exists() {return existing;}

	@Override
	public void create() throws SQLException {
		String query = "INSERT INTO permission_value (value, permission, objectId, objectType) VALUES (?, ?, ?, ?)";
		this.id = SqlDatabase.getInstance().execute(query, value, permission, objectId, objectType);
	}

	@Override
	public void remove() throws SQLException {
		String query = "DELETE FROM permission_value WHERE id = ?";
		SqlDatabase.getInstance().execute(query, id+"");
	}

	@Override
	public void read() throws SQLException {
		String query = "SELECT * FROM permission_value WHERE id = ?";
		ResultSet rs = SqlDatabase.getInstance().fetch(query, id+"");
		if (rs.first()) {
			existing = true;
			value = rs.getString("value");
			permission = rs.getString("permission");
			objectId = rs.getInt("objectId");
			objectType = rs.getString("objectType");
		}
	}

	@Override
	public void update() throws SQLException {
		String query = "UPDATE permission_value SET value = ?, permission = ?, objectId = ?, objectType = ? WHERE id = ?";
		SqlDatabase.getInstance().execute(query, value, permission, objectId, objectType, id);
	}

	@Override
	public void install() throws Exception {
		String query = "CREATE TABLE permission_value ("
				+ "id INT( 11 ) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "value TEXT DEFAULT '',"
				+ "permission VARCHAR( 255 ) NOT NULL,"
				+ "objectId INT( 11 ) NOT NULL,"
				+ "objectType VARCHAR( 255 ) NOT NULL)";
		SqlDatabase.getInstance().execute(query);
	}

	@Override
	public void uninstall() throws Exception {
		String query = "DROP TABLE IF EXISTS permission_value";
		SqlDatabase.getInstance().execute(query);
	}
	
}