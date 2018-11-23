package com.afis.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.afis.utils.Converter;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.FileUploaderException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.ExceptionRenderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller
@ControllerAdvice
public class UploadController {

	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UploadController.class);

	private WebProperties webProperties;

	@Autowired
	private ExceptionRenderUtil exceptionRenderUtil;

	@Autowired
	private MultipartConfigElement multipartConfigElement;

	private final long DIV_NUMBER = 1024L;

	private static String maxFileSize = null;

	private String[] allowedFileTypes;

	@Autowired
	public UploadController(WebProperties webProperties) {
		this.webProperties = webProperties;
		this.allowedFileTypes = webProperties.getUploader().getAllowedFileTypes();
		if (this.allowedFileTypes != null) {
			Arrays.sort(this.allowedFileTypes);
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = { "text/html" })
	@ResponseBody
	public String filesUpload(HttpServletRequest request, HttpServletResponse response)
			throws IOException, FileUploaderException, FileUploadException {
		Map<String, String> filePaths = new HashMap<>();

		if (webProperties.getUploader().getDestinationPath() == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "destination Path not setting.");
			return null;
		}

		// check the destination path exists.
		File file = new File(webProperties.getUploader().getDestinationPath());
		if (file.exists() && !file.isDirectory()) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "");
			return null;
		}

		// make destination dictionary if not exist.
		if (!file.exists()) {
			file.mkdirs();
		}
		String basePath = request.getParameter("basePath");
		String multiple = request.getParameter("multiple");
		logger.debug("base path: {}", basePath);
		basePath = normalizePath(basePath);
		logger.debug("base path: {}", basePath);

		boolean multiplePart = false;
		if (multiple != null) {
			multiplePart = Converter.getBooleanPrimitive(multiple);
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		WebResponse webResponse = new WebResponse();

		// do process.
		if (multiplePart) {
			MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
			checkFileTypes(multiFileMap);
			Map<String, List<FileInServer>> stringListMap = storeFiles(multiFileMap, basePath);
			webResponse.setData(stringListMap);
		} else {
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			checkFileTypes(fileMap);
			Map<String, FileInServer> files = storeFiles(fileMap, basePath);
			webResponse.setData(files);
		}
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(webResponse);
	}

	/**
	 * normalize upload path and check is out limit max path depth.
	 * 
	 * @param basePath
	 * @return
	 * @throws FileUploaderException
	 */
	private String normalizePath(String basePath) throws FileUploaderException {
		if (basePath == null) {
			return "";
		}
		basePath = FilenameUtils.normalizeNoEndSeparator(basePath);
		basePath = FilenameUtils.separatorsToUnix(basePath);
		basePath = FilenameUtils.getPathNoEndSeparator(basePath + "/a.text");

		int length = basePath.split("/").length;

		// check upload path out limit max depth.
		if (length + 1 >= webProperties.getUploader().getMaxUploadPathDepth()) {
			logger.debug("upload base path : [{}] is out limit max path depth: {}", basePath,
					webProperties.getUploader().getMaxUploadPathDepth());
			throw new FileUploaderException(FileUploaderException.OUT_LIMIT_MAX_UPLOAD_PATH_DEPTH,
					"upload base path : [" + basePath + "] is out limit max path depth: "
							+ webProperties.getUploader().getMaxUploadPathDepth());
		}
		return basePath;
	}

	/**
	 * check the upload file type is supported.
	 * 
	 * @param multiFileMap
	 * @throws FileUploaderException
	 */
	private void checkFileTypes(MultiValueMap<String, MultipartFile> multiFileMap) throws FileUploaderException {
		for (String s : multiFileMap.keySet()) {
			List<MultipartFile> multipartFiles = multiFileMap.get(s);
			for (MultipartFile multipartFile : multipartFiles) {
				checkFileTypes(multipartFile);
			}
		}
	}

	/**
	 * check the upload file type is supported.
	 * 
	 * @param fileMap
	 * @throws FileUploaderException
	 */
	private void checkFileTypes(Map<String, MultipartFile> fileMap) throws FileUploaderException {
		for (String key : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(key);
			checkFileTypes(multipartFile);
		}
	}

	/**
	 * check the upload file type is supported.
	 * 
	 * @param multipartFile
	 * @throws FileUploaderException
	 */
	private void checkFileTypes(MultipartFile multipartFile) throws FileUploaderException {
		if (!multipartFile.isEmpty()) {
			String originalFilename = multipartFile.getOriginalFilename();
			logger.debug("check extension of file: {}", multipartFile.getOriginalFilename());
			// check file type is allowed.
			if (this.allowedFileTypes != null && !FilenameUtils.isExtension(originalFilename, allowedFileTypes)) {
				logger.info("file : [{}] is not extension of {}", originalFilename, Arrays.toString(allowedFileTypes));
				throw new FileUploaderException(FileUploaderException.FILE_TYPE_NOT_SUPPORTED);
			}
		}
	}

	/**
	 * store files in server.
	 * 
	 * @param multiFileMap
	 * @return
	 * @throws IOException
	 */
	private Map<String, List<FileInServer>> storeFiles(MultiValueMap<String, MultipartFile> multiFileMap,
			String basePath) throws IOException {
		Map<String, List<FileInServer>> paths = new HashMap<>();
		for (String s : multiFileMap.keySet()) {
			List<MultipartFile> multipartFiles = multiFileMap.get(s);
			List<FileInServer> names = null;
			for (MultipartFile multipartFile : multipartFiles) {
				FileInServer file = storeFiles(multipartFile, basePath);
				if (file != null) {
					if (names == null) {
						names = new ArrayList<>();
					}
					names.add(file);
				}
			}
			if (names != null) {
				paths.put(s, names);
			}
		}
		return paths;
	}

	private Map<String, FileInServer> storeFiles(Map<String, MultipartFile> fileMap, String basePath)
			throws IOException {
		Map<String, FileInServer> files = new HashMap<>();
		for (String key : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(key);
			FileInServer fileInServer = storeFiles(multipartFile, basePath);
			if (fileInServer != null) {
				files.put(key, fileInServer);
			}
		}
		return files;
	}

	/**
	 * sotre files in server.
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	private FileInServer storeFiles(MultipartFile multipartFile, String basePath) throws IOException {
		if (!multipartFile.isEmpty()) {

			String originalFilename = multipartFile.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);

			String storeName = randomFileNames(extension);
			String path = FilenameUtils.concat(webProperties.getUploader().getDestinationPath(), basePath);
			File file = new File(path);
			if (!file.exists()) {
				FileUtils.forceMkdir(file);
			}
			path = FilenameUtils.concat(path, storeName);
			multipartFile.transferTo(new File(path));
			storeName = FilenameUtils.concat(basePath, storeName);
			storeName = FilenameUtils.separatorsToUnix(storeName);
			logger.debug("store file : [{}] to path: [{}] return file name: [{}]", originalFilename, path, storeName);
			FileInServer fileInServer = new FileInServer(storeName, originalFilename, multipartFile.getName(),
					extension);
			return fileInServer;
		}
		return null;
	}

	class FileInServer {
		private String name;
		private String originalFilename;
		private String field;
		private String fileType;

		public FileInServer() {
		}

		public FileInServer(String name, String originalFilename, String field, String fileType) {
			this.name = name;
			this.originalFilename = originalFilename;
			this.field = field;
			this.fileType = fileType;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOriginalFilename() {
			return originalFilename;
		}

		public void setOriginalFilename(String originalFilename) {
			this.originalFilename = originalFilename;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
	}

	/**
	 * random a file name to store in server.
	 * 
	 * @param extension
	 * @return
	 */
	private String randomFileNames(String extension) {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		String name = uuid + "." + extension;
		String path = FilenameUtils.concat(webProperties.getUploader().getDestinationPath(), name);
		File file = new File(path);
		if (file.exists()) {
			return randomFileNames(extension);
		}
		return name;
	}

	@ExceptionHandler(value = MultipartException.class)
	@ResponseBody
	public WebResponse uploadExceptionHandler(HttpServletRequest request, MultipartException ex,
			HttpServletResponse response) {
		ex.printStackTrace();
		WebResponse res = new WebResponse();
		if (ex.getRootCause() instanceof FileUploadBase.FileSizeLimitExceededException
				|| ex instanceof MaxUploadSizeExceededException) {
			FileUploaderException exception = new FileUploaderException(FileUploaderException.FILE_SIZE_OUT_LIMIT);
			exception.setDesc(dealMaxFileSize(multipartConfigElement.getMaxFileSize()));
			res.setResponseCode(exception.getErrorCode());
			res.setResponseDesc(exceptionRenderUtil.getMessage(exception));
		} else {
			FileUploaderException exception = new FileUploaderException(FileUploaderException.UPLOAD_FAILED);
			exception.setDesc(ex.getMessage());
			res.setResponseCode(exception.getErrorCode());
			res.setResponseDesc(exceptionRenderUtil.getMessage(exception));
		}
		return res;
	}

	/**
	 * 处理文件最大值
	 * 
	 * @author zhuzhenghua
	 * @date 2018年8月23日 下午3:29:55
	 *
	 * @param value
	 * @return
	 */
	private String dealMaxFileSize(long value) {
		if (maxFileSize != null) {
			return maxFileSize;
		}
		if (value < DIV_NUMBER) {
			maxFileSize = value + "B";
			return maxFileSize;
		}
		value = value / DIV_NUMBER;
		if (value < DIV_NUMBER) {
			maxFileSize = value + "KB";
			return maxFileSize;
		}
		value = value / DIV_NUMBER;
		maxFileSize = value + "MB";
		return maxFileSize;
	}

	@ExceptionHandler(value = FileUploaderException.class)
	@ResponseBody
	public WebResponse uploadFileExceptionHandler(HttpServletRequest request, FileUploaderException ex) {
		WebResponse response = new WebResponse();
		response.setResponseCode(ex.getErrorCode());
		String message = exceptionRenderUtil.getMessage(ex);
		response.setResponseDesc(message);
		return response;
	}

	/**
	 * 文件下载
	 * 
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/download/**")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = checkFileExists(request, 10);
		if (file == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		response.setContentType("application/force-download");// 设置强制下载不打开
		response.addHeader("Content-Disposition",
				"attachment;fileName=" + new String(file.getName().getBytes("GB2312"), "ISO_8859_1"));// 设置文件名

		downloadFile(file, response);
	}

	@RequestMapping(value = "/img/**")
	public void images(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = checkFileExists(request, 5);
		if (file == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String extension = FilenameUtils.getExtension(file.getName());
		switch (extension.toLowerCase()) {
		case "jpg":
		case "jpeg":
		case "jpe":
			response.setContentType("image/jpeg");
			break;
		case "png":
			response.setContentType("image/png");
			break;
		case "git":
			response.setContentType("image/gif");
			break;
		case "bmp":
			response.setContentType("image/bmp");
			break;
		default:
			response.setContentType("application/octet-stream");
			break;
		}

		downloadFile(file, response);
	}

	/**
	 * check file exists in disk.
	 * 
	 * @param request
	 * @param prefixLength
	 * @return
	 */
	private File checkFileExists(HttpServletRequest request, int prefixLength) {
		String requestURI = request.getRequestURI();
		if (webProperties.getUploader().getDestinationPath() == null) {
			return null;
		}

		if (requestURI.length() <= prefixLength) {
			return null;
		}

		// check file exists.
		String path = null;
		try {
			path = URLDecoder.decode(requestURI.substring(prefixLength), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		path = FilenameUtils.concat(webProperties.getUploader().getDestinationPath(), path);
		File file = new File(path);
		if (!file.exists() || file.isDirectory()) {
			logger.debug("download file not found [{}].", webProperties.getUploader().getDestinationPath());
			return null;
		}
		return file;
	}

	/**
	 * write file to http response.
	 * 
	 * @param file
	 * @param response
	 */
	private void downloadFile(File file, HttpServletResponse response) {
		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			OutputStream os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
		} catch (Exception e) {
			logger.info("exception when download file. {}", e.getMessage(), e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					logger.info("exception when download file. {}", e.getMessage(), e);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.info("exception when download file. {}", e.getMessage(), e);
				}
			}
		}
	}

}