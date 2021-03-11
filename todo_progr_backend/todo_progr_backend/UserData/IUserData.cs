using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using todo_progr_backend.Models;

namespace todo_progr_backend.UserData
{
    public interface IUserData
    {
        List<User> GetUsers();

        List<User> GetUsersAndTasks();

        List<Tasks> GetUsersTasks(Guid id);

        int GetUserAmount();

        User GetUser(Guid id);

        User AddUser(User user);

        void DeleteUser(User user);

        User EditUser(User user);

        User Login(string email, string password);

    }
}
