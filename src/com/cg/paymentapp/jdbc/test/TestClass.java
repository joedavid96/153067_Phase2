package com.cg.paymentapp.jdbc.test;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cg.paymentapp.jdbc.beans.Customer;
import com.cg.paymentapp.jdbc.beans.Wallet;
import com.cg.paymentapp.jdbc.exception.InsufficientBalanceException;
import com.cg.paymentapp.jdbc.exception.InvalidInputException;
import com.cg.paymentapp.jdbc.service.WalletService;
import com.cg.paymentapp.jdbc.service.WalletServiceImpl;
@SuppressWarnings("unused")
public class TestClass {
	static WalletService service;
	@Before
	public void initData() {
//		Map<String, Customer> data = new HashMap<String, Customer>();
//		Map<String, List<String>> data1 = new HashMap<String, List<String>>();
//		List<String> data3 = new ArrayList<String>();
//		data3.add("Account Opened");
//		data1.put("7299779722", data3);
//		Customer cust1 = new Customer("Joel", "7299779722", new Wallet(new BigDecimal(8989)));
//		Customer cust2 = new Customer("Josna", "9952769898", new Wallet(new BigDecimal(4000)));
//		Customer cust3 = new Customer("Max", "9887654321", new Wallet(new BigDecimal(4000)));
//		data.put("7299779722", cust1);
//		data.put("9952769898", cust2);
//		data.put("9887654321", cust3);
//		service = new WalletServiceImpl(data);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		service = new WalletServiceImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		service = null;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_1() throws InvalidInputException, SQLException {
		service.createAccount(null, null, null);
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_2() throws InvalidInputException, SQLException {
		service.createAccount(null, "", null);
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_3() throws InvalidInputException, SQLException {
		service.createAccount("", "", null);
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_4() throws InvalidInputException, SQLException {
		service.createAccount("12fgfgsd", "", new BigDecimal(0));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_5() throws InvalidInputException, SQLException {
		service.createAccount(null, "7299779722", new BigDecimal(8989));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_6() throws InvalidInputException, SQLException {
		service.createAccount("", "", new BigDecimal(8989));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_7() throws InvalidInputException, SQLException {
		service.createAccount("Max", "9887654321000", new BigDecimal(0));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_8() throws InvalidInputException, SQLException {
		service.createAccount("Max", "", new BigDecimal(-9000));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_9() throws InvalidInputException, SQLException {
		service.createAccount("eric", "", new BigDecimal(4000));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_10() throws InvalidInputException, SQLException {
		service.createAccount(null, "", new BigDecimal(0));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_11() throws InvalidInputException, SQLException {
		service.createAccount("", "", new BigDecimal(4000));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_12() throws InvalidInputException, SQLException {
		service.createAccount("", "7299779722", new BigDecimal(8989));
	}

	@Test(expected = InvalidInputException.class)
	public void testCreateAccount_13() throws InvalidInputException, SQLException {
		service.createAccount("Joel", "7299779722", new BigDecimal(-8989));
	}

	@Test(expected = InvalidInputException.class)
	public void testShowBalance_1() throws InvalidInputException, SQLException {
		service.showBalance("");
	}

	@Test(expected = InvalidInputException.class)
	public void testShowBalance_2() throws InvalidInputException, SQLException {
		service.showBalance("102301928301298");
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer_1() throws InvalidInputException, InsufficientBalanceException, SQLException {
		service.fundTransfer("", "", new BigDecimal(7000));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer_2() throws InvalidInputException, InsufficientBalanceException, SQLException {
		service.fundTransfer("", "9887654321", new BigDecimal(6000));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer_3() throws InvalidInputException, InsufficientBalanceException, SQLException {
		service.fundTransfer("98887654321", "", new BigDecimal(0));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer_4() throws InvalidInputException, InsufficientBalanceException, SQLException {
		service.fundTransfer("7299779722", "119843738248", new BigDecimal(7000));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer_5() throws InvalidInputException, InsufficientBalanceException, SQLException {
		service.fundTransfer("119843738248", "98887654321", new BigDecimal(0));
	}

	@Test(expected = InvalidInputException.class)
	public void testFundTransfer_6() throws InvalidInputException, InsufficientBalanceException, SQLException {
		service.fundTransfer("3412k3jb490412", "1938749817-13", new BigDecimal(7000));
	}

	@Test(expected = InvalidInputException.class)
	public void testDeposit_1() throws InvalidInputException, SQLException {
		service.depositAmount("", new BigDecimal(2000));
	}

	@Test(expected = InvalidInputException.class)
	public void testDeposit_2() throws InvalidInputException, SQLException {
		service.depositAmount("149873194329749", new BigDecimal(2000));
	}

	@Test(expected = InvalidInputException.class)
	public void testDeposit_3() throws InvalidInputException, SQLException {
		service.depositAmount("149873194329749", new BigDecimal(-9876));
	}

	@Test(expected = InvalidInputException.class)
	public void testDeposit_4() throws InvalidInputException, SQLException {
		service.depositAmount("", null);
	}

	@Test(expected = InvalidInputException.class)
	public void testDeposit_5() throws InvalidInputException, SQLException {
		service.depositAmount("9887654321", null);
	}

	@Test(expected = InvalidInputException.class)
	public void testDeposit_6() throws InvalidInputException, SQLException {
		service.depositAmount("9887654321", new BigDecimal(-9876));
	}

	@Test(expected = InvalidInputException.class)
	public void testWithdraw_1() throws InvalidInputException, InsufficientBalanceException, SQLException {
		service.withdrawAmount("", new BigDecimal(2000));
	}

	@Test(expected = InvalidInputException.class)
	public void testWithdraw_2() throws InvalidInputException, InsufficientBalanceException, SQLException {
		service.withdrawAmount("01293812938932", new BigDecimal(2000));
	}

	@Test(expected = InvalidInputException.class)
	public void testList_1() throws InvalidInputException, SQLException {
		service.transactions("");
	}

	@Test(expected = InvalidInputException.class)
	public void testList_2() throws InvalidInputException, SQLException {
		service.transactions("123879824018743");
	}

	@After
	public void testAfter() {
		service = null;
	}
}
