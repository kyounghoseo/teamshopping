package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BuyDBBean {

	private static BuyDBBean instance = new BuyDBBean();

	public static BuyDBBean getInstance() {
		return instance;
	}

	private BuyDBBean() {

	}

	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/Oracle11g");
		return ds.getConnection();
	}

	// bank 테이블에 있는 전체 레코드를 얻어내는 메소드
	public List<String> getAccount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> accountList = null;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("select * from bank");
			rs = pstmt.executeQuery();

			accountList = new ArrayList<String>();

			while (rs.next()) {
				String account = new String(
						rs.getString("account") + " " + rs.getString("bank") + " " + rs.getString("name"));
				accountList.add(account);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		return accountList;
	}

	// 구매 테이블인 buy에 구매 목록 등록
	@SuppressWarnings("resource")
	public void insertBuy(List<CartDataBean> lists, String id, String account, String deliveryName, String deliveryTel,
			String deliveryAddress) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Timestamp reg_date = null;
		String sql = "";
		String maxDate = "";
		String number = "";
		String todayDate = "";
		String compareDate = "";
		long buyId = 0;
		int nowCount;

		try {
			conn = getConnection();
			reg_date = new Timestamp(System.currentTimeMillis());
			todayDate = reg_date.toString();
			compareDate = todayDate.substring(0, 4) + todayDate.substring(5, 7) + todayDate.substring(8, 10);

			pstmt = conn.prepareStatement("select max(buy_id) from buy");
			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getLong(1) > 0) {
				Long val = new Long(rs.getLong(1));
				maxDate = val.toString().substring(0, 8);
				number = val.toString().substring(8);
				if (compareDate.equals(maxDate)) {
					if ((Integer.parseInt(number) + 1) < 10000)
						buyId = Long.parseLong(maxDate + (Integer.parseInt(number) + 1 + 10000));
					else
						buyId = Long.parseLong(maxDate + (Integer.parseInt(number) + 1));
				} else {
					compareDate += "00001";
					buyId = Long.parseLong(compareDate);
				}
			} else {
				compareDate += "00001";
				buyId = Long.parseLong(compareDate);
			}

			// 하나의 트랙잭션으로 처리
			conn.setAutoCommit(false);
			for (int i = 0; i < lists.size(); i++) {
				// 해당 아이디에 대한 cart 테이블의 레코드를 가져온 후 buy 테이블에 추가
				CartDataBean cart = lists.get(i);

				sql = "insert into buy(buy_id,buyer,book_id,book_title,buy_price,buy_count,book_image,"
						+ "buy_date,account,deliveryName,deliveryTel,deliveryAddress)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, buyId);
				pstmt.setString(2, id);
				pstmt.setInt(3, cart.getBook_id());
				pstmt.setString(4, cart.getBook_title());
				pstmt.setInt(5, cart.getBuy_price());
				pstmt.setInt(6, cart.getBuy_count());
				pstmt.setString(7, cart.getBook_image());
				pstmt.setTimestamp(8, reg_date);
				pstmt.setString(9, account);
				pstmt.setString(10, deliveryName);
				pstmt.setString(11, deliveryTel);
				pstmt.setString(12, deliveryAddress);
				pstmt.executeUpdate();

				// 상품이 구매되었으므로 book 테이블의 상품 수량을 재조정함
				pstmt = conn.prepareStatement("select book_count from book where book_id=?");
				pstmt.setInt(1, cart.getBook_id());
				rs = pstmt.executeQuery();
				rs.next();

				nowCount = rs.getInt(1) - 1;

				sql = "update book set book_count =? where book_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, nowCount);
				pstmt.setInt(2, cart.getBook_id());

				pstmt.executeUpdate();

			}

			pstmt = conn.prepareStatement("delete from cart where buyer=?");
			pstmt.setString(1, id);

			pstmt.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	// id에 해당하는 buy테이블의 레코드 수를 얻어내는 메소드
	public int getListCount(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("select count(*) from buy where buyer=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return x;
	}

	// buy테이블의 전체 레코드 수를 얻어내는 메소드
	public int getListCount() throws Exception {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement("select count(*) from buy");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return x;

	}

	// id에 해당하는 buy 테이블의 구매목록을 얻어내는 메소드
	public List<BuyDataBean> getBuyList(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BuyDataBean buy = null;
		String sql = "";
		List<BuyDataBean> lists = null;

		try {
			conn = getConnection();

			sql = "select * from buy where buyer=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			lists = new ArrayList<BuyDataBean>();

			while (rs.next()) {
				buy = new BuyDataBean();

				buy.setBuy_id(rs.getLong("buy_id"));
				buy.setBook_id(rs.getInt("book_id"));
				buy.setBook_title(rs.getString("book_title"));
				buy.setBuy_price(rs.getInt("buy_price"));
				buy.setBuy_count(rs.getInt("buy_count"));
				buy.setBook_image(rs.getString("book_image"));
				buy.setSanction(rs.getString("sanction"));

				lists.add(buy);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return lists;
	}

	// buy 테이블의 전체 목록을 얻어내는 메소드
	public List<BuyDataBean> getBuyList() throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BuyDataBean buy = null;
		String sql = "";
		List<BuyDataBean> lists = null;
		
		
		
		try{
			conn = getConnection();
			
			sql = "select * from buy";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			lists = new ArrayList<BuyDataBean>();
			
			while(rs.next()){
				buy = new BuyDataBean();
				
				
				buy.setBuy_id(rs.getLong("buy_id"));
				buy.setBuyer(rs.getString("buyer"));
				buy.setBook_id(rs.getInt("book_id"));
				buy.setBook_title(rs.getString("book_title"));
				buy.setBuy_price(rs.getInt("buy_price"));
				buy.setBuy_count(rs.getInt("buy_count"));
				buy.setBook_image(rs.getString("book_image"));
				buy.setBuy_date(rs.getTimestamp("buy_date"));
				buy.setAccount(rs.getString("account"));
				buy.setDeliveryName(rs.getString("deliveryName"));
				buy.setDeliveryTel(rs.getString("deliveryTel"));
				buy.setDeliveryAddress(rs.getString("deliveryAddress"));
				buy.setSanction(rs.getString("sanction"));
				
				lists.add(buy);
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
				
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return lists;
		
		
	}

}
