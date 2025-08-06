package pl.dom.UserGitRepos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pl.dom.UserGitRepos.dto.BranchInfoDto;
import pl.dom.UserGitRepos.service.UserReposService;

@RestController
public class GitReposController {
	
	@Autowired
	UserReposService reposService;
	
	@GetMapping("repos/{user}")
	public List<BranchInfoDto> getUserRepos(@PathVariable String user){
		return reposService.getUserRepos(user);
	}
}
