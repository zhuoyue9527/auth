package com.afis.cloud.entities.model.auth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TableConfigExample {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private LinkedHashMap<String, String> orderByClauseMap;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String orderByClause;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected String alias;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public TableConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
        orderByClauseMap = new LinkedHashMap<String, String>();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public TableConfigExample(String alias) {
        this();
        this.alias = alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     * @param example Another example will be copied to this.
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected TableConfigExample(TableConfigExample example) {
        this.orderByClauseMap = new LinkedHashMap<String, String>(example.orderByClauseMap);
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     * @param example Another example will be copied to this.
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected TableConfigExample(TableConfigExample example, String alias) {
        this(example);
        this.alias = alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public TableConfigExample orderById(String direction) {
        return this.orderBy("ID", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String removeOrderById() {
        return this.removeOrderBy("ID");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public TableConfigExample orderByAppId(String direction) {
        return this.orderBy("APP_ID", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String removeOrderByAppId() {
        return this.removeOrderBy("APP_ID");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public TableConfigExample orderByOperator(String direction) {
        return this.orderBy("OPERATOR", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String removeOrderByOperator() {
        return this.removeOrderBy("OPERATOR");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public TableConfigExample orderByObjId(String direction) {
        return this.orderBy("OBJ_ID", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String removeOrderByObjId() {
        return this.removeOrderBy("OBJ_ID");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public TableConfigExample orderByColumns(String direction) {
        return this.orderBy("COLUMNS", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String removeOrderByColumns() {
        return this.removeOrderBy("COLUMNS");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public TableConfigExample orderBy(String field, String direction) {
        boolean have = orderByClauseMap.containsKey(field);
        String old = orderByClauseMap.put(field, direction);
        if(have && (old == null ? direction != null : !old.equals(direction))) orderByClause = null;
        return this;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String removeOrderBy(String field) {
        if(orderByClauseMap.containsKey(field)){
            orderByClause = null;
            return orderByClauseMap.remove(field);
        }
        return null;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void clearOrderByClause() {
        if(!orderByClauseMap.isEmpty()){
            orderByClause = null;
            orderByClauseMap.clear();
        }
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getOrderByClause() {
        if(orderByClause == null){
            StringBuilder sb = new StringBuilder();
            Iterator<Entry<String, String>> iterator = orderByClauseMap.entrySet().iterator();
            boolean aliasEmpty = this.alias == null || this.alias.isEmpty();
            while(iterator.hasNext()){
                Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                boolean keyEmpty = key == null || key.isEmpty();
                boolean valueEmpty = value == null || value.isEmpty();
                if(!keyEmpty){
                    if(!aliasEmpty){
                        sb.append(this.alias);
                        sb.append(".");
                    }
                    sb.append(entry.getKey());
                }
                if(!valueEmpty){
                    if(!keyEmpty) sb.append(" ");
                    sb.append(entry.getValue());
                }
                if(!keyEmpty || !valueEmpty) sb.append(", ");
            }
            if(sb.length() != 0) sb.setLength(sb.length() - 2);
            orderByClause = sb.toString();
        }
        return orderByClause;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setAlias(String alias) {
        this.alias = alias;
        orderByClause = null;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getAlias() {
        return alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if(oredCriteria.size() == 0) oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected Criteria createCriteriaInternal() {
        return new Criteria();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void clearOredCriteria() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by www.sacool.com iBATIS builder.
     * <br>This class corresponds to the database table T_A_TABLE_CONFIG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public static class Criteria {
        protected List<String> criteriaWithSqlSnippetValue;

        protected List<String> criteriaWithoutValue;

        protected List<Map<String, Object>> criteriaWithSingleValue;

        protected List<Map<String, Object>> criteriaWithListValue;

        protected List<Map<String, Object>> criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithSqlSnippetValue = new ArrayList<String>();
            criteriaWithoutValue = new ArrayList<String>();
            criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
            criteriaWithListValue = new ArrayList<Map<String, Object>>();
            criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
        }

        public boolean isValid() {
            return criteriaWithSqlSnippetValue.size() > 0
                || criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List<String> getCriteriaWithSqlSnippetValue() {
            return criteriaWithSqlSnippetValue;
        }

        public List<String> getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List<Map<String, Object>> getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List<Map<String, Object>> getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List<Map<String, Object>> getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            List<Map<String, Object>> list = null;
            String valueKey = "value";
            if(value.getClass().isArray()) {
                if(Array.getLength(value) == 0) throw new RuntimeException("Value array for " + property + " cannot be empty");
                list = criteriaWithListValue;
                valueKey = "values";
            } else list = criteriaWithSingleValue;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put(valueKey, value);
            list.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andSQLSnippet(String sqlSnippet) {
            if(sqlSnippet == null){
                throw new RuntimeException("Value for sqlSnippet cannot be null");
            }
            criteriaWithSqlSnippetValue.add(sqlSnippet);
            return this;
        }

        public Criteria andIdIsNull() {
            addCriterion("ID IS NULL");
            return this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID IS NOT NULL");
            return this;
        }

        public Criteria andIdEqualTo(long value) {
            addCriterion("ID =", new Long(value), "id");
            return this;
        }

        public Criteria andIdNotEqualTo(long value) {
            addCriterion("ID <>", new Long(value), "id");
            return this;
        }

        public Criteria andIdGreaterThan(long value) {
            addCriterion("ID >", new Long(value), "id");
            return this;
        }

        public Criteria andIdGreaterThanOrEqualTo(long value) {
            addCriterion("ID >=", new Long(value), "id");
            return this;
        }

        public Criteria andIdLessThan(long value) {
            addCriterion("ID <", new Long(value), "id");
            return this;
        }

        public Criteria andIdLessThanOrEqualTo(long value) {
            addCriterion("ID <=", new Long(value), "id");
            return this;
        }

        public Criteria andIdIn(long[] values) {
            addCriterion("ID IN", values, "id");
            return this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID IN", values, "id");
            return this;
        }

        public Criteria andIdNotIn(long[] values) {
            addCriterion("ID NOT IN", values, "id");
            return this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID NOT IN", values, "id");
            return this;
        }

        public Criteria andIdBetween(long value1, long value2) {
            addCriterion("ID BETWEEN", new Long(value1), new Long(value2), "id");
            return this;
        }

        public Criteria andIdNotBetween(long value1, long value2) {
            addCriterion("ID NOT BETWEEN", new Long(value1), new Long(value2), "id");
            return this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("APP_ID IS NULL");
            return this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("APP_ID IS NOT NULL");
            return this;
        }

        public Criteria andAppIdEqualTo(long value) {
            addCriterion("APP_ID =", new Long(value), "appId");
            return this;
        }

        public Criteria andAppIdNotEqualTo(long value) {
            addCriterion("APP_ID <>", new Long(value), "appId");
            return this;
        }

        public Criteria andAppIdGreaterThan(long value) {
            addCriterion("APP_ID >", new Long(value), "appId");
            return this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(long value) {
            addCriterion("APP_ID >=", new Long(value), "appId");
            return this;
        }

        public Criteria andAppIdLessThan(long value) {
            addCriterion("APP_ID <", new Long(value), "appId");
            return this;
        }

        public Criteria andAppIdLessThanOrEqualTo(long value) {
            addCriterion("APP_ID <=", new Long(value), "appId");
            return this;
        }

        public Criteria andAppIdIn(long[] values) {
            addCriterion("APP_ID IN", values, "appId");
            return this;
        }

        public Criteria andAppIdIn(List<Long> values) {
            addCriterion("APP_ID IN", values, "appId");
            return this;
        }

        public Criteria andAppIdNotIn(long[] values) {
            addCriterion("APP_ID NOT IN", values, "appId");
            return this;
        }

        public Criteria andAppIdNotIn(List<Long> values) {
            addCriterion("APP_ID NOT IN", values, "appId");
            return this;
        }

        public Criteria andAppIdBetween(long value1, long value2) {
            addCriterion("APP_ID BETWEEN", new Long(value1), new Long(value2), "appId");
            return this;
        }

        public Criteria andAppIdNotBetween(long value1, long value2) {
            addCriterion("APP_ID NOT BETWEEN", new Long(value1), new Long(value2), "appId");
            return this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("OPERATOR IS NULL");
            return this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("OPERATOR IS NOT NULL");
            return this;
        }

        public Criteria andOperatorEqualTo(long value) {
            addCriterion("OPERATOR =", new Long(value), "operator");
            return this;
        }

        public Criteria andOperatorNotEqualTo(long value) {
            addCriterion("OPERATOR <>", new Long(value), "operator");
            return this;
        }

        public Criteria andOperatorGreaterThan(long value) {
            addCriterion("OPERATOR >", new Long(value), "operator");
            return this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(long value) {
            addCriterion("OPERATOR >=", new Long(value), "operator");
            return this;
        }

        public Criteria andOperatorLessThan(long value) {
            addCriterion("OPERATOR <", new Long(value), "operator");
            return this;
        }

        public Criteria andOperatorLessThanOrEqualTo(long value) {
            addCriterion("OPERATOR <=", new Long(value), "operator");
            return this;
        }

        public Criteria andOperatorIn(long[] values) {
            addCriterion("OPERATOR IN", values, "operator");
            return this;
        }

        public Criteria andOperatorIn(List<Long> values) {
            addCriterion("OPERATOR IN", values, "operator");
            return this;
        }

        public Criteria andOperatorNotIn(long[] values) {
            addCriterion("OPERATOR NOT IN", values, "operator");
            return this;
        }

        public Criteria andOperatorNotIn(List<Long> values) {
            addCriterion("OPERATOR NOT IN", values, "operator");
            return this;
        }

        public Criteria andOperatorBetween(long value1, long value2) {
            addCriterion("OPERATOR BETWEEN", new Long(value1), new Long(value2), "operator");
            return this;
        }

        public Criteria andOperatorNotBetween(long value1, long value2) {
            addCriterion("OPERATOR NOT BETWEEN", new Long(value1), new Long(value2), "operator");
            return this;
        }

        public Criteria andObjIdIsNull() {
            addCriterion("OBJ_ID IS NULL");
            return this;
        }

        public Criteria andObjIdIsNotNull() {
            addCriterion("OBJ_ID IS NOT NULL");
            return this;
        }

        public Criteria andObjIdEqualTo(String value) {
            addCriterion("OBJ_ID =", value, "objId");
            return this;
        }

        public Criteria andObjIdNotEqualTo(String value) {
            addCriterion("OBJ_ID <>", value, "objId");
            return this;
        }

        public Criteria andObjIdGreaterThan(String value) {
            addCriterion("OBJ_ID >", value, "objId");
            return this;
        }

        public Criteria andObjIdGreaterThanOrEqualTo(String value) {
            addCriterion("OBJ_ID >=", value, "objId");
            return this;
        }

        public Criteria andObjIdLessThan(String value) {
            addCriterion("OBJ_ID <", value, "objId");
            return this;
        }

        public Criteria andObjIdLessThanOrEqualTo(String value) {
            addCriterion("OBJ_ID <=", value, "objId");
            return this;
        }

        public Criteria andObjIdLike(String value) {
            addCriterion("OBJ_ID LIKE", value, "objId");
            return this;
        }

        public Criteria andObjIdNotLike(String value) {
            addCriterion("OBJ_ID NOT LIKE", value, "objId");
            return this;
        }

        public Criteria andObjIdIn(String[] values) {
            addCriterion("OBJ_ID IN", values, "objId");
            return this;
        }

        public Criteria andObjIdIn(List<String> values) {
            addCriterion("OBJ_ID IN", values, "objId");
            return this;
        }

        public Criteria andObjIdNotIn(String[] values) {
            addCriterion("OBJ_ID NOT IN", values, "objId");
            return this;
        }

        public Criteria andObjIdNotIn(List<String> values) {
            addCriterion("OBJ_ID NOT IN", values, "objId");
            return this;
        }

        public Criteria andObjIdBetween(String value1, String value2) {
            addCriterion("OBJ_ID BETWEEN", value1, value2, "objId");
            return this;
        }

        public Criteria andObjIdNotBetween(String value1, String value2) {
            addCriterion("OBJ_ID NOT BETWEEN", value1, value2, "objId");
            return this;
        }

        public Criteria andColumnsIsNull() {
            addCriterion("COLUMNS IS NULL");
            return this;
        }

        public Criteria andColumnsIsNotNull() {
            addCriterion("COLUMNS IS NOT NULL");
            return this;
        }

        public Criteria andColumnsEqualTo(String value) {
            addCriterion("COLUMNS =", value, "columns");
            return this;
        }

        public Criteria andColumnsNotEqualTo(String value) {
            addCriterion("COLUMNS <>", value, "columns");
            return this;
        }

        public Criteria andColumnsGreaterThan(String value) {
            addCriterion("COLUMNS >", value, "columns");
            return this;
        }

        public Criteria andColumnsGreaterThanOrEqualTo(String value) {
            addCriterion("COLUMNS >=", value, "columns");
            return this;
        }

        public Criteria andColumnsLessThan(String value) {
            addCriterion("COLUMNS <", value, "columns");
            return this;
        }

        public Criteria andColumnsLessThanOrEqualTo(String value) {
            addCriterion("COLUMNS <=", value, "columns");
            return this;
        }

        public Criteria andColumnsLike(String value) {
            addCriterion("COLUMNS LIKE", value, "columns");
            return this;
        }

        public Criteria andColumnsNotLike(String value) {
            addCriterion("COLUMNS NOT LIKE", value, "columns");
            return this;
        }

        public Criteria andColumnsIn(String[] values) {
            addCriterion("COLUMNS IN", values, "columns");
            return this;
        }

        public Criteria andColumnsIn(List<String> values) {
            addCriterion("COLUMNS IN", values, "columns");
            return this;
        }

        public Criteria andColumnsNotIn(String[] values) {
            addCriterion("COLUMNS NOT IN", values, "columns");
            return this;
        }

        public Criteria andColumnsNotIn(List<String> values) {
            addCriterion("COLUMNS NOT IN", values, "columns");
            return this;
        }

        public Criteria andColumnsBetween(String value1, String value2) {
            addCriterion("COLUMNS BETWEEN", value1, value2, "columns");
            return this;
        }

        public Criteria andColumnsNotBetween(String value1, String value2) {
            addCriterion("COLUMNS NOT BETWEEN", value1, value2, "columns");
            return this;
        }
    }
}