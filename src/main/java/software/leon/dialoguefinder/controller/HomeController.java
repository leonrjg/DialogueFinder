package software.leon.dialoguefinder.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import software.leon.dialoguefinder.service.SearchService;

@Controller
public class HomeController {
    private final SearchService searchService;

    public HomeController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/search")
    public String searchDialogues(final Model model, @NotBlank @RequestParam String dialogue, @RequestParam(defaultValue = "5") @Max(10) int limit) {
        model.addAttribute("results", searchService.searchDialogues(dialogue, limit));
        return "results";
    }

    @GetMapping("/dialogue")
    public String seeDialogue(final Model model, @NotBlank @RequestParam String episode, @NotNull @RequestParam Integer index) {
        model.addAttribute("episode", episode);
        model.addAttribute("index", index);
        model.addAttribute("context_dialogues", searchService.getDialogueContext(episode, index));
        return "dialogue";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }


}