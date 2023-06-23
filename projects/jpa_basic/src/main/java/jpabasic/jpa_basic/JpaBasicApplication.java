package jpabasic.jpa_basic;

import domain.Album;
import domain.Movie;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootApplication
public class JpaBasicApplication {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Movie movie = new Movie();
			movie.setDirector("aaa");
			movie.setActor("bbb");
			movie.setName("바람과 함께 사라지다");
			movie.setPrice(10000);

			em.persist(movie);

			Album album = new Album();
			album.setName("asd");
			album.setArtist("asdfa");
			album.setPrice(20000);

			em.persist(album);



			em.flush();
			em.clear();

			em.find(Movie.class, movie.getId());
			System.out.println("movie = " + movie);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

}
