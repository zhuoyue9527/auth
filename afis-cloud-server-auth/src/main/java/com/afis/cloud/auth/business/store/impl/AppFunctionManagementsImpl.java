package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;

import com.afis.cloud.auth.business.store.AppFunctionManagements;
import com.afis.cloud.auth.config.KafkaConfig;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.protocol.AdminApplicationRequest;
import com.afis.cloud.auth.model.protocol.AdminApplicationResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.dao.auth.AppFunctionDAO;
import com.afis.cloud.entities.dao.auth.ApplicationDAO;
import com.afis.cloud.entities.dao.auth.FunctionDAO;
import com.afis.cloud.entities.dao.impl.auth.AppFunctionDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.ApplicationDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.FunctionDAOImpl;
import com.afis.cloud.entities.model.auth.AppFunction;
import com.afis.cloud.entities.model.auth.AppFunctionExample;
import com.afis.cloud.entities.model.auth.AppFunctionSelective;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.entities.model.auth.ApplicationExample;
import com.afis.cloud.entities.model.auth.ApplicationSelective;
import com.afis.cloud.entities.model.auth.Function;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.utils.BizHelper;
import com.afis.cloud.utils.SessionUtil;
import com.afis.cloud.utils.ValidateScreenUtil;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/7 11:10
 */
public class AppFunctionManagementsImpl extends AbstractManagementsImpl implements AppFunctionManagements {

	private AuthCacheUtil authCacheUtil;

	private KafkaTemplate kafkaTemplate;

	private String APP_LOGO_PATH = "appLogoPath";

	public AppFunctionManagementsImpl() {
	}

	public AppFunctionManagementsImpl(KafkaTemplate kafkaTemplate, AuthCacheUtil authCacheUtil) {
		this.kafkaTemplate = kafkaTemplate;
		this.authCacheUtil = authCacheUtil;
	}

	private ApplicationDAO applicationDAO = new ApplicationDAOImpl(sqlMapClient);

	private AppFunctionDAO appFunctionDAO = new AppFunctionDAOImpl(sqlMapClient);

	private FunctionDAO functionDAO = new FunctionDAOImpl(sqlMapClient);

	@Override
	public String postAddApplication(String functionUrl, AdminApplicationRequest userParam)
			throws SQLException, CommonException {
		//校验字段长度
		checkParamLength(userParam,functionUrl,userParam.getAppName(), 60,"注册应用", "应用名称输入字段过长");
		checkParamLength(userParam,functionUrl,userParam.getUrlCallback(), 60,"注册应用", "跳转链接输入字段过长");
		checkParamLength(userParam,functionUrl,userParam.getRemark(), 200,"注册应用", "备注输入字段过长");
		// 封装应用数据
		StringBuffer returnFilePath = new StringBuffer("");
		ApplicationSelective applicationSelective = new ApplicationSelective();
		applicationSelective.setAppName(userParam.getAppName());
		applicationSelective.setKey(userParam.getKey());
		applicationSelective.setAppCode(SessionUtil.getRandomUUID());
		applicationSelective.setUrlCallback(userParam.getUrlCallback());
		handlePicture(applicationSelective, returnFilePath, userParam);
		if (userParam.getRemark() != null && !"".equals(userParam.getRemark())) {
			applicationSelective.setRemark(userParam.getRemark());
		}
		applicationSelective.setStatus(CommonConstants.Status.VALID.getKey());
		applicationSelective.setOperateAppId(userParam.getOperateAppId());
		applicationSelective.setOperator(SessionUtil.getOperator(userParam.getUserDetails()));
		applicationSelective.setOperateTime(new Date());
		// 保存应用信息
		Long applicationId = null;
		try {
			applicationId = applicationDAO.insert(applicationSelective);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "功能ID");
		}
		// 更新redis应用信息
		ApplicationModel applicationModel = getApplicationModel(applicationId);
		authCacheUtil.addApplicationToRedis(applicationModel);

		// 校验功能列表
		List<Long> functionList = userParam.getFunctionList();
		if (functionList != null && functionList.size() > 0) {
			for (int i = 0; i < functionList.size(); i++) {
				// 校验功能ID是否存在
				Function function = functionDAO.selectByPrimaryKey(functionList.get(i));
				ValidateScreenUtil.validateObject(function, "功能ID");
				// 管理员接口不能授权
				if (CommonConstants.ClientType.MANAGER.getKey().equals(function.getType())) {
					CommonException exception = new CommonException(CommonException.ANY_DESC);
					exception.setDesc("管理员接口不能授权");
					throw exception;
				}
				// 封装应用功能权限数据
				AppFunctionSelective appFunctionSelective = getAppFunctionSelective(userParam, applicationId, function);
				// 保存应用功能授权信息
				try {
					appFunctionDAO.insert(appFunctionSelective);
				} catch (SQLException e) {
					BizHelper.handleDBException(e, "应用功能");
				}
			}
		}

