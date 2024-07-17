package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.User;

//@WebFilter({"/mypage", "/mypage/password"})
@WebFilter(filterName = "securityFilter")
public class SecurityFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request; // 다운 캐스팅
		HttpSession session = httpRequest.getSession();
		
		System.out.println("시큐리티 필터");
		
		User user = (User) session.getAtt+ribute("authentication");
		if(user == null) {
			StringBuilder responseBody = new StringBuilder();
			responseBody.append("<script>");
			responseBody.append("alert('잘못된 접근입니다.\\n로그인 후에 이용 바랍니다.');"); // \n을 사용하면 자바문법으로 인식해서 \\n을 사용해야함
			responseBody.append("location.href='/ssa/login';");
			responseBody.append("</script>");
			response.setContentType("text/html");
			response.getWriter().println(responseBody.toString());
			return;
		}
		chain.doFilter(request, response);
	}
 
}
