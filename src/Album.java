import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "albums")
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "album_id")
	private Set<Photo> photos = new LinkedHashSet<Photo>();

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Photo> getPhotos() {
		return this.photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}
	
	public void addPhoto(Photo photo) {
		this.photos.add(photo);
	}
	
	public void removePhoto(Photo photo) {
		this.photos.remove(photo);
	}
	
	@Override
	public String toString() {
		return "Album \'" + getName() + "\': " + getDescription() + ". Number of photos: " + getPhotos().size() + ".";
	}
}
