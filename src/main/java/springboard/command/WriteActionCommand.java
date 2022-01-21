package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsDTO;

@Service
public class WriteActionCommand implements BbsCommandImpl{
	
	@Autowired
	JDBCTemplateDAO dao;
	
	@Override
	public void execute(Model model) {
		
		//Model에 저장된 값을 Map컬렉션으로 변환한다.
		Map<String, Object> paramMap = model.asMap();
		//첫번째로 request내장 객체를 가져온다.  
		HttpServletRequest req = 
				(HttpServletRequest)paramMap.get("req");
		//두번째로 작성페이지에서 전송한 모든 폼 값이 저장된 DTO객체를 가져온다.
		SpringBbsDTO springBbsDTO = (SpringBbsDTO)paramMap.get("springBbsDTO");
		
		//폼 값 확인용
		System.out.println("springBbsDTO.title=" + springBbsDTO.getTitle());
		
		//DAO객체 생성 및 쓰기 처리
		//JDBCTemplateDAO dao = new JDBCTemplateDAO();
		int affected = dao.write(springBbsDTO);
		System.out.println("입력된 결과:"+affected);
		//dao.close();
	}
}
