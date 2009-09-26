/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-6-1 07:38:42
 */
package cn.net.openid.jos.tools.dbupgraders.v1_0_alpha1_v1_0_alpha2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sutra Zhou
 * 
 */
public class Migration {
	private static final Log log = LogFactory.getLog(Migration.class);

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.US);
	static {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	private int batchSize = 1000;
	private boolean deleteAllFromNewDatabase = false;
	private boolean verbose = false;
	private Properties oldProperties = new Properties();
	private Properties newProperties = new Properties();
	private Connection oldConn;
	private Connection newConn;

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize
	 *            the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @return the deleteAllFromNewDatabase
	 */
	public boolean isDeleteAllFromNewDatabase() {
		return deleteAllFromNewDatabase;
	}

	/**
	 * @param deleteAllFromNewDatabase
	 *            the deleteAllFromNewDatabase to set
	 */
	public void setDeleteAllFromNewDatabase(boolean deleteAllFromNewDatabase) {
		this.deleteAllFromNewDatabase = deleteAllFromNewDatabase;
	}

	/**
	 * @return the verbose
	 */
	public boolean isVerbose() {
		return verbose;
	}

	/**
	 * @param verbose
	 *            the verbose to set
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	private void loadOldProperties() throws IOException {
		oldProperties.load(Migration.class
				.getResourceAsStream("/jdbc-old.properties"));
	}

	private void loadNewProperties() throws IOException {
		newProperties.load(Migration.class
				.getResourceAsStream("/jdbc-new.properties"));
	}

	private Connection getConnection(Properties p) throws SQLException,
			ClassNotFoundException {
		Class.forName(p.getProperty("jdbc.driverClassName"));
		Connection conn = DriverManager.getConnection(
				p.getProperty("jdbc.url"), p.getProperty("jdbc.username"), p
						.getProperty("jdbc.password"));
		return conn;
	}

	private void openOldConnection() throws SQLException,
			ClassNotFoundException {
		this.oldConn = this.getConnection(oldProperties);
	}

	private void openNewConnection() throws SQLException,
			ClassNotFoundException {
		this.newConn = this.getConnection(newProperties);
	}

	private void closeOldConnection() {
		DbUtils.closeQuietly(this.oldConn);
	}

	private void closeNewConnection() {
		DbUtils.closeQuietly(this.newConn);
	}

	private void deleteAllDataFromNewDatabase() throws SQLException {
		Statement stmt = newConn.createStatement();
		try {
			stmt.executeUpdate("delete from jos_site");
			stmt.executeUpdate("delete from jos_realm");
			stmt.executeUpdate("delete from jos_password");
			stmt.executeUpdate("delete from jos_email_confirmation_info");
			stmt.executeUpdate("delete from jos_email");
			stmt.executeUpdate("delete from jos_persona");
			stmt.executeUpdate("delete from jos_attribute_value");
			stmt.executeUpdate("delete from jos_attribute");
			stmt.executeUpdate("delete from jos_user");
		} finally {
			DbUtils.closeQuietly(stmt);
		}
	}

	private void doMigrate() throws SQLException {
		if (isDeleteAllFromNewDatabase()) {
			log.info("Deleting all data from new database...");
			this.deleteAllDataFromNewDatabase();
			this.printCounts();
			log.info("Deleted all data from new database.");
		}

		log.info("Migrating: openid_user -> jos_user, jos_persona...");
		migrateUser();
		log.info("Migrated openid_user.");

		log.info("Migrating: openid_credential -> jos_password...");
		migrateCredential();
		log.info("Migrated openid_credential.");
	}

	private void doCalcShaHex() throws SQLException {
		String selectSql = "select password_id, password_plaintext from jos_password";
		String updateSql = "update jos_password set password_sha_hex = ? where password_id = ?";
		Statement stmt = newConn.createStatement();
		PreparedStatement pstmt = newConn.prepareStatement(updateSql);
		ResultSet rs = stmt.executeQuery(selectSql);
		try {
			String id, plaintext, shaHex;
			while (rs.next()) {
				id = rs.getString(1);
				plaintext = rs.getString(2);
				shaHex = DigestUtils.shaHex(plaintext);
				pstmt.setString(1, shaHex);
				pstmt.setString(2, id);
				pstmt.addBatch();
				if (rs.getRow() % (this.batchSize / 2) == 0) {
					pstmt.executeBatch();
				}
			}
			pstmt.executeBatch();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			DbUtils.closeQuietly(pstmt);
		}
	}

	private static int getCount(Connection conn, String tableName)
			throws SQLException {
		int count = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select count(*) from " + tableName);
		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	private int getCountOfOldUser() throws SQLException {
		return getCount(oldConn, "openid_user");
	}

	private int getCountOfOldCredential() throws SQLException {
		return getCount(oldConn, "openid_credential");
	}

	private int getCountOfNewUser() throws SQLException {
		return getCount(newConn, "jos_user");
	}

	private int getCountOfNewPassword() throws SQLException {
		return getCount(newConn, "jos_password");
	}

	private int getCountOfNewPersona() throws SQLException {
		return getCount(newConn, "jos_persona");
	}

	private void printCounts() throws SQLException {
		log.info("Old user count: " + this.getCountOfOldUser());
		log.info("Old credential count: " + this.getCountOfOldCredential());
		log.info("New user count: " + this.getCountOfNewUser());
		log.info("New password count: " + this.getCountOfNewPassword());
		log.info("New persona count: " + this.getCountOfNewPersona());
	}

	private void migrateUser() throws SQLException {
		String insertUserSql = "insert into jos_user(user_id, user_username, user_creation_date)"
				+ "\nvalues(?, ?, ?)";
		String insertPersonaSql = "insert into jos_persona"
				+ "\n(persona_id, persona_user_id, persona_name, persona_nickname,"
				+ "\npersona_email, persona_fullname, persona_dob, persona_gender,"
				+ "\npersona_postcode, persona_country, persona_language, persona_timezone,"
				+ "\npersona_creation_date) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement pstmt = oldConn
				.prepareStatement("select * from openid_user");
		PreparedStatement insertUserPstmt = newConn
				.prepareStatement(insertUserSql);
		PreparedStatement insertPersonaPstmt = newConn
				.prepareStatement(insertPersonaSql);
		ResultSet rs = pstmt.executeQuery();
		try {
			String id, username, nickname, email, fullname, gender, postcode, country, language, timezone;
			Date dob;
			Timestamp registertime;
			while (rs.next()) {
				id = rs.getString("user_id");
				username = rs.getString("user_username");
				nickname = rs.getString("user_nickname");
				email = rs.getString("user_email");
				fullname = rs.getString("user_fullname");
				gender = rs.getString("user_gender");
				postcode = rs.getString("user_postcode");
				country = rs.getString("user_country");
				language = rs.getString("user_language");
				timezone = rs.getString("user_timezone");

				dob = rs.getDate("user_dob");
				registertime = rs.getTimestamp("user_registertime");

				if (verbose) {
					log.info(String.format("Migrating user(username): %1$s",
							username));
				}

				insertUserPstmt.setString(1, id);
				insertUserPstmt.setString(2, username);
				insertUserPstmt.setTimestamp(3, registertime);
				insertUserPstmt.addBatch();

				insertPersonaPstmt.setString(1, id);
				insertPersonaPstmt.setString(2, id);
				insertPersonaPstmt.setString(3, "Default Persona");
				insertPersonaPstmt.setString(4, nickname);
				insertPersonaPstmt.setString(5, email);
				insertPersonaPstmt.setString(6, fullname);
				if (dob == null) {
					insertPersonaPstmt.setString(7, "0000-00-00");
				} else {
					insertPersonaPstmt.setString(7, sdf.format(dob));
				}
				insertPersonaPstmt.setString(8, gender);
				insertPersonaPstmt.setString(9, postcode);
				insertPersonaPstmt.setString(10, country);
				insertPersonaPstmt.setString(11, language);
				insertPersonaPstmt.setString(12, timezone);
				insertPersonaPstmt.setTimestamp(13, registertime);
				insertPersonaPstmt.addBatch();

				if (rs.getRow() % (this.batchSize / 2) == 0) {
					insertUserPstmt.executeBatch();
					insertPersonaPstmt.executeBatch();
				}
			}
			insertUserPstmt.executeBatch();
			insertPersonaPstmt.executeBatch();
		} catch (SQLException e) {
			log.error("Exception occured: ", e.getNextException());
			throw e;
		} finally {
			DbUtils.closeQuietly(null, pstmt, rs);
		}
	}

	private void migrateCredential() throws SQLException {
		String oldSql = "select c1.credential_id, c1.credential_userid, c1.credential_info, '', u1.user_registertime"
				+ "\nfrom openid_credential c1 inner join openid_user u1 on u1.user_id = c1.credential_userid"
				+ "\n where c1.credential_handler = '1'";
		String newSql = "insert into jos_password"
				+ "\n(password_id, password_user_id, password_name, password_plaintext, password_sha_hex, password_creation_date)"
				+ "values(?, ?, 'Default Password', ?, ?, ?)";

		PreparedStatement oldPstmt = oldConn.prepareStatement(oldSql);
		PreparedStatement newPstmt = newConn.prepareStatement(newSql);
		ResultSet rs = oldPstmt.executeQuery();
		try {
			String passwordId, passwordUserId, passwordPlaintext;
			Timestamp passwordCreationDate;
			while (rs.next()) {
				passwordId = rs.getString(1);
				passwordUserId = rs.getString(2);
				passwordPlaintext = rs.getString(3);
				passwordCreationDate = rs.getTimestamp(5);

				if (verbose) {
					log.info(String.format(
							"Migrating password of user(id): %1$s.",
							passwordUserId));
				}

				newPstmt.setString(1, passwordId);
				newPstmt.setString(2, passwordUserId);
				try {
					passwordPlaintext = new String(rs.getBytes(3), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				newPstmt.setString(3, passwordPlaintext);
				newPstmt.setString(4, DigestUtils.shaHex(passwordPlaintext));
				newPstmt.setTimestamp(5, passwordCreationDate);
				newPstmt.addBatch();

				if (rs.getRow() % this.batchSize == 0) {
					newPstmt.executeBatch();
				}
			}
			newPstmt.executeBatch();
		} finally {
			DbUtils.closeQuietly(null, oldPstmt, rs);
			DbUtils.closeQuietly(newPstmt);
		}
	}

	public void migrate() throws IOException, SQLException,
			ClassNotFoundException {
		this.loadOldProperties();
		this.loadNewProperties();
		log.info("Old database: " + oldProperties.getProperty("jdbc.url"));
		log.info("New database: " + newProperties.getProperty("jdbc.url"));
		log.info("Batch size: " + batchSize);
		this.openOldConnection();
		this.openNewConnection();
		try {
			printCounts();
			this.doMigrate();
			printCounts();
		} finally {
			this.closeOldConnection();
			this.closeNewConnection();
		}
	}

	public void calcShaHex() throws IOException, SQLException,
			ClassNotFoundException {
		this.loadNewProperties();
		log.info("New database: " + newProperties.getProperty("jdbc.url"));
		log.info("Batch size: " + batchSize);
		this.openNewConnection();
		try {
			this.doCalcShaHex();
		} finally {
			this.closeNewConnection();
		}
	}

	private static void usage(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("migrate", options);
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, SQLException,
			ClassNotFoundException {
		Options options = new Options();
		options.addOption("h", "help", false, "print this message");
		options.addOption("s", "sha", false,
				"calculate Sha Hex of password only");
		options.addOption("b", "batch", true, "SQL query batch size");
		options.addOption("d", "delete", false,
				"delete all data from the new database before migrating");
		options.addOption("v", "verbose", false, "be extra verbose");
		PosixParser p = new PosixParser();
		CommandLine cl;
		try {
			cl = p.parse(options, args);
		} catch (ParseException e) {
			usage(options);
			return;
		}

		if (cl.hasOption("help")) {
			usage(options);
			return;
		}

		Migration migration = new Migration();
		if (cl.hasOption("batch")) {
			int batchSize = NumberUtils.toInt(cl.getOptionValue("batch"));
			migration.setBatchSize(batchSize);
		}
		if (cl.hasOption("delete")) {
			migration.setDeleteAllFromNewDatabase(true);
		}
		if (cl.hasOption("verbose")) {
			migration.setVerbose(true);
		}

		if (cl.hasOption("sha")) {
			migration.calcShaHex();
		} else {
			migration.migrate();
		}
	}
}
