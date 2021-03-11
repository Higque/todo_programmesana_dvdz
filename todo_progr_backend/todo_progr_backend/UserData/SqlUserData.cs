using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
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
            User user = _userContext.Users.Where(user => user.Email == email && user.Password == password).FirstOrDefault();

            return user;
        }
    }
}
