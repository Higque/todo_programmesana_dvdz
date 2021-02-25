using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using todo_progr_backend.Models;

namespace todo_progr_backend.TaskData
{
    public interface ITaskData
    {
        List<Tasks> GetTasks();

        Tasks GetTask(Guid id);

        Tasks AddTask(Tasks task);

        void DeleteTask(Tasks task);

        Tasks EditTask(Tasks task);
    }
}
