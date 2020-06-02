import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "PR_KEY")
	private String userName;
	
	@Column
	private Date joinDate;
	
	@OneToMany(cascade=CascadeType.ALL)
	//@JoinColumn(name="school_id")
	private Set<Album> albums = new HashSet<Album>();
	
	@ManyToMany
	private Set<User> friends = new HashSet<User>();
	
	@ManyToMany
	private Set<Photo> likedPhotos = new HashSet<Photo>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getJoinDate() {
		return this.joinDate;
	}

	public Set<Album> getAlbums() {
		return this.albums;
	}

	public Set<User> getFriends() {
		return this.friends;
	}

	public Set<Photo> getLikedPhotos() {
		return this.likedPhotos;
	}
	
	public void addAlbum(Album album) {
		this.albums.add(album);
	}
	
	public void removeAlbum(Album album) {
		this.albums.remove(album);
	}
	
	public void addFriend(User user) {
		this.friends.add(user);
		user.getFriends().add(this);
	}
	
	public void removeFriend(User user) {
		this.friends.remove(user);
		user.getFriends().remove(this);
	}
	
	public void likePhoto(Photo photo) {
		this.likedPhotos.add(photo);
		photo.getLikingUsers.add(this);
	}
	
	public void unlikePhoto(Photo photo) {
		this.likedPhotos.remove(photo);
		photo.getLikingUsers.remove(this);
	}
}
