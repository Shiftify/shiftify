package cz.cvut.fit.shiftify.data;

import android.app.ActionBar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by lukas on 07.12.2016.
 */

public abstract class DbTable {
    public static <T> T getField(Class classVal, String fieldName) {
        java.lang.reflect.Field field;
        try {
            field = classVal.getDeclaredField(fieldName);
            Object t = null;
            return (T) field.get(t);
        } catch (Exception ex) {
            try {
                field = DbTable.class.getDeclaredField(fieldName);
                Object t = null;
                return (T) field.get(t);
            } catch (Exception ex2) {
                return null;
            }
        }
    }
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

    public static final String STRING_TABLE_NAME = "TABLE_NAME";
    public static final String STRING_COL_NAME_ID = "COL_NAME_ID";
    public static final String STRING_COL_NAME_INSERTEDON = "COL_NAME_INSERTEDON";
    public static final String STRING_COLUMN_NAMES = "COLUMN_NAMES";
    public static final String STRING_COLUMN_TYPES = "COLUMN_TYPES";
    public static final String STRING_COLUMN_ATTRIBUTES = "COLUMN_ATTRIBUTES";
    public static final String STRING_FOREIGN_KEYS = "FOREIGN_KEYS";
    public static final String STRING_UNIQUE_CONSTRAINTS = "UNIQUE_CONSTRAINTS";

    public static final String TABLE_NAME = null;
    protected static final String[] COLUMN_NAMES =
            Utilities.concatStrArrays(getDefaultColumnNames(), getColumnNames());;
    protected static final HashMap<String, ColumnType> COLUMN_TYPES =
            Utilities.concatHashMaps(getDefaultColumnTypes(), getColumnTypes());
    protected static final HashMap<String, TableAttribute[]> COLUMN_ATTRIBUTES =
            Utilities.concatHashMaps(getDefaultColumnAttributes(), getColumnAttributes());
    protected static final HashMap<String, String> FOREIGN_KEYS =
            Utilities.concatHashMaps(getDefaultForeignKeys(), getForeignKeys());
    protected static final HashMap<String, String[]> UNIQUE_CONSTRAINTS =
            Utilities.concatHashMaps(getDefaultUniqueConstraints(), getUniqueConstraints());

    public static final String COL_NAME_ID = "Id";
    public static final String COL_NAME_INSERTEDON = "InsertedOn";

    public static String createTableQuery(Class cl) {
        String tableName = getField(cl, STRING_TABLE_NAME);
        String[] columnNames = getField(cl, STRING_COLUMN_NAMES);
        HashMap<String, ColumnType> columnTypes = getField(cl, STRING_COLUMN_TYPES);
        HashMap<String, TableAttribute[]> columnAttributes = getField(cl, STRING_COLUMN_ATTRIBUTES);
        HashMap<String, String> foreignKeys = getField(cl, STRING_FOREIGN_KEYS);
        HashMap<String, String[]> uniqueConstraint = getField(cl, STRING_UNIQUE_CONSTRAINTS);

        String query = "CREATE TABLE '" + tableName + "'(",
                strForeignKeys = "";
        boolean first = true;
        for (String col : columnNames) {
            query += (first ? "" : ",") + "'" + col + "' " + columnTypes.get(col).getValue();
            TableAttribute[] attrs = columnAttributes.get(col);
            if (attrs != null)
                for (TableAttribute attr : attrs)
                    query += " " + attr.getValue();
            String targetTable;
            if ((targetTable = foreignKeys.get(col)) != null)
                strForeignKeys += (strForeignKeys.isEmpty() ? "" : ",") + foreignKey(col, targetTable, COL_NAME_ID);
            first = false;
        }
        query += (strForeignKeys.isEmpty() ? "" : ("," + strForeignKeys)) + ");\n" +
                getUniqueIndices(tableName, uniqueConstraint);
        return query;
    }

