/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.debezium.jdbc.JdbcConfiguration;
import io.debezium.jdbc.JdbcConnection.ConnectionFactory;

public class OracleConnectionFactory implements ConnectionFactory {

    private final String driver;

    public OracleConnectionFactory(OracleConnectorConfig connectorConfig) {
        this.driver = (connectorConfig.getStreamingEngine() == OracleConnectorConfig.StreamingEngine.LOG_MINER) ? "thin" : "oci";
    }

    @Override
    public Connection connect(JdbcConfiguration config) throws SQLException {
        String hostName = config.getHostname();
        int port = config.getPort();
        String database = config.getDatabase();
        String user = config.getUser();
        String password = config.getPassword();

        return DriverManager.getConnection(
              "jdbc:oracle:" + driver + ":@" + hostName + ":" + port + "/" + database, user, password
        );
    }
}
