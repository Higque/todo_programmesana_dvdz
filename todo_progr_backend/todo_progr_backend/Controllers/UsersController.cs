using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using todo_progr_backend.Models;
using todo_progr_backend.UserData;

namespace todo_progr_backend.Controllers
{
    [ApiController]
    public class UsersController : ControllerBase
    {
        private IUserData _userData;

        public UsersController(IUserData userData)
        {
            _userData = userData;
        }
        

        [HttpGet]
        [Route("api/[controller]")]
        public IActionResult GetUsers()
        {
            return Ok(_userData.GetUsers());
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


        [HttpPost]
        [Route("api/[controller]")]
        public IActionResult AddUser(User user)
        {
            _userData.AddUser(user);

            return Created(HttpContext.Request.Scheme + "://" + HttpContext.Request.Host + HttpContext.Request.Path + "/" + user.UserId
                , user);
        }
    }
}
