import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
		Main main = new Main();
		main.addNewData();
		main.printUsers();
		
		
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}
	
	private void addNewData() {
		User user1 = new User();
		user1.setUserName("jurek5");
		try {
			user1.setJoinDate(dateFormat.parse("12/12/1995"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Transaction transaction = session.beginTransaction();
		session.save(user1);
		transaction.commit();
	}
	
	private void printUsers() {
		Criteria crit = session.createCriteria(User.class);
		List<User> users = crit.list();
		System.out.println("Photoalbum users:");
		for (User user : users) {
			System.out.println(user);
		}
	}
}
