package cz.cvut.fit.shiftify.data;

import android.app.ActionBar;

import java.util.HashMap;

/**
 * Created by lukas on 07.12.2016.
 */

public abstract class DbTable {
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
        public String getType() {
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
        public String getAttribute() {
            return this.attribute_name;
        }
    }

    public static final String TABLE_NAME = null;
    public static final String COL_NAME_ID = "Id";
    public static final String[] COLUMN_NAMES =
            Utilities.concatStringArrays(getDefaultColumnNames(), getColumnNames());
    public static final HashMap<String, ColumnType> COLUMN_TYPES =
            Utilities.concatHashMaps(getDefaultColumnTypes(), getColumnTypes());
    public static final HashMap<String, TableAttribute[]> COLUMN_ATTRIBUTES =
            Utilities.concatHashMaps(getDefaultColumnAttributes(), getColumnAttributes());
    public static final HashMap<String, String> FOREIGN_KEYS =
            Utilities.concatHashMaps(getDefaultForeignKeys(), getForeignKeys());
    public static final HashMap<String, String[]> UNIQUE_CONSTRAINTS =
            Utilities.concatHashMaps(getDefaultUniqueConstraints(), getUniqueConstraints());

    public static String createTableQuery() {
        String query = "CREATE TABLE '" + TABLE_NAME + "'(",
                foreignKeys = "";
        boolean first = true;
        for (String col : COLUMN_NAMES) {
            query += (first ? "" : ",") + "'" + col + "' " + COLUMN_TYPES.get(col);
            TableAttribute[] attrs = COLUMN_ATTRIBUTES.get(col);
            for (TableAttribute attr : attrs)
                query += " " + attr;
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

    private static String[] getDefaultColumnNames() {
        return new String[] { COL_NAME_ID };
    }
    private static HashMap<String, ColumnType> getDefaultColumnTypes() {
        HashMap<String, ColumnType> types = new HashMap<>();
        types.put(COL_NAME_ID, ColumnType.INTEGER);
        return types;
    }
    private static HashMap<String, TableAttribute[]> getDefaultColumnAttributes() {
        HashMap<String, TableAttribute[]> columns = new HashMap<>();
        columns.put(COL_NAME_ID, new TableAttribute[] { TableAttribute.NOTNULL,
                TableAttribute.PRIMARYKEY, TableAttribute.AUTOINCREMENT });
        return columns;
    }
    private static HashMap<String, String> getDefaultForeignKeys() {
        return new HashMap<>();
    }
    private static HashMap<String, String[]> getDefaultUniqueConstraints() {
        return new HashMap<>();
    }
    // Subclasses need to implement these if they've got these
    protected static String[] getColumnNames() {
        return new String[] {};
    }
    protected static HashMap<String, ColumnType> getColumnTypes() {
        return new HashMap<>();
    }
    protected static HashMap<String, TableAttribute[]> getColumnAttributes() {
        return new HashMap<>();
    }
    protected static HashMap<String, String> getForeignKeys() {
        return new HashMap<>();
    }
    protected static HashMap<String, String[]> getUniqueConstraints() {
        return new HashMap<>();
    }
    // Helpful methods for createTableScript
    private static String getUniqueIndices() {
        String ret = "";
        for (HashMap.Entry<String, String[]> entry : UNIQUE_CONSTRAINTS.entrySet())
            ret += uniqueIndex(entry.getKey(), entry.getValue()) + "\n";
        return ret;
    }
    private static String foreignKey(String key, String target_table, String target_column) {
        return "FOREIGN KEY('" + key + "') REFERENCES '" + target_table + "'('" + target_column + "')";
    }
    private static String uniqueIndex(String constraint_name, String[] columns) {
        String ret = "CREATE UNIQUE INDEX '" + constraint_name + "' ON '" + TABLE_NAME + "'(";
        boolean first = true;
        for (String column : columns) {
            ret += (first ? "" : ",") + "'" + column + "'";
            first = false;
        }
        return ret + ");";
    }

    // Non-static methods to work with rows.
    public String insertQuery(DbTable row, HashMap<String, String> values) {
        String query = "INSERT INTO '" + TABLE_NAME + "'(",
            vals = " VALUES(";
        boolean first = true;
        for (HashMap.Entry<String, String> value : values.entrySet()) {
            if (value.getKey() == COL_NAME_ID) continue;
            query += (first ? "" : ",") + "'" +  value.getKey() + "'";
            vals += (first ? "" : ",") + "'" +  value.getValue() + "'";
            first = false;
        }
        return query + ")" + vals + ");";
    }
    // TODO: implement update and delete row!!
    // public String updateQuery(DbTable row)

    protected Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