    protected String getDefaultParam(String paramName) {
        if (paramName.equals(COL_NAME_ID))
            return id.toString();
        if (paramName.equals(COL_NAME_INSERTEDON))
            return Long.toString(insertedOn.getTimeInMillis());
        return null;
    }
    protected static String[] getDefaultColumnNames() {
        return new String[] { COL_NAME_ID, COL_NAME_INSERTEDON };
    }
    protected static HashMap<String, ColumnType> getDefaultColumnTypes() {
        HashMap<String, ColumnType> types = new HashMap<>();
        types.put(COL_NAME_ID, ColumnType.INTEGER);
        types.put(COL_NAME_INSERTEDON, ColumnType.DATETIME);
        return types;
    }
    protected static HashMap<String, TableAttribute[]> getDefaultColumnAttributes() {
        HashMap<String, TableAttribute[]> columns = new HashMap<>();
        columns.put(COL_NAME_ID, new TableAttribute[] { TableAttribute.NOTNULL,
                TableAttribute.PRIMARYKEY, TableAttribute.AUTOINCREMENT });
        columns.put(COL_NAME_INSERTEDON, new TableAttribute[] { TableAttribute.NOTNULL });
        return columns;
    }
    protected static HashMap<String, String> getDefaultForeignKeys() {
        return new HashMap<>();
    }
    protected static HashMap<String, String[]> getDefaultUniqueConstraints() {
        return new HashMap<>();
    }
    // Subclasses need to implement these if they've got these
    protected String getParam(String paramName) {
        return null;
    }
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
    private static String getUniqueIndices(String tableName, HashMap<String, String[]> uniqueConstraints) {
        String ret = "";
        for (HashMap.Entry<String, String[]> entry : uniqueConstraints.entrySet())
            ret += uniqueIndex(tableName, entry.getKey(), entry.getValue()) + "\n";
        return ret;
    }
    private static String foreignKey(String key, String target_table, String target_column) {
        return "FOREIGN KEY('" + key + "') REFERENCES '" + target_table + "'('" + target_column + "')";
    }
    private static String uniqueIndex(String tableName, String constraint_name, String[] columns) {
        String ret = "CREATE UNIQUE INDEX '" + constraint_name + "' ON '" + tableName + "'(";
        boolean first = true;
        for (String column : columns) {
            ret += (first ? "" : ",") + "'" + column + "'";
            first = false;
        }
        return ret + ");";
    }

    // Methods to work with rows.
    public String insertQuery() {
        Class thisClass = this.getClass();
        String tableName = getField(thisClass, STRING_TABLE_NAME);
        String[] columnNames = getField(thisClass, STRING_COLUMN_NAMES);

        String query = "INSERT INTO '" + tableName + "'(",
            vals = " VALUES(";
        boolean first = true;
        for (String value : columnNames) {
            if (value.equals(COL_NAME_ID)) continue;
            query += (first ? "" : ",") + "'" + value + "'";
            vals += (first ? "" : ",") + "'" +
                    Utilities.logicalOr(getDefaultParam(value), getParam(value)) + "'";
            first = false;
        }
        return query + ")" + vals + ");";
    }
    public String updateQuery() {
        Class thisClass = this.getClass();
        String tableName = getField(thisClass, STRING_TABLE_NAME);
        String[] columnNames = getField(thisClass, STRING_COLUMN_NAMES);

        String query = "UPDATE '" + tableName + "' SET ",
                condition = " WHERE " + COL_NAME_ID + "='" + getDefaultParam(COL_NAME_ID) + "';";
        boolean first = true;
        for (String value : columnNames) {
            if (value.equals(COL_NAME_ID)) continue;
            query += (first ? "" : ",") + value + "=" +
                    "'" + Utilities.logicalOr(getDefaultParam(value), getParam(value)) + "'";
            first = false;
        }
        return query + condition;
    }
    public String deleteQuery() {
        Class thisClass = this.getClass();
        String tableName = getField(thisClass, STRING_TABLE_NAME);
        String[] columnNames = getField(thisClass, STRING_COLUMN_NAMES);

        return "DELETE FROM '" + tableName + "' WHERE " + COL_NAME_ID + "=" + "'" +
                getDefaultParam(COL_NAME_ID) + "';";
    }
    public static String selectQuery(Class cl, QueryCondition condition) {
        String tableName = getField(cl, STRING_TABLE_NAME);

        return "SELECT * FROM '" + tableName + "' WHERE " + condition.getCondition() + ";";
    }

    public DbTable() {
        insertedOn = (GregorianCalendar) Calendar.getInstance();
    }

    protected Integer id;
    protected GregorianCalendar insertedOn;
    public Integer getId() {
        return id;
    }
    public GregorianCalendar getInsertedOn() { return insertedOn; }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setInsertedOn(GregorianCalendar insertedOn) { this.insertedOn = insertedOn; }
}
