package springboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springboard.command.BbsCommandImpl;
import springboard.command.DeleteActionCommand;
import springboard.command.EditActionCommand;
import springboard.command.EditCommand;
import springboard.command.ListCommand;
import springboard.command.ViewCommand;
import springboard.command.WriteActionCommand;
import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsDTO;

@Controller
public class BbsController {
	
	@Autowired
	private JDBCTemplateDAO dao;
	
	//모든 Command객체의 부모타입의 참조 변수 생성
	BbsCommandImpl command =null;
	
	@Autowired
	ListCommand listCommand;	
	@Autowired
	WriteActionCommand writeActionCommand;
	@Autowired
	ViewCommand viewCommand;
	@Autowired
	EditCommand editCommand;
	@Autowired
	DeleteActionCommand deleteActionCommand;
	@Autowired
	EditActionCommand editActionCommand;
	
	//목록보기 
	@RequestMapping("/board/list.do")
	public String list(Model model, HttpServletRequest req) {
		model.addAttribute("req", req);
	
		//command = new ListCommand(); 
		command = listCommand;
		command.execute(model);
		
		//return "07Board/list";
		return "BootSkin/listT";
	}
	
	//글쓰기 페이지로 진입
	@RequestMapping("/board/write.do")
	public String write(Model model) {
		
		return "BootSkin/writeT";
	}
	
	//글쓰기 
	@RequestMapping(value="/board/writeAction.do",
			method=RequestMethod.POST)
	public String writeAction(Model model, HttpServletRequest req, SpringBbsDTO springBbsDTO) {
		
		model.addAttribute("req", req);
		model.addAttribute("springBbsDTO", springBbsDTO);
		//command = new WriteActionCommand();
		command = writeActionCommand;
		command.execute(model);

		return "redirect:list.do?nowPage=1";
	}
	
	//글 상세보기
	@RequestMapping("/board/view.do")
	public String view(Model model, HttpServletRequest req, SpringBbsDTO springBbsDTO) {
		
		model.addAttribute("req", req);
		model.addAttribute("springBbsDTO", springBbsDTO);
		
		//command = new ViewCommand();
		command = viewCommand;
		command.execute(model);
		
		return "BootSkin/viewT";
	}
	
	//패스워드 검증 페이지로 진입
	@RequestMapping("/board/password.do")
	public String password(Model model, HttpServletRequest req) {
		
		model.addAttribute("idx", req.getParameter("idx"));
		return "BootSkin/password";
	}
	
	//패스워드 검증
	@RequestMapping("/board/passwordAction.do")
	public String passwordAction(Model model, HttpServletRequest req) {
		
		String modePage =null;
		//폼값받기
		String mode = req.getParameter("mode");
		String idx = req.getParameter("idx");
		String nowPage = req.getParameter("nowPage");
		String pass = req.getParameter("pass");
		
		//DAO에서 일련번호와 패스워드를 통해 검증
		//JDBCTemplateDAO dao = new JDBCTemplateDAO();
		int rowExist = dao.password(idx, pass);
		
		if(rowExist<=0) {
			//패스워드 검증 실패 시에는 이전 페이지로 돌아간다.
			model.addAttribute("isCorrMsg", "패스워드가 일치하지 않습니다.");
			model.addAttribute("idx", idx);
			//패스워드 검증 페이지를 반환
			
			modePage = "BootSkin/password";
		}
		else {
			//검증에 성공한 경우 수정 혹은 삭제 처리를 한다. 
			System.out.println("검증완료");
			
			if(mode.equals("edit")) {
				/*
				 mode가 수정인 경우 수정 페이지로 이동한다. 
				 */
				model.addAttribute("req", req);
				//command = new EditCommand();
				command = editCommand;
				command.execute(model);
				
				modePage = "BootSkin/editT";
			}
			else if(mode.equals("delete")) {
				//mode가 delete인 경우 즉시 삭제 처리
				model.addAttribute("req", req);
				//command = new DeleteActionCommand();
				command = deleteActionCommand;
				command.execute(model);
				
				//삭제 후에는 리스트페이지로 이동한다
				model.addAttribute("nowPage", req.getParameter("nowPage"));
				modePage = "redirect:list.do";
			}
		}
		return modePage;
	}
	
	//수정 처리 
	@RequestMapping("/board/editAction.do")
	public String editAction(HttpServletRequest req, Model model, SpringBbsDTO springBbsDTO) {
		
		/*
		 request 내장 객체와 수정 페이지에서 전송한 모든 폼 값을 저장한 
		 DTO객체를 Model에 저장한 후 서비스 객체로 전달한다. 
		 */
		model.addAttribute("req", req);
		model.addAttribute("springBbsDTO", springBbsDTO);
		
		//command = new EditActionCommand();
		command = editActionCommand;
		command.execute(model);
		
		/*
		 수정 처리가 완료 되면 상세 페이지로 이동하게 되는 데 이때
		 idx와 같은 파라미터가 필요하다. Model객체에 저장한 후 redirect하면
		 자동으로 쿼리스트링 형태로 만들어 준다.  
		 */
		model.addAttribute("idx", req.getParameter("idx"));
		model.addAttribute("nowPage", req.getParameter("nowPage"));
		
		return "redirect:view.do";
	}
	
	
}
