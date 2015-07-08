package com.hibernatemanymanybidirectional.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({ @NamedQuery(name = "findBookByBookName", query = "from Book b where b.bookName = :bookName") })
@Entity
@Table(name = "BOOK", schema = "MYDB")
public class Book implements Serializable {

	private static final long serialVersionUID = 7954616237593749033L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BID", unique = true, nullable = false)
	private Long bookId;

	@Column(name = "B_NAME", unique = true, nullable = false)
	private String bookName;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mydb.Author_Book", joinColumns = @JoinColumn(name = "BID"), inverseJoinColumns = @JoinColumn(name = "AID"))
	private Set<Author> authors;

	public Book() {
	}

	public Book(String bookName) {
		super();
		this.bookName = bookName;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "Book [bookId=" + bookId + ", bookName=" + bookName
				+ ", authors="
				+ (authors != null ? toString(authors, maxLen) : null) + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

}
