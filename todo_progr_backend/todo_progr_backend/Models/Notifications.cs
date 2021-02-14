using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace todo_progr_backend.Models
{
    public class Notifications
    {
        [Key]
        public Guid NotificationId { get; set; }
        [Required]
        public DateTime RemindTime { get; set; }
        [Required]
        public string RemindMessage { get; set; }

        public Task Task { get; set; }
    }
}
