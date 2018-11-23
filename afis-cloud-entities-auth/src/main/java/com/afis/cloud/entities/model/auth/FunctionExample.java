package com.afis.cloud.entities.model.auth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FunctionExample {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    private LinkedHashMap<String, String> orderByClauseMap;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    private String orderByClause;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    protected String alias;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample() {
        oredCriteria = new ArrayList<Criteria>();
        orderByClauseMap = new LinkedHashMap<String, String>();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample(String alias) {
        this();
        this.alias = alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     * @param example Another example will be copied to this.
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    protected FunctionExample(FunctionExample example) {
        this.orderByClauseMap = new LinkedHashMap<String, String>(example.orderByClauseMap);
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     * @param example Another example will be copied to this.
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    protected FunctionExample(FunctionExample example, String alias) {
        this(example);
        this.alias = alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample orderById(String direction) {
        return this.orderBy("ID", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public String removeOrderById() {
        return this.removeOrderBy("ID");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample orderByUrl(String direction) {
        return this.orderBy("URL", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public String removeOrderByUrl() {
        return this.removeOrderBy("URL");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample orderByName(String direction) {
        return this.orderBy("NAME", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public String removeOrderByName() {
        return this.removeOrderBy("NAME");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample orderByType(String direction) {
        return this.orderBy("TYPE", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public String removeOrderByType() {
        return this.removeOrderBy("TYPE");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample orderByRemark(String direction) {
        return this.orderBy("REMARK", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public String removeOrderByRemark() {
        return this.removeOrderBy("REMARK");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample orderByOrderNo(String direction) {
        return this.orderBy("ORDER_NO", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public String removeOrderByOrderNo() {
        return this.removeOrderBy("ORDER_NO");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample orderByGroupName(String direction) {
        return this.orderBy("GROUP_NAME", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public String removeOrderByGroupName() {
        return this.removeOrderBy("GROUP_NAME");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public FunctionExample orderBy(String field, String direction) {
        boolean have = orderByClauseMap.containsKey(field);
        String old = orderByClauseMap.put(field, direction);
        if(have && (old == null ? direction != null : !old.equals(direction))) orderByClause = null;
        return this;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
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
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
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
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
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
     * <br>This method corresponds to the database table T_AI_FUNCTION
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public void setAlias(String alias) {
        this.alias = alias;
        orderByClause = null;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public String getAlias() {
        return alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if(oredCriteria.size() == 0) oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    protected Criteria createCriteriaInternal() {
        return new Criteria();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
     * @author sacool ibatis builder Team
     */
    public void clearOredCriteria() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by www.sacool.com iBATIS builder.
     * <br>This class corresponds to the database table T_AI_FUNCTION
     *
     * @sacoolbuildergenerated 2018-11-06 14:38:52
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

        public Criteria andUrlIsNull() {
            addCriterion("URL IS NULL");
            return this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("URL IS NOT NULL");
            return this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("URL =", value, "url");
            return this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("URL <>", value, "url");
            return this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("URL >", value, "url");
            return this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("URL >=", value, "url");
            return this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("URL <", value, "url");
            return this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("URL <=", value, "url");
            return this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("URL LIKE", value, "url");
            return this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("URL NOT LIKE", value, "url");
            return this;
        }

        public Criteria andUrlIn(String[] values) {
            addCriterion("URL IN", values, "url");
            return this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("URL IN", values, "url");
            return this;
        }

        public Criteria andUrlNotIn(String[] values) {
            addCriterion("URL NOT IN", values, "url");
            return this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("URL NOT IN", values, "url");
            return this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("URL BETWEEN", value1, value2, "url");
            return this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("URL NOT BETWEEN", value1, value2, "url");
            return this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME IS NULL");
            return this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME IS NOT NULL");
            return this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME LIKE", value, "name");
            return this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME NOT LIKE", value, "name");
            return this;
        }

        public Criteria andNameIn(String[] values) {
            addCriterion("NAME IN", values, "name");
            return this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME IN", values, "name");
            return this;
        }

        public Criteria andNameNotIn(String[] values) {
            addCriterion("NAME NOT IN", values, "name");
            return this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME NOT IN", values, "name");
            return this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME BETWEEN", value1, value2, "name");
            return this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME NOT BETWEEN", value1, value2, "name");
            return this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE IS NULL");
            return this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE IS NOT NULL");
            return this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("TYPE =", value, "type");
            return this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("TYPE <>", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("TYPE >", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE >=", value, "type");
            return this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("TYPE <", value, "type");
            return this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("TYPE <=", value, "type");
            return this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("TYPE LIKE", value, "type");
            return this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("TYPE NOT LIKE", value, "type");
            return this;
        }

        public Criteria andTypeIn(String[] values) {
            addCriterion("TYPE IN", values, "type");
            return this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("TYPE IN", values, "type");
            return this;
        }

        public Criteria andTypeNotIn(String[] values) {
            addCriterion("TYPE NOT IN", values, "type");
            return this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("TYPE NOT IN", values, "type");
            return this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("TYPE BETWEEN", value1, value2, "type");
            return this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("TYPE NOT BETWEEN", value1, value2, "type");
            return this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK IS NULL");
            return this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK IS NOT NULL");
            return this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK LIKE", value, "remark");
            return this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK NOT LIKE", value, "remark");
            return this;
        }

        public Criteria andRemarkIn(String[] values) {
            addCriterion("REMARK IN", values, "remark");
            return this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK IN", values, "remark");
            return this;
        }

        public Criteria andRemarkNotIn(String[] values) {
            addCriterion("REMARK NOT IN", values, "remark");
            return this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK NOT IN", values, "remark");
            return this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK BETWEEN", value1, value2, "remark");
            return this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK NOT BETWEEN", value1, value2, "remark");
            return this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("ORDER_NO IS NULL");
            return this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("ORDER_NO IS NOT NULL");
            return this;
        }

        public Criteria andOrderNoEqualTo(long value) {
            addCriterion("ORDER_NO =", new Long(value), "orderNo");
            return this;
        }

        public Criteria andOrderNoNotEqualTo(long value) {
            addCriterion("ORDER_NO <>", new Long(value), "orderNo");
            return this;
        }

        public Criteria andOrderNoGreaterThan(long value) {
            addCriterion("ORDER_NO >", new Long(value), "orderNo");
            return this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(long value) {
            addCriterion("ORDER_NO >=", new Long(value), "orderNo");
            return this;
        }

        public Criteria andOrderNoLessThan(long value) {
            addCriterion("ORDER_NO <", new Long(value), "orderNo");
            return this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(long value) {
            addCriterion("ORDER_NO <=", new Long(value), "orderNo");
            return this;
        }

        public Criteria andOrderNoIn(long[] values) {
            addCriterion("ORDER_NO IN", values, "orderNo");
            return this;
        }

        public Criteria andOrderNoIn(List<Long> values) {
            addCriterion("ORDER_NO IN", values, "orderNo");
            return this;
        }

        public Criteria andOrderNoNotIn(long[] values) {
            addCriterion("ORDER_NO NOT IN", values, "orderNo");
            return this;
        }

        public Criteria andOrderNoNotIn(List<Long> values) {
            addCriterion("ORDER_NO NOT IN", values, "orderNo");
            return this;
        }

        public Criteria andOrderNoBetween(long value1, long value2) {
            addCriterion("ORDER_NO BETWEEN", new Long(value1), new Long(value2), "orderNo");
            return this;
        }

        public Criteria andOrderNoNotBetween(long value1, long value2) {
            addCriterion("ORDER_NO NOT BETWEEN", new Long(value1), new Long(value2), "orderNo");
            return this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("GROUP_NAME IS NULL");
            return this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("GROUP_NAME IS NOT NULL");
            return this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("GROUP_NAME =", value, "groupName");
            return this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("GROUP_NAME <>", value, "groupName");
            return this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("GROUP_NAME >", value, "groupName");
            return this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_NAME >=", value, "groupName");
            return this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("GROUP_NAME <", value, "groupName");
            return this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("GROUP_NAME <=", value, "groupName");
            return this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("GROUP_NAME LIKE", value, "groupName");
            return this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("GROUP_NAME NOT LIKE", value, "groupName");
            return this;
        }

        public Criteria andGroupNameIn(String[] values) {
            addCriterion("GROUP_NAME IN", values, "groupName");
            return this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("GROUP_NAME IN", values, "groupName");
            return this;
        }

        public Criteria andGroupNameNotIn(String[] values) {
            addCriterion("GROUP_NAME NOT IN", values, "groupName");
            return this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("GROUP_NAME NOT IN", values, "groupName");
            return this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("GROUP_NAME BETWEEN", value1, value2, "groupName");
            return this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("GROUP_NAME NOT BETWEEN", value1, value2, "groupName");
            return this;
        }
    }
}