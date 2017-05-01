package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import work.crypt.BCrypt;
import work.crypt.SHA256;

public class MngrDBBean {//관리자 인증, 상품관리, 구매관리 부분에서 사용하는 DB
	// MngrDBBean 전역 객체 생성<- 한 개의 객체만 생성해서 공유
	private static MngrDBBean instance = new MngrDBBean();

	// MngrDBBean 객체를 리턴하는 메소드
	public static MngrDBBean getInstance() {
		return instance;
	}

	private MngrDBBean() {}

	// 커넥션 풀에서 커넥션 객체를 얻어내는 메소드
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/jsptest");
		return ds.getConnection();
	}// getConnection끝

	// 관리자 인증 메소드
	public int userCheck(String id, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;

		SHA256 sha = SHA256.getInsatnce();
		try {
			conn = getConnection();
			System.out.println("매니저 아이디 체크"+id);
			String orgPass = passwd;
			String shaPass = sha.getSha256(orgPass.getBytes());

			pstmt = conn.prepareStatement(
			"select managerPasswd from manager where managerId = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {// 해당 아이디가 있으면 수행
				String dbpasswd = rs.getString("managerPasswd");
				if(BCrypt.checkpw(shaPass, dbpasswd)){
					x = 1; // 인증 성공
				System.out.println("매니저 로그인 패스워드 인증 성공");}
				else{
					x = 0; // 비밀번호 틀림
					System.out.println("매니저 로그인 패스워드 인증 실퍂");	
				}
			} else // 해당 아이디 없으면 수행
				x = -1; // 아이디 없음

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return x;

	}// userCheck 끝

	// 책 등록 메소드
	public void insertBook(MngrDataBean book) 
	throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "insert into book(book_id,book_kind,book_title,book_price,";
			sql += "book_count,author,publishing_com,publishing_date,book_image,";
			sql += "book_content,discount_rate,reg_date) values (book_id_seq.nextval,?,?,?,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getBook_kind());
			pstmt.setString(2, book.getBook_title());
			pstmt.setInt(3, book.getBook_price());
			pstmt.setInt(4, book.getBook_count());
			pstmt.setString(5, book.getAuthor());
			pstmt.setString(6, book.getPublishing_com());
			pstmt.setString(7, book.getPublishing_date());
			pstmt.setString(8, book.getBook_image());
			pstmt.setString(9, book.getBook_content());
			pstmt.setInt(10, book.getDiscount_rate());
			pstmt.setTimestamp(11, book.getReg_date());

			pstmt.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null)
				try { pstmt.close();} catch(SQLException ex) {}
			if (conn != null)
				try {conn.close();} catch(SQLException ex) {}
		}
	}

	//이미 등록된 책을 검증
	public int registedBookconfirm(
			String kind, String booktitle, String author) 
	throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;

		try {
			conn = getConnection();

			String sql = "select book_title from book";
			sql += "where book_kind =? and book_title =? and author =?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, booktitle);
			pstmt.setString(3, author);

			rs = pstmt.executeQuery();

			if (rs.next())
				x = 1; // 해당 책이 이미 등록되어 있음
			else
				x = -1; // 해당 책이 이미 등록되어 있지 않음

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
		}
		return x;
	}// registedBookconfirm끝

	// 전체 등록된 책의 수를 얻어내는 메소드
	public int getBookCount() 
	throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select count(*) from book");
			rs = pstmt.executeQuery();

			if (rs.next())
				x = rs.getInt(1);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return x;
	}// 전체 등록된 책의 수를 얻어내는 메소드 끝

	// 해당 분류의 책의 수를 얻어내는 메소드
	public int getBookCount(String book_kind) 
	throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;
		int kind = Integer.parseInt(book_kind);

		try {
			conn = getConnection();
			String query = "select count(*) from book where book_kind=" + kind;
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if (rs.next())
				x = rs.getInt(1);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}

		}
		return x;

	}// �ش� �з��� å�� ���� ���� �޼ҵ� ��

	// å�� ������ ��
	public String getBookTitle(int book_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String x = "";

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("select book_title from book where book_id =" + book_id);
			rs = pstmt.executeQuery();

			if (rs.next())
				x = rs.getString(1);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return x;

	}// å�� ������ �� ��

	// �з��� �Ǵ� ��ü ��ϵ� å�� ������ ���� �޼ҵ�
	public List<MngrDataBean> getBooks(String book_kind) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MngrDataBean> bookList = null;

		try {
			conn = getConnection();

			String sql1 = "select * from book";
			String sql2 = "select * from book";
			sql2 += "where book_kind = ? order by reg_date desc";

			if (book_kind.equals("all") || book_kind.equals("")) {
				pstmt = conn.prepareStatement(sql1);
			} else {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, book_kind);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				bookList = new ArrayList<MngrDataBean>();
				do {
					MngrDataBean book = new MngrDataBean();

					book.setBook_id(rs.getInt("book_id"));
					book.setBook_kind(rs.getString("book_kind"));
					book.setBook_title(rs.getString("book_title"));
					book.setBook_price(rs.getInt("book_price"));
					book.setBook_count(rs.getShort("book_count"));
					book.setAuthor(rs.getString("author"));
					book.setPublishing_com(rs.getString("publishing_com"));
					book.setPublishing_date(rs.getString("publishing_date"));
					book.setBook_image(rs.getString("book_image"));
					book.setDiscount_rate(rs.getByte("discount_rate"));
					book.setReg_date(rs.getTimestamp("reg_date"));

					bookList.add(book);

				} while (rs.next());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}

		}
		return bookList;
	}// �з��� �Ǵ� ��ü ��ϵ� å�� ������ ���� �޼ҵ� ��

	// ���θ� ���ο� ǥ���ϱ� ���ؼ� ����ϴ� �з��� �Ű�å ����� ���� �޼ҵ�
	public MngrDataBean[] getBooks(String book_kind, int count) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MngrDataBean bookList[] = null;
		int i = 0;
		try {
			conn = getConnection();

			String sql = "select * from book where book_kind = ?";
			sql += "order by reg_date desc";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book_kind);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				bookList = new MngrDataBean[count];
				do {
					MngrDataBean book = new MngrDataBean();
					book.setBook_id(rs.getInt("book_id"));
					book.setBook_kind(rs.getString("book_kind"));
					book.setBook_title(rs.getString("book_title"));
					book.setBook_price(rs.getInt("book_price"));
					book.setBook_count(rs.getShort("book_count"));
					book.setAuthor(rs.getString("author"));
					book.setPublishing_com(rs.getString("publishing_com"));
					book.setPublishing_date(rs.getString("publishing_date"));
					book.setBook_image(rs.getString("book_image"));
					book.setDiscount_rate(rs.getByte("discount_rate"));
					book.setReg_date(rs.getTimestamp("reg_date"));

					bookList[i] = book;

					i++;

				} while (rs.next());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}

		}
		return bookList;
	}// MngrDataBean[] getBooks ��

	// bookId�� �ش��ϴ� å�� ������ ���� �޼ҵ��
	// ��ϵ� å�� �����ϱ� ���� ���� ������ �о���̱� ���� �޼ҵ�

	public MngrDataBean getBook(int bookId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MngrDataBean book = null;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("select * from book where book_id = ?");
			pstmt.setInt(1, bookId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				book = new MngrDataBean();

				book.setBook_kind(rs.getString("book_kind"));
				book.setBook_title(rs.getString("book_title"));
				book.setBook_price(rs.getInt("book_price"));
				book.setBook_count(rs.getShort("book_count"));
				book.setAuthor(rs.getString("author"));
				book.setPublishing_com(rs.getString("publishing_com"));
				book.setPublishing_date(rs.getString("publishing_date"));
				book.setBook_image(rs.getString("book_image"));
				book.setBook_content(rs.getString("book_content"));
				book.setDiscount_rate(rs.getByte("discount_rate"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return book;

	}// MngrDataBean getBook ��

	// ��ϵ� å�� ������ ���� �� ����ϴ� �޼ҵ�
	public void updateBook(MngrDataBean book, int bookId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = getConnection();

			sql = "update book set book_kind=?,book_title=?,book_price=?";
			sql += ",book_count=?,author=?,publishing_com=?,publishing_date=?";
			sql += ",book_image=?,book_content=?,discount_rate=?";
			sql += "where book_id=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, book.getBook_kind());
			pstmt.setString(2, book.getBook_title());
			pstmt.setInt(3, book.getBook_price());
			pstmt.setInt(4, book.getBook_count());
			pstmt.setString(5, book.getAuthor());
			pstmt.setString(6, book.getPublishing_com());
			pstmt.setString(7, book.getPublishing_date());
			pstmt.setString(8, book.getBook_image());
			pstmt.setString(9, book.getBook_content());
			pstmt.setInt(10, book.getDiscount_rate());
			pstmt.setInt(11, bookId);

			pstmt.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
	}// updateBook ��

	// bookId�� �ش��ϴ� å�� ������ ���� �� ����ϴ� �޼ҵ�
	public void deleteBook(int bookId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("delete from book where book_id=?");
			pstmt.setInt(1, bookId);

			pstmt.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
	}

}// class��