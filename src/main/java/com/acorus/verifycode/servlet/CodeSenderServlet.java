package com.acorus.verifycode.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acorus.verifycode.util.VerifyCodeConfig;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CodeSenderServlet
 */
public class CodeSenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phoneNo = request.getParameter("phone_no");
		if(phoneNo==null)
			return;
		Jedis jedis = new Jedis(VerifyCodeConfig.HOST, VerifyCodeConfig.PORT);
		String countKey = VerifyCodeConfig.PHONE_PREFIX
				+ phoneNo + VerifyCodeConfig.COUNT_SUFFIX;
		String countStr = jedis.get(countKey);
		if(countStr==null) {
			jedis.setex(countKey, VerifyCodeConfig.SECONDS_PER_DAY, "1");
		}else {
			int count = Integer.parseInt(countStr);
			if (count >= VerifyCodeConfig.COUNT_TIMES_1DAY) {
				jedis.close();
				response.getWriter().print("limit");
				return;
			} else {
				jedis.incr(countKey);
			}

		}
		String code = getCode(VerifyCodeConfig.CODE_LEN);
		String codeKey = VerifyCodeConfig.PHONE_PREFIX 
				+ phoneNo + VerifyCodeConfig.PHONE_SUFFIX;
		jedis.setex(codeKey, VerifyCodeConfig.CODE_TIMEOUT, code);
		jedis.close();
		System.out.println(code);
		response.getWriter().print(true);
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String getCode(int len) {
		String code = "";
		for (int i = 0; i < len; i++) {
		int rand = new Random().nextInt(10);
		code += rand;
		}
		return code;
	} 
}
