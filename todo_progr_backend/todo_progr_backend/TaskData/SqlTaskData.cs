using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using todo_progr_backend.Models;

namespace todo_progr_backend.TaskData
{
    public class SqlTaskData : ITaskData
    {
        private UserContext _context;

        public SqlTaskData(UserContext userContext)
        {
            _context = userContext;
        }
        public Tasks AddTask(Tasks task)
        {
            task.TaskId = Guid.NewGuid();
            _context.Tasks.Add(task);
            _context.SaveChanges();
            return task;
        }

        public void DeleteTask(Tasks task)
        {
            _context.Tasks.Remove(task);
            _context.SaveChanges();
        }

        public Tasks EditTask(Tasks task)
        {
            var existingTask = _context.Tasks.Find(task.TaskId);
            if (existingTask != null)
            {
                existingTask.Content = task.Content;
                existingTask.CreatedDate = task.CreatedDate;
                existingTask.UserId = task.UserId;

                _context.Tasks.Update(existingTask);
                _context.SaveChanges();
            }

            return task;
        }

        public Tasks GetTask(Guid id)
        {
            return _context.Tasks.Find(id);
        }

        public List<Tasks> GetTasks()
        {
            return _context.Tasks.ToList();
        }

    }
}
