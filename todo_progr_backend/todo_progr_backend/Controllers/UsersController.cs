using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Text;
using todo_progr_backend.Models;
using todo_progr_backend.UserData;

namespace todo_progr_backend.Controllers
{
   // [Authorize]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private IUserData _userData;
        private UserContext _context;

        public UsersController(IUserData userData, UserContext context)
        {
            _userData = userData;
            _context = context;
        }

        [HttpGet]
        [Route("api/[controller]")]
        public IActionResult GetUsers()
        {
            return Ok(_userData.GetUsers());
        }

        [Route("/api/[controller]/global")]
        [HttpGet]
        public IActionResult GetUsersAndTasks()
        {
            return Ok(_userData.GetUsersAndTasks());
        }

        [HttpGet]
        [Route("api/[controller]/{id}")]
        public IActionResult GetUser(Guid id)
        {
            var user = Ok(_userData.GetUser(id));
            if (user != null)
            {
                return Ok(user);
            }

            return NotFound("User with ID: " + id + " not found!");
        }

        [HttpGet]
        [Route("api/[controller]/{id}/tasks")]
        public IActionResult GetUsersTasks(Guid id)
        {
            var tasks = _userData.GetUsersTasks(id);
            if (tasks != null)
            {
                return Ok(tasks);
            }

            return NotFound("User with ID: " + id + " not found!");
        }


        [Route("/api/[controller]/amount")]
        [HttpGet]
        public int GetUserAmount()
        {

            return _userData.GetUserAmount();

        }

        [HttpPost]
        [Route("api/[controller]")]
        public IActionResult AddUser(User user)
        {
            if (_userData.ValidateUserData(user))
            {
                _userData.AddUser(user);

                return Created(HttpContext.Request.Scheme + "://" + HttpContext.Request.Host + HttpContext.Request.Path + "/" + user.UserId
                    , user);
            }

            return BadRequest("Incorrect user data!");

        }

        //[HttpPost]
        //[Route("api/[controller]/login")]
        //public IActionResult Login(string email, string password)
        //{
        //    var user = _userData.Login(email, password);

        //    if (user != null)
        //    {
        //        return Ok(user);
        //    }

        //    return BadRequest("Incorrect user data!");
        //}

        [HttpPost]
        [Route("api/[controller]/login")]
        public IActionResult Login(string loginToken)
        {
            try
            {
                var bytes = Convert.FromBase64String(loginToken);
                string[] credentials = Encoding.UTF8.GetString(bytes).Split(":");
                string email = credentials[0];
                string password = credentials[1];

                var user = _userData.Login(email, password);

                if (user != null)
                {
                    return Ok(user);
                }
            }
            catch (Exception)
            {
                throw;
            }

            return BadRequest("Incorrect user data!");
        }



        [HttpDelete]
        [Route("api/[controller]/{id}")]
        public IActionResult DeleteUser(Guid id)
        {
            var user = _userData.GetUser(id);
            if (user != null)
            {
                _userData.DeleteUser(user);
                return Ok();
            }

            return NotFound("User with ID: " + id + " not found!");
        }


        [HttpPut]
        [Route("api/[controller]/{id}")]
        public IActionResult EditUser(Guid id,[FromBody] User user)
        {
            var existingUser = _userData.GetUser(id);
            if (existingUser != null)
            {
                user.UserId = existingUser.UserId;
                _userData.EditUser(user);
                return Ok();
            }

            return NotFound("User with ID: " + id + " not found!");
        }
    }
}
