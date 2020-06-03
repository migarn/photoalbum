import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "PR_KEY")
	private String userName;
	
	@Column
	private Date joinDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner")
	private Set<Album> albums = new HashSet<Album>();
		
	@ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
	
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Set<Album> getAlbums() {
		return this.albums;
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
	
	public void likePhoto(Photo photo) {
		this.likedPhotos.add(photo);
		photo.getLikingUsers().add(this);
	}
	
	public void unlikePhoto(Photo photo) {
		this.likedPhotos.remove(photo);
		photo.getLikingUsers().remove(this);
	}
	
	public int getPhotosNumber() {
		int number = 0;
		for (Album album : this.albums) {
			number += album.getPhotos().size();
		}
		return number;
	}
	
	@Override
	public String toString() {
		return "User \'" + getUserName() + "\'. " + getPhotosNumber() + " photos in " + getAlbums().size() + " albums.";
	}
}
