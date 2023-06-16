package hiber.dao;
import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      System.out.println(user.getCar());
      Session session = sessionFactory.getCurrentSession();
      if (user.getCar() != null) {
         session.save(user.getCar());

         session.save(user);
      }
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      Query query = sessionFactory.getCurrentSession().createQuery("from Car where model= :modelName and series = :seriesNum");
      query.setParameter("modelName", model);
      query.setParameter("seriesNum", series);
      Car car = (Car) query.getSingleResult();
      if (car == null)
         return null;

      return car.getUser();
   }
}
