import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Session;

public class Main {

	Session session;
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
		Main main = new Main();
		main.addNewData();
		
		

		
		
		
		// tu wstaw kod aplikacji
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
		user1.setUserName("jan_kowalski_1995");
		try {
			user1.setJoinDate(dateFormat.parse("12/12/1995"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
