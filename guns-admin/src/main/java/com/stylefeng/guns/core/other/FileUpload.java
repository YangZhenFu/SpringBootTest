package com.stylefeng.guns.core.other;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	/***
	 * 图片上传辅助用
	 */
	private MultipartFile[] file;
	
	private String fileTypes;
	
	private String fileType;

	public String getFileTypes() {
		return fileTypes==null ? null : fileTypes.toLowerCase();
	}

	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}

	public MultipartFile[] getFile() {
		return file;
	}

	public void setFile(MultipartFile[] file) {
		this.file = file;
	}


	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Override
	public String toString() {
		return "FileUpload [file=" + Arrays.toString(file) + ", fileTypes=" + fileTypes + ", fileType=" + fileType
				+ "]";
	}
}
