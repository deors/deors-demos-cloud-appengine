package deors.demos.cloud.appengine.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Thought implements Serializable {

    private static final long serialVersionUID = -5579390958742578458L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    private String poster;

    private String target;

    private String message;

    private Date postDate;

    public Thought() {
        super();
    }

    public Thought(String poster, String target, String message, Date postDate) {
        this();
        setPoster(poster);
        setTarget(target);
        setMessage(message);
        setPostDate(postDate);
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("from ");
        sb.append(poster);
        if (target != null && target.length() != 0) {
            sb.append(" to ");
            sb.append(target);
        }
        sb.append(": ");
        sb.append(message);
        sb.append(" (");
        sb.append(postDate);
        sb.append(')');
        return sb.toString();
    }
}
