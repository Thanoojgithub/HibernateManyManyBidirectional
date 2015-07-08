package com.hibernatemanymanybidirectional.app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
		doHQLCreateQuery(session, "Programming with Spring");
		doCriteria(session, "Programming with Spring");
		doSQLQuery(session, "Programming with Spring");
		session.close();
		System.out
				.println(" ****** Hibernate One-Many Unidirectional - Foreignkey (Annotation) *** END **** ");

	}

	public static void doHQLCreateQuery(Session session, String bookName) {
		Query querySelect = session
				.createQuery("from Book where bookName = :bookName ");
		querySelect.setParameter("bookName", bookName);
		List<Book> books = (List<Book>) querySelect.list();
		for (Book book : books) {
			System.out.println(book);
			Query queryUpdate = session
					.createQuery("update Book set bookName = :bookName where bookId = :bookId");
			queryUpdate.setParameter("bookName", book.getBookName());
			queryUpdate.setParameter("bookId", book.getBookId());
			queryUpdate.executeUpdate();
		}

	}

	public static void doCriteria(Session session, String bookName) {

		Criteria criteria = session.createCriteria(Book.class);
		if (bookName != null) {
			criteria.add(Restrictions.like("bookName", bookName));
		}
		criteria.addOrder(Order.asc("bookId"));
		List<Book> books = (List<Book>) criteria.list();
		for (Book book : books) {
			System.out.println(book);
		}
	}

	private static void doSQLQuery(Session session, String bookName) {
		Query query = session
				.createSQLQuery(
						"select * from mydb.BOOK b where b.B_NAME = :bookName")
				.addEntity(Book.class).setParameter("bookName", bookName);
		List<Book> result = (List<Book>) query.list();
		for (Book book : result) {
			System.out.println(book);
		}
	}

}
