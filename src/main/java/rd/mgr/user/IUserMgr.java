package rd.mgr.user;



public interface IUserMgr {

	public Group[] getAllGroups();
	public Group[] saveGroups( Group[] groups);
	
	public User getUserByID( long id);	
	public void deleteUser( long id);
	
	public User[] getAllUsers();
	public User saveUser( User usr);
	public User[] saveUsers( User[] usrs);
	public User getUserByEmail( String email);
}
