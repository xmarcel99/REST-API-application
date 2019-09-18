package com.crud.tasks.controller;


import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DbService dbService;

    @MockBean
    TaskMapper taskMapper;

    @Test
    public void shouldFindAllTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>(Arrays.asList(new Task(1L, "testTile", "testContent")));
        List<TaskDto> taskDtos = tasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getTitle(), task.getContent()))
                .collect(Collectors.toList());
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.anyList())).thenReturn(taskDtos);
        //When && Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("testTile")))
                .andExpect(jsonPath("$[0].content", is("testContent")));
    }

    @Test
    public void shouldGetTaskById() throws Exception {
        //Given
        Optional<Task> optionalTask = Optional.of(new Task(1L, "testTitle", "TestContent"));
        TaskDto taskDto = new TaskDto(1L, "testTitle", "testContent");
        when(dbService.getTask(ArgumentMatchers.anyLong())).thenReturn(optionalTask);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);
        //When && Then
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("testTitle")))
                .andExpect(jsonPath("$.content", is("testContent")));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto content = new TaskDto(1L, "testTitle", "testContent");
        Task task = new Task(1L, "testTitle", "testContent");
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(content);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(content);
        //When && Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("testTitle")))
                .andExpect(jsonPath("$.content", is("testContent")));

    }

    @Test
    public void shouldCreateTask() throws Exception{
        //Given
        TaskDto content = new TaskDto(1L, "testTitle", "testContent");
        Task task = new Task(1L, "testTitle", "testContent");
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(content);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(content);
        //When && Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.title",is("testTitle")))
                .andExpect(jsonPath("$.content",is("testContent")));
    }

}