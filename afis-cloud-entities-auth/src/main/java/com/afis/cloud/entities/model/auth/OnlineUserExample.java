package com.afis.cloud.entities.model.auth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OnlineUserExample {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    private LinkedHashMap<String, String> orderByClauseMap;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    private String orderByClause;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    protected String alias;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public OnlineUserExample() {
        oredCriteria = new ArrayList<Criteria>();
        orderByClauseMap = new LinkedHashMap<String, String>();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public OnlineUserExample(String alias) {
        this();
        this.alias = alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     * @param example Another example will be copied to this.
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    protected OnlineUserExample(OnlineUserExample example) {
        this.orderByClauseMap = new LinkedHashMap<String, String>(example.orderByClauseMap);
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     * @param example Another example will be copied to this.
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    protected OnlineUserExample(OnlineUserExample example, String alias) {
        this(example);
        this.alias = alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public OnlineUserExample orderByLoginKey(String direction) {
        return this.orderBy("LOGIN_KEY", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public String removeOrderByLoginKey() {
        return this.removeOrderBy("LOGIN_KEY");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public OnlineUserExample orderByUserId(String direction) {
        return this.orderBy("USER_ID", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public String removeOrderByUserId() {
        return this.removeOrderBy("USER_ID");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public OnlineUserExample orderByLoginTime(String direction) {
        return this.orderBy("LOGIN_TIME", direction);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public String removeOrderByLoginTime() {
        return this.removeOrderBy("LOGIN_TIME");
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public OnlineUserExample orderBy(String field, String direction) {
        boolean have = orderByClauseMap.containsKey(field);
        String old = orderByClauseMap.put(field, direction);
        if(have && (old == null ? direction != null : !old.equals(direction))) orderByClause = null;
        return this;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
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
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
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
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
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
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     * @param alias The dynamic alias of the table.
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public void setAlias(String alias) {
        this.alias = alias;
        orderByClause = null;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public String getAlias() {
        return alias;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if(oredCriteria.size() == 0) oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    protected Criteria createCriteriaInternal() {
        return new Criteria();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public void clearOredCriteria() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by www.sacool.com iBATIS builder.
     * <br>This class corresponds to the database table T_A_ONLINE_USER
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
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

        public Criteria andLoginKeyIsNull() {
            addCriterion("LOGIN_KEY IS NULL");
            return this;
        }

        public Criteria andLoginKeyIsNotNull() {
            addCriterion("LOGIN_KEY IS NOT NULL");
            return this;
        }

        public Criteria andLoginKeyEqualTo(String value) {
            addCriterion("LOGIN_KEY =", value, "loginKey");
            return this;
        }

        public Criteria andLoginKeyNotEqualTo(String value) {
            addCriterion("LOGIN_KEY <>", value, "loginKey");
            return this;
        }

        public Criteria andLoginKeyGreaterThan(String value) {
            addCriterion("LOGIN_KEY >", value, "loginKey");
            return this;
        }

        public Criteria andLoginKeyGreaterThanOrEqualTo(String value) {
            addCriterion("LOGIN_KEY >=", value, "loginKey");
            return this;
        }

        public Criteria andLoginKeyLessThan(String value) {
            addCriterion("LOGIN_KEY <", value, "loginKey");
            return this;
        }

        public Criteria andLoginKeyLessThanOrEqualTo(String value) {
            addCriterion("LOGIN_KEY <=", value, "loginKey");
            return this;
        }

        public Criteria andLoginKeyLike(String value) {
            addCriterion("LOGIN_KEY LIKE", value, "loginKey");
            return this;
        }

        public Criteria andLoginKeyNotLike(String value) {
            addCriterion("LOGIN_KEY NOT LIKE", value, "loginKey");
            return this;
        }

        public Criteria andLoginKeyIn(String[] values) {
            addCriterion("LOGIN_KEY IN", values, "loginKey");
            return this;
        }

        public Criteria andLoginKeyIn(List<String> values) {
            addCriterion("LOGIN_KEY IN", values, "loginKey");
            return this;
        }

        public Criteria andLoginKeyNotIn(String[] values) {
            addCriterion("LOGIN_KEY NOT IN", values, "loginKey");
            return this;
        }

        public Criteria andLoginKeyNotIn(List<String> values) {
            addCriterion("LOGIN_KEY NOT IN", values, "loginKey");
            return this;
        }

        public Criteria andLoginKeyBetween(String value1, String value2) {
            addCriterion("LOGIN_KEY BETWEEN", value1, value2, "loginKey");
            return this;
        }

        public Criteria andLoginKeyNotBetween(String value1, String value2) {
            addCriterion("LOGIN_KEY NOT BETWEEN", value1, value2, "loginKey");
            return this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID IS NULL");
            return this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID IS NOT NULL");
            return this;
        }

        public Criteria andUserIdEqualTo(long value) {
            addCriterion("USER_ID =", new Long(value), "userId");
            return this;
        }

        public Criteria andUserIdNotEqualTo(long value) {
            addCriterion("USER_ID <>", new Long(value), "userId");
            return this;
        }

        public Criteria andUserIdGreaterThan(long value) {
            addCriterion("USER_ID >", new Long(value), "userId");
            return this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(long value) {
            addCriterion("USER_ID >=", new Long(value), "userId");
            return this;
        }

        public Criteria andUserIdLessThan(long value) {
            addCriterion("USER_ID <", new Long(value), "userId");
            return this;
        }

        public Criteria andUserIdLessThanOrEqualTo(long value) {
            addCriterion("USER_ID <=", new Long(value), "userId");
            return this;
        }

        public Criteria andUserIdIn(long[] values) {
            addCriterion("USER_ID IN", values, "userId");
            return this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("USER_ID IN", values, "userId");
            return this;
        }

        public Criteria andUserIdNotIn(long[] values) {
            addCriterion("USER_ID NOT IN", values, "userId");
            return this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("USER_ID NOT IN", values, "userId");
            return this;
        }

        public Criteria andUserIdBetween(long value1, long value2) {
            addCriterion("USER_ID BETWEEN", new Long(value1), new Long(value2), "userId");
            return this;
        }

        public Criteria andUserIdNotBetween(long value1, long value2) {
            addCriterion("USER_ID NOT BETWEEN", new Long(value1), new Long(value2), "userId");
            return this;
        }

        public Criteria andLoginTimeIsNull() {
            addCriterion("LOGIN_TIME IS NULL");
            return this;
        }

        public Criteria andLoginTimeIsNotNull() {
            addCriterion("LOGIN_TIME IS NOT NULL");
            return this;
        }

        public Criteria andLoginTimeEqualTo(Date value) {
            addCriterion("LOGIN_TIME =", value, "loginTime");
            return this;
        }

        public Criteria andLoginTimeNotEqualTo(Date value) {
            addCriterion("LOGIN_TIME <>", value, "loginTime");
            return this;
        }

        public Criteria andLoginTimeGreaterThan(Date value) {
            addCriterion("LOGIN_TIME >", value, "loginTime");
            return this;
        }

        public Criteria andLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LOGIN_TIME >=", value, "loginTime");
            return this;
        }

        public Criteria andLoginTimeLessThan(Date value) {
            addCriterion("LOGIN_TIME <", value, "loginTime");
            return this;
        }

        public Criteria andLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("LOGIN_TIME <=", value, "loginTime");
            return this;
        }

        public Criteria andLoginTimeIn(Date[] values) {
            addCriterion("LOGIN_TIME IN", values, "loginTime");
            return this;
        }

        public Criteria andLoginTimeIn(List<Date> values) {
            addCriterion("LOGIN_TIME IN", values, "loginTime");
            return this;
        }

        public Criteria andLoginTimeNotIn(Date[] values) {
            addCriterion("LOGIN_TIME NOT IN", values, "loginTime");
            return this;
        }

        public Criteria andLoginTimeNotIn(List<Date> values) {
            addCriterion("LOGIN_TIME NOT IN", values, "loginTime");
            return this;
        }

        public Criteria andLoginTimeBetween(Date value1, Date value2) {
            addCriterion("LOGIN_TIME BETWEEN", value1, value2, "loginTime");
            return this;
        }

        public Criteria andLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("LOGIN_TIME NOT BETWEEN", value1, value2, "loginTime");
            return this;
        }
    }
}