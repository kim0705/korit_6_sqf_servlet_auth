package servlet.service;

import java.io.IOException;

/**
 * Servlet 3대 저장소
 * 1. Application(Context)
 * - 전역(모든 쓰레드가 접근할 수 있는 영역)
 * - 서블릿 설정 관련 데이터(객체)들을 보관 
 * - 생명주기: 서버가 실행되어지고 종료되어질 때 까지 
 * 2. Request
 * - 요청이 들어오고 Filter, Servlet, JSP 등 서로 Request 객체를 넘겨줄 때 사용
 * - 생명주기: 서버가 실행되어지고 종료되어질 때 까지는 공통, 요청이 들어오고 응답이 되어지면 소멸
 * 3. Session
 * - 동일 클라이언트(동일 JSESSION KEY값)일 때, 데이터를 유지
 * - 생명주기: 서버가 실행되어지고 종료되어질 때 까지는 공통, 첫 요청 때 부터 브라우저가 종료되거나, 만료시간이 다되거나 소멸
 */
 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDao;
import entity.User;

@WebServlet("/api/login")
public class LoginServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = UserDao.findUserByUsername(username); // db에 저장되어 있는 data
		if(user == null) {
			responseLoginError(resp);
			return;
		}
		
		if(!BCrypt.checkpw(password, user.getPassword())) {
			responseLoginError(resp);
			return;
		}
		
		HttpSession session = req.getSession();
		session.setAttribute("authentication", user);
		resp.sendRedirect("/ssa/index"); // index로 재요청 
	}
	
	private void responseLoginError(HttpServletResponse resp) throws IOException {
		StringBuilder responsebody = new StringBuilder();
		responsebody.append("<script>");
		responsebody.append("alert('사용자 정보를 확인해주세요.');");
		responsebody.append("history.back();");
		responsebody.append("</script>");
		
		resp.setContentType("text/html");
		resp.getWriter().println(responsebody.toString());
	}
	
//	private void responseLoginSuccess(HttpServletResponse resp) throws IOException {
//		StringBuilder responsebody = new StringBuilder();
//		responsebody.append("<script>");
//		responsebody.append("location.replace('/ssa/index')");
//		responsebody.append("</script>");
//		
//		resp.setContentType("text/html");
//		resp.getWriter().println(responsebody.toString());
//	}

}
