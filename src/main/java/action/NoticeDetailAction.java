package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MainNoticeDao;
import dao.MainPhotoDao;
import dao.MaingoodsDao;
import dao.MatchInfoDao;
import dao.MainNewsDao;
import dao.NoticeDAO;
import dao.RankDao;
import dto.GoodsDto;
import dto.MainNoticeDto;
import dto.MainPhotoDto;
import dto.MatchDBDto;
import dto.MainNewsDto;
import dto.NoticeDTO;
import dto.RankDto;

public class NoticeDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int bno = Integer.parseInt(request.getParameter("bno"));
		int pageNum;
		try{
			pageNum = Integer.parseInt(request.getParameter("page"));
			if(pageNum == 0){
				pageNum = 1;
			} 
		} catch(NumberFormatException e){
			pageNum = 1;
		}
		int endNum = pageNum * 10;
		int startNum = endNum - 9;
		NoticeDAO nDao = new NoticeDAO();
		ArrayList<NoticeDTO> noticeList = nDao.noticeSelect(startNum, endNum);
		NoticeDTO nDetail = nDao.noticeDetail(bno);
		NoticeDTO nCount = nDao.noticeCount();
		int count = nCount.getCount();
		
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
		request.setAttribute("noticeList", noticeList);	
		request.setAttribute("title", nDetail.getTitle());
		request.setAttribute("content", nDetail.getContent());
		request.setAttribute("w_date", nDetail.getW_date());
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("bno", bno);
		
		RequestDispatcher rd = request.getRequestDispatcher("NoticeDetail.jsp");
		rd.forward(request, response);
	}

}
