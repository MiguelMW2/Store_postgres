package mx.ipn.cic.store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mx.ipn.cic.store.entities.UserEntity;
import mx.ipn.cic.store.services.UserService;

@Controller
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(path="/all")
	public ModelAndView all() {
		ModelAndView mav = new ModelAndView("user/all");
		List<UserEntity> all = userService.all();
		mav.addObject("users", all);
		return mav;
	}

	@GetMapping(path="/new")
	public ModelAndView newForm() {
		ModelAndView mav = new ModelAndView("user/form");
		mav.addObject("user", new UserEntity());
		return mav;
	}
	
	@PostMapping(path="/save")
	public String save(@ModelAttribute(name="user") UserEntity user)
	{
		this.userService.save(user);
		return "redirect:/user/all";
	}

	@GetMapping(path="/edit/{id}")
	public ModelAndView edit(@PathVariable(name="id") Integer id) {
		UserEntity found = this.userService.findById(id);
		ModelAndView mav = new ModelAndView("user/form");
		mav.addObject("user", found);
		return mav;
	}

	@GetMapping(path="/delete/{id}")
	public String delete(@PathVariable(name="id") Integer id) {
		this.userService.delete(id);
		return "redirect:/user/all";
	}
}
