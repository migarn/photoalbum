import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		main.run(main);		
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}
	
	private void run(Main main) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.println("\n-------------------------\n\nStep 1: entering data:\n\n-------------------------\n");
		
		User user1 = new User();
		user1.setUserName("Andrew13");
		try {
			user1.setJoinDate(dateFormat.parse("2017-06-15"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		User user2 = new User();
		user2.setUserName("Paulina_1989");
		try {
			user2.setJoinDate(dateFormat.parse("2018-02-20"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		User user3 = new User();
		user3.setUserName("grzesiu1");
		try {
			user3.setJoinDate(dateFormat.parse("2019-05-05"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Album album1 = new Album();
		album1.setName("Rodzina");
		album1.setDescription("Zdjêcia mojej rodziny");
		
		Album album2 = new Album();
		album2.setName("Znajomi");
		album2.setDescription("Zdjêcia moich znajomych");
		
		Album album3 = new Album();
		album3.setName("Ró¿ne");
		album3.setDescription("Ró¿ne fotografie");
		
		Album album4 = new Album();
		album4.setName("Wakacje");
		album4.setDescription("Zdjêcia z wakacji");
		
		Photo photo1 = new Photo();
		photo1.setName("Mama");
		try {
			photo1.setDate(dateFormat.parse("2020-02-21"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Photo photo2 = new Photo();
		photo2.setName("¯ona");
		try {
			photo2.setDate(dateFormat.parse("2020-04-19"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Photo photo3 = new Photo();
		photo3.setName("Kumple");
		try {
			photo3.setDate(dateFormat.parse("2019-08-15"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Photo photo4 = new Photo();
		photo4.setName("Kotek");
		try {
			photo4.setDate(dateFormat.parse("2018-09-30"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Photo photo5 = new Photo();
		photo5.setName("Z³ota pla¿a");
		try {
			photo5.setDate(dateFormat.parse("2019-07-15"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Photo photo6 = new Photo();
		photo6.setName("Opalanie");
		try {
			photo6.setDate(dateFormat.parse("2019-07-16"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		album1.addPhoto(photo1);
		album1.addPhoto(photo2);
		album2.addPhoto(photo3);
		album3.addPhoto(photo4);
		album4.addPhoto(photo5);
		album4.addPhoto(photo6);
		
		user1.addAlbum(album1);
		user1.addAlbum(album2);
		user2.addAlbum(album3);
		user3.addAlbum(album4);
		
		user1.likePhoto(photo1);
		user1.likePhoto(photo2);
		user1.likePhoto(photo6);
		user2.likePhoto(photo2);
		user3.likePhoto(photo2);
		user3.likePhoto(photo3);
		user3.likePhoto(photo4);
		user3.likePhoto(photo5);
		user3.likePhoto(photo6);

		Transaction transaction = session.beginTransaction();
		session.save(user1);
		session.save(user2);
		session.save(user3);
		transaction.commit();
		
		main.printData();
		
		System.out.println("\n-------------------------\n\nStep 2: unliking photos:\n\n-------------------------\n");
		
		user1.unlikePhoto(photo2);
		user3.unlikePhoto(photo6);
		
		transaction = session.beginTransaction();
		session.save(user1);
		session.save(user2);
		session.save(user3);
		transaction.commit();
		
		main.printData();
	}
	
	private void printData() {
		Criteria crit = session.createCriteria(User.class);
		List<User> users = crit.list();
		System.out.println("Photoalbum users:");
		for (User user : users) {
			System.out.println("\n" + user);
			for (Album album : user.getAlbums()) {
				System.out.println("  - " + album);
				for (Photo photo : album.getPhotos()) {
					System.out.println("    - " + photo);
				}
			}
			System.out.println("  Liked photos:");
			for (Photo photo : user.getLikedPhotos()) {
				System.out.println("    - " + photo.getName());
			}
		}
	}
}
