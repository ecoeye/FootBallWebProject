package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MainNoticeDao;
import dao.MainPhotoDao;
import dao.MaingoodsDao;
import dao.MatchInfoDao;
import dao.MainNewsDao;
import dao.QaDao;
import dao.RankDao;
import dto.GoodsDto;
import dto.MainNoticeDto;
import dto.MainPhotoDto;
import dto.MatchDBDto;
import dto.MainNewsDto;
import dto.QaDto;
import dto.RankDto;
import user.UserDAO;

public class QADetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); 
		HttpSession session = request.getSession();
		int bno = Integer.parseInt(request.getParameter("bno"));
		String writer = request.getParameter("writer");
		String open = (String)request.getParameter("open");

		System.out.println("open123444555!!!!!!!!!!!!!!!!!!!!!!!!!!"+open);
		System.out.println(open);
		int pageNum =0;
		String id1 = "";
		try{
			id1 = (String)session.getAttribute("id");
			pageNum = Integer.parseInt(request.getParameter("page"));
		}catch(Exception e){
			pageNum = 1;
			id1 = "";
		}
		
		QaDao qadao = new QaDao();
		QaDto qadto = null;
		try {
			qadto = qadao.getQaDetailDto(bno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String pw = (String)session.getAttribute("pw");
		UserDAO udao = new UserDAO();
		user.UserDTO udto = udao.loginDAO(id1, pw);
		MainPhotoDao pdao= new MainPhotoDao();
		ArrayList <MainPhotoDto> pdto = pdao.getPhotoimg();
		MainNewsDao ndao = new MainNewsDao();
		ArrayList <MainNewsDto> ndto = ndao.getNews();
		MainNoticeDao ntdao = new MainNoticeDao();
		ArrayList <MainNoticeDto> ntdto = ntdao.getNotice();
		RankDao radao = new RankDao();
		ArrayList <RankDto> radto = radao.getFcRank();
		
		MatchInfoDao midao = new MatchInfoDao();
		ArrayList<MatchDBDto> prevdto = midao.prevMatch();
		ArrayList<MatchDBDto> nextdto = midao.nextMatch();
		ArrayList<MatchDBDto> nextnextdto = midao.nextnextMatch();
		
		MaingoodsDao mgdao = new MaingoodsDao();
		ArrayList<GoodsDto> goodsdto = mgdao.getbannergoods();
		ArrayList<GoodsDto> goodsdto2 = mgdao.getbannergoods2();
		ArrayList<GoodsDto> goodsdto3 = mgdao.getbannergoods3();
		
		request.setAttribute("pdto", pdto);
		request.setAttribute("ndto", ndto);
		request.setAttribute("ntdto", ntdto);
		request.setAttribute("radto", radto);
		request.setAttribute("prevdto", prevdto);
		request.setAttribute("nextdto", nextdto);
		request.setAttribute("nextnextdto", nextnextdto);
		request.setAttribute("goodsdto", goodsdto);
		request.setAttribute("goodsdto2", goodsdto2);
		request.setAttribute("goodsdto3", goodsdto3);
		
		if(open.equals("y")) {
			request.setAttribute("qadto",qadto);
			request.setAttribute("udto", udto);
			RequestDispatcher rd = request.getRequestDispatcher("QaDetail.jsp");
			rd.forward(request,response);
		}else if(id1.equals("operator")) {
			request.setAttribute("qadto",qadto);
			request.setAttribute("udto", udto);
			RequestDispatcher rd = request.getRequestDispatcher("QaDetail.jsp");
			rd.forward(request,response);
		}else if(id1.equals(writer)) {
			request.setAttribute("qadto",qadto);
			request.setAttribute("udto", udto);
			RequestDispatcher rd = request.getRequestDispatcher("QaDetail.jsp");
			rd.forward(request,response);
		}else {
			request.setAttribute("qamsg", "<script>alert('비공개 게시글입니다')</script>");
			request.setAttribute("qDto",qadto);
			request.setAttribute("udto", udto);
			RequestDispatcher rd = request.getRequestDispatcher("FcController?command=QA");
			rd.forward(request,response);

		}
		
	}

}
