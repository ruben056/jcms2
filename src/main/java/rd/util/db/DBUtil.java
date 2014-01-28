package rd.util.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Use threadlocal to "cache" the entityMgr instance per thread
 * TODO this will become obsolete when using declarative transaction managment
 * 
 * @author ruben
 */
public class DBUtil {

	private static EntityManagerFactory emf = null;
	public static EntityManagerFactory getEmf(){
		if(emf == null)
			emf = Persistence.createEntityManagerFactory("cmsUnit");
		return emf;
	}
	
	private static EntityManagerFactory localEmf = null;
	public static EntityManagerFactory getLocalEmf(){
		if(localEmf == null)
			localEmf = Persistence.createEntityManagerFactory("cmsUnitLocal");
		return localEmf;
	}
	
	private static ThreadLocal<EntityManager> cachedEMgr = new ThreadLocal<EntityManager>();	
	public static EntityManager initEmgr(){
		return DBUtil.initEmgr(false);
	}	
	public static EntityManager initEmgr(boolean local){
		if(local){
			cachedEMgr.set(getLocalEmf().createEntityManager());
		}else{
			cachedEMgr.set(getEmf().createEntityManager());
		}
		return getEntityMgr();
	}
	
	
	public static EntityManager getEntityMgr(){
		return cachedEMgr.get();
	}
}