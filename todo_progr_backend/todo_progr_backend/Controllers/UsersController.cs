﻿using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
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


        [HttpPatch]
        [Route("api/[controller]/{id}")]
        public IActionResult EditUser(Guid id, User user)
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
