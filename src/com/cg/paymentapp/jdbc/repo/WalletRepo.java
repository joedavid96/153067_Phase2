package com.cg.paymentapp.jdbc.repo;
import java.sql.SQLException;
import java.util.List;
import com.cg.paymentapp.jdbc.beans.Customer;
public interface WalletRepo {
	public boolean save(Customer customer) throws SQLException;
	public Customer findOne(String mobileNo) throws SQLException;
	public boolean saveTransactions(String mobileNo, String log) throws SQLException;
	public List<String> getList(String mobileNo) throws SQLException;
}
