package iducs.springboot.board.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import iducs.springboot.board.domain.User;
import iducs.springboot.board.exception.ResourceNotFoundException;
import iducs.springboot.board.repository.UserRepository;

@Controller
public class HomeController {
	@Autowired UserRepository userRepo; // Dependency Injection
	
	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}
	@GetMapping("/register")
	public String regform() {
		return "regform";
	}
	
	@PostMapping("/users")
	public String createUser(@Valid User user, Model model) {
		userRepo.save(user);
		model.addAttribute("user", user);
		return "redirect:/users";
	}
	
	@GetMapping("/users")
	public String getAllUser(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}	
	@GetMapping("/users/byname")//byname?name="aaaa"
	public String getUsersByName(@Param(value="name")String name, Model model) {
		List<User> users = userRepo.findByName(name);
		model.addAttribute("users", users);
		return "userlist";
	}
	@GetMapping("/users/nameasc")
	public String getUsersByNameAsc(@Param(value="name")String name, Model model) {
		List<User> users = userRepo.findByNameOrderByIdAsc(name);
		model.addAttribute("users", users);
		return "userlist";
	}
	@GetMapping("/users/bycom")//byname?name="aaaa"
	public String getUsersByCompany(@Param(value="company")String company, Model model) {
		List<User> users = userRepo.findByCompany(company);
		model.addAttribute("users", users);
		return "userlist";
	}
	@GetMapping("/users/comasc")
	public String getUsersByCompanyAsc(@Param(value="company")String company, Model model) {
		List<User> users = userRepo.findByCompanyOrderByIdAsc(company);
		model.addAttribute("users", users);
		return "userlist";
	}
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId,  
			Model model) throws ResourceNotFoundException {
				User user = userRepo.findById(userId)
						.orElseThrow(() -> 
						new ResourceNotFoundException("not found " + userId ));
				model.addAttribute("user",user);
				return "user";
	}
	
	/*
	@GetMapping("/{fn}")
	public String getEmployeeByFirstName(@PathVariable(value = "fn") String firstName)
			throws ResourceNotFoundException {
		Employee employee = userRepo.findByFirstName(firstName)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this firstname :: " + firstName));
		return ResponseEntity.ok().body(employee);
	}
	*/
	@PutMapping("/users/{id}")
	public String updateUserById(@PathVariable(value = "id") Long userId,  
			@Valid User userDetails, Model model) throws ResourceNotFoundException {
				User user = userRepo.findById(userId)
						.orElseThrow(() -> 
						new ResourceNotFoundException("not found " + userId ));
				user.setName(userDetails.getName());
				user.setCompany(userDetails.getCompany());
				User userUpdate = userRepo.save(user);
				//model.addAttribute("user",userUpdate);
				return "redirect:/users";//업데이트 성공시 유저 users에 get방식으로 접근하되 model에 user 어트리뷰트를 전달
			}
	@DeleteMapping("/users/{id}")
	public String deleteUserById(@PathVariable(value = "id") Long userId,  
			Model model) throws ResourceNotFoundException {
				User user = userRepo.findById(userId)
						.orElseThrow(() -> 
						new ResourceNotFoundException("not found " + userId ));
				userRepo.delete(user); // 객체 삭제 -> jpa : record 삭제로 적용
				model.addAttribute("name", user.getName());
				return "disjoin";
			}	

}
