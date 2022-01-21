package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsDTO;

@Service
public class ViewCommand implements BbsCommandImpl{

	@Autowired
	JDBCTemplateDAO dao;
	
	@Override
	public void execute(Model model) {
		
		//요청 일괄 받기 
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
		SpringBbsDTO dto = (SpringBbsDTO)paramMap.get("springBbsDTO");
		
		//폼 값 받기
//		String idx = req.getParameter("idx");
//		String nowPage = req.getParameter("nowPage");
		
		//DAO, DTO 객체 생성 및 상세보기 처리를 위한 메서드 호출
		//JDBCTemplateDAO dao = new JDBCTemplateDAO();
//		SpringBbsDTO dto = new SpringBbsDTO();
		
		dto = dao.view(String.valueOf(dto.getIdx()));
		//줄바꿈 처리
		dto.setContents(dto.getContents().replace("\r\n", "<br/>"));
		
		model.addAttribute("viewRow", dto);
		model.addAttribute("nowPage", dto.getNowPage());
		
	}
}
