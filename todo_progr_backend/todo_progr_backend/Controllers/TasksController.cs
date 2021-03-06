using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using todo_progr_backend.Models;
using todo_progr_backend.TaskData;

namespace todo_progr_backend.Controllers
{
    [Authorize]
    public class TasksController : ControllerBase
    {
        private ITaskData _taskData;
        private UserContext _context;

        public TasksController(ITaskData taskData, UserContext context)
        {
            _taskData = taskData;
            _context = context;
        }

        [HttpGet]
        [Route("api/[controller]")]
        public IActionResult GetTasks()
        {
            return Ok(_taskData.GetTasks());
        }


        [HttpGet]
        [Route("api/[controller]/{id}")]
        public IActionResult GetTask(Guid id)
        {
            var user = Ok(_taskData.GetTask(id));
            if (user != null)
            {
                return Ok(user);
            }

            return NotFound("User with ID: " + id + " not found!");
        }


        [HttpPost]
        [Route("api/[controller]")]
        public IActionResult AddTask([FromBody] Tasks task)
        {
            _taskData.AddTask(task);

            return Created(HttpContext.Request.Scheme + "://" + HttpContext.Request.Host + HttpContext.Request.Path + "/" + task.TaskId
                , task);
        }

        [HttpDelete]
        [Route("api/[controller]/{id}")]
        public IActionResult DeleteTask(Guid id)
        {
            var task = _taskData.GetTask(id);
            if (task != null)
            {
                _taskData.DeleteTask(task);
                return Ok();
            }

            return NotFound("User with ID: " + id + " not found!");
        }


        [HttpPut]
        [Route("api/[controller]/{id}")]
        public IActionResult EditTask(Guid id, [FromBody] Tasks task)
        {
            var existingTask = _taskData.GetTask(id);
            if (existingTask != null)
            {
                task.TaskId = existingTask.TaskId;
                _taskData.EditTask(task);
                return Ok();
            }

            return NotFound("Task with ID: " + id + " not found!");
        }


        //[HttpPut]
        //[Route("api/[controller]/{id}")]
        //public IActionResult EditTask(Guid id,[FromBody] Tasks task)
        //{
        //    var existingTask = _context.Tasks.Find(id);
        //    if (existingTask != null)
        //    {
        //        existingTask.Content = task.Content;
        //        existingTask.CreatedDate = task.CreatedDate;
        //        existingTask.UserId = task.UserId;

        //        _context.Tasks.Update(existingTask);
        //        _context.SaveChanges();

        //        return Ok();
        //    }

        //    return NotFound("Task with ID: " + id + " not found!");
        //}
    }
}
