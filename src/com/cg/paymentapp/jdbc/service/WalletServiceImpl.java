package com.cg.paymentapp.jdbc.service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.cg.paymentapp.jdbc.beans.Wallet;
import com.cg.paymentapp.jdbc.exception.InsufficientBalanceException;
import com.cg.paymentapp.jdbc.repo.WalletRepo;
import com.cg.paymentapp.jdbc.repo.WalletRepoImpl;
import com.cg.paymentapp.jdbc.beans.Customer;
import com.cg.paymentapp.jdbc.exception.InvalidInputException;
public class WalletServiceImpl implements WalletService {
	WalletRepo repo;
	public WalletServiceImpl() throws ClassNotFoundException, SQLException {
		repo = new WalletRepoImpl();
	}
	public WalletServiceImpl(Map<String, Customer> data) {
		super();
	}
	public boolean isValidName(String name) {
		if ((name.matches("[A-Z][a-z]+")) && (name != "")) {
			return true;
		} else {
			throw new InvalidInputException(" Invalid Name ");
		}
	}
	public boolean isValidNumber(String mobileNo) {
		if ((mobileNo.matches("[4-9][0-9]{9}")) && (mobileNo != "")) {
			return true;
		} else {
			throw new InvalidInputException(" Invalid Mobile Number ");
		}
	}
	public boolean validAmount(BigDecimal amount) {
		if (amount.compareTo(new BigDecimal(0)) > 0) {
			return true;
		} else {
			throw new InvalidInputException(" Amount should be POSITIVE ");
		}
	}
	@Override
	public Customer createAccount(String name, String mobileNo, BigDecimal amount)
			throws InvalidInputException, SQLException {
		if (isValidName(name) && isValidNumber(mobileNo) && validAmount(amount)) {
			if (repo.findOne(mobileNo) != null) {
				throw new InvalidInputException(
						" : Account linked to the Entered Number ALREADY EXISTS: Please try again.\n");
			} else {
				Wallet w = new Wallet(amount);
				Customer c = new Customer(name, mobileNo, w);
				boolean flag = repo.save(c);
				if (flag) {
					return c;
				} else {
					throw new InvalidInputException(" : Try at a later time. Thank you.\n");
				}
			}

		} else {
			throw new InvalidInputException(" : INVALID Input ");
		}
	}
	@Override
	public Customer showBalance(String mobileNo) throws InvalidInputException, SQLException {
		if (isValidNumber(mobileNo)) {
			if (repo.findOne(mobileNo) == null) {
				throw new InvalidInputException(
						" : A/C with Entered Phone Number Does NOT EXIST: Please try again. \n");
			} else {
				String log = new java.util.Date() + "\tViewed Balance.";
				repo.saveTransactions(mobileNo, log);
				Customer c = repo.findOne(mobileNo);
				return c;
			}
		} else {
			throw new InvalidInputException(" : INVALID Input ");
		}
	}
	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws InvalidInputException, InsufficientBalanceException, SQLException {
		if (isValidNumber(sourceMobileNo) && isValidNumber(targetMobileNo) && validAmount(amount)) {
			if (repo.findOne(sourceMobileNo) != null) {
				if (repo.findOne(targetMobileNo) != null) {
					Customer bx = repo.findOne(sourceMobileNo);
					Customer cx = repo.findOne(targetMobileNo);
					BigDecimal b = bx.getWallet().getBalance();
					BigDecimal c = cx.getWallet().getBalance();
					int i = b.compareTo(amount);
					if (i >= 0) {
						b = b.subtract(amount);
						bx.getWallet().setBalance(b);
						String log1 = new java.util.Date() + "\tAmount of " + amount
								+ " Debited from A/C towards A/C linked with Number" + sourceMobileNo
								+ ". Balance in A/C : " + b;
						repo.saveTransactions(sourceMobileNo, log1);
						boolean flag1 = repo.save(bx);
						c = c.add(amount);
						cx.getWallet().setBalance(c);
						String log2 = new java.util.Date() + "\tAmount of " + amount
								+ " Credited to A/C from A/C linked with Number" + targetMobileNo
								+ ". Balance in A/C : " + c;
						repo.saveTransactions(targetMobileNo, log2);
						boolean flag2 = repo.save(cx);
						if (flag1 && flag2) {
							return bx;
						} else {
							throw new InvalidInputException(" : Try at a later time.");
						}
					} else {
						throw new InsufficientBalanceException(
								" : Insufficient Balance : Please Check Balance and Try Again. Thanks.\n");
					}
				} else {
					throw new InvalidInputException(" : A/C with Entered Recipient Phone Number Does NOT EXIST : \n");
				}
			} else {
				throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
			}
		} else {
			throw new InvalidInputException(" : INVALID Input ");
		}
	}
	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, SQLException {
		if (isValidNumber(mobileNo) && validAmount(amount)) {
			Customer c = repo.findOne(mobileNo);
			if (c != null) {
				BigDecimal b = c.getWallet().getBalance();
				b = b.add(amount);
				c.getWallet().setBalance(b);
				String log = new java.util.Date() + "\tAmount of " + amount + " Credited to A/C. Balance in A/C : " + b;
				repo.saveTransactions(mobileNo, log);
				boolean flag = repo.save(c);
				if (flag) {
					return c;
				} else {
					throw new InvalidInputException(" : Try at a later time.");
				}
			} else {
				throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
			}
		} else {
			throw new InvalidInputException(" : INVALID Input ");
		}
	}
	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount)
			throws InvalidInputException, InsufficientBalanceException, SQLException {
		if (isValidNumber(mobileNo) && validAmount(amount)) {
			Customer c = repo.findOne(mobileNo);
			if (c != null) {
				BigDecimal b = c.getWallet().getBalance();
				int i = b.compareTo(amount);
				if (i >= 0) {
					b = b.subtract(amount);
					c.getWallet().setBalance(b);
					String log = new java.util.Date() + "\tAmount of " + amount + " Debited from A/C. Balance in A/C : "
							+ b;
					repo.saveTransactions(mobileNo, log);
					boolean flag = repo.save(c);
					if (flag) {
						return c;
					} else {
						throw new InvalidInputException(" : Try at a later time.\n");
					}

				} else {
					throw new InsufficientBalanceException(
							" : Insufficient Balance : Please Check Balance and Try Again. Thanks.\n");
				}

			} else {
				throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
			}
		} else {
			throw new InvalidInputException(" : INVALID Input ");
		}
	}
	@Override
	public List<String> transactions(String mobileNo) throws InvalidInputException, SQLException {
		if (isValidNumber(mobileNo)) {
			if (repo.getList(mobileNo) != null) {
				return repo.getList(mobileNo);
			} else {
				throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
			}
		} else {
			throw new InvalidInputException(" : INVALID Input ");
		}
	}
}
