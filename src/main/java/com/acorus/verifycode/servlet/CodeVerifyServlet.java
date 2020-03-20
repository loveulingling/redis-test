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
 * Servlet implementation class CodeVerifyServlet
 */
public class CodeVerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phoneNo = request.getParameter("phone_no");
		String verifyCode = request.getParameter("verify_code");
		if (phoneNo == null || verifyCode == null) 
			return;
		Jedis jedis = new Jedis(VerifyCodeConfig.HOST, VerifyCodeConfig.PORT);
		String codeKey = VerifyCodeConfig.PHONE_PREFIX 
				+ phoneNo + VerifyCodeConfig.PHONE_SUFFIX;
		String code = jedis.get(codeKey);
		jedis.close();	
		if(verifyCode.equals(code)) 
			response.getWriter().print(true);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	


}
