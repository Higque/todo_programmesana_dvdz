using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace todo_progr_backend.Models
{
    public class Notifications
    {
        public Guid NotificationId { get; set; }
        public Task Task { get; set; }
        public DateTime RemindTime { get; set; }
        public string RemindMessage { get; set; }
    }
}
