using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace todo_progr_backend.Models
{
    public class Subtask
    {
        public Guid SubtaskId { get; set; }
        public Task task { get; set; }
        public DateTime CreatedDate { get; set; }
        public string Content { get; set; }
    }
}
