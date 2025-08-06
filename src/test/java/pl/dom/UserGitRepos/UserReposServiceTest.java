package pl.dom.UserGitRepos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;

import pl.dom.UserGitRepos.model.Branch;
import pl.dom.UserGitRepos.model.Commit;
import pl.dom.UserGitRepos.model.Repo;
import pl.dom.UserGitRepos.model.RepoOwner;
import pl.dom.UserGitRepos.service.UserReposService;

@RestClientTest(UserReposService.class)
public class UserReposServiceTest {
	
	@Autowired
	MockRestServiceServer server;
	
	@Autowired
	UserReposService repoService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void findRepos() throws JsonProcessingException {
		//given
		List<Repo> repoList = List.of(
				new Repo("repo1", new RepoOwner("vdd13")),
				new Repo("repo2", new RepoOwner("vdd13"))
				);
		//when
		server
			.expect(requestTo("https://api.github.com/users/vdd13/repos"))
			.andRespond(withSuccess(objectMapper.writeValueAsString(repoList), MediaType.APPLICATION_JSON));
		//then
		var data = repoService.getUserReposFromGit("vdd13");
		assertEquals(repoList.size(),data.size());
	}
	
	@Test
	void findBranches() throws JsonProcessingException {
		//given
		List<Branch> branchList = List.of(
				new Branch("branch1", new Commit("sha1")),
				new Branch("branch2", new Commit("sha2"))
				);
		//when
		server
			.expect(requestTo("https://api.github.com/repos/vdd13/repo/branches")) 
			.andRespond(withSuccess(objectMapper.writeValueAsString(branchList), MediaType.APPLICATION_JSON));
		
		//then
		var data1 = repoService.getRepoBranches("vdd13", "repo");
		assertEquals(branchList.size(),data1.size());
	}
}
