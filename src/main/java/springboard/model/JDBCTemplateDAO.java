package springboard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

//스프링 컨테이너가 시작될 때 Scan의 대상이 되므로 자동으로 빈이 생성된다. 
@Repository
public class JDBCTemplateDAO {

	/*
	 servlet-context.xml에서 생성한 빈을 자동 주입 받는다.
	 오라클 연결 정보를 가지고 있다.  
	 */
	@Autowired
	JdbcTemplate template;
	
	public JDBCTemplateDAO() {		
		System.out.println("JDBCTemplateDAO() 생성자 호출");
	}	
	public void close() {}
	
	//게시물의 갯수 카운트 
	public int getTotalCount(Map<String, Object> map) {
		
		String sql ="SELECT COUNT(*) FROM springboard ";
		
		if(map.get("Word")!=null) {
			sql += " WHERE " + map.get("Column") + " "
				+ " LIKE '%" + map.get("Word") + "%' ";
		}
		System.out.println(sql);
		//쿼리문에서 count(*)를 통해 반환되는 값을 정수 형태로 가져온다.
		return template.queryForObject(sql, Integer.class);
	}
	
	//게시판의 리스트 가져오기(페이징 처리 없음)
	public ArrayList<SpringBbsDTO> list(Map<String, Object> map){
		
		String sql = "SELECT * FROM springboard"; 
		
		if(map.get("Word")!=null) {
			sql += " WHERE " + map.get("Column") + " "
				+ " LIKE '%" + map.get("Word") + "%' ";
		}
		sql += " ORDER BY idx DESC";
		
		/*
		 RowMapper가 select를 통해 얻어옫 ResultSet을 DTO객체에 
		 저장하고, List컬렉션에 적재하여 반환한다. 그러므로 DAO에서
		 개발자가 반복적으로 하던 작업을 자동으로 처리해준다. 
		 */
		return (ArrayList<SpringBbsDTO>)template.query(sql,
				new BeanPropertyRowMapper<SpringBbsDTO>(SpringBbsDTO.class));
	}
	
	//글쓰기 처리1
	public int write(final SpringBbsDTO springBbsDTO) {
		//작성된 폼 값을 저장한 DTO객체를 매개변수로 전달 받음
		/*
		 매개변수로 전달된 값을 익명 클래스 내에서 사용할 때는
		 반드시 final로 선언하여 값이 변경 불가능하게 처리해야 한다.
		 final로 선언하지 않으면 에러가 발생한다. 이것은 Java의 규칙이다.
		 */
		int result = template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				
				/*
				 하나의 쿼리문 내에서 nextval을 여러 번 사용하더라도 항상
				 같은 시퀀스를 반환한다. 
				 */
				String sql = "INSERT INTO springboard ( "
						+ " idx, name, title, contents, hits, "
						+ " bgroup, bstep, bindent, pass) "
						+ " VALUES( "
						+ " springboard_seq.NEXTVAL, ?, ?, ?, 0, "
						+ " springboard_seq.NEXTVAL, 0, 0, ?)";
				
				PreparedStatement psmt =
						con.prepareStatement(sql);
				psmt.setString(1, springBbsDTO.getName());
				psmt.setString(2, springBbsDTO.getTitle());
				psmt.setString(3, springBbsDTO.getContents());
				psmt.setString(4, springBbsDTO.getPass());
				
				return psmt;
			}
		});
		return result;
	}

	//게시물 조회수 증가
	public void updateHit(final String idx) {
		//쿼리문 작성
		String sql = "UPDATE springboard SET "
				+ " hits = hits+1 "
				+ " WHERE idx=?";
		
		/*
		 행의 변화를 주는 쿼리문 실행이므로 update메서드를 사용한다.
		 첫번째 인자는 쿼리문, 두번째 인자는 익명 클래스를 통해 인파라미터를
		 설정한다.  
		 */
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(idx));
				
			}
		});
	}
	
	//게시물 상세보기
	public SpringBbsDTO view(String idx) {
		
		//조회수 증가를 위한 메서드 호출
		updateHit(idx);
		
		SpringBbsDTO dto = new SpringBbsDTO();
		
		String sql = "SELECT * FROM springboard WHERE idx="+idx;
		
		try { 
			/*
			 queryForObject() 메서드는 쿼리문을 실행한 후 반드시
			 하나의 결과를 반환해야 한다. 그렇지 않으면 에러가 
			 발생하게되므로 예외처리를 하는 것이 좋다. 
			 */
			dto = template.queryForObject(sql, 
					new BeanPropertyRowMapper<SpringBbsDTO>(SpringBbsDTO.class));
			/*
			 BeanPropertyRowMapper클래스는 쿼리의 실행 결과를
			 DTO에 저장해주는 역할을 한다. 이 때 테이블의 컬럼명과 
			 DTO의 멤버변수명이 일치해야 한다. 
			 */
		}
		catch (Exception e) {
			System.out.println("View()실행 시 예외 발생");
		}
		return dto;
	}
	
	public int password(String idx, String pass) {
		
		int retNum=0;
		String sql = "SELECT * FROM springboard WHERE pass='"+pass+"' AND idx="+idx;
		
		/*
		 일련번호와 패스워드가 일치하는 게시물이 있는 경우 정상 처리 되고,
		 만약 일치하는 게시물이 없으면 예외가 발생한다.
		 queryForObject()메서드는 반드시 하나의 결과가 나와야하고, 그렇지
		 못한 경우 예외를 발생시키기 때문이다.
		 */
		try {
			SpringBbsDTO dto = template.queryForObject(sql, 
					new BeanPropertyRowMapper<SpringBbsDTO>(SpringBbsDTO.class));
			
			/*
			 일련번호는 시퀀스를 사용하므로 반드시 1이상의 값을 가지게 된다.
			 따라서 0이 반환된다면 패스워드 검증 실패로 판단할 수 있다.  
			 */
			retNum = dto.getIdx();
		}
		catch (Exception e) {
			System.out.println("password() 예외 발생");
		}
		return retNum;
	}
	
	//수정 처리 
	public void edit(final SpringBbsDTO dto) {
		String sql = "UPDATE springboard "
				+ " SET name=?, title=?, contents=? "
				+ " WHERE idx=? AND pass=?";
		
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, dto.getName());
				ps.setString(2, dto.getTitle());
				ps.setString(3, dto.getContents());
				ps.setInt(4, dto.getIdx());
				ps.setString(5, dto.getPass());
				
			}
		});
	}
	
	//삭제 처리
	public void delete(final String idx, final String pass) {
		String sql = "DELETE FROM springboard WHERE idx=? AND pass=?";
		
		template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				
				ps.setString(1, idx);
				ps.setString(2, pass);
			}
		});
	}
	
	public ArrayList<SpringBbsDTO> listPage(Map<String, Object> map){
	
		int start = Integer.parseInt(map.get("start").toString());
		int end = Integer.parseInt(map.get("end").toString());
	
		String sql = ""
				+"SELECT * FROM ("
				+"    SELECT Tb.*, rownum rNum FROM ("
				+"        SELECT * FROM springboard ";				
			if(map.get("Word")!=null){
				sql +=" WHERE "+map.get("Column")+" "
					+ " LIKE '%"+map.get("Word")+"%' ";				
			}			
			sql += " ORDER BY idx DESC"
			+"    ) Tb"
			+")"
			+" WHERE rNum BETWEEN "+start+" and "+end;
		System.out.println(sql);
		return (ArrayList<SpringBbsDTO>)template.query(sql, 
				new BeanPropertyRowMapper<SpringBbsDTO>(SpringBbsDTO.class));	
	}

}
