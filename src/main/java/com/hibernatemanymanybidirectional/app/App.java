package com.hibernatemanymanybidirectional.app;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;

import com.hibernatemanymanybidirectional.pojo.Author;
import com.hibernatemanymanybidirectional.pojo.Book;
import com.hibernatemanymanybidirectional.util.HibernateUtil;

public class App {

	public static void main(String[] args) {
		System.out
				.println(" ****** Hibernate One-Many Unidirectional - Foreignkey (Annotation) *** START **** ");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Set<Author> authors = new HashSet<Author>();
		Set<Book> books = new HashSet<Book>();

		Author author1 = new Author();
		author1.setAuthorName("rod johnson");
		authors.add(author1);
		Author author2 = new Author();
		author2.setAuthorName("Gavin King");
		authors.add(author2);
		
		Book book1 = new Book();
		book1.setBookName("Programming with Spring");
		Book book2 = new Book();
		book2.setBookName("Programming with Hibernate");
		books.add(book1);
		books.add(book2);

		author1.setBooks(books);
		author2.setBooks(books);

		book1.setAuthors(authors);
		book2.setAuthors(authors);
		
		session.save(author1);
		session.save(author2);
		session.getTransaction().commit();
		session.close();
		System.out
				.println(" ****** Hibernate One-Many Unidirectional - Foreignkey (Annotation) *** END **** ");

	}

}
