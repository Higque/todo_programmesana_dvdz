using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace todo_progr_backend.Models
{
    public class Tasks
    {
        public Guid TaskId { get; set; }
        public User User { get; set; }
        public DateTime CreatedDate { get; set; }
        public string Content { get; set; }
        public Notifications Notification { get; set; }
    }
}
