package task_manager.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import task_manager.model.Task;
import task_manager.model.User;
import task_manager.service.TaskService;
import task_manager.service.UserService;

@Controller
@Slf4j
@RequestMapping("/tasks")
public class TaskController {

    UserService userService;
    TaskService taskService;

    @Autowired
    public TaskController(
            UserService userService,
            TaskService taskService
    ) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @ModelAttribute(name = "user")
    public User user(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            user = userService.createUser();
            session.setAttribute("user", user);
            log.info("Created new user {}", user);
        }
        return user;
    }


    @ModelAttribute(name = "task")
    public Task task() {
        return new Task();
    }

    //create task page
    @GetMapping("/new")
    public String createTaskPage() {
        return "createTaskPage";
    }

    // post created task (form with action)
    @PostMapping
    public String processTask(
            @ModelAttribute("task") @Valid Task task, Errors errors,
            HttpSession session
    ) {
        if(errors.hasErrors()) {
            log.info("Something went wrong, task wasn't created");
            return "createTaskPage";
        }

        User user = (User) session.getAttribute("user");
        task.setUserId(user.getId());
        taskService.createTask(task);
        return "redirect:/tasks/new";
    }

    //all tasks page
    @GetMapping
    public String tasksPage(
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("tasks", taskService.getAllTasks(user.getId()));
        return "allTasksPage";
    }

    //delete task
    @GetMapping("/delete-task")
    public String deleteTask(
            @RequestParam Long id,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        taskService.deleteTask(id, user.getId());
        return "redirect:/tasks";
    }

}
