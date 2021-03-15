package com.example.service.impl;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.mapper.mapperExtends.UserMapperExtends;
import com.example.po.User;
import com.example.po.UserExample;
import com.example.po.UserExample.Criteria;
import com.example.service.UserService;
import com.example.viewModel.UserLoginVM;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapperExtends userMapper;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JavaMailSenderImpl mailSender;

	@Value("${spring.mail.username}")
	private String from;

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return userMapper.selectByExample(null);
	}

	@Override
	public int add(User record) {
		// TODO Auto-generated method stub
		record.setRole(true);
		record.setCreateTime(new Date());
		userMapper.insertSelective(record);
		return 1;
	}

	@Override
	public int update(User record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public User getById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User getUserByUserName(String userName) {
		if (userName == null) {
			return null;
		}
		UserExample ex = new UserExample();
		Criteria criteria = ex.createCriteria();
		criteria.andUserNameEqualTo(userName);
		User user = new User();
		try {
			user = userMapper.selectByExample(ex).get(0);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}

	@Override
	public void sendMimeMail(String to, String code) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		String subject = "来自泉州地方传统文化网站的激活邮件";
		String context = "<h3>您的验证码是：  " + code + "   ，如果非本人操作无需理会!</h3>";
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(context, true);
			mailSender.send(message);
			// 日志信息
			logger.info("邮件已经发送。");
		} catch (MessagingException e) {
			logger.error("发送邮件时发生异常！", e);
		}
	}

	@Override
	public int setUserEnable(User user, String code) {
		if (user.getCode().equals(code)) {
			user.setStatus(true);
			try {
				return update(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int checkLogin(UserLoginVM user) {
		User getUser = getUserByUserName(user.getUserName());
		if (user.getPassword().equals(getUser.getPassword())) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int count() {
		return userMapper.countByExample(null);

	}

	@Override
	public int userStateChanged(Integer id, Boolean isShow) {
		// TODO Auto-generated method stub
		UserExample ex = new UserExample();
		Criteria criteria = ex.createCriteria();
		criteria.andIdEqualTo(id);
		User tn = new User();
		tn.setStatus(isShow);
		userMapper.updateByExampleSelective(tn, ex);
		return 1;
	}

	@Override
	public int userRoleChanged(Integer id, Boolean isShow) {
		UserExample ex = new UserExample();
		Criteria criteria = ex.createCriteria();
		criteria.andIdEqualTo(id);
		User tn = new User();
		tn.setRole(isShow);
		userMapper.updateByExampleSelective(tn, ex);
		return 1;
	}
}
