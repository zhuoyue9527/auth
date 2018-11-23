package com.afis.cloud.auth.business.store;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.afis.cloud.auth.model.protocol.AdminApplicationResponse;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.exception.CommonException;

public interface ApplicationManagements {

    public static final String SQLMAP_NAMESPACE = ApplicationManagements.class.getName();


    PageResult<AdminApplicationResponse> getApplications(Map<String, Object> map, int start, int limit) throws SQLException, CommonException;

    List<AdminApplicationResponse> getApplicationByParam(Map<String, Object> map) throws SQLException, CommonException;

    List<Application> getApplicationByUserId(Long userId) throws SQLException, CommonException;
}
