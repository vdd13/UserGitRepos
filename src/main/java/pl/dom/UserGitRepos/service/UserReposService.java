package pl.dom.UserGitRepos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import pl.dom.UserGitRepos.dto.BranchInfoDto;
import pl.dom.UserGitRepos.exception.NoUserException;
import pl.dom.UserGitRepos.model.Branch;
import pl.dom.UserGitRepos.model.Repo;

@Service
public class UserReposService {

	private List<BranchInfoDto> branchList;
	private final RestClient restClient;
	
	public UserReposService(RestClient.Builder builder) {
		this.restClient = builder
				.baseUrl("https://api.github.com/")
				.build();
	}
	
	public List<BranchInfoDto> getUserRepos(String user) {
		branchList = new ArrayList<>();
		var userRepoList = getUserReposFromGit(user);
		userRepoList.forEach(repo -> getBranches(user, repo.name(), branchList));
		return branchList;
	}	
		
	public List<Repo> getUserReposFromGit(String user){
		return restClient
			.get()
			.uri("users/"+ user +"/repos")
			.accept(MediaType.APPLICATION_JSON)
			.exchange((req,res) -> {
				if(res.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
					throw new NoUserException(user);
				}else {
					return res.bodyTo(new ParameterizedTypeReference<List<Repo>>() {});
				}
			});
	}

	public void getBranches(String user, String repo, List<BranchInfoDto> branchesList) {
		var repoBranchList = getRepoBranches(user, repo);
		repoBranchList.forEach(branch -> { 
			branchesList.add(createNewBranchInfo(user, repo, branch));
			});
	}

	public List<Branch> getRepoBranches(String user, String repo) {
		return restClient
			.get()
			.uri("repos/"+ user +"/" + repo + "/branches")
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(new ParameterizedTypeReference<List<Branch>>(){});
	}

	private BranchInfoDto createNewBranchInfo(String user, String repo, Branch branch) {
		return new BranchInfoDto(repo, user, branch.name(), branch.commit().sha());
	}
}
