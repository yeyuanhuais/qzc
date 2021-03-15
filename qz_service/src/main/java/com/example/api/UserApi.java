package com.example.api;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.base.RestResponse;
import com.example.base.SystemCode;
import com.example.config.KaptchaConfig;
import com.example.config.interceptor.UserLoginToken;
import com.example.po.User;
import com.example.service.AuthenticationService;
import com.example.service.UserService;
import com.example.utils.DateTimeUtil;
import com.example.utils.TokenUtil;
import com.example.viewModel.PageInfoVM;
import com.example.viewModel.UserLoginVM;
import com.example.viewModel.UserRegisterVM;
import com.example.viewModel.UserResponseVM;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Api("用户管理")
public class UserApi extends BaseApi {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationService authenticationService;

	@UserLoginToken
	@PostMapping(value = "/getAll")
	@ApiOperation(value = "获取所有用户信息")
	@ApiImplicitParam(name = "queryInfo", value = "", required = true, paramType = "body", dataType = "PageInfoVM")
	public RestResponse<List<UserResponseVM>> getAll(@RequestBody PageInfoVM queryInfo) {
		if (queryInfo.getSize() == 0) {
			PageHelper.startPage(queryInfo.getNum(), userService.count());
		} else {
			PageHelper.startPage(queryInfo.getNum(), queryInfo.getSize());
		}
		List<User> userList = userService.getAll();
		List<UserResponseVM> userVM = userList.stream().map(d -> {
			UserResponseVM vm = modelMapper.map(d, UserResponseVM.class);
			try {
				vm.setCreateTime(DateTimeUtil.dateFormat(d.getCreateTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vm;
		}).collect(Collectors.toList());
		return RestResponse.ok(userVM, userService.count());
	}

//	@UserLoginToken
	@PostMapping("/add")
	@ApiOperation(value = "添加用户")
	@ApiImplicitParam(name = "model", value = "用户信息", required = true, paramType = "body", dataType = "User")
	public RestResponse add(@RequestBody User model) {
		model.setPassword(authenticationService.pwdEncode(model.getPassword()));
		userService.add(model);
		return RestResponse.ok();
	}

//	@UserLoginToken
	@PutMapping("/edit")
	@ApiOperation(value = "编辑用户")
	@ApiImplicitParam(name = "model", value = "用户信息", required = true, paramType = "body", dataType = "User")
	public RestResponse edit(@RequestBody User model) {
		if (model.getPassword() != "" || model.getPassword() != null) {
			model.setPassword(authenticationService.pwdEncode(model.getPassword()));
		}
		System.out.println(model.getId());
		userService.update(model);
		return RestResponse.ok();
	}

	@UserLoginToken
	@DeleteMapping("/delete")
	@ApiOperation(value = "删除用户")
	public RestResponse delete(@ApiParam(value = "id", required = true) @RequestParam("id") Integer id) {
		userService.delete(id);
		return RestResponse.ok();
	}

	@UserLoginToken
	@PostMapping("/getById/{id}")
	@ApiOperation(value = "根据id查询用户")
	public RestResponse<UserResponseVM> getById(@ApiParam(value = "id", required = true) @PathVariable Integer id) {
		User user = userService.getById(id);
		user.setPassword(authenticationService.pwdDecode(user.getPassword()));
		UserResponseVM userVM = UserResponseVM.from(user);
		return RestResponse.ok(userVM);
	}

	@PostMapping("/register")
	@ApiOperation(value = "注册用户")
	@ApiImplicitParam(name = "model", value = "用户信息", required = true, paramType = "body", dataType = "UserRegisterVM")
	public RestResponse register(@RequestBody UserRegisterVM model) {
		User existUser = userService.getUserByUserName(model.getUserName());
		if (null != existUser) {
			if (existUser.getStatus()) {
				return new RestResponse<>(2, "用户已存在并激活成功");
			} else {
				userService.sendMimeMail(existUser.getEmail(), existUser.getCode());
				return new RestResponse<>(2, "用户已存在但未激活成功");
			}
		}
		User user = modelMapper.map(model, User.class);
		String encodePwd = authenticationService.pwdEncode(model.getPassword());
		user.setPassword(encodePwd);
		user.setStatus(true);
		userService.add(user);
		return RestResponse.ok();
	}

	/**
	 * 校验激活码
	 * 
	 */
	@GetMapping("/checkCode")
	@ApiOperation(value = "激活码查询用户")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query", dataType = "String") })
	public RestResponse checkCode(@RequestParam("userName") String userName, @RequestParam("code") String code) {
		User user = userService.getUserByUserName(userName);
		// 如果用户不等于null，把用户状态修改status=1
		if (user != null) {
			userService.setUserEnable(user, code);
			return RestResponse.ok();
		}
		return RestResponse.fail(2, "状态激活失败");
	}

	@PostMapping(value = "/login")
	@ApiOperation(value = "用户登录")
	@ApiImplicitParam(name = "user", value = "用户信息", required = true, paramType = "body", dataType = "UserLoginVM")
	public RestResponse<UserResponseVM> login(@RequestBody UserLoginVM user, HttpServletRequest request) {
		User uservm = userService.getUserByUserName(user.getUserName());
		if (uservm == null) {
			return RestResponse.system(SystemCode.INEXISTENCE);
		} else if (!uservm.getStatus()) {
			return RestResponse.system(SystemCode.INACTIVAED);
		} else if (user.getCaptcha() == null) {
			return new RestResponse<>(2, "验证码为空！");
		}
		// 账号密码验证
		if (authenticationService.authUser(user.getUserName(), user.getPassword())) {
			String verCode = (String) request.getSession().getAttribute("verify_session_Code");
			if (user.getCaptcha().toLowerCase().equals(verCode.toLowerCase())) {
				String token = "";
				token = TokenUtil.sign(uservm);
				uservm.setToken(token);
				userService.update(uservm);
				UserResponseVM vm = UserResponseVM.from(uservm);
				return RestResponse.ok("登录成功，欢迎访问！", vm);
			}
			return RestResponse.fail(2, "验证码错误！");
		}
		return RestResponse.fail(2, "账号密码错误！");
	}

	@GetMapping("/sendMimeMail")
	@ApiOperation(value = "发送邮件验证码")
	@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query", dataType = "UserLoginVM")
	public RestResponse sendMimeMail(@RequestParam("userName") String userName, @RequestParam("email") String email) {
		if (userName == null) {
			return new RestResponse<>(2, "请输入激活的用户名");
		}
		User existUser = userService.getUserByUserName(userName);
		if (null != existUser && existUser.getStatus()) {
			return new RestResponse<>(2, "该用户已存在！");
		}
		String code = String.valueOf(new Random().nextInt(899999) + 100000);
		userService.sendMimeMail(email, code);
		return RestResponse.ok(1, code);
	}

	/**
	 * 查询用户token是否失效
	 * 
	 */
	@GetMapping("/checkLogin")
	@ApiOperation(value = "查询用户token是否失效")
	@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "query", dataType = "Integer")
	public RestResponse<UserResponseVM> checkLogin(@RequestParam("id") Integer id) {
		User user = userService.getById(id);
		// 如果用户不等于null，把用户状态修改status=1
		if (user != null) {
			// 验证 token
			boolean result = TokenUtil.verify(user.getToken());
			if (result) {
				UserResponseVM vm = UserResponseVM.from(user);
				return RestResponse.ok(vm);
			}
			return RestResponse.fail(2, "该用户登录已过期，请重新登录！");
		}
		return RestResponse.fail(2, "不存在该用户");
	}

	@GetMapping(value = "/verification")
	public void getVerification(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] verByte = null;
		HttpSession session = request.getSession();
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串并保存到session中
			KaptchaConfig kaptchaConfig = new KaptchaConfig();
			String createText = kaptchaConfig.defaultKaptcha().createText();
			session.setAttribute("verify_session_Code", createText);
			session.setMaxInactiveInterval(3600);
			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = kaptchaConfig.defaultKaptcha().createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		verByte = jpegOutputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(verByte);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	@UserLoginToken
	@PutMapping("{id}/userStateChanged/{isShow}")
	@ApiOperation(value = "根据id修改用户状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
			@ApiImplicitParam(name = "isShow", value = "用户状态", required = true, paramType = "path", dataType = "Boolean") })
	public RestResponse userStateChanged(@PathVariable("id") Integer id, @PathVariable("isShow") Boolean isShow) {
		userService.userStateChanged(id, isShow);
		return RestResponse.ok();
	}

	@UserLoginToken
	@PutMapping("{id}/userRoleChanged/{isShow}")
	@ApiOperation(value = "根据id修改用户角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
			@ApiImplicitParam(name = "isShow", value = "用户角色", required = true, paramType = "path", dataType = "Boolean") })
	public RestResponse userRoleChanged(@PathVariable("id") Integer id, @PathVariable("isShow") Boolean isShow) {
		userService.userRoleChanged(id, isShow);
		return RestResponse.ok();
	}

	@UserLoginToken
	@GetMapping("/resetPassword")
	public RestResponse resetPassword(@RequestParam("id") Integer id) {
		User user = userService.getById(id);
		user.setPassword(authenticationService.pwdEncode("123456"));
		userService.update(user);
		return RestResponse.ok();
	}
}
