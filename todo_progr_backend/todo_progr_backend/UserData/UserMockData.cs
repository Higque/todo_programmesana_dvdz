using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using todo_progr_backend.Models;

namespace todo_progr_backend.UserData
{
    public class UserMockData: IUserData
    {
        private List<User> users = new List<User>()
        {
            new User()
            {
                UserId = Guid.NewGuid(),
                UserName = "AAAA",
                Password = "QWERTY",
                Email = "a@a.com"
            },
            new User()
            {
                UserId = Guid.NewGuid(),
                UserName = "BBBB",
                Password = "QWERTY",
                Email = "b@b.com"
            }
        };

        public User AddUser(User user)
        {
            user.UserId = Guid.NewGuid();
            users.Add(user);

            return user;
        }

        public void DeleteUser(User user)
        {
            users.Remove(user);
        }

        public User EditUser(User user)
        {
            var existingUser = GetUser(user.UserId);

            existingUser.UserName = user.UserName;

            return existingUser;
        }

        public User GetUser(Guid id)
        {
            return users.SingleOrDefault(x => x.UserId == id);  
        }

        public List<User> GetUsers()
        {
            return users;
        }
    }
}

