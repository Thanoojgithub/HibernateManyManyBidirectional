package com.hibernatemanymanybidirectional.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@NamedNativeQueries({ @NamedNativeQuery(name = "findAuthorByAuthorNameNativeSQL", query = "select * from mydb.AUTHOR a where a.A_NAME = :authorName", resultClass = Author.class) })
@Entity
@Table(name = "AUTHOR", schema = "MYDB")
public class Author implements Serializable {

	private static final long serialVersionUID = 7068106332981177961L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AID", unique = true, nullable = false)
	private Long authorId;

	@Column(name = "A_NAME", unique = true, nullable = false)
	private String authorName;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "authors")
	private Set<Book> books;

	public Author() {
	}

	public Author(String authorName) {
		super();
		this.authorName = authorName;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

}
