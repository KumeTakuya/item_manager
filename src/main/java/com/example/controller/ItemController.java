package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Item;
import com.example.form.ItemForm;
import com.example.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	private final ItemService itemService;
	
	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Item> items = this.itemService.findByDeletedAtIsNull();
		model.addAttribute("items", items);
		return "item/index";
	}
	
	@GetMapping("toroku")
	public String torokuPage(@ModelAttribute("itemForm") ItemForm itemForm) {
		return "item/torokuPage";
	}
	
	@PostMapping("toroku")
	public String toroku(ItemForm itemForm) {
		this.itemService.save(itemForm);
		return "redirect:/item";
	}
	
	@GetMapping("henshu/{id}")
	public String henshuPage(@PathVariable("id") Integer id, Model model
							, @ModelAttribute("itemForm") ItemForm itemForm) {
		Item item = this.itemService.findById(id);
		itemForm.setName(item.getName());
		itemForm.setPrice(item.getPrice());
		model.addAttribute("id", id);
		return "item/henshuPage";
	}
	
	@PostMapping("henshu/{id}")
	public String henshu(@PathVariable("id") Integer id, ItemForm itemForm) {
		this.itemService.update(id, itemForm);
		return "redirect:/item";
	}
	
	@PostMapping("sakujo/{id}")
	public String sakujo(@PathVariable("id") Integer id) {
		this.itemService.delete(id);
		return "redirect:/item";
	}
}