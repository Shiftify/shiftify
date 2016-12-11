package cz.cvut.fit.shiftify.data;

import android.app.ActionBar;

import java.util.HashMap;

/**
 * Created by lukas on 07.12.2016.
 */

public abstract class DbTable {
    public static class QueryCondition {
        public enum ComparisonType {
            GREATERTHAN(">"),
            GREATEROREQUALTHAN(">="),
            EQUAL("="),
            NOTEQUAL("!="),
            LESSTHAN("<"),
            LESSOREQUALTHAN("<=");

            private String type_name;
            ComparisonType(String type_name) {
                this.type_name = type_name;
            }
            public String getValue() {
                return this.type_name;
            }
        }

        public QueryCondition(String columnName, ComparisonType comparison, String value) {
            this.condition = columnName + comparison.getValue() + "'" + value + "'";
        }
        public QueryCondition(QueryCondition condition) {
            this.condition = condition.condition;
        }

        protected String condition;
        public QueryCondition or(QueryCondition condition) {
            this.condition = "(" + this.condition + ") OR (" + condition.condition + ")";
            return this;
        }
        public QueryCondition and(QueryCondition condition) {
            this.condition = "(" + this.condition + ") AND (" + condition.condition + ")";
            return this;
        }

        public String getCondition() {
            return this.condition;
        }
    }
    protected enum ColumnType {
        INTEGER("INTEGER"),
        BIGINT("BIGINT"),
        NVARCHAR50("NVARCHAR(50)"),
        NVARCHAR100("NVARCHAR(100)"),
        NVARCHAR255("NVARCHAR(255)"),
        NVARCHAR500("NVARCHAR(500)"),
        NVARCHAR1000("NVARCHAR(1000)"),
        NVARCHAR1500("NVARCHAR(1500)"),
        DATE("DATE"),
        TIME("TIME"),
        DATETIME("DATETIME");

        private String type_name;
        ColumnType(String type_name) {
            this.type_name = type_name;
        }
        public String getValue() {
            return this.type_name;
        }
    }
    protected enum TableAttribute {
        PRIMARYKEY("PRIMARY KEY"),
        UNIQUE("UNIQUE"),
        AUTOINCREMENT("AUTOINCREMENT"),
        NOTNULL("NOT NULL");

        private String attribute_name;
        TableAttribute(String attribute_name) {
            this.attribute_name = attribute_name;
        }
        public String getValue() {
            return this.attribute_name;
        }
    }

    protected void setupVariables() {
        COL_NAME_ID = "Id";
        COLUMN_NAMES =
                Utilities.concatStringArrays(getDefaultColumnNames(), getColumnNames());
        COLUMN_TYPES =
                Utilities.concatHashMaps(getDefaultColumnTypes(), getColumnTypes());
        COLUMN_ATTRIBUTES =
                Utilities.concatHashMaps(getDefaultColumnAttributes(), getColumnAttributes());
        FOREIGN_KEYS =
                Utilities.concatHashMaps(getDefaultForeignKeys(), getForeignKeys());
        UNIQUE_CONSTRAINTS =
                Utilities.concatHashMaps(getDefaultUniqueConstraints(), getUniqueConstraints());
    }

    public String TABLE_NAME;
    public String COL_NAME_ID;
    protected String[] COLUMN_NAMES;
    protected HashMap<String, ColumnType> COLUMN_TYPES;
    protected HashMap<String, TableAttribute[]> COLUMN_ATTRIBUTES;
    protected HashMap<String, String> FOREIGN_KEYS;
    protected HashMap<String, String[]> UNIQUE_CONSTRAINTS;

    public String createTableQuery() {
        String query = "CREATE TABLE '" + TABLE_NAME + "'(",
                foreignKeys = "";
        boolean first = true;
        for (String col : COLUMN_NAMES) {
            query += (first ? "" : ",") + "'" + col + "' " + COLUMN_TYPES.get(col).getValue();
            TableAttribute[] attrs = COLUMN_ATTRIBUTES.get(col);
            if (attrs != null)
                for (TableAttribute attr : attrs)
                    query += " " + attr.getValue();
            String targetTable;
            if ((targetTable = FOREIGN_KEYS.get(col)) != null)
                foreignKeys += (foreignKeys.isEmpty() ? "" : ",") + foreignKey(col, targetTable, COL_NAME_ID);
            first = false;
        }
        query += (foreignKeys.isEmpty() ? "" : ("," + foreignKeys)) + ");\n";
        for (HashMap.Entry<String, String[]> constraint : UNIQUE_CONSTRAINTS.entrySet())
            query += uniqueIndex(constraint.getKey(), constraint.getValue()) + "\n";
        return query;
    }

