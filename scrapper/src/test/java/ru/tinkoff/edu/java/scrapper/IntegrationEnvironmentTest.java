package ru.tinkoff.edu.java.scrapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class IntegrationEnvironmentTest extends IntegrationEnvironment {

    @Test
    @SneakyThrows
    void connectionGetMetaDataReturnCorrectSchema() {

        Connection connection = getConnection();
        List<String> tableNames = getTablesNames(connection);
        Set<String> correctNames = new HashSet<>(Arrays.asList(
            "link", "chat", "databasechangeloglock", "databasechangelog", "subscription"));
        for (String name : tableNames) {
            assertThat(correctNames).contains(name);
        }
    }

    @SneakyThrows
    private List<String> getTablesNames(Connection connection) {

        List<String> tablesNames = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet rs = metaData.getTables(null, "public", "%", new String[] {"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tablesNames.add(tableName);
            }
        }
        return tablesNames;
    }
}
