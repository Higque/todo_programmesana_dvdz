using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using todo_progr_backend.Models;

namespace todo_progr_backend.UserData
{
    public class SqlUserData : IUserData
    {
        private UserContext _userContext;

        public SqlUserData(UserContext userContext)
        {
            _userContext = userContext;
        }
        public User AddUser(User user)
        {
            user.UserId = Guid.NewGuid();
            _userContext.Users.Add(user);
            _userContext.SaveChanges();
            return user;
        }

        public void DeleteUser(User user)
        {
            _userContext.Users.Remove(user);
            _userContext.SaveChanges();
        }

        public User EditUser(User user)
        {
            var existingUser = _userContext.Users.Find(user.UserId);
            if (existingUser != null)
            {
                existingUser.UserName = user.UserName;
                existingUser.Password = user.Password;
                existingUser.Email = user.Email;

                _userContext.Users.Update(existingUser);
                _userContext.SaveChanges();
            }

            return user;
        }

        public User GetUser(Guid id)
        {
            var user = _userContext.Users.Find(id);
            return user;
        }

        public int GetUserAmount()
        {
            return _userContext.Users.Count();
        }

        public List<User> GetUsers()
        {
           return _userContext.Users.ToList();
        }

        public List<User> GetUsersAndTasks()
        {
            return _userContext.Users.Include(x => x.Tasks).ToList();
        }

        public List<Tasks> GetUsersTasks(Guid id)
        {
            User user = _userContext.Users.Where(user => user.UserId == id).Include(user => user.Tasks).FirstOrDefault();
            return user.Tasks;
        }

        public User Login(string email, string password)
        {
            User user = _userContext.Users
                .AsEnumerable()
                .Where(user => Encoding.UTF8.GetString(Convert.FromBase64String(user.Email)) == email && Encoding.UTF8.GetString(Convert.FromBase64String(user.Password)) == password)
                .FirstOrDefault();

            return user;
        }

        public bool IsValidEmail(string inputEmail)
        {
            Regex re = new Regex(@"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$",
                          RegexOptions.IgnoreCase);
            return re.IsMatch(inputEmail);
        }

        public bool ValidateUserData(User user)
        {
            try
            {
                if (_userContext.Users.Any(u => u.UserName == user.UserName && u.Email == user.Email))
                {
                    return false;
                }

                if (_userContext.Users.Any(u => u.Email == user.Email))
                {
                    return false;
                }

                if (_userContext.Users.Any(u => u.UserName == user.UserName))
                {
                    return false;
                }

                bool emailIsValid = IsValidEmail(Encoding.UTF8.GetString(Convert.FromBase64String(user.Email)));
                bool passIsValid = IsValidPassword(Encoding.UTF8.GetString(Convert.FromBase64String(user.Password)));

                if (!emailIsValid)
                {
                    return false;
                }

                if (!passIsValid)
                {
                    return false;
                }

                return true;
            }
            catch (Exception)
            {
                throw;
            }

        }

        public bool IsValidPassword(string password)
        {
            Regex re = new Regex(@"^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$",
              RegexOptions.IgnoreCase);
            return re.IsMatch(password);
        }
    }
}
