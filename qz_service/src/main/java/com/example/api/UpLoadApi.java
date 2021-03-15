package com.example.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.base.RestResponse;
import com.example.utils.TencentCOS;
import com.example.viewModel.UploadImageResVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/upload")
@Api("上传管理")
public class UpLoadApi {
	private static String secretId = "AKIDYw60f5uBrSqzykqZbO4ViiSxDDydDMQF";

	private static String secretKey = "kGXYS8ucDajcHUXyMWSQJ3a3Rfx3984q";

	private static final String bucketName = "qz-1300435011";
	@Value("${tencent.path}")
	private String IMAGE_PATH;

	/**
	 * 使用fileInput插件进行文件上传
	 * 
	 * @param request  获取上传图片
	 * @param response
	 * @return 上传结果
	 * @throws UnsupportedEncodingException//编码异常
	 */
	@PostMapping("/uploadimage")
	@ApiOperation(value = "图片上传")
	@ApiImplicitParam(name = "multipartFile", value = "文件", required = true, paramType = "query", dataType = "MultipartFile")
	public RestResponse<UploadImageResVM> uplodaImageOneWithFileInput(HttpServletRequest request,
			@RequestParam("file") MultipartFile multipartFile) throws UnsupportedEncodingException {
//		System.out.println(multipartFile.getContentType().substring(6));
		UploadImageResVM res = new UploadImageResVM();
		/** 页面控件的文件流* */

		if (multipartFile == null || multipartFile.isEmpty()) {
			res.setUploaded(0);
//			System.out.println(res);
			return RestResponse.fail(2, "上传失败");
		}
		String fileName = multipartFile.getOriginalFilename();
		// 获取文件后缀
		String prefix = fileName.substring(fileName.lastIndexOf("."));
		File excelFile = null;
		try {
			excelFile = File.createTempFile("imagesFile-" + System.currentTimeMillis(), prefix);
			multipartFile.transferTo(excelFile);
//			System.out.println(multipartFile.toString());
			fileName = TencentCOS.uploadfile(excelFile, secretId, secretKey, bucketName);
			// 访问路径
			String path = "https://" + bucketName + ".cos.ap-guangzhou.myqcloud.com/" + fileName;
			res.setUploaded(1);
			res.setFileName(fileName);
			res.setUrl(path);
//			System.out.println(path);
			// 程序结束时，删除临时文件
		} catch (IOException e) {
			// TODO Auto-generated catch block
			res.setUploaded(0);
		}
		return RestResponse.ok(res);
	}

	@DeleteMapping("/delete")
	public RestResponse delete(@RequestParam("imageKey") String imageKey) {
		TencentCOS.deletefile(imageKey);
		return RestResponse.ok();
	}

}