		// 记录操作日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
						SessionUtil.getOperator(userParam.getUserDetails()), "注册应用" + userParam.getAppName() + "成功"));
		// 封装功能数据返回
		return returnFilePath.toString();
	}
	private void checkParamLength(AdminApplicationRequest userParam,String functionUrl,String param, int i,String even, String desc) throws CommonException, SQLException {
		if (param != null) {
			if (param.getBytes().length > i) {
				// 记录操作日志
				kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
						createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
								SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
								SessionUtil.getOperator(userParam.getUserDetails()), even + userParam.getAppName() + "失败"));
				CommonException e = new CommonException(CommonException.ANY_DESC);
				e.setDesc(desc);
				throw e;
			}
		}
	}

	private AppFunctionSelective getAppFunctionSelective(AdminApplicationRequest userParam, Long applicationId,
			Function function) throws CommonException {
		AppFunctionSelective appFunctionSelective = new AppFunctionSelective();
		appFunctionSelective.setAppId(applicationId);
		appFunctionSelective.setFunctionId(function.getId());
		appFunctionSelective.setOperateAppId(userParam.getOperateAppId());
		appFunctionSelective.setOperator(SessionUtil.getOperator(userParam.getUserDetails()));
		appFunctionSelective.setOperateTime(new Date());
		return appFunctionSelective;
	}

	private ApplicationModel getApplicationModel(Long applicationId) throws SQLException {
		Application applicationToModel = applicationDAO.selectByPrimaryKey(applicationId);
		ApplicationModel applicationModel = new ApplicationModel();
		applicationModel.setId(applicationToModel.getId());
		applicationModel.setAppCode(applicationToModel.getAppCode());
		applicationModel.setAppName(applicationToModel.getAppName());
		applicationModel.setKey(applicationToModel.getKey());
		applicationModel.setLogoPath(applicationToModel.getLogoPath());
		applicationModel.setUrlCallback(applicationToModel.getUrlCallback());
		applicationModel.setStatus(applicationToModel.getStatus());
		applicationModel.setRemark(applicationToModel.getRemark());
		applicationModel.setOperateAppId(applicationToModel.getOperateAppId());
		applicationModel.setOperator(applicationToModel.getOperator());
		applicationModel.setOperateTime(applicationToModel.getOperateTime());
		return applicationModel;
	}

	private void handlePicture(ApplicationSelective applicationSelective, StringBuffer returnFilePath,
			AdminApplicationRequest userParam) {
		if (userParam.getLogoPath() != null && userParam.getLogoPath().trim().length() > 0
				&& !userParam.getLogoPath().startsWith(userParam.getVirtualPath())) {
			returnFilePath = returnFilePath.append(userParam.getVirtualPath()).append(APP_LOGO_PATH);
			// 文件路径
			String filePath = userParam.getLogoPath();
			// 存储名称
			String fileStoreName = filePath.substring(filePath.lastIndexOf("/"));
			returnFilePath = returnFilePath.append(fileStoreName);
			applicationSelective.setLogoPath(returnFilePath.toString());
			SessionUtil.addDebugLog(logger, "保存图片url:{}", returnFilePath.toString());
		}
	}

	@Override
	public String patchEditApplication(String functionUrl, AdminApplicationRequest userParam)
			throws SQLException, CommonException {
		//校验字段长度
		checkParamLength(userParam,functionUrl,userParam.getAppName(), 60, "修改应用","应用名称输入字段过长");
		checkParamLength(userParam,functionUrl,userParam.getUrlCallback(), 60, "修改应用","跳转链接输入字段过长");
		checkParamLength(userParam,functionUrl,userParam.getRemark(), 200, "修改应用","备注输入字段过长");
		// 封装应用数据
		StringBuffer returnFilePath = new StringBuffer("");
		ApplicationSelective applicationSelective = new ApplicationSelective();
		applicationSelective.setId(userParam.getId());
		applicationSelective.setAppName(userParam.getAppName());
		applicationSelective.setKey(userParam.getKey());
		if (userParam.getUrlCallback() != null && !"".equals(userParam.getUrlCallback())) {
			applicationSelective.setUrlCallback(userParam.getUrlCallback());
		}else{
			applicationSelective.setUrlCallback("");
		}
		handlePicture(applicationSelective, returnFilePath, userParam);

		if ( userParam.getRemark() != null && !"".equals(userParam.getRemark())) {
			applicationSelective.setRemark(userParam.getRemark());
		}else{
			applicationSelective.setRemark("");
		}
		applicationSelective.setOperator(SessionUtil.getOperator(userParam.getUserDetails()));
		applicationSelective.setOperateAppId(userParam.getOperateAppId());
		applicationSelective.setOperateTime(new Date());
		// 保存应用信息
		try {
			applicationDAO.updateByPrimaryKey(applicationSelective);
		} catch (SQLException e) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()), "修改应用" + userParam.getAppName() + "失败"));
			BizHelper.handleDBException(e, "应用ID");
		}
		// 更新redis应用信息
		ApplicationModel applicationModel = getApplicationModel(applicationSelective.getId());
		authCacheUtil.addApplicationToRedis(applicationModel);

		// 校验功能ID列表是否有数据
		List<Long> functionList = userParam.getFunctionList();
		if (functionList != null && functionList.size() > 0) {
			// 删除应用授权
			AppFunctionExample example = new AppFunctionExample();
			example.createCriteria().andAppIdEqualTo(userParam.getId());
			appFunctionDAO.deleteByExample(example);
			for (int i = 0; i < functionList.size(); i++) {
				// 校验功能ID是否存在
				Function function = functionDAO.selectByPrimaryKey(functionList.get(i));
				if (function == null) {
					kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
									SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
									SessionUtil.getOperator(userParam.getUserDetails()), "应用授权功能失败,功能ID不存在"));
					CommonException exception = new CommonException(CommonException.NOT_EXISTS);
					exception.setDesc("功能ID");
					throw exception;
				}
				// 只有功能类型为应用账号才能应用授权
				if (CommonConstants.ClientType.MANAGER.getKey().equals(function.getType())) {
					kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
									SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
									SessionUtil.getOperator(userParam.getUserDetails()), "功能类型是认证管理员账号不能授权"));
					CommonException exception = new CommonException(CommonException.ANY_DESC);
					exception.setDesc("功能类型是认证管理员账号不能授权");
					throw exception;
				}
				// 封装应用功能权限数据
				AppFunctionSelective appFunctionSelective = getAppFunctionSelective(userParam, userParam.getId(),
						function);

				try {
					// 保存应用功能授权信息
					appFunctionDAO.insert(appFunctionSelective);
				} catch (SQLException e) {
					kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
									SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
									SessionUtil.getOperator(userParam.getUserDetails()), "应用授权失败"));
					BizHelper.handleDBException(e, "应用授权ID");
				}
			}
		}

		// 记录操作日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
						SessionUtil.getOperator(userParam.getUserDetails()), "修改应用" + userParam.getAppName() + "成功"));
		// 封装功能数据返回
		return returnFilePath.toString();
	}

	@Override
	public Application patchCancelApplication(String functionUrl, AdminApplicationRequest userParam)
			throws SQLException, CommonException {
		ApplicationSelective selective = new ApplicationSelective();
		selective.setStatus(CommonConstants.Status.INVALID.getKey());
		ApplicationExample example = new ApplicationExample();
		example.createCriteria().andIdEqualTo(userParam.getId());
		try {
			applicationDAO.updateByExample(selective, example);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "功能ID");
		}
		Application application = applicationDAO.selectByPrimaryKey(userParam.getId());
		// 记录操作日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
						SessionUtil.getOperator(userParam.getUserDetails()),
						"应用" + application.getAppName() + "授权注销成功"));
		// 更新redis应用信息
		ApplicationModel applicationModel = getApplicationModel(application.getId());
		authCacheUtil.addApplicationToRedis(applicationModel);
		return application;
	}

	@Override
	public Application patchRecoverApplication(String functionUrl, AdminApplicationRequest userParam)
			throws SQLException, CommonException {
		ApplicationSelective selective = new ApplicationSelective();
		selective.setStatus(CommonConstants.Status.VALID.getKey());
		ApplicationExample example = new ApplicationExample();
		example.createCriteria().andIdEqualTo(userParam.getId());
		try {
			applicationDAO.updateByExample(selective, example);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "功能ID");
		}
		Application application = applicationDAO.selectByPrimaryKey(userParam.getId());
		// 记录操作日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
						SessionUtil.getOperator(userParam.getUserDetails()),
						"应用" + application.getAppName() + "授权恢复成功"));
		// 更新redis应用信息
		ApplicationModel applicationModel = getApplicationModel(application.getId());
		authCacheUtil.addApplicationToRedis(applicationModel);
		return application;
	}

	@Override
	public PageResult<AdminApplicationResponse> getApplications(Map<String, Object> map, int start, int limit)
			throws SQLException {
		PageResult<AdminApplicationResponse> list = this
				.page(AppFunctionManagements.SQLMAP_NAMESPACE + ".getApplications", map, start, limit);
		return list;
	}

	@Override
	public AdminApplicationResponse getApplicationById(Long id) throws SQLException {
		return this.queryForObject(AppFunctionManagements.SQLMAP_NAMESPACE + ".getApplicationById", id);
	}

	@Override
	public AppFunction getAppFunctionByUK(long functionId, long appId) throws SQLException {
		AppFunctionExample example = new AppFunctionExample();
		example.createCriteria().andFunctionIdEqualTo(functionId).andAppIdEqualTo(appId);
		List<AppFunction> list = appFunctionDAO.selectByExample(example);

		return list == null || list.isEmpty() ? null : list.get(0);
	}
}
