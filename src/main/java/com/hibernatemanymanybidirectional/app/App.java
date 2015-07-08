package com.hibernatemanymanybidirectional.app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibernatemanymanybidirectional.pojo.Author;
import com.hibernatemanymanybidirectional.pojo.Book;
import com.hibernatemanymanybidirectional.util.HibernateUtil;

public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);
	
	
	public static void main(String[] args) {
		System.out.println(" ****** Hibernate One-Many Bidirectional - Foreignkey (Annotation) *** START **** ");
		Transaction tx = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			Set<Author> authors = new HashSet<Author>();
			Set<Book> books = new HashSet<Book>();

			Author author1 = new Author();
			author1.setAuthorName("Rod johnson");
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
			tx.commit();

			queryLanguages(session);

			namedQueries(session);

			session.close();
		} catch (RuntimeException e) {
			try {
				if (tx != null) {
					tx.rollback();
				}
			} catch (RuntimeException rbe) {
				logger.error("Couldn’t roll back transaction", rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		System.out.println(" ****** Hibernate One-Many Bidirectional - Foreignkey (Annotation) *** END **** ");
	}

	private static void namedQueries(Session session) {
		Query queryNamedQuery = session.getNamedQuery("findBookByBookName")
				.setString("bookName", "Programming with Spring");
		List<Book> booksNamedQuery = (List<Book>) queryNamedQuery.list();
		for (Book book : booksNamedQuery) {
			System.out.println(book);
		}
		Query queryNamedNativeQuery = session.getNamedQuery("findAuthorByAuthorNameNativeSQL")
				.setString("authorName", "Rod johnson");
		List<Author> authorsNamedNativeQuery = (List<Author>) queryNamedNativeQuery.list();
		for (Author author : authorsNamedNativeQuery) {
			System.out.println(author);
		}
	}

	private static void queryLanguages(Session session) {
		doHQLCreateQuery(session, "Programming with Spring");
		doCriteria(session, "Programming with Spring");
		doSQLQuery(session, "Programming with Spring");
	}

	private static void doHQLCreateQuery(Session session, String bookName) {
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

	private static void doCriteria(Session session, String bookName) {

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
