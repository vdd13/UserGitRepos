package pl.dom.UserGitRepos.exception;

@SuppressWarnings("serial")
public class NoUserException extends RuntimeException{
	
	private String user;

	public NoUserException(String user) {
		super();
		this.user = "user " + user + " not found";
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
