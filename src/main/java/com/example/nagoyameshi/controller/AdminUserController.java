
package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;


@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
	private final UserRepository userRepository;
	
	public AdminUserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	//会員一覧
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@PageableDefault(page = 0, size = 20, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Page<User> userPage;
		//会員検索
		if (keyword != null && !keyword.isEmpty()) {
			userPage = userRepository.findByUserNameLikeOrFuriganaLikeOrEmailLike("%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", pageable);
		} else {
			userPage = userRepository.findAll(pageable);
		}
		
		model.addAttribute("userPage", userPage);
		model.addAttribute("keyword", keyword);
		
		return "admin/users/index";
	
	}
	
	//会員詳細
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		User user = userRepository.getReferenceById(id);
		
		model.addAttribute("user", user);
		
		return "admin/users/show";
	}
	
	//エラーハンドリング未実装
	//userRepository.getReferenceById(id)がnullを返す場合に適切な対応を追加する
}
