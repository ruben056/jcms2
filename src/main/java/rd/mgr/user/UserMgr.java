package rd.mgr.user;

import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;

import rd.util.db.DBUtil;


public class UserMgr implements IUserMgr {

	@Override
	public Group[] getAllGroups() {
		TypedQuery<Group> qry = DBUtil.getEntityMgr().createNamedQuery("Group.findAll", Group.class);
		List<Group> grps = qry.getResultList();
		Group[] result = new Group[grps.size()];
		Iterator<Group> it = grps.iterator();
		for(int i = 0; it.hasNext(); i++){
			result[i] = it.next();
		}
		return result;
	}

	@Override
	public Group[] saveGroups( Group[] groups) {
		for (int i = 0; i < groups.length; i++) {
			Group grp = groups[i];
			if(grp.getId() <= 0){
				DBUtil.getEntityMgr().persist(grp);
			}else{
				DBUtil.getEntityMgr().merge(grp);
			}
		}
		return groups;
	}

	@Override
	public User getUserByID( long id) {
		return DBUtil.getEntityMgr().find(User.class, id);
	}

	@Override
	public void deleteUser( long id) {
		User u = getUserByID( id);
		DBUtil.getEntityMgr().remove(u);
	}

	@Override
	public User[] getAllUsers() {
		
		TypedQuery<User> qry = DBUtil.getEntityMgr().createNamedQuery("User.findAll", User.class);
		List<User> usrs = qry.getResultList();
		User[] result = new User[usrs.size()];
		Iterator<User> it = usrs.iterator();
		for(int i = 0; it.hasNext(); i++){
			result[i] = it.next();
		}
		return result;
	}

	@Override
	public User saveUser( User usr) {
		return saveUsers( new User[]{usr})[0];
	}

	@Override
	public User[] saveUsers( User[] usrs) {
		for (int i = 0; i < usrs.length; i++) {
			User usr = usrs[i];
			if(usr.getId() <= 0){
				DBUtil.getEntityMgr().persist(usr);
			}else{
				DBUtil.getEntityMgr().merge(usr);
			}
		}
		
		return usrs;
	}
	
	@Override
	public User getUserByEmail( String email) {
		User result = null;
		TypedQuery<User> qry = DBUtil.getEntityMgr().createNamedQuery("User.findByEmail", User.class);
		qry.setParameter("email", email);
		List<User> usrs = qry.getResultList();		
		if(usrs.size() > 0){
			result = usrs.get(0);
		}
		return result;
	}
}