    protected String getDefaultParam(String paramName) {
        if (paramName == COL_NAME_ID)
            return id.toString();
        return null;
    }
    protected String[] getDefaultColumnNames() {
        return new String[] { COL_NAME_ID };
    }
    protected HashMap<String, ColumnType> getDefaultColumnTypes() {
        HashMap<String, ColumnType> types = new HashMap<>();
        types.put(COL_NAME_ID, ColumnType.INTEGER);
        return types;
    }
    protected HashMap<String, TableAttribute[]> getDefaultColumnAttributes() {
        HashMap<String, TableAttribute[]> columns = new HashMap<>();
        columns.put(COL_NAME_ID, new TableAttribute[] { TableAttribute.NOTNULL,
                TableAttribute.PRIMARYKEY, TableAttribute.AUTOINCREMENT });
        return columns;
    }
    protected HashMap<String, String> getDefaultForeignKeys() {
        return new HashMap<>();
    }
    protected HashMap<String, String[]> getDefaultUniqueConstraints() {
        return new HashMap<>();
    }
    // Subclasses need to implement these if they've got these
    protected String getParam(String paramName) {
        return null;
    }
    protected String[] getColumnNames() {
        return new String[] {};
    }
    protected HashMap<String, ColumnType> getColumnTypes() {
        return new HashMap<>();
    }
    protected HashMap<String, TableAttribute[]> getColumnAttributes() {
        return new HashMap<>();
    }
    protected HashMap<String, String> getForeignKeys() {
        return new HashMap<>();
    }
    protected HashMap<String, String[]> getUniqueConstraints() {
        return new HashMap<>();
    }
    // Helpful methods for createTableScript
    private String getUniqueIndices() {
        String ret = "";
        for (HashMap.Entry<String, String[]> entry : UNIQUE_CONSTRAINTS.entrySet())
            ret += uniqueIndex(entry.getKey(), entry.getValue()) + "\n";
        return ret;
    }
    private String foreignKey(String key, String target_table, String target_column) {
        return "FOREIGN KEY('" + key + "') REFERENCES '" + target_table + "'('" + target_column + "')";
    }
    private String uniqueIndex(String constraint_name, String[] columns) {
        String ret = "CREATE UNIQUE INDEX '" + constraint_name + "' ON '" + TABLE_NAME + "'(";
        boolean first = true;
        for (String column : columns) {
            ret += (first ? "" : ",") + "'" + column + "'";
            first = false;
        }
        return ret + ");";
    }

    // Methods to work with rows.
    public String insertQuery() {
        String query = "INSERT INTO '" + TABLE_NAME + "'(",
            vals = " VALUES(";
        boolean first = true;
        for (String value : COLUMN_NAMES) {
            if (value == COL_NAME_ID) continue;
            query += (first ? "" : ",") + "'" + value + "'";
            vals += (first ? "" : ",") + "'" + Utilities.logicalOr(getDefaultParam(value),
                    getParam(value)) + "'";
            first = false;
        }
        return query + ")" + vals + ");";
    }
    public String updateQuery() {
        String query = "UPDATE '" + TABLE_NAME + "' SET ",
                condition = " WHERE " + COL_NAME_ID + "='" +
                        Utilities.logicalOr(getDefaultParam(COL_NAME_ID), getParam(COL_NAME_ID)) + "';";
        boolean first = true;
        for (String value : COLUMN_NAMES) {
            if (value == COL_NAME_ID) continue;
            query += (first ? "" : ",") + value + "=" +
                    "'" + Utilities.logicalOr(getDefaultParam(value), getParam(value)) + "'";
            first = false;
        }
        return query + condition;
    }
    public String deleteQuery() {
        return "DELETE FROM '" + TABLE_NAME + "' WHERE " + COL_NAME_ID + "=" + "'" +
                Utilities.logicalOr(getDefaultParam(COL_NAME_ID), getParam(COL_NAME_ID)) + "';";
    }
    public String selectQuery(QueryCondition condition) {
        return "SELECT * FROM '" + TABLE_NAME + "' WHERE " + condition.getCondition() + ";";
    }

    protected Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
