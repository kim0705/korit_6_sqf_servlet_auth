package servlet.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import entity.User;


@WebServlet("/api/mypage/edit")
public class MypageEditServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("authentication");
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			
			User modifyUser = User.builder()
					.userId(user.getUserId())
					.name(name)
					.email(email)
					.build();
			UserDao.updateUserInfo(modifyUser);
			user.setName(name);
			user.setEmail(email);
			
			StringBuilder responseBody = new StringBuilder();
			responseBody.append("<script>");
			responseBody.append("alert('수정 완료!');"); // \n을 사용하면 자바문법으로 인식해서 \\n을 사용해야함
			responseBody.append("location.href='/ssa/mypage';");
			responseBody.append("</script>");
			resp.setContentType("text/html");
			resp.getWriter().println(responseBody.toString());
		
	}
	
}
