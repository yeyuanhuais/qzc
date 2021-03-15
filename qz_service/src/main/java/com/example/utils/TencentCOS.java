package com.example.utils;

import java.io.File;
import java.util.Random;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

public class TencentCOS {

	private static final String secretId = "AKIDYw60f5uBrSqzykqZbO4ViiSxDDydDMQF";

	private static final String secretKey = "kGXYS8ucDajcHUXyMWSQJ3a3Rfx3984q";
//
	private static final String bucketName = "qz-1300435011";
//
	private static COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
//
	private static ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));

	/**
	 * 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口 大文件上传请参照 API 文档高级 API 上传
	 * 
	 * @param localFile
	 */

	public static String uploadfile(File localFile, String secretId, String secretKey, String bucketName)
			throws CosClientException, CosServiceException {

		COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
		ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
		// 生成cos客户端
		COSClient cosclient = new COSClient(cred, clientConfig);
		String fileName = null;
		try {
			// 指定要上传的文件
			// 指定要上传到的存储桶
			// 指定要上传到 COS 上对象键
			fileName = localFile.getName();
			String substring = fileName.substring(fileName.lastIndexOf("."));
			Random random = new Random();
			String key = random.nextInt(10000) + System.currentTimeMillis() + substring;
			System.out.println(key);
			fileName = key;
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
			PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
		} catch (CosServiceException serverException) {
			serverException.printStackTrace();
		} catch (CosClientException clientException) {
			clientException.printStackTrace();
		} finally {
			// 关闭客户端(关闭后台线程)
			cosclient.shutdown();
		}
		return fileName;
	}

	/**
	 * 
	 * @Title: downFile
	 * @Description: 下载文件
	 * @return
	 */
	public static void downFile() {
		// 生成cos客户端
		COSClient cosclient = new COSClient(cred, clientConfig);
		// 要下载的文件路径和名称
		String key = "down/demo1.jpg";
		// 指定文件的存储路径
		File downFile = new File("src/test/resources/mydown.txt");
		// 指定要下载的文件所在的 bucket 和对象键
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
		ObjectMetadata downObjectMeta = cosclient.getObject(getObjectRequest, downFile);
	}

	/**
	 * 删除文件
	 * 
	 * @param key
	 */

	public static void deletefile(String key) throws CosClientException, CosServiceException {
		COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
		ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
		// 生成cos客户端
		COSClient cosclient = new COSClient(cred, clientConfig);
		// 指定要删除的 bucket 和路径
		try {
			cosclient.deleteObject(bucketName, key);
		} catch (CosServiceException serverException) {
			serverException.printStackTrace();
		} catch (CosClientException clientException) {
			clientException.printStackTrace();
		}
		// 关闭客户端(关闭后台线程)
		cosclient.shutdown();
	}

}
