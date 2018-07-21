package com.cg.paymentapp.jdbc.repo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import com.cg.paymentapp.jdbc.beans.Customer;
import com.cg.paymentapp.jdbc.beans.Wallet;
public class WalletRepoImpl implements WalletRepo {

	Connection con;

	public WalletRepoImpl() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe ", "hr", "hr");
		con.setAutoCommit(true);
	}
	@Override
	public boolean save(Customer customer) throws SQLException {
		PreparedStatement p1, p2, p3, p4;
		p1 = con.prepareStatement("select count(*) from customer where mobileno = ? ");
		p1.setString(1, customer.getMobileNo());
		ResultSet r = p1.executeQuery();
		r.next();
		int i = r.getInt(1);
		boolean flag = false;
		if (i == 0) {
			p2 = con.prepareStatement("insert into customer values (?,?)");
			p2.setString(1, customer.getMobileNo());
			p2.setString(2, customer.getName());
			p3 = con.prepareStatement("insert into wallet values (?,?)");
			p3.setString(1, customer.getMobileNo());
			p3.setBigDecimal(2, customer.getWallet().getBalance());
			p4 = con.prepareStatement("insert into transaction values (?,?)");
			String init_log = new java.util.Date() + " : Account Created. Balance in A/C : "
					+ customer.getWallet().getBalance();
			p4.setString(1, customer.getMobileNo());
			p4.setString(2, init_log);
			if (!(p2.execute()) & !(p3.execute()) & !(p4.execute())) {
				flag = true;
			} else {
				p2.close();
				p3.close();
				p4.close();
				return false;
			}
			p2.close();
			p3.close();
			p4.close();
		} else if (i == 1) {
			p2 = con.prepareStatement("update wallet set balance = ? where mobileno = ? ");
			p2.setString(2, customer.getMobileNo());
			p2.setBigDecimal(1, customer.getWallet().getBalance());
			if (p2.executeUpdate() == 1) {
				flag = true;
			} else {
				p2.close();
				return false;
			}
			p2.close();
		}
		p1.close();
		return flag;
	}
	@Override
	public Customer findOne(String mobileNo) throws SQLException {
		PreparedStatement p1, p2;
		Customer c = new Customer();
		Wallet w = new Wallet();
		p1 = con.prepareStatement("select * from customer where mobileno = ?");
		p1.setString(1, mobileNo);
		ResultSet r1 = p1.executeQuery();
		while (r1.next()) {
			c.setMobileNo(r1.getString("mobileno"));
			c.setName(r1.getString("name"));
		}
		if (c.getMobileNo() != null) {
			p2 = con.prepareStatement("select * from wallet where mobileno = ?");
			p2.setString(1, mobileNo);
			ResultSet r2 = p2.executeQuery();
			while (r2.next()) {
				w.setBalance(r2.getBigDecimal("balance"));
				c.setWallet(w);
			}
			p1.close();
			p2.close();
			return c;
		}
		else {
			return null;
		}
	}
	@Override
	public boolean saveTransactions(String mobileNo, String log) throws SQLException {
		PreparedStatement p = con.prepareStatement("insert into transaction values (?,?)");
		p.setString(1, mobileNo);
		p.setString(2, log);
		if (!p.execute()) {
			p.close();
			return true;
		} else {
			p.close();
			return false;
		}
	}
	@Override
	public List<String> getList(String mobileNo) throws SQLException {
		PreparedStatement p = con.prepareStatement("select * from transaction where mobileno = ?");
		p.setString(1, mobileNo);
		ResultSet r = p.executeQuery();
		if (r != null) {
			List<String> l = new LinkedList<String>();
			while (r.next()) {
				l.add(r.getString("log"));
			}
			p.close();
			return l;
		} else {
			p.close();
			return null;
		}
	}
}
