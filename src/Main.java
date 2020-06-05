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
	User user1 = new User();
	User user2 = new User();
	User user3 = new User();
	Album album1 = new Album();
	Album album2 = new Album();
	Album album3 = new Album();
	Album album4 = new Album();
	Photo photo1 = new Photo();
	Photo photo2 = new Photo();
	Photo photo3 = new Photo();
	Photo photo4 = new Photo();
	Photo photo5 = new Photo();
	Photo photo6 = new Photo();

	public static void main(String[] args) {
		
		Main main = new Main();
		System.out.println("\n-------------------------\n\nStep 1: entering data:\n\n-------------------------\n");
		main.setData();
		main.printData();
		System.out.println("\n-------------------------\n\nStep 2: unliking photos:\n\n-------------------------\n");
		main.unlikePhotos();
		main.printData();
		System.out.println("\n-------------------------\n\nStep 3: removing a photo:\n\n-------------------------\n");
		main.removePhoto();
		main.close();
		// Nie wiem dlaczego, ale kiedy wo³a³em printData() bezpoœrednio po  removePhoto()
		// drukowa³y siê nieaktualne dane. ¯eby temu zapobiec jeszcze raz wywo³a³em Main().
		main = new Main();
		main.printData();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}
	
	private void setData() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		user1.setUserName("Andrew13");
		try {
			user1.setJoinDate(dateFormat.parse("2017-06-15"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		user2.setUserName("Paulina_1989");
		try {
			user2.setJoinDate(dateFormat.parse("2018-02-20"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		user3.setUserName("grzesiu1");
		try {
			user3.setJoinDate(dateFormat.parse("2019-05-05"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		album1.setName("Rodzina");
		album1.setDescription("Zdjêcia mojej rodziny");
		
		album2.setName("Znajomi");
		album2.setDescription("Zdjêcia moich znajomych");
		
		album3.setName("Ró¿ne");
		album3.setDescription("Ró¿ne fotografie");
		
		album4.setName("Wakacje");
		album4.setDescription("Zdjêcia z wakacji");
		
		photo1.setName("Mama");
		try {
			photo1.setDate(dateFormat.parse("2020-02-21"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		photo2.setName("¯ona");
		try {
			photo2.setDate(dateFormat.parse("2020-04-19"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		photo3.setName("Kumple");
		try {
			photo3.setDate(dateFormat.parse("2019-08-15"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		photo4.setName("Kotek");
		try {
			photo4.setDate(dateFormat.parse("2018-09-30"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		photo5.setName("Z³ota pla¿a");
		try {
			photo5.setDate(dateFormat.parse("2019-07-15"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
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
	}
	
	private void unlikePhotos() {
		user1.unlikePhoto(photo2);
		user3.unlikePhoto(photo6);
		Transaction transaction = session.beginTransaction();
		session.save(user1);
		session.save(user3);
		transaction.commit();
	}
	
	private void removePhoto() {
		album1.removePhoto(photo2);
		Transaction transaction = session.beginTransaction();
		session.delete(photo2);
		session.save(user1);
//		session.save(user2);
//		session.save(user3);
		transaction.commit();	
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
