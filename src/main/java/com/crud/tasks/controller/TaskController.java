package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RestController
@RequestMapping("v1/task")
public class TaskController {
    @Autowired
    DbService service;
    @Autowired
    TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET,value = "getTasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }
    @RequestMapping(method = RequestMethod.GET,value = "getTask")
    public TaskDto getTask(HttpServletRequest request,HttpServletResponse response) {
        String id = request.getParameter("id");
        int number = Integer.parseInt(id);
        long resultNumber = (long) number;
        Long resultId = resultNumber;
        return taskMapper.mapToTaskDto(service.getTask(resultId));
    }
    @RequestMapping(method = RequestMethod.DELETE,value = "deleteTask")
    public void deleteTask(Long taskId) {
    }
    @RequestMapping(method = RequestMethod.PUT,value = "updateTask")
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L,"Edited test title","Test content");
    }
    @RequestMapping(method = RequestMethod.POST,value = "createTask")
    public void createTask(TaskDto taskDto) {

    }

}
