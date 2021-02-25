using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using todo_progr_backend.Models;
using todo_progr_backend.TaskData;

namespace todo_progr_backend.Controllers
{
    public class TasksController : ControllerBase
    {
        private ITaskData _taskData;


        public TasksController(ITaskData taskData)
        {
            _taskData = taskData;
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
        public IActionResult AddTask(Tasks task)
        {
            _taskData.AddTask(task);

            return Created(HttpContext.Request.Scheme + "://" + HttpContext.Request.Host + HttpContext.Request.Path + "/" + task.TaskId
                , task);
        }


        [HttpDelete]
        [Route("api/[controller]/{id}")]
        public IActionResult DeleteTask(Guid id)
        {
            var user = _taskData.GetTask(id);
            if (user != null)
            {
                _taskData.DeleteTask(user);
                return Ok();
            }

            return NotFound("User with ID: " + id + " not found!");
        }


        [HttpPatch]
        [Route("api/[controller]/{id}")]
        public IActionResult EditUser(Guid id, Tasks task)
        {
            var existingUser = _taskData.GetTask(id);
            if (existingUser != null)
            {
                task.UserId = existingUser.UserId;
                _taskData.EditTask(task);
                return Ok();
            }

            return NotFound("User with ID: " + id + " not found!");
        }
    }
}
